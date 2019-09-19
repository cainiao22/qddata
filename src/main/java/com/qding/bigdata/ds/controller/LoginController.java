package com.qding.bigdata.ds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView login(String target) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("target", target == null ? "" : target);
        return modelAndView;

    }

}
