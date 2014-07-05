package gui;
//import gui.BuddyList.addUserToChatClass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomGUI extends JFrame {
	
	private  JButton addUserButton;
	private JTextField NOCTextField;
	private JTextArea usersAddedTX;
	private JButton startChatButton;
	private JComboBox userSelectedCB;
	
	public ChatRoomGUI(String user){
		super(user);
		
//		final JTextField searchField = new JTextField("Search",10); 
//		searchField.setForeground(Color.GRAY);
//		final String searchField_username = searchField.getSelectedText();
//		jp.add(searchField); 
//		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextArea toUsernameField = new JTextArea("To: " + user);
		JScrollPane scrollPaneUsersAdded = new JScrollPane(toUsernameField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneUsersAdded.setViewportView(toUsernameField);
		toUsernameField.setPreferredSize(new Dimension(450,20));
		toUsernameField.setEditable(false); 
		toUsernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		//toUsernameField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
	
		JButton AddMoreUsersButton = new JButton("+");
		
		topPanel.add(scrollPaneUsersAdded); 
		topPanel.add(AddMoreUsersButton); 
		add(topPanel, BorderLayout.NORTH); 
		 
//		JPanel WestPanel = new JPanel();
//		WestPanel.setLayout(new BoxLayout(WestPanel, BoxLayout.Y_AXIS));
//		Font f = new Font("Serif", Font.BOLD, 12);
//		for(int i = 0; i < 6; i++){
//			JButton FriendConversationArea_1 = new JButton( "           Friend" + (i+1)+ "          ");
//			FriendConversationArea_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
//			FriendConversationArea_1.setFont(f);
//			FriendConversationArea_1.setForeground(Color.GRAY);
//			FriendConversationArea_1.setPreferredSize(new Dimension(150,100));
//			WestPanel.add(FriendConversationArea_1);
//			add(WestPanel, BorderLayout.WEST);
//		}
//		
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
		JTextField outputTextField = new JTextField("New Message",35);
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
	class addUserToChatClass implements ActionListener{
		private String cn;
		private JTextArea tx;	
		addUserToChatClass(String selected, JTextArea tx){
			this.cn = selected;
			this.tx = tx;
			
		}
		public void actionPerformed(ActionEvent ae){
//			System.out.println(userSelectedCB.getItemListeners());
//			String item = (String)userSelectedCB.getSelectedItem();
			System.out.println("item is: " + cn);
			tx.setText(cn+ ", ");
			//temp += cn;
			System.out.println("temp is: " + cn);
		}


	}
	private void addUsersToTextField(String selected, JTextArea jta){
		addUserButton.addActionListener( new addUserToChatClass(selected, jta));
	}
	class startChatClass implements ActionListener{
		startChatClass(JTextField NOCTextField){
			
		}
		public void actionPerformed(ActionEvent ae){
			
		}
	}
	static private String selectedString(ItemSelectable is) {
	    Object selected[] = is.getSelectedObjects();
	    return ((selected.length == 0) ? "null" : (String) selected[0]);
	 }
	public void createDialogeGroupMessage(){
		 JDialog jd = new JDialog();
		 jd.setTitle("Group Message");
		 jd.setLocation(450,100);
		 jd.setSize(350,100);
		 jd.setModal(true);
		 
		 JPanel jp = new JPanel();
		 jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS)); 
		 
		 JPanel topPanel = new JPanel();
		 topPanel.setLayout( new FlowLayout(FlowLayout.LEFT));
		 JLabel nameOfConversation = new JLabel("Name the Conversation:");
		 NOCTextField = new JTextField("Conversation name", 13);
		 topPanel.add(nameOfConversation);
		 topPanel.add(NOCTextField);
		 
//		 JPanel centerPanel = new JPanel();
//		 centerPanel.setLayout( new FlowLayout(FlowLayout.LEFT));
//		 JLabel usersAddedToConversation = new JLabel("Users Added:");
//		 usersAddedTX = new JTextArea("Users Added field", 1, 20);
//		 usersAddedTX.setEditable(false);
//		 JScrollPane scrollPaneUsersAdded = new JScrollPane(usersAddedTX,
//				 	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		 centerPanel.add(usersAddedToConversation);
//		 centerPanel.add(scrollPaneUsersAdded);
//		 
		 JPanel bottomPanel = new JPanel();
		 bottomPanel.setLayout( new FlowLayout(FlowLayout.RIGHT));
//		 final String options[] = {"User 1", "User 2", "User 3", "User 4"};
//		 //JLabel chooseUsersLabel = new JLabel("Add+:");
//		 userSelectedCB = new JComboBox(options);
//		 userSelectedCB.setSelectedItem(options);
//		 userSelectedCB.addItemListener(new ItemListener(){
//	 			public void itemStateChanged(ItemEvent itemEvent){
//	 				int state = itemEvent.getStateChange();
//	 		        System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
//	 		        System.out.println("Item: " + itemEvent.getItem());
//	 		        ItemSelectable is = itemEvent.getItemSelectable();
//	 		        String selected = selectedString(is);
//	 		        System.out.println(", Selected: " + selected);
//	 				 addUsersToTextField(selected, usersAddedTX);//adds users to TF
//	 			}
//	 		});
//		 userSelectedCB.setForeground(Color.blue);
//		 userSelectedCB.setBackground(Color.white);
////		 userSelectedCB.setSelectedItem("User 1");
//		 addUserButton = new JButton("Add User");
		 startChatButton = new JButton("Start Chat");
		 startChatButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent ae){
				 new ChatRoomGUI(NOCTextField.getText());
			 }
		 });
//		 //bottomPanel.add(chooseUsersLabel);
//		 bottomPanel.add(userSelectedCB);
//		 bottomPanel.add(addUserButton);
		 bottomPanel.add(startChatButton);

		 jp.add(topPanel);
//		 jp.add(centerPanel);
		 jp.add(bottomPanel);
		 jd.add(jp);
		 jd.setVisible(true);
	}
	public static void main(String []args){
		//new LogIn("Login");
	}
}
