package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.service.UserPreferenceService;
import com.mimiter.mgs.core.service.mapstruct.ExhibitionHallDTOMapper;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
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
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        List<ExhibitionHall> halls = userPreferenceService.getPreferredHallByUserId(userId, museumId);
        return BaseResponse.ok("ok", halls.stream().map(ExhibitionHall::getId).collect(Collectors.toList()));
    }

    @PutMapping("/preference")
    public BaseResponse<?> updatePreference(@RequestBody List<Long> hallIds) {
        Long userId = SecurityUtil.getCurrentUserId();
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        boolean isUpdated = userPreferenceService.updateUserPreference(userId, hallIds, museumId);

        return isUpdated
                ? BaseResponse.ok("用户偏好更新成功")
                : BaseResponse.error("用户偏好更新失败");
    }
}
