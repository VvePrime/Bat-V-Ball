package com.vivek.batvball.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionService {
	
	@Autowired
	@Qualifier("scoreCardJob")
	Job job;
	
	@Autowired
	JobLauncher jobLauncher;
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void execute() {
		JobParameters jobParams = new JobParameters();
		try {
			JobExecution jobExecution = jobLauncher.run(job,jobParams);

			logger.info("ScoreCard job is executed with the end status : {} ",jobExecution.getExitStatus().toString());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
