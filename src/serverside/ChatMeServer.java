package serverside;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class ChatMeServer {
	
	public static int NEW_USER_REQUEST = 0;
	public static int LOGIN_REQUEST = 1;
	public static int SIGN_OUT_REQUEST = 2;
	public static int NEW_MESSAGE_REQUEST = 3;

	Database database;
	static ServerSocket serverSocket;
	static Socket socket;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	public ChatMeServer(int port) throws IOException{
		System.out.println("Starting server...");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started...");
		
		while(true){
			socket = serverSocket.accept();
			System.out.println("Connection from: " + socket.getInetAddress());
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			ChatThread ct = new ChatThread(socket, out, in);
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
		
		ObjectOutputStream out;
		ObjectInputStream in;
		Socket s;
		public ChatThread(Socket s, ObjectOutputStream out, ObjectInputStream in){
			this.out = out;
			this.in = in;
			this.s = s;
		}
		public void run(){
			
				
			//1. Send Welcome Message
			String message = "Welcome. Please Enter a Command.";
			try {
				System.out.println("Welcome message Sent\n");
				out.writeObject(message);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				
			//2. Listen for Signal
			while(true){
				try {
					int command = in.readInt();
					handleCommand(command);
					
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.out = null;
					this.in = null;
				}
				
			}
		}
		private void handleCommand(int command) throws IOException, ClassNotFoundException {
			Scanner scan = new Scanner(System.in);
			if(command == NEW_USER_REQUEST){
				System.out.println("Command recieved: New User");
				
			}
			if(command==LOGIN_REQUEST){
				System.out.println("Command recieved: Login\n");
				System.out.println("Reading in: " + in.readObject());
				System.out.println("Reading in: " + in.readObject());
				System.out.println("Does this look correct? (1) Yes. (2)No.\n");
				int response = scan.nextInt();
				
				String [] strArr = new String []{"RyanC", "RyanJ", "Katrina", "Kelsey"};
				if(response == 1){
					System.out.println("Giving OK to log in.");
					System.out.println("Attempting to send online Users");
					out.writeBoolean(true);
					out.flush();
					out.writeObject(strArr);
					System.out.println("Finished command");
					return;
				}
				else{
					System.out.println("Denying user.");
					out.writeBoolean(false);
					out.flush();
					System.out.println("Finished command");
				}
			}
			if(command == SIGN_OUT_REQUEST){
				System.out.println("Command recieved: Sign Out");	
			}
			if(command == NEW_MESSAGE_REQUEST){
				System.out.println("Command recieved: New Message");
				System.out.println("Reading message . . .");
				Message msg = (Message) in.readObject();
				msg.print();
				System.out.println("Finished command");
			}
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

