package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.Antenna;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.ReadTag;
import invengo.javaapi.protocol.IRP1.ReadUserData2_6B;
import invengo.javaapi.protocol.IRP1.ReadUserData_6B;
import invengo.javaapi.protocol.IRP1.ReadUserData_6C;
import invengo.javaapi.protocol.IRP1.SelectTag_6C;
import invengo.javaapi.protocol.IRP1.WriteEpc;
import invengo.javaapi.protocol.IRP1.WriteUserData2_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6C;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.panel.AbstractReaderDataTable;
import com.invengo.xcrf.ui.panel.MainDemoPanel;

public class C4_TagRWPanel extends AbstractReaderDataTable {
	private static final long serialVersionUID = -1020525587220852519L;
	private JTextField txtTagID;
	private JButton btnStop;
	private JButton btnScan;
	private JPasswordField txtPwd;
	private JButton btnTest;
	private JButton btnStopTest;
	private JLabel lblDoneCount;
	private JPanel tagSetPanel;
	private JPanel testResultPanel;
	private JLabel lblSuccessCount;
	private JLabel lblSuccessPercentage;
	private JLabel lblTime;
	private JLabel lblAverageTime;
	private JSpinner num_DataLength;
	private JSpinner num_TestCount;
	private boolean isTesting;// 在测试中
	private JPanel panel;
	private int doneCount = 0;
	private int succCount = 0;
	private JComboBox dataTypeCbo;
	private MemoryBank mb;
	private byte antenna;
	private long startTime;
	private String tagType;
	private static final int USERDATA = 0;
	private static final int EPC = 1;
	private JSpinner num_Ptr;
	private JTextArea txtWrite;
	private JTextArea txtRead;
	private JCheckBox cbWrite;
	private JCheckBox cbRead;
	private boolean isUnfix;
	private JCheckBox checkBox;
	private JLabel label_1;

