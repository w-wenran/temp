package org.ezt.service.impl;

import org.base.constants.ExecuteStatus;
import org.base.exception.RuntimeExceptionWarning;
import org.base.utils.Assert;
import org.base.utils.MD5Util;
import org.base.utils.StrUtil;
import org.ezt.models.*;
import org.ezt.repositories.*;
import org.ezt.service.OAuthService;
import org.ezt.views.OauthClientInfo;
import org.oauth2.server.models.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;
import sun.security.provider.MD5;

import java.security.MessageDigest;

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

    @Autowired
    private OAuthRefreshTokenRepository oAuthRefreshTokenRepository;

    @Autowired
    private EztUserRepository eztUserRepository;

    @Override
    public OAuthClient getOauthClient(String clientId) {
        OAuthClient oAuthClient = oauthClientRepository.findOne(clientId);
        if(oAuthClient==null){
            throw new RuntimeExceptionWarning(ExecuteStatus.unknown_client_id);
        }
        return oAuthClient;
    }

    @Override
    public OauthClientInfo getClientInfo(String clientId) {
        return new OauthClientInfo(getOauthClient(clientId));
    }

    @Override
    public String userLogin(String userAccount, String password) {
        //验证密码
        Assert.notEmpty(userAccount,"登陆账号不能为空");

        Assert.notEmpty(password,"登陆密码不能为空");

        EztUser eztUser = eztUserRepository.findByMobileOrNumber(userAccount);

        Assert.expr(StrUtil.isNull(eztUser),ExecuteStatus.unknown_user_account);

        String md5Password = MD5Util.MD5(password);
        Assert.expr(!(eztUser.getEuPassword().equals(md5Password)),ExecuteStatus.password_error);

        OAuthUser oauthUser = oauthUserRepository.findByUserId(eztUser.getId());

        //不存在则生成新的openid与用户进行绑定
        if(StrUtil.isNull(oauthUser)){
            oauthUser=createOauthUser(eztUser);
        }
        return oauthUser.getOpenid();
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

        OAuthRefreshToken oauthRefreshToken = new OAuthRefreshToken();
        oauthRefreshToken.setClientId(clientInfo.getClientId());
        oauthRefreshToken.setOpenid(clientInfo.getOpenid());
        oauthRefreshToken.setRefreshToken(OAuthRefreshToken.generatedToken());
        oauthRefreshToken.setExpiresIn(OAuthRefreshToken.expiresInTime());

        return oAuthRefreshTokenRepository.saveAndFlush(oauthRefreshToken);
    }

    @Override
    public OAuthRefreshToken getRefreshToken(String refreshToken) {
        return oAuthRefreshTokenRepository.findByRefreshToken(refreshToken);
    }

    /**
     * 新建openid与原来的用户进行绑定
     * @param eztUser
     * @return
     */
    private OAuthUser createOauthUser(EztUser eztUser){
        OAuthUser oauthUser = new OAuthUser();
        oauthUser.setOpenid(OAuthUser.generatedOpenid());
        oauthUser.setUserId(eztUser.getId());
        oauthUserRepository.saveAndFlush(oauthUser);
        return oauthUser;
    }
}
