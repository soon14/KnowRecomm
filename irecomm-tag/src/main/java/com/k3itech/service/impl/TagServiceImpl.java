package com.k3itech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.summary.TextRankKeyword;
import com.k3itech.irecomm.caltks.entity.DkZhiliangwentianli;
import com.k3itech.irecomm.caltks.entity.MetaKnowledge;
import com.k3itech.irecomm.caltks.entity.SystemFile;
import com.k3itech.irecomm.caltks.entity.TreeNode;
import com.k3itech.irecomm.caltks.service.IDkZhiliangwentianliService;
import com.k3itech.irecomm.caltks.service.IMetaKnowledgeService;
import com.k3itech.irecomm.caltks.service.ISystemFileService;
import com.k3itech.irecomm.caltks.service.ITreeNodeService;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreTagWord;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.entity.PersonPost;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.re.service.IPersonPostService;
import com.k3itech.irecomm.re.service.impl.IreTagWordServiceImpl;
import com.k3itech.irecomm.yunque.entity.AdminUser;
import com.k3itech.irecomm.yunque.entity.ZzMessageInfo;
import com.k3itech.irecomm.yunque.service.IAdminUserService;
import com.k3itech.irecomm.yunque.service.IZzMessageInfoService;
import com.k3itech.service.TagService;
import com.k3itech.utils.FileUtils;
import com.k3itech.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-26
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private IIreTagWordService iiTagWordService;

    @Autowired
    private IDkZhiliangwentianliService iDkZhiliangwentianliService;

    @Autowired
    private ISystemFileService systemFileService;

    @Autowired
    private IIreKnowledgeInfoService iiKnowledgeInfoService;

    @Autowired
    private IMetaKnowledgeService iMetaKnowledgeService;

    @Autowired
    private IAdminUserService iAdminUserService;

    @Autowired
    private IZzMessageInfoService iZzMessageInfoService;
    @Autowired
    private IIreUserFollowService iiUserFollowService;

    @Autowired
    private IPersonPostService iPersonPostService;

    @Autowired
    private ITreeNodeService iTreeNodeService;


    @Value("${calc.ktypes}")
    private String ktypes;
    @Value("${calc.orgcode}")
    private String orgcode;

    @Value("${calc.zhilianganli.ktype}")
    private Integer zhiliang;

    static final String SPLITCHR= ",";

    static final Integer ISDEL=1;

    @Override
    public void tagKnowledge() {
        QueryWrapper<MetaKnowledge> queryWrapper= new QueryWrapper();
        System.out.println(ktypes);

        queryWrapper.in("KTYPEID",ktypes.split(SPLITCHR));
        queryWrapper.and(wrapper ->wrapper.ne("ISDEL",ISDEL).or().isNull("ISDEL"));
        List<MetaKnowledge> ks= iMetaKnowledgeService.list(queryWrapper);
        QueryWrapper<MetaKnowledge> queryWrapperdel= new QueryWrapper();
        queryWrapperdel.eq("ISDEL",ISDEL);
        List<MetaKnowledge> deks= iMetaKnowledgeService.list(queryWrapperdel);
        for (MetaKnowledge metaKnowledge:ks){
            IreKnowledgeInfo iKnowledgeInfo = new IreKnowledgeInfo();

            if (metaKnowledge.getKnowledgetypeId().compareTo(new BigDecimal(zhiliang))==0){
                String tagkeywords=tagZhilianganli(metaKnowledge.getId());
                iKnowledgeInfo.setTagKeywords(tagkeywords);

            }else {
                try {
                    String content = getContentFromCaltksFile(metaKnowledge.getId());
                    List<String> keywords = new ArrayList<>();
                    keywords.addAll(getTagkeywords(content));
                    String tagkeywords = String.join(",", keywords);
                    iKnowledgeInfo.setTagKeywords(tagkeywords);




                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            iKnowledgeInfo.setAbstractText(metaKnowledge.getAbstractText());
            iKnowledgeInfo.setTitle(metaKnowledge.getTitleName());
            iKnowledgeInfo.setCreateTime(metaKnowledge.getUploadTime());
            iKnowledgeInfo.setId(metaKnowledge.getId() + "");
            iKnowledgeInfo.setSourceId(metaKnowledge.getId() + "");
            iKnowledgeInfo.setKtype(metaKnowledge.getKtypeid() + "");
            iKnowledgeInfo.setKtype(metaKnowledge.getKtypeid()+"");
//                iKnowledgeInfo.setAuthor(metaKnowledge.get);
            iKnowledgeInfo.setSecurityLevel(metaKnowledge.getSecurityLevel());
            iKnowledgeInfo.setTagModel("");
            iKnowledgeInfo.setTagDevice("");
            String domain="";
            BigDecimal domainid=metaKnowledge.getDomainNodeid();
            if(ObjectUtils.isNotEmpty(domainid)) {
                TreeNode treeNode = iTreeNodeService.getById(domainid);

                if (ObjectUtils.isNotEmpty(treeNode)) {
                    domain = treeNode.getNodeName();
                }
                iKnowledgeInfo.setDomain(domain);
            }
//            iKnowledgeInfo.setKnowledgeSource("meta_knowledge");
            iiKnowledgeInfoService.saveOrUpdate(iKnowledgeInfo);
        }

        for (MetaKnowledge metaKnowledge:deks) {
           //同步删除已删除的知识数据
            iiKnowledgeInfoService.removeById(metaKnowledge.getId()+"");
        }

    }

    public String tagZhilianganli(BigDecimal id) {
        DkZhiliangwentianli zhiliang= iDkZhiliangwentianliService.getById(id);
        if (zhiliang==null){
            log.debug("zhiliang kong "+id);
            return "";
        }

            List<String> keywords=new ArrayList<>();

            String text = zhiliang.getChanpinbianhao()+zhiliang.getXinghaoleibie()+zhiliang.getXinghaomingcheng()
                    +zhiliang.getChanpindaihao()+zhiliang.getChanpinmingcheng()+zhiliang.getSuoshufenxitong()
                    +zhiliang.getGongzuojieduan()+zhiliang.getGuzhanggaishu()+zhiliang.getYuanyinfenlei()
                    +zhiliang.getYuanyinfenxi()+zhiliang.getYuanyinfenlei2()+zhiliang.getPicixingwenti()
                    +zhiliang.getJiuzhengcuoshi()+zhiliang.getWaixieguanliyuanyin()
                    +zhiliang.getJiafangguanli();


          keywords=getTagkeywords(text);
          String tagkeywords= String.join(",",keywords);

             return tagkeywords;


    }

    @Override
    public void tagUsers() {
        QueryWrapper<AdminUser> orgqueryWrapper= new QueryWrapper();
        for (String org:orgcode.split(SPLITCHR)) {
            orgqueryWrapper.like("ORG_CODE",org).or(true);
        }
        orgqueryWrapper.and(wrapper ->wrapper.ne("ISDEL",ISDEL).or().isNull("ISDEL"));
        List<AdminUser> users= iAdminUserService.list(orgqueryWrapper);
        System.out.println("usersize: "+users.size());
        QueryWrapper queryWrapperdel= new QueryWrapper();
        queryWrapperdel.eq("ISDEL",ISDEL);
        List<AdminUser> deks= iAdminUserService.list(queryWrapperdel);

        for (AdminUser user:users){
              QueryWrapper<ZzMessageInfo> queryWrapper= new QueryWrapper();
              queryWrapper.eq("SENDER",user.getId());
              queryWrapper.eq("FILE_TYPE",3);
            List<ZzMessageInfo> zzMessageInfos= iZzMessageInfoService.list(queryWrapper);
            List<String> keywords = new ArrayList<>();
            for (ZzMessageInfo zzMessageInfo:zzMessageInfos){
                keywords.addAll(getTagkeywords( zzMessageInfo.getMsg()));
            }


            String tagkeywords= String.join(",",keywords);
            IreUserFollow iUserFollow = new IreUserFollow();
            iUserFollow.setIdNum(user.getPId());
            iUserFollow.setUserName(user.getName());
            iUserFollow.setOrgCode(user.getOrgCode());
            iUserFollow.setOrgName(user.getOrgName());
            PersonPost personPost = iPersonPostService.getById(user.getPId());
            if (personPost!=null) {
                iUserFollow.setUserJob(personPost.getVdefl());
            }
            iUserFollow.setFollowPro(tagkeywords);
            iUserFollow.setFollowModel("");
            iUserFollow.setFollowDevice("");
            iUserFollow.setSecretLevel(user.getSecretLevel());

            iiUserFollowService.saveOrUpdate(iUserFollow);
        }

        for (AdminUser adminUser:deks) {
            //同步删除已删除的用户数据
            if (ObjectUtils.isNotEmpty(adminUser.getPId())) {
                iiUserFollowService.removeById(adminUser.getPId());
            }
        }

    }

    public  List<String> getTagkeywords(String text){
        return textRank(text);
    }



    public List<String> textRank(String text){

        List<String> keywords=TextRankKeyword.getKeywordList(text, 1000);
        if (ObjectUtils.isEmpty(keywords)){
            return new ArrayList<>();
        }
        List<String> newKeywords= keywords;
        for (String keyword:keywords){
            QueryWrapper<IreTagWord> queryWrapper= new QueryWrapper<>();
            queryWrapper.eq("WORD",keyword);
            List<IreTagWord> tagWords=iiTagWordService.list(queryWrapper);
           /* if (tagWords.size()<=0){
                newKeywords.remove(keyword);
            }*/
        }


        return newKeywords;
    }

    public String getContentFromCaltksFile(BigDecimal id) throws SQLException, IOException, TikaException, SAXException {
        SystemFile systemFile=systemFileService.getById(id);
        if (ObjectUtils.isNotEmpty(systemFile)){
            log.debug("无数据：" +id);
            return "";
        }
        log.debug("获取文本：" +id);
        byte[] filebinary= systemFile.getFileBinary();
        InputStream inputStream=null;
        if(ObjectUtils.isNotEmpty(filebinary)) {
            inputStream =new ByteArrayInputStream(filebinary);
        }else{
           log.debug("abc"+systemFile.toString());
            return "";
        }

       return FileUtils.getContent(inputStream);

    }


}
