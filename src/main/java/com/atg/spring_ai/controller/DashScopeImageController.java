package com.atg.spring_ai.controller;


import cn.hutool.core.util.StrUtil;
import com.atg.spring_ai.common.BaseResponse;
import com.atg.spring_ai.common.ResultUtils;
import com.atg.spring_ai.exception.BusinessException;
import com.atg.spring_ai.exception.ErrorCode;
import com.atg.spring_ai.model.dto.ImageRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        ImageResponse imageResponse = imageModel.call(prompt);
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

    /**
     * 返回生成多张图片URL
     * @param imageRequest
     * @return
     */
    @PostMapping("/generate/multiImage")
    public BaseResponse<Set<String>> generateMultiImage(@RequestBody ImageRequest imageRequest) {
        String imagePrompt = imageRequest.getImagePrompt();
        int imageCount = imageRequest.getImageCount();

        if (imageCount < 1 || imageCount > 10) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (imagePrompt != null && StrUtil.isBlank(imagePrompt)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,  "请输入图片描述提示词");
        }

        ImageOptions imageOptions = ImageOptionsBuilder.builder().N(imageCount).build();
//       # ImagePrompt 传递参数是 -- String 类型和 -- ImageOptions 类型
        ImagePrompt result = new ImagePrompt(imagePrompt, imageOptions);
        ImageResponse imageResponse = imageModel.call(result);
        Set<String> collect = imageResponse.getResults().
                stream().map(res -> res.getOutput().getUrl())
                .collect(Collectors.toSet());
        return ResultUtils.success(collect);

    }
}



