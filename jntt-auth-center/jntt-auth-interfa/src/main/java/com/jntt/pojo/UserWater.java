package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "jntt_user_water")
public class UserWater {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private String usernamed;
    private Date watertime;
    private Date applytime;
}
