package org.base.pluging;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.velocity.app.VelocityEngine;
import org.base.constants.ExecuteStatus;
import org.base.exception.RuntimeExceptionWarning;
import org.base.runtime.HttpServiceContext;
import org.base.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Created by wangwr on 2016/4/7.
 */
public class APIProcessor {

    @Autowired
    private VelocityEngine velocityEngine;

    public Object handleRequest(RequestHandler handler){
        try {
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
        }catch (Exception e){
            ErrorBody errorBody = new ErrorBody(ExecuteStatus.server_busy.name(),ExecuteStatus.server_busy.getCode(),ExecuteStatus.server_busy.getDescription());
            if(RuntimeExceptionWarning.class.isInstance(e)){
                RuntimeExceptionWarning warning = (RuntimeExceptionWarning) e;
                errorBody = new ErrorBody(warning.getError(),warning.getErrorCode(),warning.getErrorDescription());
            }
            return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"error.vm","UTF-8",JsonUtil.toMap(JsonUtil.toJson(errorBody)));
        }
    }

    public static class ErrorBody{

        @JsonProperty("error")
        private String error;

        @JsonProperty("error_code")
        private String errorCode;

        @JsonProperty("error_description")
        private String errorDescription;

        public ErrorBody(String error, String errorCode, String errorDescription) {
            this.error = error;
            this.errorCode = errorCode;
            this.errorDescription = errorDescription;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }
    }
}
