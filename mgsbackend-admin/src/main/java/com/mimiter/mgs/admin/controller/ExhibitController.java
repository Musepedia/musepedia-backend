package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.query.ExhibitQuery;
import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
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
@RequestMapping("/api/exhibit")
@RequiredArgsConstructor
@Api(value = "展品API", tags = {"展品API"})
public class ExhibitController {


    //------------展品信息------------

    @ApiOperation(value = "获取展品信息", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<ExhibitDTO> getExhibit(@PathVariable Long id) {

        return BaseResponse.ok();
    }

    @ApiOperation(value = "获取展品列表", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<ExhibitDTO>> listExhibit(@ApiParam ExhibitQuery query) {

        return BaseResponse.ok();
    }

    @ApiOperation(value = "添加展品", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addExhibit(@RequestBody @Validated UpsertExhibitReq req) {
        // 非超级管理员添加展品时，需要检查展区对应博物馆id是否属于当前用户

        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新展品", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibit(@RequestBody @Validated UpsertExhibitReq req) {
        // 非超级管理员更新展品时，需要检查展品对应博物馆id是否属于当前用户

        return BaseResponse.ok();
    }

    @ApiOperation(value = "删除展品", notes = "超级管理员和博物馆管理员可调用")
    @DeleteMapping("/{id:\\d+}")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> deleteExhibit(@PathVariable Long id) {
        // 非超级管理员删除展品时，需要检查展区对应博物馆id是否属于当前用户
        // 暂时决定有RecommendQuestion时不允许删除(有外键)，即该展品已经被提过问
        // 删除展品同时需要将展品下的所有展品文本和展品别名删除

        return BaseResponse.ok();
    }


    //------------展品信息------------

    @ApiOperation(value = "获取展品别名", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/alias")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> getExhibitAlias(@PathVariable("id") Long exhibitId) {

        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新展品别名", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/alias")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitAlias(@RequestBody @Validated UpdateExhibitAliasReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆

        return BaseResponse.ok();
    }

    @ApiOperation(value = "获取展品文本", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/text")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> getExhibitText(@PathVariable("id") Long exhibitId) {

        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新展品文本", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/text")
    @PreAuthorize("pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitText(@RequestBody @Validated UpdateExhibitTextReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆

        return BaseResponse.ok();
    }
}
