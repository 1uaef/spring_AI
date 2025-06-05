package com.atg.spring_ai.controller;


import org.springframework.ai.image.ImageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/generateImage")
    public void generateImage() {

    }

}
