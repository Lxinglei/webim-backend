package cn.meteor.im.dto;

/**
 * @author Meteor
 */
public class JwtResult {
    private String token;
    private Long expireIn;

    public JwtResult(String token, Long expireIn) {
        this.token = token;
        this.expireIn = expireIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }
}
