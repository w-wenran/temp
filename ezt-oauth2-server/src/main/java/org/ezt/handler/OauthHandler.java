package org.ezt.handler;

import org.ezt.controller.TemplateController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权处理
 * Created by wangwr on 2016/4/5.
 */
public interface OauthHandler {

    ActionType getAction();

    void handlerRequest(HttpServletRequest request, HttpServletResponse response, TemplateController controller);

    enum ActionType{

        /**
         * 登陆
         */
        login,

        /**
         * 授权
         */
        authorize;

        public boolean match(String action){
            return this.name().equals(action);
        }
    }
}
