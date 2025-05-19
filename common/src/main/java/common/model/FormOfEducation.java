package common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Перечисление всех форм обучения
 */
public enum FormOfEducation {
    DISTANCE_EDUCATION("дистанционное обучение"),
    FULL_TIME_EDUCATION("обычное обучение"),
    EVENING_CLASSES("вечерние занятия");

    private final String form;

    FormOfEducation (String form){
        this.form = form;
    }

    /**
     * @return название цвета
     */
    public String getForm(){
        return this.form;
    }

    /**
     * @return словарь название формы + элемент из enum'а
     */
    static public Map<String, FormOfEducation> getFormOfEducation(){

        Map <String, FormOfEducation> formOfEducationMap = new HashMap<>();

        formOfEducationMap.put(DISTANCE_EDUCATION.form, DISTANCE_EDUCATION);
        formOfEducationMap.put(FULL_TIME_EDUCATION.form, FULL_TIME_EDUCATION);
        formOfEducationMap.put(EVENING_CLASSES.form, EVENING_CLASSES);

        return formOfEducationMap;
    }

    static public Map<String, FormOfEducation> getFormOfEducationFromDB(){
        Map <String, FormOfEducation> formOfEducationMap = new HashMap<>();

        formOfEducationMap.put(DISTANCE_EDUCATION.toString(), DISTANCE_EDUCATION);
        formOfEducationMap.put(FULL_TIME_EDUCATION.toString(), FULL_TIME_EDUCATION);
        formOfEducationMap.put(EVENING_CLASSES.toString(), EVENING_CLASSES);

        return formOfEducationMap;
    }

}
