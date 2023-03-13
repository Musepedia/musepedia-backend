package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.mapstruct.CreativeDTOMapper;
import com.mimiter.mgs.admin.model.dto.CulturalCreativeDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.query.CreativeQuery;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.service.CulturalCreativeService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.CulturalCreative;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/creative")
@RequiredArgsConstructor
@Api(value = "文创API", tags = {"文创API"})
public class CulturalCreativeController {

    private final CulturalCreativeService creativeService;

    private final CreativeDTOMapper creativeDTOMapper;

    private final InstitutionAdminRepository institutionAdminRepository;

    private final Permissions permissions;

    @ApiOperation(value = "获取文创信息", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<CulturalCreativeDTO> getCulturalCreative(@PathVariable Long id) {
        CulturalCreative cc = creativeService.getById(id);
        if (cc == null) {
            throw new ResourceNotFoundException("文创不存在");
        }
        return BaseResponse.ok("ok", creativeDTOMapper.toDto(cc));
    }

    @ApiOperation(value = "获取文创列表", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<CulturalCreativeDTO>> listCulturalCreative(CreativeQuery query) {
        Page<CulturalCreative> p = creativeService.page(query.toPage(), query.toQueryWrapper());

        List<CulturalCreativeDTO> dtos = creativeDTOMapper.toDto(p.getRecords());
        return BaseResponse.ok("ok", new PageDTO<>(dtos, p.getTotal()));
    }

    @ApiOperation(value = "添加文创", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addCulturalCreative(@RequestBody @Validated CulturalCreativeDTO req) {
        if (!permissions.contains(STR_SYS_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);
            req.setMuseumId(institutionAdmin.getInstitutionId());
        }

        var cc = creativeDTOMapper.toEntity(req);
        creativeService.save(cc);

        return BaseResponse.ok("ok", cc.getId());
    }

    @ApiOperation(value = "更新文创", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateCulturalCreative(@RequestBody @Validated CulturalCreativeDTO req) {
        Assert.notNull(req.getId(), "文创id不能为空");
        if (!permissions.contains(STR_SYS_ADMIN)) {
            req.setMuseumId(null);
        }

        var cc = creativeDTOMapper.toEntity(req);
        creativeService.updateById(cc);

        return BaseResponse.ok("ok", cc.getId());
    }

}
