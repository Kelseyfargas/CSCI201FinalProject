package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomGUI extends JFrame {
	
	ChatRoomGUI(String title){
		super(title);
		
//		String [] username = {"Kelsey", "Katrina", "Ryan C.", "Ryan J."};
//		String initialSelectionValue = "HIII";
//		//JOptionPane.showInputDialog(ChatRoomGUI.this,"Enter Username:",username);
//		ImageIcon ii = new ImageIcon("Message_Icon.png");
//		JOptionPane.showInputDialog(ChatRoomGUI.this, "Enter Username:", "Log In",JOptionPane.OK_CANCEL_OPTION,ii, username, initialSelectionValue);
//		//JOptionPane.showMessageDialog(ChatRoomGUI.this, "Enter Username:", "Log In",JOptionPane.OK_CANCEL_OPTION,ii);
//		
		JPanel jp = new JPanel(); 
		
		final JTextField searchField = new JTextField("Search",10); 
		searchField.setForeground(Color.GRAY);
		final String searchField_username = searchField.getSelectedText();
//		searchField.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				searchField_username
//			}
//			
//		});
		
		jp.add(searchField); 
		
		
		JTextArea toUsernameField = new JTextArea("To: Katrina");
		toUsernameField.setPreferredSize(new Dimension(450,20));
		toUsernameField.setEditable(false); 
		toUsernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		toUsernameField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		//final JTextField toUsernameField = new JTextField("To:", 35); 
//		toUsernameField.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				String searchField_username = searchField.getSelectedText();
//			}
//			
//		});
		jp.add(toUsernameField); 
		add(jp, BorderLayout.NORTH); 
		 
		JPanel WestPanel = new JPanel();
		WestPanel.setLayout(new BoxLayout(WestPanel, BoxLayout.Y_AXIS));
		Font f = new Font("Serif", Font.BOLD, 12);
		for(int i = 0; i < 6; i++){
			JButton FriendConversationArea_1 = new JButton( "           Friend" + (i+1)+ "          ");
			FriendConversationArea_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
			FriendConversationArea_1.setFont(f);
			FriendConversationArea_1.setForeground(Color.GRAY);
			FriendConversationArea_1.setPreferredSize(new Dimension(150,100));
			//FriendConversationArea_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			//FriendConversationArea_1.setBorder(BorderFactory.createMatteBorder(1,0,1,1, Color.GRAY));
			WestPanel.add(FriendConversationArea_1);
			add(WestPanel, BorderLayout.WEST);
		}
		
		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new BoxLayout(CenterPanel, BoxLayout.Y_AXIS));
		
		//JTextField conversationField = new JTextField("Yah dis text box."); 
		//CenterPanel.add(conversationField); 
		//conversationField.setPreferredSize(new Dimension(300,300));
		
		Font textfont = new Font("SansSerif", Font.BOLD, 12);
		JTextArea areap = new JTextArea();
		
//		JTextArea Katrina = new JTextArea("Katrina:");
//		Katrina.setFont(textfont);
//		Katrina.setCaretColor(Color.MAGENTA);
//		JTextArea Kelsey = new JTextArea("Kelsey:");
//		Kelsey.setFont(textfont);
//		Kelsey.setCaretColor(Color.BLUE);
//		areap.add(Katrina);
//		areap.add(Kelsey);
		
		areap.setText("Katrina: I hope we get an A in the class!" + 
				"\n"+ "Kelsey: Me also!");
		areap.setPreferredSize(new Dimension(300,300));
		areap.setEditable(false); 
		areap.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		areap.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		
		JPanel messageBottomPanel = new JPanel();
		messageBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField outputTextField = new JTextField("New Message",29);
		outputTextField.setForeground(Color.GRAY);
		outputTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		JButton sendButton = new JButton("Send");
		messageBottomPanel.add(outputTextField);
		messageBottomPanel.add(sendButton);
		
		
		CenterPanel.add(areap);
		CenterPanel.add(messageBottomPanel); 
		add(CenterPanel, BorderLayout.CENTER);
		
		
		
		//necessities
		this.setSize(600,400);
		this.setLocation(350,200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String []args){
		Login L = new Login("Login");
	}
}

class Login extends JFrame{
	
	private JTextField usernameTF;
	private JTextField passwordTF;
	
