package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.ErrorObject;
import br.cefetmg.inf.util.GsonUtil;
import br.cefetmg.inf.util.PasswordCriptography;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String result = "";

        String userType = (String) req.getAttribute("user-type");
        boolean isMobile = false;
        
        String email = null;
        String name = null;
        String password = null;
        
        if (userType.equals("mobile-client")) {
            Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
            email = (String) parameterMap.get("email");
            name = (String) parameterMap.get("name");
            password = (String) parameterMap.get("password");
            isMobile = true;
        } else if (userType.equals("web-client")) {
            email = req.getParameter("email");
            name = req.getParameter("name");
            password = PasswordCriptography.generateMd5(req.getParameter("password"));
        }

        User user = new User();

        user.setCodEmail(email);
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserPhoto(null);
        user.setCurrentTheme(1);

        IKeepUser keepUser = new KeepUser();
        boolean success = keepUser.registerUser(user);

        if (!success) {
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro na criação do usuário");
            error.setErrorSubtext("Verifique se o usuário já existe ou se ocorreu um erro no preenchimento dos campos");
            req.getSession().setAttribute("error", error);
            
            if(isMobile){
              result = GsonUtil.toJson("false");
            }else{
              result = "/errorLogin.jsp";
            }
           
        } else {
            if(isMobile){
                result = GsonUtil.toJson("true");
            }else{
                result = "/login.jsp";
            }
            
        }

        return result;
    }

}
