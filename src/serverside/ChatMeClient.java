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
	static Socket socket;
	static ObjectInputStream in;
	static ObjectOutputStream out;
	
	private InputOutputClass ioclass;
	
	public ChatMeClient(String hostname, int port) throws IOException{
		String ipAddress = "localhost";
		
		System.out.println("Connecting...");
		socket = new Socket(ipAddress, 7777);
		System.out.println("Connection Successful...");
		
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
	}
	public void startIO(){
		ioclass = new InputOutputClass(socket, in,out);
		ioclass.run();
	}
	public void addUser(User user){
		this.user = user;
	}
	public void sendCommand(int command){
		try{
			ioclass.sendCommandAndListen(command);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	class InputOutputClass extends Thread {
		ObjectInputStream in;
		ObjectOutputStream out;
		Socket s;
		Lock lock = new ReentrantLock();
		public InputOutputClass(Socket s, ObjectInputStream in, ObjectOutputStream out){
			this.in  = in;
			this.out = out;
			this.s = s;
		}
		public void run(){
			
			try{
				System.out.println("Attempting to read welcome message: \n");
				String message = (String) in.readObject();
				System.out.println(message);
				
				while(true){
					lock.lock();
					if(in.available() == 8){
						//if there is a double in the stream, read from the stream
						System.out.println("Double in stream");
						Double dubresponse = in.readDouble();
						System.out.println(dubresponse);
					}
					lock.unlock();
					//Wait for Users to give commands
				
				}
			} catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		private void sendCommandAndListen(int command) throws IOException, ClassNotFoundException{
			out.writeInt(command);
			Scanner scan = new Scanner(System.in);
			if(command == ChatMeServer.LOGIN_REQUEST){
				System.out.println("CLIENT: log in request");
				
				String un = user.getName();
				String pw = user.getPassword();
				out.writeObject(un);
				out.writeObject(pw);
				
				System.out.println("waiting . . .");
				boolean OK = in.readBoolean();
				if(OK == true)
				{
					System.out.println("you have been cleared to log in.");
					
					ArrayList<String> onlineUsers = (ArrayList<String>) in.readObject();
					
					//Debug, display everyone online
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
				out.writeObject(username);
				out.writeObject(password);
				out.writeObject(aboutMe);
				out.writeObject(image);
				
				
			}
			else if(command == ChatMeServer.NEW_MESSAGE_REQUEST){
				System.out.println("Enter ChatName: ");
				String chatName = scan.nextLine();
				System.out.println("Enter Content: ");
				String content = scan.nextLine();
				Message msg = new Message(chatName, content);
				System.out.println("\nSending Message Packet...");
				out.writeObject(msg);
				System.out.println("Finished.");
			}
		}
	}

	public static void main(String [] args){
		try{
			User user = new User();
			ChatMeClient cme = new ChatMeClient("localhost", 7777);
			user.addClient(cme);
			cme.addUser(user);
			cme.startIO();
			
			//client.sendCommand(NEW_USER_REQUEST)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
