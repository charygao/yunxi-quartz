package com.test.quartzdemo.helloworld;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author zhouguanya
 * @Date 2018/6/10
 * @Description
 */
public class HelloQuartz  implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloQuartz.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        LOGGER.info("Hello World");
    }

}
