package cn.meteor.im.dto;

/**
 * @author Meteor
 */
public class BaseExecutor {

    /**
     * 状态码
     */
    private int state;

    /**
     * 状态说明
     */
    private String message;

    public BaseExecutor() {
    }

    public BaseExecutor(int state, String message) {
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
