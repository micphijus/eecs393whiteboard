package core.im;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class ChatboardRoster implements RosterListener{
	public XMPPConnection conn;
	public Vector<Buddy>online;
	public Vector<Buddy>offline;
	public Vector<ListDataListener> listeners;
	public Vector<RosterGroup> groups;
	public Roster roster;
	
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
	
	public void addBuddy(String userName, String alias, String[] group) throws Exception
	{
		try
		{
			roster.createEntry(userName, alias, group);
			updateOnline();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void removeBuddy(String userName) throws Exception
	{
		try
		{
			roster.removeEntry(roster.getEntry(userName));
			roster.reload();
			online = new Vector<Buddy>();
			offline = new Vector<Buddy>();
			updateOnline();
			dedupe();
			informParent();
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void pullRoster()
	{
		roster = conn.getRoster();
		groups = new Vector<RosterGroup>(roster.getGroups());
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
		if(roster.getGroup(name) == null)
		{
			roster.createGroup(name);
			groups = new Vector<RosterGroup>(roster.getGroups());
			informParent();
			return true;
		}
		else
			System.out.println("Group already exists");
		
		return false;
	}
	
	public boolean addBuddyToGroup(String userID, String groupName) throws Exception
	{
		try
		{
			RosterEntry entry = roster.getEntry(userID);
			if(entry.getGroups() != null)
			{
				Vector<RosterGroup> groups1 = new Vector<RosterGroup>(entry.getGroups());
				for(RosterGroup g : groups1)
					g.removeEntry(entry);
			}
			
			RosterGroup theGroup = roster.getGroup(groupName);
			theGroup.addEntry(entry);
			updateOnline();
			dedupe();
			groups = new Vector<RosterGroup>(roster.getGroups());
			informParent();
		}
		catch(Exception e)
		{
			throw e;
		}
		return true;
	}
	
	public void updateOnline()
	{
		Collection<RosterEntry> rosterList = roster.getEntries();
		Iterator<RosterEntry> iter = rosterList.iterator();
		while(iter.hasNext())
		{
			RosterEntry entry = iter.next();
			
			Buddy b = new Buddy();
			b.alias = entry.getName();
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
		//System.out.println("Presence change detected");
		//First get the user
		//then get the entry
		//Finally remove the entry from one list, and add to the other
		//create a new bean to handle it
		
		Buddy b = new Buddy();
		//Get the participant from the presence
		//Need to parse out some stuff
		String participant = arg0.getFrom();
		participant = participant.substring(0, participant.indexOf("/"));
		String client = participant.substring(participant.indexOf("/") + 1);
		b.userID = participant;
		b.client = client;
		
		//Set alias if we can find one
		b.alias = roster.getEntry(participant).getName();
		Presence bestPresence = roster.getPresence(arg0.getFrom());
		b.setStatusMessage(arg0.getStatus());
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
		
		dedupe();

		informParent();
		
	}
	
	public void dedupe()
	{
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
	}
	
	public void informParent()
	{
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
