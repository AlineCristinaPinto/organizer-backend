package br.cefetmg.inf.organizer.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        String parameter = request.getParameter("process");//nome do campo que deve-se passar no form, seja por js ou html
        String className = "br.cefetmg.inf.organizer.controller." + parameter;
        try {
            Class classReference = Class.forName(className);
            GenericProcess genericProcess = (GenericProcess) classReference.newInstance();
            handleMobileRequest(request, response);
            String result = genericProcess.execute(request, response);
            handleMobileResponse(request, response, result);
            
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

    private void handleMobileResponse(HttpServletRequest request, HttpServletResponse response, String result) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(result);
        out.flush();
    }

}
