package org.ezt.models;

import org.base.utils.beannote.Note;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.ezt.models.pk.OAuthUserClientPK;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权用户应用列表,一个用户可以对应多个应用授权
 * Created by wangwr on 2016/3/31.
 */
@Entity
@IdClass(OAuthUserClientPK.class)
@Table(name = "relation_oauth_user_client")
public class OAuthUserClient {

    @Id
    @Note("应用id")
    @Column(name = "client_id",length = 64)
    private String clientId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", insertable = false, updatable = false, nullable = false)
    private OAuthClient oauthClient;

    @Id
    @Note("用户唯一标识id")
    @Column(name = "openid",length = 48)
    private String openid;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "openid", insertable = false, updatable = false, nullable = false)
    private OAuthUser oauthUser;

    @Note("创建时间")
    @Column(name = "create_time",nullable = false,updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Note("最后修改时间")
    @Column(name = "last_modified",nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModified;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OAuthClient getOauthClient() {
        return oauthClient;
    }

    public void setOauthClient(OAuthClient oauthClient) {
        this.oauthClient = oauthClient;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public OAuthUser getOauthUser() {
        return oauthUser;
    }

    public void setOauthUser(OAuthUser oauthUser) {
        this.oauthUser = oauthUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
