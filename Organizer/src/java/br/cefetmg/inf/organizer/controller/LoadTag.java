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

public class LoadTag implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Tag> tagList;

        Map<String, Object> parameterMap = (Map<String, Object>) req.getAttribute("mobile-parameters");
        String email = (String) parameterMap.get("email");

        User user = new User();
        user.setCodEmail(email);

        IKeepTag keepTag = new KeepTag();
        tagList = keepTag.listAlltag(user);

        return GsonUtil.toJson(tagList);
    }
}