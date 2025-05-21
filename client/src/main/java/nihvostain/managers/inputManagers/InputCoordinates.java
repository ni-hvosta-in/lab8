package nihvostain.managers.inputManagers;

import common.exceptions.*;
import common.model.*;
import common.utility.*;
import nihvostain.managers.validate.InputValidateX;
import nihvostain.managers.validate.InputValidateY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Класс для ввода координат
 */
public class InputCoordinates extends InputClass{

    public InputCoordinates(Scanner sc, boolean fileFlag) {
        super(sc, fileFlag);
    }

    /**
     * @return введенный массив строк
     * @throws InputFromScriptException ошибка скрипта
     */
    @Override
    public ArrayList<String> input() throws InputFromScriptException, IOException, ClassNotFoundException, TimeoutException {
        ArrayList<String> args = new ArrayList<>();

        HashMap<FieldsCoordinate, Validable> validableHashMap = new HashMap<>();
        validableHashMap.put(FieldsCoordinate.X, new InputValidateX(this.getSc()));
        validableHashMap.put(FieldsCoordinate.Y, new InputValidateY(this.getSc()));

        for (FieldsCoordinate fieldsCoordinate : Coordinates.getFields()){
            args.add(validableHashMap.get(fieldsCoordinate).inputValidate(this.isFileFlag()));
        }
        return args;
    }
}
