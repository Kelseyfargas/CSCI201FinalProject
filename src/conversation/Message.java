package conversation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* This class is sent to the server and holds information about the message */
public class Message {
		private String conversationName;
		private String content;
		private String time;
	
	public Message(String content, String time, String conversationName) {
		this.content = content;
		this.time = time;
		this.conversationName = conversationName;
	}
	
	public Message( String content, String conversationName){
		this.content = content;
		this.conversationName = conversationName;
		this.time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());							 

	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getTime() {
		return this.time; 
	}
	
	public String getConversationName() {
		return this.conversationName;
	}
	
	public void print(){
		System.out.println("MESSAGE: ");
		System.out.println("Convo Name: " + conversationName);
		System.out.println("Content: " + content);
		System.out.println("Time: " + time);
	}
}
