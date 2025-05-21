package nihvostain;

import common.exceptions.*;
import common.model.*;
import common.utility.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.managers.Registration;
import nihvostain.managers.gui.ControllerLogin;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Main extends Application {
    static int timeout = 3000;
    static int serverPort = 9898;  // Порт сервера (куда отправляем)
    static int clientPort = 8888;
    static int bufferCapacity = 10000;
    static Communication communication;

    static {
        try {
            communication = new Communication(serverPort, clientPort, bufferCapacity, timeout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        System.out.println("Добро пожаловать в консоль для управления коллекцией StudyGroup");

        Coordinates.setFields(new FieldsCoordinate[]{FieldsCoordinate.X, FieldsCoordinate.Y});

        Person.setFields(new FieldsPerson[]{FieldsPerson.NAME, FieldsPerson.BIRTHDAY,
                FieldsPerson.PassportID, FieldsPerson.EyeCOLOR, FieldsPerson.HairCOLOR});

        StudyGroup.setFields(new FieldsStudyGroup[]{FieldsStudyGroup.NAME,
                FieldsStudyGroup.COORDINATES, FieldsStudyGroup.StudentsCount,
                FieldsStudyGroup.FormOFEducation, FieldsStudyGroup.SEMESTER, FieldsStudyGroup.GroupADMIN});


        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lab8auth2.fxml"));
        Parent root = fxmlLoader.load();
        ControllerLogin controllerLogin = fxmlLoader.getController();
        controllerLogin.setCommunication(communication);
        // Настройка сцены и отображение окна
        Scene scene = new Scene(root);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }
}