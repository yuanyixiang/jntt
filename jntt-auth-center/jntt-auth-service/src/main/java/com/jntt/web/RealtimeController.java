package com.jntt.web;


import com.jntt.bean.RealTime;
import com.jntt.bean.TimeInfo;
import com.jntt.entity.UserInfo;
import com.jntt.pojo.SendBag;
import com.jntt.properties.JwtProperties;
import com.jntt.service.RealService;
import com.jntt.utils.CookieUtils;
import com.jntt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class RealtimeController {
    final static ArrayList<Object> realTimesInfo = new ArrayList<Object>();
    final static ArrayList<RealTime> realTimes = new ArrayList<RealTime>();
    final static ArrayList<TimeInfo> timeInfos = new ArrayList<TimeInfo>();



    @Autowired
    private JwtProperties props;

    @Autowired
    private RealService realService;


    @PostMapping("sendgift")
    public ResponseEntity<Void> putFlower(@CookieValue("JNTT_TOKEN") String token,
                                          @RequestParam("who") String who,
                                          @RequestParam("gifttype") int gifttype,
                                          HttpServletRequest request,
                                          HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        realService.putflower(name,who,gifttype);
        return ResponseEntity.ok().build();
    }

    @PostMapping("leavemsg")
    public ResponseEntity<Void> leavemsg(@CookieValue("JNTT_TOKEN") String token,
                                          @RequestParam("who") String who,
                                          @RequestParam("msg") String msg,
                                          HttpServletRequest request,
                                          HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        realService.leavemsg(name,who,msg);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getrealtimeInfo")
    public ResponseEntity<ArrayList<Object>> timeinfo(@RequestParam("who") String who){
        ArrayList<Object> timeinfo = realService.timeinfo(who);
        return ResponseEntity.ok(timeinfo);
    }

    @GetMapping("getbag")
    public ResponseEntity<ArrayList<SendBag>> sendBag(@CookieValue("JNTT_TOKEN") String token,HttpServletRequest request,
                                                      HttpServletResponse response){
        UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        String name = userInfo.getName();
        ArrayList<SendBag> sendBags = realService.sendBags(name);
        return ResponseEntity.ok(sendBags);
    }
}
