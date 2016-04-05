package org.ezt.service;

import org.ezt.views.OauthClientInfo;

/**
 * 授权服务逻辑层
 * Created by wangwr on 2016/4/5.
 */
public interface OAuthService {

    /**
     * 获取第三方平台信息
     * @param clientId 第三方平台标识
     * @return 第三方平台信息
     */
    OauthClientInfo getOauthClient(String clientId);

    /**
     * 用户登陆
     * @param userAccount 用户账号
     * @param password 用户密码
     * @return 用户id
     */
    Long userLogin(String userAccount,String password);

}
