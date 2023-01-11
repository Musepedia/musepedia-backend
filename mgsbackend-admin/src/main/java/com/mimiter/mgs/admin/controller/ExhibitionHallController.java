package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.query.ExhibitionHallQuery;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
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


        return BaseResponse.ok();
    }

    @ApiOperation(value = "添加展区", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addExhibitionHall(@RequestBody @Validated UpsertExhibitionHallReq req) {
        // 如果是博物馆管理员，museumId需要保证是自己博物馆id
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            Long museumId = req.getMuseumId();
            Long institutionId = institutionAdminRepository.findById(userId).getInstitutionId();
            if (!institutionId.equals(museumId)) {
                throw new IllegalArgumentException("展区与用户管理的博物馆不对应");
            }
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
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long museumId = exhibitionHallService.getById(req.getId()).getMuseumId();
            req.setMuseumId(museumId);
        }
        exhibitionHallService.updateExhibitionHall(req);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "删除展区", notes = "超级管理员和博物馆管理员可调用，"
            + "删除后相关展品的展区ID会被置为null")
    @DeleteMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> deleteExhibitionHall(@RequestBody List<Long> exhibitionHallIds) {
        // 如果是博物馆管理员，exhibitionHall需要保证都是自己博物馆的
        // 超级管理员无限制
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            Long institutionId = institutionAdminRepository.findById(userId).getInstitutionId();
            for (Long exhibitionHallId : exhibitionHallIds) {
                Long museumId = exhibitionHallService.getById(exhibitionHallId).getMuseumId();
                if (!museumId.equals(institutionId)) {
                    throw new IllegalArgumentException("有展区与用户管理的博物馆不对应");
                }
            }

        }

        // TODO: 删除展区存在 tbl_user_preference 表的外键约束
        throw new BadRequestException("展区存在外键约束");

//        exhibitionHallService.removeByIds(exhibitionHallIds);

        // 删除后相关展品的展区ID会被置为null
//        for (Long exhibitionHallId : exhibitionHallIds) {
//            QueryWrapper<Exhibit> e = new QueryWrapper<>();
//            e.eq("exhibition_hall_id", exhibitionHallId);
//            List<Exhibit> exhibitList = exhibitService.list(e);
//            for (Exhibit exhibit : exhibitList) {
//                exhibit.setHallId(null);
//                exhibitService.save(exhibit);
//            }
//        }
//
//        return BaseResponse.ok();
    }

    //------------展区信息------------
}
