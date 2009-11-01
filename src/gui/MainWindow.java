
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;

import gui.WindowFactory.WindowType;
import gui.preferenceWindows.*;

import core.network.*;
import core.im.*;

public class MainWindow {

	static JFrame window;
	static JPanel panel;
	static JList friendList;
	static JMenuBar menu;
	static ChatboardConnection conn;
	static ChatboardRoster roster;
	
	//final static String[] fileMenu = {"New Instant Message", "Open Whiteboard", "Add Account", "Exit" };
	//final static String[] friendsMenu = {"Add Friend", "Add Group", "View Log" };
	//final static String[] prefsMenu = {"Edit Preferences" };
	//final static String[] helpMenu = {"Help", "About"};
	public final static String fileMenu = "File", friendsMenu = "Friends", prefsMenu = "Preferences", helpMenu = "Help"; 
	
	public MainWindow(){
		conn = new ChatboardConnection("chatboard09@gmail.com", "fishonfire");
		XMPPConnection connection = conn.createConnection("talk.google.com", 5222, "gmail.com");
		roster = new ChatboardRoster(connection);
		roster.pullRoster();
		
		Vector<RosterEntry> online = roster.getOnline();
		Iterator<RosterEntry> iter = online.iterator();
		Vector<String> onlineFriends = new Vector<String>();
		while(iter.hasNext())
			onlineFriends.add(iter.next().getUser());
		
		
		friendList = new JList(onlineFriends);
		panel = new JPanel();
		BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(bl);
		panel.setPreferredSize(new Dimension(250,700));
		menu = setupMenu();
		window = new JFrame();
		window.setTitle("EECS393 Whiteboard Client SUPER EARLY ALPHA");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.setJMenuBar(menu);
		window.setVisible(true);
		
		ImageIcon fishOnFire = new ImageIcon("Docs/chatboard-quick.png");
		window.setIconImage(fishOnFire.getImage());
		panel.add(friendList);
		window.pack();
	}
	
	public static JFrame getInstance(){
		return window;
	}
	
	public static JPanel getContent(){
		return panel;
	}
	
	public static JMenuBar getMenu(){
		return menu;
	}
	
//TODO: remove this method once testing is done!
	public static void main(String[] args){
		MainWindow mw = new MainWindow();
		//can grab each component by name...
		System.out.println(mw.menu.getMenu(0).getMenuComponent(0).getName());

		
	}
	
	private JMenuBar setupMenu(){
		JMenuBar theMenu = new JMenuBar();
		/*JMenu fileMenu = addMenu("File", this.fileMenu);
		JMenu friendMenu = addMenu("Friends", this.friendsMenu);
		JMenu prefMenu = addMenu("Preferences", this.prefsMenu);
		JMenu helpMenu = addMenu("Help", this.helpMenu);*/
		
		JMenu file = addMenu(fileMenu);
		JMenu friends = addMenu(friendsMenu);
		JMenu pref = addMenu(prefsMenu);
		JMenu help = addMenu(helpMenu);

		
		theMenu.add(file);
		theMenu.add(friends);
		theMenu.add(pref);
		theMenu.add(help);
		
		return theMenu;
	}
	
	private JMenu addMenu(String name){
		/*ActionListener dlg = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriend test = new AddFriend("Add Friend", getInstance());				
		
			}
		};*/
		
		JMenu menu = new JMenu(name);
	/*	for(int i = 0; i < items.length; i++){
			JMenuItem theItem = new JMenuItem(items[i]);
			theItem.setName(items[i]);
			theItem.addActionListener(dlg);
			menu.add(theItem);
		}*/
		for(final WindowType window : WindowType.values()){
			if(window.getParentMenu().equals(name)){
				JMenuItem theItem = new JMenuItem(window.getPrintString());
				theItem.setName(window.getPrintString());
				theItem.addActionListener(new ActionListener(){
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						AbstractWindow test = WindowFactory.createWindow(window);
					}
					
				});
				menu.add(theItem);
			}
		}
		
		return menu;
		
	}
}
