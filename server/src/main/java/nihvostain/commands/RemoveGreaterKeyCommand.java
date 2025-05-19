package nihvostain.commands;

import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.managers.DataBasesManager;
import nihvostain.utility.Command;
import common.managers.*;
import common.model.*;
import common.utility.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда удаления элементов, ключ которых больше заданных Модификация
 */
public class RemoveGreaterKeyCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public RemoveGreaterKeyCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException, SQLException {

        String key = request.getParams().get(0);
        ArrayList<String> keyToRemove = new ArrayList<>();

        for (String k : collectionManager.getStudyGroupList().keySet()) {
            if (k.compareTo(key) > 0 & dataBasesManager.allowModification(k, request.getLogin())){
                dataBasesManager.removeKey(k);
                keyToRemove.add(k);
            }
        }
        keyToRemove.forEach(collectionManager::removeKey);
        return new ShowCommand(collectionManager, communication).execute(request);


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
