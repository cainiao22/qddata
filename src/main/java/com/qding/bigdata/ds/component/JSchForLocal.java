package com.qding.bigdata.ds.component;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author yanpf
 * @date 2019/4/9 14:39
 * @description
 */
@Component
public class JSchForLocal implements ApplicationContextAware {

    Logger log = LoggerFactory.getLogger(JSchForLocal.class);

    @Value("${jsch.enabled:false}")
    Boolean enableJsch;
    /**
     * 本地端口
     */
    static int lport = 3366;

    static int lportDs = 3368;

    static String rhostDs = "10.50.8.112";
    /**
     * 远程MySQL服务器
     */
    static String rhost = "mysql-qddata.qiandingyun.com";
    /**
     * 远程MySQL服务端口
     */
    static int rport = 3306;

    Session session = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(!enableJsch){
            return;
        }
        //SSH连接用户名
        String user = "mysql";
        //SSH密码
        String password = "mysql";
        //SSH服务器
        String host = "10.37.251.84";
        //SSH访问端口
        int port = 22;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();
            //这里打印SSH服务器版本信息
            log.info(session.getServerVersion());
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            int assigned_port_ds = session.setPortForwardingL(lportDs, rhostDs, rport);
            log.info("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
            log.info("assigned_port_ds:" + assigned_port_ds + " -> " + rhostDs + ":" + rport);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @PreDestroy
    public void onClose(){
        if(session != null){
            session.disconnect();
        }
    }
}
