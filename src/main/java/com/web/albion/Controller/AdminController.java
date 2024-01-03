package com.web.albion.Controller;

import com.web.albion.Service.AdminBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminBoardService admin_board_service;

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
        boolean isRunning = admin_board_service.getIsRunning();

        mv.addObject("isRunning", isRunning);
        mv.setViewName("admin/page/board");
        return mv;
    }

    @GetMapping("/board/start")
    public String getTestStart(){
        admin_board_service.runThread();

        return "redirect:/admin/board";
    }

    @GetMapping("/board/stop")
    public String getTestStop(){
        admin_board_service.stopThread();

        return "redirect:/admin/board";
    }
}
