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

public class DeleteTag implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Map<String, Object> parameterMap = (Map<String, Object>) req.getAttribute("mobile-parameters");
        String email = (String) parameterMap.get("email");
        String nameTag = (String) parameterMap.get("nameTag");

        User user = new User();
        user.setCodEmail(email);

        IKeepTag keepTag = new KeepTag();

        Tag tag = new Tag();
        tag.setSeqTag(keepTag.searchTagByName(nameTag, user));
        tag.setTagName(nameTag);
        tag.setUser(user);

        boolean result = keepTag.deleteTag(tag);

        String success = GsonUtil.toJson(result);

        return success;
    }
}
