package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yanpf
 * @date 2019/1/16 9:58
 * @description
 */

@Controller
public class AzkabanController extends BasicController {

    @RequestMapping("azkabanIndex")
    public ModelAndView azkabanIndex(){
        ModelAndView mv = initModelAndView("azkanban");
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.Azkaban任务);
        return mv;
    }
}
