package gsm.gsmjava.global.error;

import org.springframework.http.HttpStatus;

public class ExpectedException extends RuntimeException {

    private final HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ExpectedException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ExpectedException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}