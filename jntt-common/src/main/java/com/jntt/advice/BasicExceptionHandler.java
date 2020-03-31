package com.jntt.advice;

import com.jntt.exception.JnException;
import com.jntt.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {
    @ExceptionHandler(JnException.class)
    public ResponseEntity<ExceptionResult> handleException(JnException e) {
        System.out.println("我被执行了");
        return ResponseEntity.status(e.getExceptionEnums().value())
                .body(new ExceptionResult(e.getExceptionEnums()));
    }

}
