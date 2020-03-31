package com.jntt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "jntt_user")
@Data
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Length(max = 10, min = 2, message = "用户名长度只能在2-10之间")
    private String username;

    @JsonIgnore
    @Length(max = 16, min = 4, message = "密码长度只能在4-30之间")
    private String password;

    @Pattern(regexp = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$" , message = "邮箱格式正确" )
    private String email;

    @Pattern(regexp = "^1[35678]\\d{9}$",message = "手机号格式不正确")
    private String tel;

    private String sex;

    private Date created;

    @JsonIgnore
    private String salt;
}
