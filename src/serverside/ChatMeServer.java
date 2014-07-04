package serverside;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.Icon;

public class ChatMeServer {
	
	public static int NEW_USER_REQUEST = 0;
	public static int LOGIN_REQUEST = 1;
	public static int SIGN_OUT_REQUEST = 2;
	public static int NEW_MESSAGE_REQUEST = 3;

	Database database;

	private Socket userReqSocket;
	private ObjectOutputStream userOut;
	private ObjectInputStream userIn;
	
	private Socket servReqSocket;
	private ObjectOutputStream servOut;
	private ObjectInputStream servIn;
	
	public ChatMeServer() throws IOException{
		System.out.println("Starting server...");
		ServerSocket ss1 = new ServerSocket(7777);
		ServerSocket ss2 = new ServerSocket(8888);
		System.out.println("Server started...");
		
		while(true){
			userReqSocket = ss1.accept();
			//servReqSocket = ss2.accept();
			System.out.println("Connection from: " + userReqSocket.getInetAddress());
			
			userOut = new ObjectOutputStream(userReqSocket.getOutputStream());
			userIn = new ObjectInputStream(userReqSocket.getInputStream());
			
			//servOut = new ObjectOutputStream(servReqSocket.getOutputStream());
			//servIn = new ObjectInputStream(servReqSocket.getInputStream());
			
			UserReqThread ct = new UserReqThread(userOut, userIn);
			ct.start();
			
		}
		
	}
	

	public static void main(String [] args) throws IOException{
		new ChatMeServer();
	}

	class UserReqThread extends Thread {
		
		ObjectOutputStream userOut;
		ObjectInputStream userIn;

		public UserReqThread(ObjectOutputStream userOut, ObjectInputStream userIn){
			this.userOut = userOut;
			this.userIn = userIn;
		}
		public void run(){
			
				
			//1. Send Welcome Message
			String message = "Welcome. Please Enter a Command.";
			try {
				System.out.println("Welcome message Sent\n");
				userOut.writeObject(message);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//2. Listen for Signal
			System.out.println("SERVER: Listening for command");
			while(true){
				try {
					/* */
					int command = userIn.readInt();

					handleCommand(command);
					
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.userOut = null;
					this.userIn = null;
				}
				
			}
		}
		private void handleCommand(int command) throws IOException, ClassNotFoundException {
			Scanner scan = new Scanner(System.in);
			System.out.println("SERVER: parsing command...");
			if(command==LOGIN_REQUEST){
				System.out.println("Command recieved on server: Login\n");
				System.out.println("Reading in: " + userIn.readObject());
				System.out.println("Reading in: " + userIn.readObject());
				System.out.println("Does this look correct? (1) Yes. (2)No.\n");
				int response = scan.nextInt();
				
				//debug
				ArrayList<String> strArr = new ArrayList<String>();
				strArr.add("RyanC");
				strArr.add("RyanJ");
				strArr.add("Katrina");
				strArr.add("Kelsey");
				
				if(response == 1){
					System.out.println("Giving OK to log in.");
					System.out.println("Attempting to send online Users");
					userOut.writeBoolean(true);
					userOut.flush();
					userOut.writeObject(strArr);
					System.out.println("Finished command");
					
					
					return;
				}
				else{
					System.out.println("Denying user.");
					userOut.writeBoolean(false);
					userOut.flush();
					System.out.println("Finished command");
				}
			}
			if(command == SIGN_OUT_REQUEST){
				System.out.println("Command recieved on server: Sign Out");	
			}
			if(command == NEW_USER_REQUEST){
				System.out.println("SERVER: Command recieved on server: New User");
				String username = (String) userIn.readObject();
				String password = (String) userIn.readObject();
				String bio 	    = (String) userIn.readObject();
				Icon   img 		= (Icon)   userIn.readObject();
				System.out.println("SERVER READS:"
						+ username + " " + password + " " + bio + " " + img);
			}
			if(command == NEW_MESSAGE_REQUEST){
				System.out.println("Command recieved: New Message");
				System.out.println("Reading message . . .");
				Message msg = (Message) userIn.readObject();
				msg.print();
				System.out.println("Finished command");
			}
		}
	}
	class ServReqThread extends Thread{
		
		ObjectOutputStream servOut;
		ObjectInputStream  servIn;
		
		public ServReqThread(ObjectOutputStream servOut, ObjectInputStream userIn){
			this.servOut = servOut;
			this.servIn  = servIn;
		}
		public void run(){
			//Listen for Commands from database
		}
	}


}
class Database {
	//super fake
}
class Message implements Serializable {
	String name;
	String content;
	String time;
	public Message (String name, String content){
		this.name = name;
		this.content = content;
		this.time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());	
	}
	public void print(){
		System.out.println("(" + time + ") " + name);
		System.out.println(content);
	}
	
}

