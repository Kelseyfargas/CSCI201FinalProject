package serverside;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatMeServer {

	Database database;
	public ChatMeServer(int port){
		/* Database upload */
		
		database = recoverDatabase();
		
		try {
			ServerSocket ss = new ServerSocket(port);
			while(true){
				Socket s = ss.accept();
				handleSocket(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOE: " + e.getMessage());
		}
	}
	
	private Database recoverDatabase() {
		boolean DATABASE_EXISTS = false; //Remove this during correct implementation


		if(DATABASE_EXISTS){
			//return Database from FILE
		}
		else{
			Database database = new Database();
			return database;
		}
		return database; // This should also be changed to take either existing or new database

	}

	private void handleSocket(Socket s){
		//Create and Start new Chat Thread
		ChatThread ct = new ChatThread(s);
		ct.start();
	}
	
	public static void main(String [] args){
		new ChatMeServer(1111);
	}
	/* Chat Thread Class */
	class ChatThread extends Thread {
		private Socket s;
		public ChatThread(Socket s){
			this.s = s;
			
		}
		public void run(){

			while(true){
				/* Listens */
				
				/* *********************** *
				 * 	1. Log In / New User   *
				 *  2. Start New Convo     *
				 *  3. Send/Receive Msg    *
				 *  4  . . .			   *
				 * *********************** */
			}
		}
	}
}
class Database {
	//super fake
}
