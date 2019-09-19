package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.common.SearchResult;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.MidLuopanRengongData;
import com.qding.bigdata.ds.service.MidLuopanRengongDataService;
import com.qding.bigdata.ds.util.CommonUtil;
import com.qding.bigdata.ds.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.qding.bigdata.ds.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MidLuopanRengongDataController extends BasicController{
    @Autowired
    private MidLuopanRengongDataService midLuopanRengongDataService;

    /**
     * 人工罗盘数据录入列表
     * @param midluopanrengongdata
     * @return
     */
    @RequestMapping("/midluopanrengongdatalist")
    public ModelAndView midrengongdatalist(MidLuopanRengongData midluopanrengongdata) {
        //查询所有符合条件数据,不带分页,分页暂时作用在前台
        List<MidLuopanRengongData> midluopanrengongdatalists = midLuopanRengongDataService.listNopage(midluopanrengongdata);
        ModelAndView modelAndView = initModelAndView();
        //ModelAndView modelAndView =  new ModelAndView();
        modelAndView.addObject("midluopanrengongdatalists", midluopanrengongdatalists);
        modelAndView.addObject("midluopanrengongdata", midluopanrengongdata==null?"":midluopanrengongdata);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.罗盘数据列表);
        return modelAndView;
    }


    /**
     * 删除一条人工录入数据,跳转到列表页面
     * @param midluopanrengongdata
     * @return
     */
    @RequestMapping("/midluopanrengongdatadelete")
    public ModelAndView midrengongdatadelete(MidLuopanRengongData midluopanrengongdata) {
        ModelAndView modelAndView = initModelAndView();
        //ModelAndView modelAndView = new ModelAndView();
        midLuopanRengongDataService.delete(midluopanrengongdata);
        modelAndView.addObject("key", midluopanrengongdata.getKey());
        modelAndView.setViewName("redirect:midluopanrengongdatalist");
        return modelAndView;
    }

    /**
     * 罗盘数据录入页面
     * @param midluopanrengongdata
     * @return
     */
    @RequestMapping("/midLuopanRengongData")
    public ModelAndView midrengongdatalists(MidLuopanRengongData midluopanrengongdata) {
        ModelAndView modelAndView = initModelAndView();
        //ModelAndView modelAndView =  new ModelAndView();
        //modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.罗盘数据录入);
        if(null!=midluopanrengongdata && null!= midluopanrengongdata.getKey()){
            modelAndView.addObject("midluopanrengongdata", midluopanrengongdata);
        }else{
            midluopanrengongdata = new MidLuopanRengongData();
            midluopanrengongdata.setKey("任务-绑定户数");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH )+1;
            if(month<10){
                midluopanrengongdata.setDateValue(year+"-0"+month);
            }else{
                midluopanrengongdata.setDateValue(year+"-"+month);
            }
            modelAndView.addObject("selectProjectId", midLuopanRengongDataService.selectOneProjectUUid(null));
            modelAndView.addObject("midluopanrengongdatalists",midLuopanRengongDataService.listRenwuMlrds(midluopanrengongdata));
            modelAndView.addObject("midluopanrengongdata", midluopanrengongdata);
        }
        return modelAndView;
    }

    /**
     * 罗盘数据录入保存
     * @param midluopanrengongdata
     * @param request
     * @return
     */
    @RequestMapping(value="midrengongdataadd" ,method ={RequestMethod.POST})
    public ModelAndView midrengongdataadd(MidLuopanRengongData midluopanrengongdata,HttpServletRequest request){
        String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
        ModelAndView modelAndView = initModelAndView();
        //ModelAndView modelAndView = new ModelAndView();
        Map map = midLuopanRengongDataService.addOrUpdate(midluopanrengongdata.getMlrds(),username);
        modelAndView.setViewName("midLuopanRengongData");
        modelAndView.addObject("key", midluopanrengongdata.getKey());
        modelAndView.addObject("result","本次操作,新增:"+map.get("add")+"条,修改:"+map.get("update")+"条!");
        modelAndView.addObject("resultMap",map);
        modelAndView.addObject("midluopanrengongdatalists",midluopanrengongdata.getMlrds());
        modelAndView.addObject("midluopanrengongdata",midluopanrengongdata);
        modelAndView.addObject("selectProjectId", midLuopanRengongDataService.selectOneProjectUUid(null));
        return modelAndView;
    }

    /**
     * 罗盘数据录入 页面查询按钮点击后,进行查询,只查询出符合条件数据,最多八条
     * @param midluopanrengongdata
     * @return
     */
    @RequestMapping(value="midrengongdataquery" ,method ={RequestMethod.POST})
    public ModelAndView midrengongdataquery(MidLuopanRengongData midluopanrengongdata){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("midLuopanRengongData");
        modelAndView.addObject("result","查询成功");
        if(null==midluopanrengongdata.getProjectId()|| CommonUtil.isEmpty(midluopanrengongdata.getProjectId())){
            //如果日期或者类型为空,则让其跳转到录入列表
            List<MidLuopanRengongData> midluopanrengongdatalists = midLuopanRengongDataService.listRenwuMlrds(midluopanrengongdata);
            modelAndView.addObject("midluopanrengongdatalists",midluopanrengongdatalists);
        }else{
            modelAndView.addObject("queryprojectid", midluopanrengongdata.getProjectId());
            midluopanrengongdata = midLuopanRengongDataService.selectOne(midluopanrengongdata);
            modelAndView.addObject("result","查询物业云状态成功");
            if(null!=midluopanrengongdata.getMlrds()){
                modelAndView.addObject("statu","1");
            }
        }
        modelAndView.addObject("midluopanrengongdata", midluopanrengongdata);
        modelAndView.addObject("selectProjectId", midLuopanRengongDataService.selectOneProjectUUid(null));
        return modelAndView;
    }

    /**
     * 查询时,再次点击,将页面跳转至数据录入页面
     * @param midluopanrengongdata
     * @return
     */
    @RequestMapping(value="midrengongdataquery" ,method ={RequestMethod.GET})
    public ModelAndView midrengongdataqueryGet(MidLuopanRengongData midluopanrengongdata){
        ModelAndView modelAndView = initModelAndView();
        if(null ==midluopanrengongdata || null==midluopanrengongdata.getKey()||null==midluopanrengongdata.getDateValue()){
            modelAndView.setViewName("redirect:midLuopanRengongData");
        }
        return modelAndView;
    }

    /**
     * 添加之后,页面未发生跳转,再次点击跳转至数据录入页面
     * @param midluopanrengongdata
     * @param request
     * @return
     */
    @RequestMapping(value="midrengongdataadd" ,method ={RequestMethod.GET})
    public ModelAndView midrengongdataaddGet(MidLuopanRengongData midluopanrengongdata,HttpServletRequest request){
        ModelAndView modelAndView = initModelAndView();
        if(null==midluopanrengongdata||null==midluopanrengongdata.getMlrds()){
            modelAndView.setViewName("redirect:midLuopanRengongData");
        }
        return modelAndView;
    }

    /**
     * 社区下拉列表查询
     * @param q
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryprojectbyq")
    @ResponseBody
    public List<Map<String,Object>> queryprojectbyq(String q,String page) throws Exception {
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("q",q);
        map.put("page",page);
        return midLuopanRengongDataService.selectProjectByQ(map);
    }

    /**
     * 罗盘人工数据录入 录入物业云信息 保存
     * @param midluopanrengongdata
     * @param request
     * @return
     */
    @RequestMapping(value="midrengongdataaddwuyeyun" ,method ={RequestMethod.POST})
    public ModelAndView midrengongdataaddwuyeyun(MidLuopanRengongData midluopanrengongdata,HttpServletRequest request){
        //可以在这儿尝试getReaLName 获取真实用户名
        String username = CookieUtil.getCookieValue(request, Constant.USERNAME);
        ModelAndView modelAndView = initModelAndView();
        Map map = midLuopanRengongDataService.addOrUpdatewuyeyun(midluopanrengongdata,username);
        modelAndView.setViewName("midLuopanRengongData");
        modelAndView.addObject("key", "物业云实施状态");
        modelAndView.addObject("result",map);
        modelAndView.addObject("resultMap",map);
        modelAndView.addObject("midluopanrengongdata",midluopanrengongdata);
        modelAndView.addObject("queryprojectid", midLuopanRengongDataService.selectOneProjectUUid(midluopanrengongdata.getProjectId()).get("uuid"));
        return modelAndView;
    }

    @RequestMapping(value="midrengongdataaddwuyeyun" ,method ={RequestMethod.GET})
    public ModelAndView midrengongdataaddwuyeyunGet(MidLuopanRengongData midluopanrengongdata,HttpServletRequest request){
        ModelAndView modelAndView = initModelAndView();
        modelAndView.setViewName("redirect:midLuopanRengongData");
        return modelAndView;
    }
}
