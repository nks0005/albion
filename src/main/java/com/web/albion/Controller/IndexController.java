package com.web.albion.Controller;

import com.web.albion.Model.BattleDetail;
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
    IndexService indexService;

    private int limit = 3;

    private ModelAndView getBattlesByType(ModelAndView mv, String viewName, String battleType, Integer list) {

        int offset = 0;

        if (list == null) list = (Integer)0;


        if (list < 0) offset = 0;
        else if (list > 100) offset = 100;
        else offset = list;

        offset = offset == 0 ? 0 : offset * limit;

        List<BattleDetail> battledetails = switch (battleType) {
            case "duo" -> indexService.getDuoBattles(offset);
            case "five" -> indexService.getFiveBattles(offset);
            case "ten" -> indexService.getTenBattles(offset);
            default -> indexService.getRecentBattles(offset);
        };


        if (list != null && list > 0) {
            String previousPageUrl = "/match/" + battleType + "?list=" + (list - 1);
            mv.addObject("previousPageURL", previousPageUrl);
        }
        String nextPageUrl = "/match/" + battleType + "?list=" + (list + 1);
        mv.addObject("nextPageURL", nextPageUrl);


        mv.addObject("battlesDetails", battledetails);
        mv.setViewName(viewName);
        return mv;
    }

    @GetMapping("match/duo")
    public ModelAndView getDuolistPage(ModelAndView mv, @RequestParam(name = "list", required = false) Integer list) {
        return getBattlesByType(mv, "client/page/match_list", "duo", list);
    }

    @GetMapping("match/five")
    public ModelAndView getFivelistPage(ModelAndView mv, @RequestParam(name = "list", required = false) Integer list) {
        return getBattlesByType(mv, "client/page/match_list", "five", list);
    }

    @GetMapping("match/ten")
    public ModelAndView getTenlistPage(ModelAndView mv, @RequestParam(name = "list", required = false) Integer list) {
        return getBattlesByType(mv, "client/page/match_list", "ten", list);
    }

    @GetMapping("")
    public ModelAndView getIndexPage(ModelAndView mv, @RequestParam(name = "list", required = false) Integer list) {
        return getBattlesByType(mv, "client/page/index", "recent", list);
    }
}