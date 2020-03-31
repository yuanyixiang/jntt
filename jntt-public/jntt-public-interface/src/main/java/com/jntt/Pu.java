package com.jntt;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "jntt_public")
public class Pu {

    @Id
    private Long id;

    private String name;

    private String location;

    private Float money;
}
