package rt.task2.test;

import java.sql.SQLException;

import org.junit.Test;

import junit.framework.Assert;
import rt.task2.LoginService;
import rt.task2.data.PersistException;
import rt.task2.data.domain.User;

public class LoginServiceTest {
	
	private LoginService service = new LoginService();
	
	
	@Test
	public void testAuthenticateUser() throws PersistException, SQLException{
		Assert.assertTrue(service.authenticateUser("test", "test"));	
	}
	
	@Test
	public void testGetUserByUserId() throws PersistException, SQLException{
		User user = service.getUserByUserId("test");
		Assert.assertNotNull(user);
		user = service.getUserByUserId("");
		Assert.assertNull(user);
	}
	
	@Test
	public void testRegistrationUser() throws PersistException, SQLException{
		
		User user = service.registrationUser("testregistration", "test", "testemail@test.ru", false);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId()>1);
		Assert.assertTrue(user.getUserId().equals("testregistration"));
    	Assert.assertTrue(user.getPassword().equals("test")); 
    	Assert.assertTrue(user.getEmail().equals("testemail@test.ru")); 
		
	}
	

}
