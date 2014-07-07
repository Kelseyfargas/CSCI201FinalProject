package serverside;
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Icon;

import conversation.Message;
import database.Database;
public class ChatMeServer {
	
	public static int NEW_USER_REQUEST = 0;
	public static int LOGIN_REQUEST = 1;
	public static int SIGN_OUT_REQUEST = 2;
	public static int NEW_MESSAGE_REQUEST = 3;
	public static int INVITE_CHAT_REQUEST = 4;
	public static int NEW_GROUP_REQUEST = 6;
	public static int END_GROUP_REQUEST = 7;
	public static int NEW_GROUP_MESSAGE_REQUEST = 8;
	public static int UPDATE_ONLINE_USERS_REQUEST = 9;

	Database database;
	ArrayList<SocketHolder> clients;

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
		
		clients = new ArrayList<SocketHolder> ();
		database = new Database();
		
		while(true){
			userReqSocket = ss1.accept();
			servReqSocket = ss2.accept();
			
			printDbg("Connection from: " + userReqSocket.getInetAddress());
			
			userOut = new ObjectOutputStream(userReqSocket.getOutputStream());
			userIn = new ObjectInputStream(userReqSocket.getInputStream());
			
			servOut = new ObjectOutputStream(servReqSocket.getOutputStream());
			servIn = new ObjectInputStream(servReqSocket.getInputStream());
			
		
			SocketHolder sh = new SocketHolder(userIn, userOut, servIn, servOut);
			printDbg(" ~ line 63 problem line.");
			clients.add(sh);
			UserReqThread ct = new UserReqThread(userIn, userOut);
			ServReqThread srt = new ServReqThread(servIn, servOut);
			
			ct.setServReqThread(srt);
			srt.setUserReqThread(ct);
			
			ct.setSocketHolder(sh);
			srt.setSocketHolder(sh);
			
			ct.start();
			srt.start();
			

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
		private ServReqThread srt;
		private SocketHolder sh;
		
		public UserReqThread(ObjectInputStream in, ObjectOutputStream out) throws IOException{
			threadUserIn = in;
			threadUserOut = out;
		}
		
		public void setSocketHolder(SocketHolder sh){
			this.sh = sh;
		}
		public void setServReqThread(ServReqThread srt) {
			this.srt = srt;
		}
		public void sendWelcomeMessage(){
			String message = "Welcome. Please Enter a Command.";
			try {
				printDbg("Welcome message Sent\n");
				threadUserOut.writeObject(message);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		/* * * * * * * * * * * * * * * * * * *
		 * 	 Thread LISTEN for SERVER block  *
		 * * * * * * * * * * * * * * * * * * */
		public void run(){	
				
			//1. Send Welcome Message
			sendWelcomeMessage();
			
			//2. Listen for Signal
			printDbg("SERVER: Listening for command");
			while(true)
			{	
				try {
					int command = threadUserIn.readInt(); //Read in whatever command is sent from across socket
					handleCommand(command);
					
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					threadUserOut = null;
					threadUserIn = null;
				}
			}
		}
		/* * * * * * * * * * * * * * * * * * *
		 * Thread DO action from USER block  *
		 * * * * * * * * * * * * * * * * * * */
		private void handleCommand(int command) throws IOException, ClassNotFoundException {
			
			printDbg("SERVER: parsing command...");
			try{
				if(command == NEW_USER_REQUEST){
					newUserRequest();
				}
				else if(command==LOGIN_REQUEST){
					loginRequest();
				}
				else if(command == SIGN_OUT_REQUEST){
					signOutRequest();
				}
				else if(command == NEW_MESSAGE_REQUEST){
					newMessageRequest();
				}
				else if(command == NEW_GROUP_REQUEST){
					newGroupRequest();
				}
				else if(command == END_GROUP_REQUEST){
					printDbg("Reading group convo deletion request");
					String convoName = (String) threadUserIn.readObject();
					String moderator = (String) threadUserIn.readObject();
					database.endConvo(convoName); 
					srt.removeConvoFromAll(convoName, moderator);
				}
				else if(command == UPDATE_ONLINE_USERS_REQUEST){
					ArrayList<String> strArr = database.getOnlineList();
					srt.updateAllOnlineUsers(strArr);
				}
			} catch (SQLException s){
				s.printStackTrace();
			}
		}
		
		private void newUserRequest() throws IOException, ClassNotFoundException, SQLException{
			//SAT: FINISHED
			printDbg("SERVER: Command recieved on server: New User");
			String username = (String) threadUserIn.readObject();
			String password = (String) threadUserIn.readObject();
			String bio 	    = (String) threadUserIn.readObject();
			String imgPath 	= (String) threadUserIn.readObject();
			printDbg("SERVER READS:"
					+ username + " " + password + " " + bio + " " + imgPath);
			
			boolean OK = database.verifyUserExists(username); //database compatible
			
			threadUserOut.writeBoolean(OK);
			threadUserOut.flush();
			if(OK == false){
				database.createAccount(username, password, bio, imgPath);
			}
		}
		private void loginRequest() throws IOException, ClassNotFoundException, SQLException{
			//SAT: FINISHED
			printDbg("Command recieved on server: Login\n");
			String un = (String) threadUserIn.readObject();
			String pw = (String) threadUserIn.readObject();
			printDbg("Reading in: " + un);
			printDbg("Reading in: " + pw);
			/////////////////verify//////////////////////////

			boolean OK = database.login(un, pw);
			////////////////////////////////////////////
			if(OK == true){
				database.addToOnlineList(un);
				printDbg("Giving OK to log in.");
				
				
				printDbg("Attempting to send online Users");
				
				threadUserOut.writeBoolean(true);
				threadUserOut.flush(); //send OK
				
				//send bio
				String bio = database.getBio(un);
				
				//send imagepath
				String imgPath = database.getImagePath(un);
				
				
				//send onlineUsers
				
				
				printDbg("Finished command");
				
				registerSocket(un); //<-- SUPER IMPORTANT
				this.handleCommand(UPDATE_ONLINE_USERS_REQUEST);
				
				
				
				return;
			}
			else{
				printDbg("Denying user.");
				threadUserOut.writeBoolean(false);
				threadUserOut.flush();
				printDbg("Finished command");
			}
		}
		private void signOutRequest()throws IOException, ClassNotFoundException{
			//SAT: FINISHED
			printDbg("Command recieved on server: Sign Out");
			String un = (String) threadUserIn.readObject();
			printDbg("Reading in: " + un);
			database.signOut(un);
			
			//TO DO: UPDATE GUI
		}
		private void newMessageRequest() throws IOException, ClassNotFoundException{
			//DEFINITELY needs looking at
			
			
			
			/*printDbg("Command recieved: New Message");
			printDbg("Reading message . . .");
			Message msg = (Message) threadUserIn.readObject();
			msg.print();
			String convoName = null; //this is super fake
			//String convoName = msg.getConversationName();
			String content = msg.getContent();
			boolean ok = database.verifyConvoNameExists(convoName);
			if (ok == true) {
				database.updateConvoContent(convoName, content);
				String newContent = database.getConvoContent(convoName);
				//Message newMessage = new Message(newContent, convoName);
				//UPDATE GUI
				//srt.sendMessage(msg);
				
			}
			printDbg("Finished command");*/
		}
		private void newGroupRequest() throws ClassNotFoundException, IOException{
			//needs database implementation
			printDbg("Reading group convo initiation request");
			String convoName = (String) threadUserIn.readObject();
			String moderator = (String) threadUserIn.readObject();
			boolean OK = true;
			threadUserOut.writeBoolean(OK); //Change this later when we want to put limitations on when a new group conversation can be created
			threadUserOut.flush();
			database.createConversation(convoName, moderator, ""); 
			srt.addConvoToAll(convoName, moderator);
		}
		
		/* call this method upon logging in  */
		public void registerSocket(String name){
			Iterator<SocketHolder> it = clients.iterator();
			System.out.println("Clients has " + clients.size() + " elemnts in it...");
			while(it.hasNext()){
				if(this.sh == it.next()){
					sh.setName(name);
				}
			}
		}
		/* * * * * * * * * * * * * *
		 * end USER request Thread *
		 * * * * * * * * * * * * * */
		
	}
	class ServReqThread extends Thread{
		
		ObjectOutputStream servOut;
		ObjectInputStream  servIn;
		private UserReqThread ct;
		private SocketHolder sh;
		
		public ServReqThread(ObjectInputStream servIn, ObjectOutputStream servOut){
			this.servOut = servOut;
			this.servIn  = servIn;
		}
		
		public void run(){
			//Listen for Commands from database
			while (true) {
				
			}
			
		}
		public void setSocketHolder(SocketHolder sh){
			this.sh = sh;
		}
		public void setUserReqThread(UserReqThread ct) {
			this.ct = ct;
		}
		public void updateAllOnlineUsers(ArrayList<String> onlineUsers) throws IOException{
			for(int i=0; i<clients.size();i++){
				if( ! clients.get(i).getName().isEmpty() ){
					ObjectOutputStream oos = clients.get(i).serverOut;
					oos.writeInt(UPDATE_ONLINE_USERS_REQUEST);
					oos.flush();
					oos.writeObject(onlineUsers);
					oos.flush();
				}
			}
		}
		public void addConvoToAll(String convoName, String moderator) throws IOException{
			for(int i=0; i<clients.size();i++){
				if( ! clients.get(i).getName().isEmpty()){
					ObjectOutputStream oos = clients.get(i).serverOut;
					oos.writeInt(NEW_GROUP_REQUEST);
					oos.flush();
					oos.writeObject(convoName);
					oos.writeObject(moderator);
					oos.flush();
					
				}
			}
		}
		public void removeConvoFromAll(String convoName, String moderator) throws IOException {
			for(int i=0; i<clients.size();i++){
				if( ! clients.get(i).getName().equals(null)){
					ObjectOutputStream oos = clients.get(i).serverOut;
					oos.writeInt(END_GROUP_REQUEST);
					oos.flush();
					oos.writeObject(convoName);
					oos.writeObject(moderator);
					oos.flush();
					
				}
			}
			
		}
		
	}
}


