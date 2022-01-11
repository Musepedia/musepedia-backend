package cn.abstractmgs.core.model.handler;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes(ExhibitionHall.class)
public class ExhibitionHallTypeHandler implements TypeHandler<ExhibitionHall> {
    /*
     * 用于转换java类型和jdbc类型（ExhibitionHall类型需要显式调用这个TypeHandler）
     */

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, ExhibitionHall exhibitionHall, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public ExhibitionHall getResult(ResultSet resultSet, String s) throws SQLException {
        String name = resultSet.getString("exhibition_hall_name");
        String description = resultSet.getString("exhibition_hall_description");
        return new ExhibitionHall(name, description);
    }

    @Override
    public ExhibitionHall getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public ExhibitionHall getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
