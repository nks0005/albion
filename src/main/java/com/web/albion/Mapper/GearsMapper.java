package com.web.albion.Mapper;

import com.web.albion.dto.GearsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GearsMapper {
    int insertGear(GearsDto gearsDto);

    Integer selectGearByName(String name);

}
