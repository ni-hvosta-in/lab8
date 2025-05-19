package common.managers;

import common.utility.InvalidParamMessage;
import common.utility.RegistrationMessage;
import common.utility.RequestResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ResponseRegistry implements Serializable, RequestResponse {

    private RegistrationMessage message;

    public ResponseRegistry(RegistrationMessage message) {
        this.message = message;
    }

    public byte[] serialize() throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();

    }

    public RegistrationMessage getMessage() {
        return message;
    }
}
