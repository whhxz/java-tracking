package com.whhxz.bootstrap.tracking.exception;

/**
 * BootstrapException
 * Created by xuzhuo on 2018/3/7.
 */
public class TrackingException extends RuntimeException {
    public TrackingException() {
    }

    public TrackingException(String message) {
        super(message);
    }

    public TrackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackingException(Throwable cause) {
        super(cause);
    }

}
