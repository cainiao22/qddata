package com.qding.bigdata.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.enums.SideBarMenu;
import com.qding.bigdata.ds.model.Role;
import com.qding.bigdata.ds.service.ModuleService;
import com.qding.bigdata.ds.service.RoleService;
import com.qding.bigdata.ds.util.CommonUtil;

@Controller
public class RoleController  extends BasicController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/rolelist")
    public ModelAndView rolelist(Role roleParam) {
        List<Role> roleList = roleService.listAll(roleParam);
        for (Role role : roleList) {

            roleService.putSubRoleLevels(role);
        }
        ModelAndView modelAndView = this.initModelAndView();
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.角色管理);
        return modelAndView;
    }

    @RequestMapping("/roleadd")
    public ModelAndView roleadd() {
        ModelAndView modelAndView = this.initModelAndView();
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.角色管理);
        return modelAndView;
    }

    @RequestMapping("/rolesave")
    public String rolesave(Role roleParam) {

        if (CommonUtil.isEmpty(roleParam.getId())) {
            roleService.save(roleParam);
        } else {
            roleService.update(roleParam);
        }

        return "redirect:./rolelist";
    }

    @RequestMapping("/rolemanage")
    public ModelAndView rolemanage(Role roleParam) {
        Role role = roleService.getOne(roleParam);
        ModelAndView modelAndView = this.initModelAndView();
        modelAndView.addObject("role", role);
        modelAndView.setViewName("roleadd");
        modelAndView.addObject(Constant.SIDEBAR_MENU, SideBarMenu.角色管理);
        return modelAndView;
    }

    @RequestMapping("/roledelete")
    public String roledelete(Role roleParam) {
        roleService.delete(roleParam);
        return "redirect:./rolelist";
    }
}
