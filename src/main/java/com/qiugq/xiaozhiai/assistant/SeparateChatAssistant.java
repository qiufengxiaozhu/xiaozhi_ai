package com.qiugq.xiaozhiai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 带会话隔离的智能体助手
 */
@AiService(
		wiringMode = EXPLICIT,
		chatModel = "qwenChatModel",
		chatMemoryProvider = "chatMemoryProvider",
		tools = { "calculatorTools" }
)
public interface SeparateChatAssistant {

	/**
	 * 分离聊天记录 + 系统提示词
	 * @param memoryId    聊天id
	 * @param userMessage 用户消息
	 * @return 大语言模型的回答
	 */
	@SystemMessage(fromResource = "my-prompt-template.txt")
	// @SystemMessage("你是我的好朋友，请用四川话回答问题。今天是{{current_date}}")
	// @SystemMessage("你是我的好朋友，请用东北话回答问题。") // 系统消息提示词注解
	String chat(@MemoryId int memoryId, @UserMessage String userMessage);

	/**
	 * 分离聊天记录 + 用户提示词
	 * @param memoryId    聊天id
	 * @param userMessage 用户提示词
	 * @return 大语言模型的回答
	 */
	@UserMessage("你是我的好朋友，今天是{{current_date}}，请用四川话回答问题并附带表情符号。{{message}}")
	String chat2(@MemoryId int memoryId, @V("message") String userMessage);

	/**
	 * 分离聊天记录 + 系统提示词 + @V注解 = 终极版本答案
	 * @param memoryId    聊天id
	 * @param userMessage 用户消息
	 * @return 大语言模型的回答
	 */
	@SystemMessage(fromResource = "my-prompt-template3.txt")
	String chat3(
			@MemoryId int memoryId,
			@UserMessage String userMessage,
			@V("username") String username,
			@V("age") int age
	);
}
