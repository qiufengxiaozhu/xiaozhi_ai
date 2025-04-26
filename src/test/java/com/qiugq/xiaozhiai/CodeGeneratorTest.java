package com.qiugq.xiaozhiai;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 描述：MybatisPlus代码生成器
 * 注意：其中逻辑删除注解需要自行加上，且需要关注控制器的请求路径
 * 自动生成器版本：
 * @author 邱高强
 * @since 🛩️2023-08-07,0007 10:47 上午
 */
@SpringBootTest
public class CodeGeneratorTest {

	@Test
	public void codeGenerator() {
		// 数据库连接
		String url = "jdbc:mysql://localhost:3306/guiguxiaozhi?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";
		String username = "root";//账号
		String password = "123456";//密码

		// 全局配置参数
		String author = "邱高强";//作者
		String outputDir = "D:\\CodeGenerator";//指定输出目录

		// 包配置参数
		String parent = "com.qiugq";//父包名
		String moduleName = "xiaozhiai";//父包模块名
		String entity = "entity";//Entity 实体类包名
		String mapper = "mapper";//Mapper 包名
		String mapperXml = "mapper";//Mapper XML 包名
		String service = "service";//Service 包名
		String serviceImpl = "service.impl";//Service Impl 包名
		String controller = "controller";//Controller 包名
		// 要生成的数据库表
		List<String> tables = Arrays.asList("appointment");
		// 开始生成
		FastAutoGenerator.create(url, username, password)
				// 全局配置
				.globalConfig(builder -> {
					builder.author(author)
							.outputDir(outputDir)
                            .enableSwagger() // 开启swagger
							.commentDate("yyyy-MM-dd"); // 注释日期
				})
				// 包配置
				.packageConfig(builder -> {
					builder.parent(parent)
							.moduleName(moduleName)
							.entity(entity)
							.mapper(mapper)
							.xml(mapperXml)
							.service(service)
							.serviceImpl(serviceImpl)
							.controller(controller);
				})
				// 策略配置
				.strategyConfig(builder -> {
					builder.addInclude(tables)
							// 开启生成实体类
							.entityBuilder()
							.enableLombok() // 开启 lombok 模型
							.formatFileName("%sEntity") // 设置实体类文件名称格式为 %sEntity
                           .enableTableFieldAnnotation() // 开启生成实体时生成字段注解

							// 开启生成mapper
							.mapperBuilder()
							.enableBaseResultMap() // 启用 BaseResultMap 生成
							.superClass(BaseMapper.class) // 设置父类
							.enableMapperAnnotation() // 开启 @Mapper 注解
							.formatMapperFileName("%sMapper") // 格式化 mapper 文件名称
							.formatXmlFileName("%sMapper") // 格式化 xml 实现类文件名称

							// 开启生成service及impl
							.serviceBuilder()
							.formatServiceFileName("%sService") // 格式化 service 接口文件名称
							.formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称

							// 开启生成controller
							.controllerBuilder()
							// 映射路径使用连字符格式，而不是驼峰
							.enableHyphenStyle()
							.formatFileName("%sController")//格式化文件名称
							.enableRestStyle();
					;
				})
				.templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				//.templateConfig(builder -> builder.controller(""))//关闭生成controller
				.execute();
		System.out.println("=====================代码生成成功！==========================");
	}
}
