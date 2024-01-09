package com.web.albion.Mapper;

import com.web.albion.dto.UserMatchLogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMatchLogMapper {
    List<UserMatchLogDto> getUserMatchLogs(String user_name);
}
