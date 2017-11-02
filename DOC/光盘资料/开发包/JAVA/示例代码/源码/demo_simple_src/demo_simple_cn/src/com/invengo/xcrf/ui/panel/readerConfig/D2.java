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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
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

public class D2 extends AbstractReaderDataTable {
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

	private Queue<String> dataQueue = new LinkedList<String>();
	private List<String> dataBackup = new ArrayList<String>();
	private long sleepTime;

	private JLabel lblReadDataCount;
	private TightForm tightForm;
	private JCheckBox chkEpc;
	private JCheckBox chkUserdata;
	private JCheckBox chkChangeLength;
	private JSpinner snrReadInterval;
	private JButton btnStart;
	private boolean isWriteStop = false;
	private JButton btnStopWrite ;
	public D2() {
		setLayout(null);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Text file", "txt");
		fileChooser.setFileFilter(filter);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("D2.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(22, 352, 596, 118);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("D2.label1"));
		label.setBounds(23, 29, 60, 15);
		panel.add(label);

		JButton btn_Browse = new JButton(BaseMessages
				.getString("D2.btntxtFile"));
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
		lblSelect.setBounds(295, 29, 24, 15);
		panel.add(lblSelect);

		JLabel label_3 = new JLabel(BaseMessages.getString("D2.label2"));
		label_3.setBounds(23, 54, 60, 15);
		panel.add(label_3);

		chkEpc = new JCheckBox(BaseMessages.getString("D2.cbkEpc"));
		chkEpc.setBounds(82, 50, 65, 23);
		chkEpc.setSelected(true);
		panel.add(chkEpc);

		chkUserdata = new JCheckBox(BaseMessages.getString("D2.cbkUserData"));
		chkUserdata.setBounds(149, 50, 86, 23);
		chkUserdata.setSelected(true);
		panel.add(chkUserdata);

		chkChangeLength = new JCheckBox(BaseMessages.getString("D2.cb_unfix"));
		chkChangeLength.setBounds(236, 50, 98, 23);
		chkChangeLength.setSelected(true);
		panel.add(chkChangeLength);

		JLabel lblc = new JLabel(BaseMessages.getString("D2.label4"));
		lblc.setBounds(340, 54, 85, 15);
		panel.add(lblc);

		txtPassWord = new JPasswordField();
		txtPassWord.setDocument(new TextDocument(8, 0));
		txtPassWord.setBounds(429, 51, 121, 21);
		txtPassWord.setText("00000000");
		panel.add(txtPassWord);

		JLabel label_4 = new JLabel(BaseMessages.getString("D2.label3"));
		label_4.setBounds(23, 81, 60, 15);
		panel.add(label_4);

		snrReadInterval = new JSpinner();
		snrReadInterval.setModel(new SpinnerNumberModel(new Integer(500), null,
				null, new Integer(1)));
		snrReadInterval.setBounds(82, 79, 43, 22);
		panel.add(snrReadInterval);

		JLabel lblMs = new JLabel(BaseMessages.getString("D2.label6"));
		lblMs.setBounds(137, 81, 24, 15);
		panel.add(lblMs);

		btnStart = new JButton(BaseMessages.getString("D2.btnWrite"));
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnWrite_Click();
			}

		});
		btnStart.setBounds(394, 82, 75, 23);
		panel.add(btnStart);
		/* xusheng 2012.4.25 */
		 btnStopWrite = new JButton(BaseMessages
				.getString("D2.btnStopWrite"));
		btnStopWrite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				btnStopWrite_Click();
			}

		});
		btnStopWrite.setBounds(476, 82, 75, 23);
		panel.add(btnStopWrite);
		/* xusheng 2012.4.25 */

		lblReadDataCount = new JLabel();
		lblReadDataCount.setBounds(137, 29, 110, 15);
		panel.add(lblReadDataCount);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, BaseMessages
				.getString("D2.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(27, 22, 585, 309);
		add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 565, 229);
		panel_1.add(scrollPane);

		scrollPane.setViewportView(centerTable);

		button = new JButton(BaseMessages.getString("D2.btnScan"));
		button.setBounds(389, 268, 87, 23);
		panel_1.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readTag();
			}

		});

		button_1 = new JButton(BaseMessages.getString("D2.btnStopScan"));
		button_1.setBounds(486, 268, 87, 23);
		panel_1.add(button_1);
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopAction();
			}

		});

		// button_2 = new JButton(BaseMessages.getString("D2.button1"));
		// button_2.setBounds(483, 268, 87, 23);
		// panel_1.add(button_2);
		// button_2.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// clearSelection();
		// }
		//
		// });

	}

	private void btnStopWrite_Click() {
		
		// TODO: 停止读卡时解禁某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().unlockNavigatorPanel();
		this.isWriteStop = true;
		this.btnStart.setEnabled(true);
	}

	@Override
	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "D2.centerTable");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
		centerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	private void readTag() {
		// isReading = true;
		// startTime = System.currentTimeMillis();
		// times = Integer.parseInt(spinner_1.getValue().toString());
		// timeout = Integer.parseInt(spinner.getValue().toString());
		super.addReader();
		MainDemoPanel.getReadDataPanel().setRead(false);
		currentDemo.readTag();
		button.setEnabled(false);
		button_1.setEnabled(true);
		ConfigurationDialog.isOperatting = true;
		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);
		// 监控线程启动

	}

	private void stopAction() {
		super.removeReader();
		currentDemo.stopRead();
		isStop = true;

		button.setEnabled(true);
		button_1.setEnabled(false);
		ConfigurationDialog.isOperatting = false;
	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();
		UserConfig_IRP1 userConfig = currentDemo.getConfig_IRP1();
		rmb = userConfig.getRmb();
		readerType = userConfig.getReaderType();
		q = (byte) Util.convertTagNumToQ(userConfig.getTagNum());

		// IMessage message = null;
		// if ("800".equals(userConfig.getReaderType())) {
		// byte a = antenna = (byte) (userConfig.getAntennaIndex() + 1);
		// if (a > 4) {
		// a -= 4;
		// antenna = 0;
		// }
		// message = new SysConfig_800((byte) 0x02, new byte[] { a });
		// } else if ("500".equals(userConfig.getReaderType())) {
		// byte a = antenna = (byte) (userConfig.getAntennaIndex() + 1);
		// if (antenna == 3)
		// antenna = 0;
		// message = new SysConfig_800((byte) 0x02, new byte[] { a });
		// }
		IMessage message = currentDemo.getConfig().getActiveAntenna();
		if (message != null)
			currentDemo.getReader().send(message, 300);
	}

	private void doActionReadFile() {
		if (fileChooser.showOpenDialog(D2.this) == JFileChooser.APPROVE_OPTION) {
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
		if (centerTable.getSelectedRowCount() < 1) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_108"));
			return;
		}

		if (dataQueue == null || dataQueue.size() < 1) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_110"));
			return;
		} else if (centerTable.getSelectedRowCount() > dataQueue.size()) {
			MessageDialog.showInfoMessage(BaseMessages.getString("MSG_110",
					String.valueOf(centerTable.getSelectedRowCount())));
			return;
		}
		if (!this.chkEpc.isSelected() && !this.chkUserdata.isSelected()) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_109"));
			return;
		}

		this.btnStopWrite.setEnabled(true);
		this.btnStart.setEnabled(false);
		
		// TODO: 读卡时禁用某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().lockNavigatorPanel();
		
		Thread t = new Thread(){
			@Override
			public void run(){
				writeTag();
			}
		};
		
		t.start();
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
			table.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clearSelection() {
		centerTable.getSelectionModel().clearSelection();
		centerTable.updateUI();
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

	private void writeTag(){
		// 开始写操作
		String tagType = "";
		isWriteStop = false;
		Demo demo = DemoRegistry.getCurrentDemo();
		sleepTime = Integer.parseInt(snrReadInterval.getValue().toString());

		int[] selectedRowIndexs = centerTable.getSelectedRows();
		for (int i = 0; i < selectedRowIndexs.length && isWriteStop == false; i++) {
			String sendData = dataQueue.poll();

			lblReadDataCount.setText(BaseMessages.getString("",
					"Message.MSG_172", new String[] { dataQueue.size() + "" }));
			boolean writeFlag = true;

			byte[] tagID = null;
			String tagFlag = "";
			MemoryBank mBank = MemoryBank.TIDMemory;
			byte antenna = getAntennaFromCenterTable(selectedRowIndexs[i]);

			// 标签类型
			tagType = centerTable.getValueAt(selectedRowIndexs[i], 1)
					.toString();

			Object thisEPCv = centerTable.getValueAt(selectedRowIndexs[i], 2);
			Object thisTidv = centerTable.getValueAt(selectedRowIndexs[i], 3);

			if (thisTidv != null && !thisTidv.equals("")) {
				tagFlag = thisTidv.toString().trim();
				mBank = MemoryBank.TIDMemory;
			}
			if (thisEPCv != null && !thisEPCv.equals("")) {
				tagFlag = thisEPCv.toString().trim();
				mBank = MemoryBank.EPCMemory;
			}

			if (tagFlag == null && tagFlag.equals(""))
				continue;

			tagID = Util.convertHexStringToByteArray(tagFlag);

			IMessage msg = null;
			String udStr = sendData;
			String epcStr = sendData;
			byte[] wEpcData = null;
			byte[] wUserData = null;

			if ("6B".equals(tagType) && chkUserdata.isSelected())// 6b User
			// Data
			{
				if (Common.UserData_ASCII) {// AscII
					if (udStr.length() > Common.userdata_MaxLen_6B * 2)
						udStr = sendData.substring(0,
								Common.userdata_MaxLen_6B * 2);
					wUserData = udStr.getBytes();
				} else// 16进制
				{
					if (udStr.length() > Common.userdata_MaxLen_6B * 4)
						udStr = sendData.substring(0,
								Common.userdata_MaxLen_6B * 4);
					udStr = Utils.padZero(udStr);
					wUserData = Util.convertHexStringToByteArray(udStr);
				}

				if (chkChangeLength.isSelected())// 可变长
					msg = new WriteUserData2_6B(antenna, tagID, (byte) 0,
							wUserData);
				else
					msg = new WriteUserData_6B(antenna, tagID, (byte) 0,
							wUserData);

				writeFlag = writeFlag && demo.getReader().send(msg, 1000);// 返回结果

			} else if ("6C".equals(tagType)) {
				byte[] pwd = Utils.getPwd(txtPassWord.getText());

				udStr = sendData;
				if (chkUserdata.isSelected())// userdata
				{
					udStr = sendData;
					if (Common.UserData_ASCII) {
						if (udStr.length() > Common.Userdata_MaxLen_6C * 2)
							udStr = udStr.substring(0,
									Common.Userdata_MaxLen_6C * 2);
						wUserData = udStr.getBytes();
					} else// 16进制
					{
						if (udStr.length() > Common.Userdata_MaxLen_6C * 4)
							udStr = udStr.substring(0,
									Common.Userdata_MaxLen_6C * 4);

						udStr = Utils.padZero(udStr);

						wUserData = Util.convertHexStringToByteArray(udStr);
					}

					// 构造消息6c -UserData
					if (tagID != null) {
						msg = new WriteUserData_6C(antenna, pwd, (byte) 0,
								wUserData, tagID, mBank);
						writeFlag = writeFlag
								&& demo.getReader().send(msg, 1000);
					}
				}

				epcStr = sendData;

				if (chkEpc.isSelected())// epc
				{
					if (Common.EPC_ASCII) {
						if (epcStr.length() > Common.EPC_MaxLen * 2) {
							epcStr = sendData.substring(0,
									Common.EPC_MaxLen * 2);
						}
						wEpcData = epcStr.getBytes();
					} else// 16进制
					{
						if (epcStr.length() > Common.EPC_MaxLen * 4)
							epcStr = epcStr.substring(0, Common.EPC_MaxLen * 4);

						epcStr = Utils.padZero(epcStr);

						wEpcData = Util.convertHexStringToByteArray(epcStr);
					}

					// 构造消息6c -EPC
					if (tagID != null) {
						msg = new WriteEpc(antenna, pwd, wEpcData, tagID, mBank);

						writeFlag = writeFlag
								&& demo.getReader().send(msg, 1000);
					}
				}
			}

			if (chkEpc.isSelected() && writeFlag) {
				centerTable.setValueAt(epcStr, selectedRowIndexs[i], 2);
			} else {
				centerTable.setValueAt("", selectedRowIndexs[i], 2);
			}

			if (chkUserdata.isSelected() && writeFlag) {
				centerTable.setValueAt(udStr, selectedRowIndexs[i], 4);
			} else {
				centerTable.setValueAt("", selectedRowIndexs[i], 4);
			}

			centerTable.setValueAt(writeFlag ? 1 : -1, selectedRowIndexs[i],
					centerTable.getColumnCount() - 1);
			makeFace(centerTable);

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
		btnStopWrite_Click();
	}
}
