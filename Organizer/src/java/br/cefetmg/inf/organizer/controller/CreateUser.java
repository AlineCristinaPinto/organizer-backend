package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.GsonUtil;
import br.cefetmg.inf.util.PasswordCriptography;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String result = "";

        String email = null;
        String name = null;
        String password = null;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        email = (String) parameterMap.get("email");
        name = (String) parameterMap.get("name");
        password = PasswordCriptography.generateMd5((String) parameterMap.get("password"));
        
        User user = new User();

        user.setCodEmail(email);
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserPhoto(null);
        user.setCurrentTheme(1);

        IKeepUser keepUser = new KeepUser();
        
        boolean success = keepUser.registerUser(user);
        
        //ArrayList response = new ArrayList();
        //response.add(success);
        result = GsonUtil.toJson(success); 
        
        return result;
    }

}
