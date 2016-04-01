package org.ezt.models;

import org.base.utils.beannote.Note;

import javax.persistence.*;
import java.util.Date;

/**
 * oauth第三方平台端
 * Created by wangwr on 2016/3/31.
 */
@Entity
@Table(name = "info_oauth_client")
public class OAuthClient {

    @Id
    @Note("应用id")
    @Column(name = "client_id",length = 32)
    private String clientId;

    @Note("应用KEY")
    @Column(name = "client_secret",length = 32)
    private String clientSecret;

    @Note("应用名字")
    @Column(name = "client_name",length = 120,nullable = false)
    private String clientName;

    @Note("应用回调页")
    @Column(name = "redirect_uri",length = 500,nullable = false)
    private String redirectUri;

    @Note("创建时间")
    @Column(name = "create_time",nullable = false,updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
