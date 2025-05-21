package nihvostain.managers.validate;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Валидный ввод даты рождения
 */
public class InputValidateBirthday implements Validable {

    private final Scanner sc;

    public InputValidateBirthday(Scanner sc){
        this.sc = sc;
    }

    /**
     * @param birthdayRus строка ввода
     * @return валидность даты рождения
     */
    @Override
    public boolean isValidate(String birthdayRus) {
        try {
            if (birthdayRus.length() == 25) {
                String birthday;
                birthday = Person.convertBirthdayFromRus(birthdayRus);
                ZonedDateTime.parse(birthday);
            } else {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * @param fileFlag флаг файла
     * @return результат валидного ввода даты рождения
     * @throws InputFromScriptException ошибка в скрипте
     */
    @Override
    public String inputValidate(boolean fileFlag) throws InputFromScriptException {
        String birthdayRus;
        Console.write("Введите день рождения в формате 'dd-MM-yyyy'T'HH:mm:ssXXX' ", fileFlag);
         do {
            birthdayRus = sc.nextLine().trim();
            if (birthdayRus.isEmpty()) {
                return null;
            }
            if (isValidate(birthdayRus)) {
                break;
            } else {
                if (fileFlag){
                    throw new InputFromScriptException();
                }
                System.out.println("Неверный ввод, день рождения должен быть в формате 'dd-MM-yyyy'T'HH:mm:ssXXX'\n" +
                        "Пример: 25-07-2006T14:30:00+03:00");
                System.out.print("Введите день рождения в формате 'dd-MM-yyyy'T'HH:mm:ssXXX' ");
            }
        } while (!fileFlag);
        return Person.convertBirthdayFromRus(birthdayRus);
    }
}
