package org.base.pluging;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.velocity.app.VelocityEngine;
import org.base.common.ErrorBody;
import org.base.constants.ExecuteStatus;
import org.base.exception.RuntimeExceptionWarning;
import org.base.runtime.HttpServiceContext;
import org.base.utils.ExceptionUtil;
import org.base.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.IOException;

/**
 * Created by wangwr on 2016/4/7.
 */
public class APIProcessor {

    private static Logger logger = LoggerFactory.getLogger(APIProcessor.class);

    @Autowired
    private VelocityEngine velocityEngine;

    public Object handleRequest(RequestHandler handler,Boolean modelView){
        try {
            return processHandler(handler);
        }catch (Exception e){
            ErrorBody errorBody = ExceptionUtil.processException(e);
            if(modelView){
                return exceptionPage(errorBody);
            }
            return errorBody;
        }
    }

    public Object handleRequest(RequestHandler handler){
        return handleRequest(handler,false);
    }

    protected Object processHandler(RequestHandler handler) throws Exception {
        Object result = handler.execute();
        if(ModelView.class.isInstance(result)){//模板页面
            ModelView viewModel = (ModelView) result;
            return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,viewModel.getViewLocation(),"UTF-8",viewModel.getModelMap());
        }
        if(RedirectPage.class.isInstance(result)){
            RedirectPage redirectPage = (RedirectPage) result;
            HttpServiceContext.getResponse().sendRedirect(redirectPage.getUri());
            return "";
        }
        return result;
    }


    protected Object exceptionPage(ErrorBody errorBody){
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"error.vm","UTF-8",JsonUtil.toMap(JsonUtil.toJson(errorBody)));
    }


}
