package com.qding.bigdata.ds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author yanpf
 * @Date 19-8-21 上午10:52
 * @Description
 **/

@Controller
public class GCTModelAndViewV2Controller extends BasicController {

    @RequestMapping("/louDouAnalysis_v2")
    public ModelAndView louDouAnalysis(){
        return initModelAndView("guancetai-v2/louDouAnalyse");
    }

    @RequestMapping("louDouAnalysis_dgj_v2")
    public ModelAndView louDouAnalysis_dgj(){
        return initModelAndView("guancetai-v2/louDouAnalyse_dgj");
    }

    @RequestMapping("pathAnalyse_v2")
    public ModelAndView pathAnalyse(){
        return initModelAndView("guancetai-v2/pathAnalyse");
    }

    @RequestMapping("pathAnalyse_dgj_v2")
    public ModelAndView pathAnalyse_dgj(){
        return initModelAndView("guancetai-v2/pathAnalyse_dgj");
    }
}
