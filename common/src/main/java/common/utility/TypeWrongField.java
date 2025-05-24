package common.utility;

public enum TypeWrongField {
    WRONG_NAME("имя не может быть пустым"),
    WRONG_X("X должен быть типа Integer"),
    WRONG_Y("Y должен быть типа Double"),
    WRONG_STUDENTS_COUNT("кол-во студентов должно быть типа Long и больше нуля"),
    WRONG_FORM_OF_EDU("Такой формы обучения нет в списке"),
    WRONG_SEMESTER_ENUM("Такого семестра нет в списке"),
    WRONG_NAME_P("имя не может быть пустым"),
    WRONG_BIRTHDAY("день рождения должен быть в формате 'dd-MM-yyyy'T'HH:mm:ssXXX'\\n\" +\n" +
            "                        \"Пример: 25-07-2006T14:30:00+03:00"),
    WRONG_PASSPORT_ID("Такого паспорта не существует или он не ваш"),
    WRONG_EYE("Такого цвета в списке нет"),
    WRONG_HAIR("Такого цвета в списке нет");
    private final String message;

    TypeWrongField(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
