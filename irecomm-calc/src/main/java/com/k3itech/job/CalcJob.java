package com.k3itech.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.service.CalculateService;
import com.k3itech.service.RedisService;
import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.ObjectUtils;
import com.k3itech.utils.SecretLevel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-26
 */
@Slf4j
public class CalcJob extends QuartzJobBean {

    @Autowired
    CalculateService calculateService;
    @Autowired
    IIreUserFollowService iIreUserFollowService;
    @Autowired
    IIreKnowledgeInfoService iIreKnowledgeInfoService;

    @Resource
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String flag = redisService.get(CommonConstants.DEAL_FLAG);
        if (ObjectUtils.isNotEmpty(flag)&&flag.equalsIgnoreCase(CommonConstants.TAG_OVER_FLG)) {
             redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.CALC_ING_FLG);
            String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");

            List<IreUserFollow> ireUserFollowList = iIreUserFollowService.list();

            for (IreUserFollow ireUserFollow : ireUserFollowList) {
                QueryWrapper<IreKnowledgeInfo> queryWrapper= new QueryWrapper();
                queryWrapper.in("SECURITY_LEVEL", SecretLevel.getKnowledgeSecretLevelsByUserLevel(ireUserFollow.getSecretLevel()));
                List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list(queryWrapper);
                calculateService.compareU2K(ireUserFollow, ireKnowledgeInfos);
            }

            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.CALC_OVER_FLG);
        }else{
            log.info("current deal flag is "+flag);

    }

    }
}
