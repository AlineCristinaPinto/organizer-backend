
package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.GsonUtil;
import br.cefetmg.inf.util.PasswordCriptography;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String result = "";

        String name = null;
        String password = null;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        name = (String) parameterMap.get("name");
        password = PasswordCriptography.generateMd5((String) parameterMap.get("password"));
        
        
        User user = (User) req.getSession().getAttribute("user");
        User tempUser = new User();
        
        if (name == null || name.isEmpty() ) {
            name = user.getUserName();
        }
        
        if (password == null || password.isEmpty()) {
            password = user.getUserPassword();
        } else {
            password = PasswordCriptography.generateMd5(password);
        }

        tempUser.setCodEmail(user.getCodEmail());
        tempUser.setUserName(name);
        tempUser.setUserPassword(password);
        tempUser.setCurrentTheme(user.getCurrentTheme());

        IKeepUser keepUser = new KeepUser();
        boolean success = keepUser.updateUser(tempUser);
        
        if (success){
            /*
            req.getSession().setAttribute("user",tempUser);
            
            IKeepTag keepTag = new KeepTag();
            List<Tag> tagList = keepTag.listAlltag(user);
            if (tagList == null) {
                req.getSession().setAttribute("tagList", new ArrayList());
            } else {
                req.getSession().setAttribute("tagList", tagList);
            }
            */
        }
        
        result = GsonUtil.toJson(success);
        return result;
    }

}
