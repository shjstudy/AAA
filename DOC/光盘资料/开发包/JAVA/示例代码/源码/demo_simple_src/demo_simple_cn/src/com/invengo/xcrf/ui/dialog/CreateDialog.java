package com.invengo.xcrf.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.EventListenerList;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jdom.Element;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.Const;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.util.DemoUtil;
import com.invengo.xcrf.core.util.IPField;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.tree.RootTree;

public class CreateDialog extends JDialog {

	private static final long serialVersionUID = 2511140248493969340L;
	private final JPanel contentPanel = new JPanel();
	private final CreateDialog dialog;

	private Demo demo = null;

	private boolean update = false;

	List<String> groupLst = new ArrayList<String>();

	private JTextField readNameField;
	private JComboBox groupNameComBox;
	private JCheckBox enableCheckBox;

	private JTabbedPane tabbedPane;

	private JRadioButton rdbtnTcpip;
	private JRadioButton rdbtnRs;
	private final JRadioButton rdbtnUsb;
	private JRadioButton rdbtnTcpipServer;
	private JLabel lblPort_1;

	private IPField ipField;
	private JSpinner clientPortSpinner;
	private JSpinner.NumberEditor clientPortEditor;
	private JSpinner serverPortspinner;
	private JSpinner.NumberEditor serverPortEditor;

	private JComboBox commBox;
	private JComboBox commbtBox;

	private JComboBox readerTypeComboBox;
	private JComboBox msgTypeComboBox;
	private JComboBox antennaComboBox;

	private JRadioButton loopButton;
	private JRadioButton singleButton;

	private JSpinner numberTagSpinner;
	private JSpinner numReadTimeSpinner;
	private JSpinner numStopTimeSpinner;

	private JSpinner.NumberEditor numberTagEditor;
	private JSpinner.NumberEditor numReadTimeEditor;
	private JSpinner.NumberEditor numStopTimeEditor;

	private JComboBox stopTypeComboBox;

	public static CreateDialog createDialog;
	private JPanel panel_5;
	private JPanel panel_2;
	private JPanel panel_4;
	private JPanel panel_3;
	private JPanel panel_1;
	private JPanel panel_9;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JComboBox modelTypeComboBox;
	private JLabel modelType;
	JLabel label_8;
	JLabel label_9;
	JCheckBox checkBox_4;

	public static CreateDialog getInstance() {
		// if (createDialog == null)
		return new CreateDialog();
		// else
		// return createDialog;
	}

	/*
	 * ModelTypeComBox 选择型号时的控件控制
	 * 
	 */
	 public  void ModelTypeComBoxControll(){
			try {
				if (modelTypeComboBox.getSelectedItem() == null
						|| modelTypeComboBox.getSelectedItem().toString()
								.equals("")) {
					return;
				}

				Element modeE = Common.GetModelInfoNode(modelTypeComboBox
						.getSelectedItem().toString());
				String connections = modeE.getChildText("Connection");
				if (connections.indexOf("TCP/IP Client") != -1)
					panel_5.setVisible(true);
				else
				{
					panel_5.setVisible(false);
					if(rdbtnTcpip.isSelected())
					{					
						intConnectTypeSelectPanel();
					}
				}
				if (connections.indexOf("RS232") != -1)
					panel_4.setVisible(true);
				else
				{	
					panel_4.setVisible(false);
					if(rdbtnRs.isSelected()){
						intConnectTypeSelectPanel();
					}
				}
				if (connections.indexOf("USB") != -1)
					panel_3.setVisible(true);
				else{
					panel_3.setVisible(false);
					if(rdbtnUsb.isSelected())
					{	
						intConnectTypeSelectPanel();
					}
				}		

				String protocols = modeE.getChildText("Protocol");

				tabbedPane.removeAll();
				if (protocols.indexOf("IRP1.0") != -1) {
					tabbedPane.addTab("IRP1", panel_1);
					Element IRP1E = modeE.getChild("IRP1.0");
					// 读写器类型
					readerTypeComboBox.setSelectedItem(IRP1E
							.getChildText("ReaderType"));
					readerTypeComboBox.setEnabled(false);
					// 读卡方式
					String[] rt = IRP1E.getChildText("Scan").split(",");
					if (rt != null && rt.length > 0) {
						msgTypeComboBox.removeAllItems();
						for (int i = 0; i < rt.length; i++)
							msgTypeComboBox.addItem(rt[i]);

						if (msgTypeComboBox.getItemCount() > 0)
							msgTypeComboBox.setSelectedIndex(0);

					}
					// 天线
					String antennaCtrl = IRP1E.getChildText("AntennaCtrl");
					panel_9.setVisible(antennaCtrl.equals("p_Antenna"));
					antennaComboBox.setVisible(!panel_9.isVisible());

					int na = Integer.parseInt(IRP1E
							.getChildText("AntennaNum"));
					antennaComboBox.removeAllItems();
					checkBox.setSelected(false);
					checkBox_1.setSelected(false);
					checkBox_2.setSelected(false);
					checkBox_3.setSelected(false);
					checkBox.setVisible(false);
					checkBox_1.setVisible(false);
					checkBox_2.setVisible(false);
					checkBox_3.setVisible(false);

					if (na == 1) {
						antennaComboBox.addItem("1#");
						checkBox.setVisible(true);
					}
					if (na == 2) {
						antennaComboBox.addItem("1#");
						antennaComboBox.addItem("2#");
						antennaComboBox.addItem("1-2#");
						checkBox.setVisible(true);
						checkBox_1.setVisible(true);

					}
					if (na == 3) {
						checkBox.setVisible(true);
						checkBox_1.setVisible(true);
						checkBox_2.setVisible(true);
					}
					if (na == 4) {
						antennaComboBox.addItem("1#");
						antennaComboBox.addItem("2#");
						antennaComboBox.addItem("3#");
						antennaComboBox.addItem("4#");
						antennaComboBox.addItem("1-2#");
						antennaComboBox.addItem("1-3#");
						antennaComboBox.addItem("1-4#");
						checkBox.setVisible(true);
						checkBox_1.setVisible(true);
						checkBox_2.setVisible(true);
						checkBox_3.setVisible(true);
					}
					if (antennaComboBox.getItemCount() > 0)
						antennaComboBox.setSelectedIndex(0);

					if (checkBox.isVisible()) {
						checkBox.setSelected(true);
					}

					// Q值
					boolean qEnable = Boolean.parseBoolean(IRP1E.getChild(
							"Q").getAttributeValue("Enabled"));
					numberTagSpinner.setEnabled(qEnable);
					int qVal = Integer.parseInt(IRP1E.getChildText("Q"));
					numberTagEditor.getTextField().setText(
							String.valueOf((int) Math.pow(2, qVal) - 1));
					// 关功放类型
					String stopType = IRP1E.getChildText("StopType");
					stopTypeComboBox.setSelectedItem(stopType);
					stopTypeComboBox.setEnabled(false);

				}
//				if (protocols.indexOf("IRP2.0") != -1) {
//					tabbedPane.addTab("IRP2", panel_6);
//				}
//				if (protocols.indexOf("LLRP1.01") != -1) {
//					tabbedPane.addTab("LLRP1_01", panel_7);
//				}
//				if (protocols.indexOf("LLRP1.1") != -1) {
//					tabbedPane.addTab("LLRP1_1", panel_8);
//				}

			} catch (Exception e1) {
				// do nothing
				e1.printStackTrace();
			}
		}
	 
