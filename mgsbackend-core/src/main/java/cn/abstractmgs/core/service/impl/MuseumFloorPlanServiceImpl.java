package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.service.MuseumFloorPlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("museumFloorPlanService")
public class MuseumFloorPlanServiceImpl implements MuseumFloorPlanService {

    public static final String jsonStr = "{\"5\":[],\"7\":[\"9\",\"10\"],\"9\":[\"7\"],\"10\":[\"7\"],\"8\":[\"6\"],\"3\":[\"4\",\"2\"],\"4\":[\"3\"],\"1\":[\"2\"],\"6\":[\"8\"],\"2\":[\"3\",\"1\"]}";

    @Override
    public HashMap<String, List<String>> getMuseumFloorPlan(Long museumId) {
//        HashMap<String, String[]> neighbors = new HashMap<>();
//        neighbors.put("1", new String[]{"2"});
//        neighbors.put("2", new String[]{"3", "1"});
//        neighbors.put("3", new String[]{"4", "2"});
//        neighbors.put("4", new String[]{"3"});
//        neighbors.put("5", new String[0]);
//        neighbors.put("6", new String[]{"8"});
//        neighbors.put("7", new String[]{"9", "10"});
//        neighbors.put("8", new String[]{"6"});
//        neighbors.put("9", new String[]{"7"});
//        neighbors.put("10", new String[]{"7"});
//        return neighbors;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveMuseumFloorPlan(HashMap<String, List<String>> museumFloorPlan, Long museumId) {

    }
}
