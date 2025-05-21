package nihvostain.utility;

import common.managers.Request;
import common.model.*;
import common.utility.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public interface Command {
    Request request(ArrayList<String> args) throws IOException, ClassNotFoundException, TimeoutException;
    /**
     * Возвращает описание
     * @return описание
     */
    String description();

    /**
     * Возвращает класс, который нужен для выполнения
     *
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
    InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, ClassNotFoundException, TimeoutException;

    boolean skipValidateField();

}
