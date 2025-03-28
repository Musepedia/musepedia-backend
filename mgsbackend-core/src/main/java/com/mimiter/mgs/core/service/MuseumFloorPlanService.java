package com.mimiter.mgs.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;

/**
 * 楼层平面图服务
 */
public interface MuseumFloorPlanService {
    
    HashMap<String, List<String>> getMuseumFloorPlan(Long museumId) throws JsonProcessingException;
    
    void saveMuseumFloorPlan(HashMap<String, List<String>> museumFloorPlan, Long museumId);
    
    String getMuseumFloorPlanFilepath(Long museumId);
}
