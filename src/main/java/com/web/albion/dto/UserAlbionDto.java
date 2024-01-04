package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("useralbion")
public class UserAlbionDto {
    int id;
    String uid;
    String name;
}
