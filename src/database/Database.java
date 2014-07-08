//STEP 1. Import required packages
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ChatMeDB";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public static Connection conn = null;
	public static Statement stmt = null;
	public static String sql;

	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	// ///////////////////////CREATE ACCOUNT//////////////////////////
	/*
	 * Method that creates account for user and addes them to UserInfo table in the DB
	 * Takes in the username, password, bio, and image path
	 * Does not allow a user to create an account if their username already exists in the DB
	 */
	public void createAccount(String username, String password, String bio,
			String image) {
		try {
			stmt = conn.createStatement();
			boolean userExists = verifyUserExists(username);
			if (!userExists) {
				sql = "insert into UserInfo(username, password, bio, img) values('__NAME__',password('__PW__'), '__BIO__', '__IMAGE__');";
				sql = sql.replace("__NAME__", username);
				sql = sql.replace("__PW__", password);
				sql = sql.replace("__BIO__", bio);
				sql = sql.replace("__IMAGE__", image);
				System.out.println(sql);
				stmt.execute(sql);
				System.out.println("You have successfully created an account");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Method that checks to see if the desired username is already in the DB
	 * Returns true if the username is taken, or false if the username does not exist yet
	 */
	public boolean verifyUserExists(String username) throws SQLException {
		final String queryCheck = "SELECT count(*) from UserInfo WHERE username = ?";
		final PreparedStatement ps = conn.prepareStatement(queryCheck);
		ps.setString(1, username);
		final ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			final int count = resultSet.getInt(1);
			if (count != 0) {
				System.out.println("Username is taken. Please try again.");
				return true;
			}
		}
		return false;
	}

	// ///////////////////////LOGIN//////////////////////////
	/*
	 * Method that checks the credentials entered in the GUI
	 * Takes in the username and password
	 * If username and password are correct, returns true 
	 * If the credentials do not match what is in the DB, returns false
	 */
	public boolean login(String username, String password) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		sql = "select * from UserInfo where username = '__NAME__' and password = password('__PW__');";
		sql = sql.replace("__NAME__", username);
		sql = sql.replace("__PW__", password);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			//verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
				return false;
			} else {
				//Extract data from result set
				//Retrieve by column name
				String r_username = rs.getString("username");
				String r_bio = rs.getString("bio");
				String r_img = rs.getString("img");

				// Display values
				System.out.println("Username: " + r_username);
				System.out.println("Bio: " + r_bio);
				System.out.println("Image Path: " + r_img);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	// ///////////////////////ONLINE LIST//////////////////////////
	/*
	 * Method that adds the user to the online list in the DB
	 * Takes in the username
	 * Will be called after a user successfully logins (if login method returns true)
	 */
	public synchronized void addToOnlineList(String username) throws SQLException {
		stmt = conn.createStatement();
		//add user to OnlineStatus table in database
		sql = "insert into OnlineUsers(username) values('__USER__');";
		sql = sql.replace("__USER__", username);
		stmt.execute(sql);
	}

	/*
	 * Returns an arraylist of online users from the DB
	 * Will be called after a user either logs in or logs off in order to update the GUI
	 */
	public synchronized ArrayList<String> getOnlineList() {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> onlineUsers = new ArrayList<String>();
		//get all the users in the online list in the DB
		sql = "select * from OnlineUsers";
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String user = rs.getString("username");
				onlineUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return onlineUsers;
	}

	// ///////////////////////USER INFO//////////////////////////
	/*
	 * Method that returns the user's current bio (whether updated or the inital bio) 
	 * Retrives information from the UserInfo table in the DB
	 * Takes the in the username
	 */
	public String getBio(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String r_bio = null;
		sql = "select * from UserInfo where username = '__NAME__';";
		sql = sql.replace("__NAME__", username);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			//verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
			} else {
				//extract data from result set
				// Retrieve by column name
				String r_username = rs.getString("username");
				r_bio = rs.getString("bio");

				// Display values
				System.out.println("Username: " + r_username);
				System.out.println("Bio: " + r_bio);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return r_bio;
	}

	/*
	 * Method that updates a user's bio in the DB if they choose to edit it from what the originally had
	 * Takes in the username and the new bio
	 */
	public void updateBio(String username, String newBio) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String bio = getBio(username);
		try {
			if (!bio.equals(null)) {
				//update bio
				bio = newBio;
				System.out.println("New Bio: " + bio);

				//store bio
				sql = "UPDATE UserInfo SET bio = '__BIO__' WHERE username = '__USERNAME__';";
				sql = sql.replace("__BIO__", bio);
				sql = sql.replace("__USERNAME__", username);
				stmt.execute(sql);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Method that gets the image path of the icon of a user from the UserInfo table in the DB
	 * Takes in the username
	 */
	public String getImagePath(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String r_img = null;
		sql = "select * from UserInfo where username = '__NAME__';";
		sql = sql.replace("__NAME__", username);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			//verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
			} else {
				//Extract data from result set
				//Retrieve by column name
				String r_username = rs.getString("username");
				r_img = rs.getString("img");

				//Display values
				System.out.println("Username: " + r_username);
				System.out.println("Image Path: " + r_img);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return r_img;
	}

	// ///////////////////////CONVOS//////////////////////////
	/*
	 * Method that adds a new conversation into the Conversations table in the DB
	 * Takes in the conversation name and the content
	 * Makes sure that the chosen conversation name does not already exist in the DB
	 */
	public synchronized void createConversation(String convoName, String content) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		boolean convoNameExists = verifyConvoNameExists(convoName);
		if (!convoNameExists) {
			sql = "insert into Conversations(convoName, content) values('__CONVONAME__', '__CONTENT__');";
			sql = sql.replace("__CONVONAME__", convoName);
			sql = sql.replace("__CONTENT__", content);
			System.out.println(sql);
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("You have successfully created an account");
		}
	}

	/*
	 * Method that checks to see if the chosen conversation name already exists in the DB
	 * Takes in conversation name
	 * Returns true the conversation name was found in the DB
	 * Returns false if the conversation name does not exists in the DB
	 */
	public synchronized boolean verifyConvoNameExists(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		final String queryCheck = "SELECT count(*) from Conversations WHERE convoName = ?";
		try {
			final PreparedStatement ps = conn.prepareStatement(queryCheck);
			ps.setString(1, convoName);
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				final int count = resultSet.getInt(1);
				if (count != 0) {
					System.out.println("Conversation name is taken. Please try again.");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Method that updates a conversations content with the new message sent
	 * Takes in the conversation name and the new message
	 * Retrieves the current content of the conversation, updates the content, and stores the updated content in the DB
	 */
	public void updateConvoContent(String convoName, String newContent) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String content = getConvoContent(convoName);
		try {
			if (!content.equals(null)) {
				//update content
				content = content + "\n" + newContent;
				System.out.println("New Content: " + content);

				//store content
				sql = "UPDATE Conversations SET content = '__CONTENT__' WHERE convoName = '__CONVONAME__';";
				sql = sql.replace("__CONTENT__", content);
				sql = sql.replace("__CONVONAME__", convoName);
				stmt.execute(sql);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Method that retrieves the content of a conversation and returns the entire content of the conversation
	 * Will be called after the content is updated
	 * Takes in the name of the conversation
	 */
	public String getConvoContent(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String content = null;
		sql = "select * from Conversations where convoName = '__CONVONAME__';";
		sql = sql.replace("__CONVONAME__", convoName);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			//verify if user is in database
			if (!rs.next()) {
				System.out.println("The conversation is not in database.");
			} else {
				//Extract data from result set
				//Retrieve by column name
				content = rs.getString("content");

				//Display values
				System.out.println("Content: " + content);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return content;
	}
	
	
	/*
	 * Method that returns an array of all the active group conversations from the DB
	 */
	public synchronized ArrayList<String> getGroupConversations() {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> groupConversations = new ArrayList<String>();
		sql = "select * from Conversations";
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String user = rs.getString("convoName");
				groupConversations.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groupConversations;
	}

	/*
	 * Method that deletes a conversation from the Conversations table in DB
	 * Takes in the conversation name
	 */
	public void endConvo(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		sql = "DELETE FROM Conversations WHERE convoName='__CONVONAME__';";
		sql = sql.replace("__CONVONAME__", convoName);
		System.out.println(sql);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////SIGN OUT//////////////////////////
	/*
	 * Method that removes a user from the OnlineUsers table in the DB
	 * Takes in the username
	 * getOnlinList() should be called after this method in order for the GUI buddy list to be updated
	 */
	public synchronized void  signOut(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		sql = "DELETE FROM OnlineUsers WHERE username='__USERNAME__';";
		sql = sql.replace("__USERNAME__", username);
		System.out.println(sql);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(username + " has signed off");
	}
}// end AccountInformation

