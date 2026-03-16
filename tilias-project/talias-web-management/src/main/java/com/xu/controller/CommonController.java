package com.xu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xu.result.Result;
import com.xu.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws ClientException, IOException {
        log.info("文件上传{}", file);
        if(file.isEmpty()){
            return Result.error("文件上传失败");
        }
        String url = aliOssUtil.upload(file);
        return Result.success(url);
    }
}
