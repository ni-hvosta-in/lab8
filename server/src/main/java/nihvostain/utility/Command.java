package nihvostain.utility;

import common.managers.Request;
import common.managers.RequestObj;
import common.model.TypeOfElement;
import common.utility.InvalidParamMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * интерфейс описывающий поведение команды
 */
public interface Command {
    /**
     * Выполняет команду
     * @param request запрос с клиента
     */
    RequestObj execute(Request request) throws IOException, SQLException;

    /**
     * Возвращает описание
     * @return описание
     */
    String description();

    /**
     * Возвращает класс, который нужен для выполнения
     * @return класс, который будет вводиться
     */
    TypeOfElement getElementType();

    /**
     * Возвращает размер требуемого массива аргументов
     * @return размер массива аргументов
     */
    Integer getNeededArgsLen();

    /**
     * @return необходимая длина массива параметров
     */
    Integer getNeededParamLen();

    /**
     * @param params массив параметров для команды
     * @return валидность параметров
     */
    InvalidParamMessage isValidParam(ArrayList<String> params);

    /**
     * @return нужно ли пропускать валидность полей
     */
    boolean skipValidateField();
}
