package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Map;
import java.util.Scanner;

/**
 * Валидный ввод цвета волос
 */
public class InputValidateHair implements Validable {

    private final Scanner sc;

    public InputValidateHair(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param hairColor строка ввода
     * @return валидность цвета волос
     */
    @Override
    public boolean isValidate(String hairColor) {
        Map<String, HairColor> colorHairMap = HairColor.getColors();
        return colorHairMap.containsKey(hairColor);
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода цвета волос
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {

        String hairColor;
        Map<String, HairColor> colorHairMap = HairColor.getColors();
        Console.writeln("Введите цвет волос из списка ", fileFlag);
        Console.writeln(colorHairMap.keySet(), fileFlag);
        do{
            hairColor = sc.nextLine().trim().toLowerCase();
            if (isValidate(hairColor)){
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Такого цвета в списке нет");
                System.out.println("Введите цвет волос из списка ");
                System.out.println(colorHairMap.keySet());
            }
        } while (!fileFlag);

        return hairColor;
    }
}
