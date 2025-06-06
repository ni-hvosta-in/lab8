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
                {"visualize", "визуализация"}
        };

    }
}
