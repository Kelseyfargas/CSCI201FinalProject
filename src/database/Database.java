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

	public static void doAction() {

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			Scanner scan1 = new Scanner(System.in);
			Scanner scan2 = new Scanner(System.in);
			while (true) {
				System.out
						.print("(1)Sign Up, \n(2)Login, \n(3)Create Conversation, "
								+ "\n(4)Update Convo, \n(5)End Convo, "
								+ "\n(6)Logout");
				int action = scan1.nextInt();
				if (action == 1) {
					// SETTING UP ACCOUNT
					// STEP 4: Execute a query
					stmt = conn.createStatement();
					System.out.println("Enter in desired username: ");
					String username = scan2.nextLine();
					System.out.println("Enter in password: ");
					String password = scan2.nextLine();
					System.out.println("Enter in a bio: ");
					String bio = scan2.nextLine();
					System.out.println("Enter in an image path: ");
					String image = scan2.nextLine();
					createAccount(username, password, bio, image);

				}

				else if (action == 2) {
					// LOGGING IN TO EXISTING ACCOUNT
					stmt = conn.createStatement();
					System.out.println("Username: ");
					String username = scan2.next();
					System.out.println("Password: ");
					String password = scan2.next();
					Boolean ok = login(username, password);
					if (ok) {
						System.out.println("User is in system!");
						addToOnlineList(username);
						getBio(username);
						getImagePath(username);
					}
				}

				else if (action == 3) {
					// CREATE Conversation
					stmt = conn.createStatement();
					System.out.println("Enter the name of the Conversation: ");
					String convoName = scan2.nextLine();
					System.out.println("Enter the username of the moderator of the Conversation: ");
					String moderator = scan2.nextLine();
					System.out.println("Enter in content: ");
					String content = scan2.nextLine();
					createConversation(convoName, moderator, content);
				}

				else if (action == 4) {
					//UPDATING CONVO
					stmt = conn.createStatement();
					System.out
							.println("Enter the name of the Conversation that you wish to update the content of: ");
					String convoName = scan2.nextLine();
					System.out
							.println("Enter in the new message: ");
					String content = scan2.nextLine();
					updateConvoContent(convoName, content);
				}

				else if (action == 5) {
					// END CONVO
					stmt = conn.createStatement();
					System.out.println("Enter the name of the Conversation that you would like to end: ");
					String convoName = scan2.nextLine();
					endConvo(convoName);

				}

				else if (action == 6) {
					// LOGOUT
					stmt = conn.createStatement();
					System.out
							.println("Enter the username that is logging off: ");
					String username = scan2.next();
					signOut(username);
					
				}

				else if (action == 7) {
					// LEAVING CONVO
					stmt = conn.createStatement();
					System.out
							.println("Enter the name of the conversation that you would like to leave: ");
					String convoName = scan2.next() + "Convo";
					System.out.println("Enter your username: ");
					String username = scan2.next();
					sql = "DELETE FROM __CONVONAME__ WHERE user='__USERNAME__';";
					sql = sql.replace("__CONVONAME__", convoName);
					sql = sql.replace("__USERNAME__", username);
					System.out.println(sql);
					stmt.execute(sql);
					System.out.println(username + " has left " + convoName);
				}

				else if (action == 8) {
					// LOGGING OUT
					stmt = conn.createStatement();
					System.out
							.println("Enter the username of user that is logging out: ");
					String username = scan2.next();
					sql = "DELETE FROM OnlineUsers WHERE username='__USERNAME__';";
					sql = sql.replace("__USERNAME__", username);
					System.out.println(sql);
					stmt.execute(sql);
					System.out.println(username + " has signed off");
				}

				System.out.println("Do you wish to exit the program? ");
				String response = scan2.next();
				if (response.equals("y")) {
					break;
				}
			}
			scan1.close();
			scan2.close();

			// STEP 6: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("Goodbye!");
	}// end

	/*public static void main(String[] args) {
		doAction();
	}// end main*/
	
	
	// ///////////////////////CREATE ACCOUNT//////////////////////////
	public static void createAccount(String username, String password,
			String bio, String image) {
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

	public static boolean verifyUserExists(String username) throws SQLException {
		stmt = conn.createStatement();
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
	public static boolean login(String username, String password) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "select * from UserInfo where username = '__NAME__' and password = password('__PW__');";
		sql = sql.replace("__NAME__", username);
		sql = sql.replace("__PW__", password);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			// verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
				return false;
			} else {
				// STEP 5: Extract data from result set
				// Retrieve by column name
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
	public static void addToOnlineList(String username) throws SQLException {
		stmt = conn.createStatement();
		// add user to OnlineStatus table in database
		sql = "insert into OnlineUsers(username) values('__USER__');";
		sql = sql.replace("__USER__", username);
		stmt.execute(sql);
	}

	public static ArrayList<String> getOnlineList() {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> onlineUsers = new ArrayList<String>();
		// GET ALL THE USERS IN THE ONLINE LIST IN DB
		sql = "select * from OnlineUsers";
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String user = rs.getString("user");
				onlineUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return onlineUsers;
	}
	
	// ///////////////////////USER INFO//////////////////////////
	public static String getBio(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String r_bio = null;
		sql = "select * from UserInfo where username = '__NAME__';";
		sql = sql.replace("__NAME__", username);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			// verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
			} else {
				// STEP 5: Extract data from result set
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

	public static String getImagePath(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String r_img = null;
		sql = "select * from UserInfo where username = '__NAME__';";
		sql = sql.replace("__NAME__", username);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			// verify if user is in database
			if (!rs.next()) {
				System.out.println("User not in database.");
			} else {
				// STEP 5: Extract data from result set
				// Retrieve by column name
				String r_username = rs.getString("username");
				r_img = rs.getString("img");

				// Display values
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
	public static void createConversation(String convoName, String moderator, String content) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean convoNameExists = verifyConvoNameExists(convoName);
		if(!convoNameExists) {
			sql = "insert into Conversations(convoName, moderator, content) values('__CONVONAME__','__MODERATOR__', '__CONTENT__');";
			sql = sql.replace("__CONVONAME__", convoName);
			sql = sql.replace("__MODERATOR__", moderator);
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
	
	public static boolean verifyConvoNameExists(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
	
	public static void updateConvoContent(String convoName, String newContent) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
	
	public static String getConvoContent(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String content = null;
		sql = "select * from Conversations where convoName = '__CONVONAME__';";
		sql = sql.replace("__CONVONAME__", convoName);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			// verify if user is in database
			if (!rs.next()) {
				System.out.println("The conversation is not in database.");
			} else {
				// STEP 5: Extract data from result set
				// Retrieve by column name
				content = rs.getString("content");

				// Display values
				System.out.println("Content: " + content);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return content;
	}
	
	public static String getConvoModerator(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String moderator = null;
		sql = "select * from Conversations where moderator = '__MODERATOR__';";
		sql = sql.replace("__MODERATOR__", moderator);
		System.out.println(sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			// verify if user is in database
			if (!rs.next()) {
				System.out.println("The conversation is not in database.");
			} else {
				// STEP 5: Extract data from result set
				// Retrieve by column name
				moderator = rs.getString("moderator");

				// Display values
				System.out.println("The moderator of " + convoName + " is: " + moderator);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return moderator;
	}
	
	public static void endConvo(String convoName) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
	
/////////////////////////SIGN OUT//////////////////////////
	public static void signOut(String username) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql = "DELETE FROM OnlineUsers WHERE username='__USERNAME__';";
		sql = sql.replace("__USERNAME__", username);
		System.out.println(sql);
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(username + " has signed off");
	}
}// end AccountInformation


