package com.jntt.service;

import com.jntt.bean.RealTime;
import com.jntt.bean.TimeInfo;
import com.jntt.enums.ExceptionEnums;
import com.jntt.exception.JnException;
import com.jntt.mapper.HeroFlowerMapper;
import com.jntt.mapper.HeroTimeMapper;
import com.jntt.mapper.LeaveMsgMapper;
import com.jntt.mapper.UserBagMapper;
import com.jntt.pojo.*;
import com.jntt.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class RealService {
    @Autowired
    private JwtProperties props;

    @Autowired
    private HeroFlowerMapper heroFlowerMapper;

    @Autowired
    private LeaveMsgMapper leaveMsgMapper;

    @Autowired
    private HeroTimeMapper heroTimeMapper;

    @Autowired
    private UserBagMapper userBagMapper;

    public void putflower(String username,String who,int gifttype){
            UserBag userBag = new UserBag();
            userBag.setUsername(username);
            userBag.setType(gifttype);
        UserBag bag = userBagMapper.selectOne(userBag);
        if(bag.getCount()!=0){
            HeroFlower heroFlower = new HeroFlower();
            heroFlower.setId(null);
            heroFlower.setUsername(username);
            heroFlower.setWho(who);
            heroFlower.setGifttype(gifttype);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:ss");
            String format = simpleDateFormat.format(date);
            heroFlower.setTime(format);
            int insert = heroFlowerMapper.insert(heroFlower);
            if(insert != 1){
                throw new JnException(ExceptionEnums.GIFT_FAIL);
            }
            bag.setCount(bag.getCount()-1);
            userBagMapper.updateByPrimaryKey(bag);
            HeroTime heroTime = new HeroTime();
            heroTime.setUsername(username);
            heroTime.setWho(who);
            int i = heroTimeMapper.selectCount(heroTime);
            if(i == 0){
                int insert1 = heroTimeMapper.insert(heroTime);
                if(insert1 != 1){
                    throw new JnException(ExceptionEnums.GIFT_FAIL);
                }
            }
            else new JnException(ExceptionEnums.GIFT_FAIL);
        }



    }

    public void leavemsg(String username,String who,String msg){
        LeaveMsg leaveMsg = new LeaveMsg();
        leaveMsg.setId(null);
        leaveMsg.setMsg(msg);
        leaveMsg.setUsername(username);
        leaveMsg.setWho(who);
        int insert = leaveMsgMapper.insert(leaveMsg);
        if(insert != 1){
            throw new JnException(ExceptionEnums.MSG_FAIL);
        }
        HeroTime heroTime = new HeroTime();
        heroTime.setUsername(username);
        heroTime.setWho(who);
        int i = heroTimeMapper.selectCount(heroTime);
        if(i == 0){
            int insert1 = heroTimeMapper.insert(heroTime);
            if(insert1 != 1){
                throw new JnException(ExceptionEnums.GIFT_FAIL);
            }
        }
    }

    public ArrayList<Object> timeinfo(String who){
        HeroTime heroTime = new HeroTime();
        heroTime.setWho(who);
        LeaveMsg leaveMsg = new LeaveMsg();
        leaveMsg.setWho(who);
        int leavecount = leaveMsgMapper.selectCount(leaveMsg);
        int timecount = heroTimeMapper.selectCount(heroTime);

        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setMsgCount(leavecount);
        timeInfo.setPersonCount(timecount);
        ArrayList<TimeInfo> timeInfos = new ArrayList<TimeInfo>();
        timeInfos.add(timeInfo);
        RealTime realTime = new RealTime();
        ArrayList<RealTime> realTimes = new ArrayList<RealTime>();
        HeroFlower heroFlower = new HeroFlower();
        heroFlower.setWho(who);
        List<HeroFlower> select = heroFlowerMapper.select(heroFlower);
        ArrayList<Object> objects  = new ArrayList<Object>();
        objects.add(timeInfos);
        if(!CollectionUtils.isEmpty(select)) {
            for (HeroFlower hero : select
            ) {
                if (hero.getGifttype() == 1) {
                    realTime.setItem("一束花");
                    realTime.setName(hero.getUsername());
                    realTime.setTime(hero.getTime());
                    realTimes.add(realTime);
                } else if (hero.getGifttype() == 2) {
                    realTime.setItem("一根蜡烛");
                    realTime.setName(hero.getUsername());
                    realTime.setTime(hero.getTime());
                    realTimes.add(realTime);
                }
                else if (hero.getGifttype() == 3){
                    realTime.setItem("一盘水果");
                    realTime.setName(hero.getUsername());
                    realTime.setTime(hero.getTime());
                    realTimes.add(realTime);
                }
                else if (hero.getGifttype() == 4){
                    realTime.setItem("一捆钱");
                    realTime.setName(hero.getUsername());
                    realTime.setTime(hero.getTime());
                    realTimes.add(realTime);
                }
                else if (hero.getGifttype() == 5){
                    realTime.setItem("一串鞭炮");
                    realTime.setName(hero.getUsername());
                    realTime.setTime(hero.getTime());
                    realTimes.add(realTime);
                }
            }
            objects.add(realTimes);
            heroFlowerMapper.delete(heroFlower);
        }
        return objects;
    }


    public ArrayList<SendBag> sendBags(String username){
        UserBag userBag = new UserBag();
        userBag.setUsername(username);
        List<UserBag> select = userBagMapper.select(userBag);
        ArrayList<SendBag> sendBags = new ArrayList<SendBag>();

        Iterator<UserBag> iterator = select.iterator();
        while(iterator.hasNext())
        {
            SendBag sendBag = new SendBag();
            UserBag user = iterator.next();
            sendBag.setCount(user.getCount());
            sendBag.setName(user.getName());
            sendBag.setSrc(user.getSrc());
            sendBag.setType(user.getType());
            sendBags.add(sendBag);
        }
        return sendBags;
    }

    /*
    * 每天1点执行一次*/
    @Scheduled(cron = "0 0 1 * * ?")
    public void addbag(){
        UserBag userBag = new UserBag();
        userBag.setName("花");
        List<UserBag> select = userBagMapper.select(userBag);
        for (UserBag user:select
        ) {
            user.setCount(user.getCount()+1);
            userBagMapper.updateByPrimaryKey(user);
        }
        UserBag userBag1 = new UserBag();
        userBag1.setName("蜡烛");
        List<UserBag> select1 = userBagMapper.select(userBag1);
        for (UserBag user:select1
        ) {
            user.setCount(user.getCount()+1);
            userBagMapper.updateByPrimaryKey(user);
        }
    }


}
