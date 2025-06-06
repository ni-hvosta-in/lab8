package nihvostain.managers.gui.local;

import java.util.ListResourceBundle;

public class GuiLabels_sl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"button.auth", "Avtentikacija"},
                {"button.reg", "Registracija"},
                {"label.login", "Uporabniško ime"},
                {"label.password", "Geslo"},
                {"server.timeOut", "strežnik trenutno ni na voljo"},
                {"wrong.login", "napačno uporabniško ime"},
                {"wrong.password", "napačno geslo"},
                {"data.error", "napaka pri prenosu podatkov, poskusite znova"},
                {"help", "pomoč"},
                {"exit", "izhod"},
                {"show", "prikaži"},
                {"info", "informacije"},
                {"insert", "vnos"},
                {"update", "posodobi"},
                {"remove_key", "odstrani_po_ključi"},
                {"clear", "počisti"},
                {"execute_script", "izvedi_skript"},
                {"remove_lower", "odstrani_manjše"},
                {"replace_if_greater", "zamenjaj_če_je_večje"},
                {"remove_greater_key", "odstrani_večje_ključe"},
                {"group_counting_by_semester_enum", "skupinsko_po_semestru"},
                {"filter_contains_name", "filter_vsebuje_ime"},
                {"filter_greater_than_group_admin", "filter_večji_od_skupinskega_admina"},
                {"edit", "uredi"},
                {"delete", "izbriši"},
                {"visualize", "vizualizacija"}
        };
    }
}
