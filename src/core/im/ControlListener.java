package core.im;

import gui.MessageDialog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;

import sun.rmi.server.WeakClassHashMap;

import core.abstraction.Controller;

public class ControlListener implements ChatManagerListener, ListDataListener, Controller {

	public XMPPConnection conn;
	public HashMap<String, WeakReference<MessageDialog>> messages;
	public ChatboardMessage msg;
	public String userID;
	public HashMap<String, Chat> chats;
	
	public ControlListener()
	{
		conn = null;
		messages = new HashMap<String, WeakReference<MessageDialog>>();
		msg = new ChatboardMessage();
		chats = new HashMap<String, Chat>();
		userID = "";
	}

	public ControlListener(XMPPConnection c, String from)
	{
		conn = c;
		messages = new HashMap<String, WeakReference<MessageDialog>>();
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
		
		if(messages.get(from) == null)
		{
			MessageDialog dialog = new MessageDialog(from);
			WeakReference<MessageDialog> d = new WeakReference<MessageDialog>(dialog);
			messages.put(from, d);
		}
	}
	
	public void addDialog(MessageDialog dialog, String name)
	{
		WeakReference<MessageDialog> d = new WeakReference<MessageDialog>(dialog);
		messages.put(name,  d);
	}
	

	@Override
	public void contentsChanged(ListDataEvent e) {
		Queue<IM> q = msg.queue;
		while(!q.isEmpty())
		{
			IM im = q.remove();
			WeakReference<MessageDialog> d = messages.get(im.from);
			if(d == null)
			{
				MessageDialog dialog = new MessageDialog(im.from);
				d = new WeakReference<MessageDialog>(dialog);
				messages.put(im.from, d);
			}
			d.get().receiveMessage(im);
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

	@Override
	public void removeDialog(String key) {
		// TODO Auto-generated method stub
		messages.remove(key);
	}

}
