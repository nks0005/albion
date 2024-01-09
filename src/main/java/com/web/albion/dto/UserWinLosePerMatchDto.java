package com.web.albion.dto;

import lombok.Data;

@Data
public class UserWinLosePerMatchDto {
    int user_id;
    String user_name;
    String match_type;
    int win_count;
    int loss_count;
}
