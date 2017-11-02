package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.WriteEpc;
import invengo.javaapi.protocol.IRP1.WriteUserData2_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6C;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.TightForm;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.panel.AbstractReaderDataTable;
import com.invengo.xcrf.ui.panel.MainDemoPanel;

public class D3 extends AbstractReaderDataTable {
	private static final long serialVersionUID = 115429288553822605L;
	private JPasswordField txtPassWord;
	private JFileChooser fileChooser = new JFileChooser();
	private JLabel lblSelect;
	private JButton button;
	private JButton button_1;
	// private JButton button_2;
	private ReadMemoryBank rmb;
	private String readerType;
	private byte q;
	// private byte antenna;
	private boolean isStop;

	private volatile boolean isWrite;

	private boolean isStopWrite = false;
	private Queue<String> dataQueue = new LinkedList<String>();
	private List<String> dataBackup = new ArrayList<String>();
	private long sleepTime;

	private JLabel lblReadDataCount;
	private TightForm tightForm;
	private JCheckBox chkEpc;
	private JCheckBox chkUserdata;
	private JCheckBox chkChangeLength;
	private JSpinner snrReadInterval;
	private JSpinner spinner_3;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JSpinner spinner_2;
	private JButton btnStop;
	private JButton btnStart;

	public D3() {
		setLayout(null);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Text file", "txt");
		fileChooser.setFileFilter(filter);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("D3.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(27, 330, 585, 149);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("D3.label1"));
		label.setBounds(23, 29, 60, 15);
		panel.add(label);

		JButton btn_Browse = new JButton(BaseMessages
				.getString("D3.btntxtFile"));
		btn_Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doActionReadFile();
			}
		});
		btn_Browse.setBounds(82, 25, 34, 23);
		panel.add(btn_Browse);

		lblSelect = new JLabel(BaseMessages.getString("D.view"));
		lblSelect.setForeground(Color.red);
		lblSelect.setVisible(false);
		lblSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doActionSelectData();
			}
		});
		lblSelect.setBounds(244, 29, 24, 15);
		panel.add(lblSelect);

		JLabel label_3 = new JLabel(BaseMessages.getString("D3.label2"));
		label_3.setBounds(23, 54, 60, 15);
		panel.add(label_3);

		chkEpc = new JCheckBox(BaseMessages.getString("D3.cbkEpc"));
		chkEpc.setBounds(82, 50, 86, 23);
		chkEpc.setSelected(true);
		panel.add(chkEpc);

		chkUserdata = new JCheckBox(BaseMessages.getString("D3.cbkUserData"));
		chkUserdata.setBounds(82, 75, 86, 23);
		chkUserdata.setSelected(true);
		panel.add(chkUserdata);

		chkChangeLength = new JCheckBox(BaseMessages.getString("D3.cb_unfix"));
		chkChangeLength.setBounds(286, 108, 98, 23);
		chkChangeLength.setSelected(true);
		panel.add(chkChangeLength);

		JLabel lblc = new JLabel(BaseMessages.getString("D3.label4"));
		lblc.setBounds(306, 54, 95, 15);
		panel.add(lblc);

		txtPassWord = new JPasswordField();
		txtPassWord.setDocument(new TextDocument(8, 0));
		txtPassWord.setBounds(411, 51, 121, 21);
		txtPassWord.setText("00000000");
		panel.add(txtPassWord);

		JLabel label_4 = new JLabel(BaseMessages.getString("D3.label8"));
		label_4.setBounds(306, 27, 85, 15);
		panel.add(label_4);

		snrReadInterval = new JSpinner();
		snrReadInterval.setModel(new SpinnerNumberModel(new Integer(500), null,
				null, new Integer(1)));
		snrReadInterval.setBounds(411, 26, 43, 22);
		panel.add(snrReadInterval);

		JLabel lblMs = new JLabel(BaseMessages.getString("D3.label9"));
		lblMs.setBounds(465, 29, 34, 15);
		panel.add(lblMs);

		btnStart = new JButton(BaseMessages.getString("D3.btnWrite"));
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnWrite_Click();
			}

		});
		btnStart.setBounds(411, 108, 75, 23);
		panel.add(btnStart);

		/* xusheng 2.12.4.25 */
		btnStop = new JButton(BaseMessages.getString("D3.btnwriteStop"));
		btnStop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnStop_Click();
			}

		});
		btnStop.setBounds(496, 108, 75, 23);
		btnStop.setEnabled(false);
		panel.add(btnStop);
		/* xusheng 2.12.4.25 */

		lblReadDataCount = new JLabel();
		lblReadDataCount.setBounds(126, 29, 108, 15);
		panel.add(lblReadDataCount);

		JLabel label_1 = new JLabel(BaseMessages.getString("D3.label6"));
		label_1.setBounds(174, 54, 60, 15);
		panel.add(label_1);

		spinner = new JSpinner();
		spinner.setBounds(241, 51, 43, 22);
		spinner.setValue(6);
		panel.add(spinner);

		JLabel label_2 = new JLabel(BaseMessages.getString("D3.label7"));
		label_2.setBounds(174, 80, 60, 15);
		panel.add(label_2);

		spinner_1 = new JSpinner();
		spinner_1.setBounds(241, 76, 43, 22);
		spinner_1.setValue(10);
		panel.add(spinner_1);

		JLabel label_5 = new JLabel(BaseMessages.getString("D3.label3"));
		label_5.setBounds(306, 79, 92, 15);
		panel.add(label_5);

		spinner_2 = new JSpinner();
		spinner_2.setBounds(410, 76, 43, 22);
		spinner_2.setValue(0);
		panel.add(spinner_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, BaseMessages
				.getString("D3.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(27, 22, 585, 298);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 565, 215);
		panel_1.add(scrollPane);

		scrollPane.setViewportView(centerTable);

		button = new JButton(BaseMessages.getString("D3.btnScan"));
		button.setBounds(385, 254, 87, 23);
		panel_1.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readTag();
				isWrite = false;
			}

		});

		button_1 = new JButton(BaseMessages.getString("D3.btnStopScan"));
		button_1.setBounds(485, 254, 87, 23);
		panel_1.add(button_1);
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopAction();
			}

		});

		// button_2 = new JButton(BaseMessages.getString("D3.button1"));
		// button_2.setBounds(488, 254, 87, 23);
		// panel_1.add(button_2);

		JLabel label_6 = new JLabel(BaseMessages.getString("D3.label5"));
		label_6.setBounds(215, 258, 96, 15);
		panel_1.add(label_6);

		spinner_3 = new JSpinner();
		spinner_3.setBounds(321, 254, 51, 22);
		spinner_3.setModel(new SpinnerNumberModel(3, 1, null, 1));

		panel_1.add(spinner_3);

		// button_2.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// clearSelection();
		// }
		//
		// });

	}

	/*
	 * 停止按钮响应
	 */
	private void btnStop_Click() {
		this.isStopWrite = true;

	}

	@Override
	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "D3.centerTable");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
		centerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		centerTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				if (e.getType() == TableModelEvent.INSERT) {
					if (Integer.parseInt(spinner_3.getValue().toString()) == centerTable
							.getRowCount()) {
						stopAction();

						if (isWrite) {
							Thread t = new Thread() {
								@Override
								public void run() {
									startWrite();
								}
							};
							t.start();
						}
					}
				}
			}

		});
	}

	private void readTag() {
		// isReading = true;
		// startTime = System.currentTimeMillis();
		// times = Integer.parseInt(spinner_1.getValue().toString());
		// timeout = Integer.parseInt(spinner.getValue().toString());
		super.addReader();
		MainDemoPanel.getReadDataPanel().setRead(false);
		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);
		currentDemo.readTag();
		button.setEnabled(false);
		button_1.setEnabled(true);
		ConfigurationDialog.isOperatting = true;
		// 监控线程启动

	}

	private void stopAction() {
		super.removeReader();
		currentDemo.stopRead();
		isStop = true;
		ConfigurationDialog.isOperatting = false;
		button.setEnabled(true);
		button_1.setEnabled(false);
	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();
		UserConfig_IRP1 userConfig = currentDemo.getConfig_IRP1();
		rmb = userConfig.getRmb();
		readerType = userConfig.getReaderType();
		q = (byte) Util.convertTagNumToQ(userConfig.getTagNum());

		IMessage message = currentDemo.getConfig().getActiveAntenna();
		if (message != null) {
			currentDemo.getReader().send(message, 300);
		}

		button.setEnabled(true);
		button_1.setEnabled(false);
	}

	private void doActionReadFile() {
		if (fileChooser.showOpenDialog(D3.this) == JFileChooser.APPROVE_OPTION) {
			lblSelect.setVisible(false);
			lblReadDataCount.setVisible(false);
			dataQueue.clear();
			dataBackup.clear();

			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.exists()) {
				try {
					BufferedReader input = new BufferedReader(new FileReader(
							selectedFile));
					String data = null;
					@SuppressWarnings("unused")
					String dataCopy = null;
					while ((data = input.readLine()) != null) {
						if (Utils.isEmpty(data)) {
							continue;
						}
						dataCopy = new String(data);
						data = data.replaceAll(" ", "").replaceAll("-", "");
						if (Common.EPC_ASCII || Common.UserData_ASCII) {
							dataQueue.add(data);
							dataBackup.add(data);
						} else {
							if (!Util.isHexString(data)) {
								continue;
							}
							dataQueue.add(data);
							dataBackup.add(data);
						}

					}

					lblReadDataCount.setText(BaseMessages.getString("",
							"Message.MSG_103", new String[] { dataQueue.size()
									+ "" }));
					lblReadDataCount.setVisible(true);
					lblSelect.setVisible(true);
				} catch (FileNotFoundException ex) {
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void doActionSelectData() {
		tightForm = TightForm.getTightFormDialog(this);
		int remainderCount = dataQueue.size();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < dataBackup.size(); i++) {
			sb.append(dataBackup.get(i));
			if (i == dataBackup.size() - remainderCount) {
				sb.append(" 《==");
			}
			sb.append("\r\n");
		}
		tightForm.setData(sb.toString());
		tightForm.setVisible(true);
	}

	private void btnWrite_Click() {
		if (this.button_1.isEnabled()) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_107"));
			return;
		}

		if (dataQueue.size() < 1) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_110"));
			return;
		} else if (Integer.parseInt(spinner_3.getValue().toString()) > dataQueue
				.size()) {
			MessageDialog.showInfoMessage(BaseMessages.getString("MSG_125",
					spinner_3.getValue().toString()));
			return;
		}
		if (!this.chkEpc.isSelected() && !this.chkUserdata.isSelected()) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_109"));
			return;
		}

		// 先读卡

		readTag();

		isWrite = true;
		this.isStopWrite = false;

	}

	public void makeFace(JTable table) {
		try {
			final int columnCount = table.getColumnCount();
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

				private static final long serialVersionUID = 0L;

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {

					if (table.getValueAt(row, columnCount - 1) != null) {
						String isSuc = table.getValueAt(row, columnCount - 1)
								.toString();
						if ("1".equals(isSuc)) {
							setBackground(Color.green);
						} else if ("-1".equals(isSuc)) {
							setBackground(Color.red);
						} else {
							setBackground(table.getBackground());
						}
					} else {
						setBackground(table.getBackground());
					}

					return super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < columnCount; i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			table.getSelectionModel().clearSelection();
			// table.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clearSelection() {
		centerTable.getSelectionModel().clearSelection();
		centerTable.updateUI();
	}

	private void startWrite() {
		isWrite = false;
		if (centerTable.getRowCount() != Integer.parseInt(spinner_3.getValue()
				.toString()))
			return;

		this.btnStart.setEnabled(false);
		this.btnStop.setEnabled(true);

		// TODO: 读卡时禁用某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().lockNavigatorPanel();

		int epcLen = Math.min(Integer.parseInt(spinner.getValue().toString()),
				Common.EPC_MaxLen);
		int udLen_6b = Math.min(Integer.parseInt(spinner_1.getValue()
				.toString()), Common.userdata_MaxLen_6B);
		int udLen_6c = Math.min(Integer.parseInt(spinner_1.getValue()
				.toString()), Common.Userdata_MaxLen_6C);

		String tagType = "";
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e1) {
		// }

		sleepTime = Integer.parseInt(snrReadInterval.getValue().toString());
		for (int i = 0; i < centerTable.getRowCount()
				&& this.isStopWrite == false; i++) {
			String sendData = dataQueue.poll();
			lblReadDataCount.setText(BaseMessages.getString("",
					"Message.MSG_172", new String[] { dataQueue.size() + "" }));
			byte[] tagID = null;
			MemoryBank mBank = MemoryBank.TIDMemory;

			byte antenna = getAntennaFromCenterTable(i);

			// 标签类型
			tagType = centerTable.getValueAt(i, 1).toString();

			Object thisEPCv = centerTable.getValueAt(i, 2);
			Object thisTidv = centerTable.getValueAt(i, 3);

			if (thisTidv != null && !thisTidv.equals("")) {
				tagID = Util.convertHexStringToByteArray(thisTidv.toString());
				mBank = MemoryBank.TIDMemory;
			}
			if (thisEPCv != null && !thisEPCv.equals("")) {
				tagID = Util.convertHexStringToByteArray(thisEPCv.toString());
				mBank = MemoryBank.EPCMemory;
			}

			if (tagID == null)
				continue;

			IMessage msg = null;
			String udStr = sendData;
			String epcStr = sendData;
			byte[] wEpcData = null;
			byte[] wUserData = null;
			boolean isSuccess = true;// 写成功标志

			if ("6B".equals(tagType) && chkUserdata.isSelected())// 6b User
			// Data
			{
				if (Common.UserData_ASCII) {// AscII
					if (udStr.length() > udLen_6b * 2)
						udStr = sendData.substring(0, udLen_6b * 2);
					wUserData = udStr.getBytes();
				} else// 16进制
				{
					if (udStr.length() > udLen_6b * 4)
						udStr = sendData.substring(0, udLen_6b * 4);
					udStr = Utils.padZero(udStr);
					wUserData = Util.convertHexStringToByteArray(udStr);
				}

				if (chkChangeLength.isSelected())// 可变长
					msg = new WriteUserData2_6B(antenna, tagID, Byte
							.parseByte(spinner_2.getValue().toString()),
							wUserData);
				else
					msg = new WriteUserData_6B(antenna, tagID, Byte
							.parseByte(spinner_2.getValue().toString()),
							wUserData);

				isSuccess = isSuccess
						&& currentDemo.getReader().send(msg, 1000);// 返回结果

			} else if ("6C".equals(tagType)) {
				byte[] pwd = Utils.getPwd(txtPassWord.getText());

				udStr = sendData;
				if (chkUserdata.isSelected())// userdata
				{
					udStr = sendData;
					if (Common.UserData_ASCII) {
						if (udStr.length() > udLen_6c * 2)
							udStr = udStr.substring(0, udLen_6c * 2);
						wUserData = udStr.getBytes();
					} else// 16进制
					{
						if (udStr.length() > udLen_6c * 4)
							udStr = udStr.substring(0, udLen_6c * 4);

						udStr = Utils.padZero(udStr);

						wUserData = Util.convertHexStringToByteArray(udStr);
					}

					// 构造消息6c -UserData
					if (tagID != null) {
						msg = new WriteUserData_6C(antenna, pwd, (byte) 0,
								wUserData, tagID, mBank);

						isSuccess = isSuccess
								&& currentDemo.getReader().send(msg, 1000);
					}
				}

				epcStr = sendData;

				if (chkEpc.isSelected())// epc
				{
					if (Common.EPC_ASCII) {
						if (epcStr.length() > epcLen * 2) {
							epcStr = sendData.substring(0, epcLen * 2);
						}
						wEpcData = epcStr.getBytes();
					} else// 16进制
					{
						if (epcStr.length() > epcLen * 4)
							epcStr = epcStr.substring(0, epcLen * 4);

						epcStr = Utils.padZero(epcStr);

						wEpcData = Util.convertHexStringToByteArray(epcStr);
					}

					// 构造消息6c -EPC
					if (tagID != null) {
						msg = new WriteEpc(antenna, pwd, wEpcData, tagID, mBank);

						isSuccess = isSuccess
								&& currentDemo.getReader().send(msg);
					}
				}
			}

			if (chkEpc.isSelected()) {
				centerTable.setValueAt(epcStr, i, 2);
			} else {
				centerTable.setValueAt("", i, 2);
			}

			if (chkUserdata.isSelected()) {
				centerTable.setValueAt(udStr, i, 4);
			} else {
				centerTable.setValueAt("", i, 4);
			}

			centerTable.setValueAt(isSuccess ? 1 : -1, i, centerTable
					.getColumnCount() - 1);
			makeFace(centerTable);

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// do nothing
			}

		}

		// TODO: 停止读卡时解禁某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().unlockNavigatorPanel();

		this.btnStart.setEnabled(true);
		this.btnStop.setEnabled(false);

	}

	private byte getAntennaFromCenterTable(int row) {
		int t = 0;
		byte ant = 1;
		for (int j = 6; j < 10; j++) {
			if (centerTable.getValueAt(row, j) == null
					|| ((String) centerTable.getValueAt(row, j)).equals(""))
				continue;
			if (t < Integer.parseInt((String) centerTable.getValueAt(row, j))) {
				t = Integer.parseInt((String) centerTable.getValueAt(row, j));
				String cn = centerTable.getColumnName(j);
				ant = Byte.parseByte(cn.substring(cn.length() - 1));
			}
		}
		return ant;
	}
}
