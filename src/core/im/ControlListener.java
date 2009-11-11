package core.im;

import gui.MessageDialog;

import java.util.HashMap;
import java.util.Queue;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;

import core.abstraction.Controller;

public class ControlListener implements ChatManagerListener, ListDataListener, Controller {

	public XMPPConnection conn;
	public HashMap<String, MessageDialog> messages;
	public ChatboardMessage msg;
	public String userID;
	public HashMap<String, Chat> chats;
	
	public ControlListener()
	{
		conn = null;
		messages = new HashMap<String, MessageDialog>();
		msg = new ChatboardMessage();
		chats = new HashMap<String, Chat>();
		userID = "";
	}

	public ControlListener(XMPPConnection c, String from)
	{
		conn = c;
		messages = new HashMap<String, MessageDialog>();
		msg = new ChatboardMessage(c, from);
		userID = from;
		chats = new HashMap<String, Chat>();
	}
	
	public void createChatboardMessage(String from)
	{
		msg = new ChatboardMessage(conn, from);
	}
	
	public void addDataListener()
	{
		msg.listeners.add(this);
	}
	
	public Chat createChat(String userID)
	{
		ChatManager manager = conn.getChatManager();
		Chat chat = manager.createChat(userID, msg);
		MessageDialog dialog = new MessageDialog(userID);
		messages.put(userID, dialog);
		return chat;
	}
	
	public boolean sendMessage(String from, String message)
	{
		try
		{
			Chat chat = chats.get(from);
			if(chat == null)
				chat = createChat(from);
			msg.sendMessage(chat, message);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	@Override
	public void chatCreated(Chat arg0, boolean arg1) {
		arg0.addMessageListener(msg);
		String from = arg0.getParticipant().substring(0, arg0.getParticipant().indexOf('/'));
		chats.put(from, arg0);
		
		
		MessageDialog dialog = messages.get(from);
		
		//if the dialog doesn't exist, make one
		if(dialog == null)
		{
			dialog = new MessageDialog(from);
			messages.put(from, dialog);
		}
	}
	

	@Override
	public void contentsChanged(ListDataEvent e) {
		//Contents have been changed.
		Queue<IM>q = msg.queue;
		while(!q.isEmpty())
		{
			IM im = q.remove();
			System.out.println(im.message);
		}
		
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

}
