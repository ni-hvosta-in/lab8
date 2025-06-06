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
                {"server.timeOut", "Strežnik trenutno ni na voljo"},
                {"wrong.login", "Napačno uporabniško ime"},
                {"wrong.password", "Napačno geslo"},
                {"data.error", "Napaka pri prenosu podatkov, poskusite znova"},
                {"help", "Pomoč"},
                {"exit", "Izhod"},
                {"show", "Pokaži"},
                {"info", "Informacije"},
                {"insert", "Vnos"},
                {"update", "Posodobi"},
                {"remove_key", "Odstrani_po_ključi"},
                {"clear", "Počisti"},
                {"execute_script", "Zaženi_skript"},
                {"remove_lower", "Odstrani_nižjega"},
                {"replace_if_greater", "Zamenjaj_če_večje"},
                {"remove_greater_key", "Odstrani_večje_ključe"},
                {"group_counting_by_semester_enum", "Združi_po_semestru"},
                {"filter_contains_name", "Filter_vsebuje_ime"},
                {"filter_greater_than_group_admin", "Filter_več_kot_skupinski_admin"},
                {"edit", "Uredi"},
                {"delete", "Izbriši"},
                {"visualize", "Vizualiziraj"},
                {"OK", "V redu"},
                {"key", "Ključ"},
                {"id", "ID"},
                {"name", "Ime"},
                {"x", "x"},
                {"y", "y"},
                {"studentsCount", "Število študentov"},
                {"creationDate", "Datum ustvarjanja"},
                {"formOfEducation", "Oblika izobraževanja"},
                {"semesterEnum", "Semester"},
                {"nameP", "Ime starešine"},
                {"birthday", "Rojstni dan"},
                {"passportID", "Potni list"},
                {"eyeColor", "Barva oči"},
                {"hairColor", "Barva las"},
                {"scriptName", "Ime skripte"},
                {"nameSubstring", "Podniz imena"},
                {"", ""},
                {"recursion", "Presežena globina rekurzije, preverite skripto"},
                {"alien", "Element drugega uporabnika"},
                {"wrong_name", "Ime ne sme biti prazno"},
                {"wrong_X", "X mora biti tipa Integer"},
                {"wrong_Y", "Y mora biti tipa Double"},
                {"wrong_student_count", "Število študentov mora biti tipa Long in večje od nič"},
                {"wrong_form_of_edu", "Taka oblika izobraževanja ne obstaja"},
                {"wrong_semester_enum", "Tega semestra ni na seznamu"},
                {"wrong_name_P", "Ime ne sme biti prazno"},
                {"wrong_birthday", "Rojstni dan mora biti v formatu 'dd-MM-yyyy'T'HH:mm:ssXXX'\nPrimer: 25-07-2006T14:30:00+03:00"},
                {"wrong_passport_id", "Takšen potni list ne obstaja ali ni vaš"},
                {"wrong_eye", "Taka barva oči ni na seznamu"},
                {"wrong_hair", "Taka barva las ni na seznamu"},
                {"goodParam", "vse v redu"},
                {"existingKey", "tak ključ že obstaja"},
                {"noKey", "takšnega ključa ni"},
                {"noID", "takšnega ID-ja ni"},
                {"notLongID", "ID mora biti tipa Long"},
                {"false", "vse je zelo slabo"},
                {"fileNotFound", "Datoteke s takim imenom ni"},
                {"wrongParamInScript", "Napačni parametri za ukaz v skriptu"},
                {"wrongParam", "Napačni parametri za ukaz"},
                {"wrongCommand", "Neznan ukaz v skriptu"},
                {"clear : очистить коллекцию", "clear : počisti zbirko"},
                {"execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", "execute_script file_name : preberi in izvedi skripto iz navedene datoteke. Skripta vsebuje ukaze v enaki obliki, kot jih uporabnik vnese v interaktivnem načinu"},
                {"exit : завершить программу (без сохранения в файл)", "exit : zapri program (brez shranjevanja v datoteko)"},
                {"filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку", "filter_contains_name name : prikaži elemente, katerih polje name vsebuje določeno podniz"},
                {"filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного", "filter_greater_than_group_admin groupAdmin : prikaži elemente, katerih polje groupAdmin je večje od določenega"},
                {"group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum, вывести количество элементов в каждой группе", "group_counting_by_semester_enum : združi elemente zbirke po vrednosti polja semesterEnum, prikaži število elementov v vsaki skupini"},
                {"help : вывести справку по доступным командам", "help : prikaži pomoč za razpoložljive ukaze"},
                {"info : вывести в стандартный поток вывода информацию о коллекции", "info : prikaži informacije o zbirki v standardni izhodni tok"},
                {"insert null {element} : добавить новый элемент с заданным ключом", "insert null {element} : dodaj nov element z določenim ključem"},
                {"remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный", "remove_greater_key null : odstrani vse elemente iz zbirke, katerih ključ je večji od določenega"},
                {"remove_key null : удалить элемент из коллекции по его ключу", "remove_key null : odstrani element iz zbirke po njegovem ključu"},
                {"remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", "remove_lower {element} : odstrani vse elemente iz zbirke, ki so manjši od določenega"},
                {"replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого", "replace_if_greater null {element} : zamenjaj vrednost po ključu, če je nova vrednost večja od stare"},
                {"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении", "show : prikaži vse elemente zbirke v obliki niza v standardni izhodni tok"},
                {"update id {element} : обновить значение элемента коллекции, id которого равен заданному", "update id {element} : posodobi vrednost elementa zbirke, katerega id je enak določenemu"}

        };
    }
}
