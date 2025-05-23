package nihvostain.commands;

import common.managers.*;
import common.model.TypeOfElement;
import common.utility.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Команда вывода элементов коллекции в имени, которых содержится заданная подстрока
 */
public class FilterContainsNameCommand implements Command {

    private final Communication communication;

    public FilterContainsNameCommand(Communication communication) {
        this.communication = communication;
    }


    /**
     * @param args параметры
     * запрос выполнения команды на сервере
     */
    @Override
    public Request request(ArrayList<String> args) throws IOException, ClassNotFoundException, TimeoutException {
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.FILTER_CONTAINS_NAME, args);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "filter_contains_name name : вывести элементы," +
                " значение поля name которых содержит заданную подстроку";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.PRIMITIVE;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 1;
    }

    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    @Override
    public String[] getParamsName() {
        return new String[]{"nameSubstring"};
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }
}
