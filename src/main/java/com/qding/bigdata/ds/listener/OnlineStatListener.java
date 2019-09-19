package com.qding.bigdata.ds.listener;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.qding.bigdata.ds.common.Variable;

public class OnlineStatListener implements HttpSessionListener {


    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        HashSet<HttpSession> sessions = (HashSet) application.getAttribute("sessions");
        if(sessions!=null&&sessions.contains(session)){
        sessions.remove(session);
        }
        Variable.refreshSessionStat(sessions);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

}
