package com.web.albion.Controller;

import com.web.albion.Model.Battle;
import com.web.albion.Model.BattleDetail;
import com.web.albion.Service.BattleDetailService;
import com.web.albion.Service.BattleService;
import com.web.albion.Service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    IndexService indexservice;

    @GetMapping("")
    public ModelAndView getIndexPage(ModelAndView mv, @RequestParam(name = "list", required = false) Integer list){
        Integer offset = list;
        if(offset == null) offset = 0;
        else if(offset > 100) offset = 100;

        List<BattleDetail> battledetails = indexservice.getRecentBattles(offset);

        mv.addObject("battlesDetails", battledetails);

        mv.setViewName("client/page/index");
        return mv;
    }
}
