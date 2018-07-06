package cn.meteor.im.exceptions;

/**
 * @author Meteor
 */
public class UserOperationException extends RuntimeException {
    public UserOperationException(String message) {
        super(message);
    }

    public UserOperationException(String template, Object ... args) {
        super(String.format(template, args));
    }
}
