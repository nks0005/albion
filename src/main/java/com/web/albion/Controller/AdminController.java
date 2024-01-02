package com.web.albion.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public ModelAndView loginpage(ModelAndView mv) {
        mv.setViewName("admin/page/login");
        return mv;
    }

    @PostMapping("/login")
    public void login(@RequestParam("user_name")String user_name, @RequestParam("user_password")String user_password){
    }

    @GetMapping("/board")
    public ModelAndView getAdminBoard(ModelAndView mv){


        mv.setViewName("admin/page/board");
        return mv;
    }
}
