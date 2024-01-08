package com.web.albion.Mapper;

import com.web.albion.dto.UserWinLosePerMatchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserWinLosePerMatchMapper {

    List<UserWinLosePerMatchDto> getUserWinLose(String user_name);

}
