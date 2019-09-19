package com.qding.bigdata.ds.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.Module;
import com.qding.bigdata.ds.model.Role;
import com.qding.bigdata.ds.model.RoleLevel;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.RoleService;
import com.qding.bigdata.ds.util.CommonUtil;

@Controller
public class ModuleController extends BasicController{

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleLevelService roleLevelService;

    @RequestMapping("/modulelist")
    public ModelAndView modulelist(Module moduleParam) {
        List<Module> moduleList = moduleService.listAll(moduleParam);
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("moduleList", moduleList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.模块管理);
        return modelAndView;
    }

    @RequestMapping("/moduleadd")
    public ModelAndView moduleadd() {
        ModelAndView modelAndView = initModelAndView();
        List<Module> allModules=moduleService.listAll(new Module());
        List<Module> allTreeModules=moduleService.sortToTree(allModules);
        modelAndView.addObject("allTreeModules", allTreeModules);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.模块管理);
        return modelAndView;
    }

    @RequestMapping("/modulesave")
    public String modulesave(Module moduleParam) {
        if (CommonUtil.isEmpty(moduleParam.getId())) {
            moduleService.save(moduleParam);
        }
        else {
            moduleService.update(moduleParam);
        }
        return "redirect:./modulelist";
    }

    @RequestMapping("/modulemanage")
    public ModelAndView modulemanage(Module moduleParam) {
        Module module = moduleService.getOne(moduleParam);

        ModelAndView modelAndView = initModelAndView("moduleadd");
        modelAndView.addObject("module", module);
        List<Module> allModules=moduleService.listAll(new Module());
        List<Module> allTreeModules=moduleService.sortToTree(allModules);
        modelAndView.addObject("allTreeModules", allTreeModules);
        HashSet<String> moduleRolesSet = new HashSet<String>();
        List<RoleLevel> listRoleLevelByModule = roleLevelService.listByModule(moduleParam);
        for (RoleLevel roleLevel : listRoleLevelByModule) {
            moduleRolesSet.add(roleLevel.getId());
        }
        modelAndView.addObject("moduleRoles", moduleRolesSet);

        List<Role> roleList = roleService.listAll(new Role());
        for (Role role : roleList) {

            roleService.putSubRoleLevels(role);
        }
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.模块管理);

        return modelAndView;
    }

    @RequestMapping("/moduledelete")
    public String moduledelete(Module moduleParam) {
        moduleService.delete(moduleParam);
        return "redirect:./modulelist";
    }
}
