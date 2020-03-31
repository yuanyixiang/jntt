package com.jntt.vo;

import com.jntt.enums.ExceptionEnums;
import lombok.Data;

/**
 * @author bystander
 * @date 2018/9/15
 *
 * 自定义异常结果类
 */

@Data
public class ExceptionResult {

    private int status;

    private String message;

    private long timestamp;

    public ExceptionResult(ExceptionEnums em) {
        this.status = em.value();
        this.message = em.message();
        this.timestamp = System.currentTimeMillis();
    }
}
