package org.ezt.handler;

import org.base.utils.DesUtil;
import org.base.utils.JsonUtil;
import org.base.utils.RandomUtil;
import org.ezt.controller.TemplateController;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangwr on 2016/4/5.
 */
@Component
public class OauthLoginHandler implements OauthHandler {

    @Autowired
    private OAuthService oauthService;

    @Override
    public ActionType getAction() {
        return ActionType.login;
    }

    @Override
    public void handlerRequest(HttpServletRequest request, HttpServletResponse response, TemplateController controller) {
        String userAccount = request.getParameter("user_account");
        String passworld = request.getParameter("user_password");
        String clientId = request.getParameter("client_id");
        String token = RandomUtil.randomWords(RandomUtil.RandomType.MIXING,16);
        String openid = oauthService.userLogin(userAccount,passworld);
        OauthClientInfo clientInfo = oauthService.getOauthClient(clientId);
        clientInfo.setOpenid(openid);
        Map<String,Object> model = new HashMap<String, Object>();
        model.put("client_name",clientInfo.getClientName());
        model.put("oauth_token", token);
        model.put("oauth_content", DesUtil.encrypt(JsonUtil.toJson(clientInfo),token));
        controller.parseTemplate("oauth_authorize.vm",model);
    }
}
