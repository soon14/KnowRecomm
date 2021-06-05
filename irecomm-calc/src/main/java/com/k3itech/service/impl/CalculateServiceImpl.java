package com.k3itech.service.impl;

import com.alibaba.fastjson.JSON;

import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.service.CalculateService;
import com.k3itech.utils.Analysis;
import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.vo.RecommResult;
import com.k3itech.vo.RecommResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author:dell
 * @since: 2021-05-20
 */
@Slf4j
@Service
public class CalculateServiceImpl implements CalculateService {


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

    @Override
    public void compareU2K(IreUserFollow iUserFollow, List<IreKnowledgeInfo> iKnowledgeInfos) {
        List<String> userTags = new ArrayList<>();
        List<RecommResult> result = new ArrayList<>();
        String followmodel = iUserFollow.getFollowModel();
        if (ObjectUtils.isNotEmpty(followmodel)) {
            ArrayList<String> tagModel = new ArrayList<String>(Arrays.asList(iUserFollow.getFollowModel().split(",")));
            userTags.addAll(tagModel);
        }
        String followdevice = iUserFollow.getFollowDevice();
        if (ObjectUtils.isNotEmpty(followdevice)) {
            ArrayList<String> tagDevice = new ArrayList<String>(Arrays.asList(iUserFollow.getFollowDevice().split(",")));
            userTags.addAll(tagDevice);
        }

        if (ObjectUtils.isNotEmpty(iUserFollow.getFollowPro())) {
            ArrayList<String> tagPro = new ArrayList<String>(Arrays.asList(iUserFollow.getFollowPro().split(",")));


            userTags.addAll(tagPro);
        }


        if (userTags.size() < USERTAGSIZE) {
            result = getSimilaryResult(iUserFollow, iKnowledgeInfos);
        } else {
            result = getTagResult(userTags, iKnowledgeInfos);
        }

        log.debug("resultsize: " + result.size());

        if (result.size() < RESULTSIZE) {

//            result = getDepartResult(iUserFollow);
        }

        RecommResults recommResults = new RecommResults();
        recommResults.setIUserFollow(iUserFollow);
        recommResults.setRecommResults(result);

        String key = JSON.toJSONString(iUserFollow);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(recommResults), timeout, TimeUnit.DAYS);


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
            ArrayList<String> tagModel = new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagModel().split(",")));
            ArrayList<String> tagDevice = new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagDevice().split(",")));
            ArrayList<String> tagPro = new ArrayList<String>(Arrays.asList(knowledgeInfo.getTagKeywords().split(",")));
            knowledgetags.addAll(tagDevice);
            knowledgetags.addAll(tagModel);
            knowledgetags.addAll(tagPro);
            List<String> equaltags = tags
                    .stream()
                    .filter(e -> knowledgetags.contains(e))
                    .collect(Collectors.toList());
            RecommResult recommResult = new RecommResult();
            recommResult.setInfo(knowledgeInfo);
            recommResult.setScore(0.3 * equaltags.size());
            result.add(recommResult);

        }

        Collections.sort(result);


        return result;

    }


    /**
     * 相似度匹配结果
     * @param iUserFollow
     * @param iKnowledgeInfos
     * @return
     */
    public List<RecommResult> getSimilaryResult(IreUserFollow iUserFollow, List<IreKnowledgeInfo> iKnowledgeInfos) {
        List<RecommResult> result = new ArrayList<>();
        String sencentence1 = iUserFollow.getPost() + iUserFollow.getFollowDevice() + iUserFollow.getFollowModel() + iUserFollow.getFollowPro();

        for (IreKnowledgeInfo knowledgeInfo : iKnowledgeInfos) {
            String sencentence2 = knowledgeInfo.getTitle() + knowledgeInfo.getTagKeywords();
            Analysis analysis = new Analysis();
            Double score = analysis.getCosineSimilarity(sencentence1, sencentence2);
            RecommResult recommResult = new RecommResult();
            recommResult.setInfo(knowledgeInfo);
            recommResult.setScore(score);
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


}
