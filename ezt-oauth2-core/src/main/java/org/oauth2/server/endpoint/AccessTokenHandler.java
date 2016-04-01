package org.oauth2.server.endpoint;

import org.oauth2.server.data.DataHandler;
import org.oauth2.server.data.DataHandlerFactory;
import org.oauth2.server.fetcher.clientcredential.ClientCredentialFetcher;
import org.oauth2.server.grant.GrantHandler;
import org.oauth2.server.grant.GrantHandlerProvider;
import org.oauth2.server.grant.GrantResult;
import org.oauth2.server.pluging.Request;
import org.oauth2.server.pluging.StrUtils;

/**
 * Created by wangwr on 2016/3/30.
 */
public class AccessTokenHandler {

    private DataHandlerFactory dataHandlerFactory;

    private ClientCredentialFetcher clientCredentialFetcher;

    private GrantHandlerProvider grantHandlerProvider;

    public GrantResult handlerRequest(Request request){
        String type = request.getParameter("grant_type");
        if(StrUtils.isEmpty(type)){
            throw new RuntimeException("grant_type not found");
        }

        GrantHandler grantHandler = grantHandlerProvider.getHandler(type);
        ClientCredentialFetcher.ClientCredential clientCredential = clientCredentialFetcher.fetch(request);
        if(StrUtils.isEmpty(clientCredential.getClientId())){
            throw new RuntimeException("client_id not found");
        }
        if(StrUtils.isEmpty(clientCredential.getClientSecret())){
            throw new RuntimeException("client_secret not found");
        }
        DataHandler dataHandler = dataHandlerFactory.create(request);
        if(!dataHandler.validateClient(clientCredential.getClientId(),clientCredential.getClientSecret(),type)){
            throw new RuntimeException("invalid client");
        }
        return grantHandler.handleRequest(dataHandler);
    }


    public DataHandlerFactory getDataHandlerFactory() {
        return dataHandlerFactory;
    }

    public void setDataHandlerFactory(DataHandlerFactory dataHandlerFactory) {
        this.dataHandlerFactory = dataHandlerFactory;
    }

    public ClientCredentialFetcher getClientCredentialFetcher() {
        return clientCredentialFetcher;
    }

    public void setClientCredentialFetcher(ClientCredentialFetcher clientCredentialFetcher) {
        this.clientCredentialFetcher = clientCredentialFetcher;
    }

    public GrantHandlerProvider getGrantHandlerProvider() {
        return grantHandlerProvider;
    }

    public void setGrantHandlerProvider(GrantHandlerProvider grantHandlerProvider) {
        this.grantHandlerProvider = grantHandlerProvider;
    }
}
