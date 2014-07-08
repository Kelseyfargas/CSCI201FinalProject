package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		JMenuItem startGroupMessageMI = new JMenuItem("Start Group Message");
		JMenuItem logOutMenuItem = new JMenuItem("Log Out");
		logOutMenuItem.addActionListener(new logoutAction(user));
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		startMessageMenuItem.addActionListener(new StartPrivateMessage(getUser(),PRIVATE_CHAT ));
		startGroupMessageMI.addActionListener(new StartGroupMessage(getUser(),GROUP_CHAT));
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
		fileMenu.add(startMessageMenuItem);
		fileMenu.add(startGroupMessageMI);
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
		messageButton.addActionListener(new StartPrivateMessage(getUser(), PRIVATE_CHAT));
		topPanel.add(messageButton);
		
		ImageIcon groupChatIcon = new ImageIcon("Pictures/GroupChat.png");
		JButton groupChatButton = new JButton(groupChatIcon);
		groupChatButton.setPressedIcon(groupChatIcon);
		groupChatButton.setSize(30,30);
		groupChatButton.setLocation(10,10);
		groupChatButton.setBorderPainted(false);
		groupChatButton.addActionListener(new StartGroupMessage(getUser(), GROUP_CHAT));//share action listener with group chat
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
	private class mouseClass extends MouseAdapter{
		private String name;
		private User u;
		private int messageType;
		mouseClass(String name, User us, int messageType){
			this.name = name;
			this.u = us;
			this.messageType = messageType;
		}
		public void mouseClicked(MouseEvent e){
	    	
	        if(e.getClickCount()==2){//double clicked
	        	//create a message w/ that user
	        	new MessageWindow(name, u, messageType);
	        }
	        else if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
	        	//if right click, then the about me should displayed
	        	int selection = JOptionPane.showConfirmDialog(BuddyList.this, 
	        			"About", "Are you sure you want to know their 'About Me'?", JOptionPane.YES_NO_OPTION);
	        	if(selection == 0){// yes
	        		System.out.println("Yes option");
	        		aboutMeAction(u);
	        	}
	        	else{//no option
	        		System.out.println("No option");
	        	}
	
	        }
	    }
	
	}

	private class StartPrivateMessage implements ActionListener{
		
		private User u;
		private int messageType;
		StartPrivateMessage(User user, int messageType){
			this.u = user;
			this.messageType = messageType;
		}
		
		public void actionPerformed(ActionEvent ae){
			Object[] onlinelist = user.getOnlineUsers().toArray();

			for(int o = 0; o < onlinelist.length; o++){
				System.out.println("Online user: " + onlinelist[o]);
			}

			String people = (String)JOptionPane.showInputDialog(BuddyList.this, 
			"Choose User to Start Chat!", 
			"Start Message", 
			JOptionPane.QUESTION_MESSAGE,
			null, // icon
			onlinelist, onlinelist[0]);
			try{
				if(!user.equals(null)){
				System.out.println("User selected is" + people);
				new MessageWindow(people,u,messageType);
				}
			} catch (NullPointerException npe){
				System.out.println(npe.getMessage());
			} catch(ArrayIndexOutOfBoundsException aiobe){
				System.out.println("NOBODY IS ONLINE NOOB");
				JOptionPane.showMessageDialog(null,  
						"Nobody is online, can not chat.", 
						"ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class StartGroupMessage implements ActionListener{
		private User u;
		private int messageType;
		StartGroupMessage(User user, int messageType){
			this.u = user;
			this.messageType = messageType;
		}
		public void actionPerformed(ActionEvent ae){
			 System.out.println("In StartGroupMessage AE " + messageType);
			 String convoName = createDialogeGroupMessage();
			 //new MessageWindow(convoName, u,messageType);
			 u.initiateGroupConvoRequest(convoName);
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
					System.out.println("Yes");
					us.signOut();
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("No");
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
	
	private User getUser(){
		return this.user;
	}
	
	private void aboutMeAction(User onlineuser){
        System.out.println("You right clicked, so It'll show the about me.");
		ImageIcon userIcon = new ImageIcon(onlineuser.getImagePath());
		JOptionPane.showMessageDialog(null, 
				onlineuser.getBio(), 
				"About: " + onlineuser.getName(), 
				JOptionPane.INFORMATION_MESSAGE, userIcon);
	}
	
	public String createDialogeGroupMessage(){
		String value = JOptionPane.showInputDialog(null, 
				"Group Message", 
				"Name Conversation:", 
				JOptionPane.QUESTION_MESSAGE);
		 MessageWindow mw = new MessageWindow(value, user, GROUP_CHAT);
		 user.addToOnlineConversations(mw);
		 return value;
	}
	
	public void updateOnlineUser(){
		inneronlineusersPanel.removeAll();
		for(int i = 0; i < user.getOnlineUsers().size(); i++){
			JButton OUButton = new JButton(user.getOnlineUsers().get(i));
			OUButton.setEnabled(true);
			OUButton.setBorderPainted(false);
			OUButton.addMouseListener(new mouseClass(user.getOnlineUsers().get(i), user,PRIVATE_CHAT));
			inneronlineusersPanel.add(OUButton);
		}
		repaint();
	}
	
	public void updateActiveConversations(){
		innerConvoPanel.removeAll();
		for(int i = 0; i < user.getConversations().size(); i++){
			JButton OUButton = new JButton(user.getConversations().get(i));
			OUButton.setEnabled(true);
			OUButton.setBorderPainted(false);
			OUButton.addMouseListener(new mouseClass(user.getConversations().get(i), user,GROUP_CHAT));
			innerConvoPanel.add(OUButton);
		}
		repaint();
	}
}
	
