package com.web.albion.Controller;


import com.web.albion.Model.BattleDetail;
import com.web.albion.Service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/detail")
public class DetailController {

    @Autowired
    DetailService detailservice;

    @GetMapping("/{battleid}")
    public ModelAndView getDetail(@PathVariable("battleid") String battleid, ModelAndView mv) {


        if(battleid == null) {
            mv.setViewName("error");
            return mv;
        }

        BattleDetail battledetail = detailservice.getBattleDetailByBattleId(Integer.parseInt(battleid));
        if (battledetail == null) {
            mv.setViewName("error");
        }else {
            mv.addObject("battledetail", battledetail);
            mv.setViewName("client/page/detail");
        }
        return mv;
    }

}
