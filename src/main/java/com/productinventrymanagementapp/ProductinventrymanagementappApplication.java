package com.productinventrymanagementapp;

import com.productinventrymanagementapp.util.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductinventrymanagementappApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ProductinventrymanagementappApplication.class);
		application.addInitializers(new DotenvLoader());
		application.run(args);
//		SpringApplication.run(ProductinventrymanagementappApplication.class, args);
	}

}
