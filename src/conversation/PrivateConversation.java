package conversation;

import java.util.ArrayList;

public class PrivateConversation extends Conversation {
	private String conversationName;
	private ArrayList<String> userList = new ArrayList<String>();										// list of user in current conversation
	private String content;	
	
	public PrivateConversation(String conversationName, User user) {					// conversation with one user			
		this.conversationName = conversationName; 
	}
	public void addMessage(String message)		{
		content += message;
	}
}
