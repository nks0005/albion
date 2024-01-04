package com.web.albion.Mapper;

import com.web.albion.dto.GearsSetDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GearsSetMapper {
    int insertGearset(GearsSetDto gearset);

    Integer selectGearsetByGear(GearsSetDto gearset);

}
