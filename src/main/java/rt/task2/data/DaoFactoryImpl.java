package rt.task2.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;
import rt.task2.data.domain.User;

public class DaoFactoryImpl implements DaoFactory<Connection> {
	
	private String user = "rttest";
    private String password = "rttest";
    private String url = "jdbc:postgresql://localhost:5433/rttest";
    private String driver = "org.postgresql.Driver";
    private Map<Class, DaoCreator> creators;
    

	public DaoFactoryImpl() {
        
		try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		creators = new HashMap<Class, DaoCreator>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new UserDaoImpl(connection);
            }
        });
        
        creators.put(Person.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PersonDaoImpl(connection);
            }
        });
        
       creators.put(Ticket.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new TicketDaoImpl(connection);
            }
        });
	}

	

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }


}
