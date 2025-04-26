package com.qiugq.xiaozhiai.bean;

import lombok.Data;

/**
 * 聊天窗口
 */
@Data
public class ChatForm {
	
	/** 对话id */
	private Long memoryId;
	
	/** 用户问题 */
	private String message;
}
