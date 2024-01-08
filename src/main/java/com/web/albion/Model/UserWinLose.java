package com.web.albion.Model;

import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

@Data
@JsonComponent
public class UserWinLose {
    String user_name;

    WL Duo;
    WL Five;
    WL Ten;

    public UserWinLose(){
        Duo = new WL();
        Five = new WL();
        Ten = new WL();
    }

    public void setWinLose(WL type, int win, int lose){
        // type = new WL(win, lose);

        type.setWin(win);
        type.setLose(lose);
    }
}


@Data
class WL {
    int Win;
    int Lose;

    public WL(){
        this.Win = 0;
        this.Lose = 0;
    }
}