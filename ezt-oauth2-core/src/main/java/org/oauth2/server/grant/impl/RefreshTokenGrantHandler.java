package org.oauth2.server.grant.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    public Response handleRequest(DataHandler dataHandler) {
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
        GrantResult result = issueAccessToken(dataHandler,authInfo);
        return new Response(result.getTokenType(),result.getAccessToken(),result.getExpiresIn());
    }

    @JsonPropertyOrder({
            "token_type",
            "access_token",
            "expires_in"})
    public static class Response{

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private Long expiresIn;


        public Response(String tokenType, String accessToken, Long expiresIn) {
            this.tokenType = tokenType;
            this.accessToken = accessToken;
            this.expiresIn = expiresIn;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(Long expiresIn) {
            this.expiresIn = expiresIn;
        }
    }
}
