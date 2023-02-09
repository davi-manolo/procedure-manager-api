package com.procedure.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class ProcedureManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcedureManagerApplication.class, args);
	}

}
