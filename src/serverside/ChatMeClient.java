package serverside;

import java.awt.Image;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import conversation.Conversation;
import conversation.Message;
import conversation.User;

public class ChatMeClient {
	
	private User user;
	
	private Socket userRequestSocket;
	private ObjectInputStream userIn;
	private ObjectOutputStream userOut;
	private UserInputOutputClass uioclass;
	
	private Socket serverRequestSocket;
	private ObjectInputStream servIn;
	private ObjectOutputStream servOut;
	private ServerInputOutputClass sioclass;
	
	private Lock lock = new ReentrantLock();
	

	
	public ChatMeClient(String hostname) throws IOException{
		String ipAddress = "localhost";
		
		System.out.println("Connecting...");
		userRequestSocket = new Socket(ipAddress, 7777);
		serverRequestSocket = new Socket(ipAddress, 8888);
		System.out.println("Connection Successful...");
		
		userIn = new ObjectInputStream(userRequestSocket.getInputStream());
		userOut = new ObjectOutputStream(userRequestSocket.getOutputStream());
		
		servIn = new ObjectInputStream(serverRequestSocket.getInputStream());
		servOut = new ObjectOutputStream(serverRequestSocket.getOutputStream());
		
	}
	public void startUserIO(){
		uioclass = new UserInputOutputClass();
		uioclass.start();
		sioclass = new ServerInputOutputClass();
		sioclass.start();
	}
	public void addUser(User user){
		this.user = user;
	}
	public void sendCommand(int command){
		try{
			uioclass.sendCommandAndListen(command);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class UserInputOutputClass extends Thread {
		
		boolean continueRunning = true;
		
		public void readAndPrintWelcomeMessage() throws ClassNotFoundException, IOException{
			System.out.println("Attempting to read welcome message: \n");
			String message = (String) userIn.readObject();
			System.out.println(message);
		}
		public void run(){
			try{
				readAndPrintWelcomeMessage();
				while(continueRunning){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		
		private void sendCommandAndListen(int command) throws IOException, ClassNotFoundException{
			lock.lock();
			userOut.writeInt(command);
			if(command == ChatMeServer.LOGIN_REQUEST){
				loginRequest();
			}
			else if(command == ChatMeServer.NEW_USER_REQUEST){
				newUserRequest();
			}
			else if(command == ChatMeServer.SIGN_OUT_REQUEST){
				signOutRequest();
			}
			else if(command == ChatMeServer.NEW_GROUP_REQUEST){
				System.out.println("CLIENT ERROR: THIS SHOULD HAVE GONE TO ANOTHER METHOD");
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				newMessageRequest();
			}
			lock.unlock();
		}
		
		public void newGroupRequest(Conversation convo){
			//unfinished: , GUI Implementations, 
			String convoName = convo.getName();
			String moderator = convo.getModeratorName();
			userOut.writeObject(convoName);
			userOut.writeObject(moderator);
			userOut.flush();
			
			//Precaution: Recieve OK
			
			boolean OK = userIn.readBoolean();
			if(OK == true){
				//start new group conversation window
				user.buddyList.addGroupConvo(convoName, moderator);
			}
			else if(OK == false){
				//display error message I guess....
				System.out.println("Can't start new group convo");
			}
		}
		
		private void sendCommandAndObject(int command, Conversation convo) throws IOException{
			lock.lock();
			userOut.writeInt(command);
			if(command == ChatMeServer.NEW_GROUP_REQUEST){
				newGroupRequest(convo);
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				
			}
			lock.unlock();
		}
		
		public void loginRequest() throws IOException, ClassNotFoundException{
			//finished but needs database and GUI implementations
			System.out.println("CLIENT: log in request");
			String un = user.getName();
			String pw = user.getPassword();
			userOut.writeObject(un);
			userOut.writeObject(pw);
			userOut.flush();
			
			System.out.println("waiting . . .");
			boolean OK = userIn.readBoolean();
			if(OK == true)
			{
				System.out.println("you have been cleared to log in.");
				
				ArrayList<String> onlineUsers = (ArrayList<String>) userIn.readObject();
				for(int i=0; i< onlineUsers.size();i++){
					System.out.println("Online: " + onlineUsers.get(i));
				}
				user.setOnlineUsers(onlineUsers);
				user.createBuddyList();
			}
			else{
				System.out.println("Could not log in. Incorrect Credentials");
				user.incorrectInfoError();
			}
		}
		public void newUserRequest() throws IOException{
			//finished but needs database
			System.out.println("Got new user request on client");
			String username = user.getName();
			String password = user.getPassword();
			String aboutMe  = user.getAboutme();
			String imagePath	= user.getImagePath();
			
			System.out.println("writing username, password, aboutme, and image");
			userOut.writeObject(username);
			userOut.writeObject(password);
			userOut.writeObject(aboutMe);
			userOut.writeObject(imagePath);
			userOut.flush();
			boolean OK = userIn.readBoolean();
			if(OK == true)	{
				System.out.println("CLIENT: Creating buddy list");
				user.createBuddyList();
			}
			else {
				System.out.println("This user name is already taken!!!");
				user.nameExistError();
			}
		}
		public void signOutRequest() throws IOException{
			//unfinished
			userOut.writeObject(user.getName());
			userOut.flush();
			//user.signOut(); // write this
			System.out.println(user.getName() + " has signed out...");
		}
		public void newMessageRequest(){
			//unfinished
			System.out.println("Client: NEW_MESSAGE_REQUEST");
			Message msg = user.getMessagePacket();
			userOut.writeObject(msg);
			userOut.flush();
			
			//our gui will be updated later on
			
			/*String chatName = scan.nextLine();
			//String chatName = user.loginWindow.getUsername();
			System.out.println("Enter Content: ");
			String content = scan.nextLine();
			//String content = user.loginWindow.getPassword();
			Message msg = new Message(content, chatName);
			System.out.println("\nSending Message Packet...");
			userOut.writeObject(msg);*/
			System.out.println("Finished.");
		}
		
		
	}

	class ServerInputOutputClass extends Thread{
		
		public void run(){
			
			while(true){
				try {
					int command = servIn.readInt();
					handleCommand(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void handleCommand(int command){
			//do something
			if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				
			}
		}
	}
	
	public static void main(String [] args){
		try{
			User user = new User();
			System.out.println("The path of the image is :" + user.getImagePath());
			ChatMeClient cme = new ChatMeClient("localhost");
			user.addClient(cme);
			cme.addUser(user);
			cme.startUserIO();
			
			//client.sendCommand(NEW_USER_REQUEST)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
