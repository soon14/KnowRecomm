package com.k3itech.job;

import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.k3itech.irecomm.re.entity.IreTagWord;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.service.RedisService;
import com.k3itech.service.TagService;
import com.k3itech.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
  * @author dell
 * @since 2021-05-16
 */
@Slf4j
public class TagJob extends QuartzJobBean {
    @Autowired
    private RedisService redisService;

    @Autowired
    private TagService tagService;


    @Autowired
    private IIreTagWordService ireTagWordService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start tag  ");
        redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.TAG_ING_FLG);
        List<IreTagWord> ireTagWords = ireTagWordService.list();
        for (IreTagWord ireTagWord:ireTagWords) {
            CustomDictionary.add(ireTagWord.getWord());
        }
        log.info("start tag   knowledge");


        tagService.tagKnowledge();
        log.info("start tag   users");
        tagService.tagUsers();


        redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.TAG_OVER_FLG);
        log.info("start tag   over");



    }
}
