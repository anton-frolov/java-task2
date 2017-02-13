package rt.task2.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import rt.task2.config.AppConfig;

public class AppConfigTest {
	
	@Test
	public void testAppConfig()
	{
		assertNotNull("org.postgresql.Driver", AppConfig.instance.getDriver());
		assertNotNull("rttest", AppConfig.instance.getUser());
		assertNotNull("rttest", AppConfig.instance.getPassword());
		assertNotNull("jdbc:postgresql://localhost:5433/rttest", AppConfig.instance.getUrl());

	}	
}
