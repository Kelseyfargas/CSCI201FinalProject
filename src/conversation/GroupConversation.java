package conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


public class GroupConversation extends Conversation {
	private String conversationName;
	private ArrayList<String> userList = new ArrayList<String>();										// list of user in current conversation
	private String content;	
	private User moderator; 

	public GroupConversation(String conversationName, User user) {					// conversation with one user			
		this.conversationName = conversationName;	
		moderator = user; 
	}
	
	public void addMessage(String message) {
		content += message; 
	}
}

