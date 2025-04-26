package com.qiugq.xiaozhiai.controller;

import com.qiugq.xiaozhiai.assistant.XiaozhiAgent;
import com.qiugq.xiaozhiai.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "硅谷小智", description = "用户挂号、聊天控制器")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {

	@Autowired
	private XiaozhiAgent xiaozhiAgent;

	@Operation(summary = "对话")
	@PostMapping("/chat")
	public String chat(@RequestBody ChatForm chatForm) {
		return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
	}
}
