package nihvostain.managers.gui.local;

import java.util.ListResourceBundle;

public class GuiLabels_el extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"button.auth", "Σύνδεση"},
                {"button.reg", "Εγγραφή"},
                {"label.login", "Όνομα χρήστη"},
                {"label.password", "Κωδικός"},
                {"server.timeOut", "ο διακομιστής δεν είναι προσωρινά διαθέσιμος"},
                {"wrong.login", "λάθος όνομα χρήστη"},
                {"wrong.password", "λάθος κωδικός"},
                {"data.error", "σφάλμα μεταφοράς δεδομένων, δοκιμάστε ξανά"},
                {"help", "βοήθεια"},
                {"exit", "έξοδος"},
                {"show", "εμφάνιση"},
                {"info", "πληροφορίες"},
                {"insert", "εισαγωγή"},
                {"update", "ενημέρωση"},
                {"remove_key", "διαγραφή_με_κλειδί"},
                {"clear", "εκκαθάριση"},
                {"execute_script", "εκτέλεση_σεναρίου"},
                {"remove_lower", "διαγραφή_χαμηλότερων"},
                {"replace_if_greater", "αντικατάσταση_αν_μεγαλύτερο"},
                {"remove_greater_key", "διαγραφή_μεγαλύτερων_κλειδιών"},
                {"group_counting_by_semester_enum", "ομαδοποίηση_κατά_εξάμηνο"},
                {"filter_contains_name", "φίλτρο_περιέχει_όνομα"},
                {"filter_greater_than_group_admin", "φίλτρο_μεγαλύτερο_από_τον_αρχηγό"},
                {"edit", "επεξεργασία"},
                {"delete", "διαγραφή"},
                {"visualize", "οπτικοποίηση"},
                {"OK", "OK"},
                {"key", "κλειδί"},
                {"id", "ταυτότητα"},
                {"name", "όνομα"},
                {"x", "x"},
                {"y", "y"},
                {"studentsCount", "αριθμός φοιτητών"},
                {"creationDate", "ημερομηνία δημιουργίας"},
                {"formOfEducation", "μορφή εκπαίδευσης"},
                {"semesterEnum", "εξάμηνο"},
                {"nameP", "όνομα αρχηγού"},
                {"birthday", "ημερομηνία γέννησης"},
                {"passportID", "διαβατήριο"},
                {"eyeColor", "χρώμα ματιών"},
                {"hairColor", "χρώμα μαλλιών"},
                {"scriptName", "όνομα σεναρίου"},
                {"nameSubstring", "υπο-αλφαριθμητικό ονόματος"},
                {"", ""},
                {"recursion", "υπέρβαση βάθους αναδρομής, ελέγξτε το σενάριο"},
                {"alien", "στοιχείο άλλου χρήστη"},
                {"wrong_name", "το όνομα δεν μπορεί να είναι κενό"},
                {"wrong_X", "το X πρέπει να είναι τύπου Integer"},
                {"wrong_Y", "το Y πρέπει να είναι τύπου Double"},
                {"wrong_student_count", "ο αριθμός φοιτητών πρέπει να είναι τύπου Long και > 0"},
                {"wrong_form_of_edu", "Αυτή η μορφή εκπαίδευσης δεν υπάρχει"},
                {"wrong_semester_enum", "Αυτό το εξάμηνο δεν υπάρχει"},
                {"wrong_name_P", "το όνομα δεν μπορεί να είναι κενό"},
                {"wrong_birthday", "ημερομηνία γέννησης σε μορφή 'dd-MM-yyyy'T'HH:mm:ssXXX'\nΠ.χ.: 25-07-2006T14:30:00+03:00"},
                {"wrong_passport_id", "Το διαβατήριο δεν υπάρχει ή δεν είναι δικό σας"},
                {"wrong_eye", "Το χρώμα ματιών δεν υπάρχει"},
                {"wrong_hair", "Το χρώμα μαλλιών δεν υπάρχει"},
                {"goodParam", "όλα καλά"},
                {"existingKey", "υπάρχει ήδη αυτό το κλειδί"},
                {"noKey", "δεν υπάρχει τέτοιο κλειδί"},
                {"noID", "δεν υπάρχει τέτοιο ID"},
                {"notLongID", "το ID πρέπει να είναι Long"},
                {"false", "κάτι πήγε πολύ στραβά"},
                {"fileNotFound", "δεν βρέθηκε αρχείο με αυτό το όνομα"},
                {"wrongParamInScript", "Λανθασμένες παράμετροι στο σενάριο"},
                {"wrongParam", "Λανθασμένες παράμετροι"},
                {"wrongCommand", "Μη αναγνωρισμένη εντολή στο σενάριο"},
                {"clear : очистить коллекцию", "clear : εκκαθάρισε τη συλλογή"},
                {"execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", "execute_script file_name : διάβασε και εκτέλεσε το σενάριο από το καθορισμένο αρχείο. Το σενάριο περιέχει εντολές με τον ίδιο τρόπο που τις εισάγει ο χρήστης σε διαδραστική λειτουργία"},
                {"exit : завершить программу (без сохранения в файл)", "exit : τερμάτισε το πρόγραμμα (χωρίς αποθήκευση σε αρχείο)"},
                {"filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку", "filter_contains_name name : εμφάνιση στοιχείων των οποίων το πεδίο name περιέχει το καθορισμένο υποσύνολο"},
                {"filter_greater_than_group_admin groupAdmin : вывести элементы, значение поля groupAdmin которых больше заданного", "filter_greater_than_group_admin groupAdmin : εμφάνιση στοιχείων των οποίων το πεδίο groupAdmin είναι μεγαλύτερο από το καθορισμένο"},
                {"group_counting_by_semester_enum : сгруппировать элементы коллекции по значению поля semesterEnum, вывести количество элементов в каждой группе", "group_counting_by_semester_enum : ομαδοποίηση στοιχείων της συλλογής βάσει της τιμής του πεδίου semesterEnum, εμφάνιση του αριθμού των στοιχείων σε κάθε ομάδα"},
                {"help : вывести справку по доступным командам", "help : εμφάνιση βοήθειας για τις διαθέσιμες εντολές"},
                {"info : вывести в стандартный поток вывода информацию о коллекции", "info : εμφάνιση πληροφοριών συλλογής στην τυπική έξοδο"},
                {"insert null {element} : добавить новый элемент с заданным ключом", "insert null {element} : προσθήκη νέου στοιχείου με το καθορισμένο κλειδί"},
                {"remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный", "remove_greater_key null : διαγραφή όλων των στοιχείων της συλλογής των οποίων το κλειδί είναι μεγαλύτερο από το καθορισμένο"},
                {"remove_key null : удалить элемент из коллекции по его ключу", "remove_key null : διαγραφή στοιχείου της συλλογής με το κλειδί του"},
                {"remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный", "remove_lower {element} : διαγραφή όλων των στοιχείων της συλλογής που είναι μικρότερα από το καθορισμένο"},
                {"replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого", "replace_if_greater null {element} : αντικατάσταση της τιμής με βάση το κλειδί, αν η νέα τιμή είναι μεγαλύτερη από την παλιά"},
                {"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении", "show : εμφάνιση όλων των στοιχείων της συλλογής στην τυπική έξοδο σε μορφή συμβολοσειράς"},
                {"update id {element} : обновить значение элемента коллекции, id которого равен заданному", "update id {element} : ενημέρωση της τιμής του στοιχείου συλλογής, του οποίου το id είναι ίσο με το καθορισμένο"}

        };
    }
}
