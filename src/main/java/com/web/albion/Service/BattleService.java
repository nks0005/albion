package com.web.albion.Service;

import com.web.albion.Mapper.BattleMapper;
import com.web.albion.Mapper.GearsMapper;
import com.web.albion.Model.Battle;
import com.web.albion.dto.Battles;
import com.web.albion.dto.Gears;
import com.web.albion.dto.GearsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BattleService {

    @Autowired
    private BattleMapper battlemapper;

    @Autowired
    private GearsService gearsservice;

    @Autowired
    private GearSetService gearsetservice;

    @Transactional
    public int insertBattle(Battle battle) {
        if (battlemapper.checkBattleExists(battle.getBattle_id()) == 0) {
            System.out.println("no have : " + battle.getBattle_id());


            int result = 0;

            try {
                Battles battles = getBattles(battle);
                result = battlemapper.insertBattle(battles);



            } catch (ParseException e) {
                e.printStackTrace();
            }


            return result;
        } else {
            System.out.println("have : " + battle.getBattle_id());
        }
        return 0;
    }

    ;

    public int checkBattleExists(int battle_id) {
        return battlemapper.checkBattleExists(battle_id);
    }

    private static Battles getBattles(Battle battle) throws ParseException {
        Battles battles = new Battles();
        battles.setBattle_id(battle.getBattle_id());

        String match_type = null;
        switch (battle.getMatch_type()) {
            case 2:
                match_type = "22";
                break;
            case 5:
                match_type = "55";
                break;
            case 10:
                match_type = "1010";
                break;
        }
        battles.setMatch_type(match_type);

        String battle_time = battle.getBattle_time();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = format.parse(battle_time);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        battles.setBattle_time(timestamp);
        return battles;
    }
}
