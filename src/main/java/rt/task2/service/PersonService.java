package rt.task2.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.spi.TransactionalWriter;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.Person;

public class PersonService {

	public List<Person> getPersons() throws PersistException, SQLException {

		DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
		Connection connection = factory.getContext();
		List<Person> list = null;
		try {
			connection.setAutoCommit(true);
			@SuppressWarnings("unchecked")
			GenericDao<Person, Long> dao = factory.getDao(connection, Person.class);
			list = dao.getAll();
		} catch (SQLException | PersistException e) {
			throw e;
		} finally {
			connection.close();
		}

		return list;
	}
}
