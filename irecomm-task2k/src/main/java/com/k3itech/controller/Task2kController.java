package com.k3itech.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.service.TaskCalculateService;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.utils.R;
import com.k3itech.utils.SecretLevel;
import com.k3itech.vo.*;
import io.swagger.annotations.ApiOperation;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
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

    @Value("${knowledge.url}")
    private String knowledgeurl;

    @GetMapping("/getknowledge")
    @ApiOperation(value = "获取任务匹配知识结果")
    @ResponseBody
    public Object getKnowledge(TaskParam param){
        String key = JSON.toJSONString(param);
        String json = redisTemplate.boundValueOps(key).get();
        log.info("json: " + json);
        List<RecommResult> recommResultList = new ArrayList<>();
        List<KnowledgeResult> knowledgeResults = new ArrayList<>();
        if (ObjectUtils.isEmpty(json)) {
            QueryWrapper<IreKnowledgeInfo> queryWrapper= new QueryWrapper();
            queryWrapper.in("SECURITY_LEVEL", SecretLevel.getKnowledgeSecretLevelsByUserLevel(param.getSecretLevel()));
            List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list(queryWrapper);
           TaskRecommResults taskRecommResults= taskCalculateService.compareT2K(param,ireKnowledgeInfos);
           recommResultList=taskRecommResults.getRecommResults();


        } else {
            JSONObject jsonObject = JSON.parseObject(json);
            TaskRecommResults recommResults = jsonObject.toJavaObject(TaskRecommResults.class);
            recommResultList = recommResults.getRecommResults();
        }
            Collections.sort(recommResultList);
            for (RecommResult recommResult : recommResultList) {
                IreKnowledgeInfo iKnowledgeInfo = recommResult.getInfo();
                KnowledgeResult knowledgeResult = new KnowledgeResult();
                knowledgeResult.setAuthor(iKnowledgeInfo.getAuthor());
                knowledgeResult.setFileName(iKnowledgeInfo.getTitle());
                iKnowledgeInfo.setUrl( knowledgeurl + "/giksp/ui!clientsearch.action?kid=" + iKnowledgeInfo.getSourceId() + "&kname=&j_username=" + param.getUserPId() + "&flag=client ");
                knowledgeResult.setFileType(iKnowledgeInfo.getKtype());
                knowledgeResult.setSource("知识管理系统");
                knowledgeResults.add(knowledgeResult);
            }
        Map<String,Object> map = new HashMap<>();
            map.put("data",knowledgeResults);

        return TaskResult.ok(map);


    }
}
