package com.qiugq.xiaozhiai.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * AI助手，实现大语言模型的高级功能
 */
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {

	/**
	 * 聊天
	 * @param userMessage 用户输入的消息
	 * @return 大语言模型的回答
	 */
	String chat(String userMessage);

}
