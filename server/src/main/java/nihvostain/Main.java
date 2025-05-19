package nihvostain;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import nihvostain.managers.*;

import java.io.IOException;
import java.sql.*;

public class Main {
    private static final String URL = "jdbc:postgresql://pg/studs";
    private static final String USER = "s466207";
    private static final String PASSWORD = "SRfU{1295";
    public static void main(String[] args) throws IOException, ClassNotFoundException, RecursionDepthExceededException, InputFromScriptException, SQLException {

        CollectionManager collectionManager = new CollectionManager();
        DataBasesManager dataBasesManager = new DataBasesManager("jdbc:postgresql://localhost:5432/postgres", "postgres", "1");
        collectionManager.loadDb(dataBasesManager);
        int serverPort = 9898;
        int bufferCapacity = 10000;
        Communication communication = new Communication(serverPort, bufferCapacity);
        //Invoker invoker = new Invoker(collectionManager, communication, dataBasesManager);
        MultiPoolServer multiPoolServer = new MultiPoolServer(collectionManager, communication, dataBasesManager);
        Class.forName("org.postgresql.Driver");
        multiPoolServer.scanning();
        //invoker.scanning();

         /*


        try {
            // Регистрация драйвера (не обязательно для новых версий JDBC)
            Class.forName("org.postgresql.Driver");

            // Установка соединения
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("Подключение к базе данных установлено!");

                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO PasswordUsers(login, password) VALUES ('alpin2', '123456')");
                // Здесь можно выполнять SQL-запросы

                // Не забудьте закрыть соединение
                connection.close();
            } else {
                System.out.println("Не удалось подключиться к базе данных");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC драйвер не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных");
        }


          */
    }
}