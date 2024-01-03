package com.web.albion.Model;

import lombok.Data;

import java.util.List;


@Data
public class Battle {
    int battle_id;
    int match_type;
    String battle_time;

    List<String> players;

    List<Team> winners;
    List<Team> lossers;
}
