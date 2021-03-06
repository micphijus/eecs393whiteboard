package core.im;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class ChatboardMessage implements MessageListener{
	
	XMPPConnection conn;
	Vector<ListDataListener> listeners;
	Vector<ListDataListener> whiteboards;
	Queue<IM> queue;
	String theUser;
	Queue<Message> whiteBoardQueue;
	
	public ChatboardMessage()
	{
		conn = null;
		queue = null;
		theUser = null;
		listeners = new Vector<ListDataListener>();
		whiteboards = new Vector<ListDataListener>();
		whiteBoardQueue = null;
	}
	public ChatboardMessage(XMPPConnection conn, String from)
	{
		this.conn = conn;
		queue = new ArrayBlockingQueue<IM>(10);
		this.theUser = from;
		listeners = new Vector<ListDataListener>();
		whiteboards = new Vector<ListDataListener>();
		whiteBoardQueue = new ArrayBlockingQueue<Message>(10);
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
		}
		catch(XMPPException e)
		{
			System.out.println("Caught XMPPException: " + e);
		}
		catch(Exception e)
		{
			System.out.println("Caught Exception: " + e);
			e.printStackTrace();
		}
	}
	public void sendQueue(Chat chat, Queue<String> q)
	{
		try
		{

			boolean sendFlag = true;
			String theParticipant = chat.getParticipant();
			if(theParticipant.indexOf("/") != -1)
			{
				sendFlag = true;
			}
			if(sendFlag)
			{
				Message message = new Message();
				message.setProperty("whiteboardqueue", q);
				chat.sendMessage(message);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void processMessage(Chat arg0, Message arg1) {
		
		String participant = arg0.getParticipant(); //First message has a slight exception to handle
		Object property = arg1.getProperty("whiteboardqueue");
		System.out.println(property);
		if(participant.indexOf("/") != -1)
			participant = participant.substring(0, participant.indexOf("/"));
		System.out.println(arg1.getBody());
		if(arg1.getBody() != null)
		{
			IM im = new IM();
			im.automatic = false;
			
			
			im.from = participant;
			im.to = theUser;
			im.message = arg1.getBody();
			
			queue.add(im);
			for(int i = 0; i < listeners.size(); i++)
				listeners.get(i).contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0));
		}
		if(property != null)
		{
			//We got a whiteboard message
			try
			{
				whiteBoardQueue.add(arg1);
				
				for(int i = 0; i < listeners.size(); i++) 
					listeners.get(i).intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 0, 0));
	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected()
	{
		return conn.isConnected();
	}
	

}
