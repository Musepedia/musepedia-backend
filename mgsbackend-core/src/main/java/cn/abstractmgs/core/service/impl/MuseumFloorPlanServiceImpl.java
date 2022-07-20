package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Museum;
import cn.abstractmgs.core.service.MuseumFloorPlanService;
import cn.abstractmgs.core.service.MuseumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service("museumFloorPlanService")
@RequiredArgsConstructor
public class MuseumFloorPlanServiceImpl implements MuseumFloorPlanService {

    private final MuseumService museumService;

    @Override
    public HashMap getMuseumFloorPlan(Long museumId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String path = getMuseumFloorPlanFilepath(museumId);
            JsonNode root = mapper.readTree(new File(path));
            String jsonStr = root.toString();
            try {
                return mapper.readValue(jsonStr, new TypeReference <HashMap <String, List <String>>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void saveMuseumFloorPlan(HashMap <String, List <String>> museumFloorPlan, Long museumId) {
        ObjectMapper mapper = new ObjectMapper();
        String filename = getMuseumFloorPlanFilepath(museumId);
        try {
            String jsonStr = mapper.writeValueAsString(museumFloorPlan);
            try (FileWriter fw = new FileWriter(filename))
            {
                fw.write(jsonStr);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getMuseumFloorPlanFilepath(Long museumId) {
        // 查询平面图路径
        Museum museum = museumService.getById(museumId);
        String path = museum.getFloorPlanFilepath();
        return path;
    }
}
