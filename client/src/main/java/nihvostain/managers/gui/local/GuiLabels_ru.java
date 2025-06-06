package nihvostain.managers.gui.local;

import java.util.ListResourceBundle;

public class GuiLabels_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"button.auth", "Авторизация"},
                {"button.reg", "Регистрация"},
                {"label.login", "Логин"},
                {"label.password", "Пароль"},
                {"server.timeOut", "сервер временно не доступен"},
                {"wrong.login", "не верный логин"},
                {"wrong.password", "не верный пароль"},
                {"data.error", "ошибка передачи данных, попробуйте снова"},
                {"help", "помощь"},
                {"exit", "выход"},
                {"show", "показать"},
                {"info", "информация"},
                {"insert", "ввод"},
                {"update", "обновить"},
                {"remove_key", "удалить_по_ключу"},
                {"clear", "очистить"},
                {"execute_script", "выполнить_скрипт"},
                {"remove_lower", "удалить_меньшее"},
                {"replace_if_greater", "заменить_есл_больше"},
                {"remove_greater_key", "удалить_большие_ключи"},
                {"group_counting_by_semester_enum", "сгруппировать_по_учебной_группе"},
                {"filter_contains_name", "фильтр_содержание_имя"},
                {"filter_greater_than_group_admin", "фильтр_больший_чем_староста"},
                {"edit", "редактировать"},
                {"delete", "удалить"},
                {"visualize", "визуализация"},
                {"OK", "ок"},
                {"key", "ключ"},
                {"id", "ид"},
                {"name", "имя"},
                {"x", "x"},
                {"y", "y"},
                {"studentsCount", "количество студентов"},
                {"creationDate", "дата создания"},
                {"formOfEducation", "форма обучения"},
                {"semesterEnum", "семестр"},
                {"nameP", "имя старосты"},
                {"birthday", "день рождения"},
                {"passportID", "паспорт"},
                {"eyeColor", "цвет глаз"},
                {"hairColor", "цвет волос"},
                {"scriptName", "имя скрипта"},
                {"nameSubstring", "подстрока имени"},
                {"",""},
                {"recursion", "превышен лимит глубины рекурсии, проверь скрипт"},
                {"alien", "элемент другого пользователя"},
                {"wrong_name", "имя не может быть пустым"},
                {"wrong_X", "X должен быть типа Integer"},
                {"wrong_Y", "Y должен быть типа Double"},
                {"wrong_student_count", "кол-во студентов должно быть типа Long и больше нуля"},
                {"wrong_form_of_edu", "Такой формы обучения нет в списке"},
                {"wrong_semester_enum", "Такого семестра нет в списке"},
                {"wrong_name_P", "имя не может быть пустым"},
                {"wrong_birthday", "день рождения должен быть в формате 'dd-MM-yyyy'T'HH:mm:ssXXX'\\n\" +\n" +
                        "                        \"Пример: 25-07-2006T14:30:00+03:00"},
                {"wrong_passport_id", "Такого паспорта не существует или он не ваш"},
                {"wrong_eye", "Такого цвета в списке нет"},
                {"wrong_hair", "Такого цвета в списке нет"},
                {"goodParam", "все гуд"},
                {"existingKey", "такой ключ уже есть"},
                {"noKey", "нет такого ключа"},
                {"noID", "нет такого id"},
                {"notLongID", "id должен быть Long"},
                {"false", "все очень плохо"},
                {"fileNotFound", "нет файла с таким именем"},
                {"wrongParamInScript", "Неверные параметры для вызванной команды в скрипте"},
                {"wrongParam", "Неверные параметры для вызванной команды"},
                {"wrongCommand", "Неопознанная команда в скрипте"},
                {"clear : очистить коллекцию", "clear : очистить коллекцию"},
                {"execute_script file_name : считать и исполнить скрипт из указанного файла." +
                        " В скрипте содержатся команды в таком же виде," +
                        " в котором их вводит пользователь в интерактивном режиме", "execute_script file_name : считать и исполнить скрипт из указанного файла." +
                        " В скрипте содержатся команды в таком же виде," +
                        " в котором их вводит пользователь в интерактивном режиме"},
                {"exit : завершить программу (без сохранения в файл)", "exit : завершить программу (без сохранения в файл)"},
                {"filter_contains_name name : вывести элементы," +
                        " значение поля name которых содержит заданную подстроку", "filter_contains_name name : вывести элементы," +
                        " значение поля name которых содержит заданную подстроку"},
                {"filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного", "filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного"},
                {"group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum," +
                        " вывести количество элементов в каждой группе", "group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum," +
                        " вывести количество элементов в каждой группе"},
                {"help : вывести справку по доступным командам", "help : вывести справку по доступным командам"},
                {"info : вывести в стандартный поток вывода информацию о коллекции", "info : вывести в стандартный поток вывода информацию о коллекции"},
                {"insert null {element} : добавить новый элемент с заданным ключом", "insert null {element} : добавить новый элемент с заданным ключом"},
                {"remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный", "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный"},
                {"remove_key null : удалить элемент из коллекции по его ключу", "remove_key null : удалить элемент из коллекции по его ключу"},
                {"remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный"},
                {"replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого", "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого"},
                {"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении", "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"},
                {"update id {element} : обновить значение элемента коллекции, id которого равен заданному", "update id {element} : обновить значение элемента коллекции, id которого равен заданному"}

        };

    }
}
