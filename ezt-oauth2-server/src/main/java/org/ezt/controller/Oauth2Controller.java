package org.ezt.controller;

import org.apache.velocity.app.VelocityEngine;
import org.base.annotation.API;
import org.base.runtime.HttpServiceContext;
import org.base.utils.DesUtil;
import org.base.utils.JsonUtil;
import org.base.utils.RandomUtil;
import org.ezt.handler.OauthHandler;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
import org.oauth2.server.endpoint.AccessTokenHandler;
import org.oauth2.server.grant.GrantResult;
import org.oauth2.server.models.AuthInfo;
import org.oauth2.server.pluging.HttpServletRequestAdapter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * oauth2.0授权
 * Created by wangwr on 2016.3.25.
 */
@RestController
public class Oauth2Controller extends TemplateController{

    private AccessTokenHandler accessTokenHandler;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private OAuthService oauthService;

    @API(name = "oauth2用户登录授权Code",params = {"client_id:第三方应用标识ID",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respData = "授權登录授权界面")
    @RequestMapping(value = "/oauth2/authorize",method = RequestMethod.GET)
    public void authorize(HttpServletRequest req, HttpServletResponse response){
        try {
            HttpServletRequestAdapter requestAdapter = new HttpServletRequestAdapter(req);
            String clientId = requestAdapter.getParameter("client_id");
            OauthClientInfo clientInfo = oauthService.getOauthClient(clientId);
            Map<String, Object> model = JsonUtil.toMap(JsonUtil.toJson(clientInfo));
            model.put("response_type", requestAdapter.getParameter("response_type"));
            model.put("redirect_uri", requestAdapter.getParameter("redirect_uri"));
            super.parseTemplate("oauth_login.vm",model);
        }  catch (Exception e) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("message",e.getMessage());
            super.parseTemplate("error.vm",model);
        }
    }

    @API(name = "oauth2用户登录",respData = "用户授权界面")
    @RequestMapping(value = "/oauth2/authorize",method = RequestMethod.POST)
    public void authorizeLogin(HttpServletRequest request,HttpServletResponse response){
        String action = request.getParameter("action");
        Iterator<OauthHandler> handlers = HttpServiceContext.getBeans(OauthHandler.class);
        while (handlers.hasNext()){
            OauthHandler handler = handlers.next();
            if(handler.getAction().match(action)){
                handler.handlerRequest(request,response,this);
            }
        }
    }

    @API(name = "第三方平台获取token",params = {"client_id:第三方应用标识ID",
            "client_secret:第三方秘钥","grant_type:authorization_code",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respClass = GrantResult.class)
    @RequestMapping(value = "/oauth2/access_token",method = RequestMethod.GET)
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response){
        HttpServletRequestAdapter requestAdapter = new HttpServletRequestAdapter(request);


        try {
            response.sendRedirect("http://www.baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
