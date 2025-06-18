package finalmission.exception;

public abstract class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    public GeneralException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
