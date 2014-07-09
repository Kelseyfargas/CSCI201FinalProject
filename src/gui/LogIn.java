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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import conversation.User;


public class LogIn extends JFrame {
	
	private JTextField usernameTF;
	private JPasswordField passwordTF;
	private User user;
	public JButton logInButton;
	
	public LogIn(User user){
		super("Login");
		this.user = user;
		
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
		
		passwordTF = new JPasswordField(15);
		passwordTF.setFont(inputfont);
		JLabel password = new JLabel("Password");
		centerPanel.add(password);
		centerPanel.add(passwordTF);
		
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		logInButton = new JButton("Log In");
		getRootPane().setDefaultButton(logInButton);//DOES THE ENTER BUTTON
/**********INNER CLASSES FOR LOG IN*************/
/***********************************************/
/***********************************************/
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
		logInButton.addActionListener(new LogInListener(user));

		flowPanel.add(logInButton);
		centerPanel.add(flowPanel);
		
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		
	
		this.setSize(400,130);
		this.setLocation(400,250);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
/*********FUNCTION FOR THE LOG IN***************/
/***********************************************/
/***********************************************/
	public String getPassword(){
		return passwordTF.getText();
	}
	public String getUsername(){
		return usernameTF.getText();
	}
	

}
