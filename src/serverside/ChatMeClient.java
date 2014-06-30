package serverside;

import java.io.IOException;
import java.net.Socket;

public class ChatMeClient {
	User user;
	public ChatMeClient(String hostname, int port, User user){
		this.user = user;
		try{
			Socket s = new Socket(hostname, port);
			handleSocket(s);
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}

	private void handleSocket(Socket s) {
		while (true) {
			/* Listening Cycle*/
			
			/* * * * * * * * * * * * * * * * * * * * * * * * * *
			 * Similar to ChatMeServer, lots of If Statements  *
			 * * * * * * * * * * * * * * * * * * * * * * * * * */
		}
	}
	public static void main(String [] args){
		/* host name NEEDS to be a string*/
		User me = new User();
		new ChatMeClient("localhost", 1111, me);
	}

}
class User{
	//super fake
}
