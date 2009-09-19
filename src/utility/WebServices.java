package utility;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

//This class is actually several utility methods associated with XMLRPC
//Will allow login and other method access via 
//xmlrpc webservice

public class WebServices {
	
	XmlRpcClient client;
	XmlRpcClientConfigImpl config;
	MessageDigest md;
	
	String url;
	
	//initialize the webservice stuff
	
	public WebServices()
	{
		client = new XmlRpcClient();
		config = new XmlRpcClientConfigImpl();
		
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
		}
		catch(MalformedURLException e)
		{
			System.out.println("Bad URL: " + e);
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("No such algorithm: " + e);
		}
	}
	
	public void Register(String name, String username, String email, String password)
	{
		md.update(password.getBytes());
		String md5password = md.digest().toString();
		md.reset();
		Vector<String> params = new Vector<String>();
		params.add(name); 
		params.add(email);
		params.add(username);
		params.add(md5password);
		try
		{
			client.execute("Register", params);
		}
		catch(XmlRpcException e)
		{
			System.out.println("Caught XMLRPCException: " + e);
		}
	}

}
