package com.jntt.api;

import com.jntt.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface UserAPi {
    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    void register(@Valid User user, @RequestParam("code")String code) ;

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
