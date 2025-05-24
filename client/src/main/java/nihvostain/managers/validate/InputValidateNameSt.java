package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Scanner;

public class InputValidateNameSt implements Validable {
    private final Scanner sc;

    public InputValidateNameSt(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param name строка ввода
     * @return валидность имени
     */
    @Override
    public boolean isValidate(String name) throws NoAdminException {
        return !name.isEmpty();
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода имени
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException, NoAdminException{
        String name;
        Console.write("Введите имя группы ", fileFlag);
        do{
            name = sc.nextLine().trim();
            if (!isValidate(name)){
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("имя не может быть пустым");
                System.out.print("Введите имя группы ");
            } else {
                break;
            }
        } while (!fileFlag);
        return name;
    }

    @Override
    public TypeWrongField getTypeWrongField() {
        return TypeWrongField.WRONG_NAME;
    }
}
