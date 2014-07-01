package conversation;

/* This class is sent to the server and holds information about the message */
public class Message {
		private String sender;
		private String content;
		private String time;
	
	public Message(String sender, String content, String time) {
		this.sender = sender;
		this.content = content;
		this.time = time;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getTime() {
		return this.time; 
	}
}
