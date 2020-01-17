package org.cisco.ciscoutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync // for faster processing
@EnableScheduling // for re-training the models
@PropertySource("classpath:common_util.properties")
public class CUApplication {
	public static void main(String[] args) {
		SpringApplication.run(CUApplication.class, args);
	}
}
