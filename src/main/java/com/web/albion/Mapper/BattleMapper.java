package com.web.albion.Mapper;

import com.web.albion.dto.Battles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BattleMapper {
    int insertBattle(Battles battle);

    int checkBattleExists(int battle_id);
}
