package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.GsonUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTag implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Map<String,Object> parameterMap = (Map<String,Object>) req.getAttribute("mobile-parameters");
        String email = (String) parameterMap.get("email");
        String nameTag = (String) parameterMap.get("nameTag");

        User user = new User();
        user.setCodEmail(email);      

        Tag tag = new Tag();
        tag.setTagName(nameTag);
        tag.setUser(user);
        tag.setSeqTag(null);

        IKeepTag keepTag = new KeepTag();
        boolean result = keepTag.createTag(tag);
        
        String success = GsonUtil.toJson(result);
        
        return success;
    }
}