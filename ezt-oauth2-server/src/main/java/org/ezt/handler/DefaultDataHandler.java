package org.ezt.handler;

import org.base.utils.RandomUtil;
import org.ezt.common.OAuthCodeStore;
import org.oauth2.server.data.DataHandler;
import org.oauth2.server.models.AccessToken;
import org.oauth2.server.models.AuthInfo;
import org.oauth2.server.pluging.Request;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangwr on 2016/4/5.
 */
public class DefaultDataHandler extends DataHandler {

    public DefaultDataHandler(Request request) {
        super(request);
    }

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
        return OAuthCodeStore.getInstance().getAuthInfo(code);
    }

    @Override
    public AccessToken createOrUpdateAccessToken(AuthInfo authInfo) {
        AccessToken accessToken = new AccessToken();
        accessToken.setCreatedOn(new Date());
        accessToken.setExpiresIn(3*60*60);
        accessToken.setToken(RandomUtil.randomWords(RandomUtil.RandomType.MIXING,32));
        return accessToken;
    }

    @Override
    public AuthInfo getAuthInfoByRefreshToken(String refreshToken) {
        return null;
    }

    @Override
    public boolean validateClient(String clientId, String clientSecret, String grantType) {
        return true;
    }
}
