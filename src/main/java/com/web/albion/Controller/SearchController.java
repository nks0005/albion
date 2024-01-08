package com.web.albion.Controller;

import com.web.albion.Model.UserWinLose;
import com.web.albion.Service.UserWinLosePerMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
