package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.ReadTag;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.WriteEpc;
import invengo.javaapi.protocol.IRP1.WriteUserData2_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6C;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.panel.AbstractReaderDataTable;

@SuppressWarnings("serial")
public class C6_DynamicPanel extends AbstractReaderDataTable {

	private List<Integer> successLst = new ArrayList<Integer>();
	private List<Integer> failLst = new ArrayList<Integer>();
	private int row = 0;

	private final JPanel parentPanel;
	private final JPanel tagSetPanel;
	private final JCheckBox chkEpc;
	private final JLabel lblLength;
	private final JSpinner snEpcLength;
	private final JLabel lblInit;
	private final JTextField txtEpcInitVal;
	private final JLabel lblIncr;
	private final JTextField txtEpcIncrVal;

	private final JCheckBox chkUserData;
	private final JLabel lblStartIndex;
	private final JSpinner snUserDataStartIndex;
	private final JLabel lblUserDataLength;
	private final JSpinner snUserDataLength;
	private final JCheckBox chkChangeLong;

	private final JLabel lblUserDataInitVal;
	private final JTextArea txtUserDataInitVal;
	private final JLabel lblUserDataIncrVal;
	private final JTextField txtUserDataIncrVal;

	private final JRadioButton rdbtnSequence;
	private final JRadioButton rdbtnRandom;

	private final JLabel lblPwd;
	private final JTextField txtPwd;

	private final JLabel lblRWInterval;
	private final JSpinner snRWInterval;
	private final JLabel lblUnit;
	private final JLabel lblWriteTime;
	private final JSpinner snWriteTime;

	// private final JButton btnClean;
	private final JButton btnStart;
	private final JButton btnStop;

	private ReadMemoryBank rmb;
	private Byte q;
	private byte antenna;

	private volatile boolean isStop;

	private boolean isUnfix;
	private int epcLen;
	private Integer userdataLen;
	private int userdataPtr;
	private byte[] pwd;
	private Integer wCount;
	private Integer sleepTime;
	private byte[] wEpcData;
	private byte[] wUserData;
	private String readerType;
	private final Object objLock = new Object();

