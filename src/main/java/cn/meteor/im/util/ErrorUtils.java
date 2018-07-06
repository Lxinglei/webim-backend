package cn.meteor.im.util;

import org.springframework.validation.BindingResult;

/**
 * @author Meteor
 */
public class ErrorUtils {

    public static String parserError(BindingResult bindingResult) {

        StringBuilder stringBuilder = new StringBuilder();
        bindingResult
                .getFieldErrors()
                .stream()
                .forEach(fieldError -> {
                    stringBuilder.append(fieldError.getDefaultMessage());
                    stringBuilder.append(";");
                });
        return stringBuilder.substring(0, stringBuilder.lastIndexOf(";"));
    }
}
