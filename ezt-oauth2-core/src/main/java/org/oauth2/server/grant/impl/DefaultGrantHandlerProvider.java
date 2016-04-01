package org.oauth2.server.grant.impl;

import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcher;
import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcherImpl;
import org.oauth2.server.grant.GrantHandler;
import org.oauth2.server.grant.GrantHandlerProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangwr on 2016/3/30.
 */
public class DefaultGrantHandlerProvider extends GrantHandlerProvider {

    public DefaultGrantHandlerProvider(){
        super();

        Map<String,GrantHandler> handlers = new HashMap<String, GrantHandler>();

        ClientCredentialFetcher fetcher = new ClientCredentialFetcherImpl();

        AuthorizationCodeGrantHandler authorizationCodeGrantHandler = new AuthorizationCodeGrantHandler();
        authorizationCodeGrantHandler.setClientCredentialFetcher(fetcher);
        handlers.put("refresh_token",authorizationCodeGrantHandler);

        RefreshTokenGrantHandler refreshTokenGrantHandler = new RefreshTokenGrantHandler();
        handlers.put("authorization_code",refreshTokenGrantHandler);

        this.setHandlers(handlers);

    }
}
