package com.discordbot.mathew;

import com.discordbot.mathew.entity.ChatGPT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MathewApplicationTests {
	@Autowired
	private ChatGPT chatGPT;
	@Test
	void contextLoads() {
		System.out.println(chatGPT.responseTo("what is 1 + 1"));
	}

}
