package bll;

import bll.validators.NameValidate;
import bll.validators.PhoneValidate;
import bll.validators.Validator;
import dao.ClientDao;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Clientbll {
    private List<Validator<Client>> validators;
    private ClientDao clientDao;


    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Clientbll(){
    validators = new ArrayList<Validator<Client>>();
    validators.add(new PhoneValidate());

    clientDao=new ClientDao();
    }

public Client findClientbyid(int id)
{
Client c= (Client) clientDao.findById(id);
if(c==null)
    throw new NoSuchElementException("The client with id =" + id + " was not found!");
return c;
}
    public List<Client> findClients()
    {
        List<Client> c=  clientDao.findAll();
        if(c.isEmpty())
            throw new NoSuchElementException("The client IS EMPTY");
        return c;
    }


public boolean Insert (Client c)
{
    for(Validator v:validators)
    {
        if(v.Validate(c)==false)
            return false;
    }
    Client s=clientDao.insert(c);
return true;
}

    public boolean Update(Client c)
    {
        for(Validator v:validators)
        {
            if(v.Validate(c)==false)
                return false;
        }
        Client s=clientDao.update(c);
        return true;
    }

    public boolean Delete (Client c)
    {

        Client s=clientDao.delete(c);
        return true;
    }



}
