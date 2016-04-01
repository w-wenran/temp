package org.oauth2.server.grant.impl;

import org.oauth2.server.data.DataHandler;
import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcher;
import org.oauth2.server.grant.GrantResult;
import org.oauth2.server.models.AuthInfo;
import org.oauth2.server.pluging.Request;

/**
 * Created by wangwr on 2016/3/30.
 */
public class RefreshTokenGrantHandler extends AbstractGrantHandler{


    @Override
    public GrantResult handleRequest(DataHandler dataHandler) {
        Request request = dataHandler.getRequest();
        ClientCredentialFetcher.ClientCredential clientCredential = getClientCredentialFetcher().fetch(request);
        String clientId = clientCredential.getClientId();
        String refreshToken = getParameter(request,"refresh_token");
        AuthInfo authInfo = dataHandler.getAuthInfoByRefreshToken(refreshToken);
        if(authInfo == null){
            throw new RuntimeException("invalid refresh_token");
        }
        if (!authInfo.getClientId().equals(clientId)) {
            throw new RuntimeException("invalid client_id");
        }
        return issueAccessToken(dataHandler,authInfo);
    }
}
