package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.DIMProject;
import com.qding.bigdata.ds.model.PortraitDictionaries;
import com.qding.bigdata.ds.model.PortraitTag;
import com.qding.bigdata.ds.service.PortraitDictionariesService;
import com.qding.bigdata.ds.service.PortraitTagService;
import com.qding.bigdata.ds.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserPortraitController extends BasicController {

    @Autowired
    private PortraitTagService PortraittagService;

    @Autowired
    private PortraitDictionariesService portraitDictionariesService;



    /**
     * 数据概览
     */
	@RequestMapping("dataSummary")
	public ModelAndView index() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.数据概览);
        return modelAndView;
	}
    /**
     * 画像分析
     */
    @RequestMapping("userProfileAnalysis")
    public ModelAndView userProfile() {
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.画像分析新);
        return modelAndView;
    }

    /**
     * 用户组中的标签查询
     * @return
     */
    @RequestMapping("userGroupTagSelect")
    @ResponseBody
    public List<PortraitTag> userGroupTagSelect(PortraitTag tagParam) {
        List<PortraitTag> treeList = PortraittagService.listAll(tagParam);
        try {
            for (int i = 0; i < treeList.size(); i++) {
                if ( 3 == treeList.get(i).getDataType()){
                    PortraitTag portraitTag = treeList.get(i);
                    if("city".equals(portraitTag.getTag())){
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectCity();
                        portraitDictionariesService.addDinctionaries(ldm,portraitTag);
                    }else if("community".equals(portraitTag.getTag())){
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectCommunity();
                        portraitDictionariesService.addDinctionaries(ldm,portraitTag);
                    }else if("property".equals(portraitTag.getTag())){
                        List<DIMProject> ldm = portraitDictionariesService.getDimProjectProperty();
                        portraitDictionariesService.addDinctionaries(ldm,portraitTag);
                    }else{
                        PortraitDictionaries  pd = new PortraitDictionaries();
                        pd.setSource(treeList.get(i).getTag());
                        List<PortraitDictionaries> pdList = portraitDictionariesService.listAll(pd);
                        if (pdList.size()>0) {
                            treeList.get(i).setLpd(pdList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return treeList;
    }


    /**
     * 群组管理
     * @return
     */
    @RequestMapping("groupManagement")
    public ModelAndView groupManagement(HttpServletRequest request) {
        PortraitTag tagParam = new PortraitTag();
        tagParam.setStatus(0);
        List<PortraitTag> treeList = PortraittagService.listAll(tagParam);
        List<PortraitDictionaries> pdList = null;
        PortraitDictionaries pd = null;
        try {
            for (int i = 0; i < treeList.size(); i++) {
                if ( 3 == treeList.get(i).getDataType()){
                    pd = new PortraitDictionaries();
                    pd.setSource(treeList.get(i).getTag());
                    pdList = portraitDictionariesService.listAll(pd);
                    PortraitDictionaries pdj = null;
                    if (pdList.size()>0) {
                        treeList.get(i).setLpd(pdList);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject("treeList", treeList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.群组管理新);
        return modelAndView;
    }

    /**
     * 标签列表
     * @param tagParam
     */
    @RequestMapping("tagManagement")
    public ModelAndView listTag(PortraitTag tagParam) {
        List<PortraitTag> PortraittagList = PortraittagService.listAll(tagParam);
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("PortraittagList", PortraittagList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.标签管理新);
        return modelAndView;
    }

    /**
     * 标签增加
     */
    @RequestMapping("portraitAddTag")
    public ModelAndView Tag() {
        List<PortraitTag> PortraittagList = PortraittagService.listAll(new PortraitTag());
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject("PortraittagList", PortraittagList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.标签管理新);
        return modelAndView;
    }

    /**
     * 标签编辑
     * @return
     */
    @RequestMapping("portraitEditTag")
    @ResponseBody
    public Map<String,Object> editTag(PortraitTag tagParam) {
        PortraitTag portraitTag = PortraittagService.getOne(tagParam);
        List<PortraitDictionaries> pdList = new ArrayList<PortraitDictionaries>();
        try {
            PortraitDictionaries pd = new PortraitDictionaries();
            pd.setSource(tagParam.getTag());
            pdList = portraitDictionariesService.listAll(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("portraitTag", portraitTag);
        retMap.put("pdList", pdList);
        return retMap;
    }

    /**
     * 标签添加
     * @return
     */
    @RequestMapping("savePortraitTag")
    public String tagSave(PortraitTag tagParam) {
        if (CommonUtil.isEmpty(tagParam.getId())) {
            PortraittagService.save(tagParam);
        }
        return "redirect:./tagManagement";
    }


    /**
     * 标签编辑
     * @return
     */
    @RequestMapping("editPortraitTag")
    public String tagedit(PortraitTag tagParam) {
        if (!CommonUtil.isEmpty(tagParam.getId())) {
            PortraittagService.update(tagParam);
        }
        return "redirect:./tagManagement";
    }


    /**
     * 实例添加
     * @return
     */
    @RequestMapping(value = "saveDictionariesTag",method = RequestMethod.POST)
    @ResponseBody
    public PortraitDictionaries dictionariesTagSave(PortraitDictionaries tagParam) {
        try {
            if (CommonUtil.isEmpty(tagParam.getId())) {
                portraitDictionariesService.save(tagParam);
            }
            else {
                portraitDictionariesService.update(tagParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        List<PortraitDictionaries> PortraittagList = portraitDictionariesService.listAll(tagParam);
        if (PortraittagList.size()> 0){
            return PortraittagList.get(0);
        }else{
            return null;
        }
    }


    /**
     * 标签删除
     * @return
     */
    @RequestMapping("deletePortraitTag")
    public String tagDelete(PortraitTag tagParam) {
        if (!CommonUtil.isEmpty(tagParam.getTag())) {
            try {
                PortraittagService.delete(tagParam);
                PortraitDictionaries pd = new PortraitDictionaries();
                if (null != tagParam.getTag() && !tagParam.getTag().equals("")){
                    try {
                        pd.setSource(tagParam.getTag());
                        portraitDictionariesService.delete(pd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "redirect:./tagManagement";
    }

    /**
     * 标签字典删除
     * @return
     */

    @RequestMapping("/dictionariesDelete")
    @ResponseBody
    public List<PortraitDictionaries> dictionariesDelete(PortraitDictionaries tagParam,String id , String source) {
        if (!CommonUtil.isEmpty(tagParam.getId())) {
            portraitDictionariesService.delete(tagParam);
        }
        tagParam.setId(null);
        List<PortraitDictionaries> portraitDictionaries = portraitDictionariesService.listAll(tagParam);
        return  portraitDictionaries;
    }



}
