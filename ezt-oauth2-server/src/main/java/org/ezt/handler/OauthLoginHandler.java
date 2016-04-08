package org.ezt.handler;

import org.base.constants.ExecuteStatus;
import org.base.pluging.ModelView;
import org.base.utils.*;
import org.ezt.common.OAuthContentStore;
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
    public ModelView handlerRequest(HttpServletRequest request, HttpServletResponse response) {
        String userAccount = request.getParameter("user_account");
        String passworld = request.getParameter("user_password");
        String clientId = request.getParameter("client_id");

        String openid = oauthService.userLogin(userAccount,passworld);

        OauthClientInfo clientInfo = oauthService.getOauthClient(clientId);

        Assert.expr(StrUtil.isNull(clientInfo), ExecuteStatus.unknown_client_id);

        clientInfo.setOpenid(openid);

        OAuthContentStore.EncryptClient encryptClient = OAuthContentStore.getInstance().encrypt(clientInfo);

        Map<String,Object> model = encryptClient.getMap();
        model.put("client_name",clientInfo.getClientName());

        return new ModelView("oauth_authorize.vm",model);
    }
}
