package org.ezt.models;

import org.base.utils.beannote.Note;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权信息
 * Created by wangwr on 2016/3/31.
 */
@Entity
@Table(name = "info_grant_access_token")
public class GrantAccessToken {

    @Id
    @Note("标识id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Note("用户的唯一标识")
    @Column(name = "openid",length = 32,nullable = false)
    private String openid;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "openid", insertable = false, updatable = false, nullable = false)
    private OAuthUser oauthUser;

    @Note("调用开放接口的凭证")
    @Column(name = "access_token",length = 32,nullable = false,unique = true)
    private String accessToken;

    @Note("该凭证可以换取新的凭证")
    @Column(name = "refresh_token",length = 32,nullable = false,unique = true)
    private String refreshToken;

    @Note("创建时间")
    @Column(name = "create_time",nullable = false,updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Note("过期时间")
    @Column(name = "expires_in",nullable = false)
    private Long expiresIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
