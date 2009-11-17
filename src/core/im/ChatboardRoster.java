package core.im;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class ChatboardRoster implements RosterListener{
	XMPPConnection conn;
	Vector<Buddy>online;
	Vector<Buddy>offline;
	Vector<ListDataListener> listeners;
	Roster roster;
	
	public ChatboardRoster()
	{
		conn = null;
		roster = null;
		online = new Vector<Buddy>();
		offline = new Vector<Buddy>();
		listeners = new Vector<ListDataListener>();
	}
	
	public ChatboardRoster(XMPPConnection conn)
	{
		this();
		this.conn = conn;
	}
	
	
	//Need to add some listeners
	public void addListener(ListDataListener ldl)
	{
		listeners.add(ldl);
	}
	
	public void pullRoster()
	{
		roster = conn.getRoster();
		//roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
		//updateOnline();
		roster.addRosterListener(this);	
	}
	
	public boolean updateRoster(String username, String alias, String group)
	{
		//check to see if the roster already contains username
		if(roster.contains(username))
			return false;
		//if not, then add the group if it doesn't exist
		//then add the entry
		else
		{
			if(roster.getGroup(group) == null)
				roster.createGroup(group);
			String[] groups = {group};
			try
			{
				roster.createEntry(username, alias, groups);
			}
			catch(XMPPException e)
			{
				System.out.println("Error adding new entry: " + e);
				return false;
			}
		}
		return true;
	}
	
	public boolean createGroup(String name)
	{
		if(roster.getGroup(name) != null)
		{
			roster.createGroup(name);
			return true;
		}
		else
			System.out.println("Group already exists");
		return false;
	}
	
	public void updateOnline()
	{
		Collection<RosterEntry> rosterList = roster.getEntries();
		Iterator<RosterEntry> iter = rosterList.iterator();
		while(iter.hasNext())
		{
			RosterEntry entry = iter.next();
			//System.out.println(entry.getUser() + ": " + roster.getPresence(entry.getUser()).getType());
			
			Buddy b = new Buddy();
			b.alias = entry.getName();
			//b.groupName = entry.getGroups().iterator().next().getName(); //get first one
			b.userID = entry.getUser();
			b.setPresence(roster.getPresence(entry.getUser()));
			
			if(!b.getOffline())
			{
				b.setStatusMessage(roster.getPresence(entry.getUser()).getStatus());
				online.add(b);
			}
			else
				offline.add(b);
		}
		for(int i = 0; i < online.size(); i++)
			System.out.println(online.get(i).userID);
	}

	@Override
	public void entriesAdded(Collection<String> arg0) {
		// TODO Auto-generated method stub
		pullRoster();
		
	}

	@Override
	public void entriesDeleted(Collection<String> arg0) {
		pullRoster();
		
	}

	@Override
	public void entriesUpdated(Collection<String> arg0) {
		pullRoster();
		
		
	}

	@Override
	public void presenceChanged(Presence arg0) {
		System.out.println("Presence change detected");
		//First get the user
		//then get the entry
		//Finally remove the entry from one list, and add to the other
		//create a new bean to handle it
		
		Buddy b = new Buddy();
		//Get the participant from the presence
		//Need to parse out some stuff
		System.out.println(arg0.getFrom());
		String participant = arg0.getFrom();
		participant = participant.substring(0, participant.indexOf("/"));
		b.userID = participant;
		
		//Set alias if we can find one
		b.alias = roster.getEntry(participant).getName();
		Presence bestPresence = roster.getPresence(arg0.getFrom());
		b.setStatusMessage(arg0.getStatus());
		System.out.println(bestPresence.getMode());
		b.setPresence(bestPresence);
		
		
		if(!b.getOffline())
		{
			for(int i = 0; i< offline.size(); i++)
			{
				if(offline.get(i).userID.equals(b.userID))
				{
					offline.remove(i);
					break;
				}
			}
			online.add(b);
		}
		else
		{
			for(int i = 0; i < online.size(); i++)
			{
				if(online.get(i).userID.equals(b.userID))
				{
					online.remove(i);
					break;
				}
			}
			offline.add(b);
		}
		
		//Make list unique
		HashMap<String, Buddy> theMap = new HashMap<String, Buddy>();
		for(int i = 0; i < online.size(); i++)
			theMap.put(online.get(i).userID, online.get(i));
		
		online.clear();
		Iterator<String> iter = theMap.keySet().iterator();
		while(iter.hasNext())
			online.add(theMap.get(iter.next()));
		
		for(int i = 0; i < offline.size(); i++)
			theMap.put(offline.get(i).userID, offline.get(i));
		
		offline.clear();
		iter = theMap.keySet().iterator();
		while(iter.hasNext())
			offline.add(theMap.get(iter.next()));

		for(int i = 0; i < listeners.size(); i++)
		{
			ListDataListener ldl = listeners.get(i);
			ldl.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0));
		}
		
	}

	public Vector<Buddy> getOnline() {
		return online;
	}

	public void setOnline(Vector<Buddy> online) {
		this.online = online;
	}

	public Vector<Buddy> getOffline() {
		return offline;
	}

	public void setOffline(Vector<Buddy> offline) {
		this.offline = offline;
	}
}
