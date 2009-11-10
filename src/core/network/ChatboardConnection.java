package core.network;

import java.security.*;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class ChatboardConnection {
	private String userName;
	private String password;
		
	public ChatboardConnection()
	{
		userName = null;
		password = null;
	}
	
	public ChatboardConnection(String myName, String myPass)
	{
		userName = myName;
		password = myPass;
	}
	
	public XMPPConnection createConnection(String server, int port, String alias)
	{
		XMPPConnection conn = null;
		try
		{
			conn = new XMPPConnection(new ConnectionConfiguration(server, port, alias));
			conn.connect();
			conn.login(userName, password);
			while(!conn.isAuthenticated())
			{
				
			}
			System.out.println("Connection created: " + conn.isAuthenticated());
		}
		catch(XMPPException e)
		{
			System.out.println("Error in establishing connection using XMPP: " + e);
		}
		return conn;
	}

	//Getters and setters, however password does not have a getter
	//For added security
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

}
