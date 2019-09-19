package com.qding.bigdata.ds.listener;

import com.qding.bigdata.ds.service.UserProfileV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class InitDataGenerate implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserProfileV2Service userProfileV2Service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }



}
