package eu.toennies.snippets.db;

import java.sql.DriverManager;
import java.sql.SQLException;
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
 * A pooling driver for a database connection. To get a new connection use the
 * following code:
 * <code>Connection connection = DriverManager.getConnection(DbPoolingDriver.getPoolName());</code>
 * 
 * A pooling driver instance has to be initialized with:
 * 
 * <code>IPoolingDriverConfig config = new PoolingDriverConfig();</code>
 * <code>DBPoolingDriver.setConfig(config);</code>
 * 
 * @author toennies
 * 
 */
public class DbPoolingDriver {
	/**
	 * The instance of the pooling driver
	 */
	private static volatile DbPoolingDriver INSTANCE;

	/**
	 * The logger used in the instance
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

	/**
	 * The pooling url
	 */
	private static final String POOLING_URL = "jdbc:apache:commons:dbcp:";

	/**
	 * The configuration used for this pool
	 */
	private IPoolingDriverConfig config;

	/**
	 * true if the pool has been initialized with a configuration
	 */
	private boolean isInitialized = false;

	/**
	 * true if this is a DB2 Connection configuration, determined by DB2Driver
	 * in the driver class name
	 */
	private boolean isDB2 = false;

	/**
	 * The private constructor for a real singelton.
	 */
	private DbPoolingDriver() {

	}

	/**
	 * This method retrieves the current instance of the pooling driver. There
	 * is always just one in the system (singelton).
	 * 
	 * @return the DbPoolingDriver instance
	 */
	public static DbPoolingDriver getInstance() {
		if (INSTANCE == null) {
			synchronized (DbPoolingDriver.class) {
				INSTANCE = new DbPoolingDriver();
			}
		}

		return INSTANCE;
	}

	/**
	 * This method sets the driver up.
	 * 
	 * @param connectURI
	 *            - the URI to connect to
	 * @throws ClassNotFoundException
	 *             is thrown if the org.apache.commons.dbcp.PoolingDriver class
	 *             could not be found.
	 * @throws SQLException
	 *             is thrown if the DriverManager cannot get a driver for our
	 *             pool
	 */
	private void setupDriver(String connectURI) throws ClassNotFoundException, SQLException {
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
		props.setProperty("maxActive", config.getPoolSize());
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, props);

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,
				connectionPool, null, null, false, true);

		//
		// Finally, we create the PoolingDriver itself...
		//
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(POOLING_URL);

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

	/**
	 * This method print out the current driver status. It uses the SLF4J
	 * logging functionality in the INFO mode.
	 * 
	 * @throws PoolingException
	 *             is thrown if the driver has not been initialized
	 * @throws SQLException
	 *             is thrown if the DriverManager cannot get a driver for our
	 *             pool
	 * 
	 */
	public void printDriverStats() throws PoolingException, SQLException {
		checkConfig();

		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(POOLING_URL);
		ObjectPool connectionPool = driver.getConnectionPool(config.getPoolName());

		logger.info("NumActive: " + connectionPool.getNumActive());
		logger.info("NumIdle: " + connectionPool.getNumIdle());

	}

	/**
	 * This method shut the driver down.
	 * 
	 * @throws PoolingException
	 *             is thrown if the driver has not been initialized
	 * @throws SQLException
	 *             is thrown if the DriverManager cannot get a driver for our
	 *             pool
	 */
	public void shutdownDriver() throws PoolingException, SQLException {
		checkConfig();

		PoolingDriver driver = (PoolingDriver) DriverManager.getDriver(POOLING_URL);
		driver.closePool(config.getPoolName());
	}

	/**
	 * This method initialize the driver instance with a current configuration.
	 * 
	 * @param config
	 *            - an instance of a IPoolingDriverConfig
	 */
	public static void setConfig(IPoolingDriverConfig config) {
		if (INSTANCE == null) {
			getInstance();
		}

		INSTANCE.config = config;
		INSTANCE.init();

		if (INSTANCE.config.getDriver().contains("DB2Driver")) {
			INSTANCE.isDB2 = true;
		}
	}

	/**
	 * This method initializes the current driver by retrieving the Class for
	 * the driver name.
	 */
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

	/**
	 * This method retrieves the current pool name.
	 * 
	 * @return the used pool name
	 * @throws PoolingException
	 *             is thrown if the driver has not been initialized
	 */
	public String getPoolName() throws PoolingException {
		checkConfig();

		return POOLING_URL + this.config.getPoolName();
	}

	public boolean isDB2() {
		return isDB2;
	}

	/**
	 * This method checks if the driver has been initialized with a
	 * IPoolingDriverConfig instance.
	 * 
	 * @throws PoolingException
	 *             is thrown if the driver has not been initialized
	 */
	private void checkConfig() throws PoolingException {
		if (!INSTANCE.isInitialized) {
			throw new PoolingException("Pooling Driver is not initialized. Please call setConfig()");
		}
	}

}
