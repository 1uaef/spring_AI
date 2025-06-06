package com.atg.spring_ai.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/*
author: atg
time: 2025/6/6 18:36
*/
@Data
public class ImageRequest implements Serializable {


    private String imagePrompt ;

    private int imageCount ;

    @Serial
    private static final long serialVersionUID = 8735650154179439661L;
}
