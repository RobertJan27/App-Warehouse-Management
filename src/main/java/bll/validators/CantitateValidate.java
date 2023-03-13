package bll.validators;

import model.Client;
import model.Product;

public class CantitateValidate implements Validator<Product> {
    public boolean Validate(Product product) {
        String c=Integer.toString(product.getCantitate());
        if(!c.matches("^[0-9]+$"))
            return false;
        else
            return true;
    }
}
