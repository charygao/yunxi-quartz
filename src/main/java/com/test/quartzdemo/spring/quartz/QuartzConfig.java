package com.test.quartzdemo.spring.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhouguanya
 * @Date 2018/6/25
 * @Description
 */
@Configuration
public class QuartzConfig {

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail oneJob = JobBuilder.newJob(OneJob.class).withIdentity("job1", "group1").build();
        JobDetail secondJob = JobBuilder.newJob(SecondJob.class).withIdentity("job2", "group2").build();
        TriggerBuilder<Trigger> newTrigger = trigger();
        newTrigger.withIdentity("trigger1", "group1");
        newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"));
        Trigger oneTrigger = newTrigger.build();
        newTrigger.withIdentity("trigger2", "group2");
        newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"));
        Trigger secondTrigger = newTrigger.build();
        scheduler.scheduleJob(oneJob, oneTrigger);
        scheduler.scheduleJob(secondJob, secondTrigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public TriggerBuilder<Trigger> trigger() {
        return TriggerBuilder.newTrigger();
    }

}
