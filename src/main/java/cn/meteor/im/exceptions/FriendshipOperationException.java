package cn.meteor.im.exceptions;

/**
 * @author Meteor
 */
public class FriendshipOperationException extends RuntimeException {
    public FriendshipOperationException(String message) {
        super(message);
    }

    public FriendshipOperationException(String template, Object ... args) {
        super(String.format(template, args));
    }
}
