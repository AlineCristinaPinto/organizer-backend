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
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateItem implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String selectType = null;
        String name = null;
        String description = null;
        String datItem = null;
        String email = null;
        
        User user = new User();
        
        // Pega os dados dos inputs
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        selectType = (String) parameterMap.get("typeItem");
        name = (String) parameterMap.get("nameItem");
        description = (String) parameterMap.get("descriptionItem");
        datItem = (String) parameterMap.get("dateItem");
        email = (String) parameterMap.get("codEmail");
        
        user.setCodEmail(email);
        // Tratamento de data
        Date dateItem;
        if (selectType.equals("SIM")) {
            dateItem = null;
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateItem = formatter.parse(datItem);
        }

        // Instanciando item para inserir
        Item item = new Item();

        item.setNameItem(name);
        item.setDescriptionItem(description);
        item.setIdentifierItem(selectType);
        item.setDateItem(dateItem);
        item.setUser(user);

        // se o item for uma tarefa o status não pode ser null
        if (selectType.equals("TAR")) {
            item.setIdentifierStatus("A");
        } else {
            item.setIdentifierStatus(null);
        }

        // Inserção do item mas sem a tag        
        IKeepItem keepItem = new KeepItem();
        boolean result = keepItem.createItem(item);
        
        String success = GsonUtil.toJson(result);
        
        return success;

        }
}
