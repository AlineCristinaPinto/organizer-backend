
package br.cefetmg.inf.organizer.controller;


import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.GsonUtil;
import br.cefetmg.inf.util.PasswordCriptography;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserLogin implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String result = "";
        List<Item> itemList;
        
        String email = null;
        String password = null;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        email = (String) parameterMap.get("email");
        password = PasswordCriptography.generateMd5((String) parameterMap.get("password"));
        
        IKeepUser keepUser = new KeepUser();
        User user = keepUser.getUserLogin(email, password);
        
        
            /*
            IKeepItem keepItem = new KeepItem();
            itemList = keepItem.listAllItem(user);
            if(itemList == null){
                req.setAttribute("itemList", new ArrayList());
            }else{
                req.setAttribute("itemList", itemList);
            }
            IKeepTag keepTag = new KeepTag();
            List<Tag> tagList = keepTag.listAlltag(user);
            if(tagList == null){
                req.getSession().setAttribute("tagList", new ArrayList());
            }else{
               req.getSession().setAttribute("tagList", tagList);
            }
            result = "/index.jsp";
            */
            
            
            
        
        result = GsonUtil.toJson(user);
        
        return result;
    }
    
}
