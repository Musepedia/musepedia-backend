package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.config.CosConfig;
import com.mimiter.mgs.common.exception.InternalException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Api(value = "上传文件API", tags = {"上传文件API"})
public class FileController {

    private final CosConfig cosConfig;

    private final COSClient cosClient;

    @ApiOperation(value = "上传文件", notes = "登录用户均可调用")
    @PostMapping
    public BaseResponse<String> upload(@RequestBody MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        File localFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
        try {
            file.transferTo(localFile);
            // 指定文件上传到 COS 上的路径，即对象键。
            // 例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBucket(), key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String returnUrl = "https://" + cosConfig.getBucket() + ".cos." + cosConfig.getRegion() + ".myqcloud.com/" + key;
            return BaseResponse.ok("ok", returnUrl);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new InternalException("上传文件失败");
        } finally {
            localFile.delete();
        }
    }
}
