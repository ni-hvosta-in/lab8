package common.exceptions;

/**
 * Ошибка отсутствия старосты
 */
public class NoAdminException extends RuntimeException {
    @Override
    public String getMessage() {
        return "в этой группе нет админа";
    }
}
