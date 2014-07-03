package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LogIn extends JFrame {
	
	private JTextField usernameTF;
	private JTextField passwordTF;
	private JButton logInButton;
	public LogIn(String name){
		super(name);
		
		JPanel westPanel = new JPanel();
		JLabel image = new JLabel();
		ImageIcon ii = new ImageIcon("Pictures/Message_Icon_Final.png");
		image.setIcon(ii);
		westPanel.add(image);
		
		Font inputfont = new Font("Courrier New", Font.ITALIC, 12);
		JPanel centerPanel = new JPanel();
		usernameTF = new JTextField(15); //user can type in
		usernameTF.setFont(inputfont);
		JLabel username = new JLabel("UserName");
		centerPanel.add(username);
		centerPanel.add(usernameTF);
		
		passwordTF = new JTextField(15);
		passwordTF.setFont(inputfont);
		JLabel password = new JLabel("Password");
		centerPanel.add(password);
		centerPanel.add(passwordTF);
		
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		logInButton = new JButton("Log In");
		JButton createAccount = new JButton("Create Account");
//		logInButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent ae) {
//					System.out.println("Username is:" + usernameTF.getText());
//					System.out.println("Password is:" + passwordTF.getText());
//					//if(username password is wrong){
//					//	JOptionPane.showMessageDialog(Login.this, "Incorrect password/username. Try again", "Log In",JOptionPane.OK_CANCEL_OPTION);
//					//}	
//					//when logging in, make sure that you pass in the Username, Iconname, and FriendsList
//					//when calling chatroom
//					//The BuddyList constructor will change to something like
//					//new BuddyList((usernameTF.getText() + "'s Friend's List"), image icon, friendslist)
//					new BuddyList(usernameTF.getText() +"'s Buddy List");
//			}
//			
//		});
		createAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new CreateAccount("Create Account");
			}
		});
		

		flowPanel.add(logInButton);
		flowPanel.add(createAccount);
		centerPanel.add(flowPanel);
		
		//adding the layouts
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		
	
		this.setSize(400,130);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String []args){
		final LogIn l = new LogIn("Login");
		l.logInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new BuddyList(l.usernameTF.getText() +"'s Buddy List");
				l.pullThePlug();
			}
		}); 
	}
	
    public void pullThePlug() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }
}
