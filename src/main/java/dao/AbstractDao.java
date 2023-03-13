package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;
import model.Order;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDao<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String SelectQuery(String field) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(" * ");
        query.append(" FROM ");
        query.append(" `");
        query.append(type.getSimpleName());
        query.append("`");
        query.append(" WHERE " + field + " =?");
        return query.toString();
    }

    private String insertQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("Insert into ");
        query.append("`");
        query.append(type.getSimpleName());
        query.append("`");
        query.append(" values (");
        for(int i=0;i<type.getDeclaredFields().length;i++)
        {if(i!=(type.getDeclaredFields().length-1))
            query.append("?,");
        else
            query.append("?)");
        }
        System.out.println(query.toString());
        return query.toString();
    }
    private String updateQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("Update ");
        query.append("`");
        query.append(type.getSimpleName());
        query.append("`");
        query.append(" set ");
        int i=0;
        for (Field field : type.getDeclaredFields())
        {
            if(i!=0)
            {
                if (i != (type.getDeclaredFields().length - 1))
                    query.append(field.getName() + "=?,");
                else
                    query.append(field.getName() + "=? ");
            }
            i++;
        }
        query.append(" where id=?");
        return query.toString();
    }


    private String DeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM ");
        sb.append("`");
        sb.append(type.getSimpleName());
        sb.append("`");
        sb.append(" WHERE ");
        sb.append(field + "=? ");
        return sb.toString();
    }




    public List<T> findAll() {
        StringBuilder query = new StringBuilder();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        query.append("SELECT ");
        query.append(" * ");
        query.append(" FROM ");
        query.append(" `");
        query.append(type.getSimpleName());
        query.append("`");
        System.out.println(query.toString());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            resultSet = statement.executeQuery();

            return  createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = SelectQuery("id");
        String query1 = SelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(!createObjects(resultSet).isEmpty())
            {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query1);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
    return createObjects(resultSet).get(0);
}
else
            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next())
            {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                        Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = insertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i=1;

            for(Field f : t.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    statement.setObject(i, f.get(t));
                i++;
            }
            statement.executeUpdate();

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = updateQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i=1;
            int k=0;
            for(Field f : t.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (i == 1 && k==0)
                {
                    statement.setObject(t.getClass().getDeclaredFields().length, f.get(t));
                i--;
                k=1;
                }
                else {
                    statement.setObject(i, f.get(t));
                }
            i++;
            }
             statement.executeUpdate();


            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = DeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field f= t.getClass().getDeclaredFields()[0];
            f.setAccessible(true);
            statement.setObject(1,f.get(t));
             statement.executeUpdate();

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


























}
