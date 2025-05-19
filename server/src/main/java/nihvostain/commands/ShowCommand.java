package nihvostain.commands;

import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;
import common.managers.*;
import common.model.*;
import common.utility.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Команда вывода коллекции на экран
 */
public class ShowCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    public ShowCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {

        String show = "";

        if (!collectionManager.getStudyGroupList().isEmpty()) {
            show = collectionManager.getSortedStudyGroupList().entrySet()
                    .stream().map(x -> x.getKey() + " " + x.getValue()).collect(Collectors.joining("\n"));
            System.out.println(show);
            /*
            for (Map.Entry<String, StudyGroup> pair : collectionManager.getSortedStudyGroupList().entrySet()) {
                show += pair.getKey() + " " + pair.getValue() + "\n";
                System.out.println(pair.getKey() + " " + pair.getValue());
            } */
        }
        else {
            System.out.println("Коллекция пуста");
            show = "Коллекция пуста";
        }

        return new RequestObj(show);
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
