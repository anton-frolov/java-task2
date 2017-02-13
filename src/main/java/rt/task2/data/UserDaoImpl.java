package rt.task2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import rt.task2.data.domain.User;

import java.sql.Types;

public class UserDaoImpl extends AbstractDaoImpl<User, Long>{


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
	protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
		try {
            statement.setString(1, object.getUserId());
            statement.setString(2, object.getPassword());
            if(object.getPersonId()!=null){
            	statement.setLong(3, object.getPersonId());
            }else
            {
            	statement.setNull(3, Types.NULL);
            }
            if(object.getEmail()!=null){
            	statement.setString(4, object.getEmail());
            }else
            {
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
	protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
		try {
            statement.setString(1, object.getUserId());
            statement.setString(2, object.getPassword());
            if(object.getPersonId()!=null){
            	statement.setLong(3, object.getPersonId());
            }else
            {
            	statement.setNull(3, Types.NULL);
            }
            if(object.getEmail()!=null){
            	statement.setString(4, object.getEmail());
            }else
            {
            	statement.setNull(4, Types.NULL);
            }
            statement.setLong(5, object.getId());            
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
	protected List<User> parseResultSet(ResultSet rs) throws PersistException {
		LinkedList<User> result = new LinkedList<User>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getLong("id"));
                user.setUserId(rs.getString("login"));
                user.setPassword(rs.getString("passwd"));
                user.setEmail(rs.getString("email"));
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;

	}

	

	
}