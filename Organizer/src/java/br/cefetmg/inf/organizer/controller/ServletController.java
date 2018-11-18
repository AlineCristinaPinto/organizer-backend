package br.cefetmg.inf.organizer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        String typeUser = request.getHeader("User-Type");
        boolean isMobile = false;

        if (typeUser == null) {
            typeUser = "web-client";
            request.setAttribute("user-type", typeUser);
        } else if (typeUser.equals("mobile-client")) {
            request.setAttribute("user-type", typeUser);
            isMobile = true;
        }

        String parameter = request.getParameter("process");//nome do campo que deve-se passar no form, seja por js ou html
        String className = "br.cefetmg.inf.organizer.controller." + parameter;

        try {
            Class classReference = Class.forName(className);
            GenericProcess genericProcess = (GenericProcess) classReference.newInstance();

            if (isMobile) {
                handleMobileRequest(request, response);
            }

            String result = genericProcess.execute(request, response);

            if (isMobile) {
                handleMobileResponse(request, response);
            } else {
                request.getRequestDispatcher(result).forward(request, response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Ocorreu um erro na execução " + ex.getMessage());
        }

    }

    private void handleMobileRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        Map<String, Object> retMap = new Gson().fromJson(
                data, new TypeToken<HashMap<String, Object>>() {
                }.getType());

        request.setAttribute("mobile-parameters", retMap);
    }

    private void handleMobileResponse(HttpServletRequest request, HttpServletResponse response) {

    }

}
