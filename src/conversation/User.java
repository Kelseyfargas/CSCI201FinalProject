package conversation;

import java.awt.Image;
import java.util.Vector;


/* Recieve and send convos to server */
public class User {
	private String name;
	private String aboutme;
	private String password;
	private String status;
	private Image icon;
	private Vector<User> friendList;
	private Vector<GroupConversation> convo;								
	
	/* Constructor */
	public User(String name,String password,String status, Image icon) {
		this.name = name;
		this.password = password;
		this.status = status;
		this.icon = icon;
	}
	
	public	String getName() {
		return this.name;
	}
	
	public void addFriend(String username) {
		User friend = findUser(username);
		this.friendList.add(friend);								// add as friend
		friend.notifyNewAdd(username);				// notify friend that this user added you as friend
	}
	
	public User findUser(String username) {
		//TODO: find whether given username exists in data base
	}
	
	/* display a gui(dialogbox) that a user added me as a friend*/
	public void notifyNewAdd(String username) 	{
		//TODO: display gui 
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
	
	/* chech whether previous conversation exists with given user */
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
		//TODO: create Message class and send Message class to server 
		String time = null;							// TODO: how to store time information 
		new Message(this.getName(),message,time);
		convo.storeConversation(message);
	}
	
	/* Generate ChatMe window after logging in */
	public void generateChatMeWindow() {
		// new ChatMeFrame();		
	}
	
	/*Update the display after receiving a message from server */
	public void displayMessage()	{
		//TODO: receive message from server
	}
}
