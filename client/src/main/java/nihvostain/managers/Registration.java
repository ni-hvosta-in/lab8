package nihvostain.managers;

import common.managers.Deserialize;
import common.managers.Request;
import common.managers.ResponseRegistry;
import common.utility.RegistrationMessage;
import common.utility.TypeAuthentication;
import common.utility.TypeRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class Registration {
    private final String login;
    private final String password;
    private final Communication communication;
    private final TypeAuthentication action;
    public Registration(String login, String password, Communication communication, TypeAuthentication action) {
        this.login = login;
        this.password = password;
        this.communication = communication;
        this.action = action;
    }

    public RegistrationMessage register() throws IOException, TimeoutException, ClassNotFoundException {
        ArrayList<String> user = new ArrayList<>();
        user.add(login);
        user.add(password);
        Request request;
        if (action == TypeAuthentication.REGISTRATION) {
            request = new Request(TypeRequest.REQUEST_REGISTRATION, user);
        } else {
            request = new Request(TypeRequest.REQUEST_AUTHORIZATION, user);
        }
        request.addUser(login, password);
        communication.send(request.serialize());

        byte[] response = communication.receive();
        return new Deserialize<ResponseRegistry>(response).deserialize().getMessage();
    }
}
