package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
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
	public static ChatRoomGUI CRG;
	public static JPanel buddyListPanel;
	public static JPanel onlineUsersPanel;
	public static JPanel onlineConvoPanel; 
	private JTextField NOCTextField;
	private JButton startChatButton;
	private  String []Users = {"Katrina.jpg",
			"Sharads.jpg",
			"Ryan C.jpg",
			"Ryan J.jpg",
			"Harvey.jpg",
			"Mike.jpg",
			"Katrina.jpg",
			"Sharads.jpg",
			"Ryan C.jpg",
			"Ryan J.jpg",
			"Harvey.jpg",
			"Mike.jpg"};
	private JPanel innerConvoPanel;
	private JPanel inneronlineusersPanel;
	
	//CONSTRUCTOR FOR THE BUDDY LIST
	BuddyList(User user){
		super("Buddy List");
		this.user = user;
		
		//ALL CLASSES FOR ACTION LISTENER
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem startMessageMenuItem = new JMenuItem("Start Message");
		JMenuItem startGroupMessageMI = new JMenuItem("Start Group Message");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		startMessageMenuItem.addActionListener(new StartMessage());
		startGroupMessageMI.addActionListener(new StartGroupMessage());
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

		//center panel
		buddyListPanel = new JPanel();
		buddyListPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout()); 
		
		System.out.println("User for Kelsey: " + user.getImagePath());
		ImageIcon userIcon = new ImageIcon(user.getImagePath());
		JButton userButton = new JButton(userIcon);
		userButton.setPressedIcon(userIcon);
		userButton.setSize(30,30);
		userButton.setLocation(10,10);
		userButton.setBorderPainted(false);
		topPanel.add(userButton);
		
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
		groupChatButton.addActionListener(new StartGroupMessage());//share action listener with group chat
		topPanel.add(groupChatButton);
		
		buddyListPanel.add(topPanel);
		
		
		JPanel UserConvoPanel = new JPanel();
		UserConvoPanel.setLayout(new GridLayout(2, 1));//panel to hold the "Online User/Online Convo"
		
		
		onlineUsersPanel = new JPanel();//panel to put at the top of UserConvoPanel
		
		JTextArea jtaNote = new JTextArea("Online Users", 1, 20); // online users section of code
		jtaNote.setEditable(false);
		jtaNote.setLineWrap(true);
		jtaNote.setWrapStyleWord(true);
		jtaNote.setForeground(Color.DARK_GRAY);
		jtaNote.setBackground(Color.LIGHT_GRAY);
		jtaNote.setFont(new Font("Courier", Font.BOLD, 22));
		inneronlineusersPanel = new JPanel();//panel to contain the scroll bar for usernames
		inneronlineusersPanel.setLayout(new BoxLayout(inneronlineusersPanel, BoxLayout.Y_AXIS));
		JScrollPane onlineUsersSP = new JScrollPane(inneronlineusersPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
												JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		onlineUsersSP.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		onlineUsersSP.setPreferredSize(new Dimension(210,250));
		
		onlineUsersPanel.add(jtaNote, BorderLayout.NORTH);
		onlineUsersPanel.add(onlineUsersSP, BorderLayout.CENTER);
		
		
		//BOTTOM PANEL FOR ONLINE CONVO
		onlineConvoPanel = new JPanel();//panel to but atht ebottom of UserConvoPanel
		
		JTextArea ConversationJTA = new JTextArea("Online Conversations", 1, 20); // online users section of code
		ConversationJTA.setEditable(false);
		ConversationJTA.setLineWrap(true);
		ConversationJTA.setWrapStyleWord(true);
		ConversationJTA.setForeground(Color.DARK_GRAY);
		ConversationJTA.setBackground(Color.LIGHT_GRAY);
		ConversationJTA.setFont(new Font("Courier", Font.BOLD, 22));
		
		innerConvoPanel = new JPanel();//panel for a scroll for the convo list
		innerConvoPanel.setLayout(new BoxLayout(innerConvoPanel, BoxLayout.Y_AXIS));
		JScrollPane onlineconvoSP = new JScrollPane(innerConvoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		onlineconvoSP.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		onlineconvoSP.setPreferredSize(new Dimension(210,250));
		onlineConvoPanel.add(ConversationJTA,BorderLayout.NORTH);
		onlineConvoPanel.add(onlineconvoSP, BorderLayout.CENTER);
		
		UserConvoPanel.add(onlineUsersPanel);
		UserConvoPanel.add(onlineConvoPanel);
		buddyListPanel.add(UserConvoPanel);
		add(buddyListPanel, BorderLayout.CENTER);
		this.setSize(300,700);
		this.setLocation(950,500);
		this.setVisible(true);
		
	}//public buddy list
	
	/*******classes for action listeners************/
	/*******       						************/
	/*******							************/
	class mouseClass extends MouseAdapter{
		String name;
		mouseClass(String name){
			name = name;
		}
		public void mouseClicked(MouseEvent e){
	    	
	        if(e.getClickCount()==2){//double clicked
	        	//create a message w/ that user
	        	new ChatRoomGUI(name);
	        }
	        else if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
	        	//if right click, then the about me should displayed
	        	int selection = JOptionPane.showConfirmDialog(BuddyList.this, 
	        			"About", "Are you sure you want to know their 'About Me'?", JOptionPane.YES_NO_OPTION);
	        	if(selection == 0){// yes
	        		System.out.println("Yes option");
	        		aboutMeAction();
	        	}
	        	else{//no option
	        		System.out.println("No option");
	        	}

	        }
	    }
	
	}
	class StartMessage implements ActionListener{
		StartMessage(){
		}
		public void actionPerformed(ActionEvent ae){
			//Katrina will give table of users that are online
			String user = (String)JOptionPane.showInputDialog(BuddyList.this, 
			"Choose User to Start Chat!", 
			"Start Message", 
			JOptionPane.QUESTION_MESSAGE,
			null, // icon
			Users, Users[0]);
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
	class StartGroupMessage implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			 createDialogeGroupMessage();
		}
	}
	class iconButtonClass implements ActionListener{
		User us;
		public iconButtonClass(User u){
			this.us = u;
		}
		public void actionPerformed(ActionEvent e) {
			 JDialog jd = new JDialog();
//			 jd.setTitle("About");
			 jd.setLocation(450,300);
			 jd.setSize(200, 200);
			 jd.setModal(true);
			 JPanel jp = new JPanel();
			 BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
			 jp.setLayout(bl);
			 JTextArea aboutMeTA = new JTextArea(us.getAboutme());
			 jd.add(aboutMeTA);
			 jd.add(jp);
			 jd.setVisible(true);
		}
	}
	
	/*******FUNCTIONS FOR THE BUDDY LIST************/
	/***********************************************/
	/***********************************************/
	private void aboutMeAction(){
        System.out.println("You right clicked, so It'll show the about me.");
      	 JDialog jd = new JDialog();
		 jd.setLocation(450,300);
		 jd.setSize(200, 200);
		 jd.setModal(true);
		 JPanel jp = new JPanel();
		 BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
		 jp.setLayout(bl);
		 JLabel jl = new JLabel("About Me");
		 JLabel jl1 = new JLabel("This is my about me");
		 jp.add(jl);
		 jp.add(jl1);
		 jd.add(jp);
		 jd.setVisible(true);
	}
	
	public void createDialogeGroupMessage(){
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
			 public void actionPerformed(ActionEvent ae){
				 new ChatRoomGUI(NOCTextField.getText());
			 }
		 });
		 bottomPanel.add(startChatButton);

		 jp.add(topPanel);
		 jp.add(bottomPanel);
		 jd.add(jp);
		 jd.setVisible(true);
	}
	
	public void updateOnlineUser(){
		System.out.println("In update user");
		JButton Test = new JButton("Testing");
		inneronlineusersPanel.add(Test);
		
		for(int i = 0; i < user.getOnlineUsers().size(); i++){
			JButton OUButton = new JButton(user.getOnlineUsers().get(i));
			OUButton.setEnabled(true);
			OUButton.setBorderPainted(false);
			OUButton.addMouseListener(new mouseClass(user.getOnlineUsers().get(i)));
			inneronlineusersPanel.add(OUButton);
		}

	}
	
	public void updateActiveConversations(){
		
		for(int i = 0; i < user.getConversations().size(); i++){
			JButton OUButton = new JButton(user.getConversations().get(i).getName());
			OUButton.setEnabled(true);
			OUButton.setBorderPainted(false);
			OUButton.addMouseListener(new mouseClass(user.getConversations().get(i).getName()));
			innerConvoPanel.add(OUButton);
		}

	}
	
	public static void main(String []args){
		new BuddyList(user);
	}
	
}