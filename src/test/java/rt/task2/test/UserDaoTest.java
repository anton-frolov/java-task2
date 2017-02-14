package rt.task2.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rt.task2.data.DaoFactory;
import rt.task2.data.DaoFactoryImpl;
import rt.task2.data.GenericDao;
import rt.task2.data.PersistException;
import rt.task2.data.domain.AbstractEntity;
import rt.task2.data.domain.User;

public class UserDaoTest {

    private Connection connection;
    private GenericDao<User, Long> dao;
    private static final DaoFactory<Connection> factory = DaoFactoryImpl.getInstance();

    public Connection context() {
	return connection;
    }

    @Before
    public void setUp() throws PersistException, SQLException {
	connection = factory.getContext();
	connection.setAutoCommit(false);
	dao = factory.getDao(connection, User.class);
    }

    @After
    public void tearDown() throws SQLException {
	context().rollback();
	context().close();
    }

    @Test
    public void testCreateUser() throws Exception {
	AbstractEntity<Long> dto = dao.create();
	Assert.assertNotNull(dto);
	Assert.assertNotNull(dto.getId());
    }

    @Test
    public void testGetUserById() throws Exception {
	AbstractEntity<Long> dto = dao.getById(1L);
	Assert.assertNotNull(dto);
    }

    @Test
    public void testGetAllUsers() throws Exception {
	List<User> list = dao.getAll();
	Assert.assertNotNull(list);
	Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateUser() throws Exception {

	User user = dao.getById(2L);
	Assert.assertNotNull(user);
	Assert.assertNotNull(user.getUserId());
	Assert.assertTrue(user.getUserId().equals("test2"));
	Assert.assertNotNull(user.getPassword());
	Assert.assertTrue(user.getPassword().equals("test2"));
	user.setUserId("newLogin");
	user.setPassword("newPasswd");
	user.setEmail("newemail@test.ru");
	dao.update(user);
	user = null;
	user = dao.getById(2L);
	Assert.assertNotNull(user);
	Assert.assertTrue(user.getUserId().equals("newLogin"));
	Assert.assertTrue(user.getPassword().equals("newPasswd"));
	Assert.assertTrue(user.getEmail().equals("newemail@test.ru"));
    }

    @Test
    public void testDeleteUser() throws Exception {
	User user = dao.getById(2L);
	Assert.assertNotNull(user);
	dao.delete(user);
	user = null;
	user = dao.getById(2L);
	Assert.assertNull(user);
    }

    @Test
    public void testQuery() throws Exception {
	String sql = dao.getSelectQuery() + " WHERE id = ? and login=? ORDER BY ID;";
	Object[] params = { 2L, "test2" };
	List<User> list = dao.query(sql, params);
	Assert.assertNotNull(list);
	Assert.assertTrue(list.size() > 0);
    }
}
