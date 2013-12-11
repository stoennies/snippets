package eu.toennies.snippets.db;

/**
 * An interface for a pooling driver configuration.
 * 
 * @author toennies
 *
 */
public interface IPoolingDriverConfig {
	
	/**
	 * Retrieves the driver to use
	 * @return the driver name
	 */
	String getDBDriver();
	
	/**
	 * Retrieves the db URL to connect to.
	 * @return the db url
	 */
	String getDBURL();
	
	/**
	 * Retrieves the username for the db connection
	 * @return the username to use
	 */
	String getDBUsername();
	
	/**
	 * Retrieves the password for the user for the db connection
	 * @return the password
	 */
	String getDBPassword();

	/**
	 * Retrieves the pool name for the db connection
	 * @return the pool name
	 */
	String getDBPoolName();

	/**
	 * Retrieves the number of max active connections
	 * @return the pool name
	 */
	String getDBPoolSize();

}
