package com.web.albion.Service;

import com.web.albion.Mapper.UserWinLosePerMatchMapper;
import com.web.albion.Model.UserWinLose;
import com.web.albion.dto.UserWinLosePerMatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWinLosePerMatchService {
    @Autowired
    UserWinLosePerMatchMapper userwinlosepermatchmapper;

    public UserWinLose getUserWinLoseByUsername(String user_name){
        System.out.println("User name : " + user_name);

        List<UserWinLosePerMatchDto> list_userwinlosepermatch = userwinlosepermatchmapper.getUserWinLose(user_name);
        System.out.println(list_userwinlosepermatch);

        if(list_userwinlosepermatch == null) return null;

        UserWinLose result_userwinlose = new UserWinLose();
        result_userwinlose.setUser_name(user_name);
        for(UserWinLosePerMatchDto userwinlose : list_userwinlosepermatch)
        {
            switch(userwinlose.getMatch_type()){
                case "22":
                    result_userwinlose.setWinLose(result_userwinlose.getDuo(), userwinlose.getWin_count(), userwinlose.getLose_count());
                    break;
                case "55":
                    result_userwinlose.setWinLose(result_userwinlose.getFive(), userwinlose.getWin_count(), userwinlose.getLose_count());
                    break;
                case "1010":
                    result_userwinlose.setWinLose(result_userwinlose.getTen(), userwinlose.getWin_count(), userwinlose.getLose_count());
                    break;
            }
        }

        return result_userwinlose;
    }

}
