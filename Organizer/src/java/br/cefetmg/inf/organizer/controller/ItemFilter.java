package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.util.exception.PersistenceException;
import br.cefetmg.inf.util.GsonUtil;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemFilter implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        ArrayList<Tag> tagList = new ArrayList<>();
        ArrayList<String> typeList = new ArrayList<>();
        ArrayList<Item> itemList = new ArrayList();
        String[] tags;
        String[] types;
        String result;
        User user = new User();

        //booleans to check if the filter is being used
        boolean tagFiltering = false, typeFiltering = false;

        //getting values from the checkboxes
        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        tags = ((ArrayList<String>) parameterMap.get("tag")).toArray(new String[]{});
        types = ((ArrayList<String>) parameterMap.get("type")).toArray(new String[]{});
        user.setCodEmail((String) parameterMap.get("email"));

        //checking if there is any tag to filter
        if (tags.length > 0) {
            tagFiltering = true;
            for (String tagName : tags) {
                Tag tag = new Tag();
                tag.setTagName(tagName);
                tag.setUser(user);
                tagList.add(tag);
            }
        }

        //checking if there is any type to filter
        if (types.length > 0) {
            typeFiltering = true;
            for (String type : types) {
                typeList.add(type);
            }
        }

        IKeepItem keepItem = new KeepItem();

        try {
            if (tagFiltering && typeFiltering) {
                itemList = keepItem.searchItemByTagAndType(tagList, typeList, user);
            } else if (tagFiltering) {
                itemList = keepItem.searchItemByTag(tagList, user);
            } else if (typeFiltering) {
                itemList = keepItem.searchItemByType(typeList, user);
            } else {
                itemList = keepItem.listAllItem(user);
            }
        } catch (PersistenceException ex) {
            //Exception
        }

        boolean concluidoExists = false;

        try {
            for (Tag tag : tagList) {
                if (tag.getTagName().equals("Concluidos") && (itemList != null)) {
                    concluidoExists = true;
                    for (Item item : new ArrayList<>(itemList)) {
                        if (item.getIdentifierItem().equals("TAR")
                                && item.getIdentifierStatus().equals("A")) {
                            itemList.remove(item);
                        }
                    }
                }
            }

            if (!concluidoExists && (itemList != null)) {
                for (Item item : new ArrayList<Item>(itemList)) {
                    if (item.getIdentifierItem().equals("TAR")
                            && item.getIdentifierStatus().equals("C")) {
                        itemList.remove(item);
                    }
                }
            }
        } catch (NullPointerException ex) {
            //Exception
        }
        
        result = GsonUtil.toJson(itemList);

        return result;
    }
}
