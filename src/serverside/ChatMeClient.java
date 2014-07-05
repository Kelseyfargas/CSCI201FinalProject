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
		
		public void run(){

			try{
				System.out.println("Attempting to read welcome message: \n");
				String message = (String) userIn.readObject();
				System.out.println(message);
				
				while(continueRunning){
					//int readCommandFromServer = userIn.readInt();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
			Scanner scan = new Scanner(System.in); //For Debug Purposes
			if(command == ChatMeServer.LOGIN_REQUEST){
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
					user.createBuddyList();
				}
				else{
					System.out.println("Could not log in. Incorrect Credentials");
				}
			}
			else if(command == ChatMeServer.NEW_USER_REQUEST){
				System.out.println("Got new user request on client");
				String username = user.getName();
				String password = user.getPassword();
				String aboutMe  = user.getAboutme();
				Icon image	= user.getImage();
				
				System.out.println("writing username, password, aboutme, and image");
				userOut.writeObject(username);
				userOut.writeObject(password);
				userOut.writeObject(aboutMe);
				userOut.writeObject(image);
				// ^^^ psuedo
				/* Needs Finishing*/
				
				
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				System.out.println("Enter ChatName: ");
				String chatName = scan.nextLine();
				System.out.println("Enter Content: ");
				String content = scan.nextLine();
				Message msg = new Message(chatName, content);
				System.out.println("\nSending Message Packet...");
				userOut.writeObject(msg);
				System.out.println("Finished.");
			}
			lock.unlock();
		}
	}

	class ServerInputOutputClass extends Thread{
		
		public void run(){
//			
		}
		
		public void handleCommand(int command){
			
		}
	}
	public static void main(String [] args){
		try{
			User user = new User();
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
