package com.web.albion.Model;

import com.web.albion.dto.BattleDetailDto;
import com.web.albion.dto.BattlesDto;
import lombok.Data;

import java.util.List;

@Data
public class BattleDetail {
    BattlesDto battle;
    List<BattleDetailDto> winners_detail;
    List<BattleDetailDto> lossers_detail;
}
