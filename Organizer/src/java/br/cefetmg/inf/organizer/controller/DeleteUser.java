
package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.GsonUtil;
import br.cefetmg.inf.util.PasswordCriptography;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String result = "";
        IKeepUser keepUser = new KeepUser();
        
        String email =null;
        String password = null;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        email = (String) parameterMap.get("email");
        password = (String) PasswordCriptography.generateMd5((String) parameterMap.get("password"));

        User newUser = new User();
        
        newUser.setCodEmail(email);
        User user = keepUser.searchUser(newUser);

        if (!(password.equals(user.getUserPassword()))) {
            result = GsonUtil.toJson(false);
        } else {
     
            boolean success = keepUser.deleteAccount(user);
            result = GsonUtil.toJson(success);
        }

        return result;
    }

}
