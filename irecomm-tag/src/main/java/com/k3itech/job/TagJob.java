package com.k3itech.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.k3itech.irecomm.re.entity.EtlTemp;
import com.k3itech.irecomm.re.entity.IreTagWord;
import com.k3itech.irecomm.re.service.IEtlTempService;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.service.RedisService;
import com.k3itech.service.TagService;
import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.Dicts;
import com.k3itech.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private IEtlTempService etlTempService;

    @Value("${userdict.path:}")
    private String userDict;

    final static int rangetimeend=2;
    final static int rangetimestart=1;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (ObjectUtils.isNotEmpty(userDict)) {
            Dicts.init(userDict, StandardCharsets.UTF_8);
        }
        String flag = redisService.get(CommonConstants.DEAL_FLAG);
        QueryWrapper<EtlTemp> etlTempQueryWrapper = new QueryWrapper<>();
        etlTempQueryWrapper.orderByDesc("EXTRACT_TIME");;
        List<EtlTemp> etlTemps = etlTempService.list(etlTempQueryWrapper);

        int hours=0;
//        计算当前时间与抽取结束时间差
        if (ObjectUtils.isNotEmpty(etlTemps)) {
            Timestamp etltime = etlTemps.get(0).getExtractTime();
            if (ObjectUtils.isNotEmpty(etltime)) {


                Date day = new Date();
                Timestamp currenttime = new Timestamp(day.getTime());
                long t1 = currenttime.getTime();
                long t2 = etltime.getTime();
                hours = (int) ((t1 - t2) / (1000 * 60 * 60));
                int minutes = (int) (((t1 - t2) / 1000 - hours * (60 * 60)) / 60);
                int second = (int) ((t1 - t2) / 1000 - hours * (60 * 60) - minutes * 60);
                log.info("current hours:" + hours);
            }
        }
//        若没有获取到处理标记，则默认为可以处理打标签
        if (ObjectUtils.isEmpty(flag)){
            flag=CommonConstants.POST_OVER_FLG;
        }

//            若当前时间在抽取时间结束1-2小时内，并且处理标记为POST_OVER_FLAG则进行打标签工作
        if (hours<rangetimeend && hours>rangetimestart && flag.equalsIgnoreCase(CommonConstants.POST_OVER_FLG)) {

            log.info("start tag  ");
            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.TAG_ING_FLG);
            List<IreTagWord> ireTagWords = ireTagWordService.list();
            for (IreTagWord ireTagWord : ireTagWords) {
                CustomDictionary.add(ireTagWord.getWord());
            }
            log.info("start tag   knowledge");


            tagService.tagKnowledge();
            log.info("start tag   users");
            tagService.tagUsers();


            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.TAG_OVER_FLG);
            log.info(" tag   over");
        }



    }
}
