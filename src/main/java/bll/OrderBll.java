package bll;

import bll.validators.*;

import dao.OrderDao;
import model.Client;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBll {
    private List<Validator<Order>> validators;
    private OrderDao orderDao;

    public OrderBll(){
        validators = new ArrayList<Validator<Order>>();
        validators.add(new DataValidate());
        validators.add(new CantitateOrder());
        orderDao=new OrderDao();
    }

    public Order findOrderbyid(int id)
    {
        Order c= (Order) orderDao.findById(id);
        if(c==null)
            throw new NoSuchElementException("The Order with id =" + id + " was not found!");
        return c;
    }
    public List<Order> findOrders()
    {
        List<Order> c=  orderDao.findAll();
        if(c.isEmpty())
            throw new NoSuchElementException("The Order IS EMPTY");
        return c;
    }


    public boolean Insert (Order c)
    {
        for(Validator v:validators)
        {
            if(v.Validate(c)==false)
                return false;
        }
        Order s=orderDao.insert(c);
        return true;
    }

    public boolean Update(Order c)
    {
        for(Validator v:validators)
        {
            if(v.Validate(c)==false)
                return false;
        }
        Order s=orderDao.update(c);
        return true;
    }

    public boolean Delete (Order c)
    {

        Order s=orderDao.delete(c);
        return true;
    }
}
