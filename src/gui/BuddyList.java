package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import conversation.User;

public class BuddyList extends JFrame{
	
	public static User user;
	private  JButton addUserButton;
	private JTextField NOCTextField;
	private JTextArea usersAddedTX;
	private JButton startChatButton;
	private JComboBox userSelectedCB;
	public static ChatRoomGUI CRG;
	
	
	public BuddyList(User user){
		super("Buddy List");
		this.user = user;
		//this.user = user;
		//menu bar

		class StartMessage implements ActionListener{
			StartMessage(){
			}
			public void actionPerformed(ActionEvent ae){
				//Katrina will give table of users that are online
				Object values[] = {"Online user 1", "Online user 2", "Online user 3"};
				String user = (String)JOptionPane.showInputDialog(BuddyList.this, 
				"Choose User to Start Chat!", 
				"Start Message", 
				JOptionPane.QUESTION_MESSAGE,
				null, // icon
				values, values[0]);
				try{
					if(!user.equals(null)){
					System.out.println("User selected is" + user);
					new ChatRoomGUI(user);
					}
				} catch (NullPointerException npe){
					System.out.println(npe.getMessage());
				}
			}
		}
		class GroupMessage implements ActionListener{
			GroupMessage(){
			}
			public void actionPerformed(ActionEvent ae){
				 createDialogeGroupMessage();
			}
		}


		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem startMessageMenuItem = new JMenuItem("Start Message");
		JMenuItem startGroupMessageMI = new JMenuItem("Start Group Message");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		startMessageMenuItem.addActionListener(new StartMessage());
		startGroupMessageMI.addActionListener(new GroupMessage());
		aboutMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				 JDialog jd = new JDialog();
				 jd.setTitle("About");
				 jd.setLocation(450,300);
				 jd.setSize(200, 200);
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
		fileMenu.add(startMessageMenuItem);
		fileMenu.add(startGroupMessageMI);
		jmb.add(fileMenu);
		jmb.add(helpMenu);
		setJMenuBar(jmb);
		
		//west panel
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setBackground(Color.WHITE);
		
		//Icon picIcon = user.getImage();
		//JButton iconButton = new JButton(picIcon);
		JButton iconButton = new JButton("b");
		//iconButton.setPressedIcon(picIcon);
		iconButton.setSize(50,50);
		iconButton.setLocation(0,0);
		iconButton.setBorderPainted(false);
		class iconButtonClass implements ActionListener{
			User us;
			public iconButtonClass(User u){
				this.us = u;
			}
			public void actionPerformed(ActionEvent e) {
				 JDialog jd = new JDialog();
//				 jd.setTitle("About");
				 jd.setLocation(450,300);
				 jd.setSize(200, 200);
				 jd.setModal(true);
				 JPanel jp = new JPanel();
				 BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
				 jp.setLayout(bl);
				 JTextArea aboutMeTA = new JTextArea(us.getAboutme());
//				 JLabel jl = new JLabel("About Me");
//				 JLabel jl1 = new JLabel("I go to school");
//				 jp.add(jl);
//				 jp.add(jl1);
				 jd.add(aboutMeTA);
				 jd.add(jp);
				 jd.setVisible(true);
			}
		}
		iconButton.addActionListener(new iconButtonClass(user));
		
		westPanel.add(iconButton);
		
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
		messageButton.setBorderPainted(false);
		messageButton.addActionListener(new StartMessage());
		topPanel.add(messageButton);
		
		ImageIcon groupChatIcon = new ImageIcon("Pictures/GroupChat.png");
		JButton groupChatButton = new JButton(groupChatIcon);
		groupChatButton.setPressedIcon(groupChatIcon);
		groupChatButton.setSize(30,30);
		groupChatButton.setLocation(10,10);
		groupChatButton.setBorderPainted(false);
		topPanel.add(groupChatButton);
		
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
		
		JTextArea jtaNote = new JTextArea("Online Users", 1, 16);
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
			final String usersname = Users[i].substring(0, Users[i].indexOf('.'));
			JButton friends = new JButton(usersname);
			friends.setEnabled(true);
			friends.setIcon(friendsIcon);
			friends.setBorderPainted(false);
			friends.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					 JDialog jd = new JDialog();
//					 jd.setTitle("About");
					 jd.setLocation(450,300);
					 jd.setSize(200, 200);
					 jd.setModal(true);
					 JPanel jp = new JPanel();
					 BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
					 jp.setLayout(bl);
					 JLabel jl = new JLabel("About Me");
					 JLabel jl1 = new JLabel("My Name is " + usersname);
					 jp.add(jl);
					 jp.add(jl1);
					 jd.add(jp);
					 jd.setVisible(true);
				}
				
			});
			centerPanel.add(friends);
		}
		
		ImageIcon offlineIcon = new ImageIcon("Pictures/"+Users[Users.length-1]);
		JButton offlineFriend = new JButton(Users[Users.length-1].substring(0, Users[Users.length-1].indexOf('.')));
		offlineFriend.setEnabled(true);
		offlineFriend.setIcon(offlineIcon);
		offlineFriend.setBorderPainted(false);
		centerPanel.add(offlineFriend);

		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		this.setSize(300,700);
		this.setLocation(950,500);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public String temp = "";
	class addUserToChatClass implements ActionListener{
		private String cn;
		private JTextArea tx;	
		addUserToChatClass(String selected, JTextArea tx){
			this.cn = selected;
			this.tx = tx;
			
		}
		public void actionPerformed(ActionEvent ae){
//			System.out.println(userSelectedCB.getItemListeners());
//			String item = (String)userSelectedCB.getSelectedItem();
			System.out.println("item is: " + cn);
			tx.setText(cn+ ", ");
			//temp += cn;
			System.out.println("temp is: " + temp);
		}


	}
	private void addUsersToTextField(String selected, JTextArea jta){
		addUserButton.addActionListener( new addUserToChatClass(selected, jta));
	}
	class startChatClass implements ActionListener{
		startChatClass(JTextField NOCTextField){
			
		}
		public void actionPerformed(ActionEvent ae){
			
		}
	}
	static private String selectedString(ItemSelectable is) {
	    Object selected[] = is.getSelectedObjects();
	    return ((selected.length == 0) ? "null" : (String) selected[0]);
	 }
	private void createDialogeGroupMessage(){
		 JDialog jd = new JDialog();
		 jd.setTitle("Group Message");
		 jd.setLocation(450,100);
		 jd.setSize(350,100);
		 jd.setModal(true);
		 
		 JPanel jp = new JPanel();
		 jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS)); 
		 
		 JPanel topPanel = new JPanel();
		 topPanel.setLayout( new FlowLayout(FlowLayout.LEFT));
		 JLabel nameOfConversation = new JLabel("Name the Conversation:");
		 NOCTextField = new JTextField("Conversation name", 13);
		 topPanel.add(nameOfConversation);
		 topPanel.add(NOCTextField);
		 
