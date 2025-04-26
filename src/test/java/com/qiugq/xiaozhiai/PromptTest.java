package com.qiugq.xiaozhiai;

import com.qiugq.xiaozhiai.assistant.MemoryChatAssistant;
import com.qiugq.xiaozhiai.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试提示词
 */
@SpringBootTest
public class PromptTest {

	@Autowired
	private SeparateChatAssistant separateChatAssistant;

	/**
	 * 系统提示词，当更换了提示词，之前的记忆会清空
	 */
	@Test
	public void testSystemMessage() {
		String answer1 = separateChatAssistant.chat(5, "我是谁");
		System.out.println(answer1);
		String answer2 = separateChatAssistant.chat(5, "今天几号");
		System.out.println(answer2);
		String answer3 = separateChatAssistant.chat(5, "我是秋枫");
		System.out.println(answer3);
		String answer4 = separateChatAssistant.chat(5, "现在知道我是谁了不");
		System.out.println(answer4);
	}

	@Autowired
	private MemoryChatAssistant memoryChatAssistant;

	/**
	 * 用户提示词，此记忆仅存在于内存中，会将用户的消息替换进{{it}}占位符中，不会保存进数据库中。
	 * 只用于提示词不长的情况，如果提示词过长，建议使用系统提示词。
	 */
	@Test
	public void testUserMessage() {
		String answer1 = memoryChatAssistant.chat("我是环环");
		System.out.println(answer1);
		String answer2 = memoryChatAssistant.chat("我18了");
		System.out.println(answer2);
		String answer3 = memoryChatAssistant.chat("现在知道我是谁了不");
		System.out.println(answer3);
	}

	/**
	 * 用户提示词，此记忆仅存在于内存中，会将用户的消息替换进{{it}}占位符中，不会保存进数据库中。
	 * 只用于提示词不长的情况，如果提示词过长，建议使用系统提示词。
	 */
	@Test
	public void testUserMessage2() {
		String answer1 = separateChatAssistant.chat2(8, "我是谁");
		System.out.println(answer1);
		String answer2 = separateChatAssistant.chat2(8, "今天几号");
		System.out.println(answer2);
		String answer3 = separateChatAssistant.chat2(8, "我是秋枫");
		System.out.println(answer3);
		String answer4 = separateChatAssistant.chat2(8, "现在知道我是谁了不，今天几号");
		System.out.println(answer4);
	}

	@Test
	public void testUserInfo() {

		// 假设从数据库中获取用户信息
		String username = "秋枫";
		int age = 18;
		String answer = separateChatAssistant.chat3(9, "我是谁，我多大了，今天啥日子？", username, age);
		System.out.println(answer);
	}
}
