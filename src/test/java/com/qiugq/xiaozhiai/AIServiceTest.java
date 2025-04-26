package com.qiugq.xiaozhiai;

import com.qiugq.xiaozhiai.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 引入AIService注解
 */
@SpringBootTest
public class AIServiceTest {

	@Autowired
	private QwenChatModel qwenChatModel;

	@Test
	public void testQwenChat() {

		// 创建AIService
		Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
		// 调用service的接口
		String answer = assistant.chat("你是谁？");
		System.out.println(answer);
	}

	// 通过使用@AIService注解，自行注入AiService
	@Autowired
	private Assistant assistant;

	@Test
	public void testAssistant() {
		String answer = assistant.chat("我是谁？");
		System.out.println(answer);
	}
}
