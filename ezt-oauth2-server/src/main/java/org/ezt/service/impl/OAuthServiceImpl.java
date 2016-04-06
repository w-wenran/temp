package org.ezt.service.impl;

import org.ezt.models.OAuthClient;
import org.ezt.repositories.OAuthAccessTokenRepository;
import org.ezt.repositories.OAuthClientRepository;
import org.ezt.repositories.OAuthUserClientRepository;
import org.ezt.repositories.OAuthUserRepository;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
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
    public Long userLogin(String userAccount, String password) {
        return Long.valueOf(12);
    }

}
