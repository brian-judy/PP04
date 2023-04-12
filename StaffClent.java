package source;

//add the class template

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

class StaffClent extends JFrame implements ActionListener{


	private String hostname;
	private int port;
	private Message msg;
	private String[] stateOptions = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", 
			"Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", 
			"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
			"Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
			"Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
			"Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	//boolean variable to ensure update/delete aren't clicked before insert
	boolean clickable = false;

	// declare UI component objects
	//UI Labels
	private JLabel lblID;
	private JLabel lblLastName;
	private JLabel lblFirstName;
	private JLabel lblMI;
	private JLabel lblAddress;
	private JLabel lblCity;
	private JComboBox<String> stateDropDown;
	private JLabel lblMobileNumber;
	private JLabel lblMobileCarrier;
	private JLabel lblHomeNumber;
	private JLabel lblHomeCarrier;
	
	//UI text fields
	private JTextField txtID;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtMI;
	private JTextField txtAddress;
	private JTextField txtCity;
	private JTextField txtMobileNumber;
	private JTextField txtMobileCarrier;
	private JTextField txtHomeNumber;
	private JTextField txtHomeCarrier;
	
	//UI buttons
	private JButton btnView;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnClear;
	private JButton btnClose;
	
	//UI TextArea and scrollpane
	private JTextArea textArea;
	private JScrollPane jp;
	
public StaffClent(String hostname, int port) throws UnknownHostException, IOException {
	
	this.port = port;
	this.hostname = hostname;
	
	// Create a connection with the StaffServer server on port number 8000
	DataInputStream in;
	DataOutputStream out;
	Socket socket;
	
	socket = new Socket(hostname, port);
	in = new DataInputStream(socket.getInputStream());
	out = new DataOutputStream(socket.getOutputStream());
	textArea.append("Connected \n");
	
	// call these two methods to create user GUI
	initComponenet();
	doTheLayout();
	
	//add buttons to action listeners
	this.btnClear.addActionListener(this);
	this.btnClose.addActionListener(this);
	this.btnDelete.addActionListener(this);
	this.btnInsert.addActionListener(this);
	this.btnUpdate.addActionListener(this);
	this.btnView.addActionListener(this);
	
}

private void initComponenet(){
	// Initialize the GUI components
	//Labels
	this.lblAddress = new JLabel("Address");
	this.lblCity = new JLabel("City");
	this.lblFirstName = new JLabel("First Name");
	this.lblLastName = new JLabel("Last Name");
	this.lblID = new JLabel("ID");
	this.lblMI = new JLabel("MI");
	this.lblMobileNumber = new JLabel("Mobile Number");
	this.lblMobileCarrier = new JLabel("Mobile Carrier");
	this.lblHomeNumber = new JLabel("Home Number");
	this.lblHomeCarrier = new JLabel("Home Carrier");
	
	//text fields
	this.txtAddress = new JTextField(30);
	this.txtCity = new JTextField(15);
	this.txtFirstName = new JTextField(15);
	this.txtLastName = new JTextField(15);
	this.txtID = new JTextField(10);
	this.txtMI = new JTextField(1);
	this.txtMobileNumber = new JTextField(10);
	this.txtMobileCarrier = new JTextField(10);
	this.txtHomeNumber = new JTextField(10);
	this.txtHomeCarrier = new JTextField(10);
	
	//buttons
	this.btnClear = new JButton("Clear");
	this.btnClose = new JButton("Close");
	this.btnDelete = new JButton("Delete");
	this.btnInsert = new JButton("Insert");
	this.btnUpdate = new JButton("Update");
	this.btnView = new JButton("View");
	
	//dropdown menu
	this.stateDropDown = new JComboBox<>(stateOptions);
	
	// define text area and add it to scroll pane
	this.textArea = new JTextArea("Program Display\n", 10,30);
	this.jp = new JScrollPane(textArea);
	this.textArea.setEditable(false);
	
}//end initcomponent

private void doTheLayout(){
	// Arrange the UI components into GUI window
	JPanel top = new JPanel();
    JPanel center = new JPanel();
    JPanel centerTop = new JPanel();
    JPanel centerBottom = new JPanel();
    JPanel centerMid = new JPanel();
    JPanel bottom = new JPanel();
    
    top.add(lblID);
    top.add(txtID);
    top.add(lblLastName);
    top.add(txtLastName);
    top.add(lblFirstName);
    top.add(txtFirstName);
    top.add(lblMI);
    top.add(txtMI);
    
    //set borderlayout for center panel
    center.setLayout(new BorderLayout());
    
    centerTop.add(lblAddress);
    centerTop.add(txtAddress);
    centerTop.add(lblCity);
    centerTop.add(txtCity);
    centerTop.add(stateDropDown);
    
    centerMid.add(lblMobileNumber);
    centerMid.add(txtMobileNumber);
    centerMid.add(lblMobileCarrier);
    centerMid.add(txtMobileCarrier);
    centerMid.add(lblHomeNumber);
    centerMid.add(txtHomeNumber);
    centerMid.add(lblHomeCarrier);
    centerMid.add(txtHomeCarrier);
    
    centerBottom.add(btnInsert);
    centerBottom.add(btnView);
    centerBottom.add(btnUpdate);
    centerBottom.add(btnDelete);
    centerBottom.add(btnClear);
    centerBottom.add(btnClose);
    
    //add top/mid/bottom panels to center panel
    center.add(centerTop,BorderLayout.NORTH);
    center.add(centerMid,BorderLayout.CENTER);
    center.add(centerBottom,BorderLayout.SOUTH);
    
    bottom.add(jp);
    
    //add the frames to the JFrame GUI
    this.add(top,"North");
    this.add(center,"Center");
    this.add(bottom,"South");
    
}//end doTheLayout
	
 
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource() == this.btnClear)
		this.clearButtonClicked();
	else if(e.getSource() == this.btnClose)
		this.closeButtonClicked();
	else if(e.getSource() == this.btnDelete)
		try {
			this.deleteButtonClicked();
		} catch (ClassNotFoundException e1) {
			textArea.append("Delete unsuccessful!");
		} catch (IOException e1) {
		}
	else if(e.getSource() == this.btnInsert)
		try {
			this.insertButtonClicked();
		} catch (ClassNotFoundException e1) {
			textArea.append("Insert unsuccessful!");
		} catch (IOException e1) {
		}
	else if(e.getSource() == this.btnUpdate)
		try {
			this.updateButtonClicked();
		} catch (ClassNotFoundException e1) {
			textArea.append("Update unsuccessful!");
		} catch (IOException e1) {
		}
	else if(e.getSource() == this.btnView)
		try {
			this.viewButtonClicked();
		} catch (ClassNotFoundException e1) {
			textArea.append("View unsuccessful!");
		} catch (IOException e1) {
		}
	
}//end action performed

