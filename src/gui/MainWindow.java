
package gui;

import gui.WindowFactory.WindowType;
import gui.implementations.ChatboardListCellRenderer;
import gui.preferenceWindows.LoginWindow;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jivesoftware.smack.XMPPConnection;

import core.abstraction.RosterModel;
import core.im.Buddy;
import core.im.ChatboardRoster;
import core.im.ControlListener;
import core.network.ChatboardConnection;
import core.whiteboard.WhiteboardPanel;

public class MainWindow implements ListDataListener {

	static JFrame window;
	static JPanel panel;
	static JList friendList;
	static JMenuBar menu;
	static ChatboardConnection conn;
	public static ChatboardRoster roster;
	static ControlListener theController;
	static HashMap<String, String> aliasBuddyMap;
	static JDialog whiteboard;
	public RosterModel theRosterModel;
	static String sn;
	static JList groupsList;
	//final static String[] fileMenu = {"New Instant Message", "Open Whiteboard", "Add Account", "Exit" };
	//final static String[] friendsMenu = {"Add Friend", "Add Group", "View Log" };
	//final static String[] prefsMenu = {"Edit Preferences" };
	//final static String[] helpMenu = {"Help", "About"};
	public final static String fileMenu = "File", friendsMenu = "Friends", prefsMenu = "Preferences", helpMenu = "Help"; 
	public final static String fishIcon = "Images/fish-on-fire.png";
	public final static String logFolder = System.getProperty("user.home") + File.separator + "Chatboard" + File.separator + "logs";
	
	public MainWindow(){
		aliasBuddyMap = new HashMap<String, String>();
		window = new JFrame();
		LoginWindow login = new LoginWindow("login", window);
		if(login.getConn() == null)
			System.exit(0);
		
		conn = login.getConn();
		sn = conn.getUserName();
		//conn.createConnection("talk.google.com", 5222, "gmail.com");
		XMPPConnection connection = conn.getConn();
		roster = new ChatboardRoster(connection);
		//roster.addListener(this);
		
		theRosterModel = new RosterModel(roster);
		theRosterModel.addListener(this);
		
		roster.pullRoster();
		
		theRosterModel.updateOnline(roster.online);
		
		getRoster(roster);
		
		ListCellRenderer cbRenderer = new ChatboardListCellRenderer();
		Vector<String> intermediary = toList(theRosterModel.onlineMap);
		groupsList = new JList(intermediary);
		//System.out.println(intermediary);
		groupsList.setOpaque(false);
		groupsList.setCellRenderer(cbRenderer);
		
		theController = new ControlListener(connection, conn.getUserName());
		theController.addDataListener();
		connection.getChatManager().addChatListener(theController);
		groupsList.addMouseListener(new MouseAdapter(){
		
			public void mouseReleased(MouseEvent e){
				if(e.getClickCount() == 2){
					//TODO: fix this temp call
					String sn = groupsList.getSelectedValue().toString();
					sn = aliasBuddyMap.get(sn);
					MessageDialog test = new MessageDialog(sn);
					test.addController(theController);
					theController.addDialog(test, sn);
				}
			}
		});		
	
		panel = new JPanel();
		JPanel leftAlign = new JPanel();
		BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
		BoxLayout bl2 = new BoxLayout(leftAlign, BoxLayout.X_AXIS);
		panel.setLayout(bl);
		leftAlign.setLayout(bl2);
		panel.setPreferredSize(new Dimension(250,700));
		menu = setupMenu();
		
		window.setTitle("EECS393 Whiteboard Client BETA");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.setJMenuBar(menu);
		window.setVisible(true);
		window.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				closeWindow();
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ImageIcon fishOnFire = new ImageIcon(fishIcon);
		window.setIconImage(fishOnFire.getImage());
		leftAlign.add(groupsList);
		leftAlign.add(Box.createHorizontalGlue());
		panel.add(leftAlign);
		panel.add(Box.createVerticalGlue());
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
	
	public static ControlListener getController(){
		return theController;
	}
	
//TODO: remove this method once testing is done!
	public static void main(String[] args){
		MainWindow mw = new MainWindow();
		//can grab each component by name...
		System.out.println(mw.menu.getMenu(0).getMenuComponent(0).getName());

		
	}
	
	public static void closeWindow()
	{
		conn.disconnect();
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
						//Add friend now works
						AbstractWindow test = WindowFactory.createWindow(window);

					}
					
				});
				menu.add(theItem);
			}
		}
		if(name.equals("File")){
			JMenuItem exit = new JMenuItem("Exit");
			exit.setName("Exit");
			exit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					MainWindow.getInstance().dispose();
					closeWindow();
				}
			});
			menu.add(exit);
		}
		
		return menu;
		
	}
	
	public void getRoster(ChatboardRoster r)
	{
		
		Vector<Buddy> online = r.getOnline();
		Iterator<Buddy> iter = online.iterator();
		while(iter.hasNext())
		{
			Buddy b = iter.next();
	
			if(b.alias == null)
			{
				if(b.userID.indexOf("@") != -1)
					b.alias = b.userID.substring(0, b.userID.indexOf("@"));
				else
					b.alias = b.userID;
			}
			aliasBuddyMap.put(b.alias, b.userID);
		}
		
	}
	
	public static WhiteboardPanel createWhiteBoard()
	{
		whiteboard = new JDialog(null, "Whiteboard", Dialog.ModalityType.MODELESS);
		
		BoxLayout bl = new BoxLayout(whiteboard.getContentPane(), BoxLayout.Y_AXIS);
		whiteboard.setLayout(bl);

		WhiteboardPanel wPanel = new WhiteboardPanel();
		wPanel.setPreferredSize(new Dimension(550, 550));
		whiteboard.add(wPanel);
		
		whiteboard.pack();
		whiteboard.setVisible(true);
		whiteboard.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		return wPanel;
	}
	public static ChatboardRoster getRoster()
	{
		return roster;
	}
	
	public Vector<String> toList(HashMap<String, Vector<Buddy>> theMap)
	{
		Vector<String> keys = new Vector<String>(theMap.keySet());
		Vector<String> finalList = new Vector<String>();
		for(String key : keys)
		{
			finalList.add("#" + key);
			Vector<Buddy> buddies = theMap.get(key);
			for(Buddy b : buddies)
				finalList.add(b.alias);
		}
		return finalList;
	}
	@Override
	public void contentsChanged(ListDataEvent e) {
		getRoster(roster);
		Vector <String> updatedRoster = toList(theRosterModel.onlineMap);
		System.out.println(theRosterModel.onlineMap);
		//friendList.setListData(updatedRoster);
		
		groupsList.setListData(updatedRoster);
		window.repaint();
		//theRosterModel.printAll();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}
}
