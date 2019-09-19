package com.qding.bigdata.ds.config;

import com.qding.bigdata.ds.common.Constant;
import com.qding.bigdata.ds.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yanpf
 * @date 2018/5/29 15:26
 * @description
 */

@Component
public class EnvConfig {

    //private static boolean envLocal = Boolean.valueOf(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, "env.local", "false"));

    private static boolean envLocal;

    public static boolean isEnvLocal() {
        return envLocal;
    }

    @Value("${env.local}")
    public void setEnvLocal(boolean envLocal) {
        EnvConfig.envLocal = envLocal;
    }
}
