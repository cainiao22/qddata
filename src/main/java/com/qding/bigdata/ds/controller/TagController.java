package com.qding.bigdata.ds.controller;

import java.util.List;

import com.qding.bigdata.ds.model.PortraitTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.service.TagService;
import com.qding.bigdata.ds.util.CommonUtil;

@Controller
public class TagController extends BasicController{

    @Autowired
    private TagService tagService;

    @RequestMapping("listTag")
    public ModelAndView listTag(PortraitTag tagParam) {
        List<PortraitTag> tagList = tagService.listAll(tagParam);
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("tagList", tagList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.标签管理);
        return modelAndView;
    }

    @RequestMapping("addTag")
    public ModelAndView addTag() {
        ModelAndView modelAndView = initModelAndView();
        List<PortraitTag> alltags=tagService.listAll(new PortraitTag());
        List<PortraitTag> allTreeTags=tagService.sortToTree(alltags);
        modelAndView.addObject("allTreeTags", allTreeTags);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.标签管理);
        return modelAndView;
    }

    @RequestMapping("saveTag")
    public String tagSave(PortraitTag tagParam) {
        if (CommonUtil.isEmpty(tagParam.getId())) {
            tagService.save(tagParam);
        }
        else {
            tagService.update(tagParam);
        }
        return "redirect:./listTag";
    }

    @RequestMapping("manageTag")
    public ModelAndView manageTag(PortraitTag tagParam) {
        PortraitTag tag = tagService.getOne(tagParam);

        ModelAndView modelAndView = initModelAndView("addTag");
        modelAndView.addObject("tag", tag);
        List<PortraitTag> alltags=tagService.listAll(new PortraitTag());
        List<PortraitTag> allTreeTags=tagService.sortToTree(alltags);
        modelAndView.addObject("allTreeTags", allTreeTags);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.标签管理);
        return modelAndView;
    }

    @RequestMapping("deleteTag")
    public String deleteTag(PortraitTag tagParam) {
        tagService.delete(tagParam);
        return "redirect:./listTag";
    }
}
