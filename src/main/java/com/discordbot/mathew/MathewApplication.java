package com.discordbot.mathew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:bot.properties")
public class MathewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MathewApplication.class, args);
	}

}
