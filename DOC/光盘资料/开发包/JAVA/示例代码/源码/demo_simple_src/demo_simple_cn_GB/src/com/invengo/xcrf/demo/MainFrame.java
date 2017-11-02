package com.invengo.xcrf.demo;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.GBMemoryBank;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.GBAccessReadTag;
import invengo.javaapi.protocol.IRP1.GBAccessReadTag.GBReadMemoryBank;
import invengo.javaapi.protocol.IRP1.GBAccessReadTag.ReceivedInfo;
import invengo.javaapi.protocol.IRP1.GBInventoryTag;
import invengo.javaapi.protocol.IRP1.GBInventoryTag.InventoryReceivedInfo;
import invengo.javaapi.protocol.IRP1.Buzzer_500;
import invengo.javaapi.protocol.IRP1.PowerOff;
import invengo.javaapi.protocol.IRP1.PowerOff_800;
import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;

import zht.title.ZHTTitle;
import zht.title.ZHTTitleBorder;

import com.invengo.xcrf.view.TextDocument;
import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class MainFrame extends JFrame implements IMessageNotificationReceivedHandle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private Defaults defaults;// Defaults class
	private Reader reader;
	
	private JCheckBox epcCheckBox;
	private JComboBox targetBox;
	private JComboBox sessionBox;
	private JComboBox conditionBox;
	
	private JCheckBox tidCheckBox;
	private JTextField passwordField;
	private JComboBox bankBox;
	private JTextField addressField;
	private JTextField lenField;

	private JButton conButton;
	private JButton disconButton;
	private JCheckBox one;
	private JCheckBox two;
	private JCheckBox three;
	private JCheckBox four;
	private JXTable table;
	private TagMessageTableModel tableModel;
	private static final String[] COLUMN_NAME = {"TagData", "NUMBER"};
	private static final int[] COLUMN_WIDTH = {400, 30};
	private ArrayList<TagMessageEntity> data = new ArrayList<TagMessageEntity>();
	
	private byte antenna;
	private int flag;

	/**
	 * Create the frame.
	 */
	/**
	 * @param def
	 * @param reader
	 */
	public MainFrame(Defaults def, Reader reader) {
		this.reader = reader;
		this.defaults = def;
		NimRODTheme nt = new NimRODTheme("NimRODThemeFile.theme");
		NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
		try {
			UIManager.setLookAndFeel(NimRODLF);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 660, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(new ZHTTitleBorder());
		setLocationRelativeTo(null);

		ZHTTitle title = new ZHTTitle(this, "XC-RF807固定式读写器演示软件(GB)", reader);
		contentPane.add(title, BorderLayout.NORTH);

		JPanel operationPanel = new JPanel();
		operationPanel.setBounds(0, 0, 660, 500);
		operationPanel.setLayout(null);
		contentPane.add(operationPanel);

		this.defaults.setPanel(operationPanel);
		
		JPanel ivPanel = new JPanel();
		ivPanel.setBounds(5, 5, 640, 100);
		ivPanel.setLayout(null);
		ivPanel.setBorder(BorderFactory.createTitledBorder("扫描设置"));
		operationPanel.add(ivPanel);
		
		epcCheckBox = new JCheckBox("盘存");
		epcCheckBox.setSelected(false);
		epcCheckBox.setBounds(5, 20, 100, 30);
		ivPanel.add(epcCheckBox);
		epcCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(epcCheckBox.isSelected()){
					targetBox.setEnabled(true);
					sessionBox.setEnabled(true);
					conditionBox.setEnabled(true);
					
					tidCheckBox.setSelected(false);
					passwordField.setEnabled(false);
					bankBox.setEnabled(false);
					addressField.setEnabled(false);
					lenField.setEnabled(false);
				}
			}
		});
		
		JLabel targetLabel = new JLabel("目标");
		targetLabel.setBounds(110, 20, 30, 30);
		ivPanel.add(targetLabel);
		
		String[] targetItem = {"目标=0", "目标=1", "目标=0&1"};
		targetBox = new JComboBox(targetItem);
		targetBox.setBounds(140, 20, 80, 30);
		targetBox.setEnabled(false);
		ivPanel.add(targetBox);
		
		JLabel sessionLabel = new JLabel("会话");
		sessionLabel.setBounds(225, 20, 30, 30);
		ivPanel.add(sessionLabel);
		
		String[] sessionItem = {"S0", "S1", "S2", "S3"};
		sessionBox = new JComboBox(sessionItem);
		sessionBox.setBounds(255, 20, 80, 30);
		sessionBox.setEnabled(false);
		ivPanel.add(sessionBox);
		
		JLabel conditionLabel = new JLabel("条件");
		conditionLabel.setBounds(340, 20, 30, 30);
		ivPanel.add(conditionLabel);
		
		String[] conditionItem = {"所有标签", "匹配标志是1b的标签", "匹配标志是0b的标签"};
		conditionBox = new JComboBox(conditionItem);
		conditionBox.setBounds(370, 20, 150, 30);
		conditionBox.setEnabled(false);
		ivPanel.add(conditionBox);

		tidCheckBox = new JCheckBox("访问读");
		tidCheckBox.setSelected(true);
		tidCheckBox.setBounds(5, 60, 100, 30);
		ivPanel.add(tidCheckBox);
		tidCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tidCheckBox.isSelected()){
					epcCheckBox.setSelected(false);
					targetBox.setEnabled(false);
					sessionBox.setEnabled(false);
					conditionBox.setEnabled(false);
					
					passwordField.setEnabled(true);
					bankBox.setEnabled(true);
					addressField.setEnabled(true);
					lenField.setEnabled(true);
				}
			}
		});
		
		JLabel passwordLabel = new JLabel("访问密码");
		passwordLabel.setBounds(110, 60, 50, 30);
		ivPanel.add(passwordLabel);
		
		passwordField = new JTextField();
		passwordField.setBounds(160, 60, 80, 30);
		passwordField.setDocument(new TextDocument(8, 0));
		passwordField.setText("00000000");
		ivPanel.add(passwordField);
		
		JLabel bankLabel = new JLabel("访问区域");
		bankLabel.setBounds(245, 60, 50, 30);
		ivPanel.add(bankLabel);
		
		String[] bankItem = {"标签信息区", "编码区", "用户子区1", "用户子区2", "用户子区3", "用户子区4", "用户子区5"};
		bankBox = new JComboBox(bankItem);
		bankBox.setBounds(295, 60, 100, 30);
		ivPanel.add(bankBox);
		
		JLabel addressLabel = new JLabel("首地址");
		addressLabel.setBounds(400, 60, 40, 30);
		ivPanel.add(addressLabel);
		
		addressField = new JTextField();
		addressField.setBounds(440, 60, 80, 30);
		addressField.setDocument(new TextDocument(3, 1));
		addressField.setText("0");
		ivPanel.add(addressField);
		
		JLabel lenLabel = new JLabel("长度");
		lenLabel.setBounds(525, 60, 30, 30);
		ivPanel.add(lenLabel);

		lenField = new JTextField();
		lenField.setBounds(555, 60, 80, 30);
		lenField.setDocument(new TextDocument(3, 1));
		lenField.setText("8");
		ivPanel.add(lenField);
		
		/*
		 * read tag password
		 */
		JPanel scanPanel = new JPanel();
		scanPanel.setBounds(5, 110, 640, 40);
		scanPanel.setLayout(null);
		scanPanel.setBorder(BorderFactory.createTitledBorder(""));
		operationPanel.add(scanPanel);

		JPanel checkBoxPane = new JPanel(new GridLayout(1, 5));
		checkBoxPane.setBounds(5, 5, 200, 30);
		JLabel antennaLabel = new JLabel("天线:");
		one = new JCheckBox("1");
		one.setSelected(true);
		two = new JCheckBox("2");
		three = new JCheckBox("3");
		four = new JCheckBox("4");
		checkBoxPane.add(antennaLabel);
		checkBoxPane.add(one);
		checkBoxPane.add(two);
		checkBoxPane.add(three);
		checkBoxPane.add(four);
		scanPanel.add(checkBoxPane);
		
		final JButton typeButton = new JButton("扫描");
		typeButton.setBounds(360, 5, 80, 30);
		scanPanel.add(typeButton);
		
		final JButton powerOffButton = new JButton("停止");
		powerOffButton.setBounds(445, 5, 80, 30);
		scanPanel.add(powerOffButton);

		typeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(MainFrame.this.defaults.isConnected){
					typeButton.setEnabled(false);
					powerOffButton.setEnabled(true);
					isReading = true;
					data.clear();
					tableModel.updateMessages(data);
					boolean oneSelected = one.isSelected();
					boolean twoSelected = two.isSelected();
					boolean threeSelected = three.isSelected();
					boolean fourSelected = four.isSelected();
					if(!oneSelected && !twoSelected && !threeSelected && !fourSelected){
						showMessage("请选择天线端口!");
						return;
					}
					
					String fourAntenna = fourSelected ? "1" : "0";
					String threeAntenna = threeSelected ? "1" : "0";
					String twoAntenna = twoSelected ? "1" : "0";
					String oneAntenna = oneSelected ? "1" : "0";
					
					StringBuffer antennaSB = new StringBuffer();
					antennaSB.append("1000");
					antennaSB.append(fourAntenna);
					antennaSB.append(threeAntenna);
					antennaSB.append(twoAntenna);
					antennaSB.append(oneAntenna);
					
					antenna = Util.convertHexStringToByteArray(Integer
							.toHexString(Integer.parseInt(antennaSB.toString(),2)))[0];
					
					if(epcCheckBox.isSelected()){
						flag = 0;
						int target = targetBox.getSelectedIndex();
						int session = sessionBox.getSelectedIndex();
						int condition = conditionBox.getSelectedIndex();
						
						GBInventoryTag msg = new GBInventoryTag(antenna, target, session, condition);
						MainFrame.this.reader.send(msg);
					}else if(tidCheckBox.isSelected()){
						String password = passwordField.getText().trim();
						if(null == password || "".equals(password)){
							showMessage("请输入标签访问密码!");
							return;
						}
						GBMemoryBank bank = null;
						GBReadMemoryBank rmb = null;
						int index = bankBox.getSelectedIndex();
						if(index == 0){
							bank = GBMemoryBank.GBTidMemory;
							rmb = GBReadMemoryBank.TID_GB_Access;
							flag = 1;
						}else if(index == 1){
							bank = GBMemoryBank.GBEPCMemory;
							rmb = GBReadMemoryBank.EPC_GB_Access;
							flag = 0;
						}else if(index == 2){
							bank = GBMemoryBank.GBUser1Memory;
							rmb = GBReadMemoryBank.Sub_UserData_GB_Access;
							flag = 2;
						}else if(index == 3){
							bank = GBMemoryBank.GBUser2Memory;
							rmb = GBReadMemoryBank.Sub_UserData_GB_Access;
							flag = 2;
						}else if(index == 4){
							bank = GBMemoryBank.GBUser3Memory;
							rmb = GBReadMemoryBank.Sub_UserData_GB_Access;
							flag = 2;
						}else if(index == 5){
							bank = GBMemoryBank.GBUser4Memory;
							rmb = GBReadMemoryBank.Sub_UserData_GB_Access;
							flag = 2;
						}else if(index == 6){
							bank = GBMemoryBank.GBUser5Memory;
							rmb = GBReadMemoryBank.Sub_UserData_GB_Access;
							flag = 2;
						}
						
						String address = addressField.getText().trim();
						if(null == address || "".equals(address)){
							showMessage("请输入首地址!");
							return;
						}
						String len = lenField.getText().trim();
						if(null == len || "".equals(len)){
							showMessage("请输入长度!");
							return;
						}
						
						GBAccessReadTag msg = new GBAccessReadTag(rmb, bank);
						msg.setAntenna(antenna);
						msg.setDefaultAccessPassword(password);
						msg.setHeadAddress(Integer.parseInt(address));
						msg.setLength(Integer.parseInt(len));
						MainFrame.this.reader.send(msg);
					}
				}else {
					showMessage("读写器未连接!");
				}
			}
		});
		
		powerOffButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(MainFrame.this.defaults.isConnected){
					typeButton.setEnabled(true);
					powerOffButton.setEnabled(false);
					isReading = false;
					PowerOff_800 powerOff = new PowerOff_800();
					MainFrame.this.reader.send(powerOff);
				}else{
					showMessage("读写器未连接!");
				}
			}
		});
		
		JButton clearButton = new JButton("清除");
		clearButton.setBounds(530, 5, 80, 30);
		scanPanel.add(clearButton);
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				data.clear();
				tableModel.updateMessages(data);
			}
		});
		/*
		 * read tag password
		 */
		
		JScrollPane showPane = new JScrollPane();
		showPane.setBounds(5, 155, 640, 270);
		operationPanel.add(showPane);
		
		tableModel = new TagMessageTableModel(COLUMN_NAME);
		DefaultTableColumnModelExt model = new DefaultTableColumnModelExt();
		for(int i = 0; i < COLUMN_NAME.length; i++){
			TableColumnExt tableColumn = new TableColumnExt(i, COLUMN_WIDTH[i]);
			tableColumn.setTitle(COLUMN_NAME[i]);
			tableColumn.setResizable(false);
			tableColumn.setSortable(false);
			model.addColumn(tableColumn);
		}		
		table = new JXTable(tableModel);
		table.setColumnModel(model);
		showPane.getViewport().add(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int clickCount = e.getClickCount();
				if(clickCount >= 2){
					TagMessageEntity entity = tableModel.getSelectedEntity(table.getSelectedRow());
					String id = entity.getTagData();
					TagDialogGB dialog = new TagDialogGB(antenna, id, flag, defaults);
					dialog.setLocationRelativeTo(defaults.workPanel);
					dialog.setVisible(true);
				}
			}
		});
		
		conButton = new JButton("连接");
		conButton.setEnabled(false);
		conButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!MainFrame.this.reader.connect()) {
					showMessage("连接失败!");
					conButton.setEnabled(true);
					disconButton.setEnabled(false);
					MainFrame.this.defaults.isConnected = false;
				} else {
					conButton.setEnabled(false);
					disconButton.setEnabled(true);
					MainFrame.this.defaults.isConnected = true;
				}
			}
		});
		conButton.setBounds(425, 430, 95, 25);
		operationPanel.add(conButton);

		disconButton = new JButton("断开连接");
		disconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (defaults.conType == 0) {
					int type = JOptionPane.showConfirmDialog(
							defaults.workPanel, "是否需要关闭网络连接保护？", "信息",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (type == 0) {
//						MainFrame.this.reader.send(new Restart());
					}
				}else {
					MainFrame.this.reader.send(new PowerOff());
					MainFrame.this.reader.disConnect();
				}
				disconButton.setEnabled(false);
				conButton.setEnabled(true);
				MainFrame.this.defaults.isConnected = false;
			}
		});
		disconButton.setBounds(525, 430, 95, 25);
		operationPanel.add(disconButton);
		this.defaults.setFont();

		this.reader.onMessageNotificationReceived.add(this);
		setVisible(true);
	}
	
	private class TagMessageTableModel extends AbstractTableModel{

		private static final long serialVersionUID = 2080392445214938506L;
		private ArrayList<TagMessageEntity> messages = null;
		private String[] columnName = null;
		
		public TagMessageTableModel(String[] columnName){
			this.columnName = columnName;
			messages = new ArrayList<TagMessageEntity>();
		}
		
		@Override
		public String getColumnName(int column) {
			if(null == columnName || columnName.length == 0){
				throw new NullPointerException("Column is empty!");
			}
			return columnName[column];
		}
		
		@Override
		public int getRowCount() {
			if(null == messages){
				throw new NullPointerException("Data is empty!");
			}
			
			return messages.size();
		}

		@Override
		public int getColumnCount() {
			return this.columnName.length;
		}
		
		@Override
	    public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			default:
				return Object.class;
			}
	    }

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
	    	TagMessageEntity entity = messages.get(rowIndex);
	    	Object value = null;
	    	switch (columnIndex) {
				case 0:
					value = entity.getTagData();
					break;
				case 1:
					value = entity.getNumber();
					break;
			}
			return value;
		}
		
		public ArrayList<TagMessageEntity> getAllMessages(){
			return messages;
		}
		
		public void updateMessages(ArrayList<TagMessageEntity> data){
			if(null == messages){
				throw new NullPointerException("Data is empty!");
			}
			this.messages = data;
			fireTableDataChanged();
		}
		
		public TagMessageEntity getSelectedEntity(int row){
			return this.messages.get(row);
		}
	}
	
	private class TagMessageEntity{
		private String tagData;
		private int number;
		
		public String getTagData() {
			return tagData;
		}
		public void setTagData(String tagData) {
			this.tagData = tagData;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		
	}
	
	private void showMessage(String msg){
		this.defaults.messageDialog.showMessage(msg, 2000);
	}
	
	private void openBuzzer(){
		Buzzer_500 msg = new Buzzer_500((byte) 0x00);
		this.defaults.reader.send(msg);
	}
	
	private void closeBuzzer(){
		Buzzer_500 msg = new Buzzer_500((byte) 0x01);
		this.defaults.reader.send(msg);
	}

	private boolean isReading = false;
	private Object lock = new Object();
	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,IMessageNotification msg) {
		if(isReading){
			synchronized (lock) {
				String tagData = "";
				data = tableModel.getAllMessages();
				if(msg instanceof GBInventoryTag){
					GBInventoryTag message = (GBInventoryTag) msg;
					InventoryReceivedInfo info =  message.getReceivedMessage();
					if(info != null){
						tagData = Util.convertByteArrayToHexString(info.getTagData());
					}
				}if(msg instanceof GBAccessReadTag){
					GBAccessReadTag message = (GBAccessReadTag) msg;
					ReceivedInfo info =  message.getReceivedMessage();
					if(info != null){
						tagData = Util.convertByteArrayToHexString(info.getTagData());
					}
				}
//				String response = "标签数据:"+ tagData;
//				System.out.println(response);
				
				if(data.size() == 0){
					TagMessageEntity entity = new TagMessageEntity();
					entity.setTagData(tagData);
					entity.setNumber(1);
					data.add(entity);
				}else{
					boolean isExists = false;
					for(TagMessageEntity entity : data){
						String temp = entity.getTagData();
						if(tagData.equals(temp)){
							int number = entity.getNumber() + 1;
							entity.setNumber(number);
							isExists = true;
							break;
						}
					}
					if(!isExists){
						TagMessageEntity entity = new TagMessageEntity();
						entity.setTagData(tagData);
						entity.setNumber(1);
						data.add(entity);
					}
				}
				tableModel.updateMessages(data);
			}
		}
	}
}
