import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Cell extends JLabel implements ListCellRenderer {
	
	public Cell() {
		setOpaque(true);
	}
	
	public Component getListCellRendererComponent(JList list, Object val, int index, boolean isSelected, boolean cellFocused) {
		String str = val.toString();
		setText(str);
		setBackground(Color.white);
		
		if(str.contains("Manager"))
			setForeground(Color.magenta);
		else if(str.contains("Customer"))
			setForeground(Color.blue);
		else if(str.contains("Room Attendant"))
			setForeground(Color.green);
        else if(str.contains("Single Room") || str.contains("False"))
        	setForeground(Color.red);
        else if(str.contains("Double Room") || str.contains("True"))
        	setForeground(Color.orange);
        else if(str.contains("Suite Room"))
        	setForeground(Color.cyan);
        else if(str.contains("Platnium Room"))
        	setForeground(Color.yellow);
        else 
        	setForeground(Color.BLUE);
		
		if(isSelected) 
			setBackground(Color.gray);
		return this;
	}
}
