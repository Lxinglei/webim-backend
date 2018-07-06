package cn.meteor.im.dto;



import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Meteor
 */
public class SysResult implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "请求结果")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;

    public SysResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public SysResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static SysResult success() {
        return new SysResult(true, "操作成功");
    }

    public static SysResult success(Object data) {
        return new SysResult(true, "操作成功", data);
    }

    public static SysResult fail() {
        return new SysResult(false, "操作失败");
    }

    public static SysResult fail(String message) {
        return new SysResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
