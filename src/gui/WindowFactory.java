package gui;

import core.whiteboard.WhiteboardPanelExecuter;
import gui.preferenceWindows.*;

public class WindowFactory {

	public enum WindowType {
		//new instant message, filemenu
		NewIM("New Instant Message", MainWindow.fileMenu),
		//open whiteboard, filemenu
		OpenWB("Open Whiteboard", MainWindow.fileMenu),
		AddFriend("Add Friend", MainWindow.friendsMenu),
		AddAccount("Add Account", MainWindow.fileMenu),
		AddGroup("Create Group", MainWindow.friendsMenu),
		AddToGroup("Add to Group", MainWindow.friendsMenu),
		RemoveFriend("Remove Friend", MainWindow.friendsMenu),
		ViewLog("View Log", MainWindow.friendsMenu),
		EditPrefs("Edit Preferences", MainWindow.prefsMenu),
		Help("Help", MainWindow.helpMenu),
		About("About", MainWindow.helpMenu);
		
		private final String title;
		private final String parentMenu;
		WindowType(String printout, String parent){
			this.title = printout;
			this.parentMenu = parent;
		}
		public String getPrintString(){
			return this.title;
		}
		public String getParentMenu(){
			return this.parentMenu;
		}
		
	}

	public static AbstractWindow createWindow(WindowType windowType){
		switch(windowType){
		case AddFriend:
			return new AddFriend(windowType.getPrintString(), MainWindow.getInstance());
		case AddAccount:
			return new AddAccount(windowType.getPrintString(), MainWindow.getInstance());
		case AddGroup:
			return new AddGroup(windowType.getPrintString(), MainWindow.getInstance());
		case AddToGroup:
			return new AddToGroup(windowType.getPrintString(), MainWindow.getInstance());
		case RemoveFriend:
			return new RemoveFriend(windowType.getPrintString(), MainWindow.getInstance());
		case ViewLog:
			return new ViewLog(windowType.getPrintString(), MainWindow.getInstance());
		case EditPrefs:
			return new EditPrefs(windowType.getPrintString(), MainWindow.getInstance());
		case Help:
			return new HelpWindow(windowType.getPrintString(), MainWindow.getInstance());
		case About:
			return new AboutWindow(windowType.getPrintString(), MainWindow.getInstance());
		case NewIM:
			return new EnterNameWindow(windowType.getPrintString(), MainWindow.getInstance());
		case OpenWB:
			if(MainWindow.whiteboard != null){
				MainWindow.createWhiteBoard();
			}
			return null;
		}
		throw new IllegalArgumentException("The window type " + windowType + " wasn't found");
	}
}
