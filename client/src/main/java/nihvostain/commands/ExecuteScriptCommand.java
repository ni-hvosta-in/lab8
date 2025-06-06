package nihvostain.commands;

import common.exceptions.*;
import common.managers.Request;
import common.model.TypeOfElement;
import common.utility.InvalidParamMessage;
import javafx.scene.control.TextArea;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ExecuteScriptCommand implements Command {

    private final Communication communication;
    private final String login;
    private final String password;
    private final TextArea resultLabel;
    private final ResourceBundle resourceBundle;
    public ExecuteScriptCommand(Communication communication, String login, String password, TextArea resultLabel, ResourceBundle resourceBundle) {
        this.communication = communication;
        this.login = login;
        this.password = password;
        this.resultLabel = resultLabel;
        this.resourceBundle = resourceBundle;
    }

    @Override
    public Request request(ArrayList<String> args) throws IOException, ClassNotFoundException, TimeoutException {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(args.get(0)));
            Invoker invoker = new Invoker(sc, communication, login, password, resultLabel, resourceBundle);
            invoker.setFileFlag(true);
            try {
                invoker.scanning();
                invoker.setDepth(1);

            } catch (InputFromScriptException | RecursionDepthExceededException | IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не удается найти указанный файл");;
        }
        return null;
    }

    /**
     * @return описание команды
     */
    @Override
    public String description() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла." +
                " В скрипте содержатся команды в таком же виде," +
                " в котором их вводит пользователь в интерактивном режиме";
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
    public String[] getParamsName() {
        return new String[]{"scriptName"};
    }

    @Override
    public InvalidParamMessage isValidParam(ArrayList<String> params) {
        try {
            new Scanner(new File(params.get(0)));
            return InvalidParamMessage.TRUE;
        } catch (FileNotFoundException e) {
            return InvalidParamMessage.FILENOTFOUND;
        }

    }

    @Override
    public boolean skipValidateField() {
        return false;
    }
}
