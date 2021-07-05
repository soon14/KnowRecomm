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
import com.k3itech.utils.SecretLevel;
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
                recommContent.setRsource(recommResult.getTags());
                recommContent.setBz(recommResult.getType());
                recommContent.setTime(iKnowledgeInfo.getCreateTime());
                recommContent.setCallback(serverConfig.getUrl()+"/irecommpost/getcallback?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+iUserFollow.getIdNum()+"&islike=");
                recommContents.add(recommContent);
                ids.add(iKnowledgeInfo.getSourceId());

                if (recommContents.size() == POSTNUM) {
                    break;
                }


            }
            Object object = null;
            Object message = null;
            Object status = null;
            if (ObjectUtils.isNotEmpty(recommContents)) {
//                云雀协议封装

                YunqueContent yunqueContent = new YunqueContent();
                yunqueContent.setReceiverId(iUserFollow.getIdNum());
                yunqueContent.setMsgContent(recommContents);
                yunqueContent.setReceiverName(iUserFollow.getUserName());
                yunqueContent.setSenderOrgName(iUserFollow.getOrgName());
                yunqueContent.setLevel(SecretLevel.getSecretLevelsByUserLevel(iUserFollow.getSecretLevel()));

                log.info("post recommComments；" + recommContents.toString());

                OpenApiService service = new OpenApiService();
                NoticeRequest request= new NoticeRequest();
                request.setMsgContent(yunqueContent.getMsgContent());
                request.setReceiverId(yunqueContent.getReceiverId());
                request.setReceiverName(yunqueContent.getReceiverName());
                request.setNoticeLevel(yunqueContent.getLevel());
                request.setSenderOrgName(yunqueContent.getSenderOrgName());
                request.setBz(yunqueContent.getBz());
                request.setSenderType(yunqueContent.getSenderType());
                OpenApiRequestParamVo paramVo = new OpenApiRequestParamVo();
                paramVo.setApiUrl("/openApi/service");
                paramVo.setServiceId("AvRqvvAY");


                paramVo.setRequestBody(request);
                paramVo.setToken("oeadmOczKMmUw2jnDoimdSZEWIAHqTxDwDkYiBy9uPscrLHx");
                ApiResponse<Map<String, Object>> map = service.doPostMethod(paramVo);
                System.out.println(map);
                object=map.getStatus();
                if (!object.equals(200)){
                    return false;
                }
                Map<String, Object> res= (Map<String, Object>) map.getResult().get("res");
                System.out.println("res "+res);
                if (ObjectUtils.isNotEmpty(res)) {
                    message = res.get("message");
                    System.out.println(message);
                    status = res.get("status");
                    System.out.println(status);
                }



//                object = yunqueClient.postMessage(yunqueContent);
            }
//             调用成功，则记录推荐流水，下次不再推荐
            if (ObjectUtils.isNotEmpty(object) &&ObjectUtils.isNotEmpty(status)&&ObjectUtils.isNotEmpty(message)&&status.equals(200)&&message.equals("操作成功")) {
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
                recommContent.setTitle(iKnowledgeInfo.getTitle());
                recommContent.setAuthor(iKnowledgeInfo.getAuthor());
                recommContent.setDomain(iKnowledgeInfo.getDomain());
                recommContent.setRelevancy(recommResult.getScore());
                recommContent.setSource("0");
                recommContent.setRsource(recommResult.getTags());
                recommContent.setBz(recommResult.getType());
                recommContent.setTime(iKnowledgeInfo.getCreateTime());
                recommContent.setCallback(serverConfig.getUrl()+"/irecommpost/getcallback/?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+iUserFollow.getIdNum()+"&islike=");

                recommContents.add(recommContent);



            }





        }
        return recommContents;


    }



}
