package com.qiugq.xiaozhiai;

import com.qiugq.xiaozhiai.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * mongoDB crud测试
 */
@SpringBootTest
public class MongoCrudTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 插入文档
	 */
	@Test
	public void testInsert() {
		// mongoTemplate.insert(new ChatMessages(1L, "聊天记录"));
	}

	@Test
	public void testInsert2() {
		mongoTemplate.insert(new ChatMessages("聊天记录2"));
	}

	/**
	 * 根据id查询文档
	 */
	@Test
	public void testFindById() {
		ChatMessages chatMessages = mongoTemplate.findById("680c8bf432561c0f4fac2b01", ChatMessages.class);
		System.out.println(chatMessages);

		ChatMessages chatMessages2 = mongoTemplate.findById("1", ChatMessages.class);
		System.out.println(chatMessages2);
	}

	/**
	 * 修改文档
	 */
	@Test
	public void testUpdate() {
		Criteria criteria = Criteria.where("_id").is("680c8bf432561c0f4fac2b01");
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("content", "新的聊天记录列表");
		//修改或新增
		mongoTemplate.upsert(query, update, ChatMessages.class);
	}

	/**
	 * 新增或修改文档
	 */
	@Test
	public void testUpdate2() {
		Criteria criteria = Criteria.where("_id").is("100");
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("content", "新的聊天记录列表");
		//修改或新增
		mongoTemplate.upsert(query, update, ChatMessages.class);
	}

	/**
	 * 删除文档
	 */
	@Test
	public void testDelete() {
		Criteria criteria = Criteria.where("_id").is("100");
		Query query = new Query(criteria);
		mongoTemplate.remove(query, ChatMessages.class);
	}
}
