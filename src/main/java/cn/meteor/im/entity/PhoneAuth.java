package cn.meteor.im.entity;

import java.util.Date;

/**
 * 手机号认证
 * @author Meteor
 */
public class PhoneAuth {

    /**
     * 主键
     */
    private Long phoneAuthId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    public Long getPhoneAuthId() {
        return phoneAuthId;
    }

    public void setPhoneAuthId(Long phoneAuthId) {
        this.phoneAuthId = phoneAuthId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
