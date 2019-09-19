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
import com.qding.bigdata.ds.model.User;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.RoleLevelService;
import com.qding.bigdata.ds.service.RoleService;
import com.qding.bigdata.ds.service.UserService;
import com.qding.bigdata.ds.util.CommonUtil;

@Controller
public class RoleLevelController extends BasicController{

    @Autowired
    private RoleLevelService roleLevelService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private UserService userService;

    @RequestMapping("/roleLevelAdd")
    public ModelAndView roleLevelAdd() {
        List<Role> roleAllList = roleService.listAll(new Role());
        ModelAndView modelAndView = initModelAndView();
        modelAndView.addObject("roleAllList", roleAllList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.角色管理);
        return modelAndView;
    }

    @RequestMapping("/roleLevelSave")
    public String rolesave(RoleLevel roleLevelParam) {

        if (CommonUtil.isEmpty(roleLevelParam.getId())) {
            roleLevelService.save(roleLevelParam);
        } else {
            roleLevelService.update(roleLevelParam);
        }

        return "redirect:./rolelist";
    }

    @RequestMapping("/roleLevelManage")
    public ModelAndView roleLevelManage(RoleLevel roleLevelParam) {
        RoleLevel roleLevel = roleLevelService.getOne(roleLevelParam);
        List<Module> moduleListAll = moduleService.listAll(new Module());
        List<Module> roleModules = moduleService.listByRoleLevel(roleLevelParam);
        HashSet<String> roleModulesSet = new HashSet<String>();
        for (Module module : roleModules) {
            roleModulesSet.add(module.getId());
        }

        List<User> userListAll = userService.listAll(new User());
        List<User> userListInRole = userService.listByRoleLevel(roleLevelParam);
        ModelAndView modelAndView =initModelAndView();
        modelAndView.addObject("moduleList", moduleListAll);
        modelAndView.addObject("roleModulesSet", roleModulesSet);
        modelAndView.addObject("userListAll", userListAll);
        modelAndView.addObject("userListInRole", userListInRole);
        modelAndView.addObject("roleLevel", roleLevel);
        List<Role> roleAllList = roleService.listAll(new Role());
        modelAndView.addObject("roleAllList", roleAllList);
        modelAndView.setViewName("roleLevelAdd");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.角色管理);
        return modelAndView;
    }

    @RequestMapping("/roleLevelDelete")
    public String roleLevelDelete(RoleLevel roleLevelParam) {
        roleLevelService.delete(roleLevelParam);
        return "redirect:./rolelist";
    }
}
