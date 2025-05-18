package com.qiugq.xiaozhiai;

import com.qiugq.xiaozhiai.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Function Calling 函数调用 也叫 Tools 工具
 */
@SpringBootTest
public class ToolsTest {

	@Autowired
	private SeparateChatAssistant separateChatAssistant;

	/**
	 * 简单计算
	 */
	@Test
	public void testCalculatorTools() {

		String answer = separateChatAssistant.chat(1, "1+2等于几，475695037565的平方根是多 少？");
		//答案：3，689706.4865
		System.out.println(answer);
	}
}
