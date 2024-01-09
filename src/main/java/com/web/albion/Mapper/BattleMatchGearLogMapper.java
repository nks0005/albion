package com.web.albion.Mapper;

import com.web.albion.Model.BattleMatchGearLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BattleMatchGearLogMapper {
    List<BattleMatchGearLog> getBattleMatchGearLogs(int battle_id);
}