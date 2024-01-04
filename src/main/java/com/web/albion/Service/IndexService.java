package com.web.albion.Service;


import com.web.albion.Model.Battle;
import com.web.albion.Model.BattleDetail;
import com.web.albion.dto.BattleDetailDto;
import com.web.albion.dto.BattlesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    BattleDetailService battledetailservice;

    @Autowired
    BattleService battleservice;


    /**
     * 최신 로그 10개
     * 각 로그에 BattleDetailDto
     */
    public List<BattleDetail> getRecentBattles(int offset) {
        List<BattleDetail> battledetails = new ArrayList<BattleDetail>();

        List<BattlesDto> battles = battleservice.getRecentBattles(offset);

        for (BattlesDto battle : battles) {
            BattleDetail battledetail = new BattleDetail();

            battledetail.setBattle(battle);

            battledetail.setWinners_detail(battledetailservice.getBattleDetailWinnerByBattleId(battle.getBattle_id()));
            battledetail.setLossers_detail(battledetailservice.getBattleDetailLosserByBattleId(battle.getBattle_id()));

            battledetails.add(battledetail);
        }

        return battledetails;
    }

    public List<BattleDetail> getDuoBattles(int offset){
        List<BattleDetail> battledetails = new ArrayList<BattleDetail>();

        List<BattlesDto> battles = battleservice.getBattlesDuo(offset);

        for (BattlesDto battle : battles) {
            BattleDetail battledetail = new BattleDetail();

            battledetail.setBattle(battle);

            battledetail.setWinners_detail(battledetailservice.getBattleDetailWinnerByBattleId(battle.getBattle_id()));
            battledetail.setLossers_detail(battledetailservice.getBattleDetailLosserByBattleId(battle.getBattle_id()));

            battledetails.add(battledetail);
        }

        return battledetails;
    }

    public List<BattleDetail> getFiveBattles(int offset){
        List<BattleDetail> battledetails = new ArrayList<BattleDetail>();

        List<BattlesDto> battles = battleservice.getBattlesFive(offset);

        for (BattlesDto battle : battles) {
            BattleDetail battledetail = new BattleDetail();

            battledetail.setBattle(battle);

            battledetail.setWinners_detail(battledetailservice.getBattleDetailWinnerByBattleId(battle.getBattle_id()));
            battledetail.setLossers_detail(battledetailservice.getBattleDetailLosserByBattleId(battle.getBattle_id()));

            battledetails.add(battledetail);
        }

        return battledetails;
    }

    public List<BattleDetail> getTenBattles(int offset){
        List<BattleDetail> battledetails = new ArrayList<BattleDetail>();

        List<BattlesDto> battles = battleservice.getBattlesTen(offset);

        for (BattlesDto battle : battles) {
            BattleDetail battledetail = new BattleDetail();

            battledetail.setBattle(battle);

            battledetail.setWinners_detail(battledetailservice.getBattleDetailWinnerByBattleId(battle.getBattle_id()));
            battledetail.setLossers_detail(battledetailservice.getBattleDetailLosserByBattleId(battle.getBattle_id()));

            battledetails.add(battledetail);
        }

        return battledetails;
    }
}
