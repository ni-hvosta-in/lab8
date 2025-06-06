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
 * Команда замена значения по ключу, если новое значение больше старого Модификация5
 */
public class ReplaceIfGreaterCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public ReplaceIfGreaterCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
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
        if (collectionManager.getStudyGroupList().containsKey(key)){
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setID(collectionManager.getStudyGroupList().get(key).getId());
            if (studyGroup.compareTo(collectionManager.getStudyGroupList().get(key)) > 0 & dataBasesManager.allowModification(key, request.getLogin())){
                dataBasesManager.updateStudyGroupKey(key, studyGroup);
                collectionManager.updateStudyGroup(key, studyGroup);
                return new RequestObj("replaced");
            } else {
                return new RequestObj("did not replace");
            }
        } else {
            return new RequestObj("There is no such key");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого";
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
        if (collectionManager.getStudyGroupList().containsKey(key)){
            return InvalidParamMessage.TRUE;
        } else {
            return InvalidParamMessage.NoKey;
        }
    }

    @Override
    public boolean skipValidateField() {
        return true;
    }
}