	/**
	 * Create the panel.
	 */
	public C4_TagRWPanel() {
		super();
		setLayout(null);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(10, 0, 620, 162);
		add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(centerTable);
		scrollPane.setBounds(10, 0, 600, 128);

		panel.add(scrollPane);

		btnScan = new JButton(BaseMessages.getString("C4_TagRW.btn_Scan"));
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startAndStop(true);
			}
		});
		btnScan.setBounds(10, 129, 65, 23);
		panel.add(btnScan);

		btnStop = new JButton(BaseMessages.getString("C4_TagRW.btn_StopScan"));
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startAndStop(false);
			}
		});
		btnStop.setBounds(85, 129, 65, 23);
		panel.add(btnStop);

		txtTagID = new JTextField();
		txtTagID.setBounds(161, 130, 298, 21);
		panel.add(txtTagID);
		txtTagID.setColumns(10);

		btnTest = new JButton(BaseMessages.getString("C4_TagRW.btn_Test"));
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testClick();
			}
		});
		btnTest.setBounds(470, 129, 65, 23);
		panel.add(btnTest);

		btnStopTest = new JButton(BaseMessages
				.getString("C4_TagRW.btn_StopTest"));
		btnStopTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTest();
			}
		});
		btnStopTest.setBounds(545, 129, 65, 23);
		panel.add(btnStopTest);

		tagSetPanel = new JPanel();
		tagSetPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("C4_TagRW.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLACK));
		tagSetPanel.setBounds(10, 172, 240, 162);
		add(tagSetPanel);
		tagSetPanel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("C4_TagRW.label1"));
		label.setBounds(10, 23, 114, 15);
		tagSetPanel.add(label);

		dataTypeCbo = new JComboBox();
		// dataTypeCbo.setEnabled(false);
		dataTypeCbo.setModel(new DefaultComboBoxModel(new String[] {
				"UserData", "EPC" }));
		dataTypeCbo.setSelectedIndex(0);
		dataTypeCbo.setBounds(134, 21, 96, 21);
		tagSetPanel.add(dataTypeCbo);

		label_1 = new JLabel(BaseMessages.getString("C4_TagRW.label5"));
		label_1.setBounds(10, 47, 114, 15);
		tagSetPanel.add(label_1);

		txtPwd = new JPasswordField();
		txtPwd.setDocument(new TextDocument(8, 0));
		txtPwd.setBounds(134, 47, 96, 21);
		txtPwd.setText("00000000");
		tagSetPanel.add(txtPwd);
		txtPwd.setColumns(10);

		final JLabel lbll = new JLabel(BaseMessages
				.getString("C4_TagRW.label6"));
		lbll.setBounds(10, 76, 114, 15);
		tagSetPanel.add(lbll);

		num_Ptr = new JSpinner();
		num_Ptr.setModel(new SpinnerNumberModel(0, 0, 512, 0));
		num_Ptr.setBounds(134, 73, 96, 22);
		tagSetPanel.add(num_Ptr);

		dataTypeCbo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (((String) (dataTypeCbo.getSelectedItem())).equals("EPC")) {
					num_Ptr.setValue(0);
					num_Ptr.setEnabled(false);
					lbll.setEnabled(false);
				} else {
					num_Ptr.setEnabled(true);
					lbll.setEnabled(true);
				}
			}

		});

		JLabel label_2 = new JLabel(BaseMessages.getString("C4_TagRW.label2"));
		label_2.setBounds(10, 105, 114, 15);
		tagSetPanel.add(label_2);

		final JLabel label_3 = new JLabel(BaseMessages
				.getString("C4_TagRW.label3"));
		label_3.setBounds(10, 134, 114, 15);
		tagSetPanel.add(label_3);

		num_DataLength = new JSpinner();
		num_DataLength.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(1), null, new Integer(1)));
		num_DataLength.setBounds(134, 100, 96, 22);
		tagSetPanel.add(num_DataLength);

		num_TestCount = new JSpinner();
		num_TestCount.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), new Integer(10000000), new Integer(1)));
		num_TestCount.setBounds(134, 127, 96, 22);
		tagSetPanel.add(num_TestCount);

		checkBox = new JCheckBox();
		checkBox.setVisible(false);
		checkBox.setBounds(127, 43, 103, 23);
		tagSetPanel.add(checkBox);

		testResultPanel = new JPanel();
		testResultPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("C4_TagRW.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLACK));
		testResultPanel.setBounds(10, 331, 240, 149);
		add(testResultPanel);
		testResultPanel.setLayout(null);

		JLabel label_4 = new JLabel(BaseMessages.getString("C4_TagRW.label4"));
		label_4.setBounds(10, 23, 114, 15);
		testResultPanel.add(label_4);

		JLabel label_5 = new JLabel(BaseMessages.getString("C4_TagRW.label7"));
		label_5.setBounds(10, 48, 114, 15);
		testResultPanel.add(label_5);

		JLabel label_6 = new JLabel(BaseMessages.getString("C4_TagRW.label9"));
		label_6.setBounds(10, 73, 114, 15);
		testResultPanel.add(label_6);

		JLabel label_7 = new JLabel(BaseMessages.getString("C4_TagRW.label10"));
		label_7.setBounds(10, 98, 114, 15);
		testResultPanel.add(label_7);

		JLabel label_8 = new JLabel(BaseMessages.getString("C4_TagRW.label13"));
		label_8.setBounds(10, 123, 114, 15);
		testResultPanel.add(label_8);

		lblDoneCount = new JLabel("0");
		lblDoneCount.setBounds(134, 23, 54, 15);
		testResultPanel.add(lblDoneCount);

		lblSuccessCount = new JLabel("0");
		lblSuccessCount.setBounds(134, 48, 54, 15);
		testResultPanel.add(lblSuccessCount);

		lblSuccessPercentage = new JLabel("0");
		lblSuccessPercentage.setBounds(134, 73, 54, 15);
		testResultPanel.add(lblSuccessPercentage);

		lblTime = new JLabel("0");
		lblTime.setBounds(134, 98, 54, 15);
		testResultPanel.add(lblTime);

		lblAverageTime = new JLabel("0");
		lblAverageTime.setBounds(134, 123, 88, 15);
		testResultPanel.add(lblAverageTime);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("C4_TagRW.groupBox3"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLACK));
		panel_3.setBounds(260, 172, 370, 149);
		add(panel_3);
		panel_3.setLayout(null);

		cbWrite = new JCheckBox("");
		cbWrite.setSelected(true);
		cbWrite.setBounds(69, 0, 21, 21);
		panel_3.add(cbWrite);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 27, 348, 112);
		panel_3.add(scrollPane_1);

		txtWrite = new JTextArea();
		txtWrite.setLineWrap(true);
		scrollPane_1.setViewportView(txtWrite);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("C4_TagRW.g_data"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLACK));
		panel_4.setBounds(260, 331, 370, 149);
		add(panel_4);
		panel_4.setLayout(null);

		cbRead = new JCheckBox("");
		cbRead.setSelected(true);
		cbRead.setBounds(68, 0, 21, 21);
		panel_4.add(cbRead);
		cbRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (cbRead.isSelected() && !cbWrite.isSelected()) {
					num_DataLength.setEnabled(false);
					label_3.setEnabled(false);
				} else {
					num_DataLength.setEnabled(true);
					label_3.setEnabled(true);
				}
			}

		});
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 27, 348, 112);
		panel_4.add(scrollPane_2);

		txtRead = new JTextArea();
		txtRead.setLineWrap(true);
		scrollPane_2.setViewportView(txtRead);

		centerTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						int row = centerTable.getSelectedRow();
						if (row != -1) {
							DefaultTableModel model = (DefaultTableModel) centerTable
									.getModel();
							String id = model.getValueAt(row, 3).toString();
							txtTagID.setText(id);
							if (model.getValueAt(row, 2) != null
									&& !model.getValueAt(row, 2).toString()
											.equals("")) {
								mb = MemoryBank.EPCMemory;
							}
							// else
							if (model.getValueAt(row, 3) != null
									&& !model.getValueAt(row, 3).toString()
											.equals("")) {
								mb = MemoryBank.TIDMemory;
							}
							// else if (model.getValueAt(row, 4) != null
							// && !model.getValueAt(row, 4).toString()
							// .equals("")) {
							// mb = MemoryBank.UserMemory;
							// }
							tagType = model.getValueAt(row, 1).toString();
							if (tagType.equals("6B")) {
								txtPwd.setVisible(false);
								checkBox.setVisible(true);
								label_1.setText(BaseMessages
										.getString("C4_TagRW.label8"));
							} else {
								txtPwd.setVisible(true);
								checkBox.setVisible(false);
								label_1.setText(BaseMessages
										.getString("C4_TagRW.label5"));
							}
							antenna = getAntenna(model, row);
						}
					}
				});
		centerTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (centerTable.getValueAt(centerTable.getSelectedRow(), 3)
						.toString().equals(""))
					txtTagID.setText(centerTable.getValueAt(
							centerTable.getSelectedRow(), 2).toString());
				else
					txtTagID.setText(centerTable.getValueAt(
							centerTable.getSelectedRow(), 3).toString());

			}
		});
	}

	private byte getAntenna(DefaultTableModel model, int row) {
		byte an = 0x00;
		byte[] ans = new byte[] { Antenna._ONE, Antenna._TWO, Antenna._THREE,
				Antenna._FOUR };
		for (int i = 0; i < ans.length; i++) {
			String d = model.getValueAt(row, 6 + i).toString();
			if (!"".equals(d) && !"0".equals(d)) {
				return ans[i];
			}
		}
		return an;
	}

	/**
	 * 
	 * @param isStart
	 */
	private void startAndStop(boolean isStart) {
		if (isStart) {
			/* xusheng 2012 4.23 */
			ConfigurationDialog.isOperatting = true;
			/* xusheng 2012 4.23 */
			super.addReader();
			MainDemoPanel.getReadDataPanel().setRead(!isStart);
			((DefaultTableModel) (centerTable.getModel())).setRowCount(0);
			currentDemo.readTag();
			btnTest.setEnabled(false);
		} else {
			/* xusheng 2012 4.23 */
			ConfigurationDialog.isOperatting = false;
			/* xusheng 2012 4.23 */
			super.removeReader();
			currentDemo.stopRead();
			btnTest.setEnabled(true);

			//
			Utils.setPanelEnabled(tagSetPanel);
			if (mb == MemoryBank.EPCMemory) {
				dataTypeCbo.setEnabled(false);
				dataTypeCbo.setSelectedIndex(0);
			}
		}
		btnScan.setEnabled(!isStart);
		btnStop.setEnabled(isStart);
	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();
		num_DataLength.setValue(new Integer(2));
		num_TestCount.setValue(new Integer(10));
		txtWrite.setText("");
		txtRead.setText("");
		btnStopTest.setEnabled(false);
		txtTagID.setText("");
		// resetEnabled();
		Utils.setPanelDisabled(tagSetPanel);
	}

	private void testClick() {
		new Thread(new TestThread()).start();
	}

	class TestThread implements Runnable {

		public void run() {
			isTesting = true;
			btnTest.setEnabled(false);
			btnStopTest.setEnabled(true);
			startTime = System.currentTimeMillis();
			resetEnabled();
			int i = 0;
			int len = Integer.parseInt(num_DataLength.getValue().toString());
			int count = Integer.parseInt(num_TestCount.getValue().toString());
			if (len == 0 || count == 0) {
				MessageDialog.showInfoMessage(panel, BaseMessages
						.getString("C4_TagRW.settingerror"));
				isTesting = false;
				resetEnabled();
				return;
			} else if (txtTagID.getText().trim().equals("")) {
				MessageDialog.showInfoMessage(panel, BaseMessages
						.getString("C4_TagRW.notselect"));
				isTesting = false;
				resetEnabled();
				btnTest.setEnabled(true);
				btnStopTest.setEnabled(false);
				return;
			} else if (!cbWrite.isSelected() && !cbRead.isSelected()) {
				MessageDialog.showInfoMessage(panel, BaseMessages
						.getString("C4_TagRW.pleaseselect"));
				isTesting = false;
				resetEnabled();
				btnTest.setEnabled(true);
				btnStopTest.setEnabled(false);
				return;
			}
			boolean isSuccee = true;

			isUnfix = checkBox.isSelected();

			while (true) {
				if (!isTesting || i == count) {
					break;
				}
				String writeString = "";
				if (cbWrite.isSelected()) {
					byte[] wd = Utils.createRandomData(len);
					writeString = formatString(Util
							.convertByteArrayToHexWordString(wd));
					txtWrite.append((i + 1) + ": " + writeString + "\r\n");
					// 写数据
					isSuccee = write(wd);
				}
				if (cbRead.isSelected()) {
					if (cbWrite.isSelected()) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 读数据
					String readString = formatString(read());
					if (readString != null && isSuccee) {
						txtRead.append((i + 1) + ": " + readString + "\r\n");
					} else {
						isSuccee = false;
					}
					if (cbWrite.isSelected() && !writeString.equals(readString)) {
						isSuccee = false;
					}
				}
				display(isSuccee);
				i++;
			}
			isTesting = false;
			btnStop.setEnabled(false);
			btnScan.setEnabled(!isTesting);
			Utils.setPanelEnable(!isTesting, tagSetPanel);
			if (mb == MemoryBank.EPCMemory) {
				dataTypeCbo.setEnabled(false);
				dataTypeCbo.setSelectedIndex(0);
			}
			// 所用时间
			testEnd();
		}
	}

	private void stopTest() {
		synchronized (lock) {
			isTesting = false;
		}
		if (currentDemo.isReading()) {
			currentDemo.stopRead();
		}
		testEnd();
		this.dataTypeCbo.setEnabled(false);
	}

	private void testEnd() {
		long endTime = System.currentTimeMillis();
		long ts = endTime - startTime;
		lblTime.setText(ts + "ms");
		double avgTime = (double) ts / doneCount;
		lblAverageTime
				.setText(new DecimalFormat("#.##").format(avgTime) + "ms");
		btnStopTest.setEnabled(false);
		btnTest.setEnabled(true);
	}

	/**
	 * 重置状态
	 */
	private void resetEnabled() {
		doneCount = 0;
		succCount = 0;
		btnStop.setEnabled(false);
		btnScan.setEnabled(!isTesting);
		Utils.setPanelEnable(!isTesting, tagSetPanel);
		if (mb == MemoryBank.EPCMemory) {
			dataTypeCbo.setEnabled(false);
			dataTypeCbo.setSelectedIndex(0);
		}
		lblSuccessCount.setText("");
		lblSuccessPercentage.setText("");
		lblTime.setText("");
		lblAverageTime.setText("");
		lblDoneCount.setText("");
		txtRead.setText("");
		txtWrite.setText("");
		// this.dataTypeCbo.setEnabled(false);
	}

	private void display(boolean isSucc) {
		// 完成次数
		lblDoneCount.setText(++doneCount + "");
		if (isSucc) {
			succCount++;
			lblSuccessCount.setText(succCount + "");
		}
		double sp = ((double) succCount / (double) doneCount) * 100;
		lblSuccessPercentage.setText(sp + "%");
		long ts = System.currentTimeMillis() - startTime;
		this.lblTime.setText(ts + "ms");
	}

	private boolean write(byte[] data) {
		boolean flag = false;
		IMessage msg = null;

		int index = this.dataTypeCbo.getSelectedIndex();
		byte[] tagId = Util.convertHexStringToByteArray(txtTagID.getText());
		byte[] pwd = Utils.getPwd(String.copyValueOf(txtPwd.getPassword()));
		byte ptr = Byte.parseByte(this.num_Ptr.getValue().toString());
		switch (index) {
		case USERDATA:
			if ("6C".equals(tagType)) {
				msg = new WriteUserData_6C(antenna, pwd, ptr, data, Util
						.convertHexStringToByteArray(txtTagID.getText()), mb);
			} else if ("6B".equals(tagType)) {
				if (isUnfix)
					msg = new WriteUserData2_6B(antenna, tagId, ptr, data);
				else
					msg = new WriteUserData_6B(antenna, tagId, ptr, data);
			}

			break;
		case EPC:
			msg = new WriteEpc(antenna, pwd, data, Util
					.convertHexStringToByteArray(txtTagID.getText()), mb);
			break;
		}

		if (msg != null) {
			flag = currentDemo.getReader().send(msg);
		}

		if (!flag) {
			Util.logAndTriggerApiErr(currentDemo.getDemoName(), String.format(
					"%1$02X", msg.getStatusCode()), "", LogType.Error);
		}

		return flag;
	}

	private String read() {
		String flag = null;
		IMessage msg = null;

		int index = this.dataTypeCbo.getSelectedIndex();
		byte[] tagId = Util.convertHexStringToByteArray(txtTagID.getText());
		byte ptr = Byte.parseByte(this.num_Ptr.getValue().toString());
		byte length = Byte.parseByte(this.num_DataLength.getValue().toString());
		switch (index) {
		case USERDATA:
			if ("6C".equals(tagType)) {
				msg = new ReadUserData_6C(antenna, ptr, length, Util
						.convertHexStringToByteArray(txtTagID.getText()), mb);
			} else if ("6B".equals(tagType)) {
				if (isUnfix) {
					msg = new ReadUserData2_6B(antenna, tagId, ptr,
							(byte) (length * 2));
				} else {
					msg = new ReadUserData_6B(antenna, tagId, ptr, length);
				}
			}
			break;
		case EPC:
			byte[] tagID = Util.convertHexStringToByteArray(txtTagID.getText());
			msg = new SelectTag_6C(mb, (byte) 0x00, (byte) (tagID.length * 8),
					tagID);
			break;
		}
		if (msg != null) {
			if (currentDemo.getReader().send(msg)) {
				if (index == USERDATA) {
					String ud = "";
					if ("6C".equals(tagType)) {
						ud = Util
								.convertByteArrayToHexWordString(((ReadUserData_6C) msg)
										.getReceivedMessage().getUserData());
					} else if ("6B".equals(tagType)) {
						if (isUnfix) {
							ud = Util
									.convertByteArrayToHexWordString(((ReadUserData2_6B) msg)
											.getReceivedMessage().getUserData());
						} else {
							ud = Util
									.convertByteArrayToHexWordString(((ReadUserData_6B) msg)
											.getReceivedMessage().getUserData());
						}
					}

					if (length * 4 < ud.length())
						ud = ud.substring(0, length * 4);
					return ud;
				} else {
					String readerType = currentDemo.getConfig().getReaderType();
					ReadTag rTag = new ReadTag(ReadMemoryBank.EPC_6C, true);
					rTag.setLoop(false);
					rTag.setAntenna(antenna);
					if (currentDemo.getReader().send(rTag)) {
						if (rTag.getReceivedMessage() != null) {
							RXD_TagData[] ary = rTag.getReceivedMessage()
									.getList_RXD_TagData();
							if (ary != null && ary.length > 0) {
								if (ary[0].getReceivedMessage() == null)
									return flag;
								byte[] rd = ary[0].getReceivedMessage()
										.getEPC();

								if (currentDemo.isRssiEnable_800()) {
									byte[] t = new byte[rd.length - 2];
									System.arraycopy(rd, 0, t, 0, t.length);
									rd = t;
								}
								String epc = Util
										.convertByteArrayToHexWordString(rd);
								if (length * 4 < epc.length())
									epc = epc.substring(0, length * 4);
								return epc;
							}
						}
					} else {
						Util.logAndTriggerApiErr(currentDemo.getDemoName(),
								String.format("%1$02X", msg.getStatusCode()),
								"", LogType.Error);
					}
				}
			}
		} else {
			Util.logAndTriggerApiErr(currentDemo.getDemoName(), String.format(
					"%1$02X", msg.getStatusCode()), "", LogType.Error);
		}
		return flag;
	}

	private String formatString(String str) {
		// System.out.println("**************str:"+str.length()+"\t "+str);
		StringBuffer bu = new StringBuffer("       ");
		if (str != null && str.length() > 4) {
			int len = str.length() / 4;
			for (int i = 0; i < len; i++) {
				bu.append(str.substring(i * 4, (i + 1) * 4));
				bu.append("  ");
			}
			return bu.toString();
		} else {
			return str;
		}
	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,
			IMessageNotification msg) {
		if (MainDemoPanel.getReadDataPanel().isRead()) {
			return;
		}
		super.messageNotificationReceivedHandle(reader, msg);
	}
}
