package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Alias("view_battledetail")
public class BattleDetailDto {
    int battle_id;

    String username;

    int ip;

    String match_state;

    String main_hand_name;

    String off_hand_name;

    String head_name;

    String armor_name;

    String shoes_name;

    String cape_name;

    String match_type;

    Timestamp battle_time;
}
