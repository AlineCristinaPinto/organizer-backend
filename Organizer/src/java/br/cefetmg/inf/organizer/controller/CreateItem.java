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

        List<Item> itemList;
        
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

        // Pega as tags e inserem no arrayList buscando o id delas para
        // conseguir inserir no itemtag
        /*String tag = req.getParameter("inputTag");
        ArrayList<Tag> tagItem = new ArrayList();

        if (!tag.isEmpty()) {
            String[] vetTag = tag.split(";");

            IKeepTag keepTag = new KeepTag();

            for (String vetTag1 : vetTag) {
                if (keepTag.searchTagByName(vetTag1.trim(), user) == null) {
                    System.out.println("APOSTO 50 CENTAVOS QUE O ERRO TA AQ");
                } else {
                    Tag tagOfUser = new Tag();

                    tagOfUser.setSeqTag(keepTag.searchTagByName(vetTag1.trim(), user));
                    tagOfUser.setTagName(vetTag1.trim());
                    tagOfUser.setUser(user);

                    tagItem.add(tagOfUser);
                }
            }
        }*/

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

/*        if (!result) {
        } else {

            if (!tag.isEmpty()) {
                // busca a pk de item já que ela é uma seq e necessária para
                // inserir as tags relacionadas ao item em itemtag
                Item itemWithId = keepItem.searchItemByName(name);

                if (itemWithId == null) {

                } else {

                    // Adicionando os dados do item e tag a tabela itemtag
                    ItemTag itemTag = new ItemTag();

                    itemTag.setItem(itemWithId);

                    // inserindo o array list de tag aqui
                    itemTag.setListTags(tagItem);

                    // enfim adicionando as tags do item
                    // Lembrar de fazer o Proxy
                    IKeepItemTag keepItemTag = new KeepItemTag();
                    result = keepItemTag.createTagInItem(itemTag);

                    if (!result) {
                    } else {
                        itemList = keepItem.listAllItem(user);
                        if (itemList == null) {
                            req.setAttribute("itemList", new ArrayList());
                        } else {
                            req.setAttribute("itemList", itemList);
                        }

                        IKeepTag keepTag = new KeepTag();
                        List<Tag> tagList = keepTag.listAlltag(user);
                        if (tagList == null) {
                            req.setAttribute("tagList", new ArrayList());
                        } else {
                            req.setAttribute("tagList", tagList);
                        }

                        pageJSP = "/index.jsp";
                    }
                }
            } else {
                itemList = keepItem.listAllItem(user);

                if (itemList == null) {
                    req.setAttribute("itemList", new ArrayList());
                } else {
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

        }
}
