//package com.qding.bigdata.ds.controller;
//
//
//import com.qding.bigdata.ds.common.Constant;
//import com.qding.bigdata.ds.enums.SideBarMenu;
//import com.qding.bigdata.ds.model.PortraitDictionaries;
//import com.qding.bigdata.ds.model.PortraitTag;
//import com.qding.bigdata.ds.model.Tag;
//import com.qding.bigdata.ds.service.PortraitDictionariesService;
//import com.qding.bigdata.ds.service.PortraitTagService;
//import com.qding.bigdata.ds.util.CommonUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//
//@Controller
//public class PortraitTagController extends BasicController{
//
//    @Autowired
//    private PortraitTagService PortraittagService;
//
//    @Autowired
//    private PortraitDictionariesService portraitDictionariesService;
//
//    @RequestMapping("listPortraitTag")
//    public ModelAndView listTag(PortraitTag tagParam) {
//        List<PortraitTag> PortraittagList = PortraittagService.listAll(tagParam);
//        ModelAndView modelAndView =initModelAndView();
//        modelAndView.addObject("PortraittagList", PortraittagList);
//        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户画像新);
//        return modelAndView;
//    }
//
//
//    @RequestMapping("listPortraitDictionaries")
//    public ModelAndView listPortraitDictionaries(PortraitDictionaries tagParam) {
//        List<PortraitDictionaries> portraitDictionariesList = PortraittagService.getDictionariesList(tagParam);
//        ModelAndView modelAndView =initModelAndView();
//        modelAndView.addObject("PortraitDictionariesList", portraitDictionariesList);
//        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.用户画像新);
//        return modelAndView;
//    }
//
//    @RequestMapping("savePortraitTag")
//    public String tagSave(PortraitTag tagParam) {
//        if (CommonUtil.isEmpty(tagParam.getId())) {
//            PortraittagService.save(tagParam);
//            List<PortraitDictionaries>  lpd= tagParam.getLpd();
//            for (int i = 0;i<=lpd.size();i++){
//                PortraitDictionaries pd = new PortraitDictionaries();
//                pd.setExample_id(lpd.get(i).getExample_id());
//                pd.setExample_name(lpd.get(i).getExample_name());
//                pd.setSource(lpd.get(i).getSource());
//                pd.setSortno(lpd.get(i).getSortno());
//                try {
//                    portraitDictionariesService.save(pd);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
////        else {
////            PortraittagService.update(tagParam);
////        }
//        return "redirect:./listPortraitTag";
//    }
//
//
//
//}
