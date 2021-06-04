package com.k3itech.config;

import com.k3itech.job.TagJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.text.html.HTML;

/**
 * @author dell
 * @since 2021-05-16
 */
@Configuration
public class QuartzConfig {
    @Value("${job.quartz.trigger.time}")
    private String triggerTime;

    @Bean
    public JobDetail tagJobDetail(){
        return JobBuilder.newJob(TagJob.class)
                .withIdentity("TagJob")
                .usingJobData("msg","Tag Messages")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger tagJobTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(triggerTime);
        return TriggerBuilder.newTrigger()
                //关联上述的JobDetail
                .forJob(tagJobDetail())
                //给Trigger起个名字
                .withIdentity("quartztagTaskService")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
