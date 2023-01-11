package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.query.ExhibitQuery;
import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.repository.ExhibitAliasRepository;
import com.mimiter.mgs.admin.repository.ExhibitTextRepository;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.repository.QuestionRepository;
import com.mimiter.mgs.admin.service.*;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RestController
@RequestMapping("/api/exhibit")
@RequiredArgsConstructor
@Api(value = "展品API", tags = {"展品API"})
public class ExhibitController {

    private final Permissions permissions;

    private final ExhibitService exhibitService;

    private final ExhibitTextService exhibitTextService;

    private final ExhibitTextRepository exhibitTextRepository;

    private final ExhibitAliasRepository exhibitAliasRepository;

    private final ExhibitAliasService exhibitAliasService;

    private final InstitutionAdminRepository institutionAdminRepository;

    private final QuestionRepository questionRepository;

    private final ExhibitionHallService exhibitionHallService;

    //------------展品信息------------

    @ApiOperation(value = "获取展品信息", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<ExhibitDTO> getExhibit(@PathVariable Long id) {
        Exhibit exhibit = exhibitService.getById(id);
        if (exhibit == null) {
            throw new ResourceNotFoundException("展品不存在");
        }
        return BaseResponse.ok("ok", exhibitService.toDTO(exhibit));
    }

    @ApiOperation(value = "获取展品列表", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<ExhibitDTO>> listExhibit(@ApiParam ExhibitQuery query) {
        Page<Exhibit> p = exhibitService.page(query.toPage(), query.toQueryWrapper());

        List<ExhibitDTO> dtos = p.getRecords().stream().map(exhibitService::toDTO).collect(Collectors.toList());
        return BaseResponse.ok("ok", new PageDTO<>(dtos, p.getTotal()));
    }

    @ApiOperation(value = "添加展品", notes = "超级管理员和博物馆管理员可调用")
    @PostMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<Long> addExhibit(@RequestBody @Validated UpsertExhibitReq req) {
        // 非超级管理员添加展品时，需要检查展区对应博物馆id是否属于当前用户
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            ExhibitionHall exhibitionHall = exhibitionHallService.getById(req.getHallId());
            InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

            Long museumId = exhibitionHall.getMuseumId();
            if (!institutionAdmin.getInstitutionId().equals(museumId)) {
                throw new IllegalArgumentException("上传的展品与用户管理的博物馆不对应");
            }
        }
        Exhibit exhibit = exhibitService.addExhibit(req);

        return BaseResponse.ok("ok", exhibit.getId());
    }

    @ApiOperation(value = "更新展品", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibit(@RequestBody @Validated UpsertExhibitReq req) {
        // 非超级管理员更新展品时，需要检查展品对应博否物馆id是属于当前用户
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            ExhibitionHall exhibitionHall = exhibitionHallService.getById(req.getHallId());
            InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

            Long museumId = exhibitionHall.getMuseumId();
            if (!institutionAdmin.getInstitutionId().equals(museumId)) {
                throw new IllegalArgumentException("更新的展品与用户管理的博物馆不对应");
            }
        }

        exhibitService.updateExhibit(req);

        return BaseResponse.ok();
    }

    @ApiOperation(value = "删除展品", notes = "超级管理员和博物馆管理员可调用")
    @DeleteMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> deleteExhibit(@RequestBody List<Long> ids) {
        // 非超级管理员删除展品时，需要检查展区对应博物馆id是否属于当前用户
        // 暂时决定有RecommendQuestion时不允许删除(有外键)，即该展品已经被提过问
        // 删除展品同时需要将展品下的所有展品文本和展品别名删除

        // 非超级管理员删除
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            for (Long id : ids) {
                Long hallId = exhibitService.getById(id).getHallId();

                Long museumId = exhibitionHallService.getById(hallId).getMuseumId();
                InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

                if (!institutionAdmin.getInstitutionId().equals(museumId)) {
                    throw new IllegalArgumentException("删除的展品中有展品与用户管理的博物馆不对应");
                }
            }
        }

        // 删除展品和展品下所有文本和别名，有RecommendQuestion时不允许删除
        for (Long id : ids) {
            if (questionRepository.findByExhibitId(id) != null) {
                throw new BadRequestException("当前有展品已被提问，不能删除");
            }
            exhibitAliasRepository.deleteByExhibitId(id);
            exhibitTextRepository.deleteByExhibitId(id);
        }
        exhibitService.removeByIds(ids);

        return BaseResponse.ok();
    }


    //------------展品信息------------

    @ApiOperation(value = "获取展品别名", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/alias")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> getExhibitAlias(@PathVariable("id") Long exhibitId) {
        ArrayList<String> aliasList = new ArrayList<>();
        QueryWrapper<ExhibitAlias> ea = new QueryWrapper<>();
        ea.eq("exhibit_id", exhibitId);

        for (ExhibitAlias exhibitAlias : exhibitAliasRepository.selectList(ea)) {
            String alias = exhibitAlias.getAlias();
            aliasList.add(alias);
        }
        return BaseResponse.ok("ok", aliasList);
    }

    @ApiOperation(value = "更新展品别名", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/alias")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitAlias(@RequestBody @Validated UpdateExhibitAliasReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            Long hallId = exhibitService.getById(req.getExhibitId()).getHallId();

            Long museumId = exhibitionHallService.getById(hallId).getMuseumId();
            InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

            if (!institutionAdmin.getInstitutionId().equals(museumId)) {
                throw new IllegalArgumentException("更新的展品与用户管理的博物馆不对应");
            }
        }

        exhibitAliasService.updateExhibitAlias(req);

        return BaseResponse.ok();
    }

    @ApiOperation(value = "获取展品文本", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/text")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> getExhibitText(@PathVariable("id") Long exhibitId) {
        ArrayList<String> textList = new ArrayList<>();
        QueryWrapper<ExhibitText> et = new QueryWrapper<>();
        et.eq("exhibit_id", exhibitId);

        for (ExhibitText exhibitText : exhibitTextRepository.selectList(et)) {
            String alias = exhibitText.getText();
            textList.add(alias);
        }
        return BaseResponse.ok("ok", textList);
    }

    @ApiOperation(value = "更新展品文本", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/text")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitText(@RequestBody @Validated UpdateExhibitTextReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            Long userId = SecurityUtil.getCurrentUserId();
            Long hallId = exhibitService.getById(req.getExhibitId()).getHallId();

            Long museumId = exhibitionHallService.getById(hallId).getMuseumId();
            InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

            if (!institutionAdmin.getInstitutionId().equals(museumId)) {
                throw new IllegalArgumentException("更新的展品与用户管理的博物馆不对应");
            }
        }

        exhibitTextService.updateExhibitText(req);

        return BaseResponse.ok();
    }
}
