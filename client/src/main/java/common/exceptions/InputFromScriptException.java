package common.exceptions;

/**
 * Пользователь ввел некорректный скрипт
 */
public class InputFromScriptException extends Exception{
    @Override
    public String getMessage() {
        return "Ошибка в чтении скрипта, неверный скрипт";
    }
}
