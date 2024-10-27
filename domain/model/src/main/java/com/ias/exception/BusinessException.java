package com.ias.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(BusinessEventErrorCode businessEventErrorCode) {
        super(businessEventErrorCode.getMessage());
        this.code = businessEventErrorCode.getCode();
    }
}

