package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.UserQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;


public interface UserQuestionRepository extends BaseMapper<UserQuestion>{
    @Select("select ")
}
