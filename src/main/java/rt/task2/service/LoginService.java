package rt.task2.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.User;

public class LoginService {

	public boolean authenticateUser(String userId, String password) throws PersistException, SQLException {

		User user = getUserByUserId(userId);
		if (user != null && user.getUserId().equals(userId) && user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public User getUserByUserId(String userId) throws PersistException, SQLException {

		DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
		Connection connection = factory.getContext();
		List<User> list = null;
		try {
			connection.setAutoCommit(true);
			@SuppressWarnings("unchecked")
			GenericDao<User, Long> dao = factory.getDao(connection, User.class);
			String sql = dao.getSelectQuery() + " WHERE login=?;";
			Object[] params = new Object[1];
			params[0] = userId;
			list = dao.query(sql, params);
		} catch (SQLException | PersistException e) {
			throw e;
		} finally {
			connection.close();
		}

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public User registrationUser(String userId, String password, String userEmail)
			throws PersistException, SQLException {

		return registrationUser(userId, password, userEmail, true);
	}

	public User registrationUser(String userId, String password, String userEmail, boolean autoCommit)
			throws PersistException, SQLException {

		DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();
		Connection connection = factory.getContext();
		User user = null;
		try {
			connection.setAutoCommit(autoCommit);
			@SuppressWarnings("unchecked")
			GenericDao<User, Long> dao = factory.getDao(connection, User.class);
			user = dao.create();
			user.setUserId(userId);
			user.setPassword(password);
			user.setEmail(userEmail);
			dao.update(user);
		} catch (SQLException | PersistException e) {
			throw e;
		} finally {
			connection.close();
		}
		return user;
	}
}
