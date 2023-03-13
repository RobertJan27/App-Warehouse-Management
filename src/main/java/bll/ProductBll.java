package bll;

import bll.validators.CantitateOrder;
import bll.validators.CantitateValidate;
import bll.validators.DataValidate;
import bll.validators.Validator;
import dao.OrderDao;
import dao.ProductDao;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBll {
    private List<Validator<Product>> validators;
    private ProductDao productDao;

    public ProductBll(){
        validators = new ArrayList<Validator<Product>>();

        validators.add(new CantitateValidate());
        productDao=new ProductDao();
    }

    public Product findProductbyid(int id)
    {
        Product c= (Product) productDao.findById(id);
        if(c==null)
            throw new NoSuchElementException("The Product with id =" + id + " was not found!");
        return c;
    }
    public List<Product> findProduct()
    {
        List<Product> c=  productDao.findAll();
        if(c.isEmpty())
            throw new NoSuchElementException("The Product IS EMPTY");
        return c;
    }


    public boolean Insert (Product c)
    {
        for(Validator v:validators)
        {
            if(v.Validate(c)==false)
                return false;
        }
        Product s=productDao.insert(c);
        return true;
    }

    public boolean Update(Product c)
    {
        for(Validator v:validators)
        {
            if(v.Validate(c)==false)
                return false;
        }
        Product s=productDao.update(c);
        return true;
    }

    public boolean Delete (Product c)
    {

        Product s=productDao.delete(c);
        return true;
    }
}
