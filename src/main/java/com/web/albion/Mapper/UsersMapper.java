package com.web.albion.Mapper;

import com.web.albion.dto.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    Users getUserByUsername(String user_name);

}
