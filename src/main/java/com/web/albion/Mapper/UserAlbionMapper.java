package com.web.albion.Mapper;

import com.web.albion.dto.UserAlbionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAlbionMapper {
    int insertUser(UserAlbionDto useralbion);

    Integer selectUserByName(String name);

}
