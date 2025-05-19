package common.utility;

import java.io.IOException;

public interface RequestResponse {
    byte[] serialize() throws IOException;
}
