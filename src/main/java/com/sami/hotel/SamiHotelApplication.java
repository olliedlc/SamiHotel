package com.sami.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EntityScan("com.sami.hotel.entity")
@EnableJpaRepositories("com.sami.hotel.repository")
@EnableHystrix
@EnableSwagger2
public class SamiHotelApplication {
	public static void main(String[] args) {
		SpringApplication.run(SamiHotelApplication.class, args);
	}
}
