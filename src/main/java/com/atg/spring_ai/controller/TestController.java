package com.atg.spring_ai.controller;

import com.atg.spring_ai.common.BaseResponse;
import com.atg.spring_ai.common.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
author: atg
time: 2025/6/5 19:37
*/
@Tag(name = "测试接口", description = "测试相关接口")
@RestController
@RequestMapping("/text")
public class TestController {
    @Operation(summary = "健康检查", description = "用于检查服务是否正常运行")
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<String> test() {
        return ResultUtils.success("ok");
    }
}
