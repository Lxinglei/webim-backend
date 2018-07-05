package cn.meteor.im.enums;

/**
 * @author Meteor
 */
public enum  UserStateEnum {

    SUCCESS(200, "操作成功"),
    DISABLED(-1, "用户禁用"),
    ENABLED(1, "用户启用");

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
