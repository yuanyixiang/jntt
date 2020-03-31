package com.jntt.service;

import com.jntt.enums.ExceptionEnums;
import com.jntt.exception.JnException;
import com.jntt.mapper.UserBagMapper;
import com.jntt.mapper.UserCenterMapper;
import com.jntt.mapper.UserMapper;
import com.jntt.pojo.User;
import com.jntt.pojo.UserBag;
import com.jntt.pojo.UserCenter;
import com.jntt.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCenterMapper userCenterMapper;

    @Autowired
    private UserBagMapper userBagMapper;

    public void register(User user) {

//        User user1 = new User();
//        user1.setUsername(user.getUsername());
//        List<User> select = userMapper.select(user1);
//        if(!CollectionUtils.isEmpty(select)){
//            throw new JnException(ExceptionEnums.USER_EXIST);
//        }
        user.setId(null);
        user.setCreated(new Date());
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        String md5Pwd = CodecUtils.md5Hex(user.getPassword(), user.getSalt());
        user.setPassword(md5Pwd);
        int count = userMapper.insert(user);

        if (count != 1) {
            throw new JnException(ExceptionEnums.INVALID_PARAM);
        }

    }
    public void addusercenter(User user){
        UserCenter userCenter = new UserCenter();
        userCenter.setHead("http://39.107.56.242/group1/M00/00/00/rBF2h16AgzOAcMJYAAPgwGOwMMw961.PNG");
        userCenter.setId(null);
        userCenter.setIntroduce(null);
        userCenter.setUsername(user.getUsername());
        log.info(user.getUsername());
        userCenter.setName(user.getUsername());
        userCenter.setNote(null);
        userCenter.setPhoto1(null);
        userCenter.setPhoto2(null);
        userCenter.setPhoto3(null);
        userCenter.setPhoto4(null);
        log.info(userCenter.toString());
        System.out.println(userCenter.toString());
        int insert = userCenterMapper.insert(userCenter);
        if (insert != 1) {
            throw new JnException(ExceptionEnums.USER_CENT_FAIL);
        }
    }

    public void addUserBag(User user){
        {UserBag userBag = new UserBag();
        userBag.setUsername(user.getUsername());
        userBag.setId(null);
        userBag.setType(1);
        userBag.setCount(1);
        userBag.setName("花");
        userBag.setSrc("http://39.107.56.242/group1/M00/00/00/rBF2h16AtlGAcWT3AAFUvNNHxoQ573.png");
        userBagMapper.insert(userBag);
            System.out.println(userBag);}
        {UserBag userBag = new UserBag();
        userBag.setUsername(user.getUsername());
        userBag.setId(null);
        userBag.setCount(1);
        userBag.setType(2);
        userBag.setName("蜡烛");
        userBag.setSrc("http://39.107.56.242/group1/M00/00/00/rBF2h16AtjKAcc9eAADIjwZRwlA990.png");
        userBagMapper.insert(userBag);}
        {        UserBag userBag = new UserBag();
            userBag.setUsername(user.getUsername());
            userBag.setId(null);
            userBag.setCount(1);
        userBag.setType(3);
        userBag.setName("供果");
        userBag.setSrc("http://39.107.56.242/group1/M00/00/00/rBF2h16AtmWAG2IuAAFbRTU1EpU474.png");
        userBagMapper.insert(userBag);}
        {        UserBag userBag = new UserBag();
            userBag.setUsername(user.getUsername());
            userBag.setId(null);
            userBag.setCount(1);
        userBag.setType(4);
        userBag.setName("纸钱");
        userBag.setSrc("http://39.107.56.242/group1/M00/00/00/rBF2h16AtnGAM0uXAAFeYFmFgQg803.png");
        userBagMapper.insert(userBag);}
        {        UserBag userBag = new UserBag();
            userBag.setUsername(user.getUsername());
            userBag.setId(null);
            userBag.setCount(1);
        userBag.setType(5);
        userBag.setName("鞭炮");
        userBag.setSrc("http://39.107.56.242/group1/M00/00/00/rBF2h16AtnyADTyIAAEGEnuwEnY549.png");}
    }

    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        //首先根据用户名查询用户
        User user = userMapper.selectOne(record);

        if (user == null) {
            throw new JnException(ExceptionEnums.USER_NOT_EXIST);
        }

        //检验密码是否正确
        if (!StringUtils.equals(CodecUtils.md5Hex(password, user.getSalt()), user.getPassword())) {
            //密码不正确
            throw new JnException(ExceptionEnums.PASSWORD_NOT_MATCHING);
        }

        return user;
    }
}
