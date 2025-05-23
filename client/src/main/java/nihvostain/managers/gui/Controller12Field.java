package nihvostain.managers.gui;

import common.model.EyeColor;
import common.model.FormOfEducation;
import common.model.HairColor;
import common.model.SemesterEnum;
import common.utility.InvalidParamMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nihvostain.managers.Invoker;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class Controller12Field {

    @FXML private Label nameFieldLabel;
    @FXML private TextField field1;
    @FXML private TextField field2;
    @FXML private TextField field3;
    @FXML private TextField field4;
    @FXML private TextField field5;
    @FXML private ChoiceBox<FormOfEducation> formOfEducationEnum;
    @FXML private ChoiceBox<SemesterEnum> semesterEnum;
    @FXML private TextField field8;
    @FXML private TextField field9;
    @FXML private ChoiceBox<EyeColor> eyeColorEnum;
    @FXML private ChoiceBox<HairColor> hairColorEnum;
    private String fieldValue;

    @FXML public void setField(ActionEvent event) throws IOException, ClassNotFoundException, TimeoutException {
        fieldValue = field1.getText() + "\n"
                + field2.getText() + "\n"
                + field3.getText() + "\n"
                + field4.getText() + "\n"
                + field5.getText() + "\n"
                + formOfEducationEnum.getValue().getForm() + "\n"
                + semesterEnum.getValue().getSem() + "\n"
                +field8.getText() + "\n"
                +field9.getText() + "\n"
                + eyeColorEnum.getValue() + "\n"
                + hairColorEnum.getValue();

    }


    public void setSemesterEnum() {
        semesterEnum.getItems().setAll(SemesterEnum.values());
    }
    public void setFormOfEducation() {
        formOfEducationEnum.getItems().setAll(FormOfEducation.values());
    }
    public void setHairColorEnum() {
        hairColorEnum.getItems().setAll(HairColor.values());
    }
    public void setEyeColorEnum() {
        eyeColorEnum.getItems().setAll(EyeColor.values());
    }

    public void setNameFieldLabel(String text) {
        nameFieldLabel.setText(text);

    }

    public String getFieldValue() {
        return fieldValue;
    }
}
