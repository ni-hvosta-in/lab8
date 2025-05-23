package nihvostain.commands;

import common.managers.*;
import common.model.*;
import common.model.TypeOfElement;
import common.utility.*;
import nihvostain.managers.Communication;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Команда обновления элемента по заданному id
 */
public class UpdateCommand implements Command {

    private final Communication communication;
    private final String login;
    private final String password;
    public UpdateCommand(Communication communication, String login, String password) {
        this.communication = communication;
        this.login = login;
        this.password = password;
    }

    /**
     * @param args массив аргументов
     * @return
     */
    @Override
    public Request request (ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        String id = args.get(0);
        ArrayList<String> params = new ArrayList<>();
        args.remove(0);
        params.add(id);
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.UPDATE, new StudyGroup(args), params);
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

    @Override
    public String[] getParamsName() {
        return new String[]{"id"};
    }

    /**
     * @param params массив параметров для команды
     * @return валидность этих параметров
     */
    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, TimeoutException, ClassNotFoundException {
        Request request = new Request(TypeRequest.REQUEST_PARAM, TypeCommand.UPDATE, params);
        communication.send(request.addUser(login, password).serialize());
        byte[] response = communication.receive();
        return new Deserialize<ResponseParam>(response).deserialize().getParam();
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }


}
