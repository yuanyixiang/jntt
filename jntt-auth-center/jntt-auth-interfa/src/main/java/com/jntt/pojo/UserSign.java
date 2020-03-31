package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "jntt_user_sign")
public class UserSign {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private String signtime;
    private Integer continuity;
}
