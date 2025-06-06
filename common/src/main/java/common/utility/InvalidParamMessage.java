package common.utility;

import java.io.Serializable;

/**
 * Перечисление ошибок в параметре
 */
public enum InvalidParamMessage implements Serializable {
    TRUE("goodParam"),
    ExistingKey("existingKey"),
    NoKey("noKey"),
    NoID("noID"),
    NotLongID("notLongID"),
    FALSE("false"),
    FILENOTFOUND("fileNotFound");

    private final String message;

    InvalidParamMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}
