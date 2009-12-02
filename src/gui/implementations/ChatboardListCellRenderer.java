package gui.implementations;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import sun.swing.DefaultLookup;

@SuppressWarnings("serial")
public class ChatboardListCellRenderer extends DefaultListCellRenderer implements
		ListCellRenderer {

	private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1,1,1,1);
	private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1,1,1,1);

	public ChatboardListCellRenderer(){
		super();
	}
	
	//most of this code borrowed from Java 1.6 source... just want to make the list look nice dammit! (they make this way harder than it needs to be)
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
			boolean isGroup = value.toString().substring(0,1).equals("#");
			if(isGroup){
				value = value.toString().substring(1);
			}
			else{
				value = "   " + value.toString();
			}
			
			setComponentOrientation(list.getComponentOrientation());

		  	setHorizontalTextPosition(SwingConstants.LEFT);
		  
	        Color bg = null;
	        Color fg = null;
	        
	        JList.DropLocation dropLocation = list.getDropLocation();
	        if (dropLocation != null
	                && !dropLocation.isInsert()
	                && dropLocation.getIndex() == index) {

	            bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
	            fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

	            isSelected = true;
	        }

		if (isSelected) {
			setOpaque(true);
	        setBackground(bg == null ? list.getSelectionBackground() : bg);
		    setForeground(fg == null ? list.getSelectionForeground() : fg);
		}
		else {
			setOpaque(false);
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
	        
		if (value instanceof Icon) {
		    setIcon((Icon)value);
		    setText("");
		}
		else {
		    setIcon(null);
		    setText((value == null) ? "" : value.toString());
		}

		setEnabled(list.isEnabled());
		setFont(list.getFont());

	        Border border = null;
	        if (cellHasFocus) {
	            if (isSelected) {
	                border = DefaultLookup.getBorder(this, ui, "List.focusSelectedCellHighlightBorder");
	            }
	            if (border == null) {
	                border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
	            }
	        }
	        else {
	            border = getNoFocusBorder();
	        }
		setBorder(border);
		return this;
	}

	//need this private method for the above class, borrowed from the Java 1.6 source
	private Border getNoFocusBorder(){
        Border border = DefaultLookup.getBorder(this, ui, "List.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else {
            if (border != null &&
                    (noFocusBorder == null ||
                    noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
                return border;
            }
            return noFocusBorder;
        }
	}
	
}
