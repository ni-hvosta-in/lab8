package nihvostain.commands;

import common.managers.*;
import common.model.*;
import common.model.TypeOfElement;
import common.utility.*;
import nihvostain.utility.Command;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Команда помощи
 */
public class HelpCommand implements Command {

    private final Collection<Command> commands;
    public HelpCommand(Collection<Command> commands){
        this.commands = commands;
    }

    @Override
    public Request request(ArrayList<String> args) {
        commands.forEach(x -> System.out.println(x.description()));
        return null;
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "help : вывести справку по доступным командам";
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
