package com.web.albion.Thread;


import com.web.albion.Mapper.BattleMapper;
import com.web.albion.Model.Battle;
import com.web.albion.Service.BattleService;
import com.web.albion.dto.Battles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 크롤러 기능을 총괄하는 함수입니다.
 * <p>
 * 1. Battle Id + Type 얻어오기
 * 2.
 */
@Component
public class MainProcess {

    @Autowired
    private BattleLog battlelog;


    @Autowired
    private BattleService battleservice;

    public MainProcess() {

    }

    public void run() {
        List<Battle> battlelogs = battlelog.getBattleIds(0);

        for (Battle battle : battlelogs) {
            System.out.println("========================");
            System.out.println(battle.getBattle_id());
            System.out.println(battle.getMatch_type());
            System.out.println(battle.getBattle_time());
            System.out.println("| Winners");
            System.out.println(battle.getWinners().toString());
            System.out.println("| Lossers");
            System.out.println(battle.getLossers().toString());
            System.out.println("========================");



            try {
                Battles battles = getBattles(battle);

                battleservice.insertBattle(battles);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }



    }

    private static Battles getBattles(Battle battle) throws ParseException {
        Battles battles = new Battles();
        battles.setBattle_id(battle.getBattle_id());

        String match_type = null;
        switch(battle.getMatch_type()){
            case 2:
                match_type="22";
                break;
            case 5:
                match_type="55";
                break;
            case 10:
                match_type="1010";
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
