package com.k3itech.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.IRecommApplicationPost;
import com.k3itech.api.fein.YunqueClient;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreRecommLog;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreRecommLogService;
import com.k3itech.service.PostService;
import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.vo.RecommContent;
import com.k3itech.vo.RecommResult;
import com.k3itech.vo.RecommResults;
import com.k3itech.vo.YunqueContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author dell
 * @since 2021-05-16
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {
    final static Integer POSTNUM = 5;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private YunqueClient yunqueClient;
    @Autowired
    private IIreRecommLogService iIreRecommLogService;
    @Autowired
    private ServerConfig serverConfig;

    @Value("${yunque.url}")
    private String knowledgeurl;

    @Override
    public boolean postKnowledge(IreUserFollow iUserFollow) {
        String key = JSON.toJSONString(iUserFollow);
        String json = redisTemplate.boundValueOps(key).get();
        log.info("json: " + json);
        List<String> ids = new ArrayList<>();
        if (ObjectUtils.isEmpty(json)) {

        } else {
            JSONObject jsonObject = JSON.parseObject(json);
            RecommResults recommResults = jsonObject.toJavaObject(RecommResults.class);
            List<RecommResult> recommResultList = recommResults.getRecommResults();
            Collections.sort(recommResultList);
            List<RecommContent> recommContents = new ArrayList<>();
            for (RecommResult recommResult : recommResultList) {
                IreKnowledgeInfo iKnowledgeInfo = recommResult.getInfo();
                QueryWrapper<IreRecommLog> ireRecommLogQueryWrapper = new QueryWrapper<>();
                ireRecommLogQueryWrapper.eq("ID_NUM", iUserFollow.getIdNum());
                ireRecommLogQueryWrapper.and(wrapper -> wrapper.like("KNOWLEDGE", iKnowledgeInfo.getSourceId()).or().like("KNOWLEDGE", iKnowledgeInfo.getSourceId() + ",%").or().like("KNOWLEDGE", "%," + iKnowledgeInfo.getSourceId()));
                List<IreRecommLog> ireRecommLogs = iIreRecommLogService.list(ireRecommLogQueryWrapper);
                if (ObjectUtils.isNotEmpty(ireRecommLogs)) {
                    log.info(iKnowledgeInfo.getSourceId() + " has posted");
                    continue;
                }
                log.debug("recommresult: " + recommResult.getInfo());
                RecommContent recommContent = new RecommContent();
                String url = knowledgeurl + "/giksp/ui!clientsearch.action?kid=" + iKnowledgeInfo.getSourceId() + "&kname=&j_username=" + iUserFollow.getIdNum() + "&flag=client ";
                recommContent.setUrl(url);
                recommContent.setTitle(iKnowledgeInfo.getTitle());
                recommContent.setAuthor(iKnowledgeInfo.getAuthor());
                recommContent.setDomain(iKnowledgeInfo.getDomain());
                recommContent.setRelevancy(recommResult.getScore());
                recommContent.setSource("0");
                recommContent.setTime(iKnowledgeInfo.getCreateTime());
                recommContent.setRsource("");
                recommContent.setCallback(serverConfig.getUrl()+"/irecommpost/getcallback/?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+iUserFollow.getIdNum());
                recommContents.add(recommContent);
                ids.add(iKnowledgeInfo.getSourceId());

                if (recommContents.size() == POSTNUM) {
                    break;
                }


            }
            Object object = null;
            if (ObjectUtils.isNotEmpty(recommContents)) {
//                云雀协议封装

                YunqueContent yunqueContent = new YunqueContent();
                yunqueContent.setReceiverId(iUserFollow.getIdNum());
                yunqueContent.setMsgContent(recommContents);
                yunqueContent.setReceiverName(iUserFollow.getUserName());
                yunqueContent.setSenderOrgName(iUserFollow.getOrgName());

                log.info("post recommComments；" + recommContents.toString());

                object = yunqueClient.postMessage(yunqueContent);
            }
//             调用成功，则记录推荐流水，下次不再推荐
            if (ObjectUtils.isNotEmpty(object) && object.equals("test")) {
                IreRecommLog ireRecommLog = new IreRecommLog();
                ireRecommLog.setIdNum(iUserFollow.getIdNum());
                ireRecommLog.setKnowledge(StringUtils.join(ids, ","));
                Date day = new Date();
                Timestamp localDateTime = new Timestamp(day.getTime());

                ireRecommLog.setPostTime(localDateTime);

                iIreRecommLogService.save(ireRecommLog);
            }else if (ObjectUtils.isNotEmpty(object)){
                return false;
            }




        }
        return true;


    }




    @Override
    public  List<RecommContent> getKnowledge(IreUserFollow iUserFollow) {
        String key = JSON.toJSONString(iUserFollow);
        List<RecommContent> recommContents = new ArrayList<>();

        String json = redisTemplate.boundValueOps(key).get();
        log.info("json: " + json);
        List<String> ids = new ArrayList<>();
        if (ObjectUtils.isEmpty(json)) {

        } else {
            JSONObject jsonObject = JSON.parseObject(json);
            RecommResults recommResults = jsonObject.toJavaObject(RecommResults.class);
            List<RecommResult> recommResultList = recommResults.getRecommResults();
            Collections.sort(recommResultList);
            for (RecommResult recommResult : recommResultList) {
                IreKnowledgeInfo iKnowledgeInfo = recommResult.getInfo();
                QueryWrapper<IreRecommLog> ireRecommLogQueryWrapper = new QueryWrapper<>();
                ireRecommLogQueryWrapper.eq("ID_NUM", iUserFollow.getIdNum());
                ireRecommLogQueryWrapper.and(wrapper -> wrapper.like("KNOWLEDGE", iKnowledgeInfo.getSourceId()).or().like("KNOWLEDGE", iKnowledgeInfo.getSourceId() + ",%").or().like("KNOWLEDGE", "%," + iKnowledgeInfo.getSourceId()));
                List<IreRecommLog> ireRecommLogs = iIreRecommLogService.list(ireRecommLogQueryWrapper);
                if (ObjectUtils.isNotEmpty(ireRecommLogs)) {
                    log.info(iKnowledgeInfo.getSourceId() + " has posted");
                    continue;
                }
                log.debug("recommresult: " + recommResult.getInfo());
                RecommContent recommContent = new RecommContent();
                String url = knowledgeurl + "/giksp/ui!clientsearch.action?kid=" + iKnowledgeInfo.getSourceId() + "&kname=&j_username=" + iUserFollow.getIdNum() + "&flag=client ";
                recommContent.setUrl(url);
                recommContent.setAuthor(iKnowledgeInfo.getAuthor());
                recommContent.setDomain(iKnowledgeInfo.getDomain());
                recommContent.setRelevancy(recommResult.getScore());
                recommContent.setSource(iKnowledgeInfo.getKnowledgeSource());
                recommContent.setTime(iKnowledgeInfo.getCreateTime());
                recommContent.setRsource("");
                recommContent.setCallback(serverConfig.getUrl()+"/irecommpost/getcallback/?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+iUserFollow.getIdNum());
                recommContents.add(recommContent);



            }





        }
        return recommContents;


    }



}