	private void intConnectTypeSelectPanel(){
		if(panel_5.isVisible()){
			rdbtnTcpip.setSelected(true);
			return;
		}else if(panel_4.isVisible()){
			rdbtnRs.setSelected(true);
			return;
		}else if(panel_3.isVisible()){
			rdbtnUsb.setSelected(true);
			return;
		}else if(panel_2.isVisible()){
			rdbtnTcpipServer.setSelected(true);
			return;
		}
	}
	/**
	 * Create the dialog.
	 */
	public CreateDialog() {
		dialog = this;
		setTitle(BaseMessages.getString("ReaderConfigForm.ReaderConfigForm"));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 830, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel(BaseMessages
					.getString("SysitConfigForm.label1"));
			label.setBounds(25, 10, 76, 15);
			contentPanel.add(label);
		}

		readNameField = new JTextField();
		readNameField.setBounds(103, 7, 164, 21);
		readNameField.setColumns(10);
		readNameField.setDocument(new LimitDocument(24));
		readNameField.setEditable(true);
		contentPanel.add(readNameField);
		getAutoReadName();
		

		JLabel label = new JLabel(BaseMessages
				.getString("SysitConfigForm.label15"));
		label.setBounds(534, 10, 139, 15);
		contentPanel.add(label);

		initGroupCombox();
		groupNameComBox.setBounds(664, 7, 139, 21);
		groupNameComBox.setEditable(true);
		contentPanel.add(groupNameComBox);
		

