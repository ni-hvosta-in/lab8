package nihvostain.managers.gui;

import common.utility.RegistrationMessage;
import common.utility.TypeAuthentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nihvostain.commands.*;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.managers.Registration;
import nihvostain.utility.Command;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeoutException;

public class ControllerLogin {
    @FXML public Label labelState;
    @FXML public Button authButton;
    @FXML public Button registButton;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    private Communication communication;
    private String login;
    private String password;

    @FXML private void loginButtonAction(ActionEvent actionEvent) throws NoSuchAlgorithmException, IOException {

        Button clicked = (Button) actionEvent.getSource();
        labelState.setText("");
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] hash = digest.digest(passwordField.getText().getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        String password = hexString.toString();
        Registration registration = null;

        if (clicked == authButton) {
            registration = new Registration(loginField.getText(), password, communication, TypeAuthentication.AUTHORIZATION);
        } else if (clicked == registButton) {
            registration = new Registration(loginField.getText(), password, communication, TypeAuthentication.REGISTRATION);
        }
        RegistrationMessage message = null;
        try {
            message = registration.register();
            if (message.equals(RegistrationMessage.AUTHORIZATION_SUCCESS) || message.equals(RegistrationMessage.REGISTRATION_SUCCESS)) {
                labelState.setText("Login Successful");
                login = loginField.getText();
                this.password = password;
                openMainWindow();
                Stage stage = (Stage) authButton.getScene().getWindow();
                stage.close();
            } else {
                labelState.setText(message.toString());
            }
        } catch (TimeoutException e) {
            labelState.setText("сервер временно не доступен");
        } catch (ClassNotFoundException e) {
            labelState.setText("ошибка передачи данных, попробуйте снова");
        }
    }

    public void openMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = fxmlLoader.load();
        ControllerMain controllerMain = fxmlLoader.getController();
        controllerMain.setCommunication(communication);
        controllerMain.setLogin(login);
        controllerMain.setPassword(password);


        LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(commands.values(), controllerMain.getResultLabel()));
        commands.put("show", new ShowCommand(communication));
        commands.put("info", new InfoCommand(communication));
        commands.put("insert", new InsertCommand(communication, login, password));
        commands.put("update", new UpdateCommand(communication, login, password));
        commands.put("remove_key", new RemoveKeyCommand(communication, login, password));
        commands.put("clear", new ClearCommand(communication));
        commands.put("execute_script", new ExecuteScriptCommand(communication, login, password, controllerMain.getResultLabel()));
        commands.put("exit", new ExitCommand(communication));
        commands.put("remove_lower", new RemoveLowerCommand(communication));
        commands.put("replace_if_greater", new ReplaceIfGreaterCommand(communication, login, password));
        commands.put("remove_greater_key", new RemoveGreaterKeyCommand(communication));
        commands.put("group_counting_by_semester_enum", new GroupCountingBySemesterEnum(communication));
        commands.put("filter_contains_name", new FilterContainsNameCommand(communication));
        commands.put("filter_greater_than_group_admin", new FilterGreaterThanGroupAdminCommand(communication));
        Invoker.setCommands(commands);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Main Window");
        stage.setScene(scene);
        stage.show();

    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }
}
