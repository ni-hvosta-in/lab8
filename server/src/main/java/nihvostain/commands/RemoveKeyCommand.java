package nihvostain.commands;

import common.exceptions.OtherUsersObject;
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
 * Удаление элемента по ключу Модификация
 */
public class RemoveKeyCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public RemoveKeyCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException, SQLException {

        String response;
        RequestObj req;

        if (dataBasesManager.allowModification(request.getParams().get(0), request.getLogin())) {
            if (collectionManager.getStudyGroupList().get(request.getParams().get(0)).getGroupAdmin() != null) {
                Person.removePassportID(collectionManager.getStudyGroupList().get(request.getParams().get(0)).getGroupAdmin().getPassportID());
            }
            dataBasesManager.removeKey(request.getParams().get(0));
            response = collectionManager.getStudyGroupList().get(request.getParams().get(0)).toString();
            collectionManager.removeKey(request.getParams().get(0));
            req = new RequestObj("deleted by key " + request.getParams().get(0) + "\n" + response );
        } else {
            response = "This is another user's object";
            req = new RequestObj(response );
        }

        return req;
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
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
        String key = params.get(0);
        if (collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.NoKey;
        }

    }

    @Override
    public boolean skipValidateField() {
        return false;
    }

}
