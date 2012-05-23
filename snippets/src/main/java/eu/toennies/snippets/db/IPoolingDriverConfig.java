package eu.toennies.snippets.db;

public interface IPoolingDriverConfig {
	
	public String getDriver();
	
	public String getURL();
	
	public String getUsername();
	
	public String getPassword();

	public String getPoolName();

}
