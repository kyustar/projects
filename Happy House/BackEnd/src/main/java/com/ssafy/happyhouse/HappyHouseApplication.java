package com.ssafy.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		HappyHouseApplication.class
})
public class HappyHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHouseApplication.class, args);
	}

}
