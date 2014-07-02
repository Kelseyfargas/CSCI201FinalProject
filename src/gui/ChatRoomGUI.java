package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomGUI extends JFrame {
	
	ChatRoomGUI(String title){
		super(title);

		JPanel jp = new JPanel(); 
		
		final JTextField searchField = new JTextField("Search",10); 
		searchField.setForeground(Color.GRAY);
		final String searchField_username = searchField.getSelectedText();
		jp.add(searchField); 
		
		
		JTextArea toUsernameField = new JTextArea("To: Katrina");
		toUsernameField.setPreferredSize(new Dimension(450,20));
		toUsernameField.setEditable(false); 
		toUsernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		toUsernameField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));

		jp.add(toUsernameField); 
		add(jp, BorderLayout.NORTH); 
		 
		JPanel WestPanel = new JPanel();
		WestPanel.setLayout(new BoxLayout(WestPanel, BoxLayout.Y_AXIS));
		Font f = new Font("Serif", Font.BOLD, 12);
		for(int i = 0; i < 6; i++){
			JButton FriendConversationArea_1 = new JButton( "           Friend" + (i+1)+ "          ");
			FriendConversationArea_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
			FriendConversationArea_1.setFont(f);
			FriendConversationArea_1.setForeground(Color.GRAY);
			FriendConversationArea_1.setPreferredSize(new Dimension(150,100));
			WestPanel.add(FriendConversationArea_1);
			add(WestPanel, BorderLayout.WEST);
		}
		
		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new BoxLayout(CenterPanel, BoxLayout.Y_AXIS));

		Font textfont = new Font("SansSerif", Font.BOLD, 12);
		JTextArea areap = new JTextArea();
	
		areap.setText("Katrina: I hope we get an A in the class!" + 
				"\n"+ "Kelsey: Me also!");
		areap.setPreferredSize(new Dimension(300,300));
		areap.setEditable(false); 
		areap.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		areap.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		
		JPanel messageBottomPanel = new JPanel();
		messageBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField outputTextField = new JTextField("New Message",29);
		outputTextField.setForeground(Color.GRAY);
		outputTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		JButton sendButton = new JButton("Send");
		messageBottomPanel.add(outputTextField);
		messageBottomPanel.add(sendButton);
		
		
		CenterPanel.add(areap);
		CenterPanel.add(messageBottomPanel); 
		add(CenterPanel, BorderLayout.CENTER);
		
		
		
		//necessities
		this.setSize(600,400);
		this.setLocation(350,200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String []args){
		//new LogIn("Login");
	}
}
