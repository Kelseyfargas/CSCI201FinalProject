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

import conversation.User;


public class LogIn extends JFrame {
	
	private JTextField usernameTF;
	private JTextField passwordTF;
	public JButton logInButton;
	//public static LogIn log;
	public LogIn(){
		super("Login");
	}
//    public void pullThePlug() {
//        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
//        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
//    }
    public void createLogin(final LogIn lg){
		System.out.println("login");
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

		class LogInListener implements ActionListener{
			User user;
			public LogInListener(User user){
				this.user = user;
			}
			public void actionPerformed(ActionEvent ae){
				user.setName(usernameTF.getText());
				user.setPassword(passwordTF.getText());
				user.sendLogInRequest();
			}
		}
		logInButton.addActionListener(new LogInListener());
		
		

		flowPanel.add(logInButton);
//		flowPanel.add(createAccount);
		centerPanel.add(flowPanel);
		
		//adding the layouts
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		
	
		this.setSize(400,130);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
    
	/*public static void main(String []args){
		final LogIn l = new LogIn("Login");
		l.logInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new BuddyList(l.usernameTF.getText() +"'s Buddy List");
				l.pullThePlug();
			}
		}); 

	}*/

	public String getPassword(){
		return passwordTF.getText();
	}
	public String getUsername(){
		return usernameTF.getText();
	}
	
    public void pullThePlug() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }

}
