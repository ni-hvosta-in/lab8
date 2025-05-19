package common.exceptions;

public class OtherUsersObject extends Exception {
    @Override
    public String getMessage() {
        return "Это объект другого пользователя";
    }
}
