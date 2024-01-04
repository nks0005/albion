package com.web.albion.Service;

import com.web.albion.Mapper.ConnectLogMapper;
import com.web.albion.dto.ConnectLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectLogService {
    @Autowired
    ConnectLogMapper connectlogmapper;

    public int insertConnectLog(ConnectLogDto connectlog){
        return connectlogmapper.insertConnectLog(connectlog);
    }
}
