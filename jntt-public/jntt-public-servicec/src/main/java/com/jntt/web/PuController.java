package com.jntt.web;

import com.jntt.Hero;
import com.jntt.Pu;
import com.jntt.service.PuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
@Slf4j
public class PuController {


    @Autowired
    private PuService puService ;

    @Autowired
    DataSource dataSource;

    @GetMapping("/ob")
    public ResponseEntity<List<Pu>> findById(@RequestParam("pid") Long pid){
        log.info(pid.toString());
        return ResponseEntity.ok(puService.findById(pid));
    }

    @GetMapping("getheros")
    public ResponseEntity<List<Hero>> findAll(){
        return ResponseEntity.ok(puService.findyAll());
    }

    @GetMapping("gethbyname")
    public ResponseEntity<Hero> findByName(@RequestParam("name") String name){
        return ResponseEntity.ok(puService.findByname(name));
    }

    @GetMapping("gethlikename")
    public ResponseEntity<List<Hero>> findLikeName(@RequestParam("name") String name){
        return ResponseEntity.ok(puService.findLikename(name));
    }

    @GetMapping("select")
    public void shuchu(){
        System.out.println(dataSource.getClass());
    }
}
