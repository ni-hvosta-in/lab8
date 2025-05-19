package common.utility;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Communicative {
    void send(byte [] message) throws IOException;
    byte [] receive() throws IOException, TimeoutException;
}
