package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yanpf
 * @date 2019/1/13 11:46
 * @description
 */

@Controller
public class GuancetaiModelAndViewController extends BasicController {

    //共同的页面只丁管家增加后缀 _dgj，私有的都加后缀

    //总览
    @RequestMapping("guancetai")
    public ModelAndView guancetai(){
        ModelAndView modelAndView = initModelAndView("guancetai/guancetai");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.观测台);
        return modelAndView;
    }
    @RequestMapping("guancetai_dgj")
    public ModelAndView guancetaiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/guancetai_dgj");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.观测台);
        return modelAndView;
    }
    //页面分析
    @RequestMapping("pageAnalyse")
    public ModelAndView yemianfenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/pageAnalyse");
        return modelAndView;
    }
    @RequestMapping("pageAnalyse_dgj")
    public ModelAndView yemianfenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/pageAnalyse_dgj");
        return modelAndView;
    }
    //事件分析
    @RequestMapping("eventAnalyse")
    public ModelAndView shijianfenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/eventAnalyse");
        return modelAndView;
    }
    @RequestMapping("eventAnalyse_dgj")
    public ModelAndView shijianfenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/eventAnalyse_dgj");
        return modelAndView;
    }
    //活跃分析
    @RequestMapping("activeAnalyse")
    public ModelAndView huoyuefenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/activeAnalyse");
        return modelAndView;
    }
    @RequestMapping("activeAnalyse_dgj")
    public ModelAndView huoyuefenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/activeAnalyse_dgj");
        return modelAndView;
    }
    //留存分析
    @RequestMapping("lcAnalyse")
    public ModelAndView liucunfenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/lcAnalyse");
        return modelAndView;
    }
    @RequestMapping("lcAnalyse_dgj")
    public ModelAndView liucunfenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/lcAnalyse_dgj");
        return modelAndView;
    }
    //版本分析
    @RequestMapping("versionAnalyse")
    public ModelAndView banbenfenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/versionAnalyse");
        return modelAndView;
    }
    @RequestMapping("versionAnalyse_dgj")
    public ModelAndView banbenfenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/versionAnalyse_dgj");
        return modelAndView;
    }
    //丁管家--路径分析
    @RequestMapping("pathAnalyse_dgj")
    public ModelAndView lujingfenxi(){
        ModelAndView modelAndView = initModelAndView("guancetai/pathAnalyse_dgj");
        return modelAndView;
    }
    //丁管家--操作系统
    @RequestMapping("osAnalyse_dgj")
    public ModelAndView xitongfenxiDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/osAnalyse_dgj");
        return modelAndView;
    }
    //丁管家--终端型号
    @RequestMapping("clientType_dgj")
    public ModelAndView clientTypeDGJ(){
        ModelAndView modelAndView = initModelAndView("guancetai/clientType_dgj");
        return modelAndView;
    }
    //千丁APP--设备分析
    @RequestMapping("shebeiAnalyse_app")
    public ModelAndView shebeiAnalyseApp(){
        ModelAndView modelAndView = initModelAndView("guancetai/shebeiAnalyse_app");
        return modelAndView;
    }


    @RequestMapping("pathAnalyse")
    public ModelAndView pathAnalyse(){
        ModelAndView modelAndView = initModelAndView("guancetai/pathAnalyse");
        return modelAndView;
    }

    //漏斗分析
    @RequestMapping("louDouAnalyse")
    public ModelAndView louDouAnalyse(){
        ModelAndView modelAndView = initModelAndView("guancetai/louDouAnalyse");
        return modelAndView;
    }


    //漏斗分析 丁管家
    @RequestMapping("louDouAnalyse_dgj")
    public ModelAndView louDouAnalyse_dgj(){
        ModelAndView modelAndView = initModelAndView("guancetai/louDouAnalyse_dgj");
        return modelAndView;
    }
}
