package conversation;

import gui.LogIn;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;


/* Recieve and send convos to server */
public class User {
	private int signal = 1;
	/*
	static int  
	static int 
	static int 
	static int 
	static int
	*/ 
	private JFrame buddyList;
	private JFrame loginWindow;
	private JFrame chatWindow; 
	private String name;
	private String aboutme;
	private String password;
	private String status;
	private Image icon;
	private Vector<User> friendList;
	private Vector<GroupConversation> convo;								
	
	/* Constructor */
	public User() {
		loginWindow = new LogIn("");  				// create log in GUI (First GUI of the program)
		buddyList = null;
		chatWindow = null; 
	}
	
	public int getSignal() {
		return signal;
	}
	
	public void setSignal(int command)  {
		signal = command; 
	}
	
	/* Called from client */
	public void addFriend(String username, Image img, boolean status) {
		 UpdateFriendList(username,img,status);
	}

	/* display a gui(dialogbox) that a user added me as a friend*/
	public void notifyNewAdd(String username) 	{
		//TODO: display gui for new add notification
	}

	/* if conversation exist with given user, resume
	 * Else start new conversation with given user */
	public void startNewMessage(User user) {
		if(conversationExist(user.getName())) {
			resumeConversation(user);
		}
		else {
			newConversation(user);
		}
	}
	
	public void startNewMessage(User[] users) {
			
	}
	
	/* check whether previous conversation exists with given user */
	public boolean conversationExist(String username) {
		//TODO: check if conversation exist 
	}
	
	public void resumeConversation(User user) {
		//TODO: resume convo
	}
	
	public void newConversation(User user) {
		//TODO: start new convo
	}
	
	/* send message to the users in conversation */
	public void sendMessage(String message, GroupConversation convo)	{
		String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());							 
		new Message(this.getName(),message,time);							// create new message class 
		//TODO: send message class to server 
	}
	
	/* Generate ChatMe window after logging in */
	public void generateChatMeWindow() {
		// new ChatMeFrame();		
	}
	
	/*Update the display after receiving a message from server */
	public void displayMessage()	{
		//TODO: receive message from server and display on gui
	}
	
	/*public	String getName() {
		return this.name;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public Image getIcon() {
		return this.icon;
	}
	
	public void setSignal() {
		
	} */
}

/* class ChatMeClient() ?? 
 * 
 * 
 * 
 * 
 * 
*/