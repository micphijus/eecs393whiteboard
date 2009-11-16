package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * need to set a title and parent for each window
 * @author Justin
 *
 */
public abstract class AbstractWindow {

	public static JDialog window;
	private String title;
	private Window parent;
	protected JButton ok, cancel, apply;
	private JPanel windowPanel, buttonPanel;
	private boolean includeApply = false;
	
	public AbstractWindow(){
		setup();
		buildWindow();
	}
	
	public void addToPanel(Component comp){
		windowPanel.add(comp);
	}
	
	public void setup(){
		if(parent != null && title != null){
			window = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);	
		}
		else{
			window = new JDialog(null, "", Dialog.ModalityType.APPLICATION_MODAL);
		}
		BoxLayout bl = new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS);
		window.setLayout(bl);

		windowPanel = new JPanel();
		BoxLayout bl2 = new BoxLayout(windowPanel, BoxLayout.Y_AXIS);
		windowPanel.setLayout(bl2);
	}
	
	public void build(){
		window.add(windowPanel);
		window.add(Box.createVerticalGlue());
		buttonPanel = new JPanel();
		buttonPanel.add(Box.createHorizontalGlue());
		ok = new JButton("OK");
		ok.addActionListener(okListener());
		BoxLayout bl2 = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		buttonPanel.setLayout(bl2);
		buttonPanel.add(ok);
		buttonPanel.add(Box.createHorizontalStrut(5));
		cancel = new JButton("Cancel");
		cancel.addActionListener(cancelListener());
		buttonPanel.add(cancel);
		if(includeApply){
			buttonPanel.add(Box.createHorizontalStrut(5));
			apply = new JButton("Apply");
			apply.addActionListener(applyListener());
			buttonPanel.add(apply);
		}
		buttonPanel.add(Box.createHorizontalGlue());
		window.add(buttonPanel);
		
	}
	
	public void setTitle(String theTitle){
		title = theTitle;
	}
	
	public void setParent(Window theWindow){
		parent = theWindow;
	}
	
	protected abstract void buildWindow();
	public abstract ActionListener okListener();
	public abstract ActionListener applyListener();
	public ActionListener cancelListener(){
		ActionListener al = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		};
		return al;
	}
}
