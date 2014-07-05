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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Icon;

public class ChatMeServer {
	
	public static int NEW_USER_REQUEST = 0;
	public static int LOGIN_REQUEST = 1;
	public static int SIGN_OUT_REQUEST = 2;
	public static int NEW_MESSAGE_REQUEST = 3;
	public static int INVITE_CHAT_REQUEST = 4;
	//public static int 

	Database database;

	private Socket userReqSocket;
	private ObjectOutputStream userOut;
	private ObjectInputStream userIn;
	private Socket servReqSocket;
	private ObjectOutputStream servOut;
	private ObjectInputStream servIn;
	
	private Lock lock= new ReentrantLock();
	
	public ChatMeServer() throws IOException{
		printDbg("Starting server...");
		ServerSocket ss1 = new ServerSocket(7777);
		ServerSocket ss2 = new ServerSocket(8888);
		printDbg("Server started...");
		
		while(true){
			userReqSocket = ss1.accept();
			servReqSocket = ss2.accept();
			
			printDbg("Connection from: " + userReqSocket.getInetAddress());
			
			userOut = new ObjectOutputStream(userReqSocket.getOutputStream());
			userIn = new ObjectInputStream(userReqSocket.getInputStream());
			
			servOut = new ObjectOutputStream(servReqSocket.getOutputStream());
			servIn = new ObjectInputStream(servReqSocket.getInputStream());
			
			UserReqThread ct = new UserReqThread(userIn, userOut);
			ct.start();
		}		
	}
	

	public static void printDbg(String message) {
		System.out.println(Thread.currentThread().toString() + message);
	}
	public static void main(String [] args) throws IOException{
		new ChatMeServer();
	}

	class UserReqThread extends Thread {

		private ObjectOutputStream threadUserOut;
		private ObjectInputStream threadUserIn;
		
		public UserReqThread(ObjectInputStream in, ObjectOutputStream out) throws IOException{
			threadUserIn = in;
			threadUserOut = out;
		}
		
		public void run(){	
				
			//1. Send Welcome Message
			String message = "Welcome. Please Enter a Command.";
			try {
				printDbg("Welcome message Sent\n");
				threadUserOut.writeObject(message);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//2. Listen for Signal
			printDbg("SERVER: Listening for command");
			while(true){
				try {
					/* */
					int command = threadUserIn.readInt();
					handleCommand(command);
					
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					threadUserOut = null;
					threadUserIn = null;
				}
				
			}
		}
		private void handleCommand(int command) throws IOException, ClassNotFoundException {
			Scanner scan = new Scanner(System.in);
			printDbg("SERVER: parsing command...");
			if(command==LOGIN_REQUEST){
				printDbg("Command recieved on server: Login\n");
				printDbg("Reading in: " + threadUserIn.readObject());
				printDbg("Reading in: " + threadUserIn.readObject());
				printDbg("Does this look correct? (1) Yes. (2)No.\n");
				int response = scan.nextInt();
				
				//debug
				ArrayList<String> strArr = new ArrayList<String>();
				strArr.add("RyanC");
				strArr.add("RyanJ");
				strArr.add("Katrina");
				strArr.add("Kelsey");
				
				if(response == 1){
					printDbg("Giving OK to log in.");
					printDbg("Attempting to send online Users");
					threadUserOut.writeBoolean(true);
					threadUserOut.flush();
					threadUserOut.writeObject(strArr);
					printDbg("Finished command");
					
					
					return;
				}
				else{
					printDbg("Denying user.");
					threadUserOut.writeBoolean(false);
					threadUserOut.flush();
					printDbg("Finished command");
				}
			}
			if(command == SIGN_OUT_REQUEST){
				printDbg("Command recieved on server: Sign Out");	
			}
			if(command == NEW_USER_REQUEST){
				printDbg("SERVER: Command recieved on server: New User");
				String username = (String) threadUserIn.readObject();
				String password = (String) threadUserIn.readObject();
				String bio 	    = (String) threadUserIn.readObject();
				Icon   img 		= (Icon)   threadUserIn.readObject();
				printDbg("SERVER READS:"
						+ username + " " + password + " " + bio + " " + img);
			}
			if(command == NEW_MESSAGE_REQUEST){
				printDbg("Command recieved: New Message");
				printDbg("Reading message . . .");
				Message msg = (Message) threadUserIn.readObject();
				msg.print();
				printDbg("Finished command");
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

