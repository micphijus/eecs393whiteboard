package core.im;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class ChatboardMessage implements MessageListener{
	
	XMPPConnection conn;
	Queue<String> queue;
	
	public ChatboardMessage()
	{
		conn = null;
		queue = null;
	}
	public ChatboardMessage(XMPPConnection conn)
	{
		this.conn = conn;
		queue = new ArrayBlockingQueue<String>(10);
	}
	
	public Chat createChat(String userID)
	{
		ChatManager manager = conn.getChatManager();
		Chat chat = manager.createChat(userID, this);
		return chat;
	}
	public void sendMessage(Chat chat, String message)
	{
		//Put message on message queue
		try
		{
			chat.sendMessage(message);
			queue.add(message);
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
		queue.add(arg1.getBody());
	}
	

}
