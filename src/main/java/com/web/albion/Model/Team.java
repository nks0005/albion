package com.web.albion.Model;

import lombok.Data;

@Data
public class Team {

    String user_name;
    String user_uid;

    //String user_guild;
    //String user_ally;

    Gear gear;
}