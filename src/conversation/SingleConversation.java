package conversation;

import java.util.ArrayList;

public class SingleConversation extends GroupConversation {
	private User original_poster;											// User object that started conversation
	private String conversationName;
	private ArrayList<User> userList = new ArrayList<User>();										// list of user in current conversation
	private ArrayList<String> message_store = new ArrayList<String>();								// store all the conversation between users
	 
	public SingleConversation(User original_poster,User user,String conversationName) {					// conversation with one user			
		super(original_poster,user,conversationName);
		this.original_poster = original_poster;
		this.conversationName = conversationName;
	}
}
