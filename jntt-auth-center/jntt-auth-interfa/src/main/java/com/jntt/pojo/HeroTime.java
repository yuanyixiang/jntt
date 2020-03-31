package com.jntt.pojo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "jntt_public_timeinfo")
@Data
public class HeroTime {
    private String username;
    private String who;
}
