package serverside;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocketHolder {
	
	private String name = "";
	public ObjectInputStream userIn;
	public ObjectOutputStream userOut;
	
	public ObjectInputStream serverIn;
	public ObjectOutputStream serverOut;
	
	public SocketHolder(ObjectInputStream userIn, ObjectOutputStream userOut, 
						ObjectInputStream serverIn, ObjectOutputStream serverOut){
		
		this.userIn = userIn;
		this.userOut = userOut;
		
		this.serverIn = serverIn;
		this.serverOut = serverOut;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
}
