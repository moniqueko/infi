package com.infinitum.monique;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan(basePackages = "com.infinitum.monique.mapper")
public class MoniqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoniqueApplication.class, args);
	}

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
