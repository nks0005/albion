package com.web.albion.Controller;

import com.web.albion.Model.UserWinLose;
import com.web.albion.Service.UserWinLosePerMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    UserWinLosePerMatchService userwinlosepermatchserivce;

    // [POST] /searchUser
    @PostMapping("/User")
    public UserWinLose getSearchUser(@RequestBody Map<String, String> requestBody)
    {
        String user_name = requestBody.get("user_name");
        if(user_name == null) return null;

        UserWinLose userwinlose = userwinlosepermatchserivce.getUserWinLoseByUsername(user_name);
        System.out.println(userwinlose);

        return userwinlose;
    }

    @GetMapping("/User/MatchLog/{user_name}")
    public ModelAndView getUserDetail(@PathVariable("user_name") String user_name, ModelAndView mv) {

        UserWinLose userwinlose = userwinlosepermatchserivce.getUserWinLoseByUsername(user_name);
        if(userwinlose == null) {
            mv.setViewName("error");
            return mv;
        }

        mv.addObject("userwinlose", userwinlose);

        // 뷰 설정 (여기서는 예시로 "user_match_log" 뷰를 사용하도록 설정)
        mv.setViewName("client/page/matchlog");

        return mv;
    }
}