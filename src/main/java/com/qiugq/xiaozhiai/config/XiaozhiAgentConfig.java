package com.qiugq.xiaozhiai.config;

import com.qiugq.xiaozhiai.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 小智智能体配置类
 */
@Configuration
public class XiaozhiAgentConfig {

	@Autowired
	private MongoChatMemoryStore mongoChatMemoryStore;

	/**
	 * 聊天记忆提供bean
	 */
	@Bean(name = "chatMemoryProviderXiaozhi")
	ChatMemoryProvider chatMemoryProvider() {
		return memoryId -> MessageWindowChatMemory.builder()
				.id(memoryId)
				.maxMessages(20)
				.chatMemoryStore(mongoChatMemoryStore)
				.build();
	}
}
