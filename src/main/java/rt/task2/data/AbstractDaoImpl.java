package rt.task2.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import rt.task2.data.domain.AbstractEntity;

public abstract class AbstractDaoImpl<T extends AbstractEntity<PK>, PK extends Serializable>
	implements GenericDao<T, PK> {

    protected Connection connection;

    public AbstractDaoImpl(Connection connection) {
	this.connection = connection;
    }

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws PersistException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;

    @Override
    public T persist(T object) throws PersistException {
	T persistInstance;
	Long retId;
	String sql = getCreateQuery();
	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    prepareStatementForInsert(statement, object);
	    statement.execute();
	    ResultSet resultSet = statement.getResultSet();
	    if (resultSet.next()) {
		retId = resultSet.getLong(1);
	    } else {
		throw new PersistException("Error on new persist data.");
	    }
	    resultSet.close();
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}

	sql = getSelectQuery() + " WHERE id = ?;";
	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    try {
		statement.setObject(1, retId);
	    } catch (Exception e) {
		throw new PersistException(e);
	    }
	    ResultSet resultSet = statement.executeQuery();
	    List<T> list = parseResultSet(resultSet);
	    if ((list == null) || (list.size() != 1)) {
		throw new PersistException("Exception on findByPK new persist data.");
	    }
	    persistInstance = list.iterator().next();
	    resultSet.close();
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	return persistInstance;
    }

    @Override
    public void update(T object) throws PersistException {
	String sql = getUpdateQuery();
	try (PreparedStatement statement = connection.prepareStatement(sql);) {
	    prepareStatementForUpdate(statement, object);
	    int count = statement.executeUpdate();
	    if (count != 1) {
		throw new PersistException("On update modify more then 1 record: " + count);
	    }
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }

    @Override
    public void delete(T object) throws PersistException {
	String sql = getDeleteQuery();
	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    try {
		statement.setObject(1, object.getId());
	    } catch (Exception e) {
		throw new PersistException(e);
	    }
	    int count = statement.executeUpdate();
	    if (count != 1) {
		throw new PersistException("On delete modify more then 1 record: " + count);
	    }
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}

    }

    @Override
    public T getById(Long id) throws PersistException {
	List<T> list;
	String sql = getSelectQuery();
	sql += " WHERE id = ?";
	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    list = parseResultSet(resultSet);
	    resultSet.close();
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	if (list == null || list.size() == 0) {
	    return null;
	}
	if (list.size() > 1) {
	    throw new PersistException("Received more than one record.");
	}
	return list.iterator().next();
    }

    @Override
    public List<T> getAll() throws PersistException {
	List<T> list;
	String sql = getSelectQuery();
	try (PreparedStatement statement = connection.prepareStatement(sql)) {
	    ResultSet resultSet = statement.executeQuery();
	    list = parseResultSet(resultSet);
	    resultSet.close();
	    statement.close();
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	return list;
    }

    @Override
    public List<T> query(String query, Object[] params) throws PersistException {

	if (params == null)
	    throw new PersistException("Query params should not be null");

	List<T> list = null;
	try (PreparedStatement statement = connection.prepareStatement(query)) {
	    for (int i = 0; i < params.length; i++) {
		statement.setObject(i + 1, params[i]);
	    }
	    ResultSet resultSet = statement.executeQuery();
	    list = parseResultSet(resultSet);
	    statement.clearParameters();
	    resultSet.close();
	    statement.close();
	} catch (Exception ex) {
	    throw new PersistException(ex.getMessage());
	}
	return list;
    }

}
