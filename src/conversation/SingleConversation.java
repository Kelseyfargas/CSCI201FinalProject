package conversation;

import java.util.ArrayList;

public class SingleConversation extends Conversation {
	private String conversationName;
	private ArrayList<String> userList = new ArrayList<String>();										// list of user in current conversation
	private String content;	
	private User moderator; 
	
	public SingleConversation(String conversationName, User user) {					// conversation with one user			
		this.conversationName = conversationName; 
	}
	public void addMessage(String message)		{
		content += message;
	}
}
