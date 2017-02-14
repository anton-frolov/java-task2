package rt.task2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import rt.task2.data.domain.User;

import java.sql.Types;

public class UserDaoImpl extends AbstractDaoImpl<User, Long> {

    private class PersistUser extends User {

	public void setId(Long id) {
	    super.setId(id);
	}

    }

    public UserDaoImpl(Connection connection) {
	super(connection);
    }

    @Override
    public String getSelectQuery() {
	return "SELECT * FROM public.\"user\"";
    }

    @Override
    public String getCreateQuery() {
	return "INSERT INTO public.\"user\"(login, passwd, person_id, email) VALUES(?, ?, ?, ?) RETURNING id;";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws PersistException {
	try {
	    statement.setString(1, user.getUserId());
	    statement.setString(2, user.getPassword());
	    if (user.getPersonId() != null) {
		statement.setLong(3, user.getPersonId());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	    if (user.getEmail() != null) {
		statement.setString(4, user.getEmail());
	    } else {
		statement.setNull(4, Types.NULL);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }

    @Override
    public String getUpdateQuery() {
	return "UPDATE public.\"user\" SET login = ?, passwd = ?, person_id = ?, email = ? WHERE id = ?;";
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws PersistException {
	try {
	    statement.setString(1, user.getUserId());
	    statement.setString(2, user.getPassword());
	    if (user.getPersonId() != null) {
		statement.setLong(3, user.getPersonId());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	    if (user.getEmail() != null) {
		statement.setString(4, user.getEmail());
	    } else {
		statement.setNull(4, Types.NULL);
	    }
	    statement.setLong(5, user.getId());
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }

    @Override
    public String getDeleteQuery() {
	return "DELETE FROM public.\"user\" WHERE id = ?;";
    }

    @Override
    public User create() throws PersistException {
	User user = new User();
	return persist(user);
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
	LinkedList<User> result = new LinkedList<User>();
	try {
	    while (resultSet.next()) {
		PersistUser user = new PersistUser();
		user.setId(resultSet.getLong("id"));
		user.setUserId(resultSet.getString("login"));
		user.setPassword(resultSet.getString("passwd"));
		user.setEmail(resultSet.getString("email"));
		result.add(user);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	return result;

    }
}