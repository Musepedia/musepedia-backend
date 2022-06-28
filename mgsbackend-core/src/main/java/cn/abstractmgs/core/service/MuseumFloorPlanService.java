package cn.abstractmgs.core.service;

import java.util.HashMap;
import java.util.List;

public interface MuseumFloorPlanService {

    HashMap<String, List<String>> getMuseumFloorPlan(Long museumId);

    void saveMuseumFloorPlan(HashMap<String, List<String>> museumFloorPlan, Long museumId);
}
