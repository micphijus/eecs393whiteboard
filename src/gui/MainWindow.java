
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.preferenceWindows.*;

public class MainWindow {

	static JFrame window;
	static JPanel panel;
	static JMenuBar menu;
	
	final static String[] fileMenu = {"New Instant Message", "Open Whiteboard", "Add Account", "Exit" };
	final static String[] friendsMenu = {"Add Friend", "Add Group", "View Log" };
	final static String[] prefsMenu = {"Edit Preferences" };
	final static String[] helpMenu = {"Help", "About"};
	
	public MainWindow(){
		panel = new JPanel();
		BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(bl);
		panel.setPreferredSize(new Dimension(250,700));
		menu = setupMenu();
		window = new JFrame();
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
		JMenu fileMenu = addMenu("File", this.fileMenu);
		JMenu friendMenu = addMenu("Friends", this.friendsMenu);
		JMenu prefMenu = addMenu("Preferences", this.prefsMenu);
		JMenu helpMenu = addMenu("Help", this.helpMenu);

		
		theMenu.add(fileMenu);
		theMenu.add(friendMenu);
		theMenu.add(prefMenu);
		theMenu.add(helpMenu);
		
		return theMenu;
	}
	
	private JMenu addMenu(String name, String[] items){
		ActionListener dlg = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddFriend test = new AddFriend("Add Friend", getInstance());				
		
			}
		};
		
		JMenu menu = new JMenu(name);
		for(int i = 0; i < items.length; i++){
			JMenuItem theItem = new JMenuItem(items[i]);
			theItem.setName(items[i]);
			theItem.addActionListener(dlg);
			menu.add(theItem);
		}
		return menu;
		
	}
}
