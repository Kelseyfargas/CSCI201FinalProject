package conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


public class GroupConversation {
	private User original_poster;											// User object that started conversation
	private String conversationName;
	private ArrayList<User> userList = new ArrayList<User>();										// list of user in current conversation
	private ArrayList<String> message_store = new ArrayList<String>();								// store all the conversation between users
	 
	/*  Constructor 
	 * When conversation is created, the name of the conversation 
	 * should be passed in as String
	 * */ 
	public GroupConversation(User original_poster,User user,String conversationName) {					// conversation with one user			
		this.original_poster = original_poster;
		this.conversationName = conversationName;
		//TODO: add to userlist 
	}
	
	public GroupConversation(User original_poster,User[] users) {				// group conversation
		this.original_poster = original_poster;
		//TODO: add to userlist 
	}
	
	/* this method gets called from user class method (sendMessage). */
	public void storeConversation(String message)	{					
		message_store.add(message);
	}
	
	/* returns all the messages exchanged so far (FUTURE FEATURE)
	public Vector<String> getHistory() {
		return this.message_store;
	}
	*/ 
}

