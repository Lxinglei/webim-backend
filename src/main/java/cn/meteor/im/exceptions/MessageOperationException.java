package cn.meteor.im.exceptions;

/**
 * @author Meteor
 */
public class MessageOperationException extends RuntimeException {
    public MessageOperationException(String message) {
        super(message);
    }

    public MessageOperationException(String template, Object ... args) {
        super(String.format(template, args));
    }
}
