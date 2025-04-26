package com.qiugq.xiaozhiai.tools;

import com.qiugq.xiaozhiai.entity.AppointmentEntity;
import com.qiugq.xiaozhiai.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 预约工具
 */
@Component
public class AppointmentTools {

	@Autowired
	private AppointmentService appointmentService;

	/**
	 * 预约挂号，千问模型的name可以为中文，openApi模型的好像只能是英文，待确认
	 */
	@Tool(name = "预约挂号", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，" +
			"并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。")
	public String bookAppointment(AppointmentEntity appointment) {

		// 查找数据库中是否包含对应的预约记录
		AppointmentEntity appointmentDB = appointmentService.getOne(appointment);
		if (appointmentDB != null) {
			return "您在相同的科室和时间已有预约";
		}

		// 防止大模型幻觉设置了id
		appointment.setId(null);
		return appointmentService.save(appointment) ? "预约成功，并返回预约详情" : "预约失败";
	}

	/**
	 * 取消预约挂号
	 */
	@Tool(name = "取消预约挂号", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
	public String cancelAppointment(AppointmentEntity appointment) {

		AppointmentEntity appointmentDB = appointmentService.getOne(appointment);
		// 取消失败
		if (appointmentDB == null) {
			return "您没有预约记录，请核对预约科室和时间";
		}

		// 删除预约记录
		return appointmentService.removeById(appointmentDB.getId()) ? "取消预约成功" : "取消预约失败";
	}

	/**
	 * 查询是否有号源
	 */
	@Tool(name = "查询是否有号源", value = "根据科室名称，日期，时间和医生查询是否有号源，并返回给用户")
	public boolean queryDepartment(
			@P(value = "科室名称") String name,
			@P(value = "日期") String date,
			@P(value = "时间，可选值：上午、下午") String time,
			@P(value = "医生名称", required = false) String doctorName) {

		System.out.println("查询是否有号源");
		System.out.println("科室名称：" + name);
		System.out.println("日期：" + date);
		System.out.println("时间：" + time);
		System.out.println("医生名称：" + doctorName);

		// TODO 维护医生的排班信息：
		// 如果没有指定医生名字，则根据其他条件查询是否有可以预约的医生（有返回true，否则返回false）；
		// 如果指定了医生名字，则判断医生是否有排班（没有排版返回false）
		// 如果有排班，则判断医生排班时间段是否已约满（约满返回false，有空闲时间返回true）
		return true;
	}
}
