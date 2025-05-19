package common.utility;

import java.io.Serializable;

/**
 * Перечисление ошибок в параметре
 */
public enum InvalidParamMessage implements Serializable {
    TRUE("все гуд"),
    ExistingKey("такой ключ уже есть"),
    NoKey("нет такого ключа"),
    NoID("нет такого id"),
    NotLongID("id должен быть Long"),
    FALSE("все очень плохо");


    private final String message;

    InvalidParamMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}
