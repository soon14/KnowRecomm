package com.k3itech.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.caltks.entity.MetaKnowledge;
import com.k3itech.irecomm.caltks.service.IMetaKnowledgeService;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreRecommLog;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.entity.IreUserRecommresult;
import com.k3itech.irecomm.re.service.IIreRecommLogService;
import com.k3itech.irecomm.re.service.IIreUserRecommresultService;
import com.k3itech.service.PostService;
import com.k3itech.service.RedisService;
import com.k3itech.utils.*;
import com.k3itech.vo.RecommContent;
import com.k3itech.vo.RecommResult;
import com.k3itech.vo.RecommResults;
import com.k3itech.vo.YunqueContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.openapisdk.entity.OpenApiRequestParamVo;
import org.openapisdk.request.NoticeRequest;
import org.openapisdk.response.ApiResponse;
import org.openapisdk.service.OpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dell
 * @since 2021-05-16
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {
    final static Integer POSTNUM = 5;
    @Autowired
    private RedisService redisService;

    @Autowired
    private IIreRecommLogService iIreRecommLogService;
    @Autowired
    private IIreUserRecommresultService iIreUserRecommresultService;

    @Autowired
    private IMetaKnowledgeService iMetaKnowledgeService;

    @Autowired
    private ServerConfig serverConfig;

    @Value("${yunque.url}")
    private String knowledgeurl;

    @Override
    public boolean postKnowledge(IreUserFollow iUserFollow) {
        boolean flag=true;
        List<String> ids = new ArrayList<>();
        List<RecommContent> recommContents = new ArrayList<>();

        setRecommresults(iUserFollow,recommContents,ids,true);

            if (ObjectUtils.isNotEmpty(recommContents)) {
//                云雀协议封装

                YunqueContent yunqueContent = new YunqueContent();
                yunqueContent.setReceiverId(iUserFollow.getIdNum());
                yunqueContent.setMsgContent(recommContents);
                yunqueContent.setReceiverName(iUserFollow.getUserName());
                yunqueContent.setSenderOrgName(iUserFollow.getOrgName());
                yunqueContent.setLevel(SecretLevel.getSecretLevelsByUserLevel(iUserFollow.getSecretLevel()));

                log.info("post recommComments；" + recommContents.toString());
                YunqueClient yunqueClient = new YunqueClient();
                flag=yunqueClient.sendMessage(yunqueContent);
                IreRecommLog ireRecommLog = new IreRecommLog();
                ireRecommLog.setIdNum(iUserFollow.getIdNum());
                ireRecommLog.setKnowledge(StringUtils.join(ids, ","));
                Date day = new Date();
                Timestamp localDateTime = new Timestamp(day.getTime());

                ireRecommLog.setPostTime(localDateTime);
//             调用成功，则记录推荐流水，下次不再推荐
                if (flag) {

                    ireRecommLog.setStatus(CommonConstants.STATUS_SUCCESS);

                } else {
                    ireRecommLog.setStatus(CommonConstants.STATUS_FAIL);
                    flag = false;
                }

                iIreRecommLogService.save(ireRecommLog);

            }






        return flag;


    }




    @Override
    public  List<RecommContent> getKnowledge(IreUserFollow iUserFollow) {

        List<RecommContent> recommContents = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        setRecommresults(iUserFollow,recommContents,ids,false);

        return recommContents;


    }

    public void setRecommresults(IreUserFollow iUserFollow, List<RecommContent> recommContents,  List<String> ids,boolean flag){
        String key = JSON.toJSONString(iUserFollow);
        String json = redisService.get(key);
        log.info("json: " + json);
        if (ObjectUtils.isEmpty(json)) {

        } else {
            JSONObject jsonObject = JSON.parseObject(json);
            RecommResults recommResults = jsonObject.toJavaObject(RecommResults.class);
            List<RecommResult> recommResultList = recommResults.getRecommResults();
            Collections.sort(recommResultList);
            for (RecommResult recommResult : recommResultList) {
                IreKnowledgeInfo iKnowledgeInfo = recommResult.getInfo();
                QueryWrapper<IreRecommLog> ireRecommLogQueryWrapper = new QueryWrapper<>();
                ireRecommLogQueryWrapper.eq("ID_NUM", iUserFollow.getIdNum()).eq("STATUS",CommonConstants.STATUS_SUCCESS);
                ireRecommLogQueryWrapper.and(wrapper -> wrapper.like("KNOWLEDGE", iKnowledgeInfo.getSourceId()).or().like("KNOWLEDGE", iKnowledgeInfo.getSourceId() + ",%").or().like("KNOWLEDGE", "%," + iKnowledgeInfo.getSourceId()));
                List<IreRecommLog> ireRecommLogs = iIreRecommLogService.list(ireRecommLogQueryWrapper);

                QueryWrapper<IreUserRecommresult> ireUserRecommresultQueryWrapper = new QueryWrapper<>();
//                用户反馈结果为不喜欢的
                ireUserRecommresultQueryWrapper.like("ID_NUM", iUserFollow.getIdNum()).eq("KNOWLEDGE",iKnowledgeInfo.getSourceId()).orderByDesc("UPDATE_TIME");
                ireUserRecommresultQueryWrapper.and(  wrapper ->wrapper.eq("ISLIKE",CommonConstants.ISLIKE_DISLIKE).or().eq("ISLIKE",CommonConstants.ISLIKE_LIKE));
                List<IreUserRecommresult> ireUserRecommresults = iIreUserRecommresultService.list(ireUserRecommresultQueryWrapper);
                if (ObjectUtils.isNotEmpty(ireRecommLogs)) {
                    log.info(iKnowledgeInfo.getSourceId() + " has posted");
                    continue;
                }

                if (ObjectUtils.isNotEmpty(ireUserRecommresults)) {
                    if (ireUserRecommresults.get(0).getIslike().equalsIgnoreCase(CommonConstants.ISLIKE_DISLIKE)) {
                        log.info(iKnowledgeInfo.getSourceId() + " user " +iUserFollow.getIdNum() + " dislike");
                        continue;
                    }
                }
                log.debug("recommresult: " + recommResult.getInfo());
                RecommContent recommContent = new RecommContent();
//                String url = knowledgeurl + "/giksp/ui!clientsearch.action?kid=" + iKnowledgeInfo.getSourceId() + "&kname=&j_username=" + iUserFollow.getIdNum() + "&flag=client ";
                String url= PostUtils.getKnowledgeURL(knowledgeurl,iKnowledgeInfo.getSourceId(),iUserFollow.getIdNum() );
                recommContent.setUrl(url);
                recommContent.setTitle(iKnowledgeInfo.getTitle());
                if (ObjectUtils.isNotEmpty(iKnowledgeInfo.getAuthor())) {
                    recommContent.setAuthor(iKnowledgeInfo.getAuthor());
                }
                if (ObjectUtils.isNotEmpty(iKnowledgeInfo.getDomain())) {
                    recommContent.setDomain(iKnowledgeInfo.getDomain());
                }
                recommContent.setRelevancy(recommResult.getScore());
                recommContent.setSource("知识管理系统");
                recommContent.setRsource(recommResult.getTags());
                if (ObjectUtils.isNotEmpty(iKnowledgeInfo.getAbstractText())) {
                    recommContent.setBz(iKnowledgeInfo.getAbstractText());
                }
                recommContent.setTime(iKnowledgeInfo.getCreateTime());
//                recommContent.setCallback(serverConfig.getUrl()+"/irecommpost/getCallback?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+iUserFollow.getIdNum()+"&islike=");
                recommContent.setCallback(PostUtils.getCallBackURL("irecommpost",serverConfig.getUrl(),iKnowledgeInfo.getSourceId(),iUserFollow.getIdNum(),""));
                recommContents.add(recommContent);
                ids.add(iKnowledgeInfo.getSourceId());
                if (flag&&recommContents.size() == POSTNUM){
                    break;

                }



            }





        }
    }



}
