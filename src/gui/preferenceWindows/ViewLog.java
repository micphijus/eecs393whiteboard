package gui.preferenceWindows;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.AbstractWindow;
import gui.MainWindow;
import gui.WindowFactory.WindowType;

public class ViewLog extends AbstractWindow {

	private JTextField logNameIn;
	private Window theParent;
	public ViewLog(String title, Window parent){
		super();
		theParent = parent;
		setTitle(title);
		setParent(parent);
		build();
		window.setPreferredSize(new Dimension(500, 200));
		window.pack();
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	//TODO: remove no-args constructor?
	public ViewLog(){
		
	}
	@Override
	public ActionListener applyListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildWindow() {
		JLabel title = new JLabel(WindowType.ViewLog.getPrintString());
		
		logNameIn = new JTextField();
		
		logNameIn.addKeyListener(enterAction());
		
		JPanel labelPanel = new JPanel(new GridLayout(2,1,4,4));
		JPanel valuePanel = new JPanel(new GridLayout(2,1,4,4));
		 
		labelPanel.add(new JLabel("Enter full Username to view logs of (include the @): "));
		valuePanel.add(logNameIn);
		
		 
		JPanel formPanel = new JPanel(new BorderLayout(5,5));
		formPanel.add(labelPanel, BorderLayout.WEST);
		formPanel.add(valuePanel, BorderLayout.CENTER);
		 
		JPanel theFinalOne = new JPanel(new BorderLayout(5,5));
		theFinalOne.add(formPanel, BorderLayout.NORTH);
		
		window.add(title);
		window.add(theFinalOne);
	}

	@Override
	public ActionListener okListener() {
		ActionListener okal = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File(MainWindow.logFolder,logNameIn.getText()));
				int option = fc.showOpenDialog(theParent);
				if(option == JFileChooser.APPROVE_OPTION){
					if(Desktop.isDesktopSupported()){
						Desktop dtop = Desktop.getDesktop();
						try {
							dtop.edit(fc.getSelectedFile());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(theParent, "Sorry, your OS is not currently supported.\n You must open the log files from your file browser.");
					}
				}
				window.dispose();
			}
		};
		return okal;
	}

}
