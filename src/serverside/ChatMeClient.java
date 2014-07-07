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
import conversation.GroupConversation;
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
	public void sendCommand(int command, GroupConversation convo){
		try{
			uioclass.sendCommandAndObject(command, convo);
		} catch( Exception e){
			e.printStackTrace();
		}
	}
	public void sendCommand(int command, Message msg){
		try{
			uioclass.sendCommandAndObject(command, msg);
		} catch( Exception e){
			e.printStackTrace();
		}
	}
	
	class UserInputOutputClass extends Thread {
		/* * * * * * * * * * * * * * * * * * * *
		 * 	 BEGINNING of USER Request Thread  *
		 * * * * * * * * * * * * * * * * * * * */
		boolean continueRunning = true;
		/* Welcom Message*/
		public void readAndPrintWelcomeMessage() throws ClassNotFoundException, IOException{
			System.out.println("Attempting to read welcome message: \n");
			String message = (String) userIn.readObject();
			System.out.println(message);
		}
		/* * * * * * * * * * * * * * * * * *
		 * 	 Thread LISTEN for USER block  *
		 * * * * * * * * * * * * * * * * * */
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
		
		/* * * * * * * * * * * * * * * * * * * * *
		 * 	 Thread DO request from USER block   *
		 * * * * * * * * * * * * * * * * * * * * */
		//Each Helper function should contain a comment saying finished, or unfinished, then specifying what needs to be done
		/* Command doesn't need arguments */
		private void sendCommandAndListen(int command) throws IOException, ClassNotFoundException{
			lock.lock();
			userOut.writeInt(command);
			userOut.flush();
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
				//debug
				System.out.println("CLIENT ERROR: THIS SHOULD HAVE GONE TO 'sendCommandAndObject'");
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				//debug
				System.out.println("CLIENT ERROR: THIS SHOULD HAVE GONE TO 'sendCommandAndObject'");
			}
			lock.unlock();
		}
		public void loginRequest() throws IOException, ClassNotFoundException{
			//finished but needs database and GUI implementations
			//Also, remove section: "TESTING NEW CONVO CODE"
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
				
				user.createBuddyList();
				
				
					//Thread.sleep(1000);
					System.out.println("TESTING NEW CONVO CODE");
					System.out.println("user name is: " + user.getName());
					sendCommand(ChatMeServer.NEW_GROUP_REQUEST, new GroupConversation("Butt", user));
				
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
			if(OK == false)	{
				System.out.println("CLIENT: Username is not taken :D ");
			}
			else {
				System.out.println("This user name is already taken!!!");
				user.nameExistError();
			}
		}
		public void signOutRequest() throws IOException{
			//unfinished, see comment
			userOut.writeObject(user.getName());
			userOut.flush();
			//user.signOut(); // write this
			System.out.println(user.getName() + " has signed out...Write Code!");
		}
		
		
		/* Command does need arguments */
		//Takes Conversation as parameter
		public void sendCommandAndObject(int command, GroupConversation convo) throws IOException{
			lock.lock();
			userOut.writeInt(command);
			if(command == ChatMeServer.NEW_GROUP_REQUEST){
				newGroupRequest(convo);
			}
			else if(command == ChatMeServer.END_GROUP_REQUEST){
				endGroupRequest(convo);
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				//Figure this out
			}
			lock.unlock();
		}
		public void newGroupRequest(GroupConversation convo) throws IOException{
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
				System.out.println("User will then add convo to buddy list. Write Code!");
///////////////////user.addGroupConvo(convoName, moderator);// <-- needs to be written
			}
			else if(OK == false){
				//display error message I guess....
				System.out.println("Can't start new group convo!!!");
			}
		}
		public void endGroupRequest(GroupConversation convo) throws IOException{
			//unfinished, needs GUI implementation
			String convoName = convo.getName();
			String moderator = convo.getModeratorName();
			userOut.writeObject(convoName);
			userOut.writeObject(moderator);
			userOut.flush();
			/* NOTE: THIS METHOD IS CALLED UNDER THE ASSUMPTION THAT ONLY THE MODERATOR 
			 * COULD HAVE MADE THIS CALL. CHECK INSIDE GUI IMPLEMENTATION*/
			
			//Precaution: Recieve OK
			boolean OK = userIn.readBoolean();
			if(OK == true){
				//unfinished, GUI --> remove new group conversation window
				//rewrite line below here
				System.out.println("ending group convo. write code!");
////////////////user.removeGroupConvo(convoName, moderator);
			}
			else if(OK == false){
				//display error message I guess....
				System.out.println("Can't remove group convo...");
			}
		}
		
		//Takes Message as parameter
		public void sendCommandAndObject(int command, Message msg) throws IOException{
			lock.lock();
			if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				newMessageRequest(msg);
			}
			lock.unlock();
		}
		public void newMessageRequest(Message msg) throws IOException{
			//unfinished, needs GUI implemnetation in Server Request Thread
			System.out.println("Client: NEW_MESSAGE_REQUEST");
			
			userOut.writeObject(msg);//  all of this needs to be written
			userOut.flush();
			
			//gui must be updated
		}
		
		/* * * * * * * * * * * * * * * * *
		 * 	 END of USER Request Thread  *
		 * * * * * * * * * * * * * * * * */
	}

	class ServerInputOutputClass extends Thread{
		/* * * * * * * * * * * * * * * * * * * * *
		 * 	 BEGINNING of SERVER Request Thread  *
		 * * * * * * * * * * * * * * * * * * * * */
		
		/* * * * * * * * * * * * * * * * * * *
		 * 	 Thread LISTEN for SERVER block  *
		 * * * * * * * * * * * * * * * * * * */
		public void run(){
			
			while(true){
				try {
					int command = servIn.readInt();
					handleCommand(command);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					servIn = null;
				}
			}
			
		}
		/* * * * * * * * * * * * * * * * * * * * *
		 * 	 Thread DO action from SERVER block  *
		 * * * * * * * * * * * * * * * * * * * * */
		public void handleCommand(int command) throws ClassNotFoundException, IOException{
			
			if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				//getting a new-message-request here means the gui needs to update
				
				//unfinished, see comment
				Message msg = (Message) servIn.readObject();
				System.out.println("You have the message now do something with it. Write COoooOoOde");
				//user.incomingMessageUpdate(msg);
			}
			else if(command == ChatMeServer.NEW_GROUP_REQUEST){
				//getting a new-group-request here means the gui needs to add a new convo
				newGroupRequest();
			}
			else if(command == ChatMeServer.END_GROUP_REQUEST){
				//gui needs to take off a convo (it's been deleted)
				endGroupRequest();
			}
			else if(command == ChatMeServer.UPDATE_ONLINE_USERS_REQUEST){
				System.out.println("Reading global update online user request...");
				ArrayList<String> onlineUsers = (ArrayList<String>)servIn.readObject();
				user.setOnlineUsers(onlineUsers);
			}
		}
		public void newGroupRequest() throws ClassNotFoundException, IOException{
			//unfinished see comment
			String convoName = (String) servIn.readObject();
			String moderator = (String) servIn.readObject();
			System.out.println("user.addConvo;i;ijjij WRITE");
/////////////user.addGroupConvo(convoName, moderator);
		}
		public void endGroupRequest() throws ClassNotFoundException, IOException{
			//unfinished, see comment
			String convoName = (String) servIn.readObject();
			String moderator = (String) servIn.readObject();
			System.out.println("user.removeConvo boiaeijf. write!!!111!!");
/////////////user.removeGroupConvo(convoName, moderator);

		}
		
		/* * * * * * * * * * * * * * * * * * *
		 * 	 end of SERVER request Thread    *
		 * * * * * * * * * * * * * * * * * * */
	}
	
	public static void main(String [] args){
		try{
			User user = new User();
			System.out.println("The path of the image is :" + user.getImagePath());
			ChatMeClient cme = new ChatMeClient("localhost");
			user.addClient(cme);
			cme.addUser(user);
			cme.startUserIO();
			//debug
//			Scanner scan = new Scanner(System.in);
//			String response = scan.nextLine();
			
			
			//client.sendCommand(NEW_USER_REQUEST)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
} //endChatMeClient
