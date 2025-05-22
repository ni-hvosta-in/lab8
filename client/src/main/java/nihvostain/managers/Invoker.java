package nihvostain.managers;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import nihvostain.commands.*;
import nihvostain.managers.inputManagers.InputPerson;
import nihvostain.managers.inputManagers.InputStudyGroup;
import nihvostain.utility.Command;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * Класс вызывателя команд
 */
public class Invoker {

    /**
     * Сканер
     */
    private Scanner sc;
    /**
     * Флаг файла
     */
    private boolean fileFlag = false;

    private final String login;
    private final String password;
    private final TextArea resulLabel;
    /**
     * Коммуникация
     */
    private final Communication communication;

    /**
     * Текущая глубина рекурсии
     */
    private static int depth;
    /**
     * Максимальная глубина рекурсии
     */
    private static final int maxDepth = 10;
    /**
         * @param sc сканер
     */
    private static LinkedHashMap<String, Command> commands;

    public Invoker(Scanner sc, Communication communication, String login, String password, TextArea resulLabel) {

        this.sc = sc;
        this.communication = communication;
        this.login = login;
        this.password = password;
        this.resulLabel = resulLabel;
    }

    public static void setCommands(LinkedHashMap<String, Command> commands) {
        Invoker.commands = commands;
    }

    /**
     * исполнение команд и сканирование ввода
     * @throws InputFromScriptException ошибка в скрипте
     * @throws RecursionDepthExceededException ошибка глубины рекурсии
     */
    public void scanning() throws InputFromScriptException, RecursionDepthExceededException, IOException {

        while (sc.hasNext()){

            String line = sc.nextLine().trim();
            ArrayList <String> tokens = new ArrayList<>(List.of(line.split(" +")));
            Command command = commands.get(tokens.get(0));
            tokens.remove(0);
            ArrayList<String> args = new ArrayList<>(tokens);
            if (command != null) {
                if (command.getNeededParamLen() == tokens.size()){
                    try {
                        if (command.isValidParam(tokens) == InvalidParamMessage.TRUE){

                            if (command.getElementType().equals(TypeOfElement.PERSON)) {
                                try {
                                    args.addAll(new InputPerson(sc, fileFlag, command.skipValidateField(), communication, login, password).input());
                                } catch (NoAdminException e){
                                    args.add(null);
                                }
                            } else if (command.getElementType().equals(TypeOfElement.STUDYGROUP)) {
                                args.addAll(new InputStudyGroup(sc, fileFlag, command.skipValidateField(), communication, login, password).input());
                            }

                            if (command == commands.get("help")) {
                                command.request(args);
                            } else {
                                try {
                                    communication.send(command.request(args).addUser(login, password).serialize());
                                    byte[] message = communication.receive();
                                    resulLabel.setText(new Deserialize<RequestObj>(message).deserialize().getRequest().toString());
                                    if (command == commands.get("exit")) {
                                        System.exit(0);
                                    }
                                } catch (NullPointerException e){
                                    continue;
                                }
                            }
                        } else if (fileFlag) {
                            System.out.println("Неверные параметры для вызванной команды в скрипте");
                            throw new InputFromScriptException();
                        } else {
                            try {
                                System.out.println(command.isValidParam(tokens).getMessage());
                            } catch (ClassNotFoundException e) {
                                System.out.println("ошибка передачи данных с сервера");
                            }
                        }
                    } catch (ClassNotFoundException | StreamCorruptedException e) {
                        System.out.println("ошибка передачи данных с сервера");
                    } catch (TimeoutException e) {
                        System.out.println("сервер временно не доступен");
                    }
                } else {
                    if (fileFlag){
                        System.out.println("Неверные параметры для вызванной команды в скрипте");
                        throw new InputFromScriptException();
                    }
                    System.out.println("Неверные параметры для вызванной команды");
                }
            } else {
                if (fileFlag) {
                    System.out.println("Неопознанная команда в скрипте");
                    throw new InputFromScriptException();
                }
                System.out.println("такой команды нет, используйте команду help");
            }
            Console.write("~ ", (fileFlag & Invoker.depth != 1));

        }
        if (!fileFlag){
            Invoker invoker = new Invoker(new Scanner(System.in), communication, login, password, resulLabel);
            invoker.setDepth(1);
            System.out.println("чтобы выйти из консоли введите exit");
            System.out.print("~ ");
            invoker.scanning();
        }

    }

    /**
     * Устанавливает флаг файла
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
