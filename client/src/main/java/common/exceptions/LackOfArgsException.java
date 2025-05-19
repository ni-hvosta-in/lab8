package common.exceptions;

/**
 * Ошибка нехватки параметров
 */
public class LackOfArgsException extends Exception{

    @Override
    public String getMessage() {
        return "Не хватает элементов в аргументе, введены не все параметры";
    }
}
