package common.exceptions;

/**
 * Пользователь сделал рекурсию
 */
public class RecursionDepthExceededException extends Exception {
    @Override
    public String getMessage() {
        return "recursion";
    }
}
