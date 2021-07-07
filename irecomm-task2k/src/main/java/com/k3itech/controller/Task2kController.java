package com.k3itech.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.k3itech.irecomm.caltks.entity.SystemFile;
import com.k3itech.irecomm.caltks.service.ISystemFileService;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreRecommLog;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.entity.IreUserRecommresult;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreRecommLogService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.re.service.IIreUserRecommresultService;
import com.k3itech.service.TaskCalculateService;
import com.k3itech.service.impl.ServerConfig;
import com.k3itech.utils.*;
import com.k3itech.vo.*;
import io.swagger.annotations.ApiOperation;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author:dell
 * @since: 2021-06-05
 */

@RestController
@RequestMapping("/irecommtask2k")
@Slf4j
@EnableSwagger2
public class Task2kController {

    @Autowired
    TaskCalculateService taskCalculateService;

    @Autowired
    IIreKnowledgeInfoService iIreKnowledgeInfoService;
    @Autowired
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    ISystemFileService iSystemFileService;

    @Autowired
    IIreRecommLogService iIreRecommLogService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    IIreUserRecommresultService iIreUserRecommresultService;

    @Value("${knowledge.url}")
    private String knowledgeurl;

    @GetMapping("/getknowledge")
    @ApiOperation(value = "获取任务匹配知识结果")
    @ResponseBody
    public Object getKnowledge(TaskParam param) {
        String key = JSON.toJSONString(param);
        String json = redisTemplate.boundValueOps(key).get();
        log.info("json: " + json);
        List<RecommResult> recommResultList = new ArrayList<>();
        List<KnowledgeResult> knowledgeResults = new ArrayList<>();
        if (ObjectUtils.isEmpty(json)) {
            QueryWrapper<IreKnowledgeInfo> queryWrapper = new QueryWrapper();
            queryWrapper.in("SECURITY_LEVEL", SecretLevel.getKnowledgeSecretLevelsByUserLevel(param.getSecretLevel()));
            List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list(queryWrapper);
            TaskRecommResults taskRecommResults = taskCalculateService.compareT2K(param, ireKnowledgeInfos);
            recommResultList = taskRecommResults.getRecommResults();


        } else {
            JSONObject jsonObject = JSON.parseObject(json);
            TaskRecommResults recommResults = jsonObject.toJavaObject(TaskRecommResults.class);
            recommResultList = recommResults.getRecommResults();
        }
        List<String> ids = new ArrayList<>();
        Collections.sort(recommResultList);
        for (RecommResult recommResult : recommResultList) {
            IreKnowledgeInfo iKnowledgeInfo = recommResult.getInfo();
            KnowledgeResult knowledgeResult = new KnowledgeResult();
            knowledgeResult.setAuthor(iKnowledgeInfo.getAuthor());
            knowledgeResult.setFileName(iKnowledgeInfo.getTitle());
//                iKnowledgeInfo.setUrl( knowledgeurl + "/giksp/ui!clientsearch.action?kid=" + iKnowledgeInfo.getSourceId() + "&kname=&j_username=" + param.getUserPId() + "&flag=client ");
            String url = PostUtils.getKnowledgeURL(knowledgeurl, iKnowledgeInfo.getSourceId(), param.getUserPId());
            iKnowledgeInfo.setUrl(url);
            QueryWrapper<IreUserRecommresult> ireUserRecommresultQueryWrapper = new QueryWrapper<>();
            //用户反馈结果为不喜欢的
            ireUserRecommresultQueryWrapper.like("ID_NUM", param.getUserPId()).eq("KNOWLEDGE", iKnowledgeInfo.getSourceId()).eq("ISLIKE", CommonConstants.ISLIKE_DISLIKE);
            List<IreUserRecommresult> ireUserRecommresults = iIreUserRecommresultService.list(ireUserRecommresultQueryWrapper);
            if (ObjectUtils.isNotEmpty(ireUserRecommresults)) {
                log.info(iKnowledgeInfo.getSourceId() + " user " + param.getUserPId() + " dislike");
                continue;
            }


            ids.add(iKnowledgeInfo.getSourceId());
            knowledgeResult.setPath(iKnowledgeInfo.getUrl());
            knowledgeResult.setSource("知识管理系统");
//                knowledgeResult.setCallback(serverConfig.getUrl()+"/irecommtask2k/getCallback?md5id="+iKnowledgeInfo.getSourceId()+"&pid="+param.getTaskName()+"-"+param.getUserPId()+"&islike=");
            knowledgeResult.setCallback(PostUtils.getCallBackURL("irecommtask2k", serverConfig.getUrl(), iKnowledgeInfo.getSourceId(), param.getUserPId(), param.getTaskName()));
            knowledgeResults.add(knowledgeResult);
            if (knowledgeResults.size() == 3) {
                break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", knowledgeResults);
//记录推荐日志，根据idnum为用户身份证号+任务名来区分
        IreRecommLog ireRecommLog = new IreRecommLog();
        ireRecommLog.setIdNum(param.getUserPId() + "-" + param.getTaskName());
        ireRecommLog.setKnowledge(StringUtils.join(ids, ","));
        Date day = new Date();
        Timestamp localDateTime = new Timestamp(day.getTime());

        ireRecommLog.setPostTime(localDateTime);

        iIreRecommLogService.save(ireRecommLog);

        return TaskResult.ok(map);


    }

    @GetMapping("/getCallback")
    @ApiOperation(value = "获取用户喜好返回信息")
    @ResponseBody
    public Object getCallback(CallBackParam callBackParam) {

        IreUserRecommresult ireUserRecommresult = new IreUserRecommresult();
        ireUserRecommresult.setIdNum(callBackParam.getPid());
        ireUserRecommresult.setKnowledge(callBackParam.getMd5id());
        ireUserRecommresult.setIslike(callBackParam.getIslike() + "");
        Date day = new Date();
        Timestamp localDateTime = new Timestamp(day.getTime());
        ireUserRecommresult.setUpdateTime(localDateTime);
        boolean flag = iIreUserRecommresultService.save(ireUserRecommresult);
        if (!flag) {
            return R.failed();
        }
        return R.ok();


    }
}
