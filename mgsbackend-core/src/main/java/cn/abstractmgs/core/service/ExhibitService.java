package cn.abstractmgs.core.service;

import cn.abstractmgs.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ExhibitService extends IService<Exhibit> {

    Exhibit getExhibitInfoById(Long id);

    List<String> selectRandomExhibitId(int limitPerExhibitionHall, Long museumId);

    List<Exhibit> selectRandomExhibits(@Param("ids") List<Integer> ids);

    List<Exhibit> getRandomExhibits(int limitPerExhibitionHall, Long museumId);

    String selectExhibitFigureUrlByLabel(@Param("label") String label);

    List<Exhibit> getExhibitsInSameExhibitionHall(@Param("id") Long id);

    Long selectExhibitionHallIdByExhibitId(@Param("id") Long id);

    Long selectExhibitIdByLabel(@Param("label") String label);

    boolean isSameExhibitionHall(@Param("id1") int id1, @Param("id2") int id2);

    List<Exhibit> selectPreviousAndNextExhibitById(@Param("id") Long id);

    List<Exhibit> getMostFrequentExhibits(int count, Long museumId);
}
