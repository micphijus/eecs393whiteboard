package gui;

import java.util.Vector;

import javax.swing.JList;

public class GUIRoster {
	
	private static JList onlineRoster;
	private static JList offlineRoster;
	
	public GUIRoster(Vector online, Vector offline){
		onlineRoster = new JList(online);
		offlineRoster = new JList(offline);
	}
}
