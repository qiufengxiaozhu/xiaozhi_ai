package com.qiugq.xiaozhiai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

/**
 * 数学工具
 */
@Component
public class CalculatorTools {

	@Tool(name = "加法运算", value = "返回两数相加之和")
	double sum(
			@ToolMemoryId int memoryId,
			@P(value = "加数1", required = true) double a,
			@P(value = "加数2", required = true) double b) {

		System.out.println("调用加法运算 " + memoryId);
		return a + b;
	}

	@Tool("平方根运算")
	double squareRoot(@ToolMemoryId int memoryId, double x) {
		System.out.println("调用平方根运算 " + memoryId);
		return Math.sqrt(x);
	}
}
