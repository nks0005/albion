package com.web.albion.Service;

import com.web.albion.Mapper.BattleMapper;
import com.web.albion.Mapper.BattleMatchGearLogMapper;
import com.web.albion.Mapper.GearsMapper;
import com.web.albion.Mapper.MatchCompMapper;
import com.web.albion.Model.BattleMatchGearLog;
import com.web.albion.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class MatchCompService {

    @Autowired
    BattleMatchGearLogMapper battlematchgearlogMapper;

    @Autowired
    MatchCompMapper matchCompMapper;

    @Autowired
    BattleMapper battlemapper;

    @Autowired
    GearsMapper gearsMapper;



    @Transactional
    public void updateMeta() {
        BattleMatchGearLog test = new BattleMatchGearLog();

        List<BattlesDto> battlesProcessZero = battlemapper.getBattlesInProcessZero();
        //System.out.println(battlesProcessZero);

        // join data 얻어오기
        for (BattlesDto battle : battlesProcessZero) {
            //System.out.println(battle.getProcess());

            List<BattleMatchGearLog> battlematchgearLogs = battlematchgearlogMapper.getBattleMatchGearLogs(battle.getBattle_id());
            //System.out.println(battlematchgearLogs);

            String match_type = battle.getMatch_type();

            List<Integer> win_gear_ids = new ArrayList<>();
            List<Integer> lose_gear_ids = new ArrayList<>();

            for (BattleMatchGearLog battlematchgearlog : battlematchgearLogs) {
                // main hand 다듬기
                String main_hand = battlematchgearlog.getOriginName(battlematchgearlog.getMain_hand());
                //System.out.println(main_hand);


                Integer gear_id = gearsMapper.selectGearByName(main_hand);
                if (gear_id == null) {
                    GearsDto geardto = new GearsDto();
                    geardto.setName(main_hand);
                    gearsMapper.insertGear(geardto);
                    gear_id = gearsMapper.selectGearByName(main_hand);
                    // gear_id가 null일경우 throw 해야할것
                }

                if (battlematchgearlog.getMatch_state().equals("win")) {
                    win_gear_ids.add(gear_id);
                } else {
                    lose_gear_ids.add(gear_id);
                }
            }


            //System.out.println("winner : " + win_gear_ids.toString());
            //System.out.println("losser : " + lose_gear_ids.toString());

            Collections.sort(win_gear_ids);
            Collections.sort(lose_gear_ids);

            //System.out.println("match_type : " + match_type);
            switch (match_type) {
                case "22":
                    DuoCompDto duocomp = new DuoCompDto();

                    // Winner
                    duocomp.setA_main_hand_id(win_gear_ids.get(0));
                    duocomp.setB_main_hand_id(win_gear_ids.get(1));

                    this.autoUpdateDuoComp(duocomp, "win");

                    // Losser
                    duocomp = new DuoCompDto();
                    duocomp.setA_main_hand_id(lose_gear_ids.get(0));
                    duocomp.setB_main_hand_id(lose_gear_ids.get(1));

                    this.autoUpdateDuoComp(duocomp, "lose");

                    break;
                case "55":

                    FiveCompDto fivecomp = new FiveCompDto();

                    // Winner
                    fivecomp.setA_main_hand_id(win_gear_ids.get(0));
                    fivecomp.setB_main_hand_id(win_gear_ids.get(1));
                    fivecomp.setC_main_hand_id(win_gear_ids.get(2));
                    fivecomp.setD_main_hand_id(win_gear_ids.get(3));
                    fivecomp.setE_main_hand_id(win_gear_ids.get(4));

                    this.autoUpdateFiveComp(fivecomp, "win");

                    // Losser
                    fivecomp = new FiveCompDto();
                    fivecomp.setA_main_hand_id(lose_gear_ids.get(0));
                    fivecomp.setB_main_hand_id(lose_gear_ids.get(1));
                    fivecomp.setC_main_hand_id(lose_gear_ids.get(2));
                    fivecomp.setD_main_hand_id(lose_gear_ids.get(3));
                    fivecomp.setE_main_hand_id(lose_gear_ids.get(4));

                    this.autoUpdateFiveComp(fivecomp, "lose");

                    break;
                case "1010":

                    TenCompDto tencomp = new TenCompDto();

                    // Winner
                    tencomp.setA_main_hand_id(win_gear_ids.get(0));
                    tencomp.setB_main_hand_id(win_gear_ids.get(1));
                    tencomp.setC_main_hand_id(win_gear_ids.get(2));
                    tencomp.setD_main_hand_id(win_gear_ids.get(3));
                    tencomp.setE_main_hand_id(win_gear_ids.get(4));
                    tencomp.setF_main_hand_id(win_gear_ids.get(5));
                    tencomp.setG_main_hand_id(win_gear_ids.get(6));
                    tencomp.setH_main_hand_id(win_gear_ids.get(7));
                    tencomp.setI_main_hand_id(win_gear_ids.get(8));
                    tencomp.setJ_main_hand_id(win_gear_ids.get(9));


                    this.autoUpdateTenComp(tencomp, "win");

                    // Losser
                    tencomp = new TenCompDto();
                    tencomp.setA_main_hand_id(lose_gear_ids.get(0));
                    tencomp.setB_main_hand_id(lose_gear_ids.get(1));
                    tencomp.setC_main_hand_id(lose_gear_ids.get(2));
                    tencomp.setD_main_hand_id(lose_gear_ids.get(3));
                    tencomp.setE_main_hand_id(lose_gear_ids.get(4));
                    tencomp.setF_main_hand_id(lose_gear_ids.get(5));
                    tencomp.setG_main_hand_id(lose_gear_ids.get(6));
                    tencomp.setH_main_hand_id(lose_gear_ids.get(7));
                    tencomp.setI_main_hand_id(lose_gear_ids.get(8));
                    tencomp.setJ_main_hand_id(lose_gear_ids.get(9));

                    this.autoUpdateTenComp(tencomp, "lose");

                    break;
            }

            battlemapper.updateProcessDone(battle.getBattle_id());
            System.out.println("Meta update : " + battle.getBattle_id());

        }
    }


    public void autoUpdateDuoComp(DuoCompDto duoComp, String state) {
        DuoCompDto tmp = new DuoCompDto();
        // SELECT
        tmp = matchCompMapper.selectDuoComp(duoComp);
        if (tmp == null) {
            duoComp.setWin(0);
            duoComp.setLose(0);
            matchCompMapper.insertDuoComp(duoComp);
            tmp = matchCompMapper.selectDuoComp(duoComp);
        }

        // update
        switch (state) {
            case "win":
                tmp.setWin(tmp.getWin() + 1);
                matchCompMapper.updateDuoComp(tmp);
                break;

            case "lose":
                tmp.setLose(tmp.getLose() + 1);
                matchCompMapper.updateDuoComp(tmp);
                break;
        }
    }

    public void autoUpdateFiveComp(FiveCompDto fiveComp, String state) {
        FiveCompDto tmp = new FiveCompDto();

        tmp = matchCompMapper.selectFiveComp(fiveComp);
        if (tmp == null) {
            fiveComp.setWin(0);
            fiveComp.setLose(0);

            matchCompMapper.insertFiveComp(fiveComp);
            tmp = matchCompMapper.selectFiveComp(fiveComp);
        }

        // update
        switch (state) {
            case "win":
                tmp.setWin(tmp.getWin() + 1);
                matchCompMapper.updateFiveComp(tmp);
                break;

            case "lose":
                tmp.setLose(tmp.getLose() + 1);
                matchCompMapper.updateFiveComp(tmp);
                break;
        }
    }

    public void autoUpdateTenComp(TenCompDto tenComp, String state) {
        TenCompDto tmp = new TenCompDto();

        tmp = matchCompMapper.selectTenComp(tenComp);
        if (tmp == null) {
            tenComp.setWin(0);
            tenComp.setLose(0);

            matchCompMapper.insertTenComp(tenComp);
            tmp = matchCompMapper.selectTenComp(tenComp);
        }

        // update
        switch (state) {
            case "win":
                tmp.setWin(tmp.getWin() + 1);
                matchCompMapper.updateTenComp(tmp);
                break;

            case "lose":
                tmp.setLose(tmp.getLose() + 1);
                matchCompMapper.updateTenComp(tmp);
                break;
        }
    }

}

