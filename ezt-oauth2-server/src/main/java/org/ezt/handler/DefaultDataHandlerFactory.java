package org.ezt.handler;

import org.oauth2.server.data.DataHandler;
import org.oauth2.server.data.DataHandlerFactory;
import org.oauth2.server.pluging.Request;
import org.springframework.stereotype.Component;

/**
 *
 * Created by wangwr on 2016/4/5.
 */
@Component
public class DefaultDataHandlerFactory implements DataHandlerFactory {

    @Override
    public DataHandler create(Request request) {
        return new DefaultDataHandler(request);
    }
}
