package org.ezt.service;

import org.oauth2.server.data.DataHandler;
import org.oauth2.server.models.AccessToken;
import org.oauth2.server.models.AuthInfo;
import org.springframework.stereotype.Service;

/**
 * Created by wangwr on 2016/3/31.
 */
@Service
public class DataHandlerImpl extends DataHandler {

    @Override
    public AccessToken getAccessToken(String accessToken) {
        return null;
    }

    @Override
    public AuthInfo getAuthInfo(Long authId) {
        return null;
    }

    @Override
    public AuthInfo getAuthInfoByCode(String code) {
        return null;
    }

    @Override
    public AccessToken createOrUpdateAccessToken(AuthInfo authInfo) {
        return null;
    }

    @Override
    public AuthInfo getAuthInfoByRefreshToken(String refreshToken) {
        return null;
    }

    @Override
    public boolean validateClient(String clientId, String clientSecret, String grantType) {
        return false;
    }
}
