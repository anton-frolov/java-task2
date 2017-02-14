package rt.task2.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import rt.task2.config.AppConfig;
import rt.task2.data.domain.Person;
import rt.task2.data.domain.Ticket;
import rt.task2.data.domain.User;

public class DaoFactoryImpl implements DaoFactory<Connection> {

    private Map<Class, DaoCreator> creators;
    private DataSource datasource = new DataSource();

    public static class SingletonHolder {
	public static final DaoFactoryImpl HOLDER_INSTANCE = new DaoFactoryImpl();
    }

    public static DaoFactory<Connection> getInstance() {

	return SingletonHolder.HOLDER_INSTANCE;
    }

    private DaoFactoryImpl() {

	PoolProperties p = new PoolProperties();
	p.setUrl(AppConfig.instance.getUrl());
	p.setDriverClassName(AppConfig.instance.getDriver());
	p.setUsername(AppConfig.instance.getUser());
	p.setPassword(AppConfig.instance.getPassword());
	p.setJmxEnabled(AppConfig.instance.isJmxEnabled());
	p.setTestWhileIdle(AppConfig.instance.isTestWhileIdle());
	p.setTestOnBorrow(AppConfig.instance.isTestOnBorrow());
	p.setValidationQuery(AppConfig.instance.getValidationQuery());
	p.setTestOnReturn(AppConfig.instance.isTestOnReturn());
	p.setValidationInterval(AppConfig.instance.getValidationInterval());
	p.setTimeBetweenEvictionRunsMillis(AppConfig.instance.getTimeBetweenEvictionRunsMillis());
	p.setMaxActive(AppConfig.instance.getMaxActive());
	p.setInitialSize(AppConfig.instance.getInitialSize());
	p.setMaxWait(AppConfig.instance.getMaxWait());
	p.setRemoveAbandonedTimeout(AppConfig.instance.getRemoveAbandonedTimeout());
	p.setMinEvictableIdleTimeMillis(AppConfig.instance.getMinEvictableIdleTimeMillis());
	p.setMinIdle(AppConfig.instance.getMinIdle());
	p.setLogAbandoned(AppConfig.instance.isLogAbandoned());
	p.setRemoveAbandoned(AppConfig.instance.isRemoveAbandoned());
	p.setJdbcInterceptors(AppConfig.instance.getJdbcInterceptors());
	datasource = new DataSource();
	datasource.setPoolProperties(p);

	creators = new HashMap<Class, DaoCreator>();
	creators.put(User.class, new DaoCreator<Connection>() {
	    @Override
	    public GenericDao create(Connection connection) {
		return new UserDaoImpl(connection);
	    }
	});

	creators.put(Person.class, new DaoCreator<Connection>() {
	    @Override
	    public GenericDao create(Connection connection) {
		return new PersonDaoImpl(connection);
	    }
	});

	creators.put(Ticket.class, new DaoCreator<Connection>() {
	    @Override
	    public GenericDao create(Connection connection) {
		return new TicketDaoImpl(connection);
	    }
	});
    }

    public Connection getContext() throws PersistException {

	Connection connection = null;
	try {
	    connection = datasource.getConnection();
	} catch (SQLException e) {
	    throw new PersistException(e);
	}
	return connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
	DaoCreator creator = creators.get(dtoClass);
	if (creator == null) {
	    throw new PersistException("Dao object for " + dtoClass + " not found.");
	}
	return creator.create(connection);
    }

}
