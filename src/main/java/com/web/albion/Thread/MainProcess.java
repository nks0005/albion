package com.web.albion.Thread;


import com.web.albion.Model.Battle;
import com.web.albion.Service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


            battleservice.insertBattle(battle);


        }
    }


}
