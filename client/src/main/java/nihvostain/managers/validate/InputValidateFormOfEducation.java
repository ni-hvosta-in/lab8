package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Map;
import java.util.Scanner;

/**
 * Валидный ввод формы обучения
 */
public class InputValidateFormOfEducation implements Validable {

    private final Scanner sc;

    public InputValidateFormOfEducation(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param formOfEducation строка ввода
     * @return валидность формы обучения
     */
    @Override
    public boolean isValidate(String formOfEducation) {
        Map <String, FormOfEducation> formOfEducationMap = FormOfEducation.getFormOfEducation();
        return formOfEducationMap.containsKey(formOfEducation);
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода формы обучения
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {
        String formOfEducation;
        Map <String, FormOfEducation> formOfEducationMap = FormOfEducation.getFormOfEducation();
        Console.writeln("Введите форму обучения из списка", fileFlag);
        Console.writeln(formOfEducationMap.keySet(), fileFlag);
        do {
            formOfEducation = sc.nextLine().trim().toLowerCase();
            if (isValidate(formOfEducation)){
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Такой формы обучения нет в списке");
                System.out.println("Введите форму обучения из списка");
                System.out.println(formOfEducationMap.keySet());
            }
        } while (!fileFlag);

        return formOfEducation;
    }
}
