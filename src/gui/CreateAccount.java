package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import conversation.*;

public class CreateAccount extends JFrame{
	
	private JButton createAccount;
	private JButton logInButton;
	public static CreateAccount ca;
	public static LogIn LG;
	private static User user;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextArea Bio;
	
	public CreateAccount(User user){
		super("Create Account");
		this.user = user;
		setLayout(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints(); 

		Font inputfont = new Font("Courrier New", Font.ITALIC, 12);
		usernameField = new JTextField(15); //user can type in
		usernameField.setFont(inputfont);
		JLabel username = new JLabel("Desired UserName:");
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		gbc.gridwidth = 1;
		add(username, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2; 
		add(usernameField,gbc);
		
		passwordField = new JTextField(15);
		passwordField.setFont(inputfont);
		JLabel password = new JLabel("Desired Password:");
		gbc.gridx = 0; 
		gbc.gridy = 1;  
		gbc.gridwidth = 1; 
		add(password, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1; 
		gbc.gridwidth = 2; 
		add(passwordField,gbc);

		
		JLabel ChooseIcon = new JLabel("Desired Icon:");
		gbc.gridx = 0; 
		gbc.gridy = 3;  
		gbc.gridwidth = 1; 
		add(ChooseIcon, gbc);
		
		ArrayList<JButton> buttonsArray = new ArrayList<JButton>();
		
		JPanel FirstBottomPanel = new JPanel();	
		FirstBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		String [] images = {"BlackCat.png","Cow.png","Elephant1.png",
				"FemaleLion.png","Fox.png","Kitten.png","Lion.png",
				"Penguin.png","Tiger.png","Zebra.png"};
		int left = (images.length)/2;
		for(int i = 0; i < left; i++){
			ImageIcon defaultIcon = new ImageIcon("Pictures/"+images[i]);
			JButton userIconButton = new JButton(defaultIcon);
			FirstBottomPanel.add(userIconButton,gbc);
			buttonsArray.add(userIconButton);
		}
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 5;
		add(FirstBottomPanel, gbc);
		JPanel SecBottomPanel = new JPanel();	
		SecBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		for(int k = left; k < images.length; k++){
			ImageIcon defaultIcon = new ImageIcon("Pictures/"+images[k]);
			JButton userIconButton = new JButton(defaultIcon);
			SecBottomPanel.add(userIconButton,gbc);
			buttonsArray.add(userIconButton);
		}
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 5;
		add(SecBottomPanel, gbc);
		//create class for Icon
		class setIconAction implements ActionListener{
			User us;
			JButton imageButton;
			setIconAction(User u, JButton B){
				this.us = u;
				this.imageButton = B;
			}
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("User image chose:" + imageButton.getIcon());
				us.setImage(imageButton.getIcon());
				//set setImage needs to be ImageIcon
			}
			
		}
		for(int v = 0; v < buttonsArray.size(); v++){
			final JButton B = buttonsArray.get(v);
			B.addActionListener(new setIconAction(user, B));
		}
		
		JLabel BioLabel = new JLabel("Enter Short Bio:");
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 7;
		add(BioLabel, gbc);
		

		Bio = new JTextArea(3,25);
		//Bio.setLineWrap(true);
		Bio.setWrapStyleWord(true);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;
		gbc.ipadx = 2;
		gbc.ipady = 2;
		JScrollPane src = new JScrollPane(Bio,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(src, gbc);
		
		logInButton = new JButton("LogIn");
		gbc.ipadx = 0; 
		gbc.ipady = 0;
		gbc.gridheight = 1;
		gbc.gridwidth =  1;
		gbc.gridx = 0;
		gbc.gridy = 10;
		add(logInButton,gbc);
		
		class LogInListener implements ActionListener {
			User user;
			public LogInListener(User user) {
				this.user = user;
			}

			public void actionPerformed(ActionEvent e) {
				//System.out.println("Button Pressed");
				user.createLoginWindow();
			}
		}
		logInButton.addActionListener(new LogInListener(user));
		
		createAccount = new JButton("Create Account");
		gbc.ipadx = 0; 
		gbc.ipady = 0;
		gbc.gridheight = 1;
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 10;
		class createAcc implements ActionListener{
			private User us;
			createAcc(User u){
				this.us = u;
			}
			public void actionPerformed(ActionEvent ae) {
				//System.out.println("Button Pressed");;
				//ca.pullThePlug();
				us.setPassword(passwordField.getText());
				us.setName(usernameField.getText());
				us.setAboutme(Bio.getText());
				us.createNewAccount();
				System.out.println("Name is:" + us.getName());
				System.out.println("Password is:" + us.getPassword());
				System.out.println("About me is:" + us.getAboutme());
				System.out.println("Image is: " + us.getImage());
				
			}
			
		}
		createAccount.addActionListener(new createAcc(this.user));
		add(createAccount,gbc);
	
		this.setSize(450,500);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static void main(String []args){
		new CreateAccount(user);
	}
	public String getUserNameField(){
		return 	usernameField.getText();
	}
	public String getUserPasswordField(){
		return passwordField.getText();
	}
	public String getUserBio(){
		return Bio.getText();
	}
//	public void pullThePlug() {
//	    WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
//	    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
//	}
}
