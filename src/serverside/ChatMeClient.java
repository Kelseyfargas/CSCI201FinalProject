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

	public ChatMeClient(String hostname) throws IOException {
		String ipAddress = "10.120.95.24";

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
	public void sendCommand(int command, String convo) {
		try {
			uioclass.sendCommandAndObject(command, convo);
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

		/* Welcom Message */
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

			System.out.println("waiting . . .");
			boolean OK = userIn.readBoolean();
			if (OK == true) {
				String bio = (String) userIn.readObject();
				String imagePath = (String) userIn.readObject();
				user.setBio(bio);
				user.setImagePath(imagePath);
				user.displayClearMessage();
				System.out.println("you have been cleared to log in.");
				user.createBuddyList();
			} else {
				System.out.println("Could not log in. Incorrect Credentials");
				user.incorrectInfoError();
			}
		}
		public void signOutRequest() throws IOException {
			System.out.println("CLIENT:  signout request");
			// unfinished, see comment
			userOut.writeObject(user.getName());
			userOut.flush();
			user.signOut(); // need implementation
			System.out
					.println(user.getName() + " has signed out...Write Code!");
		}
		public void newUserRequest() throws IOException {
			// finished but needs database
			System.out.println("Got new user request on client");
			String username = user.getName();
			String password = user.getPassword();
			String aboutMe = user.getBio();
			String imagePath = user.getImagePath();

			System.out
					.println("writing username, password, aboutme, and image");
			userOut.writeObject(username);
			userOut.writeObject(password);
			userOut.writeObject(aboutMe);
			userOut.writeObject(imagePath);
			userOut.flush();
			boolean OK = userIn.readBoolean();
			if (OK == false) {
				System.out.println("CLIENT: Username is not taken :D ");
			} else {
				System.out.println("This user name is already taken!!!");
				user.nameExistError();
			}
		}

		/* Command does need arguments */

		// Takes Conversation Name as parameter
		public void sendCommandAndObject(int command, String convo)
				throws IOException {
			lock.lock();
			userOut.writeInt(command);
			if (command == ChatMeServer.NEW_GROUP_REQUEST) {
				newGroupRequest(convo);
			}
			if (command == ChatMeServer.END_GROUP_REQUEST){
				endGroupRequest(convo);
			}
			lock.unlock();
		}

		public void newGroupRequest(String convoName) throws IOException {
			//bugs
			userOut.writeObject(convoName);
			userOut.flush();
			
			boolean convoExists = userIn.readBoolean();
			if (convoExists == false) {
				System.out.println("CLIENT: User will then add convo to buddy list. Write Code!");
				user.createNewMessageWindow(convoName);
			} else if (convoExists == true) {
				user.displayConvoError();
				System.out.println("CLIENT: Can't start new group convo!!!");
				JOptionPane.showMessageDialog(null, "Conversation name is already taken!", "Convo Err", JOptionPane.WARNING_MESSAGE);
			}
		}
		public void endGroupRequest(String convoName) throws IOException {
			// unfinished, needs GUI implementation
			userOut.writeObject(convoName);

			userOut.flush();
			/*
			 * NOTE: THIS METHOD IS CALLED UNDER THE ASSUMPTION THAT ONLY THE
			 * MODERATOR COULD HAVE MADE THIS CALL. CHECK INSIDE GUI
			 * IMPLEMENTATION
			 */

			boolean OK = userIn.readBoolean();
			if (OK == true) {
				
				System.out.println("ending group convo. write code!");
			} else if (OK == false) {
				user.displayConvoError();
				System.out.println("Can't remove group convo...");
			}
		}

		// Takes Message as parameter
		public void sendCommandAndObject(int command, Message msg)
				throws IOException {
			lock.lock();
			if (command == ChatMeServer.NEW_GROUP_MESSAGE_REQUEST) {
				newGroupMessageRequest(msg);
			}
			lock.unlock();
		}

		public void newGroupMessageRequest(Message msg) throws IOException {
			// unfinished, needs GUI implemnetation in Server Request Thread
			System.out.println("Client: NEW_GROUP_MESSAGE_REQUEST");
			userOut.writeObject(msg); // all of this needs to be written
			userOut.flush();

			// gui must be updated
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
			else if(command == ChatMeServer.UPDATE_GROUP_REQUEST){
				updateGroupRequest();
			}
			else if (command == ChatMeServer.UPDATE_ONLINE_USERS_REQUEST) {
				updateOnlineUsersRequest();
			}
		}
		
		public void newGroupMessageRequest() throws ClassNotFoundException, IOException{
			// getting a new-message-request here means the gui needs to update
			// unfinished, see comment
			System.out.println("CLIENTEeeee : about to get msg...");
			Message msg = (Message) servIn.readObject();
			System.out.println("You have the message now do something with it. Write COoooOoOde");
			user.getGroupMessage(msg);
		}
		public void updateGroupRequest() throws ClassNotFoundException, IOException {
			System.out.println("in new grouprequest");
			ArrayList<String> convos = (ArrayList<String>) servIn.readObject();
			user.setCurrentConversations(convos);
		}
		public void updateOnlineUsersRequest() throws ClassNotFoundException, IOException{
			System.out.println("Reading global update online user request...");
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
			System.out.println("The path of the image is :"
					+ user.getImagePath());
			ChatMeClient cme = new ChatMeClient("localhost");
			user.addClient(cme);
			cme.addUser(user);
			cme.startUserIO();
			// debug
			// Scanner scan = new Scanner(System.in);
			// String response = scan.nextLine();

			// client.sendCommand(NEW_USER_REQUEST)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
} // endChatMeClient
