package idm.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

    private HttpStatus httpStatus;

    public BaseException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }
}
