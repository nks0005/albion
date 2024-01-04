package com.web.albion.Mapper;

import com.web.albion.dto.UserMatchDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMatchMapper {
    int insertUserMatch(UserMatchDto usermatch);

}
