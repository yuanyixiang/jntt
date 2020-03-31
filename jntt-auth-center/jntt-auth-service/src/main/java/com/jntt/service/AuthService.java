package com.jntt.service;

import com.jntt.client.UserClient;
import com.jntt.entity.UserInfo;
import com.jntt.enums.ExceptionEnums;
import com.jntt.exception.JnException;
import com.jntt.mapper.UserCenterMapper;
import com.jntt.mapper.UserSignMapper;
import com.jntt.mapper.UserWaterMapper;
import com.jntt.pojo.User;
import com.jntt.pojo.UserCenter;
import com.jntt.pojo.UserSign;
import com.jntt.pojo.UserWater;
import com.jntt.properties.JwtProperties;
import com.jntt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties props;

    @Autowired
    private UserCenterMapper userCenterMapper;

    @Autowired
    private UserSignMapper userSignMapper;

    @Autowired
    private UserWaterMapper userWaterMapper;

    public String login(String username, String password) {
        try {
            User user = userClient.queryUser(username, password);
            if (user == null) {
                return null;
            }
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
            //生成Token
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            return token;
        } catch (Exception e) {
            log.error("【授权中心】用户名和密码错误用户名：{}", username, e);
            throw new JnException(ExceptionEnums.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    public void changeUserCenter(UserCenter userCenter, String name){
        UserCenter userCenter1 = new UserCenter();
        userCenter1.setUsername(name);
        userCenter1 = userCenterMapper.selectOne(userCenter1);
        userCenter.setId(userCenter1.getId());
        userCenter.setUsername(userCenter1.getUsername());
        userCenterMapper.updateByPrimaryKey(userCenter);
    }

    public UserCenter selectbyName(String name){
        UserCenter userCenter1 = new UserCenter();
        userCenter1.setUsername(name);
        userCenter1 = userCenterMapper.selectOne(userCenter1);
        return userCenter1;
    }

    public void sign(String name){
        UserSign userSign = new UserSign();
        userSign.setContinuity(null);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        userSign.setSigntime(format);
        userSign.setUsername(name);
        List<UserSign> select = userSignMapper.select(userSign);
        if(!select.isEmpty()){
            throw new JnException(ExceptionEnums.HAS_SIGN);
        }
        userSignMapper.insert(userSign);
    }

    public String putwater(String username,String usernamed,String type,Date date){
        UserWater userWater = new UserWater();
        userWater.setUsername(username);
        userWater.setUsernamed(usernamed);
        if(type.equals("1")){
            userWater.setApplytime(date);
        }else {
        userWater.setWatertime(date);}
        userWaterMapper.insert(userWater);
        UserCenter userCenter = new UserCenter();
        userCenter.setUsername(username);
        userCenter = userCenterMapper.selectOne(userCenter);
        return userCenter.getHead();
    }


    public void delete(){
        UserWater userWater = new UserWater();
        userWater.setUsername("yuan");
        userWaterMapper.delete(userWater);

    }

}
