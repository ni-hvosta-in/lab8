package common.managers;

import common.utility.InvalidParamMessage;
import common.utility.RequestResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ResponseParam implements Serializable, RequestResponse {

    private InvalidParamMessage param;

    public ResponseParam(InvalidParamMessage param) {
        this.param = param;
    }

    public byte[] serialize() throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(this); //Сериализация
        objectStream.flush();
        return byteStream.toByteArray();

    }

    public InvalidParamMessage getParam() {
        return param;
    }
}
