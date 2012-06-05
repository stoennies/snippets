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
	public String getDriver();
	
	/**
	 * Retrieves the db URL to connect to.
	 * @return the db url
	 */
	public String getURL();
	
	/**
	 * Retrieves the username for the db connection
	 * @return the username to use
	 */
	public String getUsername();
	
	/**
	 * Retrieves the password for the user for the db connection
	 * @return the password
	 */
	public String getPassword();

	/**
	 * Retrieves the pool name for the db connection
	 * @return the pool name
	 */
	public String getPoolName();

}
