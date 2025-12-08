package exceptions;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

public abstract class BaseException extends Exception {
    private String message;

    public BaseException(String message) {
        super(message);
    }

    public String getMessage() {
        return this.message;
    }
}