package com.web.albion.Mapper;

import com.web.albion.dto.Gears;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GearsMapper {
    int insertGear(Gears gears);

    Integer selectGearByName(String name);

}
