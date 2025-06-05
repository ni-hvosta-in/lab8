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
                {"data.error", "ошибка передачи данных, попробуйте снова"}
        };

    }
}
