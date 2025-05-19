package common.exceptions;

public class SqlException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Ошибка с БД";
    }
}
