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
 * Команда удаления элементов меньших заданному Модификация
 */
public class RemoveLowerCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public RemoveLowerCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {

        //Map<String,StudyGroup> studyGroupList = new LinkedHashMap<>();
        //StudyGroup studyGroup = new StudyGroup(0L, args); ВНИМАНИЕ
        StudyGroup studyGroup = request.getStudyGroup();

        if (!collectionManager.getStudyGroupList().isEmpty()) {

            ArrayList<String> keyToRemove = new ArrayList<>();
            collectionManager.getStudyGroupList().entrySet().stream()
                    .filter(x -> {
                        try {
                            return x.getValue().compareTo(studyGroup) < 0 & dataBasesManager.allowModification(x.getKey(), request.getLogin());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(x -> {
                        keyToRemove.add(x.getKey());
                        try {
                            dataBasesManager.removeKey(x.getKey());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
            keyToRemove.forEach(collectionManager::removeKey);

            return new ShowCommand(collectionManager, communication).execute(request);
            /*
            for (Map.Entry<String, StudyGroup> pair : collectionManager.getStudyGroupList().entrySet()) {
                if (studyGroup.compareTo(pair.getValue()) < 0){
                    studyGroupList.put(pair.getKey(), pair.getValue());
                }
            }
            collectionManager.setStudyGroupList(studyGroupList);
             */
        }
        else {
            System.out.println("Коллекция пуста");
            return new RequestObj("Коллекция пуста");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
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
        return 11;
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
        return true;
    }
}
