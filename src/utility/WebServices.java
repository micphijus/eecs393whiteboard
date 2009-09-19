package utility;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

//This class is actually several utility methods associated with XMLRPC
//Will allow login and other method access via 
//xmlrpc webservice

public class WebServices {
	XmlRpcClient client;
	XmlRpcClientConfigImpl config;
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
		}
		catch(MalformedURLException e)
		{
			System.out.println("Bad URL");
		}
	}

}
