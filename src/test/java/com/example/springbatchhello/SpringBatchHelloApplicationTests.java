package com.example.springbatchhello;

import com.example.springbatchhello.batch.BatchConfig;
import com.example.springbatchhello.batch.HelloJobConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SpringBatchHelloApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testHelloWorldJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}

	@Configuration
	@Import({BatchConfig.class, HelloJobConfig.class})
	static class BatchTestConfig{
		@Autowired
		private Job helloWorldJob;

		@Bean
		JobLauncherTestUtils jobLauncherTestUtils(){
			JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
			jobLauncherTestUtils.setJob(helloWorldJob);

			return jobLauncherTestUtils;
		}
	}

}
