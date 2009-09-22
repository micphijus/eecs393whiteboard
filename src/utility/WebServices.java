package utility;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

//Set up logging
import java.util.logging.*;

//This class is actually several utility methods associated with XMLRPC
//Will allow login and other method access via 
//xmlrpc webservice

public class WebServices {
	
	XmlRpcClient client;
	XmlRpcClientConfigImpl config;
	MessageDigest md;
	
	String url;
	boolean initialized = false;
	Logger logger;
	
	//initialize the webservice stuff
	
	public WebServices()
	{
		client = new XmlRpcClient();
		config = new XmlRpcClientConfigImpl();
		logger = Logger.getLogger("WebServices");
	}
	
	//overloaded constructor
	public WebServices(String url)
	{
		this();
		init(url);
	}
	
	//This will initialize the configuration
	public void init(String url)
	{
		this.url = url;
		try
		{
			config.setServerURL(new URL(url));
			client.setConfig(config);
			md = MessageDigest.getInstance("MD5");
			initialized = true;
			logger.info("Initialized");
		}
		catch(MalformedURLException e)
		{
			logger.severe("bad url: " + e);
		}
		catch(NoSuchAlgorithmException e)
		{
			logger.severe("No such algorithm: " + e);
		}
	}
	
	//Method registers the account
	public Object Register(String name, String username, String email, String password)
	{
		//First check if the program is initialized
		if(initialized)
		{
			//Creates an md5 hash of the password and transforms it into 
			//a string
			md.update(password.getBytes());
			String md5password = md.digest().toString();
			md.reset();
			
			//Add the params
			Vector<String> params = new Vector<String>();
			params.add(name); 
			params.add(email);
			params.add(username);
			params.add(md5password);
			
			//Finally run the register method
			try
			{
				Object result = client.execute("Register", params);
				logger.info("Result returned from Register function");
				return result;
			}
			catch(XmlRpcException e)
			{
				logger.severe("Caught XMLRPCException in Register: " + e);
				return null;
			}
		}
		else
			logger.warning("Uninitialized.  Cannot run register");
		
		return null;
	}
	
	//Same as above, slightly simpler
	public Object Login(String username, String password)
	{
		if(initialized)
		{
			md.update(password.getBytes());
			String md5password = md.digest().toString();
			md.reset();
			
			Vector<String> params = new Vector<String>();
			params.add(username);
			params.add(md5password);
			
			try
			{
				Object result = client.execute("Login", params);
				return result;
			}
			catch(XmlRpcException e)
			{
				logger.severe("Caught XMLRPCException in Login: " + e);
				return null;
			}
		}
		else
			logger.warning("Uninitialized.  Cannot run register");
		
		return null;
	}

}
