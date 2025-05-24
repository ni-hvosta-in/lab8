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
 * Команда добавления элемента в коллекцию с ключом
 */
public class InsertCommand implements Command {

    private final Communication communication;
    private final String login;
    private final String password;
    public InsertCommand(Communication communication, String login, String password) {
        this.communication = communication;
        this.login = login;
        this.password = password;
    }


    @Override
    public Request request(ArrayList<String> args) throws IOException, TimeoutException, ClassNotFoundException {
        String key = args.get(0);
        ArrayList<String> params = new ArrayList<>();
        params.add(key);
        args.remove(0);
        return new Request(TypeRequest.REQUEST_COMMAND, TypeCommand.INSERT, new StudyGroup(args), params);
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
    public String[] getParamsName() {
        return new String[]{"key"};
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) throws IOException, ClassNotFoundException, TimeoutException {
        if (params.get(0).isEmpty()){
            return InvalidParamMessage.FALSE;
        }
        Request request = new Request(TypeRequest.REQUEST_PARAM, TypeCommand.INSERT, params);
        communication.send(request.addUser(login, password).serialize());
        byte[] response = communication.receive();
        return new Deserialize<ResponseParam>(response).deserialize().getParam();
    }

    @Override
    public boolean skipValidateField() {
        return false;
    }


}
