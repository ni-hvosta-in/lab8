package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Scanner;

/**
 * Валидный ввод координаты y
 */
public class InputValidateY implements Validable {

    private final Scanner sc;

    public InputValidateY(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param y строка ввода
     * @return валидность y
     */
    @Override
    public boolean isValidate(String y) {
        y = y.replace(",", ".");
        try {
            Double.parseDouble(y);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода y
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {

        String y;
        Console.write("Введите координату y ", fileFlag);
        do{
            y = sc.nextLine().trim();
            if (y.isEmpty()){
                return "0";
            } else {
                if (isValidate(y)){
                    break;
                } else {
                    if (fileFlag){
                        throw new InputFromScriptException();
                    }
                    System.out.println("Неверный ввод, Y должен быть типа Double");
                    System.out.print("Введите координату y ");
                }
            }
        } while (!fileFlag);
        return y;
    }
}
