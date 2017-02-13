package rt.task2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.Person;

public class PersonService {
	
private static DaoFactory<Connection> factory;
	
	
	protected static DaoFactory<Connection> getFactory(){
		
		if(factory==null){
			factory = new DaoFactoryImpl();
		}
		return factory;
		
	}
	
	public List<Person> getPersons() throws PersistException, SQLException{
		
		Connection connection = getFactory().getContext(); 
		connection.setAutoCommit(true);
	    @SuppressWarnings("unchecked")
		GenericDao<Person, Long> dao = factory.getDao(connection, Person.class);

	    List<Person> list = dao.getAll();
       	return list;
	}

}