//		 JPanel centerPanel = new JPanel();
//		 centerPanel.setLayout( new FlowLayout(FlowLayout.LEFT));
//		 JLabel usersAddedToConversation = new JLabel("Users Added:");
//		 usersAddedTX = new JTextArea("Users Added field", 1, 20);
//		 usersAddedTX.setEditable(false);
//		 JScrollPane scrollPaneUsersAdded = new JScrollPane(usersAddedTX,
//				 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		 centerPanel.add(usersAddedToConversation);
//		 centerPanel.add(scrollPaneUsersAdded);
//		 
		 JPanel bottomPanel = new JPanel();
		 bottomPanel.setLayout( new FlowLayout(FlowLayout.RIGHT));
//		 final String options[] = {"User 1", "User 2", "User 3", "User 4"};
//		 //JLabel chooseUsersLabel = new JLabel("Add+:");
//		 userSelectedCB = new JComboBox(options);
//		 userSelectedCB.setSelectedItem(options);
//		 userSelectedCB.addItemListener(new ItemListener(){
//	 			public void itemStateChanged(ItemEvent itemEvent){
//	 				int state = itemEvent.getStateChange();
//	 		        System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
//	 		        System.out.println("Item: " + itemEvent.getItem());
//	 		        ItemSelectable is = itemEvent.getItemSelectable();
//	 		        String selected = selectedString(is);
//	 		        System.out.println(", Selected: " + selected);
//	 				 addUsersToTextField(selected, usersAddedTX);//adds users to TF
//	 			}
//	 		});
//		 userSelectedCB.setForeground(Color.blue);
//		 userSelectedCB.setBackground(Color.white);
////		 userSelectedCB.setSelectedItem("User 1");
//		 addUserButton = new JButton("Add User");
		 startChatButton = new JButton("Start Chat");
		 startChatButton.addActionListener(new ActionListener(){
			// ChatRoomGUI
			 public void actionPerformed(ActionEvent ae){
				 //new ChatRoomGUI(NOCTextField);
			 }
		 });
//		 //bottomPanel.add(chooseUsersLabel);
//		 bottomPanel.add(userSelectedCB);
//		 bottomPanel.add(addUserButton);
		 bottomPanel.add(startChatButton);

		 jp.add(topPanel);
//		 jp.add(centerPanel);
		 jp.add(bottomPanel);
		 jd.add(jp);
		 jd.setVisible(true);
	}
	public static void main(String []args){
		new BuddyList(user);
	}
}