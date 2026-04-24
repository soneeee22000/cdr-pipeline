package dev.pseonkyaw.cdrpipeline;

import org.springframework.boot.SpringApplication;

public class TestCdrPipelineApplication {

	public static void main(String[] args) {
		SpringApplication.from(CdrPipelineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
