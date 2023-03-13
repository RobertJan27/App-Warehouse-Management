package bll.validators;

import model.Client;

public class PhoneValidate<T> implements Validator<Client>{

    public boolean Validate(Client client) {
if(!client.getTelefon().matches("07[0-9]{8}"))
    return false;
else
    return true;
    }

}
