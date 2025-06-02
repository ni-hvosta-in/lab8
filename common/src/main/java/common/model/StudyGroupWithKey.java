package common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StudyGroupWithKey implements Serializable{
    private final String key;
    private final StudyGroup studyGroup;
    private final String login;
    public StudyGroupWithKey(String key, StudyGroup studyGroup, String login) {
        this.studyGroup = studyGroup;
        this.key = key;
        this.login = login;
    }
    public StudyGroup getStudyGroup() {
        return studyGroup;
    }
    public String getKey() {
        return key;
    }
    public Long getId() {
        return this.studyGroup.getId();
    }
    public String getName() {
        return this.studyGroup.getName();
    }
    public Person getGroupAdmin() {
        return this.studyGroup.getGroupAdmin();
    }
    public SemesterEnum getSemesterEnum(){
        return this.studyGroup.getSemesterEnum();
    }

    public Coordinates getCoordinates() {
        return this.studyGroup.getCoordinates();
    }

    public LocalDateTime getCreationDate() {
        return this.studyGroup.getCreationDate();
    }

    public long getStudentsCount() {
        return this.studyGroup.getStudentsCount();
    }

    public FormOfEducation getFormOfEducation() {
        return this.studyGroup.getFormOfEducation();
    }

    public String getLogin() {
        return login;
    }
}
