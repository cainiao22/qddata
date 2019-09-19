package com.qding.bigdata.ds.common;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpSession;

public class Variable {
    public static HashMap<String, String> onlineUser = new HashMap<String, String>();

    public static void refreshSessionStat(HashSet<HttpSession> sessions) {
        if(sessions==null){
            return;
        }
        onlineUser.clear();
        for (HttpSession httpSession : sessions) {
            if (httpSession.getAttribute(Constant.USERNAME) != null){
                onlineUser.put((String) httpSession.getAttribute(Constant.USERNAME),
                        (String) httpSession.getAttribute(Constant.REALNAME));
            }

        }
    }

}
