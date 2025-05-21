package nihvostain.managers.gui;

import common.managers.Deserialize;
import common.utility.RegistrationMessage;
import common.utility.TypeAuthentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Registration;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ControllerLogin {
    @FXML public Label labelState;
    @FXML public Button authButton;
    @FXML public Button registButton;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    private Communication communication;

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

        System.out.println(authButton);
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



    public void setCommunication(Communication communication) {
        this.communication = communication;
    }
}
