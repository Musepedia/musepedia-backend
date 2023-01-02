package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.query.ExhibitionHallQuery;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.ExhibitionHallRepository;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/hall")
@RequiredArgsConstructor
@Api(value = "展区API", tags = {"展区API"})
public class ExhibitionHallController {

    private final ExhibitionHallRepository exhibitionHallRepository;

    private final InstitutionAdminRepository institutionAdminRepository;

    //------------展区信息------------

    @ApiOperation(value = "获取展区信息", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<ExhibitionHallDTO> getExhibitionHall(@PathVariable Long id) {
        return BaseResponse.ok();
    }

    @ApiOperation(value = "获取展区列表", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<ExhibitionHallDTO>> listExhibitionHall(@ApiParam ExhibitionHallQuery query) {
        return BaseResponse.ok();
    }

    @ApiOperation(value = "添加展区", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addExhibitionHall(@RequestBody @Validated UpsertExhibitionHallReq req) {
        // 如果是博物馆管理员，museumId需要保证是自己博物馆id
        // 超级管理员无限制

        // 返回插入的id
        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新展区", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitionHall(@RequestBody @Validated UpsertExhibitionHallReq req) {
        // 如果是博物馆管理员，不能更新museumId
        // 超级管理员无限制
        return BaseResponse.ok();
    }

    @ApiOperation(value = "删除展区", notes = "超级管理员和博物馆管理员可调用，"
            + "删除后相关展品的展区ID会被置为null")
    @DeleteMapping
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> deleteExhibitionHall(@RequestBody List<Long> exhibitionHallIds) {
        // 如果是博物馆管理员，exhibitionHall需要保证都是自己博物馆的
        // 超级管理员无限制

        // 删除后相关展品的展区ID会被置为null
        return BaseResponse.ok();
    }

    //------------展区信息------------
}
