package conversation;

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
	
	public String getContent() {
		return this.content;
	}
	
	public String getTime() {
		return this.time; 
	}
	
	public String getconversationName() {
		return this.conversationName;
	}
	
	public void print(){
		
	}
}
