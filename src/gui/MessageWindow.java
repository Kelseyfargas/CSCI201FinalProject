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
	private JComboBox<?> fontCB;
	private JComboBox<?> colorCB;
	private JComboBox<?> sizeCB;
	private JComboBox<?> fontTypeCB;
	private JComboBox<?> emojiCB;
	
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
		setJMenuBar(jmb);
		
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
 		fontCB = new JComboBox(fontArray);
 		
		//font color
		Object colorArray[] = {"Black","Grey","Green","Magenta","Blue", "Pink"};
		colorCB = new JComboBox(colorArray);
		colorCB.setSelectedItem("Black");
		
 		//font size
		Object sizeArray[] = {"8","10","12","16"};
		sizeCB = new JComboBox(sizeArray);
		sizeCB.setSelectedItem("12");
		
		//font type
		Object fontTypeArray[] = {"BOLD", "ITALIC","PLAIN"};
		fontTypeCB = new JComboBox(fontTypeArray);
		fontTypeCB.setSelectedItem("12");
		
//		//emoji's
//		ImageIcon emojiList[] = {
//				new ImageIcon("Emoji/Happy.png")};
//        emojiCB = new JComboBox(emojiList);
//        emojiCB.setMaximumRowCount(3);
        
        fontCB.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				setInputTextField();
 			}
 		});
        colorCB.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				setInputTextField();
 			}
 		});
        sizeCB.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				setInputTextField();
 			}
 		});
        fontTypeCB.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				setInputTextField();
 			}
 		});
//        emojiCB.addItemListener(new ItemListener(){
// 			public void itemStateChanged(ItemEvent ie){
// 				setInputTextField();
// 			}
// 		});
 		choicesPanel.add(fontCB);
		choicesPanel.add(colorCB);
		choicesPanel.add(sizeCB);
		choicesPanel.add(fontTypeCB);
//		choicesPanel.add(emojiCB);
		
		JPanel messageBottomPanel = new JPanel();
		messageBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		outputTextField = new JTextField();
		outputTextField.setForeground(Color.BLACK);
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
			user.sendGroupConvoRequest(convoName);
		}
		//necessities
		this.setSize(600,420);
		this.setLocation(350,200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
/*******CLASSES FOR THE ChatRoomGUI************/
/***********************************************/
/***********************************************/
	private class addUserToChatClass implements ActionListener{
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
	private class startChatClass implements ActionListener{
		startChatClass(JTextField NOCTextField){
			
		}
		public void actionPerformed(ActionEvent ae){
			
		}
	}
	
	private class sendButtonAction implements ActionListener{
		String messageinput;
		int messageType;
		sendButtonAction(JTextField outputTextField, int messageType){
			messageinput = outputTextField.getText();
			this.messageType = messageType;
			//outputTextField.setText(messageinput);
			System.out.println("Message is : " + messageinput);
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("Message type is: "+ messageType);
			if(messageType == GROUP_CHAT){
				System.out.println("IN GROUP MESSAGE AFTER SEND BUTTON");
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
		String text = outputTextField.getText();
		System.out.println(user.getName() + ": " + text);
		if (text == null || text.length() == 0) {
			outputTextField.setText(messageinput);
		}
		else {
			outputTextField.setText(outputTextField.getText() + "\n" + messageinput);
		}
	}
	
	public void setName(String convoName){
		this.convoName = convoName;
	}
	public String getName(){
		return convoName;
	}
	public void setInputTextField(){
		
		Color colors[] = {Color.BLACK,Color.GRAY,
						  Color.GREEN,Color.MAGENTA,
						  Color.BLUE, Color.PINK};
		
		String font = (String)fontCB.getSelectedItem();
		int color = (int)colorCB.getSelectedIndex();
		int size = Integer.parseInt((String)sizeCB.getSelectedItem());
		String fontType = (String)fontTypeCB.getSelectedItem();
		
		if(fontType.equals("BOLD")){
			chatBoxTextArea.setFont(new Font(font,Font.BOLD, size));
			outputTextField.setFont(new Font(font,Font.BOLD, size));
		}
		else if(fontType.equals("PLAIN")){
			chatBoxTextArea.setFont(new Font(font,Font.PLAIN, size));
			outputTextField.setFont(new Font(font,Font.PLAIN, size));
		}
		else if(fontType.equals("ITALIC")){
			chatBoxTextArea.setFont(new Font(font,Font.ITALIC, size));
			outputTextField.setFont(new Font(font,Font.ITALIC, size));
		}
		chatBoxTextArea.setForeground(colors[color]);
		outputTextField.setForeground(colors[color]);
		
	}
	public static void main(String []args){
	}
}
