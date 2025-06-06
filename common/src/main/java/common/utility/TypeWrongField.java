package common.utility;

public enum TypeWrongField {
    WRONG_NAME("wrong_name"),
    WRONG_X("wrong_X"),
    WRONG_Y("wrong_Y"),
    WRONG_STUDENTS_COUNT("wrong_student_count"),
    WRONG_FORM_OF_EDU("wrong_form_of_edu"),
    WRONG_SEMESTER_ENUM("wrong_semester_enum"),
    WRONG_NAME_P("wrong_name_P"),
    WRONG_BIRTHDAY("wrong_birthday"),
    WRONG_PASSPORT_ID("wrong_passport_id"),
    WRONG_EYE("wrong_eye"),
    WRONG_HAIR("wrong_hair");
    private final String message;

    TypeWrongField(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
