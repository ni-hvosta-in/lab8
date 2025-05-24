package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Scanner;

/**
 * Валидный ввод координаты x
 */
public class InputValidateX implements Validable {

    private final Scanner sc;

    public InputValidateX(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param x строка ввода
     * @return валидность x
     */
    @Override
    public boolean isValidate(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода x
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {

        String x;
        Console.write("Введите координату x ", fileFlag);
        do{
            x = sc.nextLine().trim();
            if (isValidate(x)){
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Неверный ввод, X должен быть типа Integer");
                System.out.print("Введите координату x ");
            }
        } while (!fileFlag);
        return x;
    }

    @Override
    public TypeWrongField getTypeWrongField() {
        return TypeWrongField.WRONG_X;
    }
}
