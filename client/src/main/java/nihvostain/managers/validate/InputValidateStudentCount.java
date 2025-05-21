package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Scanner;

/**
 * Валидный ввод количества студентов
 */
public class InputValidateStudentCount implements Validable {

    private final Scanner sc;

    public InputValidateStudentCount(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param studentCount строка ввода
     * @return валидность кол-ва студентов
     */
    @Override
    public boolean isValidate(String studentCount) {
        try {
            long stC = Long.parseLong(studentCount);
            return stC > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода числа студентов
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {

        String studentCount;
        Console.write("Введите кол-во студентов ", fileFlag);
        do{
            studentCount = sc.nextLine().trim();
            if (studentCount.equals("0")){
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Неверный ввод, число студентов не может быть 0");
                System.out.print("Введите кол-во студентов ");
            } else {
                if (isValidate(studentCount)){
                    break;
                } else {
                    if (fileFlag){
                        throw new InputFromScriptException();
                    }
                    System.out.println("Неверный ввод, кол-во студентов должно быть типа Long");
                    System.out.print("Введите кол-во студентов ");
                }
            }
        } while (!fileFlag);
        return studentCount;
    }
}
