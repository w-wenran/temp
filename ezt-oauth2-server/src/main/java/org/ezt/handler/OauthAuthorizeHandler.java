package org.ezt.handler;

import org.base.utils.DesUtil;
import org.base.utils.JsonUtil;
import org.ezt.common.OAuthCodeStore;
import org.ezt.controller.TemplateController;
import org.ezt.views.OauthClientInfo;
import org.oauth2.server.models.AuthInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户授权模块
 * Created by wangwr on 2016/4/5.
 */
@Component
public class OauthAuthorizeHandler implements OauthHandler {

    @Override
    public ActionType getAction() {
        return ActionType.authorize;
    }

    @Override
    public void handlerRequest(HttpServletRequest request, HttpServletResponse response, TemplateController controller) {
        String token = request.getParameter("oauth_token");
        String content = request.getParameter("oauth_content");
        String data = DesUtil.decrypt(content,token);
        OauthClientInfo oauthClientInfo = JsonUtil.toBean(data,OauthClientInfo.class);
        AuthInfo authInfo = new AuthInfo();
        authInfo.setRedirectUri(oauthClientInfo.getRedirectUri());
        authInfo.setClientId(oauthClientInfo.getClientId());
        authInfo.setUserId(oauthClientInfo.getUserId().toString());
        String code = OAuthCodeStore.getInstance().addAuthInfo(authInfo);
        try {
            response.sendRedirect(oauthClientInfo.getRedirectUri()+"?code="+code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
