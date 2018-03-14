package org.mel.generic.auth.common.exception;

public abstract class GenericException extends RuntimeException {
    public GenericException() {
        super();
    }

    public GenericException(String message) {
        super(message);
    }

    public abstract String getCode();


    public static final String USER_NOT_EXIST = "0001";

}