		enableCheckBox = new JCheckBox(BaseMessages
				.getString("SysitConfigForm.cEnable"));
		enableCheckBox.setBounds(693, 6, 68, 23);
		contentPanel.add(enableCheckBox);
		enableCheckBox.setVisible(false);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("SysitConfigForm.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(25, 45, 242, 411);
		contentPanel.add(panel);
		panel.setLayout(null);

		ButtonGroup buttonGroup = new ButtonGroup();

		// 端口设定
		String[] comm = DemoUtil.getCommPorts();

		String[] commbt = { "9600", "19200", "115200" };

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, BaseMessages
				.getString("SysitConfigForm.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		tabbedPane.setBounds(290, 48, 513, 408);
		contentPanel.add(tabbedPane);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("IRP1", panel_1);
//		tabbedPane.addTab("IRP2", panel_6);
//		tabbedPane.addTab("LLRP1_01", panel_7);
//		tabbedPane.addTab("LLRP1_1", panel_8);

		panel_1.setLayout(null);

		JLabel label_1 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label6"));
		label_1.setBounds(10, 10, 82, 15);
		//panel_1.add(label_1);

		JLabel label_2 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label13"));
		label_2.setBounds(10, 46, 104, 15);
		panel_1.add(label_2);

		JLabel label_5 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label12"));
		label_5.setBounds(10, 93, 68, 15);
		panel_1.add(label_5);

		JLabel label_6 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label8"));
		label_6.setBounds(10, 126, 68, 15);
		panel_1.add(label_6);

		JLabel label_7 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label7"));
		label_7.setBounds(10, 160, 104, 15);
		panel_1.add(label_7);

		label_8 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label10"));
		label_8.setBounds(110, 236, 124, 15);
		panel_1.add(label_8);

		label_9 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label11"));
		label_9.setBounds(110, 265, 124, 21);
		panel_1.add(label_9);

		JLabel label_10 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label9"));
		label_10.setBounds(10, 256, 124, 15);
		//panel_1.add(label_10);

		String[] readerType = { "500", "800" };
		readerTypeComboBox = new JComboBox(readerType);
		readerTypeComboBox.setSelectedIndex(1);
		// readerTypeComboBox.setBackground(Color.WHITE);
		readerTypeComboBox.setBounds(102, 7, 105, 21);
		//panel_1.add(readerTypeComboBox);

		// 初始化读卡方式
		// String[] msgTypes = {
		// "EPC_6C",
		// "TID_6C",
		// "EPC_TID_UserData_6C",
		// "EPC_TID_UserData_6C_2",
		// "ID_6B",
		// "ID_UserData_6B",
		// "EPC_6C_ID_6B",
		// "TID_6C_ID_6B",
		// "EPC_TID_UserData_6C_ID_UserData_6B",
		// "EPC_PC_6C"};
		msgTypeComboBox = new JComboBox(Const.MSG_TYPES);
		// msgTypeComboBox.setBackground(Color.WHITE);
		msgTypeComboBox.setBounds(125, 43, 320, 21);
		panel_1.add(msgTypeComboBox);

		String[] antennaTypes = { "1#", "2#", "3#", "4#", "1-2#", "1-3#",
				"1-4#" };
		antennaComboBox = new JComboBox(antennaTypes);
		// antennaComboBox.setBackground(Color.WHITE);
		antennaComboBox.setBounds(125, 90, 68, 21);
		panel_1.add(antennaComboBox);

		ButtonGroup readLoopGroup = new ButtonGroup();
		loopButton = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbLoop"));
		loopButton.setSelected(true);
		loopButton.setBounds(125, 122, 92, 23);
		readLoopGroup.add(loopButton);
		panel_1.add(loopButton);

		singleButton = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbSingle"));
		singleButton.setBounds(264, 122, 73, 23);
		readLoopGroup.add(singleButton);
		panel_1.add(singleButton);

		numberTagSpinner = new JSpinner();
		numberTagSpinner.setModel(new SpinnerNumberModel(new Integer(8), null,
				null, new Integer(1)));
		numberTagEditor = new JSpinner.NumberEditor(numberTagSpinner, "#####");
		initNumTagEditor(numberTagEditor, "8");
		numberTagSpinner.setEditor(numberTagEditor);
		numberTagSpinner.setBounds(125, 157, 84, 22);
		panel_1.add(numberTagSpinner);

		numReadTimeSpinner = new JSpinner();
		numReadTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0),
				null, null, new Integer(1)));
		numReadTimeEditor = new JSpinner.NumberEditor(numReadTimeSpinner,
				"#####");
		initNumTagEditor(numReadTimeEditor, "0");
		numReadTimeSpinner.setEditor(numReadTimeEditor);
		numReadTimeSpinner.setBounds(244, 233, 104, 22);
		panel_1.add(numReadTimeSpinner);

		numStopTimeSpinner = new JSpinner();
		numStopTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0),
				null, null, new Integer(1)));
		numStopTimeEditor = new JSpinner.NumberEditor(numStopTimeSpinner,
				"#####");
		initNumTagEditor(numStopTimeEditor, "0");
		numStopTimeSpinner.setEditor(numStopTimeEditor);
		numStopTimeSpinner.setBounds(244, 264, 104, 22);
		panel_1.add(numStopTimeSpinner);

		stopTypeComboBox = new JComboBox(Const.STOP_TYPES);
		stopTypeComboBox.setSelectedIndex(1);
		// stopTypeComboBox.setBackground(Color.WHITE);
		stopTypeComboBox.setBounds(144, 253, 104, 21);
		//panel_1.add(stopTypeComboBox);

		panel_9 = new JPanel();
		panel_9.setVisible(false);
		panel_9.setBounds(125, 78, 209, 33);
		panel_1.add(panel_9);
		panel_9.setLayout(null);

		checkBox = new JCheckBox("1");
		checkBox.setBounds(6, 6, 42, 23);
		panel_9.add(checkBox);

		checkBox_1 = new JCheckBox("2");
		checkBox_1.setBounds(56, 6, 42, 23);
		panel_9.add(checkBox_1);

		checkBox_2 = new JCheckBox("3");
		checkBox_2.setBounds(100, 6, 42, 23);
		panel_9.add(checkBox_2);

		checkBox_3 = new JCheckBox("4");
		checkBox_3.setBounds(144, 6, 48, 23);
		panel_9.add(checkBox_3);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(null);
		buttonPane.setPreferredSize(new Dimension(700,50));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(BaseMessages
				.getString("SysitConfigForm.btnOK"));
		okButton.setBounds(599, 9, 100, 24);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton(BaseMessages
				.getString("SysitConfigFormEdit.button2"));
		cancelButton.setBounds(701, 9, 100, 24);
		buttonPane.add(cancelButton);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(MainFrame.class
				.getResource("image/logo.gif"));
		setIconImage(image);
		setLocationRelativeTo(null);

