package bll.validators;

import model.Order;

public class DataValidate implements Validator<Order>{
    public boolean Validate(Order order) {

        if(!order.getData().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
        return false;
        else
            return true;
    }

}
