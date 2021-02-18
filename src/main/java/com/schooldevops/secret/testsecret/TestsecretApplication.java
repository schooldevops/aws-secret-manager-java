package com.schooldevops.secret.testsecret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.introspector.Property;

@SpringBootApplication
public class TestsecretApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TestsecretApplication.class);
		app.addListeners(new PropertyListener());
		app.run(args);
	}

}
