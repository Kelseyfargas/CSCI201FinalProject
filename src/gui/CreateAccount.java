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
import javax.swing.JTextField;

public class CreateAccount extends JFrame{
	
	private JButton createAccount;
	private JButton logInButton;
	
	public CreateAccount(String name){
		super(name);
		setLayout(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints(); 

		Font inputfont = new Font("Courrier New", Font.ITALIC, 12);
		JTextField usernameField = new JTextField(15); //user can type in
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
		
		JTextField passwordField = new JTextField(15);
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
		
		for(int v = 0; v < buttonsArray.size(); v++){
			final JButton B = buttonsArray.get(v);
			B.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//if the button is clicked, then assign it to the user
					//something like
					//User User1;
					//User1.setIcon(B.getIcon());
					System.out.println("Selected icon is: "+ B.getIcon());
				}
				
			});
		}
		
		JLabel BioLabel = new JLabel("Enter Short Bio:");
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 7;
		add(BioLabel, gbc);
		
		JTextField Bio = new JTextField(20);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 5;
		gbc.gridheight = 2;
		gbc.ipadx = 20;
		gbc.ipady = 20;
		add(Bio, gbc);
		
		logInButton = new JButton("LogIn");
		gbc.ipadx = 0; 
		gbc.ipady = 0;
		gbc.gridheight = 1;
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 10;
		add(createAccount,gbc);
		createAccount = new JButton("Create Account");
		gbc.ipadx = 0; 
		gbc.ipady = 0;
		gbc.gridheight = 1;
		gbc.gridwidth =  1;
		gbc.gridx = 1;
		gbc.gridy = 10;
		add(createAccount,gbc);
//		createAccount.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Created Account");
//				new LogIn("Login");
//			}
//		});
	
		this.setSize(450,500);
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static void main(String []args){
		
		final CreateAccount ca = new CreateAccount("LogIn");
		ca.createAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Pressed");
				new LogIn("Login");
				ca.pullThePlug();
			}
			
		});
	}
	public void pullThePlug() {
	    WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
	    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
}