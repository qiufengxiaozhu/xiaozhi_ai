package com.qiugq.xiaozhiai.store;

import com.qiugq.xiaozhiai.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * mongodb存储聊天记忆实现类
 */
@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 根据memoryId获取聊天记录
	 * @param memoryId The ID of the chat memory.
	 * @return List of messages for the specified chat memory, that represent the current state of the {@link ChatMemory}.
	 */
	@Override
	public List<ChatMessage> getMessages(Object memoryId) {
		Criteria criteria = Criteria.where("memoryId").is(memoryId);
		Query query = new Query(criteria);
		ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
		if (chatMessages == null) {
			return new LinkedList<>();
		}
		return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
	}

	/**
	 * 修改或创建一个聊天记录
	 * @param memoryId The ID of the chat memory.
	 * @param messages List of messages for the specified chat memory, that represent the current state of the {@link ChatMemory}.
	 *                 Can be serialized to JSON using {@link ChatMessageSerializer}.
	 */
	@Override
	public void updateMessages(Object memoryId, List<ChatMessage> messages) {
		Criteria criteria = Criteria.where("memoryId").is(memoryId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("content", ChatMessageSerializer.messagesToJson(messages));
		//根据query条件能查询出文档，则修改文档；否则新增文档
		mongoTemplate.upsert(query, update, ChatMessages.class);
	}

	/**
	 * 删除一个聊天记录
	 * @param memoryId The ID of the chat memory.
	 */
	@Override
	public void deleteMessages(Object memoryId) {
		Criteria criteria = Criteria.where("memoryId").is(memoryId);
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ChatMessages.class);
	}
}
