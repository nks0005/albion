package com.web.albion.Model;

import com.web.albion.dto.UserMatchLogDto;
import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonComponent
public class UserWinLose {
    String user_name;

    WL Duo;
    WL Five;
    WL Ten;

    List<UserMatchLogDto> DuoUserMatchLog;
    List<UserMatchLogDto> FiveUserMatchLog;
    List<UserMatchLogDto> TenUserMatchLog;




    public UserWinLose(){
        Duo = new WL();
        Five = new WL();
        Ten = new WL();

        DuoUserMatchLog = new ArrayList<>();
        FiveUserMatchLog = new ArrayList<>();
        TenUserMatchLog = new ArrayList<>();

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