package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("gears")
public class Gears {
    int id;
    String name;
}
