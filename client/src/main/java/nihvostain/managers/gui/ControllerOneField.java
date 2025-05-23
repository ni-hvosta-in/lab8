package nihvostain.managers.gui;

import common.utility.InvalidParamMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ControllerOneField {
    @FXML private Label nameFieldLabel;
    @FXML private TextField fieldLabel;
    private String fieldValue;
    private Command command;
    private String fieldName;
    private TextArea resultLabel;
    @FXML public void setField(ActionEvent event) throws IOException, ClassNotFoundException, TimeoutException {
        fieldValue = fieldLabel.getText();
        ArrayList<String> args = new ArrayList<>();
        args.add(fieldValue);
        Stage stage = (Stage) fieldLabel.getScene().getWindow();
        if (command.isValidParam(args) == InvalidParamMessage.TRUE){
            stage.close();
        } else {
            fieldLabel.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
            resultLabel.setText(command.isValidParam(args).getMessage());
        }

    }

    public void setFieldLabel(String fieldName) {
        this.fieldName = fieldName;
        this.nameFieldLabel.setText(fieldName);
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setResultLabel(TextArea resultLabel) {
        this.resultLabel = resultLabel;
    }
}
