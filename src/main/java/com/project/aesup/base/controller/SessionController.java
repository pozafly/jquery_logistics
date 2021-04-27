package com.project.aesup.base.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionController implements HttpSessionListener {

    private int userCount;
    private ServletContext application;

    @Override
    public void sessionCreated(HttpSessionEvent SE) {

       HttpSession session = SE.getSession();
       application = session.getServletContext();

             if(application.getAttribute("userCount") == null) {
                application.setAttribute("userCount", 1);
             }else {
                userCount = (Integer)application.getAttribute("userCount");
                application.setAttribute("userCount", ++userCount);
             }
       
       System.out.println("접속자 수"+userCount);
       
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

       if(se.getSession().getServletContext().getAttribute("userCount") != null) {
          userCount = (Integer)application.getAttribute("userCount");
          application.setAttribute("userCount", --userCount);
       }
       
       System.out.println("접속자 수"+userCount);

    }
 }
