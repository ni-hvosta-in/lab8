package common.managers;

import common.utility.RequestResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RequestObj implements Serializable, RequestResponse {
    private final Object request;
    public RequestObj(Object request) {
        this.request = request;
    }
    public Object getRequest() {
        return request;
    }
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();
    }
}
