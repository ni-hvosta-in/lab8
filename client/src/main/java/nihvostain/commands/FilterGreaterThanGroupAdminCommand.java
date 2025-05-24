package nihvostain.commands;


import common.managers.*;
import common.model.*;
import common.utility.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Команда вывода элементов коллекции, значение старосты которых больше заданного
 */
public class FilterGreaterThanGroupAdminCommand implements Command {

    private final Communication communication;

    public FilterGreaterThanGroupAdminCommand(Communication communication) {
        this.communication = communication;
    }


    /**
     * @param args массив аргументов
     */
    @Override
    public Request request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        Person p;
        if (args.get(0) == null){
            p = null;
        } else {
            p = new Person(args);
        }
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.FILTER_GREATER_THAN_GROUP_ADMIN, p);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.PERSON;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 5;
    }

    @Override
    public Integer getNeededParamLen() {
        return 0;
    }

    @Override
    public String[] getParamsName() {
        return new String[]{""};
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        return InvalidParamMessage.TRUE;
    }

    @Override
    public boolean skipValidateField() {
        return true;
    }
}
