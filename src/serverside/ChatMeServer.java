package serverside;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatMeServer {
	
	public static int NEW_USER_REQUEST = 0;
	public static int LOGIN_REQUEST = 1;
	public static int SIGN_OUT_REQUEST = 2;

	Database database;
	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;
	
	public ChatMeServer(int port) throws IOException{
		System.out.println("Starting server...");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started...");
		
		while(true){
			socket = serverSocket.accept();
			System.out.println("Connection from: " + socket.getInetAddress());
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			ChatThread ct = new ChatThread(out, in);
			ct.run();
			
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


	public static void main(String [] args) throws IOException{
		new ChatMeServer(7777);
	}
	/* Chat Thread Class */
	class ChatThread extends Thread {
		
		DataOutputStream out;
		DataInputStream in;
		public ChatThread(DataOutputStream out, DataInputStream in){
			this.out = out;
			this.in = in;
		}
		public void run(){
			while(true){
				
				//1. Send Welcome Message
				String message = "Welcome. Please Enter a Command.";
				try {
					System.out.println("Welcome message Sent");
					out.writeUTF(message);
					
				} catch (IOException e1) {
	
					e1.printStackTrace();
				}
				
				
				try {
					int command = in.readInt();
					
					if(command == NEW_USER_REQUEST){
						System.out.println("Command recieved: New User");
					}
					if(command==LOGIN_REQUEST){
						System.out.println("Command recieved: Login");
					}
					if(command == SIGN_OUT_REQUEST){
						System.out.println("Command recieved: Sign Out");	
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.out = null;
					this.in = null;
				}
				
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
