package com.atg.spring_ai.controller;


import cn.hutool.core.util.StrUtil;
import com.atg.spring_ai.common.BaseResponse;
import com.atg.spring_ai.common.ResultUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/*
author: atg
time: 2025/6/4 20:27
*/
@RestController
@RequestMapping("/dashscope/image")
public class DashScopeImageController {

    private final ImageModel imageModel;

    // promote
    private final String PROMOTE = "为努力学习的自己生成一张励志海报";

    public DashScopeImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    /**
     * 返回生成图片URL
     * @return
     */
    @GetMapping("/generateImage")
    public BaseResponse<String> generateImage() {
        ImagePrompt prompt = new ImagePrompt(PROMOTE);
        ImageResponse imageResponse = imageModel.call(prompt); // 可能返回一个任务 ID
        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        if (StrUtil.isEmpty(imageUrl)) {
            throw new RuntimeException("Image generation timeout or failed");
        }

        return ResultUtils.success(imageUrl);
    }


    /**
     * 返回显示图片
     * @param response
     */
    @GetMapping("/image")
    public void image(HttpServletResponse response) {

        ImageResponse imageResponse = imageModel.call(new ImagePrompt(PROMOTE));
        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        try {
            URL url = URI.create(imageUrl).toURL();
            InputStream in = url.openStream();

            response.setHeader("Content-Type", MediaType.IMAGE_PNG_VALUE);
            response.getOutputStream().write(in.readAllBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}



