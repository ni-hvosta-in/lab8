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
 * Команда очистки коллекции
 */
public class ClearCommand implements Command {

    private final Communication communication;
    public ClearCommand(Communication communication)
    {
        this.communication = communication;
    }

    /**
     * @param args массив аргументов
     */
    @Override
    public Request request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.CLEAR);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "clear : очистить коллекцию";
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
        return 0;
    }

    @Override
    public Integer getNeededParamLen() {
        return 0;
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
