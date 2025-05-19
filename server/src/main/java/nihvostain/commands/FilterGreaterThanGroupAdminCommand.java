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
 * Команда вывода элементов коллекции, значение старосты которых больше заданного
 */
public class FilterGreaterThanGroupAdminCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;

    public FilterGreaterThanGroupAdminCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {

        Person person;
        if (request.getPerson() == null){
           person = null;
        } else {
            person = request.getPerson();
        }
        //Stream
        String ans = "";
        if (person != null) {

            ans = collectionManager.getSortedStudyGroupList().values()
                    .stream().filter(x -> x.getGroupAdmin() != null)
                    .map(StudyGroup::toString).collect(Collectors.joining("\n"));

            /*
            for (StudyGroup st : collectionManager.getSortedStudyGroupList().values()) {
                if (st.getGroupAdmin() != null) {
                    if (st.getGroupAdmin().compareTo(person) > 0) {
                        System.out.println(st);
                        ans += st;
                    }
                }
            }

             */
        } else {

            ans = collectionManager.getSortedStudyGroupList().values()
                    .stream().filter(x -> x.getGroupAdmin() != null)
                    .map(StudyGroup::toString).collect(Collectors.joining("\n"));
            System.out.println(ans);
            /*
            for (StudyGroup st : collectionManager.getStudyGroupList().values()) {
                System.out.println(st);
                ans += st;
            }

             */
        }
        return new RequestObj(ans);

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного";
    }

    /**
     * @return класс, который будет вводиться
     */
    @Override
    public TypeOfElement getElementType() {
        return TypeOfElement.PERSON;
    }

    /**
     * @return размер массива аргументов
     */
    @Override
    public Integer getNeededArgsLen() {
        return 5;
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
