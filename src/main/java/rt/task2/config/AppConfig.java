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

}
