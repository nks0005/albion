package com.web.albion.Mapper;

import com.web.albion.dto.ConnectLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConnectLogMapper {
    int insertConnectLog(ConnectLogDto connectlog);
}
