package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.ExhibitLabelAliasRequest;
import com.mimiter.mgs.admin.OpenDocumentRequest;
import com.mimiter.mgs.admin.OpenDocumentServiceGrpc;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.query.ExhibitQuery;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.repository.ExhibitAliasRepository;
import com.mimiter.mgs.admin.repository.ExhibitTextRepository;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.service.ExhibitAliasService;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.ForbiddenException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.var;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

    private final ExhibitionHallService exhibitionHallService;

    @GrpcClient("openDocumentService")
    private OpenDocumentServiceGrpc.OpenDocumentServiceBlockingStub openDocumentService;

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
            assertExhibitManageable(req.getId());
        }

        exhibitService.updateExhibit(req);

        return BaseResponse.ok();
    }

    @ApiOperation(value = "设置展品启用状态", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/enable")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> enableExhibit(@RequestBody @Validated SetEnableReq req) {
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            assertExhibitManageable(req.getId());
        }

        Exhibit exhibit = new Exhibit();
        exhibit.setId(req.getId());
        exhibit.setEnabled(req.getEnable());
        exhibitService.updateById(exhibit);

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

    @ApiOperation(value = "根据已存储的展品文本获取展品可能的别名", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/possible-alias")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> possibleAlias(@PathVariable("id") Long exhibitId) {
        List<String> textList = exhibitTextService.listExhibitText(exhibitId);

        var request = ExhibitLabelAliasRequest
                .newBuilder()
                .addAllTexts(textList).build();
        var resp = openDocumentService.getExhibitAlias(request);

        return BaseResponse.ok(resp.getAliasesList());
    }

    @ApiOperation(value = "更新展品别名", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/alias")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitAlias(@RequestBody @Validated UpdateExhibitAliasReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            assertExhibitManageable(req.getExhibitId());
        }

        exhibitAliasService.updateExhibitAlias(req);

        return BaseResponse.ok();
    }

    @ApiOperation(value = "获取展品文本", notes = "超级管理员和博物馆管理员可调用")
    @GetMapping("/{id:\\d+}/text")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<List<String>> getExhibitText(@PathVariable("id") Long exhibitId) {
        return BaseResponse.ok("ok", exhibitTextService.listExhibitText(exhibitId));
    }

    @ApiOperation(value = "更新展品文本", notes = "超级管理员和博物馆管理员可调用")
    @PutMapping("/text")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateExhibitText(@RequestBody @Validated UpdateExhibitTextReq req) {
        // 如果是博物馆管理员，需要判断展品是否属于自己的博物馆
        if (permissions.contains(STR_MUSEUM_ADMIN)) {
            assertExhibitManageable(req.getExhibitId());
        }
        Exhibit exhibit = exhibitService.getNotNullById(req.getExhibitId());

        exhibitTextService.updateExhibitText(req);

        // 请求爬取OpenDocument
        var openDocumentReq = OpenDocumentRequest.newBuilder()
                .addAllTexts(req.getExhibitText())
                .setLabel(exhibit.getLabel())
                .build();
        openDocumentService.getOpenDocument(openDocumentReq);

        return BaseResponse.ok();
    }

    private void assertExhibitManageable(Long exhibitId) {
        Long userId = SecurityUtil.getCurrentUserId();
        Long hallId = exhibitService.getById(exhibitId).getHallId();

        Long museumId = exhibitionHallService.getById(hallId).getMuseumId();
        InstitutionAdmin institutionAdmin = institutionAdminRepository.findById(userId);

        if (!institutionAdmin.getInstitutionId().equals(museumId)) {
            throw new ForbiddenException("更新的展品与用户管理的博物馆不对应");
        }
    }
}
