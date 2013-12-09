package eu.toennies.snippets.db;

/**
 * This exception is thrown if there is any error with the creation of the DB pool.
 * 
 * @author toennies
 *
 */
public class PoolingException extends Exception {

	private static final long serialVersionUID = 1166053314352642110L;

	/**
	 * The default constructor.
	 * @param message - the message to use for the exception instance
	 */
	public PoolingException(String message) {
		super(message);
	}

	

}
