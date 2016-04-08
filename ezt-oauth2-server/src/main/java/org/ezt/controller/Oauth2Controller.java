package org.ezt.controller;

import org.base.annotation.API;
import org.base.constants.ExecuteStatus;
import org.base.exception.RuntimeExceptionWarning;
import org.base.pluging.APIProcessor;
import org.base.pluging.RequestHandler;
import org.base.pluging.ModelView;
import org.base.runtime.HttpServiceContext;
import org.base.utils.JsonUtil;
import org.ezt.handler.OauthHandler;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
import org.oauth2.server.data.DataHandlerFactory;
import org.oauth2.server.endpoint.AccessTokenHandler;
import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcher;
import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcherImpl;
import org.oauth2.server.grant.GrantHandlerProvider;
import org.oauth2.server.grant.GrantResult;
import org.oauth2.server.grant.impl.DefaultGrantHandlerProvider;
import org.oauth2.server.pluging.HttpServletRequestAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

/**
 * oauth2.0授权
 * Created by wangwr on 2016.3.25.
 */
@RestController
public class Oauth2Controller extends APIProcessor {

    @Autowired
    private OAuthService oauthService;

    @Autowired
    private DataHandlerFactory dataHandlerFactory;

    private ClientCredentialFetcher clientCredentialFetcher;

    private GrantHandlerProvider grantHandlerProvider;

    private AccessTokenHandler accessTokenHandler;


    public Oauth2Controller() {
        super();
        clientCredentialFetcher = new ClientCredentialFetcherImpl();
        grantHandlerProvider = new DefaultGrantHandlerProvider();
        accessTokenHandler = new AccessTokenHandler();
        accessTokenHandler.setClientCredentialFetcher(clientCredentialFetcher);
        accessTokenHandler.setGrantHandlerProvider(grantHandlerProvider);
    }

    @API(name = "oauth2用户登录授权Code",params = {"client_id:第三方应用标识ID",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respData = "登录界面")
    @RequestMapping(value = "/oauth2/authorize",method = RequestMethod.GET)
    public Object authorize(){
        return handleRequest(new RequestHandler() {
            @Override
            public Object execute() {
                HttpServletRequestAdapter requestAdapter = new HttpServletRequestAdapter(HttpServiceContext.getRequest());
                String clientId = requestAdapter.getParameter("client_id");
                OauthClientInfo clientInfo = oauthService.getOauthClient(clientId);
                Map<String, Object> model = JsonUtil.toMap(JsonUtil.toJson(clientInfo));
                model.put("response_type", requestAdapter.getParameter("response_type"));
                model.put("redirect_uri", requestAdapter.getParameter("redirect_uri"));
                return new ModelView("oauth_login.vm",model);
            }
        });
    }

    @API(name = "oauth2用户登录",respData = "用户授权界面")
    @RequestMapping(value = "/oauth2/authorize",method = RequestMethod.POST)
    public Object authorizeLogin(final HttpServletRequest request, final HttpServletResponse response){
        return handleRequest(new RequestHandler() {
            @Override
            public Object execute() {
                String action = request.getParameter("action");
                Iterator<OauthHandler> handlers = HttpServiceContext.getBeans(OauthHandler.class);
                while (handlers.hasNext()){
                    OauthHandler handler = handlers.next();
                    if(handler.getAction().match(action)){
                        return handler.handlerRequest(request,response);
                    }
                }
                throw new RuntimeExceptionWarning(ExecuteStatus.execute_failure);
            }
        });

    }

    @API(name = "第三方平台获取token",params = {"client_id:第三方应用标识ID",
            "client_secret:第三方秘钥","grant_type:authorization_code",
            "response_type:目前只支持code","redirect_uri:登录后跳转的第三方地址"},respClass = GrantResult.class)
    @RequestMapping(value = "/oauth2/access_token",method = RequestMethod.GET)
    public Object getAccessToken(){
        return handleRequest(new RequestHandler() {
            @Override
            public Object execute() {
                HttpServletRequestAdapter requestAdapter = new HttpServletRequestAdapter(HttpServiceContext.getRequest());
                accessTokenHandler.setDataHandlerFactory(dataHandlerFactory);
                return accessTokenHandler.handlerRequest(requestAdapter);
            }
        });
    }
}
