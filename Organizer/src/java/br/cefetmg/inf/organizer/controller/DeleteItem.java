package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepItemTag;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepItemTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DeleteItem implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        //List<Item> itemList;
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        String email = (String) parameterMap.get("email");
        Double idItem = (Double) parameterMap.get("id");
        String idItemString = Double.toString(idItem);
        
        idItemString = idItemString.substring(0, idItemString.length()-2);
        
        User user = new User();
        user.setCodEmail(email);
        
        Long id = Long.parseLong(idItemString); 
       
        IKeepItem keepItem = new KeepItem();
        boolean result = keepItem.deleteItem(id, user);
       
        /*if(!result){
        } else {
            
            IKeepItemTag keepItemTag = new KeepItemTag();
            result = keepItemTag.deleteTagByItemId(idItem);
            
            if(!result){

            } else {
                
                itemList = keepItem.listAllItem(user);
                if(itemList == null){
                    req.setAttribute("itemList", new ArrayList());
                }else{
                    req.setAttribute("itemList", itemList);
                }
                
                IKeepTag keepTag = new KeepTag();
                List<Tag> tagList = keepTag.listAlltag(user);
                if (tagList == null) {
                    req.getSession().setAttribute("tagList", new ArrayList());
                } else {
                   req.getSession().setAttribute("tagList", tagList);
                }
                
                pageJSP = "/index.jsp";
            
            }
        }*/
        return GsonUtil.toJson(result);
        
    }    
}
