package nihvostain.commands;

import common.managers.*;
import common.model.*;
import common.model.TypeOfElement;
import common.utility.*;
import javafx.scene.control.TextArea;
import nihvostain.utility.Command;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Команда помощи
 */
public class HelpCommand implements Command {

    private final Collection<Command> commands;
    private final TextArea resultLabel;
    public HelpCommand(Collection<Command> commands, TextArea resultLabel){
        this.commands = commands;
        this.resultLabel = resultLabel;
    }

    @Override
    public Request request(ArrayList<String> args) {

        final String[] answer = {""};
        for (Command command : commands) {
            answer[0] = answer[0] + command.description() + "\n";
        }
        commands.forEach(x -> {
            System.out.println(x.description());
            answer[0] = answer[0] +x.description()+"\n";});
        resultLabel.setText(answer[0]);
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
    public String[] getParamsName() {
        return null;
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
