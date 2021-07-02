package com.k3itech.service.impl;

import com.alibaba.fastjson.JSON;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankcs.hanlp.summary.TextRankKeyword;
import com.k3itech.irecomm.caltks.entity.SystemFile;
import com.k3itech.irecomm.re.entity.*;
import com.k3itech.irecomm.re.service.IIrePersonJobService;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.irecomm.re.service.IPersonPostService;
import com.k3itech.service.TaskCalculateService;
import com.k3itech.utils.Analysis;
import com.k3itech.utils.FileUtils;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.vo.RecommResult;
import com.k3itech.vo.RecommResults;
import com.k3itech.vo.TaskParam;
import com.k3itech.vo.TaskRecommResults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author:dell
 * @since: 2021-05-20
 */
@Slf4j
@Service
public class TaskCalculateServiceImpl implements TaskCalculateService {


    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${calc.redis.timeout}")
    private long timeout;


    /**
     * 用户标签数量
     */
    static final Integer USERTAGSIZE = 3;
    /**
     * 匹配结果数量
     */
    static final Integer RESULTSIZE = 5;


    @Autowired
    private IIreTagWordService iiTagWordService;

    @Autowired
//    private IPersonPostService iPersonPostService;
    private IIrePersonJobService iIrePersonJobService;

    /**
     * 计算任务的知识匹配结果
     * @param taskParam
     * @param iKnowledgeInfos
     */
    @Override
    public TaskRecommResults compareT2K(TaskParam taskParam, List<IreKnowledgeInfo> iKnowledgeInfos) {
        List<String> taskTags = new ArrayList<>();
        List<RecommResult> result = new ArrayList<>();
        String text=taskParam.getOutputData()+taskParam.getTaskName();
//       通过任务名和任务输出文件列表获取标签
        taskTags=getTagkeywords(text);


//        判断任务标签数量
        if (taskTags.size() >USERTAGSIZE) {
//            result = getSimilaryResult(iUserFollow, iKnowledgeInfos);
//        } else {
            result = getTagResult(taskTags, iKnowledgeInfos);
        }

        log.info("tagresultsize: " + result.size());
        if (result.size() < RESULTSIZE) {
//            PersonPost personPost=iPersonPostService.getById(taskParam.getUserPId());
            IrePersonJob personPost=iIrePersonJobService.getById(taskParam.getUserPId());
            String uesrjob = "";
            if (ObjectUtils.isNotEmpty(personPost)){
//                uesrjob=personPost.getVdefl();
                uesrjob=personPost.getProffessional()+personPost.getDirection();
            }
            List<RecommResult> simresults= getSimilaryResult(uesrjob+taskParam.getTaskName()+taskParam.getProjectName(), iKnowledgeInfos);
            if (ObjectUtils.isNotEmpty(simresults)){
                result.addAll(simresults);
            }
        }
        log.info("simresultsize: " + result.size());


        if (result.size() < RESULTSIZE) {

        }

        TaskRecommResults recommResults = new TaskRecommResults();
        recommResults.setTaskParam(taskParam);
        recommResults.setRecommResults(result);

        String key = JSON.toJSONString(taskParam);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(recommResults), timeout, TimeUnit.DAYS);

        return recommResults;


    }

    /**
     * 标签数匹配结果
     * @param tags
     * @param iKnowledgeInfos
     * @return
     */
    public List<RecommResult> getTagResult(List<String> tags, List<IreKnowledgeInfo> iKnowledgeInfos) {
        List<RecommResult> result = new ArrayList<>();


        for (IreKnowledgeInfo knowledgeInfo : iKnowledgeInfos) {
            List<String> knowledgetags = new ArrayList<>();
            ArrayList<String> tagModel = new ArrayList<String>();
            if (ObjectUtils.isNotEmpty(knowledgeInfo.getTagModel())) {
               tagModel= new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagModel().split(",")));
            }
            ArrayList<String> tagDevice = new ArrayList<String>();
            if (ObjectUtils.isNotEmpty(knowledgeInfo.getTagDevice())) {
           tagDevice= new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagDevice().split(",")));

            }
            ArrayList<String> tagPro = new ArrayList<String>();
            if (ObjectUtils.isNotEmpty(knowledgeInfo.getTagKeywords())) {
                tagPro = new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagKeywords().split(",")));
            }
            knowledgetags.addAll(tagDevice);
            knowledgetags.addAll(tagModel);
            knowledgetags.addAll(tagPro);
            //知识标签和人员标签命中的结果获取
            List<String> equaltags = tags
                    .stream()
                    .filter(e -> knowledgetags.contains(e))
                    .collect(Collectors.toList());
            if (equaltags.size()<1){
                continue;
            }
            RecommResult recommResult = new RecommResult();
            recommResult.setType("标签");
            recommResult.setTags(String.join(",",equaltags));
            recommResult.setInfo(knowledgeInfo);
            recommResult.setScore(0.3 * equaltags.size());
            result.add(recommResult);

        }

        Collections.sort(result);


        return result;

    }


    /**
     * 相似度匹配结果
     * @param task
     * @param iKnowledgeInfos
     * @return
     */
    public List<RecommResult> getSimilaryResult(String task, List<IreKnowledgeInfo> iKnowledgeInfos) {
        List<RecommResult> result = new ArrayList<>();
        String sencentence1 = task;

        for (IreKnowledgeInfo knowledgeInfo : iKnowledgeInfos) {
            String sencentence2 = knowledgeInfo.getTitle() + knowledgeInfo.getTagKeywords();
            Analysis analysis = new Analysis();
            Double score = analysis.getCosineSimilarity(sencentence1, sencentence2);
            RecommResult recommResult = new RecommResult();
            recommResult.setInfo(knowledgeInfo);
            recommResult.setScore(score);
            recommResult.setType("相似度");
            if (score > 0.3) {
                result.add(recommResult);
            }


        }
        Collections.sort(result);


        return result;
    }

    public List<RecommResult> getDepartResult(IreUserFollow userFollow) {
        List<RecommResult> result = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();

//        List<IKnowledgeInfo> iKnowledgeInfos = iKnowledgeInfoMapper.


        return result;
    }
    public  List<String> getTagkeywords(String text){
        return textRank(text);
    }



    public List<String> textRank(String text){

        List<String> keywords= TextRankKeyword.getKeywordList(text, 100);
        if (ObjectUtils.isEmpty(keywords)){
            return new ArrayList<>();
        }
        List<String> newKeywords= new ArrayList<>();
        for (String keyword:keywords){
            QueryWrapper<IreTagWord> queryWrapper= new QueryWrapper<>();
            queryWrapper.eq("WORD",keyword);
            List<IreTagWord> tagWords=iiTagWordService.list(queryWrapper);
            if (tagWords.size()>0){
                newKeywords.add(keyword);
            }
        }


        return newKeywords;
    }



}
