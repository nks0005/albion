package com.web.albion.Controller;

import com.web.albion.Service.MetaCompSummaryService;
import com.web.albion.dto.MetaCompSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    MetaCompSummaryService metacompsummaryService;


    @GetMapping("/duo")
    public ModelAndView getDuoCompsMeta(ModelAndView mv) {
        List<MetaCompSummaryDto> duoMetaComps = metacompsummaryService.getDuoComp(0);


        mv.addObject("comps", duoMetaComps);
        mv.setViewName("client/page/metaindex");
        return mv;
    }

    @GetMapping("/five")
    public ModelAndView getFiveCompsMeta(ModelAndView mv) {
        List<MetaCompSummaryDto> fiveMetaComps = metacompsummaryService.getFiveComp(0);

        mv.addObject("comps", fiveMetaComps);
        mv.setViewName("client/page/metaindex");
        return mv;
    }

    @GetMapping("/ten")
    public ModelAndView getTenCompsMeta(ModelAndView mv) {
        List<MetaCompSummaryDto> tenMetaComps = metacompsummaryService.getTenComp(0);

        mv.addObject("comps", tenMetaComps);
        mv.setViewName("client/page/metaindex");
        return mv;
    }

}
