package nihvostain.managers.inputManagers;

import common.exceptions.*;
import common.model.*;
import common.utility.*;
import nihvostain.managers.Communication;
import nihvostain.managers.validate.InputValidateFormOfEducation;
import nihvostain.managers.validate.InputValidateNameSt;
import nihvostain.managers.validate.InputValidateSemesterEnum;
import nihvostain.managers.validate.InputValidateStudentCount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Класс для ввода учебной группы
 */
public class InputStudyGroup extends InputClass{

    private boolean skipFlag;
    private final Communication communication;
    private final String login;
    private final String password;
    public InputStudyGroup(Scanner sc, boolean fileFlag, boolean skipFlag, Communication communication, String login, String password) {
        super(sc, fileFlag);
        this.skipFlag = skipFlag;
        this.communication = communication;
        this.login = login;
        this.password = password;
    }

    /**
     * @return ввод
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public ArrayList<String> input() throws InputFromScriptException, IOException, ClassNotFoundException, TimeoutException {

        ArrayList<String> args = new ArrayList<>();
        HashMap<FieldsStudyGroup, Validable> validableHashMap = new HashMap<>();
        validableHashMap.put(FieldsStudyGroup.NAME, new InputValidateNameSt(this.getSc()));
        validableHashMap.put(FieldsStudyGroup.StudentsCount, new InputValidateStudentCount(this.getSc()));
        validableHashMap.put(FieldsStudyGroup.FormOFEducation, new InputValidateFormOfEducation(this.getSc()));
        validableHashMap.put(FieldsStudyGroup.SEMESTER, new InputValidateSemesterEnum(this.getSc()));

        for (FieldsStudyGroup fieldsStudyGroup : StudyGroup.getFields()){
            if (fieldsStudyGroup == FieldsStudyGroup.COORDINATES){
                ArrayList<String> coordinates = new InputCoordinates(this.getSc(), this.isFileFlag()).input();
                args.addAll(coordinates);
            } else if (fieldsStudyGroup == FieldsStudyGroup.GroupADMIN) {
                try {
                    ArrayList<String> groupAdmin = new InputPerson(this.getSc(), this.isFileFlag(), skipFlag, communication, login, password).input();
                    args.addAll(groupAdmin);
                } catch (NoAdminException e){
                    args.add(null);
                }
            } else {
                args.add(validableHashMap.get(fieldsStudyGroup).inputValidate(this.isFileFlag()));
            }
        }

        return args;
    }
}
