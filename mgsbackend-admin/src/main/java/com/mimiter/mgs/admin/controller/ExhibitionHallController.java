package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.query.ExhibitionHallQuery;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.ForbiddenException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/hall")
@RequiredArgsConstructor
@Api(value = "展区API", tags = {"展区API"})
public class ExhibitionHallController {

    private final ExhibitionHallService exhibitionHallService;

    private final InstitutionAdminRepository institutionAdminRepository;

    private final Permissions permissions;

    private final ExhibitService exhibitService;

    //------------展区信息------------

    @ApiOperation(value = "获取展区信息", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<ExhibitionHallDTO> getExhibitionHall(@PathVariable Long id) {
        ExhibitionHall exhibitionHall = exhibitionHallService.getById(id);
        if (exhibitionHall == null) {
            throw new ResourceNotFoundException("展区不存在");
        }
        return BaseResponse.ok("ok", exhibitionHallService.toDTO(exhibitionHall));
    }

    @ApiOperation(value = "获取展区列表", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<ExhibitionHallDTO>> listExhibitionHall(@ApiParam ExhibitionHallQuery query) {
        Page<ExhibitionHall> p = exhibitionHallService.page(query.toPage(), query.toQueryWrapper());
        List<ExhibitionHallDTO> list = p.getRecords()
                .stream()
                .map(exhibitionHallService::toDTO)
                .collect(Collectors.toList());
        return BaseResponse.ok(new PageDTO<>(list, p.getTotal()));
    }

    @ApiOperation(value = "添加展区", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addExhibitionHall(@RequestBody @Validated UpsertExhibitionHallReq req) {
        // 如果是博物馆管理员，museumId需要保证是自己博物馆id
        if (!permissions.contains(STR_SYS_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            Long institutionId = institutionAdminRepository.findById(userId).getInstitutionId();
            req.setMuseumId(institutionId);
        }

        ExhibitionHall e = exhibitionHallService.addExhibitionHall(req);

        // 返回插入的id
        return BaseResponse.ok("ok", e.getId());
    }

    @ApiOperation(value = "更新展区", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitionHall(@RequestBody @Validated UpsertExhibitionHallReq req) {
        // 如果是博物馆管理员，不能更新museumId
        // 超级管理员无限制
        if (!permissions.contains(STR_SYS_ADMIN)) {
            req.setMuseumId(null);
        }
        exhibitionHallService.updateExhibitionHall(req);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "设置展区启用状态", notes = "超级管理员和博物馆管理员可调用, \n"
            + "同时会设置展区下所有展品的启用状态")
    @PutMapping("/enable")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> enableExhibitionHall(@RequestBody @Validated SetEnableReq req) {
        if (!permissions.contains(STR_SYS_ADMIN)) {
            assertHallManageable(req.getId());
        }

        exhibitionHallService.setExhibitionHallEnable(req);

        return BaseResponse.ok();
    }

    private void assertHallManageable(Long hallId) {
        Long userId = SecurityUtil.getCurrentUserId();
        Long institutionId = institutionAdminRepository.findById(userId).getInstitutionId();
        Long museumId = exhibitionHallService.getNotNullById(hallId).getMuseumId();
        if (!museumId.equals(institutionId)) {
            throw new ForbiddenException("展区与用户管理的博物馆不对应");
        }
    }

    //------------展区信息------------
}
