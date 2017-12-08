import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

public class hotelView {
	
	private static final JComponent idField = null;
	private hotelModel model;
	final hotelView view = this;
	private JPanel pane;
	private CardLayout card;
	private JFrame frame;
	private JList allUsers;
	private SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
	
	private JTextField firstName;
	private JTextField lastName;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField passwordConfirm;
	private JComboBox age;
	private JComboBox gender;
	private List<String> ageArray;
	private List<String> genderArray;
	
	public hotelView(hotelModel model) {
		this.model = model;
		frame = new JFrame("Hotel Reservation System");
		pane = new JPanel(card = new CardLayout());

		// add panels to the card layout
		pane.add(getLoginPanel(), "Login");
		pane.add(getRegisterPanel(), "Register");
		pane.add(getWelcomePanel("Customer"), "Customer");
		pane.add(getWelcomePanel("Manager"), "Manager");
		pane.add(getWelcomePanel("Room Attendant"), "Room Attendant");

		// customer panels
		pane.add(getMakeReservationPanel(), "Book");
		pane.add(getReceiptPanel(), "Receipt");
		pane.add(getViewCancelPanel(), "View/Cancel");
		pane.add(getOrderRoomServicePanel(), "Order");
		pane.add(getFileComplaintPanel(), "File Complaint");

		// employee panels
		pane.add(getReservationsPanel(), "Reservations");
		pane.add(getRoomServicePanel(), "Tasks");
		pane.add(getStatisticsPanel(), "Statistics");
		pane.add(getArchivePanel(), "Archive");
		pane.add(getUsersPanel(), "Users");
		pane.add(getAddEmployeePanel(), "AddEmployee");
		pane.add(getSettingsPanel(), "Setting");

		pane.add(getComplaintsPanel(), "Complaints");		
		pane.add(getViewRoomServicePanel(), "View Room Service");

		frame.add(pane); // add the panel with card layout to the frame
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public hotelModel getHotelModel() {
		return model;
	}
	
	public void switchPanel(String name) {
		if(name.equals("Login"))
			frame.setSize(700, 500);
		else
			frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		card.show(pane, name);
	}
	
	private JPanel getSettingsPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Account Settings", 24, "center", Color.white, Color.black, 0, 0);
		grid.insets = new Insets(5, 25, 5, 25);
		grid.weightx = 0;
		grid.gridwidth = 1;
		
		panel.addLabel("First Name: ", 20, "left", null, null, 0, 1);
		panel.addLabel("Last Name: ", 20, "left", null, null, 0, 2);
		panel.addLabel("Password: ", 20, "left", null, null, 0, 3);
		panel.addLabel("Comfirm Password: ", 20, "left", null, null, 0, 4);
		panel.addLabel("Age: ", 20, "left", null, null, 0, 5);
		panel.addLabel("Gender: ", 20, "left", null, null, 0, 6);
		
		JButton goBackButton = new JButton("Back");
		panel.addComponent(goBackButton, 0, 7);
		
		panel.setFont(new Font("Tahoma", Font.BOLD, 16));
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(model.getCurrentRole());
			}
		});
		
		grid.weightx = 1;
		grid.gridwidth = 3;
		firstName = new JTextField();
		panel.addComponent(firstName, 1, 1);
		
		lastName = new JTextField();
		panel.addComponent(lastName, 1, 2);
		
		password = new JPasswordField();
		panel.addComponent(password, 1, 3);
		
		passwordConfirm = new JPasswordField();
		panel.addComponent(passwordConfirm, 1, 4);
		
		ageArray = new ArrayList<String>();
		ageArray.add("Select Age");
		for(int i = 18; i < 100; i++) {
			ageArray.add(String.valueOf(i));
		}
		age = new JComboBox(ageArray.toArray());
		panel.addComponent(age, 1, 5);
		
		genderArray = new ArrayList<String>();
		genderArray.add("Select Gender");
		genderArray.add("F");
		genderArray.add("M");
		gender = new JComboBox(genderArray.toArray());
		panel.addComponent(gender, 1, 6);
		
		JButton registerButton = new JButton("Change");
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "";

				String first = firstName.getText();
				if (first.isEmpty()) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot be empty";
				}
				else if(first.length() > 15) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters";
				}
				
				String last = lastName.getText();
				if (last.isEmpty()) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot be empty";
				}
				else if(last.length() > 15) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters";
				}

				String pass = new String(password.getPassword()); 
				String passConfirm = new String(passwordConfirm.getPassword());
				if (pass.length() > 20 ) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters";
				}
				else if(pass.isEmpty()) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot be empty";
				}
				else if(!pass.equals(passConfirm)) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Passwords do not match";
				}

				Integer userAge = null;
				try {
					userAge = Integer.parseInt((String)age.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected";
				}

				String gen = (String)gender.getSelectedItem();
				int genIndex = 0;
				if (gen.equals("F")) {
					genIndex = 1;
					gen = "F";
				}					
				else if (gen.equals("M")) {
					gen = "M";
					genIndex = 2;
				}
				else {
					validEntry = false;
					errors += "Gender must be selected";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.changeAccount(pass, first, last, userAge, gen)){
						User user = model.getAccountInfo(model.getCurrentUser().getUsername());
						firstName.setText(user.getFirstName());						
						lastName.setText(user.getLastName());
						password.setText(user.getPassword());
						passwordConfirm.setText(user.getPassword());
						age.setSelectedItem(ageArray.get(user.getAge() - 17));
						gender.setSelectedItem(genderArray.get(genIndex));
						
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerButton, 1, 7);		
		return panel;		
	}
	
	private JPanel getAddEmployeePanel(){
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Add Employee", 24, "center", Color.white, Color.black, 0, 0);
		
		grid.weightx = 0;
		grid.insets = new Insets(5,25,5,25);
		grid.gridwidth = 1;
		panel.addLabel("Account type:", 20, "left", null, null, 0, 1);
		panel.addLabel("First name:", 20, "left", null, null, 0, 2);
		panel.addLabel("Last name:", 20, "left", null, null, 0, 3);
		panel.addLabel("Username:", 20, "left", null, null, 0, 4);
		panel.addLabel("Password:", 20, "left", null, null, 0, 5);
		panel.addLabel("Confirm Password:", 20, "left", null, null, 0, 6);
		panel.addLabel("Age:", 20, "left", null, null, 0, 7);
		panel.addLabel("Gender:", 20, "left", null, null, 0, 8);
		panel.addNavigation("Back", 16, "Users", 0, 11);
		
		grid.weightx = 1;
		grid.gridwidth = 3;
		
		List<String> accountType = new ArrayList<String>();
		accountType.add("Select Role");
		accountType.add("Manager");
		accountType.add("Room Attendant");
	
		final JComboBox accountTypeBox = new JComboBox(accountType.toArray());
		panel.addComponent(accountTypeBox, 1, 1);
		
		final JTextField firstName = new JTextField();
		panel.addComponent(firstName, 1, 2);

		final JTextField lastName = new JTextField();
		panel.addComponent(lastName, 1, 3);

		final JTextField username = new JTextField();
		panel.addComponent(username, 1, 4);

		final JPasswordField password = new JPasswordField();
		panel.addComponent(password, 1, 5);
		
		final JPasswordField passwordConfirm = new JPasswordField();
		panel.addComponent(passwordConfirm, 1, 6);

		List<String> age = new ArrayList<String>();
		age.add("Select Age");
		for (int i = 18; i < 100; ++i)
			age.add(String.valueOf(i));
		final JComboBox ageBox = new JComboBox(age.toArray());
		panel.addComponent(ageBox, 1, 7);

		List<String> gender = new ArrayList<String>();
		gender.add("Select Gender");
		gender.add("Female");
		gender.add("Male");
		final JComboBox genderBox = new JComboBox(gender.toArray());
		panel.addComponent(genderBox, 1, 8);

		JButton registerButton = new JButton("Add");
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "";				
				String type = (String)accountTypeBox.getSelectedItem();
				if (type.equals("Manager"))
					type = "Manager";
				else if (type.equals("Room Attendant"))
					type = "Room Attendant";
				else {
					validEntry = false;
					errors += "Account type must be selected";
				}				

				String first = firstName.getText();
				if (first.isEmpty()) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot be empty";
				}
				else if(first.length() > 15) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters";
				}

				String last = lastName.getText();
				if (last.isEmpty()) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot be empty";
				}
				else if(last.length() > 15) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters";
				}

				String login = username.getText();
				if(login.isEmpty()){
					username.setText("");
					validEntry = false;
					errors += "Username cannot be empty";
				}
				else if (login.length() > 12 ) {
					username.setText("");
					validEntry = false;
					errors += "Username cannot exceed 12 characters<br>";
				}
				else if(model.checkUser(login)) {
					username.setText("");
					validEntry = false;
					errors += "Username has been taken";
				}

				String pass = new String(password.getPassword()); 
				String passConfirm = new String(passwordConfirm.getPassword());
				if (pass.length() > 20 ) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters";
				}
				else if(pass.isEmpty()) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot be empty";
				}
				else if(!pass.equals(passConfirm)){
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Passwords do not match";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageBox.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected";
				}

				String gen = (String)genderBox.getSelectedItem();
				if (gen.equals("Female"))
					gen = "F";
				else if (gen.equals("Male"))
					gen = "M";
				else {
					validEntry = false;
					errors += "Gender must be selected";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.addAccount(login, pass, first, last, type, age, gen)){
						ArrayList<Account> users = model.getAllUsers(null);
						allUsers.setListData(users.toArray());
						view.switchPanel("Users");
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerButton, 1, 9);		
		return panel;
	}
	
	private JPanel getArchivePanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.gridwidth = 2;
		grid.ipady = 30;
		panel.addLabel("Archive", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;

		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("Enter a date. Reservations, room service requests, and "
				+ "complaints will be archived from this date.", 14, "left", null, null, 0, 1);

		grid.gridwidth = 1;
		grid.weighty = 1;
		panel.addLabel("Date to archive from (MM/DD/YYYY):", 20, "left", null, null, 0, 2);
		grid.gridwidth = 1;
		final JTextField date = new JTextField();
		panel.addComponent(date, 1, 2);

		grid.gridwidth = 2;
		JButton archiveButton = new JButton("Archive");
		archiveButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		archiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String archiveDate = date.getText();
				GregorianCalendar cal;
				if (archiveDate.length() == 10) {
					cal = isValidDateFormat(archiveDate);
					if (cal != null) {
						int response = JOptionPane.showConfirmDialog(
								new JFrame(), "Are you sure you want to archive?",
								"Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.NO_OPTION) ;
						if (response == JOptionPane.YES_OPTION) {
							if(!model.archive(cal.getTime())) 
								JOptionPane.showMessageDialog(new JFrame(), 
										"An unexpected error has occurred. Please contact your system admin.", "Error", 
										JOptionPane.ERROR_MESSAGE);	
							else {
								JOptionPane.showMessageDialog(new JFrame(), "Archive Successful", "Result", JOptionPane.DEFAULT_OPTION);
								panel.clearComponents();
								switchPanel("Manager");
							}
						}
					}
					else 
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Invalid format.", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(archiveButton, 0, 3);
		panel.addNavigation("Back to main menu", 16, "Manager", 0, 4);
		return panel;
	}

	
	private JPanel getLoginPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.gridwidth = 2;
		grid.ipady = 15;
		panel.addLabel("Welcome!", 32, "center", Color.white, Color.black, 0, 0);
		
		grid.insets = new Insets(10,15,5,15);
		grid.weightx = 1;
		grid.gridheight = 1;
		grid.gridwidth = 1;
		grid.ipady = 0;
		panel.addLabel("Username", 20, "center", null, null, 0, 2);
		final JTextField usernameField = new JTextField();
		usernameField.setMargin(new Insets(5,5,5,5));
		panel.addComponent(usernameField, 1, 2);
		
		grid.insets = new Insets(5,15,5,15);
		panel.addLabel("Password", 20, "center", null, null, 0, 3);
		//final JTextField passwordField = new JTextField();
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setMargin(new Insets(5,5,5,5));
		panel.addComponent(passwordField, 1, 3);
		
		grid.gridwidth = 2;
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String( passwordField.getPassword());
				if (username.length() > 12) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: Entered user username is invalid.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
				else if (!model.checkUser(username)) {	
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Username does not exist in the system.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
				else if (!model.checkUserPassword(username, password)) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Password is incorrect.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
				else {
					panel.clearComponents();
					model.setCurrentUser(username);
					view.switchPanel(model.getCurrentRole());
				}
			}
		});
		panel.addComponent(loginButton, 0, 4);

		grid.gridwidth = 1;
		panel.addNavigation("Register", 16, "Register", 0, 5);

		return panel;
	}
	
	private JPanel getRegisterPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Registration", 24, "center", Color.white, Color.black, 0, 0);
		
		grid.weightx = 0;
		grid.insets = new Insets(5,25,5,25);
		grid.gridwidth = 1;
		panel.addLabel("First name:", 20, "left", null, null, 0, 1);
		panel.addLabel("Last name:", 20, "left", null, null, 0, 2);
		panel.addLabel("Username:", 20, "left", null, null, 0, 3);
		panel.addLabel("Password:", 20, "left", null, null, 0, 4);
		panel.addLabel("Confirm Password:", 20, "left", null, null, 0, 5);
		panel.addLabel("Age:", 20, "left", null, null, 0, 6);
		panel.addLabel("Gender:", 20, "left", null, null, 0, 7);
		panel.addNavigation("Back", 16, "Login", 0, 8);
		
		grid.weightx = 1;
		grid.gridwidth = 3;
		
		final JTextField firstName = new JTextField();
		panel.addComponent(firstName, 1, 1);

		final JTextField lastName = new JTextField();
		panel.addComponent(lastName, 1, 2);

		final JTextField username = new JTextField();
		panel.addComponent(username, 1, 3);

		final JPasswordField password = new JPasswordField();
		panel.addComponent(password, 1, 4);
		
		final JPasswordField passwordConfirm = new JPasswordField();
		panel.addComponent(passwordConfirm, 1, 5);

		List<String> age = new ArrayList<String>();
		age.add("Select Age");
		for (int i = 18; i < 100; ++i)
			age.add(String.valueOf(i));
		final JComboBox ageBox = new JComboBox(age.toArray());
		panel.addComponent(ageBox, 1, 6);

		List<String> gender = new ArrayList<String>();
		gender.add("Select Gender");
		gender.add("Female");
		gender.add("Male");
		final JComboBox genderBox = new JComboBox(gender.toArray());
		panel.addComponent(genderBox, 1, 7);

		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "";

				String first = firstName.getText();
				if (first.isEmpty()) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot be empty";
				}
				else if(first.length() > 15) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters";
				}

				String last = lastName.getText();
				if (last.isEmpty()) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot be empty";
				}
				else if(last.length() > 15) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters";
				}

				String login = username.getText();
				if(login.isEmpty()) {
					username.setText("");
					validEntry = false;
					errors += "Username cannot be empty";
				}
				else if (login.length() > 12 ) {
					username.setText("");
					validEntry = false;
					errors += "Username cannot exceed 12 characters";
				}
				else if(model.checkUser(login)) {
					username.setText("");
					validEntry = false;
					errors += "Username has been taken";
				}

				String pass = new String(password.getPassword()); 
				String passConfirm = new String(passwordConfirm.getPassword());
				if (pass.length() > 20 ) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters";
				}
				else if(pass.isEmpty()) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Password cannot be empty";
				}
				else if(!pass.equals(passConfirm)) {
					password.setText("");
					passwordConfirm.setText("");
					validEntry = false;
					errors += "Passwords do not match";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageBox.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected";
				}

				String gen = (String)genderBox.getSelectedItem();
				if (gen.equals("Female"))
					gen = "F";
				else if (gen.equals("Male"))
					gen = "M";
				else {
					validEntry = false;
					errors += "Gender must be selected";
				}

				if(validEntry) {
					panel.clearComponents();
					if (model.addAccount(login, pass, first, last, "Customer", age, gen)){
						model.setCurrentUser(login);
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerButton, 1, 8);
		return panel;
	}

	private JPanel getWelcomePanel(String role) {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.insets = new Insets(10,10,10,10);

		final JLabel profile = new JLabel();
		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				if (model.getCurrentUser() != null) {
					Account user = model.getCurrentUser();
					profile.setText("Username: " + user.getUsername() 
					+ "  Name: " + user.getFirstName() + " " + user.getLastName()
					+ "  Role: " + user.getUserRole());
				}
			}
		});
		panel.addComponent(profile, 0, 0);
		panel.addSignOut(16, "Login", 1, 0);

		grid.gridwidth = 2;
		JButton settingButton = new JButton("Setting");
		settingButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		settingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = model.getAccountInfo(model.getCurrentUser().getUsername());
				firstName.setText(user.getFirstName());
				lastName.setText(user.getLastName());
				password.setText(user.getPassword());
				passwordConfirm.setText(user.getPassword());
				age.setSelectedItem(ageArray.get(user.getAge()-17));
				int index = (user.getGender() == "F") ? 1 : 2;
				gender.setSelectedItem(genderArray.get(index));
				switchPanel("Setting");				
			}
		});		
		
		if (role.equalsIgnoreCase("manager")) {
			panel.addNavigation("Reservations", 16, "Reservations", 0, 1);
			panel.addNavigation("Complaints", 16, "Complaints", 0, 2); 
			panel.addNavigation("Manage System User", 16, "Users", 0, 3);
			panel.addNavigation("Statistics", 16, "Statistics", 0, 4);
			panel.addNavigation("Archive Database", 16, "Archive", 0, 5);
			panel.addComponent(settingButton, 0, 6);
		}
		else if (role.equalsIgnoreCase("customer")) {
			panel.addNavigation("Book a reservation", 16, "Book", 0, 1);
			panel.addNavigation("View/Cancel Reservations", 16, "View/Cancel", 0, 2);
			panel.addNavigation("Room Service Request", 16, "Order", 0, 3);
			panel.addNavigation("File Complaint", 16, "File Complaint", 0, 4);
			JButton rating = new JButton("Rating");
			rating.setFont(new Font("Tahoma", Font.BOLD, 16));
			rating.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					String[] rate = {"Select a rating", "1", "2", "3", "4" , "5"};
					JComboBox selectRate = new JComboBox(rate);
					int result = JOptionPane.showConfirmDialog(null, selectRate, "Rating of our service", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						String r = (String)selectRate.getSelectedItem();
						if(r.equals("Select a rating")) {
							JOptionPane.showMessageDialog(new JFrame(), "Please select a rating.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							int rt = Integer.parseInt(r);
							String username = model.getCurrentUser().getUsername();						
							model.updateRating(username, rt);								
						}
	                }					
				}				
			});
			panel.addComponent(rating, 0, 5);
			panel.addComponent(settingButton, 0, 6);
		}
		else if (role.equalsIgnoreCase("room attendant")) {
			panel.addNavigation("Handle room request", 16, "Tasks", 0, 1);
			panel.addComponent(settingButton, 0, 6);
		}
		return panel;
	}

	private JPanel getMakeReservationPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.gridwidth = 2;
		grid.ipady = 30;
		panel.addLabel("Reserve a Room", 24, "center", Color.white, Color.black, 0, 0);

		grid.insets = new Insets(10, 10, 10, 10);
		grid.gridwidth = 1;
		grid.ipady = 0;
		panel.addLabel("Check-in (MM/DD/YYYY):", 20, "left", null, null, 0, 1);
		panel.addLabel("Check-out (MM/DD/YYYY):", 20, "left", null, null, 1, 1);

		grid.gridwidth = 1;
		final JTextField checkIn = new JTextField();
		panel.addComponent(checkIn, 0, 2);

		final JTextField checkOut = new JTextField();
		panel.addComponent(checkOut, 1, 2);

		grid.gridwidth = 2;
		grid.weighty = 1;
		final JList list = new JList();
		list.setCellRenderer(new Cell());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(12);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 4);

		grid.weighty = 0;

		JButton searchButton = new JButton("Search for rooms");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setListData(new String[0]);
				String in = checkIn.getText();
				String out = checkOut.getText();
				GregorianCalendar inCal;
				GregorianCalendar outCal;
				if (in.length() == 10 && out.length() == 10) {
					inCal = isValidDateFormat(in);
					outCal = isValidDateFormat(out);
					if (inCal != null && outCal != null) {
						if (inCal.before(hotelModel.TODAY) || outCal.before(hotelModel.TODAY))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in and check-out dates must prior to today.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (outCal.before(inCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in date must be before check-out date.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (inCal.equals(outCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in and check-out cannot be the same day.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							if (model.getAvailableRooms(in, out) != null)
								list.setListData(model.getAvailableRooms(in, out).toArray());
						}
					}
					else
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Invalid formats.", "Error",
								JOptionPane.ERROR_MESSAGE);
				} else
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: Invalid date format.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(searchButton, 0, 3);

		grid.gridwidth = 2;
		JButton confirmButton = new JButton("Book");
		confirmButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Room room = (Room)list.getSelectedValue();
				if (room != null) {
					if (model.addReservation(room.getRoomId(), checkIn.getText(), checkOut.getText())) {
						int response = JOptionPane.showConfirmDialog(
								new JFrame(), "Do you want to make this reservation?",
										"Confirmation", JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {
							list.setListData(new String[0]);
							panel.clearComponents();
							switchPanel("Receipt");
						}
					}
				}
				else
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: No room has been selected.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(confirmButton, 0, 5);

		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getReservations().isEmpty()) {
					panel.clearComponents();
					list.setListData(new String[0]);
					view.switchPanel("Customer");
				} 
				else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: You must complete your transaction.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(backButton, 0, 6);
		return panel;
	}

	private JPanel getReceiptPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.gridwidth = 1;
		grid.ipady = 30;
		panel.addLabel("Receipt", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.weighty = 1;
		grid.insets = new Insets(10,10,10,10);
		final JTextArea receipt = new JTextArea();
		receipt.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(receipt,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) receipt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 2);
		panel.addComponent(receipt);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!model.getReservations().isEmpty()) {
					Account user = model.getCurrentUser();
					String text = "Username: " + user.getUsername() + "\nName: " + user.getFirstName() 
					+ " " + user.getLastName() + "\nReservations made: " + model.getReservations().size();

					double cost = 0;
					int i = 1;
					for (Reservation r : model.getReservations()) {
						text += String.format("\n\nReservation # %d\n%s", i, r.toString());
						cost += r.getTotalCost();
						i++;
					}

					text += String.format("\n\nTotal: $%.2f", cost);
					receipt.setText(text);
				}
			};
		});

		grid.weighty = 0;
		
		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearReservations();
				panel.clearComponents();
				view.switchPanel("Customer");
			}
		});
		panel.addComponent(backButton, 0, 3);
		return panel;
	}

	private JPanel getViewCancelPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.gridwidth = 1;
		grid.ipady = 30;
		panel.addLabel("View or Cancel a Reservation", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>Below are all your reservations.<br>"
		+ "To cancel a reservation, select the one you with the cancel. Press cancel.<br>"
				+ "If the list is empty, then you do not have any reservations</html>" , 13, "left", 
				null, null, 0, 1);

		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		grid.weighty = 1;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ArrayList<Reservation> notCanceled = new ArrayList<Reservation>();
				if (model.getCurrentUser() != null) {
					for (Reservation r : model.getCurrentUser().getReservation())
						if (!r.getCancelled())
							notCanceled.add(r);

					list.setListData(notCanceled.toArray());
				}
				else list.setListData(new Reservation[0]);
			}
		});

		grid.weighty = 0;
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to cancel this reservation?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						if (!model.cancelReservation((Reservation) list.getSelectedValue()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"An unexpected error has occurred. Please contact your system admin.", "Error", 
									JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
		});
		panel.addComponent(cancelButton, 0, 3);

		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Customer");
			}
		});
		panel.addComponent(backButton, 0, 4);
		return panel;
	}

	private JPanel getReservationsPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.gridwidth = 4;
		grid.ipady = 30;
		grid.weighty = 0;
		panel.addLabel("Reservations", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("Enter a min and max and sort by room or customer.", 20, "left", 
				null, null, 0, 1);

		grid.gridwidth = 1;
		panel.addLabel("Min cost (optional)", 20, "left", null, null, 0, 2);
		final JTextField minTF = new JTextField();
		panel.addComponent(minTF, 1, 2);

		panel.addLabel("Max cost (optional)", 20, "left", null, null, 2, 2);
		final JTextField maxTF = new JTextField();
		panel.addComponent(maxTF, 3, 2);

		grid.gridwidth = 2;
		JButton roomButton = new JButton("Order by Rooms");
		roomButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.addComponent(roomButton, 0, 3);
		JButton customerButton = new JButton("Order by Customer");
		roomButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.addComponent(customerButton, 2, 3);

		grid.weightx = 1;
		grid.gridwidth = 4;
		grid.weighty = 1;
		grid.insets = new Insets(10,10,10,10);
		final JTextArea list = new JTextArea();
		list.setEditable(false);
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) list.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 4);
		panel.addComponent(list);

		roomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				try {
					if (!minTF.getText().equals("")) 
						min = Double.parseDouble(minTF.getText());
					if (!maxTF.getText().equals(""))
						max = Double.parseDouble(maxTF.getText());

					ArrayList<Reservation> res = model.getReservations("roomType", min, max);
					if (res != null)
						list.setText(formatReservations(res));
					else 
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Invalid input(s).", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				if (!minTF.getText().equals("")) 
					min = Double.parseDouble(minTF.getText());
				if (!maxTF.getText().equals(""))
					max = Double.parseDouble(maxTF.getText());

				ArrayList<Reservation> res = model.getReservations("customerName", min, max);
				if (res != null)
					list.setText(formatReservations(res));
				else
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
			}
		});

		grid.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backBtn, 0, 5);
		return panel;
	}

	private JPanel getUsersPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.gridwidth = 2;
		grid.ipady = 30;
		grid.weighty = 0;
		panel.addLabel("Manage System Users", 24, "center", Color.white, Color.black, 0, 0);

		allUsers = new JList();
		allUsers.setCellRenderer(new Cell());
		ArrayList<Account> users = model.getAllUsers(null);
		allUsers.setListData(users.toArray());
		allUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allUsers.setLayoutOrientation(JList.VERTICAL);
		allUsers.setVisibleRowCount(10);
		JScrollPane listScroller = new JScrollPane(allUsers);
		panel.addComponent(listScroller, 0, 1);
		
		grid.gridwidth = 1;
		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.addComponent(addEmployeeButton, 0, 2);
		addEmployeeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switchPanel("AddEmployee");
			}
		});		
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.addComponent(deleteButton, 1, 2);
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account deletedUser = (Account)allUsers.getSelectedValue();
				if(deletedUser == null) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please, select a user.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					String deletedUserName = deletedUser.getUsername();
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(new JFrame(), "Do you want to delete user " + deletedUserName + "?", "Confirmation", dialogButton, JOptionPane.QUESTION_MESSAGE);
					if(dialogResult == 0) {
						model.deleteUser(deletedUserName);
						allUsers.setListData(model.getAllUsers(null).toArray());
					} 
				}
			}			
		});
		
		grid.gridwidth = 5;
		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backButton, 0, 3);
		return panel;
	}
	
	private JPanel getViewRoomServicePanel(){
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weightx = 1;
		grid.weighty = 0;

		String userID = "USERID";
		String roomID= "ROOMID";
		String task = "TASK";

		panel.addLabel("View of Room Service (CUSTOMER)", 20, "center", null, null, 0, 0);
		panel.addLabel("UserID:  " + userID, 20, "left", null, null, 0, 1);
		panel.addLabel("RoomID:  " + roomID, 20, "left", null, null, 0, 2);
		panel.addLabel("Task: "  + task, 20, "left", null, null, 0, 3);
		panel.addNavigation("CHANGE", 16, "Room Service", 0, 6);
		panel.addNavigation("BACK", 16, "Customer", 1, 6);
		panel.addNavigation("CANCEL", 16,"Customer", 2,6);
		
		return panel;
	}

	private JPanel getFileComplaintPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.weighty = 1;
		grid.ipady = 30;
		panel.addLabel("File a Complaint", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>We apologize for any inconvenience.<br>"
				+ "Please file your complaint here and a hotel manager<br>"
				+ "will contact you as soon as possible.</html>", 13, "left", null, null, 0, 1);

		final JTextArea complaintArea = new JTextArea();
		complaintArea.setWrapStyleWord(true);
		complaintArea.setLineWrap(true);
		panel.addComponent(complaintArea, 0, 2);

		grid.weighty = 0;
		panel.addNavigation("Back", 16, "Customer", 0, 4);

		JButton sumbitButton = new JButton("Submit");
		sumbitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		sumbitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String complaintTest = complaintArea.getText();
				if (complaintTest.isEmpty() || complaintTest.length() < 1 || complaintTest.length() > 100) 
					validEntry = false;
				if (validEntry) {
					panel.clearComponents();
					if (model.addComplaint(model.getCurrentUser().getUsername(), complaintTest)) {	
						JOptionPane.showMessageDialog(new JFrame(), "Your complaint has been filed.", 
								"Result", JOptionPane.DEFAULT_OPTION);
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), "Error: Must be between 1 and 100 characters.", 
							"Error", JOptionPane.ERROR_MESSAGE);
			};
		});
		panel.addComponent(sumbitButton, 0, 3);
		return panel;
	}

	private JPanel getStatisticsPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.weighty = 1;
		grid.ipady = 30;
		panel.addLabel("Statistics", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.insets = new Insets(10,10,10,10);
		
		final JTextArea stats = new JTextArea();
		stats.setWrapStyleWord(true);
		stats.setLineWrap(true);
		stats.setLineWrap(true);
		stats.setWrapStyleWord(true);
		stats.setOpaque(false);
		stats.setEditable(false);
		stats.setBorder(new EmptyBorder(10,10,2,2));
		panel.addComponent(stats, 0, 2);

		grid.weighty = 0;
		grid.gridx = 1;
		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Manager");
			}
		});
		panel.addComponent(backButton, 0, 3);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = model.getReceipt();
				if (output != null){
					stats.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});
		return panel;
	}

	private JPanel getComplaintsPanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();
		grid.weighty = 1;
		grid.ipady = 30;
		grid.gridwidth = 2;
		panel.addLabel("Customer Complaints", 24, "center", Color.white, Color.black, 0, 0);

		grid.weightx = 0;
		grid.ipady = 0;
		grid.gridwidth = 1;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("Enter a complaint ID and solution to resolve the complaint.", 13, "left", null, null, 1, 1);

		panel.addLabel("Complaint ID: ", 20, "left", null, null, 1, 2);
		panel.addLabel("Solution: ", 20, "left", null, null, 1, 4);

		final JTextField idField = new JTextField();
		panel.addComponent(idField, 1, 3);

		final JTextArea solutionField = new JTextArea();
		solutionField.setWrapStyleWord(true);
		solutionField.setLineWrap(true);
		panel.addComponent(solutionField, 1, 5);

		JButton submitButton = new JButton("Resolve Complaint");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.addComponent(submitButton, 1, 6);

		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backButton, 0, 6);

		grid.gridheight = 5;
		final JTextArea list = new JTextArea();
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		list.setEditable(false);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 1);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = "";
				ArrayList<Complaint> complaints = model.getAllComplaints();
				if (complaints != null){
					output += "Number of complaints: " + complaints.size();
					for (Complaint c : complaints)
						output += "\n\n" + c.toString();
					list.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Integer id = null;
				String solution = solutionField.getText();

				try {
					if (!idField.getText().equals(""))
						id = Integer.parseInt(idField.getText());

					Complaint c = model.getComplaint(id);
					if (c == null)
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: Complaint does not exist.", "Error", 
								JOptionPane.ERROR_MESSAGE);
					else if (solution.length() < 1 || solution.length() > 100)
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: Solution must be between 1 and 100 characters.", "Error", 
								JOptionPane.ERROR_MESSAGE);
					else {
						if (c.getResolvedBy() == null) {
							panel.clearComponents();
							model.updateCompaint(id, model.getCurrentUser().getUsername(), solution);
							JOptionPane.showMessageDialog(new JFrame(), "Complaint resolved", "Result", JOptionPane.DEFAULT_OPTION);
						}
						else {
							JOptionPane.showMessageDialog(new JFrame(), 
									"Error: The complaint has already been resolved.", "Error", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Invalid input(s).", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		return panel;
	}

	private JPanel getRoomServicePanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.ipady = 30;
		panel.addLabel("Room Service Request", 24, "center", Color.white, Color.black, 0, 0);

		grid.weightx = 0;
		grid.ipady = 0;
		grid.gridwidth = 1;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("Select a task", 12, "left", null, null, 0, 1);

		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		grid.weighty = 1;
		panel.addComponent(listScroller, 0, 2);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ArrayList<RoomService> current = model.getRoomService();
				if (current != null) 
					list.setListData(current.toArray());
				else {
					list.setListData(new RoomService[0]);
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton submitButton = new JButton("Complete");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		submitButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to complete this task?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						RoomService rs = (RoomService)list.getSelectedValue();
						if (!model.resolveTask(rs.getTaskID()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"An unexpected error has occurred. Please contact your system admin.", "Error", 
									JOptionPane.ERROR_MESSAGE);
					}
				}
			};
		});
		panel.addComponent(submitButton, 0, 3);
		panel.addNavigation("Back to main menu", 16, "Room Attendant" , 0, 4);
		return panel;
	}
	
	private JPanel getOrderRoomServicePanel() {
		final BasicMenuPanel panel = new BasicMenuPanel(this);
		GridBagConstraints grid = panel.getConstraints();

		grid.ipady = 30;
		grid.gridwidth = 2;
		panel.addLabel("Room Service Request", 24, "center", Color.white, Color.black, 0, 0);

		grid.ipady = 0;
		grid.insets = new Insets(10,10,10,10);
		panel.addLabel("Description: (Please, include your room number in the description)", 13, "left", null, null, 0, 1);
		
		JTextArea roomServiceField = new JTextArea(10, 20);
		roomServiceField.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(roomServiceField); 
		panel.addComponent(scrollPane, 0, 2);
		
		JButton submitButton = new JButton("Request");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = roomServiceField.getText();
				if(s.isEmpty()){
					JOptionPane.showMessageDialog(new JFrame(), 
							"Description cannot be empty.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}else{
					int response = JOptionPane.showConfirmDialog(
							new JFrame(), "Do you want to request a room servce?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						model.addRoomService(model.getCurrentUser().getUsername(), s);
						roomServiceField.setText("");
						JOptionPane.showMessageDialog(new JFrame(), 
								"Your request has been made.");
					}
					
				}

			}
		});
		panel.addComponent(submitButton, 0, 3);	

		JButton backButton = new JButton("Back to main menu");
		backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Customer");
				roomServiceField.setText("");
			}
		});
		panel.addComponent(backButton, 0, 4);
		return panel;
	}
	
	private GregorianCalendar isValidDateFormat(String input) {
		try {
			date.setLenient(false);
			GregorianCalendar cal = new GregorianCalendar();
			Date d = date.parse(input);
			cal.setTime(d);

			return cal;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

/*	private int checkDaysBetweenDate(GregorianCalendar checkIn, GregorianCalendar checkOut) {
		GregorianCalendar temp = (GregorianCalendar) checkIn.clone();
		int count = 0;
		while (!temp.equals(checkOut)) {
			temp.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}*/

	private String formatReservations(ArrayList<Reservation> reservation) {
		String result = "Total reservations: " + reservation.size();
		if (!reservation.isEmpty()) {
			for (Reservation r : reservation) {
				String in = date.format(r.getStartDate());
				String out = date.format(r.getEndDate());

				result += "\n\nReservation # " + r.getReservationID()
				+ "\nUsername: " + r.getCustomer()
				+ "\nRoom: " + r.getRoom().getRoomType()
				+ "\nStart: " + in
				+ "\nEnd: " + out
				+ "\nCost: $" + Double.toString(r.getTotalCost());

				if (r.getCancelled())
					result += "\nThis reservation has been cancelled";
			}
		}
		return result;
	}

/*	private String formatUsers(ArrayList<Account> users) {
		String result = "Total users: " + users.size();
		if (!users.isEmpty()) {
			for (Account a : users) {
				result += "\n\nUsername: " + a.getUsername()
				+ "\nName: " + a.getFirstName() + " " + a.getLastName()
				+ "\nUser role: " + a.getUserRole();
				if (a.getUserRole().equals("Customer")) {
					int count = 0;
					for (Reservation r : a.getReservation())
						if (!r.getCancelled()) count++;
					result += "\nNumber of Reservations: " + count;
				}
			}
		}
		return result;
	}*/

}
