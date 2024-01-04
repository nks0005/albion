package com.web.albion.Service;

import com.web.albion.Mapper.BattleDetailMapper;
import com.web.albion.dto.BattleDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleDetailService {

    @Autowired
    BattleDetailMapper battledetailmapper;

    public List<BattleDetailDto> getBattleDetailByBattleId(int battle_id) {
        return battledetailmapper.getBattleDetailByBattleId(battle_id);
    }

    public List<BattleDetailDto> getBattleDetailWinnerByBattleId(int battle_id) {
        return battledetailmapper.getBattleDetailWinnerByBattleId(battle_id);
    }

    public List<BattleDetailDto> getBattleDetailLosserByBattleId(int battle_id) {
        return battledetailmapper.getBattleDetailLosserByBattleId(battle_id);
    }

}
