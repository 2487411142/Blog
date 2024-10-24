package com.limemartini.controller;

import com.limemartini.annotation.SystemLog;
import com.limemartini.domain.ResponseResult;
import com.limemartini.domain.dto.MultipartDto;
import com.limemartini.enums.AppHttpCodeEnum;
import com.limemartini.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping
    @SystemLog(businessName = "upload an img")
    public ResponseResult uploadImg(MultipartDto file){
        if (file == null || file.getImg() == null || file.getImg().isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        return ResponseResult.okResult(uploadService.uploadImg(file));
    }
}
