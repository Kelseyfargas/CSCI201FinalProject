package conversation;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public String getStatus() {
		return this.status;
	}
	
	public Image getIcon() {
		return this.icon;
	}
	
	public void addFriend(String username) {
		if(findUser(username)) {
			this.friendList.add(getUser(username));								// add as friend
			notifyNewAdd(username);				// notify friend that this user added you as friend
		}
		else {
			//TODO: display error message saying user does not exist
		}
	}
	
	/* Given a username, return a User object from database */
	public User getUser(String username)	{
		//TODO: return User object from database
	}
	/* Find friend using given username, return User object */
	public boolean findUser(String username) {
		//TODO: Find whether username exist in database. Return true if exist
		// If not exist, return null
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
		//TODO: receive message from server
	}
}

/* class ChatMeClient() ?? 
 * 
 * 
 * 
 * 
 * 
*/