package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ExhibitService extends IService<Exhibit> {

    Exhibit getExhibitInfoById(Long id);

    List<String> selectRandomExhibitId(@Param("limit") int limitPerExhibitionHall);

    List<Exhibit> selectRandomExhibits(@Param("ids") List<Integer> ids);

    List<Exhibit> getRandomExhibits(int limitPerExhibitionHall);

    String selectExhibitFigureUrlByLabel(@Param("label") String label);

    List<Exhibit> getExhibitsInSameExhibitionHall(@Param("id") Long id);

    boolean isSameExhibitionHall(@Param("id1") int id1, @Param("id2") int id2);
}
