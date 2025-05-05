package com.qiugq.xiaozhiai.config;

import com.qiugq.xiaozhiai.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 带会话隔离 + 聊天记忆的智能体配置
 */
@Configuration
public class SeparateChatAssistantConfig {

	@Autowired
	private MongoChatMemoryStore mongoChatMemoryStore;

	@Bean
	ChatMemoryProvider chatMemoryProvider() {
		return memoryId -> MessageWindowChatMemory.builder()
				.id(memoryId)
				.maxMessages(10)
				.chatMemoryStore(mongoChatMemoryStore) // 配置自定义持久化对象
				// .chatMemoryStore(new InMemoryChatMemoryStore())
				// .chatMemoryStore(new SingleSlotChatMemoryStore())
				.build();
	}
}
