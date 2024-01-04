package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("usermatch")
public class UserMatchDto {
    int id;

    int battle_id;

    int ip;

    int user_id;

    int gearset_id;

    String match_state;


}
