package com.jntt.exception;

import com.jntt.enums.ExceptionEnums;
import lombok.Getter;

@Getter
public class JnException extends RuntimeException{

    private ExceptionEnums exceptionEnums;

    public JnException(ExceptionEnums exceptionEnums) {
        this.exceptionEnums = exceptionEnums;
    }
}
