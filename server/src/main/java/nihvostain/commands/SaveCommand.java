package nihvostain.commands;

import common.managers.*;
import common.model.StudyGroups;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.FileWriter;
import nihvostain.utility.Command;
import common.managers.*;
import common.model.*;
import common.utility.*;
import java.util.ArrayList;

/**
 * Команда сохранения коллекции в файл xml
 */
public class SaveCommand implements Command {

    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) {
        FileWriter fileWriter = new FileWriter(new StudyGroups(collectionManager.getStudyGroupList()));
        try {
            fileWriter.toXML(System.getenv("MY_VAR"));
            System.out.println("записано в файл");
        }catch (NullPointerException e){
            System.out.println("Отсутствует переменная окружения с именем MY_VAR");
            System.out.println("Коллекция записана в файл studygroupNotFromVAR");
            fileWriter.toXML("studygroupNotFromVAR");
        }
        return new RequestObj("saved");
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "save : сохранить коллекцию в файл";
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
