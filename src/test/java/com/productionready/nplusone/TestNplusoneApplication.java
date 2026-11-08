package com.productionready.nplusone;

import org.springframework.boot.SpringApplication;

public class TestNplusoneApplication {

	public static void main(String[] args) {
		SpringApplication.from(NplusoneApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
