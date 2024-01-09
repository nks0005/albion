package com.web.albion.Mapper;

import com.web.albion.dto.BattlesDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BattleMapper {
    int insertBattle(BattlesDto battle);

    List<BattlesDto> getBattlesInProcessZero();

    int checkBattleExists(int battle_id);

    List<BattlesDto> getBattles(@Param("offset") int offset);
    //List<BattlesDto> getBattlesType(@Param("offset") int offset, @Param("type") int type);

    List<BattlesDto> getBattlesDuo(@Param("offset") int offset);
    List<BattlesDto> getBattlesFive(@Param("offset") int offset);
    List<BattlesDto> getBattlesTen(@Param("offset") int offset);

    BattlesDto getBattleByBattleId(@Param("battle_id") int battle_id);

    void updateProcessDone(int battle_id);

}
