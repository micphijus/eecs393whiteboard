package core.im;

import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class ChatboardRoster implements RosterListener{
	XMPPConnection conn;
	Roster roster;
	
	public ChatboardRoster()
	{
		conn = null;
		roster = null;
	}
	
	public ChatboardRoster(XMPPConnection conn)
	{
		this.conn = conn;
		roster = null;
	}
	
	
	public Roster pullRoster()
	{
		roster = conn.getRoster();
		roster.addRosterListener(this);
		return roster;
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

	@Override
	public void entriesAdded(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entriesDeleted(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entriesUpdated(Collection<String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void presenceChanged(Presence arg0) {
		// TODO Auto-generated method stub
		
	}


	
	

}
