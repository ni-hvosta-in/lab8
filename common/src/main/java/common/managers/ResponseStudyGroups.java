package common.managers;

import common.model.StudyGroup;
import common.model.StudyGroupWithKey;
import common.utility.RequestResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseStudyGroups implements Serializable, RequestResponse {

    private final ArrayList<StudyGroupWithKey> studyGroups;

    public ResponseStudyGroups(ArrayList<StudyGroupWithKey> studyGroups) {
        this.studyGroups = studyGroups;
    }

    @Override
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();
    }

    public ArrayList<StudyGroupWithKey> getStudyGroups() {
        return studyGroups;
    }
}
