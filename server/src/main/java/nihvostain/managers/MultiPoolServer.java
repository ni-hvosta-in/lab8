package nihvostain.managers;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import common.managers.*;
import common.model.FormOfEducation;
import common.model.Person;
import common.model.StudyGroup;
import common.model.StudyGroupWithKey;
import common.utility.*;
import nihvostain.commands.*;
import nihvostain.utility.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
/**
 * Класс вызывателя команд
 */
public class MultiPoolServer {

    private final ForkJoinPool readPool = ForkJoinPool.commonPool();

    private final ExecutorService executePool = Executors.newCachedThreadPool();

    private final ExecutorService responsePool = Executors.newCachedThreadPool();

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


    public MultiPoolServer(CollectionManager collectionManager, Communication communication, DataBasesManager dataBasesManager) {
        this.collectionManager = collectionManager;
        this.communication = communication;
        this.dataBasesManager = dataBasesManager;
        depth += 1;
    }

    private class ReadTaskManager extends RecursiveAction {

        private final Map<TypeCommand, Command> commands;

        public ReadTaskManager(Communication communication, Map<TypeCommand, Command> commands) {
            this.commands = commands;
        }

        protected void compute() {
            try {
                byte[] message = communication.receive();
                Request request = new Deserialize<Request>(message).deserialize();
                System.out.println(request.getTypeRequest()+" "+ request.getLogin() + " " + request.getPassword());
                executePool.execute(new ExecuteTaskManager(request, commands));
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("ошибка десериализации");
                responsePool.execute(new ResponseTaskManager(new RequestObj("data.error")));
            }
        }
    }

    private class ExecuteTaskManager implements Runnable {
        private final Request request;
        private final Map<TypeCommand, Command> commands;
        public ExecuteTaskManager(Request request, Map<TypeCommand, Command> commands) {
            this.request = request;
            this.commands = commands;
        }

        @Override
        public void run() {

            try {
                if (request.getTypeRequest() == TypeRequest.REQUEST_COMMAND) {

                    Command command = commands.get(request.getName());
                    if (command.isValidParam(request.getParams()) == InvalidParamMessage.TRUE) {
                        RequestObj response = command.execute(request);
                        responsePool.execute(new ResponseTaskManager(response));
                    }
                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PARAM) {

                    ResponseParam responseParam = new ResponseParam(commands.get(request.getName()).isValidParam(request.getParams()));
                    responsePool.execute(new ResponseTaskManager(responseParam));


                } else if (request.getTypeRequest() == TypeRequest.REQUEST_PASSPORT) {

                    if (Person.getPassportIDList().contains(request.getParams().get(0))) {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.FALSE);
                        responsePool.execute(new ResponseTaskManager(responseParam));
                    } else {
                        ResponseParam responseParam = new ResponseParam(InvalidParamMessage.TRUE);
                        responsePool.execute(new ResponseTaskManager(responseParam));
                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_REGISTRATION) {
                    if (!dataBasesManager.checkLogin(request.getParams().get(0))) {
                        dataBasesManager.insertUser(request.getParams().get(0), request.getParams().get(1));
                        responsePool.execute(new ResponseTaskManager(new ResponseRegistry(RegistrationMessage.REGISTRATION_SUCCESS)));
                    } else {
                       responsePool.execute(new ResponseTaskManager(new ResponseRegistry(RegistrationMessage.WRONG_LOGIN)));
                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_AUTHORIZATION) {

                    if (!dataBasesManager.checkLogin(request.getParams().get(0))) {
                        responsePool.execute(new ResponseTaskManager(new ResponseRegistry(RegistrationMessage.WRONG_LOGIN)));
                    } else {
                        if (dataBasesManager.checkPassword(request.getParams().get(0), request.getParams().get(1))) {
                            responsePool.execute(new ResponseTaskManager(new ResponseRegistry(RegistrationMessage.AUTHORIZATION_SUCCESS)));
                        } else {
                            responsePool.execute(new ResponseTaskManager(new ResponseRegistry(RegistrationMessage.WRONG_PASSWORD)));
                        }
                    }

                } else if (request.getTypeRequest() == TypeRequest.REQUEST_STUDYGROUPS){

                    System.out.println("отправил");
                    ArrayList<StudyGroupWithKey> studyGroupWithKeys = new ArrayList<>();
                    for (Map.Entry<String, StudyGroup> pair : collectionManager.getSortedStudyGroupList().entrySet()) {
                        studyGroupWithKeys.add(new StudyGroupWithKey(pair.getKey(),pair.getValue(), dataBasesManager.getLoginByKey(pair.getKey())));
                    }
                    ResponseStudyGroups responseStudyGroups = new ResponseStudyGroups(studyGroupWithKeys);

                    responsePool.execute(new ResponseTaskManager(responseStudyGroups));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                responsePool.execute(new ResponseTaskManager(new RequestObj("database.error")));
            } catch (IOException e) {
                System.out.println("ошибка передачи данных с клиента");
            }
        }
    }

    private class ResponseTaskManager implements Runnable {
        private final RequestResponse response;
        public ResponseTaskManager(RequestResponse response) {
            this.response = response;
        }

        @Override
        public void run() {

            try {
                communication.send(response.serialize());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }



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
        commands.put(TypeCommand.REMOVE_LOWER, new RemoveLowerCommand(collectionManager, communication, dataBasesManager));
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
            readPool.invoke(new ReadTaskManager(communication, commands));
        }
    }

    /**
     * Устанавливает флаг файла)
     *
     * @param fileFlag читаем ли из файла
     */
    public void setFileFlag(boolean fileFlag) {
        this.fileFlag = fileFlag;
    }

    /**
     * Установить текущую глубину рекурсии
     *
     * @param depth рекурсия
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
