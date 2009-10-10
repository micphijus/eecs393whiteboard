package gui;

import gui.preferenceWindows.*;

public class WindowFactory {
	/*
	 * 	final static String[] fileMenu = {"New Instant Message", "Open Whiteboard", "Add Account", "Exit" };
	final static String[] friendsMenu = {"Add Friend", "Add Group", "View Log" };
	final static String[] prefsMenu = {"Edit Preferences" };
	final static String[] helpMenu = {"Help", "About"};
	 * 
	 */
	/*TODO: Possibly add another parameter specifying whether or not to call an action listener method? No.. not necessary
	 		because can just have an empty method in each class, if it's empty then don't do it!*/
	public enum WindowType {
		AddFriend("Add Friend", MainWindow.friendsMenu),
		AddAccount("Add Account", MainWindow.fileMenu),
		AddGroup("Add Group", MainWindow.friendsMenu),
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
			return new AddFriend();
		case AddAccount:
			return new AddAccount();
		case AddGroup:
			return new AddGroup();
		case ViewLog:
			return new ViewLog();
		case EditPrefs:
			return new EditPrefs();
		case Help:
			return new HelpWindow();
		case About:
			return new AboutWindow();
		}
		throw new IllegalArgumentException("The window type" + windowType + "wasn't found");
	}
}
