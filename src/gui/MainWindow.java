
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.WindowFactory.WindowType;
import gui.preferenceWindows.*;

public class MainWindow {

	private static JFrame window;
	private static JPanel panel;
	static JMenuBar menu;
	
	//final static String[] fileMenu = {"New Instant Message", "Open Whiteboard", "Add Account", "Exit" };
	//final static String[] friendsMenu = {"Add Friend", "Add Group", "View Log" };
	//final static String[] prefsMenu = {"Edit Preferences" };
	//final static String[] helpMenu = {"Help", "About"};
	public final static String fileMenu = "File", friendsMenu = "Friends", prefsMenu = "Preferences", helpMenu = "Help"; 
	
	public MainWindow(){
		panel = new JPanel();
		BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(bl);
		panel.setPreferredSize(new Dimension(250,700));
		menu = setupMenu();
		window = new JFrame();
		ImageIcon chatboardImg = new ImageIcon("Images/chatboard-quick.png");
		window.setIconImage(chatboardImg.getImage());
		window.setTitle("EECS393 Whiteboard Client SUPER EARLY BETA");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(panel);
		window.setJMenuBar(menu);
		window.setVisible(true);
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
						WindowFactory.createWindow(window);
					}
					
				});
				menu.add(theItem);
			}
		}
		if(name.equals(MainWindow.fileMenu)){
			JMenuItem exit = new JMenuItem("Exit");
			exit.setName("Exit");
			exit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					MainWindow.getInstance().dispose();
					System.exit(0);
				}
			});
			menu.add(exit);
		}
		return menu;
		
	}
}
