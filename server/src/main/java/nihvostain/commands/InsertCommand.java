package nihvostain.commands;

import common.exceptions.ExistingKeyException;
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
 * Команда добавления элемента в коллекцию с ключом
 */
public class InsertCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public InsertCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
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
        RequestObj req;
        try {
            long id = dataBasesManager.insertStudyGroup(key, request.getStudyGroup(), request.getLogin());
            request.getStudyGroup().setID(id);
            StudyGroup.addId(id);
            collectionManager.insert(key, request.getStudyGroup());
            if (request.getStudyGroup().getGroupAdmin() != null) {
                Person.addPassportID(request.getStudyGroup().getGroupAdmin().getPassportID());
            }
            System.out.println("Добавил " + collectionManager.getStudyGroupList().get(key));
            req = new RequestObj("Added " + collectionManager.getStudyGroupList().get(key));

        } catch (ExistingKeyException e) {
            System.out.println(e.getMessage());
            req = new RequestObj("Ошибка исполнения");

        }
        return req;
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.STUDYGROUP;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 12;
    }

    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        String key = params.get(0);
        if (!collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.ExistingKey;
        }

    }

    @Override
    public boolean skipValidateField() {
        return false;
    }

}
