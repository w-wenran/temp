package org.ezt.controller;

import org.apache.velocity.app.VelocityEngine;
import org.base.runtime.HttpServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 集成了spring Velocity
 * Created by wangwr on 2016/4/5.
 */
public class TemplateController {

    @Autowired
    private VelocityEngine velocityEngine;

    public void parseTemplate(String templateLocation,Map<String, Object> model){
        try {
            VelocityEngineUtils.mergeTemplate(this.velocityEngine,templateLocation,"UTF-8",model, HttpServiceContext.getResponse().getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
