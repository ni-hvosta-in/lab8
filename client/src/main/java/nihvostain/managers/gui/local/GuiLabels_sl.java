package nihvostain.managers.gui.local;

import java.util.ListResourceBundle;

public class GuiLabels_sl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"button.auth", "Prijava"},
                {"button.reg", "Registracija"},
                {"label.login", "Uporabniško ime"},
                {"label.password", "Geslo"},
                {"server.timeOut", "strežnik trenutno ni na voljo"},
                {"wrong.login", "nepravilno uporabniško ime"},
                {"wrong.password", "nepravilno geslo"},
                {"data.error", "napaka pri prenosu podatkov, poskusite znova"}
        };
    }
}