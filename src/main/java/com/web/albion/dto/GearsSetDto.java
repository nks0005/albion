package com.web.albion.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("gearsset")
public class GearsSetDto {
    int id;

    int main_hand_id;
    int off_hand_id;
    int head_id;
    int armor_id;
    int shoes_id;
    int cape_id;
}
