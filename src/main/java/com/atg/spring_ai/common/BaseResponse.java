package com.atg.spring_ai.common;
import com.atg.spring_ai.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/*
author: atg
time: 2025/6/5 19:32
*/

// 全局响应封装类

@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
