package com.k3itech.config;

import com.k3itech.job.CalcJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dell
 * @since 2021-05-16
 */
@Configuration
public class QuartzConfig {
    @Value("${job.quartz.trigger.time}")
    private String triggerTime;
    @Bean
    public JobDetail calcJobDetail(){
        return JobBuilder.newJob(CalcJob.class)
                .withIdentity("CalcJob")
                .usingJobData("msg","calc")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger calcJobTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(triggerTime);
        return TriggerBuilder.newTrigger()
                //关联上述的JobDetail
                .forJob(calcJobDetail())
                //给Trigger起个名字
                .withIdentity("quartzTaskService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
