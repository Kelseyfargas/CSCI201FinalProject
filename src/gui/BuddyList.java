package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import conversation.User;

public class BuddyList extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int GROUP_CHAT = 1;
	public static int PRIVATE_CHAT = 2;
	public static User user;
	public static JPanel buddyListPanel;
	public static JPanel onlineUsersPanel;
	public static JPanel onlineConvoPanel; 
	private JPanel innerConvoPanel;
	private JPanel inneronlineusersPanel;
	
	//CONSTRUCTOR FOR THE BUDDY LIST
	public BuddyList(User user){
		super("Buddy List");
		setUser(user);
		setLayout(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints(); 
		
		//setLayout(new GridLayout(5, 1));
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem startMessageMenuItem = new JMenuItem("Start Message");
		JMenuItem startGroupMessageMenuItem = new JMenuItem("Start Group Message");
		JMenuItem editBioMenuItem = new JMenuItem("Edit Bio");
		JMenuItem logOutMenuItem = new JMenuItem("Log Out");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		
		startMessageMenuItem.addActionListener(new StartPrivateMessage(getUser()));
		startGroupMessageMenuItem.addActionListener(new StartGroupMessage(getUser()));
		logOutMenuItem.addActionListener(new logoutAction(user));
		
		startMessageMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 
				ActionEvent.CTRL_MASK));
		startGroupMessageMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 
				ActionEvent.CTRL_MASK));
		editBioMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 
				ActionEvent.CTRL_MASK));
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 
				ActionEvent.CTRL_MASK));
		logOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
				ActionEvent.CTRL_MASK));
		

		aboutMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JOptionPane.showMessageDialog(null, 
						"Created for CSCI 201L by Ryan Chase, "
						+ "Kelsey Fargas, "
						+ "Katrina Gloriani, "
						+ " and Ryan Jeong.", 
						"About", 
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		helpMenu.add(aboutMenuItem);
		fileMenu.add(editBioMenuItem);
		fileMenu.add(startMessageMenuItem);
		fileMenu.add(startGroupMessageMenuItem);
		fileMenu.add(logOutMenuItem);
		jmb.add(fileMenu);
		jmb.add(helpMenu);
		setJMenuBar(jmb);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout()); 
		
		ImageIcon userIcon = new ImageIcon(user.getImagePath());
		JButton userButton = new JButton(userIcon);
		userButton.setPressedIcon(userIcon);
		userButton.setSize(30,30);
		userButton.setLocation(10,10);
		userButton.setBorderPainted(false);
		userButton.addActionListener(new iconButtonClass(user));
		topPanel.add(userButton);
		
		ImageIcon messageIcon = new ImageIcon("Pictures/Message.png");
		JButton messageButton = new JButton(messageIcon);
		messageButton.setPressedIcon(messageIcon);
		messageButton.setSize(30,30);
		messageButton.setLocation(10,10);
		messageButton.setBorderPainted(false);
		messageButton.addActionListener(new StartPrivateMessage(getUser()));
		topPanel.add(messageButton);
		
		ImageIcon groupChatIcon = new ImageIcon("Pictures/GroupChat.png");
		JButton groupChatButton = new JButton(groupChatIcon);
		groupChatButton.setPressedIcon(groupChatIcon);
		groupChatButton.setSize(30,30);
		groupChatButton.setLocation(10,10);
		groupChatButton.setBorderPainted(false);
		groupChatButton.addActionListener(new StartGroupMessage(getUser()));//share action listener with group chat
		topPanel.add(groupChatButton);
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		add(topPanel, gbc);//at grid 1
		
	
		JTextArea jtaNote = new JTextArea("Online Users", 1, 20); // online users section of code
		jtaNote.setEditable(false);
		jtaNote.setLineWrap(true);
		jtaNote.setWrapStyleWord(true);
		jtaNote.setForeground(Color.DARK_GRAY);
		jtaNote.setBackground(Color.LIGHT_GRAY);
		jtaNote.setFont(new Font("Courier", Font.BOLD, 22));
		gbc.gridx = 0; 
		gbc.gridy = 1;
		add(jtaNote, gbc);//grid 2
		
		inneronlineusersPanel = new JPanel();//panel to contain the scroll bar for usernames
		inneronlineusersPanel.setLayout(new BoxLayout(inneronlineusersPanel, BoxLayout.Y_AXIS));
		JScrollPane onlineUsersSP = new JScrollPane(inneronlineusersPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
												JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		onlineUsersSP.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		onlineUsersSP.setPreferredSize(new Dimension(210,250));
		
		gbc.gridx = 0; 
		gbc.gridy = 3;
		add(onlineUsersSP, gbc);//grid 3
		
		JTextArea ConversationJTA = new JTextArea("Online Conversations", 1, 20); // online users section of code
		ConversationJTA.setEditable(false);
		ConversationJTA.setLineWrap(true);
		ConversationJTA.setWrapStyleWord(true);
		ConversationJTA.setForeground(Color.DARK_GRAY);
		ConversationJTA.setBackground(Color.LIGHT_GRAY);
		ConversationJTA.setFont(new Font("Courier", Font.BOLD, 22));
		gbc.gridx = 0; 
		gbc.gridy = 4;
		add(ConversationJTA, gbc);//grid 4
		
		innerConvoPanel = new JPanel();//panel to contain the scroll bar for usernames
		innerConvoPanel.setLayout(new BoxLayout(innerConvoPanel, BoxLayout.Y_AXIS));
		JScrollPane onlineconvoSP = new JScrollPane(innerConvoPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		onlineconvoSP.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		onlineconvoSP.setPreferredSize(new Dimension(210,250));
		gbc.gridx = 0; 
		gbc.gridy = 5;
		add(onlineconvoSP,gbc);//grid 5
		
		this.setSize(300,700);
		this.setLocation(950,500);
		this.setVisible(true);
		
	}//public buddy list
	public static void main(String []args){
		new BuddyList(user);
	}
	
/*******classes for action listeners************/
/*******       						************/
/*******							************/
	private class mouseClassOnlineUser extends MouseAdapter{
		private String frienduserName;
		private User onlineuser;
		mouseClassOnlineUser(String userName, User us){
			this.frienduserName = userName;
			this.onlineuser = us;
		}
		public void mouseClicked(MouseEvent e){
	    	
	        if(e.getClickCount()==2){//double clicked
	        	//create a message w/ that user
	        	//double click button , make null to make sure that the the moderator is NOT anyone
	        	
	        	//create a string that has Atuser and Atfriend
	        	String user = onlineuser.getName();
	        	
        		String combinedConvoName = null;
        		System.out.println("IN THE DOUBLE CLICK");
        		System.out.println("Comparing : " + user + " to: " + frienduserName);
        		int comparingusers = user.compareTo(frienduserName);
        		System.out.println("The comparingusers int is " + comparingusers);
        		if(comparingusers < 0){
        			combinedConvoName = "@" + onlineuser.getName() + "@" + frienduserName;
        		}
        		else if(comparingusers > 0){//friend is before user
        			combinedConvoName = "@" + onlineuser.getName() + "@" + frienduserName;
        		}
	        	
	        	
	        	System.out.println("Online user selected is : " + frienduserName);
	        	System.out.println("Combined name in double click is " + combinedConvoName);
	        	
	        	if (onlineuser.windowIsOpen(combinedConvoName)){
	        		System.out.println("~~~~~~~Window is open from double clicking"
	        				+ " user from 'Online user' list ~~~~~~~~");
	        	}
	        	else{//make more sense in chatmeclient.newprivaterequest since it would retrieve old history from
	        		//data base, but since we're not going to implement that feature, then it's okay to leave here
		        	//ALPHABETIZE
	        		//if onlineuser's name is alphabetically before their friend
		        	MessageWindow mw = new MessageWindow(combinedConvoName, onlineuser);
		        	mw.setTitle(frienduserName);
		        	//onlineuser.addToOnlineConversations(mw);
		        	onlineuser.addToOpenConversations(mw);
		        	onlineuser.initiatePrivateConvoRequest(combinedConvoName);
	        	}
	        	
	        }
	        else if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
	        	//FIX THIS AS OF 10:56PM IT SHOWS THE ABOUT ME OF THE CURRENT USER NOT THE FRIEND
	        	int selection = JOptionPane.showConfirmDialog(BuddyList.this, 
	        			"About", "Are you sure you want to know their 'About Me'?", JOptionPane.YES_NO_OPTION);
	        	if(selection == 0){// yes
	        		System.out.println("Yes option");
	        		System.out.println("The user is : " + onlineuser );
	        		aboutMeAction(onlineuser);
	        	}
	        	else{//no option
	        		System.out.println("No option");
	        	}
	
	        }
	    }
	
	}
	private class mouseClassActiveConvo extends MouseAdapter{
		
		private String convoname;
		private User u;
		
		mouseClassActiveConvo(String convoname, User us){
			this.convoname = convoname;
			this.u = us;
		}
		public void mouseClicked(MouseEvent e){
	        if(e.getClickCount()==2){//double clicked
	        	
	        	if(!u.windowIsOpen(convoname)){
		        	MessageWindow mw = new MessageWindow(convoname, u);
		        	u.addToOnlineConversations(mw);
		        	//doesn't call client cause we know conversation 
		        	//already exists via initiate convo from other user
	        	}
			}
		}
	}

	private class StartPrivateMessage implements ActionListener{
		
		private User privateUser;
		StartPrivateMessage(User user){
			this.privateUser = user;
		}
		
		public void actionPerformed(ActionEvent ae){
			Object[] onlinelist = user.getOnlineUsers().toArray();

			for(int o = 0; o < onlinelist.length; o++){
				System.out.println("Online user: " + onlinelist[o]);
			}
			//NOTE AS OF 11:21PM MAKE SURE THAT PEOPLE ISN'T NULL. WINDOWLISTENER FOR X
			String frienduserName = (String)JOptionPane.showInputDialog(BuddyList.this, 
			"Choose User to Start Chat!", 
			"Start Message", 
			JOptionPane.QUESTION_MESSAGE,
			null, // icon
			onlinelist, onlinelist[0]);
			System.out.println("In BUTTON/DROPDOWN. User selected is" + privateUser);
			String user = privateUser.getName();
    		String combinedConvoName = null;
    		System.out.println("Comparing : " + user + " to: " + frienduserName);
    		int comparingusers = user.compareTo(frienduserName);
    		System.out.println("The comparingusers int is " + comparingusers);
    		if(comparingusers > 0){
    			combinedConvoName = "@" + privateUser.getName() + "@" + frienduserName;
    		}
    		
    		else if(comparingusers < 0){//friend is before user
    			combinedConvoName = "@" + privateUser.getName() + "@" + frienduserName;
    		}
    		System.out.println("combined string = " + combinedConvoName);
			
			try{
				if (privateUser.windowIsOpen(combinedConvoName)){
	        		System.out.println("~~~~~~~IN START PRIVATE MESSSAGE. "
	        				+ "Window is open from double clicking"
	        				+ " user from 'Online user' list ~~~~~~~~");
				}
				else if (!privateUser.equals("null")){//make more sense in chatmeclient.newprivaterequest since it would retrieve old history from
	        		//data base, but since we're not going to implement that feature, then it's okay to leave here
					//MAKE THEM GO IN ALPHABETICAL ORDER
					MessageWindow mw = new MessageWindow(combinedConvoName,privateUser);
		        	mw.setTitle(frienduserName);
		        	privateUser.addToOpenConversations(mw);
		        	//privateUser.addToOnlineConversations(mw);
		        	privateUser.initiatePrivateConvoRequest(combinedConvoName);
				}
			} catch (NullPointerException npe){
				System.out.println(npe.getMessage());
			} catch(ArrayIndexOutOfBoundsException aiobe){
				JOptionPane.showMessageDialog(null,  
						"Nobody is online, can not chat.", 
						"ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class StartGroupMessage implements ActionListener{
		private User groupUser;
		StartGroupMessage(User user){
			this.groupUser = user;
		}
		public void actionPerformed(ActionEvent ae){
			String convoName = null;
			 try{
				 convoName = JOptionPane.showInputDialog(null, 
								"Group Message", 
								"Name Conversation:", 
								JOptionPane.OK_CANCEL_OPTION);
				Pattern p = Pattern.compile("[^a-zA-Z0-9]");
				boolean hasSpecialChar = p.matcher(convoName).find();
				//System.out.println("hasSpecialChar: " + hasSpecialChar);
				if(hasSpecialChar){//contains special characters
					 JOptionPane.showMessageDialog(null, "Must enter AlphaNumeric characters for Group"
							 + " Conversations!", 
							 "Group Message", JOptionPane.WARNING_MESSAGE);
					 throw new NullPointerException();
				}
				if(convoName == null){//just pressed X 
					throw new NullPointerException();
				}
				//NOT CREATING MESSAGE WINDOW B/C CLIENT WILL TELL USER TO DO THAT
				groupUser.initiateGroupConvoRequest(convoName);
				
			 } catch(NullPointerException npe){
				 System.out.println("NPE: " + npe.getMessage());
			 }
		}
	}

	private class iconButtonClass implements ActionListener{
		User us;
		public iconButtonClass(User u){
			this.us = u;
		}
		public void actionPerformed(ActionEvent e) {
			aboutMeAction(us);
		}
	}
	
	private class logoutAction implements ActionListener{
		User us;
		public logoutAction(User user){
			this.us = user;
		}
	
		public void actionPerformed(ActionEvent ae){
			ImageIcon message = new ImageIcon("Pictures/Message_Icon.png");
			int selection = JOptionPane.showConfirmDialog(null, 
					"Are you sure you want to Log Out?", "Log Out", 
					JOptionPane.YES_NO_OPTION);
			switch (selection) {
				case JOptionPane.YES_OPTION: // case JOptionPane.OK_OPTION is the same
					System.out.println("Yes, user wants to Log Out");
					us.signOut();
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("No, user doesn't want to Log Out");
					break;
			}
		}
	}

/*******FUNCTIONS FOR THE BUDDY LIST************/
/***********************************************/
/***********************************************/
	private void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	private void aboutMeAction(User onlineuser){//About me for Online User/Self
		System.out.println("The user you want info about is : " + onlineuser.getName());
		ImageIcon userIcon = new ImageIcon(onlineuser.getImagePath());
		JOptionPane.showMessageDialog(null, 
				onlineuser.getBio(), 
				"About: " + onlineuser.getName(), 
				JOptionPane.INFORMATION_MESSAGE, userIcon);
	}
	
	public void updateOnlineUser(){ // update Online User GUI
		inneronlineusersPanel.removeAll();
		for(int i = 0; i < user.getOnlineUsers().size(); i++){
			JButton OUButton = new JButton(user.getOnlineUsers().get(i));
			OUButton.setEnabled(true);
			OUButton.setBorderPainted(false);
			OUButton.addMouseListener(new mouseClassOnlineUser(user.getOnlineUsers().get(i), user));//PRIVATE CHATS
			inneronlineusersPanel.add(OUButton);
		}
		repaint();
	}
	
	public void updateActiveConversations(){//update Online Conversations GUI
		innerConvoPanel.removeAll();
		for(int i = 0; i < user.getConversations().size(); i++){
			JButton ACButton = new JButton(user.getConversations().get(i));
			ACButton.setEnabled(true);
			ACButton.setBorderPainted(true);
			ACButton.addMouseListener(new mouseClassActiveConvo(user.getConversations().get(i), user));//GROUP CHATS
			innerConvoPanel.add(ACButton);
		}
		repaint();
	}
	//method that start the message window and called by the user
	
}
	
