package nihvostain.commands;

import common.managers.*;
import common.model.*;
import common.utility.*;
import nihvostain.managers.CollectionManager;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Команда вывода элементов коллекции в имени, которых содержится заданная подстрока
 */
public class FilterContainsNameCommand implements Command {

    private final CollectionManager collectionManager;
    private final Communication communication;

    public FilterContainsNameCommand(CollectionManager collectionManager, Communication communication) {
        this.collectionManager = collectionManager;
        this.communication = communication;
    }

    /**
     * @param request запрос с клиента
     */
    @Override
    public RequestObj execute(Request request) throws IOException {

        //stream
        String substring = request.getParams().get(0);
        String regex = ".*" + substring + ".*";
        String ans = "";
        ans = collectionManager.getSortedStudyGroupList().entrySet()
                .stream().filter(x -> Pattern.matches(regex, x.getValue().getName()))
                .map(x -> x.getKey() + " " + x.getValue()).collect(Collectors.joining("\n"));
        System.out.println(ans);
                /*
        for (Map.Entry<String, StudyGroup> pair : collectionManager.getSortedStudyGroupList().entrySet()) {
            if (Pattern.matches(regex, pair.getValue().getName())){
                System.out.println(pair.getKey() + " " + pair.getValue());
                ans += pair.getKey() + " " + pair.getValue();
            }
        }

         */
        return new RequestObj(ans);

    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "filter_contains_name name : вывести элементы," +
                " значение поля name которых содержит заданную подстроку";
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