	Login(String name){
		super(name);
		
		JPanel westPanel = new JPanel();
		JLabel image = new JLabel();
		ImageIcon ii = new ImageIcon("Pictures/Message_Icon_Final.png");
		image.setIcon(ii);
		westPanel.add(image);
		
		Font inputfont = new Font("Courrier New", Font.ITALIC, 12);
		JPanel centerPanel = new JPanel();
		usernameTF = new JTextField(15); //user can type in
		usernameTF.setFont(inputfont);
		JLabel username = new JLabel("UserName");
		centerPanel.add(username);
		centerPanel.add(usernameTF);
		
		passwordTF = new JTextField(15);
		passwordTF.setFont(inputfont);
		JLabel password = new JLabel("Password");
		centerPanel.add(password);
		centerPanel.add(passwordTF);
		
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton logInButton = new JButton("Log In");
		JButton createAccount = new JButton("Create Account");
		logInButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent ae) {
					System.out.println("Username is:" + usernameTF.getText());
					System.out.println("Password is:" + passwordTF.getText());
					//if(username password is wrong){
					//	JOptionPane.showMessageDialog(Login.this, "Incorrect password/username. Try again", "Log In",JOptionPane.OK_CANCEL_OPTION);
					//}	
					//when logging in, make sure that you pass in the Username, Iconname, and FriendsList
					//when calling chatroom
					//The BuddyList constructor will change to something like
					//new BuddyList((usernameTF.getText() + "'s Friend's List"), image icon, friendslist)
					new ChatRoomGUI("Messages");
					new BuddyList("Friends List");		
			}
			
		});
		createAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new CreateAccount("Create Account");
			}
		});
		

		flowPanel.add(logInButton);
		flowPanel.add(createAccount);
		centerPanel.add(flowPanel);
		
		//adding the layouts
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		
	
		this.setSize(400,130);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class CreateAccount extends JFrame{
	CreateAccount(String name){
		super(name);
		setLayout(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints(); 

		Font inputfont = new Font("Courrier New", Font.ITALIC, 12);
		JTextField usernameField = new JTextField(15); //user can type in
		usernameField.setFont(inputfont);
		JLabel username = new JLabel("Desired UserName:");
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		gbc.gridwidth = 1;
		add(username, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2; 
		add(usernameField,gbc);
		
		JTextField passwordField = new JTextField(15);
		passwordField.setFont(inputfont);
		JLabel password = new JLabel("Desired Password:");
		gbc.gridx = 0; 
		gbc.gridy = 1;  
		gbc.gridwidth = 1; 
		add(password, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1; 
		gbc.gridwidth = 2; 
		add(passwordField,gbc);

		
		JLabel ChooseIcon = new JLabel("Desired Icon:");
		gbc.gridx = 0; 
		gbc.gridy = 3;  
		gbc.gridwidth = 1; 
		add(ChooseIcon, gbc);
		
		ArrayList<JButton> buttonsArray = new ArrayList<JButton>();
		
		JPanel FirstBottomPanel = new JPanel();	
		FirstBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String [] images = {"BlackCat.png","Cow.png","Elephant1.png",
				"FemaleLion.png","Fox.png","Kitten.png","Lion.png",
				"Penguin.png","Tiger.png","Zebra.png"};
		int left = (images.length)/2;
		for(int i = 0; i < left; i++){
			ImageIcon defaultIcon = new ImageIcon("Pictures/"+images[i]);
			JButton userIconButton = new JButton(defaultIcon);
			FirstBottomPanel.add(userIconButton,gbc);
			buttonsArray.add(userIconButton);
		}
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 5;
		add(FirstBottomPanel, gbc);
		JPanel SecBottomPanel = new JPanel();	
		SecBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		for(int k = left; k < images.length; k++){
			ImageIcon defaultIcon = new ImageIcon("Pictures/"+images[k]);
			JButton userIconButton = new JButton(defaultIcon);
			SecBottomPanel.add(userIconButton,gbc);
			buttonsArray.add(userIconButton);
		}
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 5;
		add(SecBottomPanel, gbc);
		
		for(int v = 0; v < buttonsArray.size(); v++){
			final JButton B = buttonsArray.get(v);
			B.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//if the button is clicked, then assign it to the user
					//something like
					//User User1;
					//User1.setIcon(B.getIcon());
					System.out.println("Selected icon is: "+ B.getIcon());
				}
				
			});
		}
		
		JLabel BioLabel = new JLabel("Enter Short Bio:");
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 7;
		add(BioLabel, gbc);
		
		JTextField Bio = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 5;
		gbc.gridheight = 2;
		gbc.ipadx = 20;
		gbc.ipady = 20;
		add(Bio, gbc);
		
	
		JButton createAccount = new JButton("Create Account");
		gbc.ipadx = 0; 
		gbc.ipady = 0;
		gbc.gridheight = 1;
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 10;
		add(createAccount,gbc);
		
//		createAccount.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Information is stored");
//				//In here, an instance of a User should be called and added to the database
//			   // LogIn.setVisible(true);
//				CloseFrame();
//			}
//
//			private void CloseFrame() {
//				super.dispose();
//				
//			}
//			
//		});
		
//		class createAcc implements ActionListener {
//			
//			createAcc(){
//				
//			}
//			public void actionPerformed(ActionEvent e){
//				
//			}
//			private void CloseFrame(){
//				this.dispose();
//			}
//		}
	
		this.setSize(450,500);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}




class BuddyList extends JFrame{
	
	BuddyList(String name){
		super(name);
		//menu bar
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu contactsMenu = new JMenu("Contacts");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent ae) {
			 JDialog jd = new JDialog();
			 jd.setTitle("About");
			 jd.setLocation(450,300);
			 jd.setSize(150, 100);
			 jd.setModal(true);
			 JPanel jp = new JPanel();
			 BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
			 jp.setLayout(bl);
			 JLabel jl = new JLabel("Created for CSCI 201L");
			 JLabel jl1 = new JLabel("Kelsey, Katrina, "
			 		+ "Ryan C., Ryan J.");
			 jp.add(jl);
			 jp.add(jl1);
			 jd.add(jp);
			 jd.setVisible(true);
			 }
		});

		helpMenu.add(aboutMenuItem);
		jmb.add(fileMenu);
		jmb.add(editMenu);
		jmb.add(contactsMenu);
		jmb.add(helpMenu);
		setJMenuBar(jmb);
		
		//west panel
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setBackground(Color.WHITE);
		
		ImageIcon picIcon = new ImageIcon("Pictures/Kelsey_Icon.jpg");
		JButton iconButton = new JButton(picIcon);
		iconButton.setPressedIcon(picIcon);
		iconButton.setSize(50,50);
		iconButton.setLocation(0,0);
		
