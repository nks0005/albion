package com.web.albion.Mapper;

import com.web.albion.dto.UsersDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    UsersDto getUserByUsername(String user_name);

}
