package core.im;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class ChatboardMessage implements MessageListener{
	
	XMPPConnection conn;
	
	public ChatboardMessage()
	{
		conn = null;
	}
	public ChatboardMessage(XMPPConnection conn)
	{
		this.conn = conn;
	}
	
	public Chat createChat(String userID)
	{
		ChatManager manager = conn.getChatManager();
		Chat chat = manager.createChat(userID, this);
		return chat;
	}
	public void sendMessage(Chat chat, Message message)
	{
		try
		{
			chat.sendMessage(message);
		}
		catch(XMPPException e)
		{
			System.out.println("Caught XMPPException: " + e);
		}
		catch(Exception e)
		{
			System.out.println("Caught Exception: " + e);
		}
	}
	@Override
	public void processMessage(Chat arg0, Message arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
