package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "jntt_user_center")
public class UserCenter {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private String name;
    private String head;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String introduce;
    private String note;
}
