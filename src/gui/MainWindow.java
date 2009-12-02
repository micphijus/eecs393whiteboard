
package gui;

import gui.WindowFactory.WindowType;
import gui.implementations.ChatboardListCellRenderer;
import gui.preferenceWindows.LoginWindow;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	static Vector<JList> groupsList;
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
		conn = login.getConn();
		sn = conn.getUserName();
		//conn.createConnection("talk.google.com", 5222, "gmail.com");
		XMPPConnection connection = conn.getConn();
		roster = new ChatboardRoster(connection);
		roster.addListener(this);
		
		theRosterModel = new RosterModel(roster);
		theRosterModel.addListener(this);
		
		roster.pullRoster();
		
		
		
		ListCellRenderer cbRenderer = new ChatboardListCellRenderer();
		friendList = new JList(getRoster(roster));
		friendList.setOpaque(false);
		friendList.setCellRenderer(cbRenderer);
		//friendList.setAlignmentX(SwingConstants.LEFT);
		
		theController = new ControlListener(connection, conn.getUserName());
		theController.addDataListener();
		connection.getChatManager().addChatListener(theController);
		
		groupsList = new Vector<JList>();
		groupsList = toList(theRosterModel.onlineMap);
		for(JList list : groupsList){
			list.setOpaque(false);
			list.setCellRenderer(cbRenderer);
			final JList finList = list;
			list.addMouseListener(new MouseAdapter(){
				
				public void mouseReleased(MouseEvent e){
					if(e.getClickCount() == 2){
						//TODO: fix this temp call
						String sn = finList.getSelectedValue().toString();
						sn = theRosterModel.aliasMap.get(sn);
						MessageDialog test = new MessageDialog(sn);
						test.addController(theController);
						theController.addDialog(test, sn);
					}
				}
			});
		}
		friendList.addMouseListener(new MouseAdapter(){
		
			public void mouseReleased(MouseEvent e){
				if(e.getClickCount() == 2){
					//TODO: fix this temp call
					String sn = friendList.getSelectedValue().toString();
					sn = theRosterModel.aliasMap.get(sn);
					MessageDialog test = new MessageDialog(sn);
					test.addController(theController);
					theController.addDialog(test, sn);
				}
			}
		});
		
		//add the controller
		
	
		panel = new JPanel();
		JPanel leftAlign = new JPanel();
		JPanel flow = new JPanel();
		BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
		BoxLayout bl2 = new BoxLayout(leftAlign, BoxLayout.X_AXIS);
		BoxLayout bl3 = new BoxLayout(flow, BoxLayout.Y_AXIS);
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
		//leftAlign.add(friendList);
		for(JList list : groupsList){
			flow.add(list);
		}
		flow.add(Box.createVerticalGlue());
		leftAlign.add(flow);
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
	
	public Vector<String> getRoster(ChatboardRoster r)
	{
		
		Vector<Buddy> online = r.getOnline();
		Iterator<Buddy> iter = online.iterator();
		Vector<String> onlineFriends = new Vector<String>();
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
			onlineFriends.add(b.alias);
		}
		return onlineFriends;
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
	
	public Vector<JList> toList(HashMap<String, Vector<Buddy>> theMap)
	{
		Vector<JList> lists = new Vector<JList>();
		Vector<String> keys = new Vector<String>(theMap.keySet());
		for(String k : keys)
		{
			Vector<String> groupList = new Vector<String>();
			groupList.add(k);
			Vector<Buddy> groupBuddies = theMap.get(k);
			for(Buddy b : groupBuddies)
				groupList.add(b.alias);
			lists.add(new JList(groupList));
		}
		return lists;
	}
	@Override
	public void contentsChanged(ListDataEvent e) {
		Vector <String> updatedRoster = getRoster(roster);
		System.out.println(theRosterModel.onlineMap);
		friendList.setListData(updatedRoster);
		groupsList = toList(theRosterModel.onlineMap);
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
