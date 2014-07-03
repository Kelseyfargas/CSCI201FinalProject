package conversation;

import gui.LogIn;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;

import serverside.ChatMeClient;


/* Recieve and send convos to server */
public class User {
	private int signal = 1;
	public  Message messagePackage;

	/*
	static int  
	static int 
	static int 
	static int
	*/ 
	private JFrame buddyList;
	private LogIn loginWindow;
	private JFrame chatWindow; 
	private String name;
	private String aboutme;
	private String password;
	private String status;
	private Image icon;
	private ArrayList<User> friendList;
	private ArrayList<GroupConversation> currentConversations;		// change to Conversation 						
	private ChatMeClient chatClient;
	/* Constructor */
	public User() {
		
		loginWindow = new LogIn("Test");  				// create log in GUI (First GUI of the program)
		buddyList = null;
		chatWindow = null; 
	}
	public LogIn getLoginWindow(){
		return loginWindow;
	}
	public void addClient(ChatMeClient client){
		this.chatClient = client;
		System.out.println("adding client");
	}
	public int getSignal() {
		return signal;
	}
	
	public void setSignal(int command)  {
		signal = command; 
	}
	
	/* Deleted Feature! */
	public void addFriend(String username, Image img, boolean status) {
		 //buddyList.updateFriendList(username,img,status);
	}
	// update design document (notifynewAdd deleted) //
	
	
	public void startNewMessage(String conversationName) {					//single conversation
		//chatWindow.beginConversation(conversationName);
	}
	
	/* send message to the users in conversation */
	public void sendMessage(String message, GroupConversation convo)	{
		String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());							 
		//new Message(message,time,conversationName);							// create new message class 
		//TODO: send message class to server 
	}
}