/**
 * 1. battlelog_info 로 부터 process=0 인 값들을 가져온다.
 * 2. user_match_info JOIN 값을 가져온다
 * 3.
 * ```sql
 * SELECT bi.battle_id, bi.match_type, umi.match_state, bi.battle_time, main_hand_gi.name AS main_hand, off_hand_gi.name AS off_hand, head_gi.name AS head, armor_gi.name AS armor, shoes_gi.name AS shoes, cape_gi.name AS cape
 * FROM battlelog_info bi
 * JOIN user_match_info umi ON umi.battle_id = bi.battle_id
 * JOIN gearset_info gsi ON umi.gearset_id = gsi.id
 * JOIN gear_info main_hand_gi ON gsi.main_hand_id = main_hand_gi.id
 * JOIN gear_info off_hand_gi ON gsi.off_hand_id = off_hand_gi.id
 * JOIN gear_info head_gi ON gsi.head_id = head_gi.id
 * JOIN gear_info armor_gi ON gsi.armor_id = armor_gi.id
 * JOIN gear_info shoes_gi ON gsi.shoes_id = shoes_gi.id
 * JOIN gear_info cape_gi ON gsi.cape_id = cape_gi.id
 * WHERE bi.battle_id = 175628936;
 * <p>
 * <p>
 * ```
 */