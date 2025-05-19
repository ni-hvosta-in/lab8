package common.managers;

import common.model.StudyGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ReqStudyGroup implements Serializable {
    private final StudyGroup studyGroup;
    public ReqStudyGroup(StudyGroup studyGroup)
    {
        this.studyGroup = studyGroup;
    }
    public Object getStudyGroup() {
        return studyGroup;
    }
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();
    }
}
