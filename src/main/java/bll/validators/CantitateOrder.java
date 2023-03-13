package bll.validators;

import model.Product;
import model.Order;

public class CantitateOrder implements Validator<Order> {
    public boolean Validate(Order order) {
        String c=Integer.toString(order.getCantitate());
        if(!c.matches("^[0-9]+$"))
            return false;
        else
            return true;
    }
}
