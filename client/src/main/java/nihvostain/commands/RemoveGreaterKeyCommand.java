package nihvostain.commands;

import common.managers.*;
import common.model.*;
import common.model.TypeOfElement;
import common.utility.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Команда удаления элементов, ключ которых больше заданных
 */
public class RemoveGreaterKeyCommand implements Command {

    private final Communication communication;
    public RemoveGreaterKeyCommand(Communication communication) {
        this.communication = communication;
    }

    /**
     * @param args массив аргументов
     */
    @Override
    public Request request (ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {

        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.REMOVE_GREATER_KEY, args);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный";
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
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }
}
