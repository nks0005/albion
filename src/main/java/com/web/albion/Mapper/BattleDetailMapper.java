package com.web.albion.Mapper;

import com.web.albion.dto.BattleDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BattleDetailMapper {
    public List<BattleDetailDto> getBattleDetailByBattleId(int battle_id);
    public List<BattleDetailDto> getBattleDetailWinnerByBattleId(int battle_id);
    public List<BattleDetailDto> getBattleDetailLosserByBattleId(int battle_id);
}
