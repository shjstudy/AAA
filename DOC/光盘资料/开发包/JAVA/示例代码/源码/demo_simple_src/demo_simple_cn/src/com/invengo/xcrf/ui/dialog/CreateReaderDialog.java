package com.invengo.xcrf.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
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
import com.invengo.xcrf.core.demo.UserConfigUtil;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.util.DemoUtil;
import com.invengo.xcrf.core.util.IPField;
import com.invengo.xcrf.ui.tree.CheckNode;
import com.invengo.xcrf.ui.tree.RootTree;

public class CreateReaderDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int step = 1;
	// page3是否第一次显示
	private int count = 0;
	// 是否sever更新
	private boolean update = false;
	private String severPort;

	private String stopType;

	// 第一页
	private JLabel label;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;

	// 第二页
	private JTextField tfdReaderId;
	private JLabel lblReaderType;
	private JLabel lblreaderId;
	private JLabel lblGroupName;
	private JComboBox groupNameComBox;
	private List<String> groupLst = new ArrayList<String>();
	private JComboBox modelTypeComboBox;

	// 第三页
	private IPField tfdTcpIp;
	private JRadioButton rbnTcpIp;
	private JLabel lblIP;
	private JLabel lblPort;
	private JRadioButton rbnRS232;
	private JLabel lblPort2;
	private JLabel lblBote;
	private JSpinner spnPort;
	private JComboBox cmbPort2;
	private JComboBox cmbBote;
	private JRadioButton rbnUSB;

	// 第四页
	private JTabbedPane tabbedPane;
	private JComboBox msgTypeComboBox;
	private JRadioButton loopButton;
	private JRadioButton singleButton;
	private JSpinner numberTagSpinner;
	private JSpinner numReadTimeSpinner;
	private JSpinner numStopTimeSpinner;
	private JSpinner.NumberEditor numberTagEditor;
	private JSpinner.NumberEditor numReadTimeEditor;
	private JSpinner.NumberEditor numStopTimeEditor;
	public static CreateDialog createDialog;
	private JPanel panel_1;
	private JPanel panel_9;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JComboBox antennaComboBox;

	// 5
	private JLabel label_5_port;
	private JSpinner spinner_5_port;

	// button
	private JButton priStep;
	public JButton okButton;
	public JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public CreateReaderDialog(JFrame mainframe) {
		this.setSize(new Dimension(750, 550));
		this.setLocationRelativeTo(mainframe);
		// setBounds(200, 200, 450, 350);
		this.setResizable(false);
		this.setTitle(BaseMessages.getString("SysitConfigForm.SysitConfigForm"));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		// 第一页 start
		label = new JLabel(BaseMessages.getString("SysitConfigForm.label17"));
		label.setBounds(250, 183, 200, 15);
		contentPanel.add(label);

		ButtonGroup bg = new ButtonGroup();
		radioButton = new JRadioButton(BaseMessages.getString("SysitConfigForm.rbClient"));
		radioButton.setBounds(250, 215, 121, 23);
		bg.add(radioButton);
		radioButton.setSelected(true);
		contentPanel.add(radioButton);

		radioButton_1 = new JRadioButton(BaseMessages.getString("SysitConfigForm.rbTcpServer"));
		radioButton_1.setBounds(250, 250, 121, 23);
		bg.add(radioButton_1);
		contentPanel.add(radioButton_1);
		// 第一页 end

		// 第二页
		{
			lblreaderId = new JLabel(BaseMessages.getString("SysitConfigForm.label1"));
			lblreaderId.setHorizontalAlignment(SwingConstants.RIGHT);
			lblreaderId.setBounds(130, 153, 200, 15);
			contentPanel.add(lblreaderId);

			tfdReaderId = new JTextField();
			tfdReaderId.setBounds(350, 148, 120, 24);
			contentPanel.add(tfdReaderId);
			tfdReaderId.setColumns(10);

			tfdReaderId.setText(getAutoReadName());
		}
		{
			lblReaderType = new JLabel(BaseMessages.getString("SysitConfigForm.label16"));
			lblReaderType.setHorizontalAlignment(SwingConstants.RIGHT);
			lblReaderType.setBounds(130, 190, 200, 15);
			contentPanel.add(lblReaderType);

			String[] modelTypes = Common.GetModels();
			modelTypeComboBox = new JComboBox(modelTypes);
			modelTypeComboBox.setBounds(350, 185, 120, 21);
			modelTypeComboBox.setEditable(false);
			contentPanel.add(modelTypeComboBox);
			modelTypeComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					ModelTypeComBoxControll();
				}
			});
		}
		{
			lblGroupName = new JLabel(BaseMessages.getString("SysitConfigForm.label15"));
			lblGroupName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblGroupName.setBounds(130, 230, 200, 15);
			contentPanel.add(lblGroupName);

			initGroupCombox();
			groupNameComBox.setBounds(350, 225, 120, 21);
			groupNameComBox.setEditable(true);
			contentPanel.add(groupNameComBox);
		}

		page2(false);
		// 第二页 end

		// 第三页
		ButtonGroup group = new ButtonGroup();

		rbnTcpIp = new JRadioButton(BaseMessages.getString("SysitConfigForm.rbTcpClient"));
		rbnTcpIp.setBounds(15, 85+100, 121, 23);
		contentPanel.add(rbnTcpIp);
		group.add(rbnTcpIp);

		lblIP = new JLabel("IP:");
		lblIP.setBounds(19, 120+100, 24, 15);
		contentPanel.add(lblIP);

		lblPort = new JLabel("Port:");
		lblPort.setBounds(19, 145+100, 30, 15);
		contentPanel.add(lblPort);

		tfdTcpIp = new IPField();
		tfdTcpIp.setBounds(50, 114+100, 115, 21);
		tfdTcpIp.setIpAddress("192.168.1.0");
		contentPanel.add(tfdTcpIp);

		rbnRS232 = new JRadioButton(BaseMessages.getString("SysitConfigForm.rbRs232"));
		rbnRS232.setBounds(250, 85+100, 121, 23);
		contentPanel.add(rbnRS232);
		group.add(rbnRS232);

		lblPort2 = new JLabel(BaseMessages.getString("SysitConfigForm.label4"));
		lblPort2.setBounds(250, 120+100, 57, 15);
		contentPanel.add(lblPort2);

		lblBote = new JLabel(BaseMessages.getString("SysitConfigForm.label5"));
		lblBote.setBounds(250, 145+100, 57, 15);
		contentPanel.add(lblBote);

		spnPort = new JSpinner();
		spnPort.setBounds(50, 141+100, 140, 24);
		spnPort.setValue(7086);
		contentPanel.add(spnPort);

		// 端口设定
		String[] comm = DemoUtil.getCommPorts();

		String[] commbt = { "9600", "19200", "115200" };

		cmbPort2 = new JComboBox(comm);
		cmbPort2.setBounds(310, 114+100, 104, 21);
		cmbPort2.setEditable(false);
		contentPanel.add(cmbPort2);

		cmbBote = new JComboBox(commbt);
		cmbBote.setBounds(310, 141+100, 104, 21);
		contentPanel.add(cmbBote);
		cmbBote.setEditable(false);

		rbnUSB = new JRadioButton("USB");
		rbnUSB.setBounds(480, 85+100, 73, 23);
		contentPanel.add(rbnUSB);
		group.add(rbnUSB);

		myActionListener ma = new myActionListener();
		rbnTcpIp.addActionListener(ma);
		rbnRS232.addActionListener(ma);
		rbnUSB.addActionListener(ma);

		page3(false);
		// 第三页end

		// 第四页
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, BaseMessages
				.getString("SysitConfigForm.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		tabbedPane.setBounds(5, 5, 740, 450);
		contentPanel.add(tabbedPane);

		panel_1 = new JPanel();
		tabbedPane.addTab("IRP1", panel_1);

		panel_1.setLayout(null);

		JLabel label_2 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label13"));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(20, 22, 120, 15);
		panel_1.add(label_2);

		JLabel label_5 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label12"));
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(20, 60, 120, 15);
		panel_1.add(label_5);

		JLabel label_6 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label8"));
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(20, 97, 120, 15);
		panel_1.add(label_6);

		JLabel label_7 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label7"));
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(20, 130, 120, 15);
		panel_1.add(label_7);

		//	String[] readerType = { "500", "800" };

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
		msgTypeComboBox.setBounds(172, 19, 181, 21);
		panel_1.add(msgTypeComboBox);

		ButtonGroup readLoopGroup = new ButtonGroup();
		loopButton = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbLoop"));
		loopButton.setSelected(true);
		loopButton.setBounds(171, 95, 120, 23);
		readLoopGroup.add(loopButton);
		panel_1.add(loopButton);

		singleButton = new JRadioButton(BaseMessages
				.getString("SysitConfigForm.rbSingle"));
		singleButton.setBounds(295, 95, 73, 23);
		readLoopGroup.add(singleButton);
		panel_1.add(singleButton);

		numberTagSpinner = new JSpinner();
		numberTagSpinner.setModel(new SpinnerNumberModel(new Integer(7), null,
				null, new Integer(1)));
		numberTagEditor = new JSpinner.NumberEditor(numberTagSpinner, "#####");
		initNumTagEditor(numberTagEditor, "8");
		numberTagSpinner.setEditor(numberTagEditor);
		numberTagSpinner.setBounds(174, 125, 54, 22);
		panel_1.add(numberTagSpinner);

		JPanel myPanel = new JPanel();
		myPanel.setLayout(null);
		myPanel.setBorder(new TitledBorder(null, null, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		myPanel.setBounds(46, 202, 305, 70);

		panel_1.add(myPanel);

		final JLabel label_8 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label10"));
		label_8.setBounds(14, 15, 124, 15);
		myPanel.add(label_8);

		final JLabel label_9 = new JLabel(BaseMessages
				.getString("SysitConfigForm.label11"));
		label_9.setBounds(14, 40, 124, 21);
		myPanel.add(label_9);

		numReadTimeSpinner = new JSpinner();
		numReadTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0),
				null, null, new Integer(1)));
		numReadTimeEditor = new JSpinner.NumberEditor(numReadTimeSpinner,
				"#####");
		initNumTagEditor(numReadTimeEditor, "0");
		numReadTimeSpinner.setEditor(numReadTimeEditor);
		numReadTimeSpinner.setBounds(144, 12, 133, 22);
		myPanel.add(numReadTimeSpinner);

		numStopTimeSpinner = new JSpinner();
		numStopTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0),
				null, null, new Integer(1)));
		numStopTimeEditor = new JSpinner.NumberEditor(numStopTimeSpinner,
				"#####");
		initNumTagEditor(numStopTimeEditor, "0");
		numStopTimeSpinner.setEditor(numStopTimeEditor);
		numStopTimeSpinner.setBounds(143, 40, 134, 22);
		myPanel.add(numStopTimeSpinner);

		panel_9 = new JPanel();
		panel_9.setVisible(false);
		panel_9.setBounds(172, 51, 209, 33);
		panel_1.add(panel_9);
		panel_9.setLayout(null);

		String[] antennaTypes = { "1#", "2#", "3#", "4#", "1-2#", "1-3#",
				"1-4#" };
		antennaComboBox = new JComboBox(antennaTypes);
		antennaComboBox.setBounds(172, 57, 181, 21);
		panel_1.add(antennaComboBox);

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

		final JCheckBox checkBox_4 = new JCheckBox(BaseMessages
				.getString("SysitConfigForm.checkBox2"));
		checkBox_4.setBounds(46, 167, 181, 23);
		panel_1.add(checkBox_4);
		checkBox_4.setOpaque(true);
		checkBox_4.setSelected(false);
		{
			numReadTimeSpinner.setValue(0);
			numReadTimeSpinner.setEnabled(false);
			numStopTimeSpinner.setValue(0);
			numStopTimeSpinner.setEnabled(false);
			label_8.setEnabled(false);
			label_9.setEnabled(false);
		}

		checkBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (checkBox_4.isSelected()) {
					numReadTimeSpinner.setEnabled(true);
					numStopTimeSpinner.setEnabled(true);
					label_8.setEnabled(true);
					label_9.setEnabled(true);
				} else {
					numReadTimeSpinner.setValue(0);
					numReadTimeSpinner.setEnabled(false);
					numStopTimeSpinner.setValue(0);
					numStopTimeSpinner.setEnabled(false);
					label_8.setEnabled(false);
					label_9.setEnabled(false);
				}
			}

		});

		page4(false);
		// 第四页end

		// 第五页
		label_5_port = new JLabel("Port:");
		label_5_port.setBounds(300, 243, 66, 21);
		contentPanel.add(label_5_port);

		spinner_5_port = new JSpinner();
		spinner_5_port.setBounds(370, 244, 73, 20);
		contentPanel.add(spinner_5_port);

		page5(false);
		// end

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, null,
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			{
				priStep = new JButton(BaseMessages.getString("CreateReaderDialog.pristep"));

				buttonPane.add(priStep);

				priStep.setVisible(false);

				priStep.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (step == 5) {
							page5(false);
							page1(true);
							step = 1;
							okButton.setEnabled(true);
							priStep.setVisible(false);
							cancelButton.setEnabled(false);
							return;
						}
						if (step == 4) {
							page4(false);
							page3(true);
							step = 3;
							okButton.setEnabled(true);
							priStep.setVisible(true);
							cancelButton.setEnabled(false);
							return;
						}
						if (step == 3) {
							page3(false);
							page2(true);
							step = 2;
							okButton.setEnabled(true);
							priStep.setVisible(true);
							cancelButton.setEnabled(false);
							return;
						}
						if (step == 2) {
							page2(false);
							page1(true);
							step = 1;
							okButton.setEnabled(true);
							priStep.setVisible(false);
							cancelButton.setEnabled(false);
							return;
						}
					}
				});

			}
			{
				okButton = new JButton(BaseMessages.getString("CreateReaderDialog.nextstep"));

				buttonPane.add(okButton);

				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (step == 1) {
							if (CreateReaderDialog.this.radioButton
									.isSelected()) {
								page1(false);
								page2(true);
								step = 2;
								priStep.setVisible(true);
							} else {
								page1(false);
								page5(true);
								step = 5;
								priStep.setVisible(true);
								cancelButton.setEnabled(true);
								okButton.setEnabled(false);
							}
							return;
						}
						if (step == 2) {
							if (!page2_check()) {
								return;
							}
							page2(false);
							page3(true);
							step = 3;
							return;
						}
						if (step == 3) {
							if (!page3_check()) {
								return;
							}
							page3(false);
							page4(true);
							step = 4;
							priStep.setVisible(true);
							cancelButton.setEnabled(true);
							okButton.setEnabled(false);
							return;
						}

					}
				});
			}
			{
				cancelButton = new JButton(BaseMessages.getString("CreateReaderDialog.ok"));

				buttonPane.add(cancelButton);

				cancelButton.setEnabled(false);

				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						if (radioButton_1.isSelected()) {
							if (!page5_check()) {
								return;
							}
						}

						if (update) {
							CheckNode server = RootTree.getServerRootNode();
							for (int i = server.getChildCount() - 1; i >= 0; i--) {
								CheckNode child = (CheckNode) server
										.getChildAt(i);

								if (child.getNodeName().equals(severPort)) {
									server.remove(child);
									UserConfigUtil.removeServer(child
											.getNodeName().substring(
													child.getNodeName()
															.indexOf(":") + 1));
								}
							}
						}

						String className;

						className = "com.invengo.xcrf.core.demo.UserConfig_IRP1";

						UserConfig config;
						try {

							config = (UserConfig) Class.forName(className)
									.newInstance();
							config.config(CreateReaderDialog.this);

							Demo demo = DemoRegistry.getDemoByName(config
									.getReaderName());
							if (demo != null) {
								MessageDialog.showInfoMessage(BaseMessages
										.getString("Message.MSG_54"));
								return;
							}

							// 持久化读写器信息
							if (!config.saveConfig()) {
								MessageDialog.showInfoMessage(BaseMessages
										.getString("Message.MSG_53"));
								return;
							}

							CreateReaderDialog.this.setVisible(false);
							// 添加到节点

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
			}
		}
		ModelTypeComBoxControll();
	}

	public void page1(boolean visible) {
		label.setVisible(visible);
		radioButton.setVisible(visible);
		radioButton_1.setVisible(visible);
	}

	public void page2(boolean visible) {
		tfdReaderId.setVisible(visible);
		lblReaderType.setVisible(visible);
		lblreaderId.setVisible(visible);
		lblGroupName.setVisible(visible);
		groupNameComBox.setVisible(visible);
		modelTypeComboBox.setVisible(visible);
	}

	public void page3(boolean visible) {
		if (!visible) {
			tfdTcpIp.setVisible(visible);
			rbnTcpIp.setVisible(visible);
			lblIP.setVisible(visible);
			lblPort.setVisible(visible);
			rbnRS232.setVisible(visible);
			lblPort2.setVisible(visible);
			lblBote.setVisible(visible);
			spnPort.setVisible(visible);
			cmbPort2.setVisible(visible);
			cmbBote.setVisible(visible);
			rbnUSB.setVisible(visible);
			return;
		}
		try {
			Element modeE = Common.GetModelInfoNode(modelTypeComboBox
					.getSelectedItem().toString());
			String connections = modeE.getChildText("Connection");
			if (count == 0) {

				count++;
				if (connections.indexOf("TCP/IP Client") != -1)
					TcpIp(true);
				else {
					TcpIp(false);
				}
				if (connections.indexOf("RS232") != -1)
					RS232(true);
				else {
					RS232(false);
				}
				if (connections.indexOf("USB") != -1)
					USB(true);
				else {
					USB(false);
				}

				intConnectTypeSelectPanel();
			} else {
				if (connections.indexOf("TCP/IP Client") != -1)
					TcpIp(true);
				else {
					TcpIp(false);
				}
				if (connections.indexOf("RS232") != -1)
					RS232(true);
				else {
					RS232(false);
				}
				if (connections.indexOf("USB") != -1)
					USB(true);
				else {
					USB(false);
				}
				return;
			}
		} catch (Exception e) {

		}

	}

	public void page4(boolean visible) {
		for (Component c : tabbedPane.getComponents()) {
			c.setVisible(visible);
		}
		tabbedPane.setVisible(visible);
	}

	public void page5(boolean visible) {
		label_5_port.setVisible(visible);
		spinner_5_port.setVisible(visible);
	}

	// TCPIP的visible
	public void TcpIp(boolean visible) {
		rbnTcpIp.setVisible(visible);
		lblIP.setVisible(visible);
		lblPort.setVisible(visible);
		tfdTcpIp.setVisible(visible);
		spnPort.setVisible(visible);
	}

	// RS232的visible
	public void RS232(boolean visible) {
		rbnRS232.setVisible(visible);
		lblPort2.setVisible(visible);
		lblBote.setVisible(visible);
		cmbPort2.setVisible(visible);
		cmbBote.setVisible(visible);
	}

	// USB visible
	public void USB(boolean visible) {
		rbnUSB.setVisible(visible);
	}

	/**
	 * 页面跳转时的check
	 */
	public boolean page2_check() {
		if (this.tfdReaderId.getText().trim().equals("")) {

			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_50"));

			return false;
		}

		if (((String) (this.groupNameComBox.getEditor().getItem())).trim()
				.equals("")) {

			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_51"));

			return false;
		}

		return true;
	}

	public boolean page3_check() {
		if (this.getRbnTcpIp().isSelected()) {
			// ping test
			if (this.getTfdTcpIp().getIpAddress() == null
					|| !Common.pingTest(this.getTfdTcpIp().getIpAddress()))
				if (!MessageDialog.showConfirmDialog(BaseMessages
						.getString("Message.MSG_52"), "")) {
					return false;
				}

		}
		return true;
	}

	public boolean page5_check() {
		if (this.getSpinner_5_port().getValue().toString().equals("")) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_51"));
			return false;
		}
		return true;
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

		if (groupLst.size() == 0) {
			groupLst.add("Group1");
		}
		Collections.sort(groupLst);
		groupNameComBox = new JComboBox(groupLst.toArray());
		groupNameComBox.setEditor(new MyEditor());
		groupNameComBox.setSelectedIndex(0);

	}

	private class MyEditor implements ComboBoxEditor {

		final protected JTextField editor;
		private EventListenerList listenerList = new EventListenerList();

		public MyEditor() {
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
			if (arg0 instanceof String) {
				String item = (String) arg0;
				// char[] c = item.toCharArray();
				// if(c.length > 18){
				//					
				// }
				// else
				// {
				//					
				// }
				editor.setText(item);
			}
		}

	}

	public class LimitDocument extends PlainDocument {
		// 只充许输入maxLength 个字符
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
				super.insertString(offset + i, s.substring(i, i + 1), a);
			}
			return;
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

	/*
	 * ModelTypeComBox 选择型号时的控件控制
	 * 
	 */
	public void ModelTypeComBoxControll() {
		try {
			if (modelTypeComboBox.getSelectedItem() == null
					|| modelTypeComboBox.getSelectedItem().toString()
							.equals("")) {
				return;
			}

			Element modeE = Common.GetModelInfoNode(modelTypeComboBox
					.getSelectedItem().toString());

			String protocols = modeE.getChildText("Protocol");

			tabbedPane.removeAll();
			if (protocols.indexOf("IRP1.0") != -1) {
				tabbedPane.addTab("IRP1", panel_1);
				Element IRP1E = modeE.getChild("IRP1.0");

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

				int na = Integer.parseInt(IRP1E.getChildText("AntennaNum"));
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
				boolean qEnable = Boolean.parseBoolean(IRP1E.getChild("Q")
						.getAttributeValue("Enabled"));
				numberTagSpinner.setEnabled(qEnable);
				int qVal = Integer.parseInt(IRP1E.getChildText("Q"));
				numberTagEditor.getTextField().setText(
						String.valueOf((int) Math.pow(2, qVal) - 1));
				// 关功放类型
				stopType = IRP1E.getChildText("StopType");
				// stopTypeComboBox.setSelectedItem(stopType);
				// stopTypeComboBox.setEnabled(false);

			}
			// if (protocols.indexOf("IRP2.0") != -1) {
			// tabbedPane.addTab("IRP2", panel_6);
			// }
			// if (protocols.indexOf("LLRP1.01") != -1) {
			// tabbedPane.addTab("LLRP1_01", panel_7);
			// }
			// if (protocols.indexOf("LLRP1.1") != -1) {
			// tabbedPane.addTab("LLRP1_1", panel_8);
			// }

		} catch (Exception e1) {
			// do nothing
			e1.printStackTrace();
		}
	}

	private void intConnectTypeSelectPanel() {
		if (this.rbnTcpIp.isVisible()) {
			this.rbnTcpIp.setSelected(true);
			{
				this.lblPort2.setEnabled(false);
				this.lblBote.setEnabled(false);

				this.cmbPort2.setEnabled(false);
				this.cmbBote.setEnabled(false);
			}
			return;
		} else if (this.rbnRS232.isVisible()) {
			this.rbnRS232.setSelected(true);
			{
				this.lblIP.setEnabled(false);
				this.lblPort.setEnabled(false);

				this.tfdTcpIp.setEnabled(false);
				this.spnPort.setEnabled(false);
			}
			return;
		} else if (this.rbnUSB.isVisible()) {
			this.rbnUSB.setSelected(true);
			{
				this.lblIP.setEnabled(false);
				this.lblPort.setEnabled(false);

				this.tfdTcpIp.setEnabled(false);
				this.spnPort.setEnabled(false);

				this.lblPort2.setEnabled(false);
				this.lblBote.setEnabled(false);

				this.cmbPort2.setEnabled(false);
				this.cmbBote.setEnabled(false);
			}
			return;
		}
	}

	public class myActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (CreateReaderDialog.this.rbnTcpIp.isSelected()) {
				CreateReaderDialog.this.lblIP.setEnabled(true);
				CreateReaderDialog.this.lblPort.setEnabled(true);

				CreateReaderDialog.this.tfdTcpIp.setEnabled(true);
				CreateReaderDialog.this.spnPort.setEnabled(true);

				CreateReaderDialog.this.lblPort2.setEnabled(false);
				CreateReaderDialog.this.lblBote.setEnabled(false);

				CreateReaderDialog.this.cmbPort2.setEnabled(false);
				CreateReaderDialog.this.cmbBote.setEnabled(false);
			} else if (CreateReaderDialog.this.rbnRS232.isSelected()) {
				CreateReaderDialog.this.lblIP.setEnabled(false);
				CreateReaderDialog.this.lblPort.setEnabled(false);

				CreateReaderDialog.this.tfdTcpIp.setEnabled(false);
				CreateReaderDialog.this.spnPort.setEnabled(false);

				CreateReaderDialog.this.lblPort2.setEnabled(true);
				CreateReaderDialog.this.lblBote.setEnabled(true);

				CreateReaderDialog.this.cmbPort2.setEnabled(true);
				CreateReaderDialog.this.cmbBote.setEnabled(true);
			} else {
				CreateReaderDialog.this.lblIP.setEnabled(false);
				CreateReaderDialog.this.lblPort.setEnabled(false);

				CreateReaderDialog.this.tfdTcpIp.setEnabled(false);
				CreateReaderDialog.this.spnPort.setEnabled(false);

				CreateReaderDialog.this.lblPort2.setEnabled(false);
				CreateReaderDialog.this.lblBote.setEnabled(false);

				CreateReaderDialog.this.cmbPort2.setEnabled(false);
				CreateReaderDialog.this.cmbBote.setEnabled(false);
			}
		}

	}

	/**
	 * 读写器自动命名
	 * 
	 * @return
	 */
	private String getAutoReadName() {
		Map<String, Demo> allDemo = DemoRegistry.getAllDemo();
		Integer ReaderNameid = 1;
		for (Entry<String, Demo> demo : allDemo.entrySet()) {
			String readerName = demo.getValue().getDemoName();
			int index = readerName.indexOf("Reader");
			if (index != -1) {
				String Readerid = readerName.substring(index + 6);
				char[] id = Readerid.toCharArray();
				int j = 0;
				for (int i = 0; i < id.length; i++) {
					if (id[i] >= '0' && id[i] <= '9') {
						j++;
					}
				}
				if (j == id.length) {
					if (Integer.valueOf(Readerid) >= ReaderNameid)
						ReaderNameid = Integer.valueOf(Readerid) + 1;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Reader");
		sb.append(ReaderNameid.toString());
		return sb.toString();
	}

	/**
	 * 天线选择信息
	 * 
	 * @return
	 */
	public boolean[] getAntentaCheckBoxAry() {
		return new boolean[] { true, false, false, false,
				this.checkBox_3.isSelected(), this.checkBox_2.isSelected(),
				this.checkBox_1.isSelected(), this.checkBox.isSelected() };
	}

	public JRadioButton getRadioButton() {
		return radioButton;
	}

	public void setRadioButton(JRadioButton radioButton) {
		this.radioButton = radioButton;
	}

	public JRadioButton getRadioButton_1() {
		return radioButton_1;
	}

	public void setRadioButton_1(JRadioButton radioButton_1) {
		this.radioButton_1 = radioButton_1;
	}

	public JTextField getTfdReaderId() {
		return tfdReaderId;
	}

	public void setTfdReaderId(JTextField tfdReaderId) {
		this.tfdReaderId = tfdReaderId;
	}

	public JComboBox getGroupNameComBox() {
		return groupNameComBox;
	}

	public void setGroupNameComBox(JComboBox groupNameComBox) {
		this.groupNameComBox = groupNameComBox;
	}

	public List<String> getGroupLst() {
		return groupLst;
	}

	public void setGroupLst(List<String> groupLst) {
		this.groupLst = groupLst;
	}

	public JComboBox getModelTypeComboBox() {
		return modelTypeComboBox;
	}

	public void setModelTypeComboBox(JComboBox modelTypeComboBox) {
		this.modelTypeComboBox = modelTypeComboBox;
	}

	public IPField getTfdTcpIp() {
		return tfdTcpIp;
	}

	public void setTfdTcpIp(IPField tfdTcpIp) {
		this.tfdTcpIp = tfdTcpIp;
	}

	public JRadioButton getRbnTcpIp() {
		return rbnTcpIp;
	}

	public void setRbnTcpIp(JRadioButton rbnTcpIp) {
		this.rbnTcpIp = rbnTcpIp;
	}

	public JRadioButton getRbnRS232() {
		return rbnRS232;
	}

	public void setRbnRS232(JRadioButton rbnRS232) {
		this.rbnRS232 = rbnRS232;
	}

	public JSpinner getSpnPort() {
		return spnPort;
	}

	public void setSpnPort(JSpinner spnPort) {
		this.spnPort = spnPort;
	}

	public JComboBox getCmbPort2() {
		return cmbPort2;
	}

	public void setCmbPort2(JComboBox cmbPort2) {
		this.cmbPort2 = cmbPort2;
	}

	public JComboBox getCmbBote() {
		return cmbBote;
	}

	public void setCmbBote(JComboBox cmbBote) {
		this.cmbBote = cmbBote;
	}

	public JRadioButton getRbnUSB() {
		return rbnUSB;
	}

	public void setRbnUSB(JRadioButton rbnUSB) {
		this.rbnUSB = rbnUSB;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JComboBox getMsgTypeComboBox() {
		return msgTypeComboBox;
	}

	public void setMsgTypeComboBox(JComboBox msgTypeComboBox) {
		this.msgTypeComboBox = msgTypeComboBox;
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

	public JPanel getPanel_1() {
		return panel_1;
	}

	public void setPanel_1(JPanel panel_1) {
		this.panel_1 = panel_1;
	}

	public JPanel getPanel_9() {
		return panel_9;
	}

	public void setPanel_9(JPanel panel_9) {
		this.panel_9 = panel_9;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public JCheckBox getCheckBox_1() {
		return checkBox_1;
	}

	public void setCheckBox_1(JCheckBox checkBox_1) {
		this.checkBox_1 = checkBox_1;
	}

	public JCheckBox getCheckBox_2() {
		return checkBox_2;
	}

	public void setCheckBox_2(JCheckBox checkBox_2) {
		this.checkBox_2 = checkBox_2;
	}

	public JCheckBox getCheckBox_3() {
		return checkBox_3;
	}

	public void setCheckBox_3(JCheckBox checkBox_3) {
		this.checkBox_3 = checkBox_3;
	}

	public JComboBox getAntennaComboBox() {
		return antennaComboBox;
	}

	public void setAntennaComboBox(JComboBox antennaComboBox) {
		this.antennaComboBox = antennaComboBox;
	}

	public JSpinner getSpinner_5_port() {
		return spinner_5_port;
	}

	public void setSpinner_5_port(JSpinner spinner_5_port) {
		this.spinner_5_port = spinner_5_port;
	}

	public JButton getPriStep() {
		return priStep;
	}

	public void setPriStep(JButton priStep) {
		this.priStep = priStep;
	}

	public String getStopType() {
		if (this.stopType == null || "".equals(this.stopType)) {
			return "500";
		}
		return stopType;
	}

	public void setStopType(String stopType) {

		this.stopType = stopType;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getSeverPort() {
		return severPort;
	}

	public void setSeverPort(String severPort) {
		this.severPort = severPort;
	}

}
