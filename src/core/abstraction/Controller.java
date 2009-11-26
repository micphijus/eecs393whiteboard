package core.abstraction;

import gui.MessageDialog;

public interface Controller {
	
	public boolean sendMessage(String from, String message);
	public void removeDialog(String key);
	public void addDialog(MessageDialog dialog, String name);
}
