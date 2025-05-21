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
 * Команда группировки вывода по семестру
 */
public class GroupCountingBySemesterEnum implements Command {

    private final Communication communication;

    public GroupCountingBySemesterEnum(Communication communication) {
        this.communication = communication;
    }

    /**
     * @param args массив аргументов
     */
    @Override
    public Request request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.GROUP_COUNTING_BY_SEMESTER_ENUM, args);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum," +
                " вывести количество элементов в каждой группе";
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
