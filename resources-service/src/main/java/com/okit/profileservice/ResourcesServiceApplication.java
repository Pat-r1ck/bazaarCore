package com.okit.profileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class ResourcesServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ResourcesServiceApplication.class, args);
	}
}
