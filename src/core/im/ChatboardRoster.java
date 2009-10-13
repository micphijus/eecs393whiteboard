package core.im;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

public class ChatboardRoster implements RosterListener{
	XMPPConnection conn;
	Vector<RosterEntry>online;
	Vector<RosterEntry>offline;
	Roster roster;
	
	public ChatboardRoster()
	{
		conn = null;
		roster = null;
		online = new Vector<RosterEntry>();
		offline = new Vector<RosterEntry>();
	}
	
	public ChatboardRoster(XMPPConnection conn)
	{
		super();
		this.conn = conn;
	}
	
	
	public void pullRoster()
	{
		roster = conn.getRoster();
		roster.addRosterListener(this);
		updateOnline();
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
			if(roster.getPresence(entry.getName()).getType() == Type.available)
				online.add(entry);
			else
				offline.add(entry);
		}
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
		//First get the user
		//then get the entry
		//Finally remove the entry from one list, and add to the other
		String user = arg0.getFrom();
		RosterEntry entry = roster.getEntry(user);
		if(arg0.getType() == Type.available)
		{
			if(offline.contains(entry))
				offline.remove(entry);
			online.add(entry);
		}
		else
		{
			if(online.contains(entry))
				online.remove(entry);
			offline.add(entry);
		}
		
	}


	
	

}
