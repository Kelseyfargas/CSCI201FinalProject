package serverside;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatMeClient {

	private User me;
	static Socket socket;
	static ObjectInputStream in;
	static ObjectOutputStream out;
	
	public ChatMeClient(String hostname, int port, User user) throws IOException{
		me = user;
		String ipAddress = "localhost";
		
		System.out.println("Connecting...");
		socket = new Socket(ipAddress, 7777);
		System.out.println("Connection Successful...");
		
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
		InputOutputClass ioc = new InputOutputClass(socket, in,out, user);
		ioc.run();
	}
	
	class InputOutputClass extends Thread {
		ObjectInputStream in;
		ObjectOutputStream out;
		Socket s;
		public InputOutputClass(Socket s, ObjectInputStream in, ObjectOutputStream out, User user){
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

					Scanner sc = new Scanner(System.in); //Make input from terminal
					int command = sc.nextInt(); //gets (int) command
					
					
					System.out.println("Sending Command: ");
					
					sendCommandAndListen(command);
					
					System.out.println("Command Sequence Terminated (" + command + ").");
					System.out.println("Please enter another command.\n");
				
				}
			} catch(IOException | ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		
		private void sendCommandAndListen(int command) throws IOException, ClassNotFoundException{
			out.writeInt(command);
			Scanner scan = new Scanner(System.in);
			if(command == ChatMeServer.LOGIN_REQUEST){
				System.out.println("Please Enter username, then password:");
				String un = scan.nextLine();
				String pw = scan.nextLine();
				out.writeObject("un:" + un);
				out.writeObject("pw:" + pw);
				
				System.out.println("waiting . . .");
				boolean OK = in.readBoolean();
				if(OK == true){
					System.out.println("you have been cleared to log in.");

					String [] sarr = (String[]) in.readObject();
					for(int i=0; i< sarr.length;i++){
						System.out.println("Online: " + sarr[i]);
					}
				}
				else{
					System.out.println("Could not log in");
				}
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
		
		User me = new User();
		try {
			ChatMeClient cme = new ChatMeClient("localhost", 7777, me);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class User{
	//super fake
}
