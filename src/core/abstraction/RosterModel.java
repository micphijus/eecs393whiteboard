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
	private Vector<ListDataListener>listeners;
	private ChatboardRoster roster;
	
	//public HashMap<String, String> aliasMap;
	public HashMap<String, Vector<Buddy>> onlineMap;
	
	
	
	public RosterModel()
	{
		groups = new Vector<RosterGroup>();
		onlineMap = new HashMap<String, Vector<Buddy>>();
		//aliasMap = new HashMap<String, String>();
		listeners = new Vector<ListDataListener>();
		roster = null;
	}
	
	public RosterModel(ChatboardRoster theRoster)
	{
		this();
		roster = theRoster;
		//setup();
		roster.addListener(this);
	}
	
	public void addListener(ListDataListener listener)
	{
		listeners.add(listener);
	}
	
	
	public String toString()
	{
		return onlineMap.toString();
	}
	
	public void updateOnline(Vector<Buddy> online)
	{
		Vector<Buddy>localOnline = new Vector<Buddy>(online);
		Vector<RosterGroup> groups = roster.groups;
		for(RosterGroup g : groups)
		{
			Vector<Buddy>groupBuddies = new Vector<Buddy>();
			for(int i = 0; i < localOnline.size(); i++)
			{
				Buddy b = localOnline.get(i);
				//System.out.println(g.contains(b.userID));
				if(g.contains(b.userID))
				{
					//System.out.println("In here");
					b.groupName = g.getName();
					groupBuddies.add(b);
					localOnline.remove(i);
					i--;
					
				}		
			//	if(aliasMap.get(b.alias) == null)
				//	aliasMap.put(b.alias, b.userID);
				//System.out.println(aliasMap);
			}
			onlineMap.put(g.getName(), groupBuddies);
		}
		if(!localOnline.isEmpty())
			onlineMap.put("Unorganized", localOnline);
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
		updateOnline(roster.online);
		for(ListDataListener l : listeners)
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0));
	}
	@Override
	public void intervalAdded(ListDataEvent e) {
		updateOnline(roster.online);
		
	}
	@Override
	public void intervalRemoved(ListDataEvent e) {
		updateOnline(roster.online);
		
	}
}
