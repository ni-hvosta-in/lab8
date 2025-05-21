package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.util.Map;
import java.util.Scanner;

/**
 * Валидный ввод семестра
 */
public class InputValidateSemesterEnum implements Validable {

    private final Scanner sc;

    public InputValidateSemesterEnum(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param semester строка ввода
     * @return валидность семестра
     */
    @Override
    public boolean isValidate(String semester) {
        Map <String, SemesterEnum> semesterMap = SemesterEnum.getSemester();
        return semesterMap.containsKey(semester);
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного семестра
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {
        String semester;
        Map <String, SemesterEnum> semesterMap = SemesterEnum.getSemester();
        Console.writeln("Введите семестр", fileFlag);
        Console.writeln(semesterMap.keySet(), fileFlag);
        do {
            semester = sc.nextLine().trim().toLowerCase();
            if (isValidate(semester)){
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Такого семестра нет в списке");
                System.out.println("Введите семестр");
                System.out.println(semesterMap.keySet());
            }
        } while (!fileFlag);

        return semester;
    }
}
