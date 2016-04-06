package org.ezt.service.impl;

import org.ezt.models.OAuthAccessToken;
import org.ezt.models.OAuthClient;
import org.ezt.models.OAuthRefreshToken;
import org.ezt.repositories.OAuthAccessTokenRepository;
import org.ezt.repositories.OAuthClientRepository;
import org.ezt.repositories.OAuthUserClientRepository;
import org.ezt.repositories.OAuthUserRepository;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
import org.oauth2.server.models.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangwr on 2016/4/5.
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private OAuthUserRepository oauthUserRepository;

    @Autowired
    private OAuthClientRepository oauthClientRepository;

    @Autowired
    private OAuthUserClientRepository oauthUserClientRepository;

    @Autowired
    private OAuthAccessTokenRepository grantAccessTokenRepository;

    @Override
    public OauthClientInfo getOauthClient(String clientId) {
        OAuthClient oAuthClient = oauthClientRepository.findOne(clientId);
        if(oAuthClient==null){
            throw new RuntimeException("无效的客户端");
        }
        return new OauthClientInfo(oAuthClient);
    }

    @Override
    public String userLogin(String userAccount, String password) {
        //验证密码成功

        return "";
    }

    @Override
    public OAuthAccessToken createOrUpdateAccessToken(AuthInfo authInfo) {
        OAuthAccessToken token = new OAuthAccessToken();
        token.setClientId(authInfo.getClientId());
        token.setRefreshToken(authInfo.getRefreshToken());
        token.setOpenid(authInfo.getUserId());
        token.setAccessToken(OAuthAccessToken.generatedToken());
        token.setExpiresIn(OAuthAccessToken.expiresInTime());
        grantAccessTokenRepository.saveAndFlush(token);
        return token;
    }

    @Override
    public OAuthRefreshToken createRefreshToken(OauthClientInfo clientInfo) {
        return null;
    }
}
