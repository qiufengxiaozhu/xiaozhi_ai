package com.qiugq.xiaozhiai;

import com.qiugq.xiaozhiai.entity.AppointmentEntity;
import com.qiugq.xiaozhiai.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 预约表增删改查测试
 */
@SpringBootTest
class AppointmentServiceTest {

	@Autowired
	private AppointmentService appointmentService;

	@Test
	void testGetOne() {
		AppointmentEntity appointment = new AppointmentEntity();
		appointment.setUsername("张三");
		appointment.setIdCard("123456789012345678");
		appointment.setDepartment("内科");
		appointment.setDate("2025-04-14");
		appointment.setTime("上午");
		AppointmentEntity appointmentDB = appointmentService.getOne(appointment);
		System.out.println(appointmentDB);
	}

	@Test
	void testSave() {
		AppointmentEntity appointment = new AppointmentEntity();
		appointment.setUsername("张三");
		appointment.setIdCard("123456789012345678");
		appointment.setDepartment("内科");
		appointment.setDate("2025-04-14");
		appointment.setTime("上午");
		appointment.setDoctorName("张医生");
		appointmentService.save(appointment);
	}

	@Test
	void testRemoveById() {
		appointmentService.removeById(1L);
	}
}
