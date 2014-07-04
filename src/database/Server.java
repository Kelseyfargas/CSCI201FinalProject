package database;

import java.io.*;
import java.net.*;

public class Server {
	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static DataInputStream in;
	static Chatroom[] user = new Chatroom[10];
	public static void main(String [] args) throws IOException {
		System.out.println("Starting server...");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started...");
		while(true) {
			socket = serverSocket.accept();
			for (int i = 0; i < 10; i++) {
				System.out.println("Connection from: " + socket.getInetAddress());
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				if (user[i] == null) {
					user[i] = new Chatroom(out, in, user);
					Thread thread = new Thread(user[i]);
					thread.start();
					break;
				}
			}
		}
	}
}

class Chatroom implements Runnable {
	
	DataOutputStream out;
	DataInputStream in;
	Chatroom[] user = new Chatroom[10];
	String name;
	public Chatroom(DataOutputStream out, DataInputStream in, Chatroom[] user) {
		this.out = out;
		this.in = in;
		this.user = user;
	}
	
	public void run() {
		try {
			name = in.readUTF();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true) {
			try {
				String message = in.readUTF();
				for (int i = 0; i < 10; i++) {
					//if user exists
					if (user[i] != null) {
						user[i].out.writeUTF(name + ": " + message);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.out = null;
				this.in = null;
			}
		}
	}
}