//		panel_2 = new JPanel();
//		panel_2.setBounds(10, 330, 222, 71);
//		panel.add(panel_2);
//		panel_2.setLayout(null);

		JPanel p = new JPanel();
		p.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages.getString("SysitConfigFormEdit.checkBox2"), TitledBorder.TOP,
				TitledBorder.TOP, null, null));
		
		//p.setOpaque(false);
		p.setBounds(10, 201, 435, 99);
		p.setEnabled(false);
		p.setLayout(null);
		panel_1.add(p);
		
		checkBox_4 = new JCheckBox("");
		checkBox_4.setBounds(128, -3, 21, 23);
		//checkBox_4.setOpaque(true);
		p.add(checkBox_4);
		checkBox_4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(checkBox_4.isSelected()){
					label_8.setEnabled(true);
					numReadTimeSpinner.setEnabled(true);
					label_9.setEnabled(true);
					numStopTimeSpinner.setEnabled(true);
				}else{
					label_8.setEnabled(false);
					numReadTimeSpinner.setValue(0);
					numReadTimeSpinner.setEnabled(false);
					numStopTimeSpinner.setValue(0);
					label_9.setEnabled(false);
					numStopTimeSpinner.setEnabled(false);
				}
			}
			
		});
		// 为组件增加监听
		// 连接方式切换监听-- TCP-IP 、 COM 、Server
		ActionListener connTypeRidioLsn = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configEnable();
			}

		};
		
		serverPortspinner = new JSpinner();
		serverPortspinner.setBounds(107, 28, 105, 22);
		//panel_2.add(serverPortspinner);
		serverPortspinner.setModel(new SpinnerNumberModel(new Integer(12800),
				null, null, new Integer(1)));
		serverPortEditor = new JSpinner.NumberEditor(serverPortspinner, "#####");
		initPortEditor(serverPortEditor, "12800");
		serverPortspinner.setEditor(serverPortEditor);
		serverPortspinner.setEnabled(false);

		lblPort_1 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label14"));
		lblPort_1.setBounds(10, 34, 61, 15);
		//panel_2.add(lblPort_1);

		rdbtnTcpipServer = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbTcpServer"));
		rdbtnTcpipServer.setBounds(6, 6, 144, 23);
		//panel_2.add(rdbtnTcpipServer);
		buttonGroup.add(rdbtnTcpipServer);
		rdbtnTcpipServer.addActionListener(connTypeRidioLsn);

		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(10, 261, 222, 63);
		panel.add(panel_3);

		rdbtnUsb = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbUsb"));
		rdbtnUsb.setBounds(6, 18, 121, 23);
		panel_3.add(rdbtnUsb);
		buttonGroup.add(rdbtnUsb);
		rdbtnUsb.addActionListener(connTypeRidioLsn);

		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(10, 151, 222, 100);
		panel.add(panel_4);

		JLabel label_4 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label5"));
		label_4.setBounds(10, 75, 61, 15);
		panel_4.add(label_4);

		JLabel label_3 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label4"));
		label_3.setBounds(10, 47, 61, 15);
		panel_4.add(label_3);
		commbtBox = new JComboBox(commbt);
		commbtBox.setBounds(106, 69, 106, 21);
		panel_4.add(commbtBox);
		commbtBox.setSelectedIndex(2);
		commbtBox.setEnabled(false);
		commBox = new JComboBox(comm);
		commBox.setBounds(106, 38, 106, 21);
		panel_4.add(commBox);

		// disable
		commBox.setEnabled(false);

		rdbtnRs = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbRs232"));
		rdbtnRs.setBounds(6, 6, 121, 23);
		panel_4.add(rdbtnRs);
		buttonGroup.add(rdbtnRs);

		panel_5 = new JPanel();
		panel_5.setBounds(10, 39, 222, 100);
		panel.add(panel_5);
		panel_5.setLayout(null);

		clientPortSpinner = new JSpinner();
		clientPortSpinner.setBounds(106, 68, 106, 22);
		panel_5.add(clientPortSpinner);
		clientPortSpinner.setModel(new SpinnerNumberModel(new Integer(7086),
				null, null, new Integer(1)));
		clientPortEditor = new JSpinner.NumberEditor(clientPortSpinner, "#####");
		initPortEditor(clientPortEditor, "7086");
		clientPortSpinner.setEditor(clientPortEditor);
		clientPortSpinner.setEnabled(true);

		JLabel lblPort = new JLabel(BaseMessages
				.getString("SysitConfigForm.label3"));
		lblPort.setBounds(10, 71, 61, 19);
		panel_5.add(lblPort);

		JLabel lblIp = new JLabel(BaseMessages
				.getString("SysitConfigForm.label2"));
		lblIp.setBounds(10, 44, 27, 15);
		panel_5.add(lblIp);

		ipField = new IPField();
		ipField.setBounds(72, 36, 140, 23);
		panel_5.add(ipField);

		// enable
		ipField.setEnabled(true);
		rdbtnTcpip = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbTcpClient"));
		rdbtnTcpip.setBounds(6, 6, 121, 23);
		panel_5.add(rdbtnTcpip);
		rdbtnTcpip.setSelected(true);
		buttonGroup.add(rdbtnTcpip);
		rdbtnTcpip.addActionListener(connTypeRidioLsn);
		rdbtnRs.addActionListener(connTypeRidioLsn);

		String[] modelTypes = Common.GetModels();
		modelTypeComboBox = new JComboBox(modelTypes);
		modelTypeComboBox.setBounds(371, 7, 145, 21);
		modelTypeComboBox.setEnabled(true);
		
		contentPanel.add(modelTypeComboBox);

		modelType = new JLabel(BaseMessages
				.getString("ReaderConfigForm.label44"));
		modelType.setBounds(277, 6, 109, 23);
		contentPanel.add(modelType);


		modelTypeComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ModelTypeComBoxControll();
				
			}

		});

		modelTypeComboBox.setSelectedIndex(0);
		// 读写器类型切换 -- 800型 或 500型
		readerTypeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String type = readerTypeComboBox.getSelectedItem().toString();

				if (type.equals("500")) {
					// 修改天线设定
					antennaComboBox.removeAllItems();
					antennaComboBox.addItem("1#");
					antennaComboBox.addItem("2#");
					antennaComboBox.addItem("1-2#");

					// 修改停止类型
					stopTypeComboBox.setSelectedIndex(0);
				} else if (type.equals("800")) {
					// 修改天线设定
					antennaComboBox.removeAllItems();
					antennaComboBox.addItem("1#");
					antennaComboBox.addItem("2#");
					antennaComboBox.addItem("3#");
					antennaComboBox.addItem("4#");
					antennaComboBox.addItem("1-2#");
					antennaComboBox.addItem("1-3#");
					antennaComboBox.addItem("1-4#");

					// 修改停止类型
					stopTypeComboBox.setSelectedIndex(1);
				}
			}
		});

		// 保存配置按钮
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String className;
				String select = tabbedPane.getTitleAt(tabbedPane
						.getSelectedIndex());
				// TODO : 需要调整
				if (select.equals("IRP1")) {
					className = "com.invengo.xcrf.core.demo.UserConfig_IRP1";
				} else {
					className = "com.invengo.xcrf.core.demo.UserConfig_IRP1";
				}

				UserConfig config;
				try {

					config = (UserConfig) Class.forName(className)
							.newInstance();
					config = DemoRegistry.getCurrentDemo().getConfig();
					config.config(dialog);

					if (!update) {
						Demo demo = DemoRegistry.getDemoByName(config
								.getReaderName());
						if (demo != null) {
							MessageDialog.showInfoMessage(BaseMessages
									.getString("Message.MSG_54"));
							return;
						}
					}

					// ping test
//					if (config.getConnType().equals("TCPIP_Client")) {
//						if (!Common.pingTest(config.getConnStr()))
//							if (!MessageDialog.showConfirmDialog(BaseMessages
//									.getString("Message.MSG_52"), "")) {
//								dialog.setVisible(false);
//								clearData();
//								return;
//							}
//					}

					boolean selected = false;
					if (update) {
						if (demo != null) {
							selected = demo.getNode().isSelected();
							/*xusheng 2012.5.31 start*/
							if((config.getReaderName().equals(demo.getDemoName())) && (config.getReaderGroup().equals(demo.getGroupName()))){
								config.setNewReaderName(config.getReaderName());
								//demo.setConfig(config);
								DemoRegistry.getAllDemo().remove(demo.getDemoName());
								DemoRegistry.removeRegistryDemo(demo);
								Demo newDemo = new Demo(config);
								newDemo.setNode(demo.getNode());
								DemoRegistry.setCurrentDemo(newDemo);
								DemoRegistry.getAllDemo().put(newDemo.getDemoName(), newDemo);
								DemoRegistry.getCurrentDemos().put(newDemo.getDemoName(), newDemo);
								
							}else{
								DemoRegistry.removeRegistryDemo(demo);
								Demo newDemo = new Demo(config);
								RootTree.getTree().addReaderNode(newDemo);
								DemoRegistry.registryDemo(newDemo);
								DemoRegistry.setCurrentDemo(newDemo);
								RootTree.getTree().removeNode(demo);
								if (newDemo.getNode() != null) {
									newDemo.getNode().setSelected(true);
								}
								RootTree.getTree().updateUI();		
								
								//如果名称有修改
								config.setNewReaderName(config.getReaderName());
								if(!(config.getReaderName().equals(demo.getDemoName()))){
									//把读写器名称改回原来的名称，否则在XML里面查找不到相应的读写器
									config.setReaderName(demo.getDemoName());
								}
								/*xusheng 2012.5.31 end*/
							}
							demo = null;
							if (config.getConnType().equals("TCPIP_Server")) {
								if (config instanceof UserConfig_IRP1) {
									UserConfig_IRP1 irp1 = (UserConfig_IRP1) config;
									RootTree.getTree().addServerNode(
											"Port:" + irp1.getServerPort(),
											irp1.isEnable());
								}
							}
						}

						if (!config.updateConfig())
						{
							return;
						}
						/*成功后还得改回来*/
						config.setReaderName(config.getNewReaderName());
						/*xusheng 2012.5.31 end*/
					} else {
						// 持久化读写器信息
						if (!config.saveConfig()) {
							MessageDialog.showInfoMessage(BaseMessages
									.getString("Message.MSG_53"));
							return;
						}
					}

					dialog.setVisible(false);
					clearData();
					// 添加到节点
					if (!update) {
						if (config.getConnType().equals("TCPIP_Server")) {
							if (config instanceof UserConfig_IRP1) {
								UserConfig_IRP1 irp1 = (UserConfig_IRP1) config;
								RootTree.getTree().addServerNode(
										"Port:" + irp1.getServerPort(),
										irp1.isEnable());
							}
						} else {
							Demo newDemo = new Demo(config);
							DemoRegistry.registryDemo(newDemo);
							RootTree.getTree().addReaderNode(newDemo);
							if (newDemo.getNode() != null) {
								newDemo.getNode().setSelected(true);
							}
						}
					}
					// 分组信息更新
					String groupName = config.getReaderGroup();
					if (!groupLst.contains(groupName)) {
						groupLst.add(groupName);
						Collections.sort(groupLst);
						groupNameComBox.addItem(groupName);
					}

					RootTree.getTree().updateUI();

				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});

		// 取消配置按钮
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				clearData();
			}
		});
		

	}
	private String getAutoReadName(){
		Map<String, Demo> allDemo = DemoRegistry.getAllDemo();
		Integer ReaderNameid = 1;
		for (Entry<String, Demo> demo : allDemo.entrySet()) {
			String readerName = demo.getValue().getDemoName();
			int index = readerName.indexOf("Reader");
			if(index != -1){
				String Readerid = readerName.substring(index + 6);
				char[] id = Readerid.toCharArray();
				int j = 0;
				for(int i = 0 ; i < id.length; i++ ){
					if(id[i] >= '0' && id[i] <= '9'){
						j++;
					}
				}
				if(j == id.length){
					if(Integer.valueOf(Readerid) >= ReaderNameid)
					ReaderNameid = Integer.valueOf(Readerid) + 1;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Reader");
		sb.append(ReaderNameid.toString());
		return sb.toString();
	}

	private void configEnable() {
			if (rdbtnTcpip.isSelected()) {
				// enable
				ipField.setEnabled(true);
				clientPortSpinner.setEnabled(true);
	
				// disable
				commBox.setEnabled(false);
				commbtBox.setEnabled(false);
				serverPortspinner.setEnabled(false);
	
				//changeViewMode("client");
	
			} else if (rdbtnRs.isSelected()) {
				// enable
				commBox.setEnabled(true);
				commbtBox.setEnabled(true);
	
				// disable
				ipField.setEnabled(false);
				clientPortSpinner.setEnabled(false);
				serverPortspinner.setEnabled(false);
	
				//changeViewMode("client");
	
			} else if (rdbtnUsb.isSelected()) {
				// disable
				commBox.setEnabled(false);
				commbtBox.setEnabled(false);
				ipField.setEnabled(false);
				clientPortSpinner.setEnabled(false);
				serverPortspinner.setEnabled(false);
	
				//changeViewMode("client");
	
			} else if (rdbtnTcpipServer.isSelected()) {
				// enable
				serverPortspinner.setEnabled(true);
	
				// disable
				ipField.setEnabled(false);
				clientPortSpinner.setEnabled(false);
				commBox.setEnabled(false);
				commbtBox.setEnabled(false);
	
				//changeViewMode("server");
			}
	}

	public void fillData(Demo demo) {
		this.demo = demo;
		UserConfig config = demo.getConfig();
		if (config instanceof UserConfig_IRP1) {
			UserConfig_IRP1 irp1 = (UserConfig_IRP1) config;

			readNameField.setText(irp1.getReaderName());

			if (config.getModelNo() == null
					|| Common.isGetModelNOByCommand(config.getModelNo())) {
				//modelType.setSelected(false);
				modelTypeComboBox.setSelectedIndex(-1);
				modelTypeComboBox.setEnabled(false);
				stopTypeComboBox.setEditable(false);
			} else {
				//modelType.setSelected(true);
				modelTypeComboBox.setSelectedItem(irp1.getModelNo());
				modelTypeComboBox.setEnabled(true);
			}

			for (int i = 0; i < groupNameComBox.getItemCount(); i++) {
				if (groupNameComBox.getItemAt(i).equals(irp1.getReaderGroup())) {
					groupNameComBox.setSelectedItem(groupNameComBox
							.getItemAt(i));
					break;
				}
			}

			enableCheckBox.setSelected(irp1.isEnable());

			if (irp1.getConnType().equals("TCPIP_Client")) {
				rdbtnTcpip.setSelected(true);

				ipField.setText(irp1.getConnStr().substring(0,
						irp1.getConnStr().indexOf(":")));
				clientPortEditor.getTextField().setText(
						irp1.getConnStr().substring(
								irp1.getConnStr().indexOf(":") + 1));

			} else if (irp1.getConnType().equals("RS232")) {
				rdbtnRs.setSelected(true);
				String connStr = irp1.getConnStr();
				String port = connStr.substring(0, connStr.indexOf(","));
				String btn = connStr.substring(connStr.indexOf(",") + 1);

				for (int i = 0; i < commBox.getItemCount(); i++) {
					if (commBox.getItemAt(i).equals(port)) {
						commBox.setSelectedItem(commBox.getItemAt(i));
						break;
					}
				}
				for (int i = 0; i < commbtBox.getItemCount(); i++) {
					if (commbtBox.getItemAt(i).equals(btn)) {
						commbtBox.setSelectedItem(commbtBox.getItemAt(i));
						break;
					}
				}

			} else if (irp1.getConnType().equals("USB")) {
				rdbtnUsb.setSelected(true);

			}
			configEnable();

			for (int i = 0; i < readerTypeComboBox.getItemCount(); i++) {
				if (readerTypeComboBox.getItemAt(i)
						.equals(irp1.getReaderType())) {
					readerTypeComboBox.setSelectedItem(readerTypeComboBox
							.getItemAt(i));
					break;
				}
			}

			for (int i = 0; i < msgTypeComboBox.getItemCount(); i++) {
				if (msgTypeComboBox.getItemAt(i).equals(
						irp1.getRmb().toString())) {
					msgTypeComboBox.setSelectedItem(msgTypeComboBox
							.getItemAt(i));
					break;
				}
			}
			for (int i = 0; i < antennaComboBox.getItemCount(); i++) {
				if (i == irp1.getAntennaIndex()) {
					antennaComboBox.setSelectedIndex(i);
					break;
				}
			}
			if (panel_9.isVisible()) {
				int i = irp1.getAntennaIndex();
				checkBox.setSelected(Common.isZeroInByte(i, 0));
				if(i >= 8){
					checkBox_1.setSelected(Common.isZeroInByte(i, 1));
					checkBox_2.setSelected(Common.isZeroInByte(i, 2));
					checkBox_3.setSelected(Common.isZeroInByte(i, 3));
				}

			}

			loopButton.setSelected(irp1.getIsLoop());
			singleButton.setSelected(!irp1.getIsLoop());

			numberTagEditor.getTextField().setText(irp1.getTagNum() + "");
			numReadTimeEditor.getTextField().setText(irp1.getReadTime() + "");
			numStopTimeEditor.getTextField().setText(irp1.getStopTime() + "");

			for (int i = 0; i < stopTypeComboBox.getItemCount(); i++) {
				if (stopTypeComboBox.getItemAt(i).equals(irp1.getStopType())) {
					stopTypeComboBox.setSelectedItem(stopTypeComboBox
							.getItemAt(i));
					break;
				}
			}
			if(((UserConfig_IRP1)config).getReadTime() == 0 && ((UserConfig_IRP1)config).getStopTime() == 0){
				checkBox_4.setSelected(false);
				label_8.setEnabled(false);
				numReadTimeSpinner.setValue(0);
				numReadTimeSpinner.setEnabled(false);
				numStopTimeSpinner.setValue(0);
				label_9.setEnabled(false);
				numStopTimeSpinner.setEnabled(false);
			}else{
				checkBox_4.setSelected(true);
				label_8.setEnabled(true);
				numReadTimeSpinner.setValue(((UserConfig_IRP1)config).getReadTime());
				numReadTimeSpinner.setEnabled(true);
				numStopTimeSpinner.setValue(((UserConfig_IRP1)config).getStopTime());
				label_9.setEnabled(true);
				numStopTimeSpinner.setEnabled(true);
			}
		}
	}

	public void clearData() {
		//readNameField.setText("");
		enableCheckBox.setSelected(true);

		ipField.setIpAddress("");
		clientPortEditor.getTextField().setText("7086");
		serverPortEditor.getTextField().setText("12800");
		commBox.setSelectedIndex(-1);
		commbtBox.setSelectedIndex(-1);

		msgTypeComboBox.setSelectedIndex(0);
		antennaComboBox.setSelectedIndex(0);
		loopButton.setSelected(true);
		numberTagEditor.getTextField().setText("8");
		numReadTimeEditor.getTextField().setText("0");
		numStopTimeEditor.getTextField().setText("0");
		readerTypeComboBox.setSelectedIndex(1);
	}

	// 初始化分组下拉框
	public void initGroupCombox() {
		Map<String, Demo> allDemo = DemoRegistry.getAllDemo();
		for (Entry<String, Demo> demo : allDemo.entrySet()) {
			String groupName = demo.getValue().getGroupName();
			if (!groupLst.contains(groupName)) {
				groupLst.add(groupName);
			}
		}

		if(groupLst.size() == 0){
			groupLst.add("Group1");
		}
		Collections.sort(groupLst);
		groupNameComBox = new JComboBox(groupLst.toArray());
		groupNameComBox.setEditor(new MyEditor());
		groupNameComBox.setSelectedIndex(0);
		
		
		
	}

	private class MyEditor implements ComboBoxEditor{

		final protected JTextField editor;
		private EventListenerList listenerList = new EventListenerList();
		public MyEditor(){
			editor = new JTextField();
			editor.setDocument(new LimitDocument(18));
		}
		@Override
		public void addActionListener(ActionListener arg0) {
			// TODO Auto-generated method stub
			listenerList.add(ActionListener.class, arg0);
		}

		@Override
		public Component getEditorComponent() {
			// TODO Auto-generated method stub
			return editor;
		}

		@Override
		public Object getItem() {
			// TODO Auto-generated method stub
			return editor.getText();
		}

		@Override
		public void removeActionListener(ActionListener arg0) {
			// TODO Auto-generated method stub
			listenerList.remove(ActionListener.class, arg0);
		}

		@Override
		public void selectAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setItem(Object arg0) {
			// TODO Auto-generated method stub
			if(arg0 instanceof String){
				String item = (String)arg0;
//				char[] c = item.toCharArray();
//				if(c.length > 18){
//					
//				}
//				else
//				{
//					
//				}
				editor.setText(item);
			}
		}
		
	}
	// 初始化port选择栏
	public void initPortEditor(final JSpinner.NumberEditor editor,
			final String defaultVal) {
		editor.getTextField().getActionMap().put(
				editor.getTextField().getInputMap().get(
						KeyStroke.getKeyStroke("ctrl V")),
				new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {

					}
				});
		editor.getTextField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(
						String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}

		});
		editor.getTextField().addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				int portInt = 0;
				try {
					portInt = Integer.parseInt(editor.getTextField().getText());
				} catch (Exception e) {
					editor.getTextField().setText(defaultVal);
				}
				if (portInt > 65535) {
					editor.getTextField().setText(defaultVal);
				}
			}

		});
	}

	// 初始化预计读标签数选择栏
	public void initNumTagEditor(final JSpinner.NumberEditor editor,
			final String defaultVal) {
		editor.getTextField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(
						String.valueOf(e.getKeyChar())).find();
				if (!flag) {
					e.consume();
					return;
				}
			}

		});
		editor.getTextField().addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				int portInt = 0;
				try {
					portInt = Integer.parseInt(editor.getTextField().getText());
				} catch (Exception e) {
					editor.getTextField().setText(defaultVal);
				}
				if (portInt > 32768) {
					editor.getTextField().setText("32768");
				}
			}

		});
	}

	// getters and setters
	public List<String> getGroupLst() {
		return groupLst;
	}

	public void setGroupLst(List<String> groupLst) {
		this.groupLst = groupLst;
	}

	public JTextField getReadNameField() {
		return readNameField;
	}

	public void setReadNameField(JTextField readNameField) {
		this.readNameField = readNameField;
	}

	public JComboBox getGroupNameComBox() {
		return groupNameComBox;
	}

	public void setGroupNameComBox(JComboBox xfGroupNameComBox) {
		this.groupNameComBox = xfGroupNameComBox;
	}

	public JCheckBox getEnableCheckBox() {
		return enableCheckBox;
	}

	public void setEnableCheckBox(JCheckBox enableCheckBox) {
		this.enableCheckBox = enableCheckBox;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JRadioButton getRdbtnUsb() {
		return rdbtnUsb;
	}

	public JRadioButton getRdbtnTcpip() {
		return rdbtnTcpip;
	}

	public void setRdbtnTcpip(JRadioButton rdbtnTcpip) {
		this.rdbtnTcpip = rdbtnTcpip;
	}

	public JRadioButton getRdbtnRs() {
		return rdbtnRs;
	}

	public void setRdbtnRs(JRadioButton rdbtnRs) {
		this.rdbtnRs = rdbtnRs;
	}

	public JRadioButton getRdbtnTcpipServer() {
		return rdbtnTcpipServer;
	}

	public void setRdbtnTcpipServer(JRadioButton rdbtnTcpipServer) {
		this.rdbtnTcpipServer = rdbtnTcpipServer;
	}

	public IPField getIpField() {
		return ipField;
	}

	public void setIpField(IPField ipField) {
		this.ipField = ipField;
	}

	public JSpinner getClientPortSpinner() {
		return clientPortSpinner;
	}

	public void setClientPortSpinner(JSpinner clientPortSpinner) {
		this.clientPortSpinner = clientPortSpinner;
	}

	public JSpinner.NumberEditor getClientPortEditor() {
		return clientPortEditor;
	}

	public void setClientPortEditor(JSpinner.NumberEditor clientPortEditor) {
		this.clientPortEditor = clientPortEditor;
	}

	public JSpinner getServerPortspinner() {
		return serverPortspinner;
	}

	public void setServerPortspinner(JSpinner serverPortspinner) {
		this.serverPortspinner = serverPortspinner;
	}

	public JSpinner.NumberEditor getServerPortEditor() {
		return serverPortEditor;
	}

	public void setServerPortEditor(JSpinner.NumberEditor serverPortEditor) {
		this.serverPortEditor = serverPortEditor;
	}

	public JComboBox getCommBox() {
		return commBox;
	}

	public void setCommBox(JComboBox commBox) {
		this.commBox = commBox;
	}

	public JComboBox getCommbtBox() {
		return commbtBox;
	}

	public void setCommbtBox(JComboBox commbtBox) {
		this.commbtBox = commbtBox;
	}

	public JComboBox getReaderTypeComboBox() {
		return readerTypeComboBox;
	}

	public void setReaderTypeComboBox(JComboBox readerTypeComboBox) {
		this.readerTypeComboBox = readerTypeComboBox;
	}

	public JComboBox getMsgTypeComboBox() {
		return msgTypeComboBox;
	}

	public void setMsgTypeComboBox(JComboBox msgTypeComboBox) {
		this.msgTypeComboBox = msgTypeComboBox;
	}

	public JComboBox getAntennaComboBox() {
		return antennaComboBox;
	}

	public void setAntennaComboBox(JComboBox antennaComboBox) {
		this.antennaComboBox = antennaComboBox;
	}

	public JRadioButton getLoopButton() {
		return loopButton;
	}

	public void setLoopButton(JRadioButton loopButton) {
		this.loopButton = loopButton;
	}

	public JRadioButton getSingleButton() {
		return singleButton;
	}

	public void setSingleButton(JRadioButton singleButton) {
		this.singleButton = singleButton;
	}

	public JSpinner getNumberTagSpinner() {
		return numberTagSpinner;
	}

	public void setNumberTagSpinner(JSpinner numberTagSpinner) {
		this.numberTagSpinner = numberTagSpinner;
	}

	public JSpinner getNumReadTimeSpinner() {
		return numReadTimeSpinner;
	}

	public void setNumReadTimeSpinner(JSpinner numReadTimeSpinner) {
		this.numReadTimeSpinner = numReadTimeSpinner;
	}

	public JSpinner getNumStopTimeSpinner() {
		return numStopTimeSpinner;
	}

	public void setNumStopTimeSpinner(JSpinner numStopTimeSpinner) {
		this.numStopTimeSpinner = numStopTimeSpinner;
	}

	public JSpinner.NumberEditor getNumberTagEditor() {
		return numberTagEditor;
	}

	public void setNumberTagEditor(JSpinner.NumberEditor numberTagEditor) {
		this.numberTagEditor = numberTagEditor;
	}

	public JSpinner.NumberEditor getNumReadTimeEditor() {
		return numReadTimeEditor;
	}

	public void setNumReadTimeEditor(JSpinner.NumberEditor numReadTimeEditor) {
		this.numReadTimeEditor = numReadTimeEditor;
	}

	public JSpinner.NumberEditor getNumStopTimeEditor() {
		return numStopTimeEditor;
	}

	public void setNumStopTimeEditor(JSpinner.NumberEditor numStopTimeEditor) {
		this.numStopTimeEditor = numStopTimeEditor;
	}

	public JComboBox getStopTypeComboBox() {
		return stopTypeComboBox;
	}

	public void setStopTypeComboBox(JComboBox stopTypeComboBox) {
		this.stopTypeComboBox = stopTypeComboBox;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public boolean[] getAntentaCheckBoxAry() {
		return new boolean[] { true, false, false, false,
				this.checkBox_3.isSelected(), this.checkBox_2.isSelected(),
				this.checkBox_1.isSelected(), this.checkBox.isSelected() };
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
		//readNameField.setEnabled(!update);
		//groupNameComBox.setEnabled(!update);
		// this.rdbtnTcpipServer.setVisible(!update);
		// this.lblPort_1.setVisible(!update);
		// this.serverPortspinner.setVisible(!update);
	}

	public JComboBox getModelTypeComboBox() {
		return modelTypeComboBox;
	}

	
	
	public class LimitDocument extends PlainDocument {
		//只充许输入maxLength 个字符
		private int maxLength = 8;
	 
		public LimitDocument(int maxLength) {
			super();
			this.maxLength = maxLength;
		}
	 
		public LimitDocument(Content c, int maxLength) {
			super(c);
			this.maxLength = maxLength;
		}
		private static final long serialVersionUID = 1L;
	 

		@Override
		public void insertString(int offset, String s, AttributeSet a)
				throws BadLocationException {
			if (s == null || offset < 0) {  
	            return;  
	        }  
	        for (int i = 0; i < s.length(); i++) {  
	            if (getLength() > maxLength - 1) {  
	                break;  
	            }  
	            super.insertString(offset + i, s.substring(i, i + 1),  
	                    a); 
	        }  
	        return;
		} 
	}
	
	public class CheckBoxBorder extends AbstractBorder{
		
		public CheckBoxBorder(){
			
		}
	}
}
