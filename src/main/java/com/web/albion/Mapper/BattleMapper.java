package com.web.albion.Mapper;

import com.web.albion.dto.BattlesDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BattleMapper {
    int insertBattle(BattlesDto battle);

    int checkBattleExists(int battle_id);
}
