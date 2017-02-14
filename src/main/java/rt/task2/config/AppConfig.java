package rt.task2.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	
	public static final AppConfig instance = new AppConfig();

	private String user;
	private String password;
	private String driver;
	private String url;
	//Tomcat pool 
	private boolean jmxEnabled = true;
	private boolean testWhileIdle = false;
	private boolean testOnBorrow = true;
	private String validationQuery = "SELECT 1";
	private boolean testOnReturn = false;
	private int validationInterval = 30000;
	private int timeBetweenEvictionRunsMillis = 30000;
	private int initialSize = 1;
	private int maxActive = 10;
	private int maxWait = 10000;
	private int removeAbandonedTimeout = 30;
	private int minEvictableIdleTimeMillis = 30000;
	private int minIdle = 10;
	private boolean logAbandoned = true;
	private boolean removeAbandoned = true;
	private String jdbcInterceptors = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
									"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";
	
	public AppConfig() {
		Properties prop = new Properties();
		InputStream inStream = AppConfig.class.getClassLoader().getResourceAsStream("app.properties");
		
		try {
			prop.load(inStream);
			user = prop.getProperty("db.user");
			password = prop.getProperty("db.password");
			url = prop.getProperty("db.url");
			driver = prop.getProperty("db.driver");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public boolean isJmxEnabled() {
		return jmxEnabled;
	}

	public void setJmxEnabled(boolean jmxEnabled) {
		this.jmxEnabled = jmxEnabled;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public int getValidationInterval() {
		return validationInterval;
	}

	public void setValidationInterval(int validationInterval) {
		this.validationInterval = validationInterval;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public boolean isLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public String getJdbcInterceptors() {
		return jdbcInterceptors;
	}

	public void setJdbcInterceptors(String jdbcInterceptors) {
		this.jdbcInterceptors = jdbcInterceptors;
	}

}
