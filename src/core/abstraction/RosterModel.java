package core.abstraction;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import core.im.Buddy;
import core.im.ChatboardRoster;

public class RosterModel implements ListDataListener{
	
	private Vector<RosterGroup> groups;
	private ChatboardRoster roster;
	
	public HashMap<String, String> aliasMap;
	public HashMap<String, Vector<Buddy>> onlineMap;
	
	
	public RosterModel()
	{
		groups = new Vector<RosterGroup>();
		onlineMap = new HashMap<String, Vector<Buddy>>();
		aliasMap = new HashMap<String, String>();
		roster = null;
	}
	
	public RosterModel(ChatboardRoster theRoster)
	{
		this();
		roster = theRoster;
		//setup();
		roster.addListener(this);
	}
	
	public void setup()
	{
		groups = new Vector<RosterGroup>(roster.roster.getGroups());
		updateOnline(roster.online);
	}
	
	public String toString()
	{
		return onlineMap.toString();
	}
	
	public synchronized void updateOnline(Vector<Buddy> online)
	{
		for(RosterGroup g : groups)
		{
			Vector<Buddy>groupBuddies = new Vector<Buddy>();
			for(Buddy b : online)
			{
				if(g.contains(b.userID))
				{
					b.groupName = g.getName();
					groupBuddies.add(b);
					online.removeElement(b);
				}		
			}
			onlineMap.put(g.getName(), groupBuddies);
		}
		if(!online.isEmpty())
			onlineMap.put("Unorganized", online);
	}
	
	public ChatboardRoster getRoster() {
		return roster;
	}
	public void setRoster(ChatboardRoster roster) {
		this.roster = roster;
	}
	public Set<String> getGroups()
	{
		return onlineMap.keySet();
	}
	@Override
	public void contentsChanged(ListDataEvent e) {
		setup();
		
	}
	@Override
	public void intervalAdded(ListDataEvent e) {
		setup();
		
	}
	@Override
	public void intervalRemoved(ListDataEvent e) {
		setup();
		
	}
}
