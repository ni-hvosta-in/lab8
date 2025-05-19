package common.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Map;

/**
 * Класс обертка для учебной группы
 */
public class StudyGroups {

    @JacksonXmlProperty(localName = "studyGroups")
    private Map<String,StudyGroup> studyGroupList;

    /**
     * Конструктор
     * @param studyGroupList массив учебных групп
     */
    public StudyGroups(Map<String, StudyGroup> studyGroupList) {
        this.studyGroupList = studyGroupList;
    }

    /**
     * Конструктор по умолчанию для сериализации
     */
    public StudyGroups(){}

    /**
     * Возвращает массив учебных групп
     * @return массив учебных групп
     */
    public Map<String, StudyGroup> getStudyGroupList() {
        return studyGroupList;
    }
}
