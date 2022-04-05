package by.university.hippo.exception;

public class NoSuchHippoException  extends RuntimeException{
    public NoSuchHippoException() {
    }

    public NoSuchHippoException(String message) {
        super(message);
    }

    public NoSuchHippoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchHippoException(Throwable cause) {
        super(cause);
    }

    public NoSuchHippoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
