package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.enums.InstitutionType;
import com.mimiter.mgs.admin.model.query.MuseumQuery;
import com.mimiter.mgs.admin.model.request.SetServicedReq;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.model.request.UpdateMuseumReq;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/museum")
@RequiredArgsConstructor
@Api(value = "博物馆API", tags = {"博物馆API"})
public class MuseumController {

    private final AdminMuseumService adminMuseumService;

    private final RoleService roleService;

    private final InstitutionAdminRepository institutionAdminRepository;

    //--------Museum Admin Operation------------

    @ApiOperation(value = "根据ID获取博物馆信息", notes = "博物馆管理员和超级管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Museum> getMuseum(@PathVariable Long id) {
        return BaseResponse.ok(adminMuseumService.getById(id));
    }

    @ApiOperation(value = "更新所管理的博物馆信息", notes = "博物馆管理员可调用")
    @PutMapping("/mine")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "')")
    public BaseResponse<?> updateMyMuseum(@RequestBody @Validated UpdateMuseumReq req) {
        Long userId = SecurityUtil.getCurrentUserId();
        QueryWrapper<InstitutionAdmin> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        query.eq("institution_type", InstitutionType.MUSEUM);
        InstitutionAdmin ia = institutionAdminRepository.selectOne(query);
        if (ia == null) {
            throw new BadRequestException("您不是博物馆管理员");
        }
        req.setId(ia.getInstitutionId());

        adminMuseumService.updateById(req);

        return BaseResponse.ok();
    }

    //---------Museum Admin Operation-----------

    //-----------Sys Admin Operation------------

    @ApiOperation(value = "查询博物馆列表", notes = "仅超级管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<Museum>> listMuseum(@ApiParam MuseumQuery query) {
        Page<Museum> p = adminMuseumService.page(query.toPage(), query.toQueryWrapper());
        List<Museum> museums = p.getRecords();
        return BaseResponse.ok("ok", new PageDTO<>(museums, p.getTotal()));
    }

    @ApiOperation(value = "添加博物馆", notes = "仅超级管理员可调用")
    @PostMapping
    @PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> addMuseum(@RequestBody @Validated AddMuseumReq museum) {
        adminMuseumService.addMuseum(museum);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新单个博物馆信息", notes = "仅超级管理员可调用")
    @PutMapping
    @PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateMuseum(@RequestBody @Validated UpdateMuseumReq museum) {
        adminMuseumService.updateById(museum);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "设置单个博物馆的经营状态", notes = "仅超级管理员可调用")
    @PutMapping("/service")
    @PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> setServiced(@RequestBody @Validated SetServicedReq req) {
        adminMuseumService.setServiced(req.getId(), req.getService());
        return BaseResponse.ok();
    }

    //-----------Sys Admin Operation------------

}
