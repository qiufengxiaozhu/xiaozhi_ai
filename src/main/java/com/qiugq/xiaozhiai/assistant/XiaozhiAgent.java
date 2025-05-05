package com.qiugq.xiaozhiai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 小智智能体服务
 */
@AiService(
		wiringMode = EXPLICIT,
		// chatModel = "qwenChatModel", 替换为下面的流式输出
		streamingChatModel = "qwenStreamingChatModel",
		chatMemoryProvider = "chatMemoryProviderXiaozhi",
		tools = { "calculatorTools", "appointmentTools"},
		contentRetriever = "contentRetrieverXiaozhiPincone"
)
public interface XiaozhiAgent {

	/**
	 * 与大模型对话
	 * 加载系统提示词
	 * @param memoryId 会话记忆id
	 * @param userMessage 用户聊天消息内容
	 * @return 大语言模型的回答
	 */
	@SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
	Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);
	// String chat(@MemoryId Long memoryId, @UserMessage String userMessage); 使用上面的流式输出，改返回值
}
