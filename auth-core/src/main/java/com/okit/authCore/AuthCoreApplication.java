package com.okit.authCore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthCoreApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(AuthCoreApplication.class, args);
	}
}
