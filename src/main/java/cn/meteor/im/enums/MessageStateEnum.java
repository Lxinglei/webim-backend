package cn.meteor.im.enums;

/**
 * @author Meteor
 */
public enum MessageStateEnum {

    UNREAD(0, "消息未读"),
    SUCCESS(200, "操作成功");
    /**
     * 状态码
     */
    private int state;

    /**
     * 状态码解释
     */
    private String message;

    private MessageStateEnum(int state, String message) {
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
