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
	Queue<IM> queue;
	String theUser;
	
	public ChatboardMessage()
	{
		conn = null;
		queue = null;
		theUser = null;
	}
	public ChatboardMessage(XMPPConnection conn, String from)
	{
		this.conn = conn;
		queue = new ArrayBlockingQueue<IM>(10);
		this.theUser = from;
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
			IM im = new IM();
			String theParticipant = chat.getParticipant();
			if(theParticipant.indexOf("/") != -1)
				theParticipant = theParticipant.substring(0, theParticipant.indexOf("/"));
			im.to = theParticipant;
			im.from = theUser;
			im.message = message;
			im.automatic = false;
			queue.add(im);
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
		if (arg1.getBody() == null)
			return;
		IM im = new IM();
		im.automatic = false;
		
		String participant = arg0.getParticipant(); //First message has a slight exception to handle
		
		if(participant.indexOf("/") != -1)
			participant = participant.substring(0, participant.indexOf("/"));
		im.from = participant;
		im.to = theUser;
		im.message = arg1.getBody();
		
		queue.add(im);
	}
	
	public boolean isConnected()
	{
		return conn.isConnected();
	}
	

}