//sendMessage for insert operation
public void sendMessage(int id, String lastName, String firstName, char mi, String address, String city, String state,
			BigInteger mPhoneNo, BigInteger hPhoneNo, String mPhoneCarrier, String hPhoneCarrier) 
					throws IOException, ClassNotFoundException {
	
	Message message = new Message(id, lastName, firstName, mi, address, city, state, mPhoneNo, hPhoneNo, mPhoneCarrier, hPhoneCarrier);
	out.writeObject(message);
	receivingMessage();
}

//sendMessage for view operation
public void sendMessage(int id) throws IOException, ClassNotFoundException {
	Message message = new Message(id);
	out.writeObject(message);
	receivingMessage();
	
}

//sendMessage for update operation
public void sendMessage(int id, String address) throws IOException, ClassNotFoundException {
	
	Message message = new Message(id, address);
	out.writeObject(message);
	receivingMessage();
}

//sendMessage for delete operation
public void sendMessage(int id, BigInteger mPhone) throws IOException, ClassNotFoundException {
	
	Message message = new Message(id, mPhone);
	out.writeObject(message);
	receivingMessage();
}

public void receivingMessage() throws IOException, ClassNotFoundException {
	
	String message = (String)in.readObject();
	textArea.append(message);
}

private void viewButtonClicked() throws ClassNotFoundException, IOException {
	  
	// handle view button clicked event
	if(clickable) {
		int id = Integer.valueOf(txtID.getText());
		sendMessage(id);
	}
	else
		textArea.append("Must insert before viewing.");
  }

private void insertButtonClicked() throws ClassNotFoundException, IOException{
	  
	// handle insert button clicked event 
	int id = Integer.valueOf(txtID.getText());
	String lastName = txtLastName.getText();
	String firstName = txtFirstName.getText();
	char mi = txtMI.getText().charAt(0);
	String address = txtAddress.getText();
	String city = txtCity.getText();
	String state = stateDropDown.getSelectedItem().toString();
	BigInteger mPhone = new BigInteger(txtMobileNumber.getText());
	BigInteger hPhone = new BigInteger(txtHomeNumber.getText());
	String mCarrier = txtMobileCarrier.getText();
	String hCarrier = txtHomeCarrier.getText();
	
	clickable = true;
	sendMessage(id, lastName, firstName, mi, address, city, state, mPhone, hPhone, mCarrier, hCarrier);
	
  }


private void updateButtonClicked() throws ClassNotFoundException, IOException{
	
	// handle update button clicked event
	if(clickable) {
		int id = Integer.valueOf(txtID.getText());
		String address = txtAddress.getText();
	
		sendMessage(id, address);
	}
	else
		textArea.append("Must insert before updating.");
	
  }

private void deleteButtonClicked() throws ClassNotFoundException, IOException{
	  
	// handle delete button clicked event
	if(clickable) {
		int id = Integer.valueOf(txtID.getText());
		BigInteger mPhone = new BigInteger(txtMobileNumber.getText());
	
		sendMessage(id, mPhone);
	}
	else
		textArea.append("Must insert before deleting.");
  }
  
  
  void clearButtonClicked(){
	  
	// handle clear button clicked event
	txtFirstName.setText("");
	txtLastName.setText("");
	txtMI.setText("");
	txtAddress.setText("");
	txtCity.setText("");
	txtMobileNumber.setText("");
	txtMobileCarrier.setText("");
	txtHomeNumber.setText("");
	txtHomeCarrier.setText("");
	txtID.setText("");
	  
  }
  

  void closeButtonClicked(){
	  
	// handle close button clicked event
	JOptionPane.showMessageDialog(null, "Program Exit!");
	System.exit(0);
	
  }
  
  /**Main method
 * @throws IOException 
 * @throws UnknownHostException */
  public static void main(String[] args) throws UnknownHostException, IOException {
	  
	  // create the user GUI
	  StaffClent frame = new StaffClent("LocalHost", 8000);
	  frame.setTitle("Staff Information Entry");
	  frame.pack();
	  frame.setLocationRelativeTo(null);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setVisible(true);
	  
  }//end main
}