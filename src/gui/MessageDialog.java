package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


import core.abstraction.Controller;
import core.im.IM;
import core.whiteboard.WhiteboardPanel;

public class MessageDialog implements ListDataListener{

	String userName;
	JDialog conversation;
	JPanel messagePanel;
	JTextArea inputArea;
	//continued conversation from another messageDialog
	String contConvo = null;
	Vector<Controller>listeners;
	protected JTextPane convoWindow;
	protected Dimension defaultSize = new Dimension(600,400);
	Queue <String> commandQueue;
	
	public MessageDialog(){
		/*Do nothing, because we don't want WhiteboardDialog to use this */
	}
	
	public MessageDialog(String dialogName){
		userName = dialogName;
		conversation = new JDialog(null, dialogName, Dialog.ModalityType.MODELESS);
		messagePanel = new JPanel();
		setupGUI();
		conversation.pack();
		conversation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		conversation.setVisible(true);
		listeners = new Vector<Controller>();
	}
	
	public MessageDialog(String dialogName, String oldMessage, Vector<Controller> oldListeners){
		userName = dialogName;
		contConvo = oldMessage;
		messagePanel = new JPanel();
		conversation = new JDialog(null, dialogName, Dialog.ModalityType.MODELESS);
		setupGUI();
		conversation.pack();
		conversation.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		conversation.setVisible(true);
		listeners = new Vector<Controller>();
		for(Controller c : oldListeners){
			listeners.add(c);
			c.removeDialog(userName);
			c.removeWhiteboard(userName);
			c.addDialog(this, userName);
		}
	}
	/*public MessageDialog(JDialog dialog, String oldMessage, Vector<Controller> oldListeners)
	{
		userName = dialog.getName();
		contConvo = oldMessage;
		conversation = dialog;
		conversation.setVisible(true);
		listeners = oldListeners;
		for(Controller c : oldListeners)
		{
			c.removeDialog(userName);
			c.removeWhiteboard(userName);
			c.addDialog(this, userName);
		}
	}*/
	public void addController(Controller controller)
	{
		listeners.add(controller);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageDialog test = new MessageDialog();
	}

	private void setupGUI(){
	//	BoxLayout bl = new BoxLayout(conversation.getContentPane(), BoxLayout.Y_AXIS);
		BoxLayout bl2 = new BoxLayout(messagePanel, BoxLayout.Y_AXIS);
	//	conversation.setLayout(bl); 
		messagePanel.setLayout(bl2);
		conversation.addWindowListener(new WindowListener() {
			
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

				startLogs();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0; i < listeners.size(); i++)
				{
					
					listeners.get(i).removeDialog(userName);
				}
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		//TODO: for adding a menu later
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(new JMenu("One"));
		menuBar.add(new JMenu("Two"));
		menuBar.add(new JMenu("Three"));
		conversation.setJMenuBar(menuBar);
		
		JPanel topPanel = new JPanel();
		JPanel separatorPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	//	convoPanel.setPreferredSize(new Dimension(300, 200));
		topPanel.setLayout(new BorderLayout(0,5));
		bottomPanel.setLayout(new BorderLayout(0,5));
//		scrollWindow.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollWindow.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		convoWindow = new JTextPane();
		convoWindow.setEditable(false);
		convoWindow.setPreferredSize(new Dimension(300, 200));
		if(contConvo != null){
			convoWindow.setText(contConvo);
		}

		JScrollPane scrollWindow = new JScrollPane(convoWindow);
		//TODO: Will need a method that keeps a large string of the conversation and inserts usernames and shit
		
		scrollWindow.getViewport().add(convoWindow);
		topPanel.add(scrollWindow);
		//conversation.add(convoPanel);
		inputArea = new JTextArea();
		inputArea.setPreferredSize(new Dimension(300, 150));
		inputArea.addKeyListener(new KeyListener(){
		
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
		
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					if(!arg0.isShiftDown()){
						sendMessage(inputArea);
						arg0.consume();
					}
					else{
						inputArea.append("\n");
					}
				}
				
			}
		});
		
		bottomPanel.add(inputArea);
		messagePanel.add(topPanel);
		messagePanel.add(separatorPanel);
		messagePanel.add(bottomPanel);
		messagePanel.add(Box.createHorizontalStrut(3));
		
		JButton sendMsgButton = new JButton("Send Message");
		sendMsgButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMessage(inputArea);				
			}
		});
	
		JButton openWbButton = new JButton("Whiteboard >");
		openWbButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
		            MessageDialog wb = new WhiteboardDialog(userName, convoWindow.getText(), messagePanel, inputArea, convoWindow, listeners);
		            wb.listeners = listeners;
		            //will remove the dialog
		            for(int i = 0; i < listeners.size(); i++)
					{
						listeners.get(i).removeDialog(userName);
						listeners.get(i).addDialog(wb, userName);
						listeners.get(i).addWhiteboard(userName, (WhiteboardDialog)wb);
					}
		            conversation.setVisible(false);
		            wb.setPreviousPanel(conversation);
		            wb.commandQueue = commandQueue;
			}
		});
		
		buttonPanel.add(sendMsgButton);
		buttonPanel.add(openWbButton);
		//conversation.add(Box.createVerticalGlue());
		
		conversation.add(messagePanel, BorderLayout.EAST);
		conversation.add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//TODO: currently just clears the box... eventually make it do other stuff
	protected void sendMessage(JTextArea inputField){
		String message = inputField.getText();
		for(int i = 0; i < listeners.size(); i++)
		{
			boolean b = listeners.get(i).sendMessage(userName, message);
			if(b == false)
				System.out.println("Error, could not send");
			else
				System.out.println(message);
		}
		
		inputField.setText("");
		//TODO:fix the user's username
		convoWindow.setText(convoWindow.getText() + "\n" + "me!" + ":  " + message);
	}
	
	public void receiveMessage(IM im)
	{
		//if needed we can map im.from to client aliases
		String newSentence = im.from + ":  "+ im.message;
		convoWindow.setText(convoWindow.getText() + "\n" + newSentence);
		System.out.println(im.message);
	}
	
	protected void startLogs(){
		Date currentDate = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat();
		dateForm.applyPattern("yyyy_MM_dd_HH_mm");
		String dateName = dateForm.format(currentDate);
		File logFolder = new File(MainWindow.logFolder + File.separator + userName);
		logFolder.mkdirs();
		File logFile = new File(logFolder.getAbsolutePath(), dateName+".txt");
		try {
			logFile.createNewFile();
			PrintWriter print = new PrintWriter(logFile);
			String conversation = convoWindow.getText();
			String[] lines = conversation.split("\\n");
			for(String line : lines){
				print.write(line);
				print.println();
			}
			
			print.flush();
			print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setPreviousPanel(JDialog panel)
	{
		
	}
	public void applyQueue(Queue<String> q)
	{
		if(commandQueue == null)
			commandQueue = q;
		else
			commandQueue.addAll(q);
	}
}
