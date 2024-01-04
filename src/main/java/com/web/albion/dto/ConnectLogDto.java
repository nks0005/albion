package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("connectlog")
public class ConnectLogDto {

    int id;
    String remote_ip;
    String request_uri;

}
