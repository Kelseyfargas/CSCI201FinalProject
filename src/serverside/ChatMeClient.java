package serverside;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

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

	public ChatMeClient() throws IOException {
		String ipAddress = "10.122.192.141";

		System.out.println("Connecting...");
		userRequestSocket = new Socket(ipAddress, 7777);
		serverRequestSocket = new Socket(ipAddress, 8888);
		System.out.println("Connection Successful...");

		userIn = new ObjectInputStream(userRequestSocket.getInputStream());
		userOut = new ObjectOutputStream(userRequestSocket.getOutputStream());
		servIn = new ObjectInputStream(serverRequestSocket.getInputStream());
		servOut = new ObjectOutputStream(serverRequestSocket.getOutputStream());
	}

	public void startUserIO() {
		uioclass = new UserInputOutputClass();
		uioclass.start();
		sioclass = new ServerInputOutputClass();
		sioclass.start();
	}

	public void addUser(User user) {
		this.user = user;
	}
	public void sendCommand(int command) {
		try {
			uioclass.sendCommandAndListen(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendCommand(int command, String string) {
		try {
			uioclass.sendCommandAndObject(command, string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendCommand(int command, Message msg) {
		try {
			uioclass.sendCommandAndObject(command, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class UserInputOutputClass extends Thread {
		/* * * * * * * * * * * * * * * * * * * *
		 * BEGINNING of USER Request Thread    * 
		 * * * * * * * * * * * * * * * * * * * */
		boolean continueRunning = true;

		/* Welcome Message */
		public void readAndPrintWelcomeMessage() throws ClassNotFoundException, IOException {
			System.out.println("Attempting to read welcome message: \n");
			String message = (String) userIn.readObject();
			System.out.println(message);
		}

		/* * * * * * * * * * * * * * * * * *
		 * Thread LISTEN for USER block    * 
		 * * * * * * * * * * * * * * * * * */
		public void run() {
			try {
				readAndPrintWelcomeMessage();
				while (continueRunning) {
					try {
						Thread.sleep(100);
						// userin.readInt();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		/* * * * * * * * * * * * * * * * * * * * *
		 * Thread DO request from USER block     * 
		 * * * * * * * * * * * * * * * * * * * * */
		
		// Each Helper function should contain a comment saying finished, or
		// unfinished, then specifying what needs to be done
		
		/* Command doesn't need arguments */
		private void sendCommandAndListen(int command) throws IOException,
				ClassNotFoundException {
			lock.lock();
			userOut.writeInt(command);
			userOut.flush();
			if (command == ChatMeServer.LOGIN_REQUEST) {
				loginRequest();
			} else if (command == ChatMeServer.NEW_USER_REQUEST) {
				newUserRequest();
			} else if (command == ChatMeServer.SIGN_OUT_REQUEST) {
				signOutRequest();
			} 
			lock.unlock();
		}
		public void loginRequest() throws IOException, ClassNotFoundException {
			
			System.out.println("CLIENT: log in request");
			String un = user.getName();
			String pw = user.getPassword();
			userOut.writeObject(un);
			userOut.writeObject(pw);
			userOut.flush();

			boolean OK = userIn.readBoolean();
			if (OK == true) {
				String bio = (String) userIn.readObject();
				String imagePath = (String) userIn.readObject();
				user.setBio(bio);
				user.setImagePath(imagePath);
				System.out.println("CLIENT: cleared to log in.");
				user.createBuddyList();
			} else {
				System.err.println("CLIENT: Could not log in. Incorrect Credentials");
				user.incorrectInfoError();
			}
		}
		public void signOutRequest() throws IOException {
			System.out.print("CLIENT:  (SIGN OUT) ");
			userOut.writeObject(user.getName());
			userOut.flush();
			System.out.println(user.getName() + " has signed out...");
			
		}
		public void newUserRequest() throws IOException {
			// finished but needs database
			System.out.println("CLIENT: (NEW USER) obtaining from data fields");
			String username = user.getName();
			String password = user.getPassword();
			String aboutMe = user.getBio();
			String imagePath = user.getImagePath();

			userOut.writeObject(username);
			userOut.writeObject(password);
			userOut.writeObject(aboutMe);
			userOut.writeObject(imagePath);
			userOut.flush();
			boolean OK = userIn.readBoolean();
			if (OK == false) {
				System.out.println("CLIENT: (NEW USER) username available.");
			} else {
				System.out.println("CLIENT: (NEW USER) username not available.");
				user.nameExistError();
			}
		}

		/* Command does need arguments */

		// Takes String as parameter
		public void sendCommandAndObject(int command, String string)
				throws IOException, ClassNotFoundException {
			lock.lock();
			userOut.writeInt(command);
			if (command == ChatMeServer.NEW_GROUP_REQUEST) {
				newGroupRequest(string);
			}
			if (command == ChatMeServer.END_GROUP_REQUEST){
				endGroupRequest(string);
			}
			if(command == ChatMeServer.NEW_PRIVATE_REQUEST){
				newPrivateRequest(string);
			}
			if(command == ChatMeServer.GET_BIO_REQUEST){
				getBioRequest(string);
			}
			lock.unlock();
		}

		public void newGroupRequest(String convoName) throws IOException {
			userOut.writeObject(convoName);
			userOut.flush();
			
			boolean convoExists = userIn.readBoolean();
			if (convoExists == false) {
				System.out.println("CLIENT: (NEW GROUP) Adding Convo to BL");
				user.createNewMessageWindow(convoName, true); //true for "setModerator()" 
			} else if (convoExists == true) {
				user.displayConvoError();
				System.err.println("CLIENT: (NEW GROUP) conversation name unavailable");
				JOptionPane.showMessageDialog(null, "Conversation name is already taken!", "Convo Err", JOptionPane.WARNING_MESSAGE);
			}
		}
		public void endGroupRequest(String convoName) throws IOException {
			// unfinished, needs GUI implementation
			userOut.writeObject(convoName);
			userOut.flush();
			
			boolean OK = userIn.readBoolean();
			if (OK == true) {
				System.out.println("CLIENT: (END GCONVO) Terminating GConvo");
			} else if (OK == false) {
				user.displayConvoError();
				System.err.println("CLIENT: (END GCONVO) Unable to remove GConvo");
			}
		}
		public void newPrivateRequest(String convoName) throws IOException{
			userOut.writeObject(convoName);
			userOut.flush();
			
			boolean convoExists = userIn.readBoolean();
			if( convoExists == false) {
				System.out.println("CLIENT: (NEW PCONVO) Opening new PConvo");
				//user.createNewMessageWindow(convoName, false); //no moderator
				
				/* Hacky Note: */
				//the commented out section would make more sense in this function, however it is
				//called in Buddy.mouseClassOnlineUser and BuddyList.startPrivateMessage
				//because we don't want to have to write extra methods/ do extra checks
			}
			else{
				System.out.println("CLIENT: (NEW PCONVO) Opening existing PConvo");
			}
		}
		public void getBioRequest(String username) throws IOException, ClassNotFoundException{
			userOut.writeObject(username);
			
			String imagePath = (String) userIn.readObject();
			String bio 		 = (String) userIn.readObject();
			
			user.displayFriendBio(username, imagePath, bio);
		}
		
		// Takes Message as parameter
		public void sendCommandAndObject(int command, Message msg)
				throws IOException {
			lock.lock();
			userOut.writeInt(command);
			if (command == ChatMeServer.NEW_GROUP_MESSAGE_REQUEST) {
				newGroupMessageRequest(msg);
			}
			else if(command == ChatMeServer.NEW_PRIVATE_MESSAGE_REQUEST) {
				newPrivateMessageRequest(msg);
			}
			else if(command == ChatMeServer.SET_BIO_REQUEST){
				setBioRequest(msg);
			}
			lock.unlock();
		}

		public void newGroupMessageRequest(Message msg) throws IOException {
			// unfinished, needs GUI implementation in Server Request Thread
			System.out.println("CLIENT: (SEND GMSG TO: " + msg.getConversationName() +") " + msg.getContent());
			userOut.writeObject(msg);
			userOut.flush();
		}
		public void newPrivateMessageRequest(Message msg) throws IOException {
			System.out.println("CLIENT: (SEND PMSG TO: " + msg.getConversationName() + ") " + msg.getContent());
			System.out.println("In newPrivateMessageRequest. Convo name is " + msg.getConversationName());
			userOut.writeObject(msg);
			userOut.flush();
		}
		public void setBioRequest(Message msg) throws IOException{
			//SUPER HACKY
			userOut.writeObject(msg);
			System.out.println("b4 read bool");
			//userIn.readBoolean();
			System.out.println("4r read bool");
		}
		/* * * * * * * * * * * * * * * * *
		 * END of USER Request Thread 	 * 
		 * * * * * * * * * * * * * * * * */
	}

	class ServerInputOutputClass extends Thread {

		/* * * * * * * * * * * * * * * * * * * * *
		 * BEGINNING of SERVER Request Thread    * 
		 * * * * * * * * * * * * * * * * * * * * */


		/* Thread LISTEN for SERVER block    */
		public void run() {

			while (true) {
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
		 * Thread DO action from SERVER block    *
		 * * * * * * * * * * * * * * * * * * * * */
		
		public void handleCommand(int command) throws ClassNotFoundException,
				IOException {

			if (command == ChatMeServer.NEW_GROUP_MESSAGE_REQUEST) {
				newGroupMessageRequest();
			} 
			else if (command == ChatMeServer.NEW_PRIVATE_MESSAGE_REQUEST)	{
				newPrivateMessageRequest();
			}
			else if(command == ChatMeServer.UPDATE_GROUP_REQUEST){
				updateGroupRequest();
			}
			else if (command == ChatMeServer.UPDATE_ONLINE_USERS_REQUEST) {
				updateOnlineUsersRequest();
			}
		}
		
		public void newGroupMessageRequest() throws ClassNotFoundException, IOException{
			Message msg = (Message) servIn.readObject();
			System.out.println("CLIENT: (RECIEVE GMSG IN: " + msg.getConversationName() + ")" + msg.getContent());
			user.getGroupMessage(msg);
		}
		
		public void newPrivateMessageRequest() throws ClassNotFoundException,IOException {
			Message msg = (Message) servIn.readObject();
			System.out.println("CLIENT: (RECIEVE PMSG IN: " + msg.getConversationName() + ")" + msg.getContent());
			user.getPrivateMessage(msg);

		}
		public void updateGroupRequest() throws ClassNotFoundException, IOException {
			ArrayList<String> convos = (ArrayList<String>) servIn.readObject();
			user.setCurrentConversations(convos);
		}
		public void updateOnlineUsersRequest() throws ClassNotFoundException, IOException{
			ArrayList<String> onlineUsers = (ArrayList<String>) servIn.readObject();
			lock.lock();
			user.setOnlineUsers(onlineUsers);
			lock.unlock();
		}


		/* * * * * * * * * * * * * * * * * * *
		 * end of SERVER request Thread 	 * 
		 * * * * * * * * * * * * * * * * * * */
	}

	public static void main(String[] args) {
		try {
			User user = new User();
			ChatMeClient cme = new ChatMeClient();
			user.addClient(cme);
			cme.addUser(user);
			cme.startUserIO();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
} // endChatMeClient
