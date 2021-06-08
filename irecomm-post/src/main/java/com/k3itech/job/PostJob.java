package com.k3itech.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.irecomm.yunque.entity.AdminUser;
import com.k3itech.service.PostService;
import com.k3itech.service.RedisService;
import com.k3itech.utils.CommonConstants;
import com.k3itech.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
  * @author dell
 * @since 2021-05-16
 */
@Slf4j
public class PostJob extends QuartzJobBean {
    @Autowired
    private RedisService redisService;
    @Autowired
    private PostService postService;
    @Autowired
    private IIreUserFollowService iIreUserFollowService;

    @Value("${calc.orgcode}")
    private String orgcode;

    static final String SPLITCHR= ",";

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String flag = redisService.get(CommonConstants.DEAL_FLAG);
        boolean postresult=true;
        if (ObjectUtils.isNotEmpty(flag)&&flag.equalsIgnoreCase(CommonConstants.CALC_OVER_FLG)){

            log.info("start post ");
            QueryWrapper<IreUserFollow> orgqueryWrapper= new QueryWrapper();
            for (String org:orgcode.split(SPLITCHR)) {
                orgqueryWrapper.like("ORG_CODE",org).or(true);
            }
            List<IreUserFollow> ireUserFollows = iIreUserFollowService.list(orgqueryWrapper);
            for (IreUserFollow ireUserFollow:ireUserFollows){
              postresult = postService.postKnowledge(ireUserFollow);
              if (!postresult){

              }
            }



            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.POST_OVER_FLG);


        }else{

                log.info("current deal flag is "+flag);



        }
    }
}
