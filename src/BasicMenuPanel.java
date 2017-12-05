import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * This is the basic panel that we will build on in the
 * hotelView class
 */
public class BasicMenuPanel extends JPanel {
	private GridBagConstraints grid;
	private hotelModel model;
	private hotelView viewManager;
	private ArrayList<JTextField> textField;
	private ArrayList<JTextArea> textArea;
	private ArrayList<JComboBox> comboBox;
	private ArrayList<JList> list;
	
	public BasicMenuPanel(hotelView viewManager) {
		this.viewManager = viewManager;
		model = viewManager.getHotelModel();
		textField = new ArrayList<JTextField>();
		textArea = new ArrayList<JTextArea>();
		comboBox = new ArrayList<JComboBox>();
		list = new ArrayList<JList>();
		this.setLayout(new GridBagLayout());
		grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.BOTH;
		grid.weightx = 1;
	}
	
	public void clearComponents() {
		for (JTextField text : textField)
			text.setText("");
		for (JTextArea area : textArea) {
			area.setText("");
			area.setCaretPosition(0);
		}
		for (JComboBox combo : comboBox)
			combo.setSelectedIndex(0);
		for (JList ls : list) 
			ls.setListData(new Object[1]);
	}
	
	public GridBagConstraints getConstraints() {
		return grid;
	}
	
	/**
	 * Add a component to the menu at location (x,y)
	 * @param c
	 * @param x
	 * @param y
	 */
	public void addComponent(JComponent c, int x, int y) {
		grid.gridx = x;
		grid.gridy = y;
		add(c, grid);
		addComponent(c);
	}
	
	public void addComponent(JComponent c) {
		if (c instanceof JTextField)
			textField.add((JTextField) c);
		if (c instanceof JTextArea)
			textArea.add((JTextArea) c);
		if (c instanceof JComboBox)
			comboBox.add((JComboBox) c);
		if (c instanceof JList)
			list.add((JList) c);
	}
	
	/**
	 * Add instructions to the menu
	 * @param instructions
	 */
	public void addInstructions(String instructions) {
		JLabel label = new JLabel(instructions);
		addComponent(label, 0, 0);
	}
	
	public void addNavigation(String text, int size, String destination, int x, int y) {
		JButton button = new JButton(text);
		button.setFont((new Font("Tahoma", Font.BOLD, size)));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearComponents();
				viewManager.switchPanel(destination);
			}
		});
		addComponent(button, x, y);
	}
	
	public void addSignOut(int size, String back, int x, int y) {
		JButton button = new JButton("Sign Out");
		button.setFont((new Font("Tahoma", Font.BOLD, size)));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setCurrentUser(null);
				clearComponents();
				viewManager.switchPanel(back);
			}
		});
		addComponent(button, x, y);
	}
	
	public JLabel addLabel(String text, int size, String align, Color fGround, Color bGround, int x, int y) {
		JLabel label = new JLabel(text);
		if(align.equals("left"))
			label.setHorizontalAlignment(SwingConstants.LEFT);
		else if(align.equals("center"))
			label.setHorizontalAlignment(SwingConstants.CENTER);
		else
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		
		if(fGround != null) 
			label.setForeground(fGround);
		
		if(bGround != null) {
			label.setBackground(bGround);
			label.setOpaque(true);
		}
		
		label.setFont((new Font("Tahoma", Font.BOLD, size)));
		addComponent(label, x, y);
		return label;		
	}

}
