package conversation;

import gui.CreateAccount;
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
	private CreateAccount createAccountWindow;
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
		createAccountWindow();
	}
	
	public void createAccountWindow() {
		 createAccountWindow =  new CreateAccount(this);
	}
	public LogIn getLoginWindow(){
		return loginWindow;
	}
	public void addClient(ChatMeClient client){
		this.chatClient = client;
		System.out.println("adding client");
	}
	
	public void createNewAccount() {
		client.sendCommand(NEW_ACCOUNT_REQUEST);
	}
	public int getSignal() {
		return signal;
	}

	public void setSignal(int command)  {
		signal = command; 
	}
	
	public void sendCreateNewAccountRequest() {
		
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
	
	public void setName(String name)	{
		this.name = name;
	}

	public String getName()		{
		return this.name;
	}

	public void setAboutme(String aboutme)	{
		this.aboutme = aboutme;
	}

	public String getAboutme()				{
		return this.aboutme;
	}
	public void setPassword(String password)	{
		this.password = password;
	}

	public String getPassword() 		{
		return this.password;
	}

	public void setImage(Image icon)		{
		this.icon = icon;
	}

	public Image getImage()				{
		return this.icon;
	}
}
