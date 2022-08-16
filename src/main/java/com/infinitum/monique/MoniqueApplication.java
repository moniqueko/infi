package com.infinitum.monique;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.infinitum.monique.mapper")
public class MoniqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoniqueApplication.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler() {

		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);

		return taskScheduler;
	}

}
