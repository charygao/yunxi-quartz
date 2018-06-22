package com.test.quartzdemo.job;

import com.test.quartzdemo.helloworld.HelloQuartz;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author zhouguanya
 * @Date 2018/6/10
 * @Description
 */
@SpringBootTest
public class RamJobTest {
    private static Logger LOGGER = LoggerFactory.getLogger(RamJobTest.class);
    @Test
    public void test() throws SchedulerException, InterruptedException {
        //1.创建Scheduler的工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //2.从工厂中获取调度器实例
        Scheduler scheduler = schedulerFactory.getScheduler();
        //3.创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloQuartz.class).withDescription("this is a ram job")
                .withIdentity("ramJob", "ramGroup").build();
        //任务运行的时间，SimpleSchedule类型触发器有效   3秒后启动任务
        long time = System.currentTimeMillis() + 3 * 1000;
        Date start = new Date(time);
        //4.创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger trigger = TriggerBuilder.newTrigger().withDescription("this is a trigger")
                .withIdentity("ramTrigger", "ramTriggerGroup")
                //默认当前时间启动
                .startAt(start)
                //两秒执行一次
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();
        //5.注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);
        //6.启动 调度器
        scheduler.start();
        System.out.println("启动时间 ： " + new Date());
        Thread.sleep(10000);
        scheduler.shutdown();
    }
}
