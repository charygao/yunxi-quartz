package com.test.quartzdemo.helloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author zhouguanya
 * @Date 2018/6/10
 * @Description
 */
public class SimpleTriggerDemo {
    public static void main(String[] args) throws InterruptedException {

        //通过schedulerFactory获取一个调度器
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            // 通过schedulerFactory获取一个调度器
            scheduler = schedulerfactory.getScheduler();

            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail job = JobBuilder.newJob(HelloQuartz.class).withIdentity("JobName", "JobGroupName").build();

            // 定义调度触发规则

            // SimpleTrigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger", "SimpleTriggerGroup")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(5))
                    //默认当前时间启动
                    .startNow().build();

            // cron表达式 每1秒执行一次 (http://cron.qqe2.com/)
//            Trigger trigger=TriggerBuilder.newTrigger().withIdentity("CronTrigger1", "CronTriggerGroup")
//                    .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?"))
//                    .startNow().build();

            //把作业和触发器注册到任务调度中
            scheduler.scheduleJob(job, trigger);

            // 启动调度
            scheduler.start();

            Thread.sleep(10000);

            // 停止调度
            scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
