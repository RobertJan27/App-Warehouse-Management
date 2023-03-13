package bll.validators;

import model.Client;

public class NameValidate implements Validator<Client> {
    public boolean Validate(Client client) {
        if(!client.getNume().matches("/^[A-Za-z]+([\\ A-Za-z]+)*/"))
            return false;
        else
            return true;
    }
}
