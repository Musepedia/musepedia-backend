package com.mimiter.mgs.admin.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creative")
@RequiredArgsConstructor
@Api(value = "文创API", tags = {"文创API"})
public class CulturalCreativeController {
}
