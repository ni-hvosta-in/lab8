package nihvostain.managers.gui;

import common.exceptions.InputFromScriptException;
import common.exceptions.RecursionDepthExceededException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;

import java.io.IOException;
import java.util.Scanner;

public class ControllerMain {
    @FXML private Label loginLabel;
    @FXML private TextArea resultLabel;
    private Communication communication;
    private String login;
    private String password;

    @FXML public void execute(ActionEvent actionEvent) throws RecursionDepthExceededException, InputFromScriptException, IOException {

        Button clicked = (Button) actionEvent.getSource();
        System.out.println(clicked.getText());
        Scanner scanner = new Scanner(clicked.getText());
        resultLabel.setEditable(false);
        Invoker invoker = new Invoker(scanner, communication, login, password, resultLabel);
        invoker.setFileFlag(true);
        invoker.scanning();
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
