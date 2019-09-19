package com.qding.bigdata.ds.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.*;
import com.qding.bigdata.ds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/3/20 17:18
 * @description
 */

@Controller
public class TrackMetaController extends BasicController {

    @Autowired
    ProductService productService;

    @Autowired
    BusinessService businessService;

    @Autowired
    MaidianEventService maidianEventService;

    @Autowired
    MaidianParamService maidianParamService;

    @Autowired
    MaidianPageService maidianPageService;

    /**
     * 页面维表
     * @return
     */
    @RequestMapping("pageTrackMetaList")
    public ModelAndView trackMetaList(){
        ModelAndView mv = initModelAndView("treackDIM/pageTrackMetaList");
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.页面维表);
        PageInfo<DsMaidianParam> paramPageInfo = maidianParamService.list(new DsMaidianParam(), null, null);
        mv.addObject("allParamList", paramPageInfo.getList());
        setProductAndBusiness(mv);
        return mv;
    }

    @RequestMapping("skipMetaList")
    public ModelAndView skipMetaList(){
        ModelAndView mv = initModelAndView("treackDIM/skipMetaList");
        PageInfo<DsMaidianPage> pagePageInfo = maidianPageService.list(new DsMaidianPage(), null, null);
        mv.addObject("pageList", pagePageInfo.getList());
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.skip映射表);
        setProductAndBusiness(mv);
        return mv;
    }
    @RequestMapping("eventTrackMetaList")
    public ModelAndView evenTrackMetaList(){
        ModelAndView mv = initModelAndView("treackDIM/evenTrackMetaList");
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.事件维表);
        PageInfo<DsMaidianParam> paramPageInfo = maidianParamService.list(new DsMaidianParam(), null, null);
        mv.addObject("allParamList", paramPageInfo.getList());
        setProductAndBusiness(mv);
        return mv;
    }
    @RequestMapping("eventPositionMetaList")
    public ModelAndView evenPositionMetaList(){
        ModelAndView mv = initModelAndView("treackDIM/evenPositionMetaList");
        PageInfo<DsMaidianEvent> eventPageInfo = maidianEventService.list(new DsMaidianEvent(), null, null);
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.事件位置维表);
        mv.addObject("eventList", eventPageInfo.getList());
        setProductAndBusiness(mv);
        return mv;
    }
    /**
     * 参数表
     * @return
     */
    @RequestMapping("parameterDIM")
    public ModelAndView parameterDIM(){
        ModelAndView mv = initModelAndView("treackDIM/parameterDIM");
        mv.addObject(Constant.SIDEBAR_MENU, SideBarMenu.参数表);
        return mv;
    }


    private void setProductAndBusiness(ModelAndView mv) {
        List<Product> productList = productService.getAll();
        List<Business> businessList = businessService.getAll();
        mv.addObject("productList", productList);
        mv.addObject("businessList", businessList);
        mv.addObject("productListForJson", JSON.toJSONString(productList));
        mv.addObject("businessListForJson", JSON.toJSONString(businessList));
    }

}
