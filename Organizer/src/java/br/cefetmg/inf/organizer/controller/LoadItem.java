package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoadItem implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String pageJSP = "";
        List<Item> itemList;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        String email = (String) parameterMap.get("email");
        
        User user = new User();
        user.setCodEmail(email);
        
        IKeepItem keepItem = new KeepItem();
        itemList = keepItem.listAllItem(user);
        
        /*IKeepTag keepTag = new KeepTag();
        List<Tag> tagList = keepTag.listAlltag(user);
        if (tagList == null) {
            req.getSession().setAttribute("tagList", new ArrayList());
        } else {
            req.getSession().setAttribute("tagList", tagList);
        }
        
        pageJSP = "/index.jsp";
        */
        
        return GsonUtil.toJson(itemList);
    
    }
    
}
