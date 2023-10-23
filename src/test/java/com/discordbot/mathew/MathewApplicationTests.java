package com.discordbot.mathew;

import com.discordbot.mathew.service.ChatGPTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MathewApplicationTests {
	@Autowired
	private ChatGPTService chatGPTService;
	@Test
	void contextLoads() {
		System.out.println(chatGPTService.responseTo("what is 1 + 1"));
	}

}
