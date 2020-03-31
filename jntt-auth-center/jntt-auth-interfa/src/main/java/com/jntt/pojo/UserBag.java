package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jntt_public_bag")
@Data
public class UserBag {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private Integer count;
    private String src;
    private String name;
    private Integer type;
}
