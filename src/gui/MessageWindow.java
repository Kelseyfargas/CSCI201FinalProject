package gui;
//import gui.BuddyList.addUserToChatClass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import conversation.User;


public class MessageWindow extends JFrame {
	
	public static int GROUP_CHAT = 1;
	public static int PRIVATE_CHAT = 2;
	private JTextArea chatBoxTextArea;
	private JTextField outputTextField;
	private JButton sendButton;
	private JMenuItem removeChatMenuItem;
	private JMenu fileMenu;
	private String convoName;
	private User user;
	//private String moderator;
	private int messageType;
	private JComboBox<?> fontCB;
	private JComboBox<?> colorCB;
	private JComboBox<?> sizeCB;
	private JComboBox<?> fontTypeCB;
	private JComboBox<?> emojiCB;
	private Color HILIT_COLOR = Color.ORANGE;
	private Highlighter hilit;
//	private Highlighter.HighlightPainter painter;
	private String copieditem = "";
	private boolean moderator = false;
	
	//SEND BUTTON-- CHECK IF EMPTY
	//MODERATOR  MENU
		// -- disable send button/chat
		// -- close chat (remove chat)
	
	public MessageWindow(String convoName, User user){
		
		super(convoName);
		this.user = user;
		setName(convoName);
		//Menu bars
		JMenuBar jmb = new JMenuBar();
		fileMenu = new JMenu("File");
		JMenuItem copyMenuItem = new JMenuItem("Copy");
		JMenuItem cutMenuItem = new JMenuItem("Cut");
		JMenuItem pasteMenuItem = new JMenuItem("Paste");
		JMenuItem saveConversationMenuItem = new JMenuItem("Save Conversation");
		removeChatMenuItem = new JMenuItem("Remove Chat");//CHECK FOR MODERATOR
		
		copyMenuItem.addActionListener(new CutCopyPasteAction(copyMenuItem.getText()));
		cutMenuItem.addActionListener(new CutCopyPasteAction(cutMenuItem.getText()));
		pasteMenuItem.addActionListener(new CutCopyPasteAction(pasteMenuItem.getText()));
		saveConversationMenuItem.addActionListener(new saveConversationAction());
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
				ActionEvent.CTRL_MASK));
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 
				ActionEvent.CTRL_MASK));
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 
				ActionEvent.CTRL_MASK));
		saveConversationMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				ActionEvent.CTRL_MASK));
		removeChatMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 
				ActionEvent.CTRL_MASK));
		fileMenu.add(copyMenuItem);
		fileMenu.add(cutMenuItem);
		fileMenu.add(pasteMenuItem);
		fileMenu.add(saveConversationMenuItem);
		jmb.add(fileMenu);
		setJMenuBar(jmb);
		
		JPanel CenterPanel = new JPanel();
		CenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Font textfont = new Font("SansSerif", Font.BOLD, 12);
		chatBoxTextArea = new JTextArea();
		
		chatBoxTextArea.setEditable(false); 
		chatBoxTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatBoxTextArea.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
		chatBoxTextArea.setPreferredSize(new Dimension(590,280));
		JScrollPane chatBoxScrollPane = new JScrollPane(chatBoxTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatBoxScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatBoxScrollPane.setPreferredSize(new Dimension(590,280));
//		DefaultCaret caret = (DefaultCaret)chatBoxTextArea.getCaret();
//		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JPanel choicesPanel = new JPanel();
		choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//font choices
		Object fontArray[] = {"SansSerif", "Courier New","Arial", "Times New Roman"};
 		fontCB = new JComboBox<>(fontArray);
 		
		//font color
		Object colorArray[] = {"Black","Grey","Green","Magenta","Blue", "Pink"};
		colorCB = new JComboBox<>(colorArray);
		colorCB.setSelectedItem("Black");
		
 		//font size
		Object sizeArray[] = {"8","10","12","16"};
		sizeCB = new JComboBox<>(sizeArray);
		sizeCB.setSelectedItem("12");
		
		//font type
		Object fontTypeArray[] = {"BOLD", "ITALIC","PLAIN"};
		fontTypeCB = new JComboBox<>(fontTypeArray);
		fontTypeCB.setSelectedItem("12");
		
//		//emoji's
//		ImageIcon emojiList[] = {
//				new ImageIcon("Emoji/Happy.png")};
//        emojiCB = new JComboBox<>(emojiList);
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
		sendButton = new JButton("Send");
		sendButton.addActionListener(new sendButtonAction(outputTextField, messageType));
		getRootPane().setDefaultButton(sendButton);//sets the ENTER key
		messageBottomPanel.add(outputTextField);
		messageBottomPanel.add(sendButton);
		
		
		CenterPanel.add(chatBoxScrollPane);

		CenterPanel.add(choicesPanel);
 		
		CenterPanel.add(messageBottomPanel); 
		add(CenterPanel, BorderLayout.CENTER);
		
		//necessities
		addWindowListener(new windowClosedAction(convoName));
		this.setSize(600,420);
		this.setLocation(350,200);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
/*******CLASSES FOR THE ChatRoomGUI************/
/***********************************************/

	private class sendButtonAction implements ActionListener{
		JTextField outputTF;
		String messageinput;
		int messageType;
		sendButtonAction(JTextField outputTextField, int messageType){
			this.outputTF = outputTextField;
			this.messageType = messageType;
		}
		public void actionPerformed(ActionEvent e) {
			
			messageinput = outputTF.getText();
//			if(messageType == GROUP_CHAT){
				System.out.println("IN GROUP MESSAGE AFTER SEND BUTTON");
				user.sendNewGroupMessage(messageinput,convoName);
//			}
//			else if(messageType == PRIVATE_CHAT){
//				user.sendNewPrivateMessage(messageinput,convoName);
//			}
			outputTextField.setText("");
			//updateContent(messageinput);
		}
	}
	private class CutCopyPasteAction implements ActionListener{
		
		private String actionword;
		private CutCopyPasteAction(String action){
			this.actionword = action;
		}
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("String is : " + actionword);
			hilit = new DefaultHighlighter();
			new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
		    outputTextField.setHighlighter(hilit);
			if(actionword.equals("Cut")){
				copieditem = outputTextField.getText();
				System.out.println("cut item is : " + copieditem);
				outputTextField.setText("");
				
			}
			else if(actionword.equals("Copy")){
				copieditem = outputTextField.getText();
				System.out.println("copied item is : " + copieditem);
			}
			else if(actionword.equals("Paste")){
				outputTextField.setText(outputTextField.getText()+copieditem);
			}
		}
		
	}
	
	private class saveConversationAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String text = chatBoxTextArea.getText();
		}
	}
	
	private class removeChatAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			//sendButton.setEnabled(false);
			user.removeGroupConvoRequest(convoName);
			user.closeMessageWindow(convoName);
		}
	}
	
	private class windowClosedAction implements WindowListener{
		
		String convoName;
		windowClosedAction(String convoName){
			this.convoName = convoName;
		}

		public void windowClosing(WindowEvent e) {
			System.out.println("WINDOW CLOSING");
			System.out.println("REMOVING: " + convoName);
//			thinking about changing it
//			if(moderator == true){
//				user.removeGroupConvoRequest(convoName);
//			}
			
		}
		//UGGHH UNNCESSEARY
		public void windowClosed(WindowEvent e) {
		}
		public void windowIconified(WindowEvent e) {
		}
		public void windowDeiconified(WindowEvent e) {
		}
		public void windowActivated(WindowEvent e) {
		}
		public void windowDeactivated(WindowEvent e) {
		}
		public void windowOpened(WindowEvent e) {
		}

		
	}
/*******FUNCTION FOR THE ChatRoomGUI************/
/***********************************************/
/***********************************************/
	public void updateContent(String messageinput){
		
		String text = chatBoxTextArea.getText();
		System.out.println(user.getName() + ": " + text);
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

	public void setModerator(){
		this.moderator = true;
		removeChatMenuItem.addActionListener(new removeChatAction());
		fileMenu.add(removeChatMenuItem);
	}
	public boolean getModerator(){
		return this.moderator;
	}
	
	public static void main(String []args){
	}
}
