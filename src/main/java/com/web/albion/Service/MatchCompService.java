package com.web.albion.Service;

import com.web.albion.Mapper.BattleMatchGearLogMapper;
import com.web.albion.Mapper.MatchCompMapper;
import com.web.albion.Model.BattleMatchGearLog;
import com.web.albion.dto.DuoCompDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MatchCompService {

    @Autowired
    BattleMatchGearLogMapper battlematchgearlogMapper;

    @Autowired
    MatchCompMapper matchCompMapper;

    public void test(){
        DuoCompDto duocomp = new DuoCompDto();

        duocomp.setA_main_hand_id(380);
        duocomp.setB_main_hand_id(212);

        duocomp.setWin(1);
        duocomp.setLose(1);

        System.out.println(duocomp);

        matchCompMapper.insertDuoComp(duocomp);

        BattleMatchGearLog test = new BattleMatchGearLog();
        //System.out.println(BattleMatchGearLog.getOriginName("T8_Helloworld@2"));

        //System.out.println(BattleMatchGearLog.getOriginName("T8_Helloworld"));

        //System.out.println(BattleMatchGearLog.getOriginName("Helloworld"));

        List<BattleMatchGearLog> battlematchgearLogs = battlematchgearlogMapper.getBattleMatchGearLogs(175628936);
        System.out.println(battlematchgearLogs);

        



    }

}

/**
 * 1. battlelog_info 로 부터 process=0 인 값들을 가져온다.
 * 2. user_match_info JOIN 값을 가져온다
 * 3.
 * ```sql
 SELECT bi.battle_id, bi.match_type, umi.match_state, bi.battle_time, main_hand_gi.name AS main_hand, off_hand_gi.name AS off_hand, head_gi.name AS head, armor_gi.name AS armor, shoes_gi.name AS shoes, cape_gi.name AS cape
 FROM battlelog_info bi
 JOIN user_match_info umi ON umi.battle_id = bi.battle_id
 JOIN gearset_info gsi ON umi.gearset_id = gsi.id
 JOIN gear_info main_hand_gi ON gsi.main_hand_id = main_hand_gi.id
 JOIN gear_info off_hand_gi ON gsi.off_hand_id = off_hand_gi.id
 JOIN gear_info head_gi ON gsi.head_id = head_gi.id
 JOIN gear_info armor_gi ON gsi.armor_id = armor_gi.id
 JOIN gear_info shoes_gi ON gsi.shoes_id = shoes_gi.id
 JOIN gear_info cape_gi ON gsi.cape_id = cape_gi.id
 WHERE bi.battle_id = 175628936;


 * ```

 *
 *
 *
 */