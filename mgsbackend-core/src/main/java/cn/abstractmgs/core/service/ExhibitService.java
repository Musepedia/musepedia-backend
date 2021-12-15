package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;


public interface ExhibitService extends IService<Exhibit> {

    Exhibit selectInfoById(@Param("id") Long id);

    @Deprecated
    Exhibit selectTest(@Param("id") Long id);
}
