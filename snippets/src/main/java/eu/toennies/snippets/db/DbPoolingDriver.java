package eu.toennies.snippets.db;

import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection con = DriverManager.getConnection(DbPoolingDriver.getPoolName());
 * 
 * @author toennies
 * 
 */
public class DbPoolingDriver {
	private static volatile DbPoolingDriver INSTANCE;
	private Logger logger = LoggerFactory.getLogger(this.getClass()
			.getCanonicalName());

	private static final String POOLING_URL = "jdbc:apache:commons:dbcp:";

	private IPoolingDriverConfig config;
	private boolean isInitialized = false;

	private DbPoolingDriver() {

	}

	public static DbPoolingDriver getInstance() {
		if (INSTANCE == null) {
			synchronized (DbPoolingDriver.class) {
				INSTANCE = new DbPoolingDriver();
			}
		}

		return INSTANCE;
	}

	private void setupDriver(String connectURI) throws Exception {
		//
		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		ObjectPool connectionPool = new GenericObjectPool(null);

		//
		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string passed in the command line
		// arguments.
		//
		Properties props = new Properties();
		props.setProperty("user", config.getUsername());
		props.setProperty("password", config.getPassword());
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, props);

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, false, true);

		//
		// Finally, we create the PoolingDriver itself...
		//
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver(POOLING_URL);

		//
		// ...and register our pool with it.
		//
		driver.registerPool(config.getPoolName(), connectionPool);

		//
		// Now we can just use the connect string
		// "jdbc:apache:commons:dbcp:example"
		// to access our pool of Connections.
		//
	}

	public void printDriverStats() throws Exception {
		checkConfig();

		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver(POOLING_URL);
		ObjectPool connectionPool = driver.getConnectionPool(config
				.getPoolName());

		System.out.println("NumActive: " + connectionPool.getNumActive());
		System.out.println("NumIdle: " + connectionPool.getNumIdle());

	}

	public void shutdownDriver() throws Exception {
		checkConfig();

		PoolingDriver driver = (PoolingDriver) DriverManager
				.getDriver(POOLING_URL);
		driver.closePool(config.getPoolName());
	}

	public static void setConfig(IPoolingDriverConfig config) {
		if (INSTANCE == null) {
			getInstance();
		}

		INSTANCE.config = config;
		INSTANCE.init();
	}

	private void init() {
        logger.trace("Loading underlying JDBC driver.");
        try {
            Class.forName(config.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.trace("Done.");
		
        logger.trace("Setting up driver.");
        try {
            setupDriver(config.getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.trace("Done.");  
        isInitialized = true;
	}
	
	public String getPoolName() throws PoolingException {
		checkConfig();

		return POOLING_URL + this.config.getPoolName();
	}

	private void checkConfig() throws PoolingException {
		if (!INSTANCE.isInitialized) {
			throw new PoolingException(
					"Pooling Driver is not initialized. Please call setConfig()");
		}
	}

}
