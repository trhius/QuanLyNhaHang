package com.project3.quanlynhahang.config;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
