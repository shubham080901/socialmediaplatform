package com.oops.major.socialMediaPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan("com.oops.major.socialMediaPlatform.model")
@SpringBootApplication
public class SocialMediaPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaPlatformApplication.class, args);
	}

}
