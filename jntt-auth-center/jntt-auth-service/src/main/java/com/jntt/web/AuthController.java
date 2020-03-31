package com.jntt.web;

import com.jntt.entity.UserInfo;
import com.jntt.enums.ExceptionEnums;
import com.jntt.exception.JnException;
import com.jntt.pojo.UserCenter;
import com.jntt.pojo.WaterReturn;
import com.jntt.properties.JwtProperties;
import com.jntt.service.AuthService;
import com.jntt.utils.CookieUtils;
import com.jntt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {



    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties props;


    @PostMapping("log")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        log.info(username+password);
        String token = authService.login(username, password);
        if (StringUtils.isBlank(token)) {
            throw new JnException(ExceptionEnums.USERNAME_OR_PASSWORD_ERROR);
        }
        //将Token写入cookie中
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), token);
        log.info(props.getCookieName()+token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("checklog")
    public ResponseEntity<UserInfo> checklog(@CookieValue("JNTT_TOKEN") String token, HttpServletRequest request, HttpServletResponse response){
        try {
            //从Token中获取用户信息
            UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
            //成功，刷新Token
            String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            //将新的Token写入cookie中，并设置httpOnly
            CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            //Token无效
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("changeinfo")
    public ResponseEntity<Void> ChangeUserCenter(@CookieValue("JNTT_TOKEN") String token, @Valid UserCenter userCenter, HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        authService.changeUserCenter(userCenter,name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getuserinfo")
    public ResponseEntity<UserCenter> selectUserCenter(@CookieValue("JNTT_TOKEN") String token, HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        UserCenter userCenter = authService.selectbyName(name);
        return ResponseEntity.ok(userCenter);
    }

    @PostMapping("sign")
    public ResponseEntity<Void> UserSign(@CookieValue("JNTT_TOKEN") String token, HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        authService.sign(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("putwater")
    public ResponseEntity<WaterReturn> PutWater(@RequestParam("usernamed") String usernamed,
                                                @CookieValue("JNTT_TOKEN") String token,
                                                @RequestParam("type") String type,
                                                HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String username = userInfo.getName();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:ss");
        String format = simpleDateFormat.format(date);
        String head = authService.putwater(username, usernamed, type,date);
        WaterReturn waterReturn = new WaterReturn();
        waterReturn.setAva(head);
        if(type.equals("1")){
        waterReturn.setActive("施肥");}
        else{
            waterReturn.setActive("浇水");
        }
        waterReturn.setName(username);
        waterReturn.setTime(format);
        return ResponseEntity.ok(waterReturn);
    }


}
