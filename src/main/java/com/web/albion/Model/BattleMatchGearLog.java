package com.web.albion.Model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BattleMatchGearLog {
    int battle_id;

    String match_type;
    String match_state;

    Timestamp battle_time;

    String main_hand;
    String off_hand;
    String head;
    String armor;
    String shoes;
    String cape;

    public String getOriginName(String item_name){
        String tmp = item_name;

        if(tmp.charAt(0) == 'T'){
            int index = tmp.indexOf('_');
            if(index != -1){
                tmp = tmp.substring(index+1);
            }
        }

        int atIndex = tmp.indexOf('@');
        if(atIndex != -1){
            tmp = tmp.substring(0, atIndex);
        }

        return tmp;
    }
}
