package de.othr.sw.cashbackplatform;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateBuilderConfiguration {

	@Bean
	public RestTemplate createRestTemplateBuilder(RestTemplateBuilder builder) {
		return builder.basicAuthentication("max.muster1@email.de", "1234").build();
	}
}
