package common.managers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize <T>{

    private byte[] bytes;
    public Deserialize(byte[] bytes){
        this.bytes = bytes;
    }

    public T deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        return (T) objectStream.readObject();
    }
}
