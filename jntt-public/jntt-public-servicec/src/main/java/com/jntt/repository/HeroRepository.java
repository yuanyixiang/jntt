package com.jntt.repository;

import com.jntt.Hero;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface HeroRepository extends ElasticsearchRepository<Hero,Long> {
    public List<Hero> findAll();
    public Hero findHeroByName(String name);
    public List<Hero> findHeroByNameIsLike(String name);


}
