package serverside;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatMeClient {

	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	
	public ChatMeClient(String hostname, int port, User user) throws IOException{
		
		String ipAddress = "localhost";
		
		System.out.println("Connecting...");
		socket = new Socket(ipAddress, 7777);
		System.out.println("Connection Successful...");
		
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		InputClass ic = new InputClass(in,out);
		ic.run();
	}
	
	class InputClass extends Thread {
		DataInputStream in;
		DataOutputStream out;
		public InputClass(DataInputStream in, DataOutputStream out){
			this.in  = in;
			this.out = out;
		}
		public void run(){
			while(true){
				try{
					System.out.println("Attempting to read welcome message: \n");
					String message = in.readUTF();
					System.out.println(message);
					
					Scanner sc = new Scanner(System.in); //Make input from terminal
					int command = sc.nextInt(); //gets (int) command
					
					System.out.println("Sending Command: ");
					out.writeInt(command);
					
					System.out.println("Command Sent (" + command + ").");
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	

	
	public static void main(String [] args){
		/* host name NEEDS to be a string*/
		User me = new User();
		try {
			new ChatMeClient("localhost", 7777, me);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class User{
	//super fake
}
