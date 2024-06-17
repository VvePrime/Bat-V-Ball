package com.vivek.batvball.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.vivek.batvball.dto.ScoreCardInputDTO;
import com.vivek.batvball.entities.Player;

@Configuration
@EnableBatchProcessing
public class BatVBallJobConfig {
	
//	@Autowired
//	private JobBuilder jobBuilder;	
//	@Autowired
//	private StepBuilder stepBuilder;
	@Autowired
	ScoreCardItemProcessor scoreCardItemProcessor;
	@Autowired
	ScoreCardItemWriter scoreCardItemWriter;
	
	@Bean(name="scoreCardJob")
	Job job(@Autowired @Qualifier("scoreCardStep") Step step, JobRepository jobRepository) {
		return new JobBuilder("scoreCardSaveJob", jobRepository).incrementer(new  RunIdIncrementer())
				.flow(step).end().build();				
	}
	
	@Bean(name="scoreCardStep")
	Step step(@Autowired @Qualifier("scoreCardItemReader") FlatFileItemReader<ScoreCardInputDTO> itemReader,
			JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("scoreCardSaveStep", jobRepository).<ScoreCardInputDTO,Player>chunk(5,transactionManager)
				.reader(itemReader).processor(scoreCardItemProcessor).writer(scoreCardItemWriter)
				.allowStartIfComplete(true)
				.build();
	}
	
	@Bean(name="scoreCardItemReader")
	FlatFileItemReader<ScoreCardInputDTO> itemReader(){
		return new FlatFileItemReaderBuilder<ScoreCardInputDTO>()
				.resource(new ClassPathResource("input-file.txt")).name("ScoreCardFileReader").delimited()
				.delimiter(",").names(new String[] {"dateString","playerId","scoreCard"})
				.linesToSkip(1).targetType(ScoreCardInputDTO.class).build();
	}
	
//	@Bean
//	ScoreCardItemProcessor processor() {
//		return new ScoreCardItemProcessor();
//	}
//	
//	@Bean
//	ScoreCardItemWriter writer() {
//		return new ScoreCardItemWriter();
//	}

}
