package org.ezt.controller;

import org.base.annotation.API;
import org.oauth2.server.data.DataHandler;
import org.oauth2.server.endpoint.AccessTokenHandler;
import org.oauth2.server.grant.GrantResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2.0授权
 * Created by wangwr on 2016.3.25.
 */
@RestController
public class Oauth2Controller {

    private AccessTokenHandler accessTokenHandler;

    @Autowired
    private DataHandler dataHandlerImpl;

    @API(name = "oauth2用户登录授权Code",params = {"client_id:第三方应用标识ID",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respData = "授權登录授权界面")
    @RequestMapping(value = "/oauth2/authorize",method = RequestMethod.GET)
    public void authorize(HttpServletRequest request, HttpServletResponse response){
        try {
            request.getRequestDispatcher("/authorize.html").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @API(name = "第三方平台获取token",params = {"client_id:第三方应用标识ID",
            "client_secret:第三方秘钥","grant_type:authorization_code",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respClass = GrantResult.class)
    @RequestMapping(value = "/oauth2/access_token",method = RequestMethod.GET)
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response){
        try {
            response.sendRedirect("http://www.baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
