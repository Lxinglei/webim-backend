package cn.meteor.im.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 账号密码认证
 * @author Meteor
 */

@Table(name = "t_local_auth")
public class LocalAuth {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long localAuthId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名(不能重复)", required = true)
    @NotEmpty
    @Size(min = 3, max = 10, message = "{localAuth.username}")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码)", required = true)
    @NotEmpty
    @Size(min = 3, max = 10, message = "{localAuth.password}")
    private String password;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;

    public Long getLocalAuthId() {
        return localAuthId;
    }

    public void setLocalAuthId(Long localAuthId) {
        this.localAuthId = localAuthId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
