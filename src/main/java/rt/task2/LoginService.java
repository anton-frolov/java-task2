package rt.task2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.User;

public class LoginService {
	
	private static DaoFactory<Connection> factory;
	
	
	protected static DaoFactory<Connection> getFactory(){
		
		if(factory==null){
			factory = new DaoFactoryImpl();
		}
		return factory;
		
	}
	
	public boolean authenticateUser(String userId, String password) throws PersistException, SQLException {
        
		User user = getUserByUserId(userId);         
        if(user!=null && user.getUserId().equals(userId) && user.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }

	public User getUserByUserId(String userId) throws PersistException, SQLException {
		
		Connection connection = getFactory().getContext(); 
		connection.setAutoCommit(true);
	    @SuppressWarnings("unchecked")
		GenericDao<User, Long> dao = factory.getDao(connection, User.class);
	    String sql = dao.getSelectQuery()+ " WHERE login=?;";
    	Object[] params = new Object[1];
    	params[0] = userId;
       	List<User> list = dao.query(sql, params);
       	if(list!=null && list.size()>0){
       		return list.get(0);
       	}else{
       		return null;
       	}
       	
	}
	
	public User registrationUser(String userId, String password, String userEmail) throws PersistException, SQLException{
		
		return registrationUser(userId, password, userEmail, true);
	}
	
	public User registrationUser(String userId, String password, String userEmail, boolean autoCommit) throws PersistException, SQLException{

		Connection connection = getFactory().getContext(); 
		connection.setAutoCommit(autoCommit);
	    @SuppressWarnings("unchecked")
		GenericDao<User, Long> dao = factory.getDao(connection, User.class);
	    User user = dao.create();
	    user.setUserId(userId);
    	user.setPassword(password);
    	user.setEmail(userEmail);
    	dao.update(user);
    	return user;
	}


}
