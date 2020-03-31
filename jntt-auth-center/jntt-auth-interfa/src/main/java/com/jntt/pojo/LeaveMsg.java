package com.jntt.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jntt_public_leavmsg")
@Data
public class LeaveMsg {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String username;
    private String who;
    private String msg;
}
