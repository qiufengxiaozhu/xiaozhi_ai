package com.qiugq.xiaozhiai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 用户挂号预约表
 * </p>
 * @author 邱高强
 * @since 2025-04-26
 */
@Getter
@Setter
@ToString
@TableName("appointment")
@Schema(name = "AppointmentEntity", description = "用户挂号预约表")
public class AppointmentEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	@TableField("username")
	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;

	/**
	 * 身份证号码
	 */
	@TableField("id_card")
	@Schema(description = "身份证号码", requiredMode = RequiredMode.NOT_REQUIRED)
	private String idCard;

	/**
	 * 挂号科室
	 */
	@Schema(description = "挂号科室")
	@TableField("department")
	private String department;

	/**
	 * 挂号日期
	 */
	@TableField("date")
	@Schema(description = "挂号日期")
	private String date;

	/**
	 * 上午或下午
	 */
	@TableField("time")
	@Schema(description = "上午或下午", allowableValues = {"上午", "下午"}, requiredMode = RequiredMode.NOT_REQUIRED)
	private String time;

	/**
	 * 主任医师名
	 */
	@Schema(description = "主任医师名")
	@TableField("doctor_name")
	private String doctorName;
}
