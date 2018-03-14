package org.mel.generic.auth.common.exception;

public class UserNotExistException extends GenericException {

    public UserNotExistException() {
        super("用户不存在");
    }

    public UserNotExistException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return GenericException.USER_NOT_EXIST;
    }
}
