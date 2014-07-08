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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import conversation.User;


public class MessageWindow extends JFrame {
	
	public static int GROUP_CHAT = 1;
	public static int PRIVATE_CHAT = 2;
	private JButton addUserButton;
	private JTextArea chatBoxTextArea;
	private JTextField outputTextField;
	private String convoName;
	private User user;
	private String moderator;
	private int messageType;
	
	public MessageWindow(String convoName, User user, int messageType){
		
		super(convoName);
		this.user = user;
		this.messageType = messageType;
		setName(convoName);
		
		if(messageType == GROUP_CHAT){
			this.moderator = user.getName();
		}
		
		//Menu bar
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveConversationMI = new JMenuItem("Save Conversation");
		fileMenu.add(saveConversationMI);
		jmb.add(fileMenu);
		add(jmb);
		
		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Font textfont = new Font("SansSerif", Font.BOLD, 12);
		chatBoxTextArea = new JTextArea();
		chatBoxTextArea.setPreferredSize(new Dimension(590,280));
		chatBoxTextArea.setEditable(false); 
		chatBoxTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatBoxTextArea.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		
		JPanel choicesPanel = new JPanel();
		choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//font choices
		Object fontArray[] = {"SansSerif", "Courier New","Arial", "Times New Roman"};
 		JComboBox fontCB = new JComboBox(fontArray);
 		fontCB.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				//setcenteredTextFieldFont();
 			}
 		});

		//font color
		Object colorArray[] = {"Black","Brown","Grey","Green","Magenta","Blue"};
		JComboBox colorCB = new JComboBox(colorArray);
		colorCB.setSelectedItem("Black");
		colorCB.addItemListener(new ItemListener() {
			
 			public void itemStateChanged(ItemEvent ie){
 				//setcenteredTextFieldFont();
 			}
 		});
		
 		//font size
		Object sizeArray[] = {"8","10","12","16","20"};
		JComboBox sizeCB = new JComboBox(sizeArray);
		sizeCB.setSelectedItem("12");
		sizeCB.addItemListener(new ItemListener() {
			
 			public void itemStateChanged(ItemEvent ie){
 				//setcenteredTextFieldFont();
 			}
 		});
		
		//emoji's
		ImageIcon emojiList[] = {
				new ImageIcon("Emoji/Happy.png")};
        JComboBox emojiCB = new JComboBox(emojiList);
        emojiCB.setMaximumRowCount(3);

 		choicesPanel.add(fontCB);
		choicesPanel.add(colorCB);
		choicesPanel.add(sizeCB);
		choicesPanel.add(emojiCB);
		
		JPanel messageBottomPanel = new JPanel();
		messageBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		outputTextField = new JTextField();
		outputTextField.setForeground(Color.GRAY);
		outputTextField.setPreferredSize(new Dimension(510,20));
		outputTextField.setEditable(true); 
		outputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		outputTextField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new sendButtonAction(outputTextField, messageType));
		messageBottomPanel.add(outputTextField);
		messageBottomPanel.add(sendButton);
		
		
		CenterPanel.add(chatBoxTextArea);

		CenterPanel.add(choicesPanel);
 		
		CenterPanel.add(messageBottomPanel); 
		add(CenterPanel, BorderLayout.CENTER);
		
		if(messageType == GROUP_CHAT){
			user.addGroupConvo(convoName, moderator);
		}
		//necessities
		this.setSize(600,400);
		this.setLocation(350,200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/*******CLASSES FOR THE ChatRoomGUI************/
	/***********************************************/
	/***********************************************/
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
	class startChatClass implements ActionListener{
		startChatClass(JTextField NOCTextField){
			
		}
		public void actionPerformed(ActionEvent ae){
			
		}
	}
	
	class sendButtonAction implements ActionListener{
		String messageinput;
		int messageType;
		sendButtonAction(JTextField outputTextField, int messageType){
			messageinput = outputTextField.getText();
			this.messageType = messageType;
			//outputTextField.setText(messageinput);
			System.out.println("Message is : " + messageinput);
		}
		public void actionPerformed(ActionEvent e) {
			if(messageType == GROUP_CHAT){
				user.sendNewGroupMessage(messageinput,convoName);
			}
			else if(messageType == PRIVATE_CHAT){
				user.sendNewPrivateMessage(messageinput,convoName);
			}
			outputTextField.setText("");
			//updateContent(messageinput);
		}
	}
	/*******FUNCTION FOR THE ChatRoomGUI************/
	/***********************************************/
	/***********************************************/
	public void updateContent(String messageinput){
		String text = chatBoxTextArea.getText();
		if (text == null || text.length() == 0) {
			chatBoxTextArea.setText(messageinput);
		}
		else {
			chatBoxTextArea.setText(chatBoxTextArea.getText() + "\n" + messageinput);
		}
	}
	
	public void setName(String convoName){
		this.convoName = convoName;
	}
	public String getName(){
		return convoName;
	}
	public static void main(String []args){
	}
}
