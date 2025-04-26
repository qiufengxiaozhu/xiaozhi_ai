package com.qiugq.xiaozhiai.service;

import com.qiugq.xiaozhiai.entity.AppointmentEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 邱高强
 * @since 2025-04-26
 */
public interface AppointmentService {

	/**
	 * 根据条件查询一条记录
	 */
	AppointmentEntity getOne(AppointmentEntity appointmentEntity);

	/**
	 * 保存一条记录
	 */
	Boolean save(AppointmentEntity appointment);

	/**
	 * 根据主键删除一条记录
	 */
	Boolean removeById(long id);
}
