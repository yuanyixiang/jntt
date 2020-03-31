package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jntt_public_flower")
@Data
public class HeroFlower {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private String who;
    private Integer gifttype;
    private String time;
}
