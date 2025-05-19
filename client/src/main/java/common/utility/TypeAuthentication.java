package common.utility;

public enum TypeAuthentication {
    REGISTRATION("регистрация"),
    AUTHORIZATION("авторизация");

    private final String message;

    TypeAuthentication(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}
