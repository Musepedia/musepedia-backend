package com.mimiter.mgs.admin.service.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 抽象的CrudService，涉及到Crud的业务都可以继承此类
 *
 * @param <M> repository类
 * @param <T> entity类
 */
public abstract class AbstractCrudService<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T> implements CrudService<T> {

    /**
     * 根据id获取实体，如果id不存在则抛出异常
     *
     * @param id id
     * @return T
     * @throws ResourceNotFoundException 如果无法通过id找到对应实体
     */
    public T getNotNullById(Serializable id) {
        Assert.notNull(id, "Id must not be null");
        T t = getById(id);
        if (t == null) {
            throw new ResourceNotFoundException("Id: " + id + " not found");
        }
        return t;
    }
}
