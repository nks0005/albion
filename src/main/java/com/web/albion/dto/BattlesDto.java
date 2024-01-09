package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("battles")
public class BattlesDto {
    private int id;
    private int battle_id;
    private String match_type;
    private Timestamp battle_time;
    private int process;
}
