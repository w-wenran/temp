package org.ezt.models;

import org.base.utils.RandomUtil;
import org.base.utils.beannote.Note;

import javax.persistence.*;
import java.util.Date;

/**
 * 授权用户，每个授权的用户有且只有一个openid与之对应
 * Created by wangwr on 2016/3/31.
 */
@Entity
@Table(name = "info_oauth_user")
public class OAuthUser {

    public static String generatedOpenid(){
        return RandomUtil.randomWords(RandomUtil.RandomType.MIXING,48);
    }

    /**
     * 用户唯一标识，一旦生成不可以修改
     */
    @Id
    @Note("用户唯一标识id")
    @Column(name = "openid",length = 48)
    private String openid;

    @Note("用户id")
    @Column(name = "user_id",unique = true,nullable = false)
    private Long userId;

    @Note("创建时间")
    @Column(name = "create_time",nullable = false,updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @PrePersist
    private void onCreate() {
        this.setCreateTime(new Date());
    }
}
