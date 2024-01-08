package com.web.albion.Service;

import com.web.albion.Model.Battle;
import com.web.albion.Model.BattleDetail;
import com.web.albion.dto.BattlesDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService {

    @Autowired
    BattleDetailService battledetailservice;

    @Autowired
    BattleService battleservice;

    public BattleDetail getBattleDetailByBattleId(@Param("battleId") int battleId){

        BattleDetail battledetail = new BattleDetail();

        // check if have battleDto
        // test
        BattlesDto battledto = battleservice.getBattleByBattleId(battleId);
        if(battledto == null){
            return null;
        }

        battledetail.setBattle(battledto);

        battledetail.setWinners_detail(battledetailservice.getBattleDetailWinnerByBattleId(battledetail.getBattle().getBattle_id()));
        battledetail.setLossers_detail(battledetailservice.getBattleDetailLosserByBattleId(battledetail.getBattle().getBattle_id()));

        return battledetail;
    }
}
