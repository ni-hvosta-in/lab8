package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Map;
import java.util.Scanner;

/**
 * Валидный ввод цвета глаз
 */
public class InputValidateEye implements Validable {

    private final Scanner sc;

    public InputValidateEye(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param eyeColor строка ввода
     * @return валидность цвета глаз
     */
    @Override
    public boolean isValidate(String eyeColor) {
        Map<String, EyeColor> colorEyeMap = EyeColor.getColors();
        return colorEyeMap.containsKey(eyeColor);
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода цвета глаз
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {

        String eyeColor;
        Map<String, EyeColor> colorEyeMap = EyeColor.getColors();
        Console.writeln("Введите цвет глаз из списка ", fileFlag);
        Console.writeln(colorEyeMap.keySet(), fileFlag);
        do {
            eyeColor = sc.nextLine().trim().toLowerCase();
            if (eyeColor.isEmpty()){
                return null;
            }
            if (isValidate(eyeColor)){
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Такого цвета в списке нет");
                System.out.println("Введите цвет глаз из списка ");
                System.out.println(colorEyeMap.keySet());
            }
        } while (!fileFlag);

        return eyeColor;
    }

    @Override
    public TypeWrongField getTypeWrongField() {
        return TypeWrongField.WRONG_EYE;
    }
}
