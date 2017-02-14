package rt.task2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import rt.task2.data.domain.Person;

public class PersonDaoImpl extends AbstractDaoImpl<Person, Long> {

    private class PersistPerson extends Person {

	public void setId(Long id) {
	    super.setId(id);
	}
    }

    public PersonDaoImpl(Connection connection) {
	super(connection);
    }

    @Override
    public Person create() throws PersistException {
	Person person = new Person();
	return persist(person);
    }

    @Override
    public String getSelectQuery() {
	return "SELECT * FROM public.person";
    }

    @Override
    public String getCreateQuery() {
	return "INSERT INTO public.person(first_name, middle_name, last_name, email) VALUES(?, ?, ?, ?) RETURNING id;";
    }

    @Override
    public String getUpdateQuery() {
	return "UPDATE public.person SET first_name = ?, middle_name = ?, last_name = ?, email = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
	return "DELETE FROM public.person WHERE id = ?;";
    }

    @Override
    protected List<Person> parseResultSet(ResultSet resultSet) throws PersistException {
	LinkedList<Person> result = new LinkedList<Person>();
	try {
	    while (resultSet.next()) {
		PersistPerson person = new PersistPerson();
		person.setId(resultSet.getLong("id"));
		person.setFirstName(resultSet.getString("first_name"));
		person.setMiddleName(resultSet.getString("middle_name"));
		person.setLastName(resultSet.getString("last_name"));
		person.setEmail(resultSet.getString("email"));
		result.add(person);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
	return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Person person) throws PersistException {

	try {
	    if (person.getFirstName() != null) {
		statement.setString(1, person.getFirstName());
	    } else {
		statement.setNull(1, Types.NULL);
	    }
	    if (person.getMiddleName() != null) {
		statement.setString(2, person.getMiddleName());
	    } else {
		statement.setNull(2, Types.NULL);
	    }
	    if (person.getLastName() != null) {
		statement.setString(3, person.getLastName());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	    if (person.getEmail() != null) {
		statement.setString(4, person.getEmail());
	    } else {
		statement.setNull(4, Types.NULL);
	    }
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Person person) throws PersistException {
	try {
	    statement.setString(1, person.getFirstName());
	    statement.setString(2, person.getMiddleName());
	    if (person.getLastName() != null) {
		statement.setString(3, person.getLastName());
	    } else {
		statement.setNull(3, Types.NULL);
	    }
	    if (person.getEmail() != null) {
		statement.setString(4, person.getEmail());
	    } else {
		statement.setNull(4, Types.NULL);
	    }
	    statement.setLong(5, person.getId());
	} catch (Exception e) {
	    throw new PersistException(e);
	}
    }
}
