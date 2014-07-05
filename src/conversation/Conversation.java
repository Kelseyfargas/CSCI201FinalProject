package conversation;

import java.util.ArrayList;

public abstract class Conversation {
	private String conversationName;
	private ArrayList<String> userList;										
	private String content;	
	private User moderator;
	public abstract void addMessage(String message);
}
