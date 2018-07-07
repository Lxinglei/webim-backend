package cn.meteor.im.enums;

/**
 * @author Meteor
 */
public enum  UserStateEnum {

    SUCCESS(200, "操作成功"),
    GENDER_FEMALE(1, "女"),
    GENDER_MALE(1, "男"),
    GENDER_SECRET(0, "保密"),
    DISABLED(-1, "用户禁用"),
    REGISTERED(-1001, "用户名已被注册"),
    ENABLED(1, "用户启用"),
    LOGIN_FAILED(-1001, "用户名或密码错误"),
    NOT_EXISTS(-1001, "用户不存在");

    /**
     * 状态码
     */
    private int state;

    /**
     * 状态码解释
     */
    private String message;

    private UserStateEnum(int state, String message) {
        this.state = state;
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
