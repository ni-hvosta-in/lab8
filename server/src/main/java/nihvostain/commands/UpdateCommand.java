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
import java.util.Collection;
import java.util.Map;

/**
 * Команда обновления элемента по заданному id Модификация
 */
public class UpdateCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;
    private final DataBasesManager dataBasesManager;
    public UpdateCommand(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException, SQLException {
        try {

            long id = Long.parseLong(request.getParams().get(0));
            StudyGroup studyGroup = request.getStudyGroup();
            studyGroup.setID(id);
            String key = null;
            for (Map.Entry<String, StudyGroup> pair : collectionManager.getSortedStudyGroupList().entrySet()){
                if (pair.getValue().getId() == id){
                    key = pair.getKey();
                    break;
                }
            }
            if (dataBasesManager.allowModification(key, request.getLogin())){

                dataBasesManager.updateStudyGroupKey(key, studyGroup);
                collectionManager.getStudyGroupList().entrySet()
                        .stream()
                        .filter(x -> x.getValue().getId().equals(id))
                        .limit(1)
                        .forEach(x -> collectionManager.updateStudyGroup(x.getKey(), studyGroup));

                return new RequestObj("updated id " + id + " " + studyGroup);
            } else {
                return new RequestObj("This is another user's object");
            }


            /*for (Map.Entry<String, StudyGroup> pair : collectionManager.getStudyGroupList().entrySet()){
                if (pair.getValue().getId().equals(id)) {
                    collectionManager.updateStudyGroup(pair.getKey(), studyGroup);
                    RequestObj req = new RequestObj("обновил id " + id + " " + studyGroup);
                    communication.send(req.serialize());
                    break;
                }
            }
             */
        } catch (NumberFormatException e){
            System.out.println("id введен не верно");
            return new RequestObj("ошибка id");
        }

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
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

    /**
     * @return кол-во параметров для функции
     */
    @Override
    public Integer getNeededParamLen() {
        return 1;
    }

    /**
     * @param params массив параметров для команды
     * @return валидность этих параметров
     */
    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        String id = params.get(0);
        try{
            long idLong = Long.parseLong(id);
            if (StudyGroup.getIdList().contains(idLong)){
                return InvalidParamMessage.TRUE;
            } else {
                return InvalidParamMessage.NoID;
            }
        } catch (NumberFormatException e){
            return InvalidParamMessage.NotLongID;
        }
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }


}
