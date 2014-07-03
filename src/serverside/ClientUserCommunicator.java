package serverside;

import java.io.IOException;

import conversation.*;

public class ClientUserCommunicator {
	public static void main(String [] args){
		try{
			User user = new User();
			ChatMeClient cme = new ChatMeClient("localhost", 7777);
			user.addClient(cme);
			cme.addUser(user);
			
			//client.sendCommand(NEW_USER_REQUEST)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
