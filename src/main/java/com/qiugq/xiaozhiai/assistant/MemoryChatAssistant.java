package com.qiugq.xiaozhiai.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * AI助手，实现大语言模型的高级功能
 * 带聊天记忆
 * 已经是一个初级智能体了
 */
@AiService(
		wiringMode = EXPLICIT,
		chatModel = "qwenChatModel",
		chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

	/**
	 * 聊天
	 * @param msg 用户输入的消息
	 * @return 大语言模型的回答
	 */
	@UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。 {{msg}}")
	// @UserMessage("你是我的好朋友，请用上海话回答问题，并且添加一些表情符号。 {{it}}")
	// {{it}}表示这里唯一的默认参数的占位符，用户提示词是基于内存存储的，没办法在数据库中查看
	String chat(@V("msg") String msg);

}
