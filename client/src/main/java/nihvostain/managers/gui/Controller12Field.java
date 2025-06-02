package nihvostain.managers.gui;

import common.exceptions.NoAdminException;
import common.model.EyeColor;
import common.model.FormOfEducation;
import common.model.HairColor;
import common.model.SemesterEnum;
import common.utility.InvalidParamMessage;
import common.utility.Validable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nihvostain.managers.Communication;
import nihvostain.managers.Invoker;
import nihvostain.managers.validate.*;
import nihvostain.utility.Command;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Controller12Field {

    @FXML private VBox VBoxRoot;
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
    @FXML private TextField field10;
    @FXML private ChoiceBox<EyeColor> eyeColorEnum;
    @FXML private ChoiceBox<HairColor> hairColorEnum;
    @FXML Button setFieldButton;
    private String fieldValue;
    private Communication communication;
    private Command command;
    private String login;
    private String password;
    private TextArea resultLabel;
    private int removeRow;
    private boolean inputPerson;
    private boolean inputFromTableFlag = false;
    @FXML public void setField(ActionEvent event) throws IOException, ClassNotFoundException, TimeoutException {
        fieldValue = "";
        if (!inputPerson) {
            fieldValue = field1.getText().trim() + "\n"
                    + field2.getText().trim() + "\n"
                    + field3.getText().trim() + "\n"
                    + field4.getText().trim() + "\n"
                    + field5.getText().trim() + "\n";
            if (formOfEducationEnum.getValue() != null) {
                fieldValue += formOfEducationEnum.getValue().getForm() + "\n";
            } else {
                fieldValue += "\n";
            }
            if (semesterEnum.getValue() != null) {
                fieldValue += semesterEnum.getValue().getSem() + "\n";
            } else {
                fieldValue += "\n";
            }
        } else {
            fieldValue = "\n";
        }
        fieldValue += field8.getText() + "\n"
                +field9.getText().trim() + "\n"
                + field10.getText().trim() + "\n";
        if (eyeColorEnum.getValue() != null) {
            fieldValue += eyeColorEnum.getValue().getColor() + "\n";
        } else {
            fieldValue += "\n";
        }
        if (hairColorEnum.getValue() != null) {
            fieldValue += hairColorEnum.getValue().getColor() + "\n";
        } else {
            fieldValue += "\n";
        }
        Map<Control, Validable> map = new LinkedHashMap<>();
        map.put(field2, new InputValidateNameSt(null));
        map.put(field3, new InputValidateX(null));
        map.put(field4, new InputValidateY(null));
        map.put(field5, new InputValidateStudentCount(null));
        map.put(formOfEducationEnum, new InputValidateFormOfEducation(null));
        map.put(semesterEnum, new InputValidateSemesterEnum(null));
        map.put(field8, new InputValidateNameP(null));
        map.put(field9, new InputValidateBirthday(null));
        map.put(field10, new InputValidatePassportID(null, command.skipValidateField() ,communication, login, password));
        map.put(eyeColorEnum, new InputValidateEye(null));
        map.put(hairColorEnum, new InputValidateHair(null));

        String wrongFields = "";
        int i = 2;
        for (Map.Entry<Control, Validable> entry : map.entrySet()) {
            if (i<=removeRow) {
                i++;
                continue;
            }

            if (entry.getKey() instanceof TextField) {
                try {
                    if (!entry.getValue().isValidate(((TextField) entry.getKey()).getText().trim())) {
                        entry.getKey().setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                        wrongFields += entry.getValue().getTypeWrongField().getMessage() + "\n";
                    } else {
                        entry.getKey().setStyle("-fx-text-box-border: #00ff00; -fx-focus-color: #00ff00;");
                    }
                } catch (NoAdminException e) {
                    System.out.println("нет админа");
                    break;
                }
            } else if (entry.getKey() instanceof ChoiceBox) {
                if (((ChoiceBox<?>) entry.getKey()).getValue() == null) {
                    if (!entry.getValue().isValidate("")) {
                        entry.getKey().setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                        wrongFields += entry.getValue().getTypeWrongField().getMessage() + "\n";
                    }
                } else{
                    entry.getKey().setStyle("-fx-text-box-border: #00ff00; -fx-focus-color: #00ff00;");

                }
            }
        }
        ArrayList<String> args = new ArrayList<>();
        args.add(field1.getText().trim());
        InvalidParamMessage paramMessage = command.isValidParam(args);
        if ((!wrongFields.isEmpty() || (paramMessage != InvalidParamMessage.TRUE & !inputFromTableFlag))){
            if (inputFromTableFlag) {
                resultLabel.setText(wrongFields);
            } else {
                resultLabel.setText(wrongFields + paramMessage.getMessage());
            }
            if (paramMessage != InvalidParamMessage.TRUE) {
                field1.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
            }
        } else {
            Stage stage = (Stage) setFieldButton.getScene().getWindow();
            stage.close();
        }
        //СОЗДАЮ STUDYGROUP мотрю на валидность формирую массив enum ответы на валидность
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

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResultLabel(TextArea resultLabel) {
        this.resultLabel = resultLabel;
    }
    public void removeFirstHBox(){
        VBoxRoot.getChildren().remove(0);
        removeRow++;
    }

    public void setInputPerson(boolean inputPerson) {
        this.inputPerson = inputPerson;
    }

    public void setInputFromTableFlag(boolean inputFromTableFlag) {
        this.inputFromTableFlag = inputFromTableFlag;
    }
}
