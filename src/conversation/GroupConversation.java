package conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


public class GroupConversation {
	private String conversationName;
	
	/* User -> String */
	private ArrayList<User> userList = new ArrayList<User>();										// list of user in current conversation
	
	 
	/*  Constructor 
	 * When conversation is created, the name of the conversation 
	 * should be passed in as String
	 * */ 
	public GroupConversation(User original_poster,User user,String conversationName) {					// conversation with one user			
		//this.original_poster = original_poster;
		this.conversationName = conversationName;
		//TODO: add to userlist 
		//userList.
	}
	
	public GroupConversation(User original_poster,User[] users) {				// group conversation
		//this.original_poster = original_poster;
		//TODO: add to userlist 
	}
	
	/* this method gets called from user class method (sendMessage). */
	public void storeConversation(String message)	{					
		//message_store.add(message);
	}
	
	/* returns all the messages exchanged so far (FUTURE FEATURE)
	public Vector<String> getHistory() {
		return this.message_store;
	}
	*/ 
}

