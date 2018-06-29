package com.test.quartzdemo.spring.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author zhouguanya
 * @Date 2018/6/25
 * @Description
 */
public class OneJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("*********************");

    }

}
