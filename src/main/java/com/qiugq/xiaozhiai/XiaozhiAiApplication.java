package com.qiugq.xiaozhiai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qiugq.xiaozhiai.mapper")
public class XiaozhiAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaozhiAiApplication.class, args);
	}

}
