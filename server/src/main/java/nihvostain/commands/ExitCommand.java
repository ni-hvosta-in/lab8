package nihvostain.commands;

import common.managers.Request;
import common.managers.RequestObj;
import common.model.TypeOfElement;
import common.utility.InvalidParamMessage;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Команда выхода из программы
 */
public class ExitCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public ExitCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {
        new SaveCommand(collectionManager).execute(request);
        return new RequestObj("I save the collection to a file");
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "exit : завершить программу (без сохранения в файл)";
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
