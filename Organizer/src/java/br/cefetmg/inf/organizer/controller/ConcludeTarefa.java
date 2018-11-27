package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.ItemTag;
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ConcludeTarefa implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        User user = new User();
        
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        
        // Pegando usuário
        user.setCodEmail((String) parameterMap.get("email"));
      
        int idItemInt = ((Double) parameterMap.get("takeId")).intValue();
        Long idItem = Integer.toUnsignedLong(idItemInt); 
        
        IKeepItem keepItem = new KeepItem();        
        Item item = keepItem.searchItemById(idItem);
        
        item.setIdentifierStatus("C");
        item.setUser(user);
        
        boolean result = keepItem.updateItem(item);
        
        if(!result){
        } else {
            IKeepTag keepTag = new KeepTag();
            Long idConclude = keepTag.searchTagByName("Concluidos", user);
            Tag concludeTag = keepTag.searchTagById(idConclude);
            ArrayList<Tag> tag = new ArrayList();
            tag.add(concludeTag);
           
            IKeepItemTag keepItemTag = new KeepItemTag();
            ItemTag itemTag = new ItemTag(); 
            itemTag.setItem(item);
            itemTag.setListTags(tag);
            
            result = keepItemTag.createTagInItem(itemTag);
        }
       
        return GsonUtil.toJson(result);
    }
    
}
