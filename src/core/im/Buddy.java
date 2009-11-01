package core.im;

import org.jivesoftware.smack.packet.Presence;

public class Buddy {
	String alias;
	String groupName;
	String mergeID;
	String userID;
	String screenname;
	String status;
	String message;
	
	boolean isOffline;
	
	public Buddy()
	{
		status = "offline";
		isOffline = true;
	}

	
	public void setPresence(Presence p)
	{
		if(p.getMode() == Presence.Mode.available || p.getMode() == Presence.Mode.chat)
		{
			status = "Available";
			isOffline = false;
		}
		else if(p.getMode() == Presence.Mode.away || p.getMode() == Presence.Mode.dnd)
		{
			status = "Away";
			isOffline = false;
		}
		else if (p.getMode() == Presence.Mode.xa) //No idea what the connection between xa and idle actually is
		{
			status = "Idle";
			isOffline = false;
		}
		else
			status = "offline";
	}
	
	public boolean getOffline()
	{
		return isOffline;
	}
	
	public void setStatusMessage(String s)
	{
		message = s;
	}
	
}
