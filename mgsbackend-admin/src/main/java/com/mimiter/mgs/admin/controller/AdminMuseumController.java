package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.mapstruct.AdminUserMapper;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.query.MuseumQuery;
import com.mimiter.mgs.admin.model.request.SetServicedReq;
import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.admin.model.query.UserQuery;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.UpdateMuseumReq;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("@pm.check('" + STR_SYS_ADMIN + "')")
public class AdminMuseumController {

    private final AdminMuseumService adminMuseumService;

    private final RoleService roleService;

    //-----------Admin Operation------------

    @ApiOperation("查询博物馆列表")
    @GetMapping("/museumList")
    public BaseResponse<PageDTO<Museum>> listMuseum(@ApiParam MuseumQuery query) {
        Page<Museum> p = adminMuseumService.page(query.toPage(), query.toQueryWrapper());
        List<Museum> museums = p.getRecords();
        return BaseResponse.ok("ok", new PageDTO<>(museums, p.getTotal()));
    }

    @ApiOperation("添加博物馆")
    @PostMapping("/addMuseum")
    public BaseResponse<?> addMuseum(@RequestBody @Validated AddMuseumReq museum) {
        adminMuseumService.addMuseum(museum);
        return BaseResponse.ok();
    }

    @ApiOperation("更新单个博物馆信息")
    @PutMapping("/updateMuseum")
    public BaseResponse<?> updateMuseum(@RequestBody @Validated UpdateMuseumReq museum) {
        adminMuseumService.updateById(museum);
        return BaseResponse.ok();
    }

    @ApiOperation("切换单个博物馆的经营状态")
    @PutMapping("/service")
    public BaseResponse<?> setServiced(@RequestBody @Validated SetServicedReq req) {
        adminMuseumService.setServiced(req.getId(), req.getService());
        return BaseResponse.ok();
    }

}
