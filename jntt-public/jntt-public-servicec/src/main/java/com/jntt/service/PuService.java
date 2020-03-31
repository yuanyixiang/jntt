package com.jntt.service;

import com.jntt.enums.ExceptionEnums;
import com.jntt.exception.JnException;
import com.jntt.mapper.PuMapper;
import com.jntt.Hero;
import com.jntt.Pu;
import com.jntt.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PuService {
    @Autowired
    private PuMapper puMapper;

    @Autowired
    private HeroRepository heroRepository;

    public List<Pu> findById(Long id) {
        Pu pu = new Pu();
        pu.setId(id);
        List<Pu> select = puMapper.select(pu);
        if(CollectionUtils.isEmpty(select)){
            throw new JnException(ExceptionEnums.Pu_NOT_EXIST);
        }
        return select;
    }

    public List<Hero> findyAll(){
        List<Hero> hero = heroRepository.findAll();
        return hero;
    }

    public Hero findByname(String name){
        Hero hero = heroRepository.findHeroByName(name);
        return hero;
    }

    public List<Hero> findLikename(String name){
        List<Hero> herolist = heroRepository.findHeroByNameIsLike(name);
        return herolist;
    }
}
