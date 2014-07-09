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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import conversation.User;


public class MessageWindow extends JFrame {
	
	public static int GROUP_CHAT = 1;
	public static int PRIVATE_CHAT = 2;
	private JTextArea chatBoxTextArea;
	private JTextField outputTextField;
	private JButton sendButton;
	//private JMenuItem removeChatMenuItem;
	private JMenu fileMenu;
	private String convoName;
	private User user;
	private int messageType;
	private JComboBox<?> fontCB;
	private JComboBox<?> colorCB;
	private JComboBox<?> sizeCB;
	private JComboBox<?> fontTypeCB;
	private JComboBox<?> colorCBBackground;
	private Color HILIT_COLOR = Color.ORANGE;
	private Highlighter hilit;
	private String copieditem = "";
	private boolean moderator = false;
	
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
		//removeChatMenuItem = new JMenuItem("Remove Chat");//CHECK FOR MODERATOR
		
		copyMenuItem.addActionListener(new CutCopyPasteAction(copyMenuItem.getText()));
		cutMenuItem.addActionListener(new CutCopyPasteAction(cutMenuItem.getText()));
		pasteMenuItem.addActionListener(new CutCopyPasteAction(pasteMenuItem.getText()));
		saveConversationMenuItem.addActionListener(new saveConversationAction(convoName));
		
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
				ActionEvent.CTRL_MASK));
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 
				ActionEvent.CTRL_MASK));
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 
				ActionEvent.CTRL_MASK));
		saveConversationMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 
				ActionEvent.CTRL_MASK));
		//removeChatMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 
				//ActionEvent.CTRL_MASK));
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
		chatBoxTextArea.setLineWrap(true);
		chatBoxTextArea.setWrapStyleWord(true);
		chatBoxTextArea.setBounds(20, 20, 590, 280);
		JScrollPane chatBoxScrollPane = new JScrollPane(chatBoxTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chatBoxScrollPane.setBounds(20, 20, 590, 280);
		chatBoxScrollPane.setBorder(BorderFactory.createTitledBorder("Message"));
		chatBoxScrollPane.setPreferredSize(new Dimension(590,280));
		DefaultCaret caret = (DefaultCaret)chatBoxTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JPanel choicesPanel = new JPanel();
		choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//font choices
		Object fontArray[] = {"SansSerif", "Courier New","Arial", "Times New Roman"};
 		fontCB = new JComboBox<>(fontArray);
 		
		//font color
		Object colorArray[] = {"White","Black","Grey","Green","Magenta","Blue", 
							"Pink", "Cyan","Orange","Red", "Yellow"};
		colorCB = new JComboBox<>(colorArray);
		colorCB.setSelectedItem("Black");
 		//font size
		Object sizeArray[] = {"8","10","12","14","16"};
		sizeCB = new JComboBox<>(sizeArray);
		sizeCB.setSelectedItem("12");
		
		//font type
		Object fontTypeArray[] = {"BOLD", "ITALIC","PLAIN"};
		fontTypeCB = new JComboBox<>(fontTypeArray);
		fontTypeCB.setSelectedItem("12");
		
		//background color
		colorCBBackground = new JComboBox<>(colorArray);
		colorCBBackground.setSelectedItem("Black");
		
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
        colorCBBackground.addItemListener(new ItemListener(){
 			public void itemStateChanged(ItemEvent ie){
 				System.out.println("IN COLOR CB BACKGROUND");
 				setInputTextField();
 			}
 		});
        JLabel font = new JLabel("Text");
        JLabel background = new JLabel("Background:");
 		choicesPanel.add(fontCB);
 		choicesPanel.add(font);
		choicesPanel.add(colorCB);
		choicesPanel.add(sizeCB);
		choicesPanel.add(fontTypeCB);
		choicesPanel.add(background);
		choicesPanel.add(colorCBBackground);
		
		JPanel messageBottomPanel = new JPanel();
		messageBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		outputTextField = new JTextField();
		outputTextField.setRequestFocusEnabled(true);
		outputTextField.setForeground(Color.WHITE);
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
			if(!messageinput.isEmpty()){//IF IT'S NOT EMPTY
				if(getName().contains("@")){//PRIVATE
					System.out.println("@");
					user.sendNewPrivateMessage(messageinput,convoName);
				}
				else{//GROUP
					user.sendNewGroupMessage(messageinput,convoName);
				}
				outputTextField.setText("");
			}
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
		String convoname;
		
		saveConversationAction(String convoname){
			this.convoname = convoname;
		}
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("SVAAAAVV");
			String text = chatBoxTextArea.getText();
			
	        //PrintWriter printWriter = null;
			try {
//		        FileOutputStream fos = new FileOutputStream(text);
//		        ObjectOutputStream oos = new ObjectOutputStream(fos);
//		        oos.writeObject(list);
//		        oos.close();
				System.out.println("Saiving in try");
				File saveConvoFile = new File(convoname);
				PrintWriter printWriter = new PrintWriter(saveConvoFile +".txt");
				printWriter.println(text);
				printWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, 
					"Saved the convo!", 
					"Save Conversation", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
//	private class removeChatAction implements ActionListener{
//		
//		public void actionPerformed(ActionEvent e){
//			//sendButton.setEnabled(false);
//			user.removeGroupConvoRequest(convoName);
//			user.closeMessageWindow(convoName);
//		}
//	}
	
	
	private class windowClosedAction extends WindowAdapter{
		
		String convoName;
		windowClosedAction(String convoName){
			this.convoName = convoName;
		}

		public void windowClosing(WindowEvent e) {
			System.out.println("WINDOW CLOSING " + convoName);
			user.closeMessageWindow(convoName);
		}
		public void windowOpened(WindowEvent e) {
			outputTextField.requestFocus();
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
	

	public void setInputTextField(){
		
		chatBoxTextArea.setBackground(Color.WHITE);
		Color colors[] = {Color.WHITE,
						  Color.BLACK,Color.GRAY,
						  Color.GREEN,Color.MAGENTA,
						  Color.BLUE, Color.PINK,
						  Color.CYAN, Color.ORANGE,
						  Color.RED, Color.YELLOW};
		
		String font = (String)fontCB.getSelectedItem();
		int color = (int)colorCB.getSelectedIndex();
		int size = Integer.parseInt((String)sizeCB.getSelectedItem());
		String fontType = (String)fontTypeCB.getSelectedItem();
		int backgroundcolor = (int) colorCBBackground.getSelectedIndex();
		
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
		chatBoxTextArea.setBackground(colors[backgroundcolor]);
		outputTextField.setForeground(colors[color]);
		
	}

	public void setModerator(){
		this.moderator = true;
//		removeChatMenuItem.addActionListener(new removeChatAction());
//		fileMenu.add(removeChatMenuItem);
	}
	public boolean getModerator(){
		return this.moderator;
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
