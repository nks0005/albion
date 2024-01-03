package com.web.albion.Mapper;

import com.web.albion.dto.Gears;
import com.web.albion.dto.GearsSet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GearsSetMapper {
    int insertGearset(GearsSet gearset);

    Integer selectGearsetByGear(GearsSet gearset);

}
