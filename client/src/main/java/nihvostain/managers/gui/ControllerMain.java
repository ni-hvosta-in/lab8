package nihvostain.managers.gui;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ControllerMain {
    @FXML private Label loginLabel;
    @FXML private TextArea resultLabel;
    private Communication communication;
    private String login;
    private String password;

    @FXML public void execute(ActionEvent actionEvent) throws RecursionDepthExceededException, IOException {

        Button clicked = (Button) actionEvent.getSource();
        System.out.println(clicked.getText());
        Command command = Invoker.getCommands().get(clicked.getText());
        String comm = clicked.getText();

        if (command.getNeededArgsLen() == 1){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/onefieldset.fxml"));
            Parent root = fxmlLoader.load();
            ControllerOneField controllerOneField = fxmlLoader.getController();
            controllerOneField.setFieldLabel(clicked.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Окно ввода");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокирует родительское окно
            stage.showAndWait(); // Ожидание закрытия окна
            comm = comm +" "+ controllerOneField.getFieldValue();
        }
        Scanner scanner = new Scanner(comm);
        resultLabel.setEditable(false);

        Invoker invoker = new Invoker(scanner, communication, login, password, resultLabel);
        invoker.setFileFlag(true);
        try {
            invoker.scanning();
        } catch (InputFromScriptException e) {
            resultLabel.setText(e.getMessage());
        }
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public void setLogin(String login) {
        this.login = login;
        loginLabel.setText(login);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TextArea getResultLabel() {
        return resultLabel;
    }
}
