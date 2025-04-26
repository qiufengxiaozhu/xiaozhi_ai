package com.qiugq.xiaozhiai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiugq.xiaozhiai.entity.AppointmentEntity;
import com.qiugq.xiaozhiai.mapper.AppointmentMapper;
import com.qiugq.xiaozhiai.service.AppointmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author 邱高强
 * @since 2025-04-26
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentMapper appointmentMapper;

	/**
	 * 根据条件查询一条记录
	 */
	@Override
	public AppointmentEntity getOne(AppointmentEntity entity) {
		LambdaQueryWrapper<AppointmentEntity> query = new LambdaQueryWrapper<AppointmentEntity>()
				.eq(AppointmentEntity::getUsername, entity.getUsername())
				.eq(AppointmentEntity::getIdCard, entity.getIdCard())
				.eq(AppointmentEntity::getDepartment, entity.getDepartment())
				.eq(AppointmentEntity::getDate, entity.getDate())
				.eq(AppointmentEntity::getTime, entity.getTime());
				// .eq(StringUtils.isNotBlank(entity.getDoctorName()),AppointmentEntity::getDoctorName, entity.getDoctorName());
		return appointmentMapper.selectOne(query);
	}

	/**
	 * 保存一条记录
	 */
	@Override
	public Boolean save(AppointmentEntity appointment) {
		return appointmentMapper.insert(appointment) > 0;
	}

	/**
	 * 根据主键删除一条记录
	 */
	@Override
	public Boolean removeById(long id) {
		return appointmentMapper.deleteById(id) > 0;
	}
}
