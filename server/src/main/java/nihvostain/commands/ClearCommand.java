package nihvostain.commands;

import common.managers.Request;
import common.managers.RequestObj;
import common.model.TypeOfElement;
import common.utility.InvalidParamMessage;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.managers.DataBasesManager;
import nihvostain.utility.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда очистки коллекции Модификация
 */
public class ClearCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public ClearCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException, SQLException {

        RequestObj req;
        ArrayList<String> keysToRemove = new ArrayList<>();
        for (String key : collectionManager.getStudyGroupList().keySet()) {
            if (dataBasesManager.allowModification(key, request.getLogin())) {
                System.out.println(key);
                keysToRemove.add(key);
                dataBasesManager.removeKey(key);
            }
        }
        for (String key : keysToRemove) {
            collectionManager.removeKey(key);
        }
        req = new RequestObj("отчистил ваши объекты");
        return req;
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
