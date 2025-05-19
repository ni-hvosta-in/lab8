package nihvostain.managers;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import common.managers.Deserialize;
import common.managers.Request;
import common.utility.TypeCommand;
import nihvostain.commands.*;
import common.managers.*;
import common.model.*;
import common.utility.*;
import nihvostain.utility.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс вызывателя команд
 */
public class Invoker {

    private final CollectionManager collectionManager;

    private Communication communication;

    private final DataBasesManager dataBasesManager;
    /*
     * Флаг файла
     */
    private boolean fileFlag = false;
    /**
     * Текущая глубина рекурсии
     */
    private static int depth;
    /**
     * Максимальная глубина рекурсии
     */
    private static final int maxDepth = 10;


    public Invoker(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
        depth+=1;
    }

    /**
     * исполнение команд и сканирование ввода
     * @throws InputFromScriptException ошибка в скрипте
     * @throws RecursionDepthExceededException ошибка глубины рекурсии
     */
    public void scanning() throws InputFromScriptException, RecursionDepthExceededException, IOException, SQLException {

        if (depth > maxDepth) {
            throw new RecursionDepthExceededException();
        }
        Map<TypeCommand, Command> commands = new LinkedHashMap<>();
        commands.put(TypeCommand.INFO, new InfoCommand(collectionManager, communication));
        commands.put(TypeCommand.SHOW, new ShowCommand(collectionManager, communication));
        commands.put(TypeCommand.INSERT, new InsertCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.UPDATE, new UpdateCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.REMOVE_KEY, new RemoveKeyCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.CLEAR, new ClearCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.EXIT, new ExitCommand(collectionManager, communication));
        commands.put(TypeCommand.REMOVE_LOWER, new  RemoveLowerCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.REPLACE_IF_GREATER, new ReplaceIfGreaterCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.REMOVE_GREATER_KEY, new RemoveGreaterKeyCommand(collectionManager, communication, dataBasesManager));
        commands.put(TypeCommand.GROUP_COUNTING_BY_SEMESTER_ENUM, new GroupCountingBySemesterEnum(collectionManager, communication));
        commands.put(TypeCommand.FILTER_CONTAINS_NAME, new FilterContainsNameCommand(collectionManager, communication));
        commands.put(TypeCommand.FILTER_GREATER_THAN_GROUP_ADMIN, new FilterGreaterThanGroupAdminCommand(collectionManager, communication));

        Thread thread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine().trim();
                ArrayList<String> tokens = new ArrayList<>(List.of(line.split(" +")));
                if (tokens.get(0).equals("save")) {
                    new SaveCommand(collectionManager).execute(new Request());

                }
            }
        });
        thread.start();
        while (true) {

            byte[] message;

            message = communication.receive();
            Request request;

            try {

                request = new Deserialize<Request>(message).deserialize();
                System.out.println(request.getTypeRequest());
                System.out.println(request.getLogin() + " " + request.getPassword());

                if (request.getTypeRequest() == TypeRequest.REQUEST_COMMAND){

                    Command command = commands.get(request.getName());
                    if (command.isValidParam(request.getParams()) == InvalidParamMessage.TRUE) {
                        try {
                            RequestObj req = command.execute(request);
                            communication.send(req.serialize());
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                            communication.send(new RequestObj("Ошибка работы с бд").serialize());
                        }

                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PARAM){

                    ResponseParam responseParam = new ResponseParam(commands.get(request.getName()).isValidParam(request.getParams()));
                    communication.send(responseParam.serialize());


                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PASSPORT) {

                    if (Person.getPassportIDList().contains(request.getParams().get(0))) {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.FALSE);
                        communication.send(responseParam.serialize());
                    } else {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.TRUE);
                        communication.send(responseParam.serialize());
                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_REGISTRATION) {

                    if (!dataBasesManager.checkLogin(request.getParams().get(0))){
                        dataBasesManager.insertUser(request.getParams().get(0), request.getParams().get(1));
                        communication.send(new ResponseRegistry(RegistrationMessage.REGISTRATION_SUCCESS).serialize());
                    } else {
                        communication.send(new ResponseRegistry(RegistrationMessage.WRONG_LOGIN).serialize());
                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_AUTHORIZATION) {

                    if (!dataBasesManager.checkLogin(request.getParams().get(0))){
                        communication.send(new ResponseRegistry(RegistrationMessage.WRONG_LOGIN).serialize());
                    } else {
                        if (dataBasesManager.checkPassword(request.getParams().get(0), request.getParams().get(1))){
                            communication.send(new ResponseRegistry(RegistrationMessage.AUTHORIZATION_SUCCESS).serialize());
                        } else {
                            communication.send(new ResponseRegistry(RegistrationMessage.WRONG_PASSWORD).serialize());
                        }
                    }

                }
            } catch (ClassNotFoundException e) {
                System.out.println("ошибка передачи данных с клиента");
            }

        }
    }

    /**
     * Устанавливает флаг файла)
     * @param fileFlag читаем ли из файла
     */
    public void setFileFlag(boolean fileFlag) {
        this.fileFlag = fileFlag;
    }

    /**
     * Установить текущую глубину рекурсии
     * @param depth рекурсия
     */
    public void setDepth(int depth){
        Invoker.depth = depth;
    }

}
