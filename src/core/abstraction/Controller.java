package core.abstraction;

import java.util.Queue;

import gui.MessageDialog;
import gui.WhiteboardDialog;

public interface Controller {
	
	public boolean sendMessage(String from, String message);
	public boolean sendQueue(String from, Queue<String> q);
	public void removeDialog(String key);
	public void addDialog(MessageDialog dialog, String name);
	public void addWhiteboard(String from, WhiteboardDialog wb);
	public void removeWhiteboard(String key);
}
