package com.web.albion.Service;

import com.web.albion.Mapper.BattleMapper;
import com.web.albion.dto.Battles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    @Autowired
    private BattleMapper battlemapper;

    public int insertBattle(Battles battle){
        if(battlemapper.checkBattleExists(battle.getBattle_id()) == 0){
            System.out.println("no have : "+ battle.getBattle_id());
            return battlemapper.insertBattle(battle);
        } else{
            System.out.println("have : "+ battle.getBattle_id());
        }
        return 0;
    };

    public int checkBattleExists(int battle_id){
        return battlemapper.checkBattleExists(battle_id);
    }
}