//		JTextField aboutMeTF = new JTextField("About me: I love cats. My cat's"
//				+ " name is Clover");
//		Dimension preferredSize = new Dimension(1,1);
//		aboutMeTF.setPreferredSize(preferredSize);
		
//		ImageIcon messageIcon = new ImageIcon("Message.png");
//		JButton messageButton = new JButton(messageIcon);
//		messageButton.setPressedIcon(messageIcon);
//		messageButton.setSize(30,30);
//		messageButton.setLocation(10,10);
		
		
		westPanel.add(iconButton);
//		westPanel.add(aboutMeTF);


		//center panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout()); 
		
		ImageIcon messageIcon = new ImageIcon("Pictures/Message.png");
		JButton messageButton = new JButton(messageIcon);
		messageButton.setPressedIcon(messageIcon);
		messageButton.setSize(30,30);
		messageButton.setLocation(10,10);
		topPanel.add(messageButton);
		
		ImageIcon chatIcon = new ImageIcon("Pictures/GroupChat.png");
		JButton chatButton = new JButton(chatIcon);
		chatButton.setPressedIcon(chatIcon);
		chatButton.setSize(30,30);
		chatButton.setLocation(10,10);
		topPanel.add(chatButton);
		
		
		
//		ImageIcon searchIcon = new ImageIcon("Search.png");
//		JButton jtf = new JButton();
//		jtf.setEnabled(true);
//		jtf.setIcon(searchIcon);
//		
//		JTextField chatLabel = new JTextField("Start Chat",13);
//		chatLabel.setEnabled(true);
		
		//topPanel.add(jtf);
		//topPanel.add(chatLabel);
		centerPanel.add(topPanel);

		
		JTextArea jtaNote = new JTextArea("Recent Chats", 1, 16);
		jtaNote.setEditable(false);
		jtaNote.setLineWrap(true);
		jtaNote.setWrapStyleWord(true);
		jtaNote.setForeground(Color.DARK_GRAY);
		jtaNote.setBackground(Color.LIGHT_GRAY);
		jtaNote.setFont(new Font("Courier", Font.BOLD, 22));
		centerPanel.add(jtaNote);
		
		String [] Users = {"Katrina.jpg",
				"Sharads.jpg",
				"Ryan C.jpg",
				"Ryan J.jpg",
				"Harvey.jpg",
				"Mike.jpg"};
		for(int i = 0; i < Users.length-1; i++){
			ImageIcon friendsIcon = new ImageIcon("Pictures/"+Users[i]);
			JLabel friends = new JLabel(Users[i].substring(0, Users[i].indexOf('.')));
			friends.setEnabled(true);
			friends.setIcon(friendsIcon);
			centerPanel.add(friends);
		}
		
		JTextArea jtaNote1 = new JTextArea("Offline Chats", 1, 16);
		jtaNote1.setEditable(false);
		jtaNote1.setLineWrap(true);
		jtaNote1.setFont(new Font("Courier", Font.BOLD, 22));
		centerPanel.add(jtaNote1);
		
		ImageIcon friendsIcon = new ImageIcon(Users[Users.length-1]);
		JLabel friends = new JLabel(Users[Users.length-1].substring(0, Users[Users.length-1].indexOf('.')));
		friends.setEnabled(true);
		friends.setIcon(friendsIcon);
		centerPanel.add(friends);

		
		
		
		
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		this.setSize(300,700);
		this.setLocation(950,500);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}