package com.mimiter.mgs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mimiter.mgs.admin.config.security.Permissions;
import com.mimiter.mgs.admin.mapstruct.QuestionDTOMapper;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.dto.QuestionDTO;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.query.QuestionQuery;
import com.mimiter.mgs.admin.model.request.UpdateQuestionReq;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.repository.QuestionRepository;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import com.mimiter.mgs.common.exception.ForbiddenException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@Slf4j
@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Api(value = "提问/问题API", tags = {"提问/问题API"})
public class QuestionController {

    private final QuestionRepository questionRepository;

    private final ExhibitService exhibitService;

    private final ExhibitionHallService hallService;

    private final AdminMuseumService adminMuseumService;

    private final QuestionDTOMapper questionDTOMapper;

    private final Permissions permissions;

    private final InstitutionAdminRepository institutionAdminRepository;

    @ApiOperation(value = "获取提问信息", notes = "博物馆管理员和超级管理员可调用")
    @GetMapping("/{id:\\d+}")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<QuestionDTO> getQuestion(@PathVariable Long id) {
        RecommendQuestion question = questionRepository.selectById(id);
        if (question == null) {
            throw new ResourceNotFoundException("问题不存在");
        }
        if (!permissions.contains(STR_SYS_ADMIN)) {
            // 非超级管理员，只能查询自己博物馆
            Long userId = SecurityUtil.getCurrentUserId();
            InstitutionAdmin admin = institutionAdminRepository.selectById(userId);
            Assert.notNull(admin, "管理员不存在");
            if (!admin.getInstitutionId().equals(question.getMuseumId())) {
                throw new ResourceNotFoundException("问题不存在");
            }
        }

        return BaseResponse.ok(toDto(question));
    }

    @ApiOperation(value = "获取提问列表", notes = "博物馆管理员和超级管理员可调用")
    @GetMapping("/list")
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<PageDTO<QuestionDTO>> listQuestion(QuestionQuery query) {
        if (!permissions.contains(STR_SYS_ADMIN)) {
            // 非超级管理员，只能查询自己博物馆
            Long userId = SecurityUtil.getCurrentUserId();
            InstitutionAdmin admin = institutionAdminRepository.selectById(userId);
            Assert.notNull(admin, "管理员不存在");
            query.setMuseumId(admin.getInstitutionId());
        }

        Page<RecommendQuestion> page = questionRepository.selectPage(query.toPage(), query.toQueryWrapper());
        List<QuestionDTO> questions = toDto(page.getRecords());
        return BaseResponse.ok(new PageDTO<>(questions, page.getTotal()));
    }

    @ApiOperation(value = "更新提问信息", notes = "博物馆管理员和超级管理员可调用")
    @PutMapping
    @PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
    public BaseResponse<?> updateQuestion(@RequestBody @Validated UpdateQuestionReq req) {
        RecommendQuestion question = questionRepository.selectById(req.getId());
        Assert.notNull(question, "问题不存在");
        if (!permissions.contains(STR_SYS_ADMIN)) {
            // 非超级管理员，验证权限
            Long userId = SecurityUtil.getCurrentUserId();
            InstitutionAdmin admin = institutionAdminRepository.selectById(userId);
            Assert.notNull(admin, "管理员不存在");
            if (!question.getMuseumId().equals(admin.getInstitutionId())) {
                throw new ForbiddenException("您没有权限这么做");
            }
            if (req.getExhibitId() != null) {
                Exhibit exhibit = exhibitService.getNotNullById(req.getExhibitId());
                if (!exhibit.getMuseumId().equals(admin.getInstitutionId())) {
                    throw new ForbiddenException("该展品不属于您的博物馆");
                }
            }
        } else {
            if (req.getExhibitId() != null) {
                Exhibit exhibit = exhibitService.getNotNullById(req.getExhibitId());
                Assert.isTrue(question.getMuseumId().equals(exhibit.getMuseumId()), "展品不属于问题所对应的博物馆");
            }
        }

        question.setAnswerText(req.getAnswerText());
        question.setAnswerType(req.getAnswerType());
        question.setExhibitId(req.getExhibitId());
        questionRepository.updateById(question);

        return BaseResponse.ok();
    }

    private QuestionDTO toDto(RecommendQuestion question) {
        if (question == null) {
            return null;
        }
        QuestionDTO dto = questionDTOMapper.toDto(question);

        Long museumId = question.getMuseumId();
        if (museumId != null) {
            Museum museum = adminMuseumService.getById(museumId);
            if (museum != null) {
                dto.setMuseumName(museum.getName());
            } else {
                log.warn("博物馆不存在，id={}", museumId);
            }
        }

        Long exhibitId = question.getExhibitId();
        if (exhibitId != null) {
            Exhibit exhibit = exhibitService.getById(exhibitId);
            if (exhibit != null) {
                dto.setExhibitName(exhibit.getLabel());

                Long hallId = exhibit.getHallId();
                ExhibitionHall hall = hallService.getById(hallId);
                dto.setHallId(hallId);
                dto.setHallName(hall == null ? "" : hall.getName());
            } else {
                log.warn("展品不存在，id={}", exhibitId);
            }
        }

        return dto;
    }

    private List<QuestionDTO> toDto(Collection<RecommendQuestion> questions) {
        if (questions == null) {
            return new ArrayList<>();
        }
        List<QuestionDTO> res = new ArrayList<>(questions.size());
        for (RecommendQuestion question : questions) {
            res.add(toDto(question));
        }
        return res;
    }
}