	public C6_DynamicPanel() {
		setLayout(null);

		parentPanel = new JPanel();
		parentPanel.setBorder(null);
		parentPanel.setBounds(10, 0, 620, 162);
		add(parentPanel);
		parentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(centerTable);
		scrollPane.setBounds(10, 0, 600, 148);

		parentPanel.add(scrollPane);

		tagSetPanel = new JPanel();
		tagSetPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("C6_Dynamic.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLACK));
		tagSetPanel.setBounds(10, 172, 620, 300);
		add(tagSetPanel);
		tagSetPanel.setLayout(null);

		chkEpc = new JCheckBox(BaseMessages.getString("C6_Dynamic.checkBox1"));
		chkEpc.setBounds(10, 30, 60, 30);
		tagSetPanel.add(chkEpc);
		chkEpc.setSelected(false);
		chkEpc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (chkEpc.isSelected()) {
					if (rdbtnSequence.isSelected()) {
						snEpcLength.setEnabled(false);
						lblLength.setEnabled(true);
						txtEpcInitVal.setEnabled(true);
						lblInit.setEnabled(true);
						lblIncr.setEnabled(true);
						txtEpcIncrVal.setEnabled(true);
					} else {
						snEpcLength.setEnabled(true);
						lblLength.setEnabled(true);
						txtEpcInitVal.setEnabled(false);
						lblInit.setEnabled(false);
						lblIncr.setEnabled(false);
						txtEpcIncrVal.setEnabled(false);
					}
				} else {
					snEpcLength.setEnabled(false);
					lblLength.setEnabled(false);
					txtEpcInitVal.setEnabled(false);
					lblInit.setEnabled(false);
					lblIncr.setEnabled(false);
					txtEpcIncrVal.setEnabled(false);
				}
			}

		});

		lblLength = new JLabel(BaseMessages.getString("C6_Dynamic.label5"));
		lblLength.setBounds(70, 30, 40, 30);
		lblLength.setEnabled(false);
		tagSetPanel.add(lblLength);

		snEpcLength = new JSpinner(new SpinnerNumberModel(6, 0, 15, 1));
		snEpcLength.setBounds(110, 34, 45, 20);

		tagSetPanel.add(snEpcLength);

		lblInit = new JLabel(BaseMessages.getString("C6_Dynamic.label9"));
		lblInit.setBounds(10, 70, 60, 30);
		tagSetPanel.add(lblInit);

		txtEpcInitVal = new JTextField();
		txtEpcInitVal.setBounds(70, 70, 350, 30);
		txtEpcInitVal.setDocument(new TextDocument(0, 0));
		txtEpcInitVal.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				snEpcLength
						.setValue(txtEpcInitVal.getDocument().getLength() / 4);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				snEpcLength
						.setValue(txtEpcInitVal.getDocument().getLength() / 4);
			}
		});
		tagSetPanel.add(txtEpcInitVal);

		lblIncr = new JLabel(BaseMessages.getString("C6_Dynamic.label10"));
		lblIncr.setBounds(420, 70, 60, 30);
		lblIncr.setEnabled(false);
		tagSetPanel.add(lblIncr);

		txtEpcIncrVal = new JTextField();
		txtEpcIncrVal.setBounds(480, 70, 50, 30);
		txtEpcIncrVal.setDocument(new TextDocument(0, 0));
		tagSetPanel.add(txtEpcIncrVal);

		chkUserData = new JCheckBox(BaseMessages
				.getString("C6_Dynamic.checkBox2"));
		chkUserData.setBounds(10, 110, 100, 30);

		tagSetPanel.add(chkUserData);
		chkUserData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (chkUserData.isSelected()) {
					if (rdbtnSequence.isSelected()) {
						lblStartIndex.setEnabled(true);
						snUserDataStartIndex.setEnabled(true);
						lblUserDataLength.setEnabled(false);
						snUserDataLength.setEnabled(false);
						chkChangeLong.setEnabled(true);
						lblUserDataInitVal.setEnabled(true);
						txtUserDataInitVal.setEnabled(true);
						lblUserDataIncrVal.setEnabled(true);
						txtUserDataIncrVal.setEnabled(true);
					} else {
						lblStartIndex.setEnabled(true);
						snUserDataStartIndex.setEnabled(true);
						lblUserDataLength.setEnabled(true);
						snUserDataLength.setEnabled(true);
						chkChangeLong.setEnabled(true);
						lblUserDataInitVal.setEnabled(false);
						txtUserDataInitVal.setEnabled(false);
						lblUserDataIncrVal.setEnabled(false);
						txtUserDataIncrVal.setEnabled(false);
					}
				} else {
					lblStartIndex.setEnabled(false);
					snUserDataStartIndex.setEnabled(false);
					lblUserDataLength.setEnabled(false);
					snUserDataLength.setEnabled(false);
					chkChangeLong.setEnabled(false);
					lblUserDataInitVal.setEnabled(false);
					txtUserDataInitVal.setEnabled(false);
					lblUserDataIncrVal.setEnabled(false);
					txtUserDataIncrVal.setEnabled(false);
				}
			}

		});

		lblStartIndex = new JLabel(BaseMessages.getString("C6_Dynamic.label7"));
		lblStartIndex.setBounds(110, 110, 70, 30);
		lblStartIndex.setEnabled(false);
		tagSetPanel.add(lblStartIndex);

		snUserDataStartIndex = new JSpinner(new SpinnerNumberModel(0, 0, 31, 2));
		snUserDataStartIndex.setBounds(180, 115, 45, 20);
		tagSetPanel.add(snUserDataStartIndex);
		snUserDataStartIndex.setEnabled(false);

		lblUserDataLength = new JLabel(BaseMessages
				.getString("C6_Dynamic.label5"));
		lblUserDataLength.setBounds(240, 110, 45, 30);
		lblUserDataLength.setEnabled(false);
		tagSetPanel.add(lblUserDataLength);

		snUserDataLength = new JSpinner(new SpinnerNumberModel(8, 0, 32, 1));
		snUserDataLength.setBounds(280, 115, 45, 20);
		snUserDataLength.setEnabled(false);
		tagSetPanel.add(snUserDataLength);

		chkChangeLong = new JCheckBox(BaseMessages
				.getString("C6_Dynamic.cb_unfix"));
		chkChangeLong.setBounds(330, 110, 120, 30);
		tagSetPanel.add(chkChangeLong);
		chkChangeLong.setEnabled(false);

		lblUserDataInitVal = new JLabel(BaseMessages
				.getString("C6_Dynamic.label1"));
		lblUserDataInitVal.setBounds(10, 140, 60, 30);
		tagSetPanel.add(lblUserDataInitVal);

		JScrollPane scpUserData = new JScrollPane();
		scpUserData.setBounds(70, 140, 350, 60);
		tagSetPanel.add(scpUserData);

		txtUserDataInitVal = new JTextArea();
		txtUserDataInitVal.setLineWrap(true);
		txtUserDataInitVal.setEnabled(false);
		txtUserDataInitVal.setDocument(new TextDocument(0, 0));
		txtUserDataInitVal.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						snUserDataLength.setValue(txtUserDataInitVal
								.getDocument().getLength() / 4);
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						snUserDataLength.setValue(txtUserDataInitVal
								.getDocument().getLength() / 4);
					}
				});
		scpUserData.setViewportView(txtUserDataInitVal);

		lblUserDataIncrVal = new JLabel(BaseMessages
				.getString("C6_Dynamic.label10"));
		lblUserDataIncrVal.setBounds(420, 140, 60, 30);
		lblUserDataIncrVal.setEnabled(false);
		tagSetPanel.add(lblUserDataIncrVal);

		txtUserDataIncrVal = new JTextField();
		txtUserDataIncrVal.setBounds(480, 140, 50, 30);
		txtUserDataIncrVal.setDocument(new TextDocument(0, 0));
		txtUserDataIncrVal.setEnabled(false);
		tagSetPanel.add(txtUserDataIncrVal);

		ButtonGroup group = new ButtonGroup();

		rdbtnSequence = new JRadioButton(BaseMessages
				.getString("C6_Dynamic.radioButton1"));
		rdbtnSequence.setBounds(10, 210, 60, 30);
		tagSetPanel.add(rdbtnSequence);

		rdbtnRandom = new JRadioButton(BaseMessages
				.getString("C6_Dynamic.radioButton2"));
		rdbtnRandom.setBounds(70, 210, 60, 30);
		rdbtnRandom.setSelected(true);
		tagSetPanel.add(rdbtnRandom);

		group.add(rdbtnSequence);
		group.add(rdbtnRandom);
		switchInputEnable(false);
		rdbtnSequence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snEpcLength.setValue(0);
				snUserDataLength.setValue(0);
				if (chkEpc.isSelected()) {
					snEpcLength.setEnabled(false);
					lblLength.setEnabled(false);
					txtEpcInitVal.setEnabled(true);
					lblInit.setEnabled(true);
					lblIncr.setEnabled(true);
					txtEpcIncrVal.setEnabled(true);
				}
				if (chkUserData.isSelected()) {
					lblStartIndex.setEnabled(true);
					snUserDataStartIndex.setEnabled(true);
					lblUserDataLength.setEnabled(false);
					snUserDataLength.setEnabled(false);
					chkChangeLong.setEnabled(true);
					lblUserDataInitVal.setEnabled(true);
					txtUserDataInitVal.setEnabled(true);
					lblUserDataIncrVal.setEnabled(true);
					txtUserDataIncrVal.setEnabled(true);
				}
			}
		});

		rdbtnRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snEpcLength.setEnabled(false);
				lblLength.setEnabled(false);
				txtEpcInitVal.setEnabled(false);
				lblInit.setEnabled(false);
				lblIncr.setEnabled(false);
				txtEpcIncrVal.setEnabled(false);

				lblStartIndex.setEnabled(false);
				snUserDataStartIndex.setEnabled(false);
				lblUserDataLength.setEnabled(false);
				snUserDataLength.setEnabled(false);
				chkChangeLong.setEnabled(false);
				lblUserDataInitVal.setEnabled(false);
				txtUserDataInitVal.setEnabled(false);
				lblUserDataIncrVal.setEnabled(false);
				txtUserDataIncrVal.setEnabled(false);
				if (chkEpc.isSelected()) {
					snEpcLength.setEnabled(true);
					lblLength.setEnabled(true);
				}
				if (chkUserData.isSelected()) {
					lblStartIndex.setEnabled(true);
					snUserDataStartIndex.setEnabled(true);
					lblUserDataLength.setEnabled(true);
					snUserDataLength.setEnabled(true);
					chkChangeLong.setEnabled(true);
				}
				snEpcLength.setValue(6);
				snUserDataLength.setValue(8);
			}
		});

		lblPwd = new JLabel(BaseMessages.getString("C6_Dynamic.label4"));
		lblPwd.setBounds(240, 210, 100, 30);
		tagSetPanel.add(lblPwd);
		txtPwd = new JPasswordField();
		txtPwd.setDocument(new TextDocument(8, 0));
		txtPwd.setBounds(350, 210, 100, 30);
		txtPwd.setText("000000");
		tagSetPanel.add(txtPwd);

		lblRWInterval = new JLabel(BaseMessages.getString("C6_Dynamic.label2"));
		lblRWInterval.setBounds(10, 240, 80, 30);
		tagSetPanel.add(lblRWInterval);
		snRWInterval = new JSpinner(new SpinnerNumberModel(500, 0, 10000, 1));
		snRWInterval.setBounds(70, 245, 65, 20);
		snRWInterval.getEditor().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				try {
					snRWInterval.commitEdit();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					MessageDialog.showInfoMessage(C6_DynamicPanel.this,
							BaseMessages.getString("Message.MSG_176"));

				}
			}

		});

		tagSetPanel.add(snRWInterval);
		lblUnit = new JLabel("ms");
		lblUnit.setBounds(140, 240, 20, 30);
		tagSetPanel.add(lblUnit);

		lblWriteTime = new JLabel(BaseMessages.getString("C6_Dynamic.label8"));
		lblWriteTime.setBounds(170, 240, 100, 30);
		tagSetPanel.add(lblWriteTime);

		snWriteTime = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
		snWriteTime.setBounds(270, 245, 45, 20);
		tagSetPanel.add(snWriteTime);

		// btnClean = new JButton(BaseMessages.getString("C6_Dynamic.button3"));
		// btnClean.setBounds(320, 242, 60, 27);

		// btnClean.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
		// .getModel();
		// defaultTableModel.setRowCount(0);
		// row = 0;
		// failLst.clear();
		// successLst.clear();
		// }
		// });
		// tagSetPanel.add(btnClean);

		btnStart = new JButton(BaseMessages.getString("C6_Dynamic.button1"));
		btnStart.setBounds(390, 242, 60, 27);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
						.getModel();
				defaultTableModel.setRowCount(0);
				row = 0;
				failLst.clear();
				successLst.clear();
				startAction();

			}
		});

		tagSetPanel.add(btnStart);
		btnStop = new JButton(BaseMessages.getString("C6_Dynamic.button2"));
		btnStop.setBounds(460, 242, 60, 27);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopAction();
			}

		});
		tagSetPanel.add(btnStop);

	}

	/**
	 * 重写父类的createTable，使用属性文件中的centerTable.dynamicreadwrite作为表格的column
	 */
	@Override
	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "centerTable.dynamicreadwrite");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();

		UserConfig_IRP1 userConfig = currentDemo.getConfig_IRP1();
		rmb = userConfig.getRmb();
		readerType = userConfig.getReaderType();
		q = (byte) Util.convertTagNumToQ(userConfig.getTagNum());

		IMessage message = null;
		if ("800".equals(userConfig.getReaderType())) {

			if (userConfig.getAntennaIndex() >= 0x80) {
				antenna = (byte) (userConfig.getAntennaIndex());
			} else {
				antenna = (byte) (userConfig.getAntennaIndex() + 1);
			}

			short a = (short) (antenna & 0XFF);
			if (a < 0x80) {
				if (a > 4) {
					a -= 4;
					antenna = 0;
				}
			}
			message = new SysConfig_800((byte) 0x02, new byte[] { (byte) a });
		} else if ("500".equals(userConfig.getReaderType())) {
			byte a = antenna = (byte) (userConfig.getAntennaIndex() + 1);
			if (antenna == 3)
				antenna = 0;
			message = new SysConfig_800((byte) 0x02, new byte[] { a });
		}
		if (message != null)
			currentDemo.getReader().send(message, 300);

	}

	private void switchInputEnable(boolean enabled) {
		snEpcLength.setEnabled(enabled);
		txtEpcInitVal.setEnabled(enabled);
		txtEpcIncrVal.setEnabled(enabled);
		// txtUserDataInitVal.setEnabled(enabled);
		// txtUserDataIncrVal.setEnabled(enabled);
	}

	private void switchAllEnable(boolean enabled) {
		boolean isSequence = rdbtnSequence.isSelected();

		chkEpc.setEnabled(enabled);
		lblLength.setEnabled(enabled);
		snEpcLength.setEnabled(enabled);
		lblInit.setEnabled(enabled);
		txtEpcInitVal.setEnabled(enabled && isSequence);
		lblIncr.setEnabled(enabled);
		txtEpcIncrVal.setEnabled(enabled && isSequence);

		chkUserData.setEnabled(enabled);
		lblStartIndex.setEnabled(enabled);
		snUserDataStartIndex.setEnabled(enabled);
		lblUserDataLength.setEnabled(enabled);
		snUserDataLength.setEnabled(enabled);
		chkChangeLong.setEnabled(enabled);

		lblUserDataInitVal.setEnabled(enabled);
		// txtUserDataInitVal.setEnabled(enabled && isSequence);
		// lblUserDataIncrVal.setEnabled(enabled);
		txtUserDataIncrVal.setEnabled(enabled && isSequence);

		rdbtnSequence.setEnabled(enabled);
		rdbtnRandom.setEnabled(enabled);

		lblPwd.setEnabled(enabled);
		txtPwd.setEnabled(enabled);

		lblRWInterval.setEnabled(enabled);
		snRWInterval.setEnabled(enabled);
		lblUnit.setEnabled(enabled);
		lblWriteTime.setEnabled(enabled);
		snWriteTime.setEnabled(enabled);
	}

	private void stopAction() {
		super.removeReader();
		currentDemo.stopRead();
		isStop = true;
		switchAllEnable(true);
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);

		/* xusheng 2012 4.23 */
		ConfigurationDialog.isOperatting = false;
		/* xusheng 2012 4.23 */
	}

	private void startAction() {

		if (rdbtnSequence.isSelected()) {
			if (chkEpc.isSelected()) {
				String epc = txtEpcInitVal.getText().trim();
				epc = epc.replace(" ", "");
				String epcIndex = txtEpcIncrVal.getText().trim();
				epcIndex = epcIndex.replace(" ", "");
				Integer length = (Integer) snEpcLength.getValue();

				if (epc.length() == 0) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_192"));
					return;
				}

				if (epcIndex.equals("")) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_38"));
					return;
				}
				if (epcIndex.length() > epc.length()) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_39"));
					return;
				}
			}
			if (chkUserData.isSelected()) {
				String userdata = txtUserDataInitVal.getText().trim();
				String userdataIndex = txtUserDataIncrVal.getText().trim();
				Integer userDataLength = (Integer) snUserDataLength.getValue();

				if (userdata.length() == 0) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_37"));
					return;
				}

				if (userdataIndex.equals("")) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_38"));
					return;
				}
				if (userdataIndex.length() > userdata.length()) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_39"));
					return;
				}
			}
			if (!"".equals(txtEpcInitVal.getText().trim())) {
				if (!Util.isHexString(txtEpcInitVal.getText().trim())) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_40"));
					return;
				}
			}
			if (!"".equals(txtEpcIncrVal.getText().trim())) {
				if (!Util.isHexString(txtEpcIncrVal.getText().trim())) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_40"));
					return;
				}
			}
			if (!"".equals(txtUserDataInitVal.getText().trim())) {
				if (!Util.isHexString(txtUserDataInitVal.getText().trim())) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_40"));
					return;
				}
			}
			if (!"".equals(txtUserDataIncrVal.getText().trim())) {
				if (!Util.isHexString(txtUserDataIncrVal.getText().trim())) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_40"));
					return;
				}
			}
		}

		// Common.Dt_C6_Dynamic.Rows.Clear();
		/* xusheng 2012 4.23 */
		ConfigurationDialog.isOperatting = true;
		// TODO: 读卡时禁用某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().lockNavigatorPanel();
		/* xusheng 2012 4.23 */

		isStop = false;
		isUnfix = chkChangeLong.isSelected();
		epcLen = (Integer) snEpcLength.getValue();
		userdataLen = (Integer) snUserDataLength.getValue();
		userdataPtr = (Integer) snUserDataStartIndex.getValue();
		wCount = (Integer) snWriteTime.getValue();
		sleepTime = (Integer) snRWInterval.getValue();
		pwd = Util.getPwd(txtPwd.getText());

		if (rdbtnSequence.isSelected()) {
			if (chkEpc.isSelected())
				wEpcData = getWriteData(txtEpcInitVal.getText().trim());
			if (chkUserData.isSelected())
				wUserData = getWriteData(txtUserDataInitVal.getText().trim());
		}
		Thread t = new Thread() {
			@Override
			public void run() {
				doTest();
			}
		};
		t.start();
		switchAllEnable(false);
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	}

	private void doTest() {

		while (!isStop) {
			ReadTag msg = new ReadTag(rmb, true);
			msg.setAntenna(antenna);
			msg.setQ((byte) 2);
			msg.setLoop(false);

			boolean isReadSuc = currentDemo.getReader().send(msg, 3000);
			@SuppressWarnings("unused")
			byte wAntenna = 0x01;

			MemoryBank sb = MemoryBank.EPCMemory;

			String tagType = "";
			String epc = "";
			String tid = "";
			String userdata = "";
			String rssi = "";

			byte[] tidBuff = null;
			byte[] epcBuff = null;
			byte[] idBytes = null;
			if (isReadSuc) {
				/* xusheng 2012.4.23 */
				String readTagTime = Common.getSystemTime();

				if (msg.getReceivedMessage() == null) {
					continue;
				}
				;

				/* xusheng 2012.4.23 */

				if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.EPCMemory;
						tagType = "6C";
						epcBuff = m.getReceivedMessage().getEPC();

						if (epcBuff.length >= 2) {
							if (currentDemo.getConfig().getReaderType().equals(
									"500")) {
								byte[] t_epc = new byte[epcBuff.length - 2];
								System.arraycopy(epcBuff, 0, t_epc, 0,
										t_epc.length);

								rssi = Util
										.convertByteToHexWordString(epcBuff[epcBuff.length - 2])
										+ Util
												.convertByteToHexWordString(epcBuff[epcBuff.length - 1]);
								epcBuff = t_epc;
							} else {
								if (currentDemo.isUtcEnable_800()) {
									byte[] t_epc = new byte[epcBuff.length - 8];
									System.arraycopy(epcBuff, 0, t_epc, 0,
											t_epc.length);
									epcBuff = t_epc;
								}

								if (currentDemo.isRssiEnable_800()) {
									byte[] t_epc = new byte[epcBuff.length - 2];
									System.arraycopy(epcBuff, 0, t_epc, 0,
											t_epc.length);
									rssi = Util
											.convertByteToHexWordString(epcBuff[epcBuff.length - 2]);
									epcBuff = t_epc;
								}
							}
						}
						epc = Util.convertByteArrayToHexWordString(epcBuff);
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.EPCMemory;
						tagType = "6C";
						epcBuff = m.getReceivedMessage().getEPC();
						epc = Util.convertByteArrayToHexWordString(epcBuff);
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.EPCMemory;
						tagType = "6C";
						epcBuff = m.getReceivedMessage().getEPC();
						if (epcBuff.length >= 2 && readerType.equals("800")
								&& currentDemo.isRssiEnable_800()) {
							byte[] t = new byte[epcBuff.length - 2];
							System.arraycopy(epcBuff, 0, t, 0, t.length);
							rssi = Util
									.convertByteToHexWordString(epcBuff[epcBuff.length - 2]);
							epcBuff = t;
						}
						epc = Util.convertByteArrayToHexWordString(epcBuff);
						if (m.getReceivedMessage().getTID() != null
								&& m.getReceivedMessage().getTID().length > 0) {
							tidBuff = m.getReceivedMessage().getTID();
							tid = Util.convertByteArrayToHexWordString(tidBuff);
							sb = MemoryBank.TIDMemory;
						}
						if (m.getReceivedMessage().getUserData() != null
								&& m.getReceivedMessage().getUserData().length > 0) {
							userdata = Util.convertByteArrayToHexWordString(m
									.getReceivedMessage().getUserData());
						}
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.EPCMemory;
						tagType = "6C";
						epcBuff = m.getReceivedMessage().getEPC();
						if (epcBuff.length >= 2 && readerType.equals("800")
								&& currentDemo.isRssiEnable_800()) {
							byte[] t = new byte[epcBuff.length - 2];
							System.arraycopy(epcBuff, 0, t, 0, t.length);
							rssi = Util
									.convertByteToHexWordString(epcBuff[epcBuff.length - 2]);
							epcBuff = t;
						}
						epc = Util.convertByteArrayToHexWordString(epcBuff);
						if (m.getReceivedMessage().getTID() != null
								&& m.getReceivedMessage().getTID().length > 0) {
							tidBuff = m.getReceivedMessage().getTID();
							tid = Util.convertByteArrayToHexWordString(tidBuff);
							sb = MemoryBank.TIDMemory;
						}
						if (m.getReceivedMessage().getUserData() != null
								&& m.getReceivedMessage().getUserData().length > 0)
							userdata = Util.convertByteArrayToHexWordString(m
									.getReceivedMessage().getUserData());
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						tagType = "6B";
						idBytes = m.getReceivedMessage().getTID();
						tid = Util.convertByteArrayToHexWordString(idBytes);

						if (m.getReceivedMessage().getRSSI() != null
								&& m.getReceivedMessage().getRSSI().length > 0) {
							rssi = Util.convertByteArrayToHexString(m
									.getReceivedMessage().getRSSI());
						}
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						tagType = "6B";
						idBytes = m.getReceivedMessage().getTID();
						tid = Util.convertByteArrayToHexWordString(idBytes);
						userdata = Util.convertByteArrayToHexWordString(m
								.getReceivedMessage().getUserData());
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.TIDMemory;
						tagType = "6C";
						tidBuff = m.getReceivedMessage().getTID();

						if (currentDemo.getConfig().getReaderType().equals(
								"500")) {
							byte[] t_tid = new byte[tidBuff.length - 2];
							System
									.arraycopy(tidBuff, 0, t_tid, 0,
											t_tid.length);
							tidBuff = t_tid;
							rssi = Util
									.convertByteToHexWordString(tidBuff[tidBuff.length - 2])
									+ Util
											.convertByteToHexWordString(tidBuff[tidBuff.length - 1]);
							tid = Util.convertByteArrayToHexWordString(t_tid);
						} else {
							tid = Util.convertByteArrayToHexWordString(tidBuff);
						}

						if (readerType.equals("500")) {
							rssi = tid.substring(tid.length() - 4);
							tid = tid.substring(0, tid.length() - 4);
						}
					} else {
						continue;
					}
				} else if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.TIDMemory;
						tagType = "6C";
						tidBuff = m.getReceivedMessage().getTID();
						tid = Util.convertByteArrayToHexString(tidBuff);
					} else {
						continue;
					}

				} else {
					continue;
				}

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {

				}

				boolean isAdd = true;
				synchronized (objLock) {
					int rowCount = centerTable.getRowCount();
					if (rowCount > 0) {
						for (int i = 0; i < rowCount; i++) {
							if (epc != null && !"".equals(epc)) {
								String rowEpc = (String) centerTable
										.getValueAt(i, 1);
								if (epc.equals(rowEpc)) {
									isAdd = false;
								}
							} else if (tid != null && !"".equals(tid)) {
								String rowTid = (String) centerTable
										.getValueAt(i, 2);
								if (tid.equals(rowTid)) {
									isAdd = false;
								}
							} else {
								return;
							}

							if (!isAdd) {
								break;
							}
						}
					}
					antenna = wAntenna;

					boolean wEpc = false;
					boolean wUd = false;
					boolean isSuc = false;
					if (isAdd) {
						for (int i = 0; i < wCount; i++) {
							IMessage wmsg = null;

							if (rdbtnRandom.isSelected()) {
								if (chkEpc.isSelected())
									wEpcData = createRandomData(epcLen);
								if (chkUserData.isSelected())
									wUserData = createRandomData(userdataLen);
							}

							if ("6B".equals(tagType)
									&& chkUserData.isSelected()) {
								if (isUnfix) {
									wmsg = new WriteUserData2_6B(antenna,
											idBytes, (byte) userdataPtr,
											wUserData);
								} else {
									wmsg = new WriteUserData_6B(antenna,
											idBytes, (byte) userdataPtr,
											wUserData);

								}
							} else if ("6C".equals(tagType)) {
								byte[] tagID = null;
								if (sb == MemoryBank.EPCMemory
										&& epcBuff != null) {
									tagID = Util
											.convertHexStringToByteArray(epc);
								} else if (sb == MemoryBank.TIDMemory
										&& tidBuff != null) {
									tagID = tidBuff;
								}
								if (tagID != null) {
									if (chkEpc.isSelected() && !wEpc) {
										wmsg = new WriteEpc(antenna, pwd,
												wEpcData, tagID, sb);
										if (currentDemo.getReader().send(wmsg)) {
											epc = Util
													.convertByteArrayToHexWordString(wEpcData);
											wEpc = true;
											if (rdbtnSequence.isSelected()) {
												StringBuilder sb1 = new StringBuilder();
												if (txtEpcIncrVal.getText()
														.trim().length() % 2 != 0) {
													sb1.append('0');
													sb1.append(txtEpcIncrVal
															.getText().trim());
												} else {
													sb1.append(txtEpcIncrVal
															.getText().trim());
												}
												wEpcData = bytesAdd(
														wEpcData,
														Util
																.convertHexStringToByteArray(sb1
																		.toString()));
											}
											tagID = wEpcData;
										} else {
											// Util
											// .logAndTriggerApiErr(
											// currentDemo
											// .getDemoName(),
											// String
											// .format(
											// "%1$02X",
											// wmsg
											// .getStatusCode()),
											// "", LogType.Error);
										}
									}
									if (chkUserData.isSelected()) {
										wmsg = new WriteUserData_6C(antenna,
												pwd, (byte) userdataPtr,
												wUserData, tagID, sb);
									}
								}
							}

							if (chkUserData.isSelected() && !wUd) {
								if (currentDemo.getReader().send(wmsg)) {
									userdata = Util
											.convertByteArrayToHexWordString(wUserData);
									wUd = true;
									if (rdbtnSequence.isSelected()) {
										StringBuilder sb1 = new StringBuilder();
										if (txtUserDataIncrVal.getText().trim()
												.length() % 2 != 0) {
											sb1.append('0');
											sb1.append(txtUserDataIncrVal
													.getText().trim());
										} else {
											sb1.append(txtUserDataIncrVal
													.getText().trim());
										}
										wUserData = bytesAdd(
												wUserData,
												Util
														.convertHexStringToByteArray(sb1
																.toString()));
									}
								} else {
									// Util.logAndTriggerApiErr(currentDemo
									// .getDemoName(), String.format(
									// "%1$02X", wmsg.getStatusCode()),
									// "", LogType.Error);
								}
							}
							if (chkEpc.isSelected()) {
								isSuc = wEpc;
								if (chkUserData.isSelected() && isSuc) {
									isSuc = wUd;
								}
							} else {
								isSuc = chkUserData.isSelected() && wUd;
							}

							if (isSuc) {
								break;
							}
						}
						if (isSuc) {
							successLst.add(row++);
						} else {
							failLst.add(row++);
						}
						if (isAdd) {
							displayDataGridView("", tagType, epc, tid,
									userdata, antenna, rssi, isSuc, readTagTime);
						}

						synchronized (centerTable) {
							makeFace(centerTable);
						}
					}
				}
			}
		}

	}

	private void displayDataGridView(String string, String tagType, String epc,
			String tid, String userdata, byte antenna2, String rssi,
			Boolean isSuc, String readTagTime) {
		addTag("", tagType, epc, tid, userdata, antenna2, rssi, readTagTime);
	}

	@Override
	public void addTag(String readerName, String tagType, String epc,
			String tid, String userData, int antenna, String rssi,
			String readTagTime) {
		synchronized (centerTable) {
			boolean isExist = false;
			DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
					.getModel();
			if (!isExist) {
				String[] rowData = new String[defaultTableModel
						.getColumnCount()];
				rowData[0] = tagType;
				rowData[1] = epc;
				rowData[2] = tid;
				rowData[3] = userData;
				rowData[4] = readTagTime;

				defaultTableModel.addRow(rowData);
				Common.myBeep();
			}
		}
	}

	private byte[] bytesAdd(byte[] bytes, byte[] index) {
		byte[] b = new byte[bytes.length];
		System.arraycopy(bytes, 0, b, 0, b.length);
		int pre = 0;
		for (int i = 0; i < index.length; i++) {
			int bs = bytes[bytes.length - i - 1] + index[index.length - i - 1]
					+ pre;
			pre = bs / 256;
			b[b.length - i - 1] = (byte) (bs % 256);
		}
		int p = index.length;
		if (pre > 0 && b.length > p) {
			while (b[b.length - p - 1] == 0xff) {
				b[b.length - p - 1] = 0;
				p++;
				if (b.length <= p)
					break;
			}
			if (b.length > p)
				b[b.length - p - 1] += 1;
		}
		return b;
	}

	private byte[] createRandomData(int len) {
		// 创建一个随机字节数组
		byte[] writeData = new byte[len * 2];
		Random random = new Random(new Date().getTime());
		for (int i = 0; i < writeData.length; i++) {
			writeData[i] = (byte) random.nextInt(256);
		}
		return writeData;
	}

	private byte[] getWriteData(String str) {
		byte[] data = Util.convertHexStringToByteArray(str);
		if (data.length % 2 == 1) {
			byte[] d = new byte[data.length + 1];
			System.arraycopy(data, 0, d, 0, data.length);
			return d;
		}
		return data;
	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,
			invengo.javaapi.core.IMessageNotification msg) {
	}

	public void makeFace(JTable table) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {

					if (successLst.contains(row)) {
						setBackground(Color.green);
					} else if (failLst.contains(row)) {
						setBackground(Color.red);
					} else {
						setBackground(Color.white);
					}
					return super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			table.getSelectionModel().clearSelection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
