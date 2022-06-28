package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.ExhibitionHallDTO;
import cn.abstractmgs.core.model.entity.ExhibitionHall;
import cn.abstractmgs.core.service.UserPreferenceService;
import cn.abstractmgs.core.service.mapstruct.ExhibitionHallDTOMapper;
import cn.abstractmgs.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

    private final UserPreferenceService userPreferenceService;

    private final ExhibitionHallDTOMapper exhibitionHallDTOMapper;

    @GetMapping("/preference")
    public BaseResponse<List<Long>> getUserPreference(){
        Long userId = SecurityUtil.getCurrentUserId();
        List<ExhibitionHall> halls = userPreferenceService.getPreferredHallByUserId(userId);
        return BaseResponse.ok("ok", halls.stream().map(ExhibitionHall::getId).collect(Collectors.toList()));
    }

    @PutMapping("/preference")
    public BaseResponse<?> updatePreference(@RequestBody List<Long> hallIds) {
        Long userId = SecurityUtil.getCurrentUserId();
        boolean isUpdated = userPreferenceService.updateUserPreference(userId, hallIds);

        return isUpdated
                ? BaseResponse.ok("用户偏好更新成功")
                : BaseResponse.error("用户偏好更新失败");
    }
}
