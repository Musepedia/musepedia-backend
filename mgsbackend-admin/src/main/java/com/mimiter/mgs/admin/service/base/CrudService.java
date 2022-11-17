package com.mimiter.mgs.admin.service.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * 包含共同的Crud方法，包含Crud的服务的接口都可以继承此接口
 *
 * @param <T> 实体类
 */
public interface CrudService<T> extends IService<T> {

    T getNotNullById(Serializable id);
}
