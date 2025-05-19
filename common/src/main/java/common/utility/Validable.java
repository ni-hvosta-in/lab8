package common.utility;

import common.exceptions.InputFromScriptException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * интерфейс валидности
 */
public interface Validable {
    /**
     * Проверяет валидность
     * @param s строка ввода
     * @return валидна ли
     */
    boolean isValidate(String s) throws IOException, TimeoutException, ClassNotFoundException;

    /**
     * Валидный ввод
     * @param fileFlag флаг файла
     * @return результат валидного ввода
     * @throws InputFromScriptException ошибка в скрипте
     */
    String inputValidate(boolean fileFlag) throws InputFromScriptException, IOException, ClassNotFoundException, TimeoutException;
}
