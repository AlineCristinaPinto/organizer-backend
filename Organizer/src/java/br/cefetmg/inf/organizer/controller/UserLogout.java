package br.cefetmg.inf.organizer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLogout implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";

        HttpSession session = req.getSession(false);

        if (session == null) {
           
        } else {
            session.invalidate();
            pageJSP = "/login.jsp";
        }

        return pageJSP;
    }

}
