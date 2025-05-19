package common.exceptions;

/**
 * Существование такого же ключа
 */
public class ExistingKeyException extends Exception{

    @Override
    public String getMessage() {
        return "Такой ключ уже существует, придумайте другой";
    }

}
