package com.jntt.web;

import com.jntt.pojo.User;
import com.jntt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /*用户注册*/
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user) {
        userService.register(user);
        userService.addusercenter(user);
        userService.addUserBag(user);
        log.info(user.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.queryUser(username, password));
    }



}
