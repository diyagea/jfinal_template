package com.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Task1 implements Job{

    public void run() {
	System.out.println("定时器test");
    }

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
	System.out.println("定时器test");	
    }
    
}

