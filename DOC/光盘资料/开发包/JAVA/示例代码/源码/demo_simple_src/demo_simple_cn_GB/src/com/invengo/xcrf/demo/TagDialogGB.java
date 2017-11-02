package com.invengo.xcrf.demo;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.GBMemoryBank;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.GBAccessReadTag;
import invengo.javaapi.protocol.IRP1.GBConfigTagLockOrSafeMode;
import invengo.javaapi.protocol.IRP1.GBConfigTagLockOrSafeMode.LockAction;
import invengo.javaapi.protocol.IRP1.GBDynamicWriteTag;
import invengo.javaapi.protocol.IRP1.GBEraseTag;
import invengo.javaapi.protocol.IRP1.GBInactivateTag;
import invengo.javaapi.protocol.IRP1.GBSelectTag;
import invengo.javaapi.protocol.IRP1.GBWriteTag;
import invengo.javaapi.protocol.IRP1.PowerOff_800;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import zht.title.ZHTTitle2;
import zht.title.ZHTTitleBorder;

import com.invengo.xcrf.view.DemoUtil;
import com.invengo.xcrf.view.TextDocument;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class TagDialogGB extends JDialog implements IMessageNotificationReceivedHandle{

	private static final long serialVersionUID = -132648676917423305L;

	private JPanel contentPanel;

	private Defaults defaults;

	private JLabel epcLabel;
	private JTextField epcText;
	private JLabel passwordLabel;
	private JPasswordField passwordText;
	
	private static final String[] SAFE_AREA_ITEM_LIST = {"灭活口令", "锁定口令"};
	private static final String[] USER_DATA_AREA_ITEM_LIST = {"读口令", "写口令"};
	private static final String[] LOCK_ACTIVE_LIST = {"可读可写", "可读不可写", "不可读可写", "不可读不可写"};
	private static final String[] MODE_ACTIVE_LIST = {"保留", "不需要鉴别", "需要鉴别，不需要安全通信", "需要鉴别，需要安全通信"};

	private GBMemoryBank bank;
	private int ptr;
	private byte[] tagID;
	private int rule = 1;
	private int target = 1;
	private JComboBox targetBox;
	private JComboBox ruleBox;
	
	private JTextArea readContentArea;
	private JPasswordField readPasswordText;
	private JTextField readUserNoField;
	private JSpinner.DefaultEditor readPointerEditor;
	private JSpinner.DefaultEditor readLengthEditor;
	
	private JPasswordField writePasswordField;
	private JComboBox writeAreaBox;
	private JTextField writeUserNoField;
	private JSpinner.DefaultEditor writePointerEditor;
	private JSpinner.DefaultEditor writeLengthEditor;
	private JTextArea writeDataArea;
	
	private JPasswordField configTagPasswordField;
	private JComboBox configStoreAreaBox;
	private JTextField configLockUserNoField;
	private JRadioButton safeAreaButton;
	private JRadioButton userDataAreaButton;
	private JComboBox configTypeBox;
	private JLabel configUserNoLabel;
	private JTextField configUserNoField;
	private JLabel configUserDataNoLabel;
	private JPasswordField configOldPasswordField;
	private JPasswordField configNewPasswordField;
	private JPasswordField confirmNewPasswordField;
	
	private JRadioButton lockRadioButton;
	private JRadioButton modeRadioButton;
	private JComboBox configActiveBox;
	
	private JButton dynamicWriteButton;
	private JButton stopButton;
	private JLabel resultLabel;
	
	private JPasswordField configInactivatePasswordField;
	private int antenna;

	/**
	 * @param ant
	 * @param id
	 * @param flag 0-编码区	1-信息区
	 * @param def
	 */
	public TagDialogGB(int ant, String id, int flag, Defaults def) {
		this.defaults = def;
		this.antenna = ant;
		if (flag == 0) {//编码区
			bank = GBMemoryBank.GBEPCMemory;
			ptr = 0;
		} else if (flag == 1) {//信息区
			bank = GBMemoryBank.GBTidMemory;
			ptr = 0;
		} 
//		else if(flag == 2){
//			bank = GBMemoryBank.GBUser1Memory;
//		}
		tagID = Util.convertHexStringToByteArray(id);
		
		NimRODTheme nt = new NimRODTheme("NimRODThemeFile.theme");
		NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
		try {
			UIManager.setLookAndFeel(NimRODLF);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();

		}
		setUndecorated(true);
		setModal(true);
		setBounds(200, 200, 700, 430);

		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new ZHTTitleBorder());
		setContentPane(contentPanel);

		ZHTTitle2 title = new ZHTTitle2(this, "国标标签操作");
		contentPanel.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		contentPanel.add(panel, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panel.add(mainPanel, BorderLayout.NORTH);
		
		epcLabel = new JLabel("标签数据:");
		epcLabel.setHorizontalAlignment(JLabel.RIGHT);
		mainPanel.add(epcLabel);

		epcText = new JTextField();
		epcText.setEditable(false);
		epcText.setText(id);
//		epcText.setColumns(32);
		epcText.setPreferredSize(new Dimension(300, epcText.getPreferredSize().height));
		mainPanel.add(epcText);

		JLabel targetLabel = new JLabel("目标:");
		mainPanel.add(targetLabel);
		
		String[] targetItem = {"S0", "S1", "S2", "S3", "SL"};
		targetBox = new JComboBox(targetItem);
		targetBox.setSelectedIndex(4);
		mainPanel.add(targetBox);
		
		JLabel ruleLabel = new JLabel("规则:");
		mainPanel.add(ruleLabel);
		
		String[] ruleItem = { "0", "1", "2", "3" };
		ruleBox = new JComboBox(ruleItem);
		mainPanel.add(ruleBox);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, 700, 400 - mainPanel.getPreferredSize().height - 20);
		panel.add(tabbedPane);
		
		JPanel readUserDataPanel = new JPanel(null);
		tabbedPane.add(readUserDataPanel, "读用户子区");
		
		JScrollPane contentAreaPane = new JScrollPane();
		contentAreaPane.setBounds(5, 5, 680, 240);
		contentAreaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		readContentArea = new JTextArea();
		readContentArea.setEditable(false);
		contentAreaPane.setViewportView(readContentArea);
		readUserDataPanel.add(contentAreaPane);
		
		JLabel readPasswordLabel = new JLabel("访问密码:");
		readPasswordLabel.setBounds(5, 250, 60, 25);
		readUserDataPanel.add(readPasswordLabel);
		
		readPasswordText = new JPasswordField();
		readPasswordText.setDocument(new TextDocument(8, 0));
		readPasswordText.setText("00000000");
		readPasswordText.setBounds(70, 250, 110, 23);
		readUserDataPanel.add(readPasswordText);
		
		JLabel readUserNoLabel = new JLabel("用户子区编号:");
		readUserNoLabel.setBounds(185, 250, 80, 25);
		readUserDataPanel.add(readUserNoLabel);
		
		readUserNoField = new JTextField();
		readUserNoField.setDocument(new TextDocument(2, 1));
		readUserNoField.setBounds(270, 250, 50, 23);
		readUserNoField.setText("0");
		readUserDataPanel.add(readUserNoField);
		
		JLabel readUserDataNoLabel = new JLabel("0~15");
		readUserDataNoLabel.setBounds(325, 250, 40, 25);
		readUserDataPanel.add(readUserDataNoLabel);

		JLabel readPointerLabel = new JLabel("首地址:");
		readPointerLabel.setBounds(370, 250, 40, 25);
		readUserDataPanel.add(readPointerLabel);
		
		SpinnerNumberModel readPointerModel = new SpinnerNumberModel(4, 0, 65535, 2);
		JSpinner readPointerSpinnerc = new JSpinner(readPointerModel);
		readPointerSpinnerc.setBounds(415, 250, 60, 23);
		readPointerEditor = (JSpinner.DefaultEditor) readPointerSpinnerc.getEditor();
		readPointerEditor.getTextField().getActionMap().put(
				readPointerEditor.getTextField().getInputMap().get(KeyStroke.getKeyStroke("ctrl V")), new AbstractAction() {
					private static final long serialVersionUID = 1L;
					public void actionPerformed(ActionEvent e) {
					}
				});
		readPointerEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}

		});
		readPointerEditor.getTextField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent focusevent) {
				String s = readPointerEditor.getTextField().getText();
				if (null == s || "".equals(s)) {
					readPointerEditor.getTextField().setText("0");
				}
			}

		});
		readUserDataPanel.add(readPointerSpinnerc);
		
		JLabel readLengthLabel = new JLabel("长度:");
		readLengthLabel.setBounds(480, 250, 30, 25);
		readUserDataPanel.add(readLengthLabel);

		SpinnerNumberModel readLengthModel = new SpinnerNumberModel(8, 0, 32, 1);
		JSpinner readLengthSpinner = new JSpinner(readLengthModel);
		readLengthSpinner.setBounds(515, 250, 60, 23);
		readLengthEditor = (JSpinner.DefaultEditor) readLengthSpinner.getEditor();
		readLengthEditor.getTextField().getActionMap().put(
				readLengthEditor.getTextField().getInputMap().get(KeyStroke.getKeyStroke("ctrl V")),
				new AbstractAction() {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {

					}
				});
		readLengthEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}
		});
		readLengthEditor.getTextField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent focusevent) {
				String s = readLengthEditor.getTextField().getText();
				if (null == s || "".equals(s)) {
					readLengthEditor.getTextField().setText("0");
				}
				if (Integer.parseInt(s) < 2) {
					readLengthEditor.getTextField().setText("0");
				}
			}

		});
		readUserDataPanel.add(readLengthSpinner);
		
		JButton readButton = new JButton("读取");
		readButton.setBounds(590, 250, 80, 25);
		readButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readTagUserData();
			}
		});
		readUserDataPanel.add(readButton);
		
		JButton userDataClearButton = new JButton("清空");
		userDataClearButton.setBounds(590, 280, 80, 25);
		readUserDataPanel.add(userDataClearButton);
		userDataClearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				readContentArea.setText("");
			}
		});
		
		JPanel writePanel = new JPanel(null);
		tabbedPane.add(writePanel, "写操作");
		
		JLabel writePasswordLabel = new JLabel("访问密码:");
		writePasswordLabel.setBounds(5, 5, 60, 25);
		writePanel.add(writePasswordLabel);
		
		writePasswordField = new JPasswordField();
		writePasswordField.setDocument(new TextDocument(8, 0));
		writePasswordField.setText("00000000");
		writePasswordField.setBounds(65, 5, 110, 23);
		writePanel.add(writePasswordField);
		
		JLabel writeAreaLabel = new JLabel("写入区域:");
		writeAreaLabel.setBounds(180, 5, 60, 25);
		writePanel.add(writeAreaLabel);
		
		writeAreaBox = new JComboBox();
		writeAreaBox.addItem("编码区");
		writeAreaBox.addItem("安全区");
		writeAreaBox.addItem("用户子区");
		writeAreaBox.setBounds(240, 5, 100, 23);
		writePanel.add(writeAreaBox);
		
		writeUserNoField = new JTextField();
		writeUserNoField.setDocument(new TextDocument(2, 1));
		writeUserNoField.setBounds(345, 5, 50, 23);
		writeUserNoField.setVisible(false);
		writePanel.add(writeUserNoField);
		
		final JLabel writeUserDataNoLabel = new JLabel("0~15");
		writeUserDataNoLabel.setBounds(395, 5, 40, 25);
		writeUserDataNoLabel.setVisible(false);
		writePanel.add(writeUserDataNoLabel);
		
		writeAreaBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					int selectedIndex = writeAreaBox.getSelectedIndex();
					if(selectedIndex == 2){
						writeUserNoField.setVisible(true);
						writeUserDataNoLabel.setVisible(true);
					}else{
						writeUserNoField.setVisible(false);
						writeUserDataNoLabel.setVisible(false);
					}
				}
			}
		});
		
		JLabel writePointerLabel = new JLabel("首地址:");
		writePointerLabel.setBounds(16, 30, 40, 25);
		writePanel.add(writePointerLabel);
		
		SpinnerNumberModel writePointerModel = new SpinnerNumberModel(4, 0, 65535, 2);
		JSpinner writePointerSpinnerc = new JSpinner(writePointerModel);
		writePointerSpinnerc.setBounds(64, 30, 110, 23);
		writePointerEditor = (JSpinner.DefaultEditor) writePointerSpinnerc.getEditor();
		writePointerEditor.getTextField().getActionMap().put(
				writePointerEditor.getTextField().getInputMap().get(KeyStroke.getKeyStroke("ctrl V")), new AbstractAction() {
					private static final long serialVersionUID = 1L;
					public void actionPerformed(ActionEvent e) {
					}
				});
		writePointerEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}

		});
		writePointerEditor.getTextField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent focusevent) {
				String s = writePointerEditor.getTextField().getText();
				if (null == s || "".equals(s)) {
					writePointerEditor.getTextField().setText("0");
				}
			}

		});
		writePanel.add(writePointerSpinnerc);
		
		JLabel writeLengthLabel = new JLabel("长度:");
		writeLengthLabel.setBounds(203, 30, 30, 25);
		writePanel.add(writeLengthLabel);

		SpinnerNumberModel writeLengthModel = new SpinnerNumberModel(8, 0, 32, 2);
		JSpinner writeLengthSpinner = new JSpinner(writeLengthModel);
		writeLengthSpinner.setBounds(239, 30, 100, 23);
		writeLengthEditor = (JSpinner.DefaultEditor) writeLengthSpinner.getEditor();
		writeLengthEditor.getTextField().getActionMap().put(
				writeLengthEditor.getTextField().getInputMap().get(KeyStroke.getKeyStroke("ctrl V")),
				new AbstractAction() {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {

					}
				});
		writeLengthEditor.getTextField().addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}
		});
		writeLengthEditor.getTextField().addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent focusevent) {
				String s = writeLengthEditor.getTextField().getText();
				if (null == s || "".equals(s)) {
					writeLengthEditor.getTextField().setText("0");
				}
				if (Integer.parseInt(s) < 2) {
					writeLengthEditor.getTextField().setText("0");
				}
			}

		});
		writePanel.add(writeLengthSpinner);
		
		JLabel writeDataLabel = new JLabel("数据:");
		writeDataLabel.setBounds(5, 55, 30, 25);
		writePanel.add(writeDataLabel);
		
		JScrollPane writeDataAreaPane = new JScrollPane();
		writeDataAreaPane.setBounds(5, 80, 680, 170);
		writeDataAreaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		writeDataAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		writeDataArea = new JTextArea();
		writeDataArea.setRows(10);
		writeDataArea.setColumns(90);
		writeDataArea.setLineWrap(true);
		writeDataArea.setWrapStyleWord(true);
		writeDataArea.setDocument(new TextDocument(510, 0));
		writeDataAreaPane.setViewportView(writeDataArea);
		writePanel.add(writeDataAreaPane);
		
		resultLabel = new JLabel("test");
		resultLabel.setBounds(5, 255, 100, 25);
		writePanel.add(resultLabel);
		
		dynamicWriteButton = new JButton("动态写");
		dynamicWriteButton.setBounds(420, 255, 80, 25);
		writePanel.add(dynamicWriteButton);
		
		stopButton = new JButton("停止");
		stopButton.setBounds(420, 285, 80, 25);
		writePanel.add(stopButton);
		dynamicWriteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaults.isConnected){
					dynamicWriteData();
				}
			}
		});
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopDynamicWrite();
			}
		});
		
		JButton writeButton = new JButton("写数据");
		writeButton.setBounds(505, 255, 80, 25);
		writeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				writeTagData();
			}
		});
		writePanel.add(writeButton);
		
		JButton wipeButton = new JButton("擦除数据");
		wipeButton.setBounds(590, 255, 80, 25);
		wipeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wipeTagData();
			}
		});
		writePanel.add(wipeButton);

		BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
		JPanel tagConfigPanel = new JPanel(null);
		tabbedPane.add(tagConfigPanel, "标签配置");
		
		TitledBorder passwordTitledBorder = new TitledBorder(bb, "密码配置",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, defaults.borderFont,
				defaults.titleColor);
		
		JPanel passwordConfigPanel = new JPanel(null);
		passwordConfigPanel.setBounds(0, 5, 690, 85);
		passwordConfigPanel.setBorder(passwordTitledBorder);
		tagConfigPanel.add(passwordConfigPanel);
		
		JLabel configAreaLabel = new JLabel("设置区域:");
		configAreaLabel.setBounds(5, 20, 60, 25);
		passwordConfigPanel.add(configAreaLabel);
		
		safeAreaButton = new JRadioButton("安全区");
		safeAreaButton.setBounds(65, 20, 70, 22);
		safeAreaButton.setSelected(true);
		passwordConfigPanel.add(safeAreaButton);
		safeAreaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(safeAreaButton.isSelected()){
					configUserNoLabel.setVisible(false);
					configUserNoField.setVisible(false);
					configUserDataNoLabel.setVisible(false);
					if(null != configTypeBox){
						configTypeBox.removeAllItems();
						for(String item : SAFE_AREA_ITEM_LIST){
							configTypeBox.addItem(item);
						}
					}
				}
			}
		});
		
		userDataAreaButton = new JRadioButton("用户子区");
		userDataAreaButton.setBounds(135, 20, 85, 22);
		passwordConfigPanel.add(userDataAreaButton);
		userDataAreaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userDataAreaButton.isSelected()){
					configUserNoLabel.setVisible(true);
					configUserNoField.setVisible(true);
					configUserDataNoLabel.setVisible(true);
					if(null != configTypeBox){
						configTypeBox.removeAllItems();
						for(String item : USER_DATA_AREA_ITEM_LIST){
							configTypeBox.addItem(item);
						}
					}
				}
			}
		});
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(safeAreaButton);
		buttonGroup.add(userDataAreaButton);
		
		JLabel configTypeLabel = new JLabel("类型:");
		configTypeLabel.setBounds(225, 20, 30, 25);
		passwordConfigPanel.add(configTypeLabel);
		
		configTypeBox = new JComboBox(SAFE_AREA_ITEM_LIST);
		configTypeBox.setBounds(255, 20, 120, 23);
		passwordConfigPanel.add(configTypeBox);
		
		configUserNoLabel = new JLabel("子区编号:");
		configUserNoLabel.setBounds(380, 20, 60, 23);
		configUserNoLabel.setVisible(false);
		passwordConfigPanel.add(configUserNoLabel);
		
		configUserNoField = new JTextField();
		configUserNoField.setDocument(new TextDocument(2, 1));
		configUserNoField.setBounds(440, 20, 50, 23);
		configUserNoField.setVisible(false);
		passwordConfigPanel.add(configUserNoField);
		
		configUserDataNoLabel = new JLabel("(0~15)");
		configUserDataNoLabel.setBounds(490, 20, 40, 25);
		configUserDataNoLabel.setVisible(false);
		passwordConfigPanel.add(configUserDataNoLabel);

		JLabel configOldPasswordLabel = new JLabel("旧口令:");
		configOldPasswordLabel.setBounds(16, 50, 60, 25);
		passwordConfigPanel.add(configOldPasswordLabel);
		
		configOldPasswordField = new JPasswordField();
		configOldPasswordField.setDocument(new TextDocument(8, 0));
		configOldPasswordField.setText("00000000");
		configOldPasswordField.setBounds(65, 50, 120, 23);
		passwordConfigPanel.add(configOldPasswordField);

		JLabel configNewPasswordLabel = new JLabel("新口令:");
		configNewPasswordLabel.setBounds(211, 50, 60, 25);
		passwordConfigPanel.add(configNewPasswordLabel);
		
		configNewPasswordField = new JPasswordField();
		configNewPasswordField.setDocument(new TextDocument(8, 0));
		configNewPasswordField.setText("00000000");
		configNewPasswordField.setBounds(255, 50, 120, 23);
		passwordConfigPanel.add(configNewPasswordField);
		
		JLabel confirmNewPasswordLabel = new JLabel("确认口令:");
		confirmNewPasswordLabel.setBounds(380, 50, 60, 25);
		passwordConfigPanel.add(confirmNewPasswordLabel);
		
		confirmNewPasswordField = new JPasswordField();
		confirmNewPasswordField.setDocument(new TextDocument(8, 0));
		confirmNewPasswordField.setText("00000000");
		confirmNewPasswordField.setBounds(440, 50, 120, 23);
		passwordConfigPanel.add(confirmNewPasswordField);

		JButton configPasswordButton = new JButton("设置");
		configPasswordButton.setBounds(590, 50, 80, 25);
		passwordConfigPanel.add(configPasswordButton);
		configPasswordButton.setEnabled(false);
		configPasswordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				configTagPassword();
			}
		});
		
		TitledBorder lockModeTitledBorder = new TitledBorder(bb, "模式配置",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, defaults.borderFont,
				defaults.titleColor);

		JPanel configLockModePanel = new JPanel(null);
		configLockModePanel.setBounds(0, 100, 690, 85);
		configLockModePanel.setBorder(lockModeTitledBorder);
		tagConfigPanel.add(configLockModePanel);
		
		JLabel configTagPasswordLabel = new JLabel("锁密码:");
		configTagPasswordLabel.setBounds(16, 20, 60, 25);
		configLockModePanel.add(configTagPasswordLabel);
		
		configTagPasswordField = new JPasswordField();
		configTagPasswordField.setDocument(new TextDocument(8, 0));
		configTagPasswordField.setText("00000000");
		configTagPasswordField.setBounds(65, 20, 120, 25);
		configLockModePanel.add(configTagPasswordField);
		
		JLabel configStoreAreaLabel = new JLabel("存储区域:");
		configStoreAreaLabel.setBounds(199, 20, 80, 25);
		configLockModePanel.add(configStoreAreaLabel);
		
		configStoreAreaBox = new JComboBox();
		configStoreAreaBox.addItem("信息区");
		configStoreAreaBox.addItem("编码区");
		configStoreAreaBox.addItem("安全区");
		configStoreAreaBox.addItem("用户子区");
		configStoreAreaBox.setBounds(255, 20, 120, 23);
		configLockModePanel.add(configStoreAreaBox);
		
		configLockUserNoField = new JTextField();
		configLockUserNoField.setDocument(new TextDocument(2, 1));
		configLockUserNoField.setBounds(375, 20, 50, 23);
		configLockUserNoField.setVisible(false);
		configLockModePanel.add(configLockUserNoField);
		
		final JLabel configLockUserDataNoLabel = new JLabel("(0~15)");
		configLockUserDataNoLabel.setBounds(425, 20, 40, 25);
		configLockUserDataNoLabel.setVisible(false);
		configLockModePanel.add(configLockUserDataNoLabel);
		configStoreAreaBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					if(null != configLockUserNoField && null != configLockUserDataNoLabel){
						int selectIndex = configStoreAreaBox.getSelectedIndex();
						if(selectIndex == 3){
							configLockUserNoField.setVisible(true);
							configLockUserDataNoLabel.setVisible(true);
						}else{
							configLockUserNoField.setVisible(false);
							configLockUserDataNoLabel.setVisible(false);
						}
					}
				}
			}
		});

		JLabel configLockModeLabel = new JLabel("配置:");
		configLockModeLabel.setBounds(27, 50, 30, 25);
		configLockModePanel.add(configLockModeLabel);
		
		lockRadioButton = new JRadioButton("锁模式");
		lockRadioButton.setSelected(true);
		lockRadioButton.setBounds(60, 50, 70, 23);
		configLockModePanel.add(lockRadioButton);
		lockRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lockRadioButton.isSelected()){
					if(null != configActiveBox){
						configActiveBox.removeAllItems();
						for(String lock : LOCK_ACTIVE_LIST){
							configActiveBox.addItem(lock);
						}
					}
				}
			}
		});
		
		modeRadioButton = new JRadioButton("安全模式");
		modeRadioButton.setBounds(130, 50, 85, 23);
		configLockModePanel.add(modeRadioButton);
		modeRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modeRadioButton.isSelected()){
					if(null != configActiveBox){
						configActiveBox.removeAllItems();
						for(String mode : MODE_ACTIVE_LIST){
							configActiveBox.addItem(mode);
						}
					}
				}
			}
		});
		
		
		ButtonGroup configButtonGroup = new ButtonGroup();
		configButtonGroup.add(lockRadioButton);
		configButtonGroup.add(modeRadioButton);
		
		JLabel configActiveLabel = new JLabel("动作:");
		configActiveLabel.setBounds(223, 50, 30, 25);
		configLockModePanel.add(configActiveLabel);
		
		configActiveBox = new JComboBox(LOCK_ACTIVE_LIST);
		configActiveBox.setBounds(255, 50, 180, 23);
		configLockModePanel.add(configActiveBox);
		
		JButton configLockModeButton = new JButton("设置");
		configLockModeButton.setBounds(590, 50, 80, 25);
		configLockModePanel.add(configLockModeButton);
		configLockModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configTagMode();
			}
		});
		
		TitledBorder inactivateTitledBorder = new TitledBorder(bb, "标签灭活",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, defaults.borderFont,
				defaults.titleColor);

		JPanel configInactivateTagPanel = new JPanel(null);
		configInactivateTagPanel.setBorder(inactivateTitledBorder);
		configInactivateTagPanel.setBounds(0, 195, 690, 90);
		tagConfigPanel.add(configInactivateTagPanel);
		
		JLabel configInactivatePasswordLabel = new JLabel("灭活密码:");
		configInactivatePasswordLabel.setBounds(16, 20, 60, 25);
		configInactivateTagPanel.add(configInactivatePasswordLabel);
		
		configInactivatePasswordField = new JPasswordField();
		configInactivatePasswordField.setDocument(new TextDocument(8, 0));
		configInactivatePasswordField.setText("00000000");
		configInactivatePasswordField.setBounds(65, 20, 120, 25);
		configInactivateTagPanel.add(configInactivatePasswordField);

		JButton configInactivateButton = new JButton("设置");
		configInactivateButton.setBounds(590, 50, 80, 25);
		configInactivateTagPanel.add(configInactivateButton);
		configInactivateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inactivateTag();
			}
		});
		
		this.defaults.reader.onMessageNotificationReceived.add(this);
	}

	private boolean isReading = false;
	protected void dynamicWriteData() {
		if(SwingUtilities.isEventDispatchThread()){
			if(!validateParamData()){
				return;
			}
			if(null == writeDataArea.getText() || "".equals(writeDataArea.getText())){
				Defaults.messageDialog.showMessage("数据不得为空，请重试！", 1000);
				writeDataArea.requestFocus();
				return;

			}
			result = 0;
			resultLabel.setText("");
			isReading = true;
			dynamicWriteButton.setEnabled(false);
			stopButton.setEnabled(true);
			
			if(selectTag()){
				String password = String.valueOf(writePasswordField.getPassword());
				GBMemoryBank bank = null;
				if(writeAreaBox.getSelectedIndex() == 0){
					bank = GBMemoryBank.GBEPCMemory;
				}else if(writeAreaBox.getSelectedIndex() == 1){
					bank = GBMemoryBank.GBReservedMemory;
				}else if(writeAreaBox.getSelectedIndex() == 2){
					bank = DemoUtil.getUserBank(Integer.parseInt(writeUserNoField.getText().trim()));
				}
				int userHeadAddress = Integer.parseInt(writePointerEditor.getTextField().getText().trim());
				int length = Integer.parseInt(writeLengthEditor.getTextField().getText().trim());
				String dataStr = writeDataArea.getText().trim();
				byte[] data = Util.convertHexStringToByteArray(dataStr);
				GBDynamicWriteTag msg = new GBDynamicWriteTag(antenna, password, bank, userHeadAddress, data);
//				GBWriteTag writeTag = new GBWriteTag(antenna, password, bank, userHeadAddress, data);
				if(defaults.reader.send(msg)){
					Defaults.messageDialog.showMessage("发送动态写指令成功！", 1000);
				}else{
					Defaults.messageDialog.showMessage("发送动态写指令失败，请重试！", 1000);
				}
			}else{
				Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
			}
			
			writeAreaBox.setSelectedIndex(0);
			writePointerEditor.getTextField().setText("4");
			writeLengthEditor.getTextField().setText("8");
			writeDataArea.setText("");
		}else{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					dynamicWriteData();
				}
			});
		}
	}

	protected void configTagPassword() {
//		if(userDataAreaButton.isSelected()){
//			if(configUserNoField.getText() == null || "".equals(configUserNoField.getText())){
//				Defaults.messageDialog.showMessage("用户子区编号不得为空，请重新输入！", 1000);
//				configUserNoField.requestFocus();
//				return;
//			}
//			if((Integer.parseInt(configUserNoField.getText().trim())) > 15){
//				Defaults.messageDialog.showMessage("用户子区编号不得大于15，请重新输入！", 1000);
//				configUserNoField.requestFocus();
//				return;
//			}
//		}
//		if (configOldPasswordField.getPassword() == null) {
//			Defaults.messageDialog.showMessage("旧口令不得为空，请重新输入！", 1000);
//			configOldPasswordField.requestFocus();
//			return;
//		}
//		if (configOldPasswordField.getPassword().length < 8) {
//			Defaults.messageDialog.showMessage("旧口令不得少于8位，请重新输入！", 1000);
//			configOldPasswordField.requestFocus();
//			return;
//		}
//		if (configNewPasswordField.getPassword() == null) {
//			Defaults.messageDialog.showMessage("新口令不得为空，请重新输入！", 1000);
//			configNewPasswordField.requestFocus();
//			return;
//		}
//		if (configNewPasswordField.getPassword().length < 8) {
//			Defaults.messageDialog.showMessage("新口令不得少于8位，请重新输入！", 1000);
//			configNewPasswordField.requestFocus();
//			return;
//		}
//		if (confirmNewPasswordField.getPassword() == null) {
//			Defaults.messageDialog.showMessage("确认口令不得为空，请重新输入！", 1000);
//			confirmNewPasswordField.requestFocus();
//			return;
//		}
//		if (confirmNewPasswordField.getPassword().length < 8) {
//			Defaults.messageDialog.showMessage("确认口令不得少于8位，请重新输入！", 1000);
//			confirmNewPasswordField.requestFocus();
//			return;
//		}
//		String newPassword = String.valueOf(configNewPasswordField.getPassword());
//		String confirmPassword = String.valueOf(confirmNewPasswordField.getPassword());
//		if(!newPassword.equals(confirmPassword)){
//			Defaults.messageDialog.showMessage("新口令与确认口令不一致，请重新输入！", 1000);
//			confirmNewPasswordField.requestFocus();
//			return;
//		}
//		if(selectTag()){
//			MemoryBank bank = null;
//			CommandType type = null;
//			if(safeAreaButton.isSelected()){
//				bank = MemoryBank.GBReservedMemory;
//				if(configTypeBox.getSelectedIndex() == 0){
//					type = CommandType.Inactivate_Command_GB;
//				}else if(configTypeBox.getSelectedIndex() == 1) {
//					type = CommandType.Lock_Command_GB;
//				}
//			}else if(userDataAreaButton.isSelected()){
//				bank = DemoUtil.getUserBank(Integer.parseInt(configUserNoField.getText().trim()));
//				if(configTypeBox.getSelectedIndex() == 0){
//					type = CommandType.Read_Command_GB;
//				}else if(configTypeBox.getSelectedIndex() == 1){
//					type = CommandType.Write_Command_GB;
//				}
//			}
//			String oldPassword = String.valueOf(configOldPasswordField.getPassword());
//			GBConfigTagPassword message = new GBConfigTagPassword(antenna, bank, type, oldPassword, newPassword);
//			if(defaults.reader.send(message)){
//				Defaults.messageDialog.showMessage("配置标签访问口令成功！", 1000);
//			}else{
//				Defaults.messageDialog.showMessage("配置标签访问口令失败，请重试！", 1000);
//			}
//		}else{
//			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
//		}
	}

	protected void configTagMode() {
		if (configTagPasswordField.getPassword() == null) {
			Defaults.messageDialog.showMessage("密码不得为空，请重新输入！", 1000);
			configTagPasswordField.requestFocus();
			return;
		}
		if (configTagPasswordField.getPassword().length < 8) {
			Defaults.messageDialog.showMessage("密码不得少于8位，请重新输入！", 1000);
			configTagPasswordField.requestFocus();
			return;
		}
		if(configStoreAreaBox.getSelectedIndex() == 3){
			if(configLockUserNoField.getText() == null || "".equals(configLockUserNoField.getText())){
				Defaults.messageDialog.showMessage("用户子区编号不得为空，请重新输入！", 1000);
				configLockUserNoField.requestFocus();
				return;
			}
			if((Integer.parseInt(configLockUserNoField.getText().trim())) > 15){
				Defaults.messageDialog.showMessage("用户子区编号不得大于15，请重新输入！", 1000);
				configLockUserNoField.requestFocus();
				return;
			}
		}
		if(selectTag()){
			String password = String.valueOf(configTagPasswordField.getPassword());
			GBMemoryBank bank = null;
			if(configStoreAreaBox.getSelectedIndex() == 3){
				bank = DemoUtil.getUserBank(Integer.parseInt(configLockUserNoField.getText().trim()));
			}else if(configStoreAreaBox.getSelectedIndex() == 0){
				bank = GBMemoryBank.GBTidMemory;
			}else if(configStoreAreaBox.getSelectedIndex() == 1){
				bank = GBMemoryBank.GBEPCMemory;
			}else if(configStoreAreaBox.getSelectedIndex() == 2){
				bank = GBMemoryBank.GBReservedMemory;
			}
			GBConfigTagLockOrSafeMode message = null;
			if(lockRadioButton.isSelected()){
				LockAction action = null;
				int actionIndex = configActiveBox.getSelectedIndex();
				if(actionIndex == 0){
					action = LockAction.Read_Write_GB;
				}else if(actionIndex == 1){
					action = LockAction.Read_Only_GB;
				}else if(actionIndex == 2){
					action = LockAction.Write_Only_GB;
				}else if(actionIndex == 3){
					action = LockAction.No_Read_Write_GB;
				}
				message =  new GBConfigTagLockOrSafeMode(antenna, password, bank, action);
				if(defaults.reader.send(message)){
					Defaults.messageDialog.showMessage("配置标签存储区属性成功！", 1000);
				}else{
					Defaults.messageDialog.showMessage("配置标签存储区属性失败，请重试！", 1000);
				}
			}else if(modeRadioButton.isSelected()){
				int mode = configActiveBox.getSelectedIndex();
				message = new GBConfigTagLockOrSafeMode(antenna, password, bank, mode);
				if(defaults.reader.send(message)){
					Defaults.messageDialog.showMessage("配置标签安全模式成功！", 1000);
				}else{
					Defaults.messageDialog.showMessage("配置标签安全模式失败，请重试！", 1000);
				}
			}
		}else{
			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
		}
	}

	protected void inactivateTag() {
		if (configInactivatePasswordField.getPassword() == null) {
			Defaults.messageDialog.showMessage("密码不得为空，请重新输入！", 1000);
			configInactivatePasswordField.requestFocus();
			return;
		}
		if (configInactivatePasswordField.getPassword().length < 8) {
			Defaults.messageDialog.showMessage("密码不得少于8位，请重新输入！", 1000);
			configInactivatePasswordField.requestFocus();
			return;
		}
		
		if(selectTag()){
			String password = String.valueOf(configInactivatePasswordField.getPassword());
			GBInactivateTag message = new GBInactivateTag(antenna, password);
			if(defaults.reader.send(message)){
				Defaults.messageDialog.showMessage("灭活标签成功！", 1000);
			}else{
				Defaults.messageDialog.showMessage("灭活标签失败，请重试！", 1000);
			}
		}else{
			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
		}
	}

	protected void wipeTagData() {
		if(!validateParamData()){
			return;
		}
		if(selectTag()){
			String password = String.valueOf(writePasswordField.getPassword());
			GBMemoryBank bank = null;
			if(writeAreaBox.getSelectedIndex() == 0){
				bank = GBMemoryBank.GBEPCMemory;
			}else if(writeAreaBox.getSelectedIndex() == 1){
				bank = GBMemoryBank.GBReservedMemory;
			}else if(writeAreaBox.getSelectedIndex() == 2){
				bank = DemoUtil.getUserBank(Integer.parseInt(writeUserNoField.getText().trim()));
			}
			int userHeadAddress = Integer.parseInt(writePointerEditor.getTextField().getText().trim());
			int length = Integer.parseInt(writeLengthEditor.getTextField().getText().trim());
			GBEraseTag wipeTag = new GBEraseTag(antenna, password, bank, userHeadAddress, length);
			if(defaults.reader.send(wipeTag)){
				Defaults.messageDialog.showMessage("擦除数据成功！", 1000);
			}else{
				Defaults.messageDialog.showMessage("擦除数据失败，请重试！", 1000);
			}
		}else{
			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
		}
		writeAreaBox.setSelectedIndex(0);
		writePointerEditor.getTextField().setText("4");
		writeLengthEditor.getTextField().setText("8");
		writeDataArea.setText("");
	}

	public boolean validateParamData(){
		if (writePasswordField.getPassword() == null) {
			Defaults.messageDialog.showMessage("密码不得为空，请重新输入！", 1000);
			writePasswordField.requestFocus();
			return false;
		}
		if (writePasswordField.getPassword().length < 8) {
			Defaults.messageDialog.showMessage("密码不得少于8位，请重新输入！", 1000);
			writePasswordField.requestFocus();
			return false;
		}
		int index = writeAreaBox.getSelectedIndex();
		if(index == 2){
			if(writeUserNoField.getText() == null || "".equals(writeUserNoField.getText())){
				Defaults.messageDialog.showMessage("用户子区编号不得为空，请重新输入！", 1000);
				writeUserNoField.requestFocus();
				return false;
			}
			if((Integer.parseInt(writeUserNoField.getText().trim())) > 15){
				Defaults.messageDialog.showMessage("用户子区编号不得大于15，请重新输入！", 1000);
				writeUserNoField.requestFocus();
				return false;
			}
		}
		if((Integer.parseInt(writePointerEditor.getTextField().getText().trim())) % 2 != 0){
			Defaults.messageDialog.showMessage("长度必须为偶数，请重试！", 1000);
			writePointerEditor.getTextField().requestFocus();
			return false;
		}
		
		return true;
	}
	
	protected void writeTagData() {
		if(!validateParamData()){
			return;
		}
		if(null == writeDataArea.getText() || "".equals(writeDataArea.getText())){
			Defaults.messageDialog.showMessage("数据不得为空，请重试！", 1000);
			writeDataArea.requestFocus();
			return;

		}
		if(selectTag()){
			String password = String.valueOf(writePasswordField.getPassword());
			GBMemoryBank bank = null;
			if(writeAreaBox.getSelectedIndex() == 0){
				bank = GBMemoryBank.GBEPCMemory;
			}else if(writeAreaBox.getSelectedIndex() == 1){
				bank = GBMemoryBank.GBReservedMemory;
			}else if(writeAreaBox.getSelectedIndex() == 2){
				bank = DemoUtil.getUserBank(Integer.parseInt(writeUserNoField.getText().trim()));
			}
			int userHeadAddress = Integer.parseInt(writePointerEditor.getTextField().getText().trim());
			int length = Integer.parseInt(writeLengthEditor.getTextField().getText().trim());
			String dataStr = writeDataArea.getText().trim();
			byte[] data = Util.convertHexStringToByteArray(dataStr);
			GBWriteTag writeTag = new GBWriteTag(antenna, password, bank, userHeadAddress, data);
			if(defaults.reader.send(writeTag)){
				Defaults.messageDialog.showMessage("写数据成功！", 1000);
			}else{
				Defaults.messageDialog.showMessage("写数据失败，请重试！", 1000);
			}
		}else{
			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
		}
		
		writeAreaBox.setSelectedIndex(0);
		writePointerEditor.getTextField().setText("4");
		writeLengthEditor.getTextField().setText("8");
		writeDataArea.setText("");
	}

	protected void readTagUserData() {
		if (readPasswordText.getPassword() == null) {
			Defaults.messageDialog.showMessage("密码不得为空，请重新输入！", 1000);
			readPasswordText.requestFocus();
			return;
		}
		if (readPasswordText.getPassword().length < 8) {
			Defaults.messageDialog.showMessage("密码不得少于8位，请重新输入！", 1000);
			readPasswordText.requestFocus();
			return;
		}
		String password = new String(readPasswordText.getPassword());
		if(readUserNoField.getText() == null || "".equals(readUserNoField.getText())){
			Defaults.messageDialog.showMessage("用户子区编号不得为空，请重新输入！", 1000);
			readUserNoField.requestFocus();
			return;
		}
		int userNo = Integer.parseInt(readUserNoField.getText().trim());
		if(userNo > 15){
			Defaults.messageDialog.showMessage("用户子区编号不得大于15，请重新输入！", 1000);
			readUserNoField.requestFocus();
			return;
		}
		int length = Integer.parseInt(readLengthEditor.getTextField().getText().trim());
//		if(length % 2 != 0){
//			Defaults.messageDialog.showMessage("用户子区读取长度必须为偶数，请重试！", 1000);
//			readLengthEditor.getTextField().requestFocus();
//			return;
//		}
		int userHeadAddress = Integer.parseInt(readPointerEditor.getTextField().getText().trim());

		if(selectTag()){
			GBAccessReadTag readTag = new GBAccessReadTag((byte) this.antenna, password, DemoUtil.getUserBank(userNo), userHeadAddress, length);
			if(defaults.reader.send(readTag)){
				if(null != readTag.getReceivedMessage()){
					updateReadContentArea(Util.convertByteArrayToHexString(readTag.getReceivedMessage().getTagData()));
				}
			}else{
				Defaults.messageDialog.showMessage("读取失败，请重试！", 1000);
				return;
			}
		}else{
			Defaults.messageDialog.showMessage("选择标签失败，请重试！", 1000);
			return;
		}
	}

	private void updateReadContentArea(final String message) {
		if(SwingUtilities.isEventDispatchThread()){
			String oldContent = "";
			StringBuffer newContent = new StringBuffer();
			if(null != readContentArea.getText() && !"".equals(readContentArea.getText())){
				oldContent = readContentArea.getText();
				newContent.append(oldContent);
				newContent.append("\n");
				newContent.append(message);
			}else{
				newContent.append(message);
			}
			readContentArea.setText(newContent.toString());
		}else{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateReadContentArea(message);
				}
			});
		}
	}

	private boolean selectTag() {
		target = targetBox.getSelectedIndex();
		rule = ruleBox.getSelectedIndex();
		GBSelectTag selectTag = new GBSelectTag(bank, target, rule,  ptr, tagID);
		return defaults.reader.send(selectTag);
	}

	private int result = 0;
	@Override
	public void messageNotificationReceivedHandle(BaseReader reader, IMessageNotification msg) {
		if(isReading){
			if(msg instanceof GBDynamicWriteTag){
				result += 1;
				String show = "写入次数为：" + result;
				resultLabel.setText(show);
			}
		}
	}

	private void stopDynamicWrite() {
		if(SwingUtilities.isEventDispatchThread()){
			if(defaults.isConnected){
				isReading = false;
				dynamicWriteButton.setEnabled(true);
				stopButton.setEnabled(false);
				PowerOff_800 powerOff = new PowerOff_800();
				defaults.reader.send(powerOff);
				defaults.reader.send(powerOff);
				defaults.reader.send(powerOff);
			}
		}else{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					stopDynamicWrite();
				}
			});
		}
	}
	
}
