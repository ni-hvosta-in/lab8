package common.utility;

public enum RegistrationMessage {
    AUTHORIZATION_SUCCESS("Authorization Success"),
    REGISTRATION_SUCCESS("Registration Success"),
    WRONG_PASSWORD("wrong.password"),
    WRONG_LOGIN("wrong.login"),;
    private final String message;

    RegistrationMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
