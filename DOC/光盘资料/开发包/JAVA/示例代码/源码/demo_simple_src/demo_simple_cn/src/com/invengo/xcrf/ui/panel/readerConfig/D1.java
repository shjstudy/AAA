package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.ReadTag;
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
import java.lang.Thread.UncaughtExceptionHandler;
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
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.TightForm;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.panel.AbstractReaderDataTable;

public class D1 extends AbstractReaderDataTable {
	private static final long serialVersionUID = 115429288553822605L;
	private JPasswordField txtPassWord;
	private JFileChooser fileChooser = new JFileChooser();
	private JLabel lblReadDataCount;
	private JLabel lblSelect;

	private Queue<String> dataQueue = new LinkedList<String>();
	private List<String> dataBackup = new ArrayList<String>();

	private volatile boolean stop;
	private JButton btnStart;
	private JCheckBox chkChangeLength;
	private JSpinner snrReadInterval;

	private boolean isUnfix;
	private int wCount;
	private long sleepTime;
	private byte[] pwd;
	private ReadMemoryBank rmb;
	private String readerType;
	@SuppressWarnings("unused")
	private byte q;
	private JCheckBox chkUserdata;
	private JCheckBox chkEpc;
	private int userdataLen;
	private int epcLen;
	private JButton btnStop;

	private byte[] wEpcData = null;
	private byte[] wUserData = null;

	private int userdataPtr = 0x00;

	private final Object objLock = new Object();

	public D1() {
		setLayout(null);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Text file", "txt");
		fileChooser.setFileFilter(filter);

		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "centerTable.dynamicreadwrite");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);

		epcLen = Common.EPC_MaxLen;
		userdataLen = Common.Userdata_MaxLen_6C;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 25, 596, 300);
		add(scrollPane);

		scrollPane.setViewportView(centerTable);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("D1.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(22, 352, 596, 118);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("D1.label3"));
		label.setBounds(23, 29, 60, 15);
		panel.add(label);

		JButton btn_Browse = new JButton("...");
		btn_Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doActionReadFile();
			}
		});
		btn_Browse.setBounds(82, 25, 34, 23);
		panel.add(btn_Browse);

		lblReadDataCount = new JLabel();
		lblReadDataCount.setVisible(false);
		lblReadDataCount.setBounds(139, 29, 118, 15);
		panel.add(lblReadDataCount);

		lblSelect = new JLabel(BaseMessages.getString("D1.linkLabel1"));
		lblSelect.setForeground(Color.RED);
		lblSelect.setVisible(false);
		lblSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doActionSelectData();
			}
		});
		lblSelect.setBounds(295, 29, 24, 15);
		panel.add(lblSelect);

		JLabel label_3 = new JLabel(BaseMessages.getString("D1.label1"));
		label_3.setBounds(23, 54, 60, 15);
		panel.add(label_3);

		chkEpc = new JCheckBox(BaseMessages.getString("D1.checkBox1"));
		chkEpc.setSelected(true);
		chkEpc.setBounds(82, 50, 65, 23);
		panel.add(chkEpc);

		chkUserdata = new JCheckBox(BaseMessages.getString("D1.checkBox2"));
		chkUserdata.setSelected(true);
		chkUserdata.setBounds(149, 50, 86, 23);
		panel.add(chkUserdata);

		chkChangeLength = new JCheckBox(BaseMessages.getString("D1.cb_unfix"));
		chkChangeLength.setSelected(true);
		chkChangeLength.setBounds(236, 50, 98, 23);
		panel.add(chkChangeLength);

		JLabel lblc = new JLabel(BaseMessages.getString("D1.label4"));
		lblc.setBounds(340, 54, 85, 15);
		panel.add(lblc);

		txtPassWord = new JPasswordField();
		txtPassWord.setDocument(new TextDocument(8, 0));
		txtPassWord.setBounds(429, 51, 112, 21);
		txtPassWord.setText("00000000");
		panel.add(txtPassWord);

		JLabel label_4 = new JLabel(BaseMessages.getString("D1.label2"));
		label_4.setBounds(23, 81, 60, 15);
		panel.add(label_4);

		snrReadInterval = new JSpinner();
		snrReadInterval.setModel(new SpinnerNumberModel(new Integer(500), null,
				null, new Integer(1)));
		snrReadInterval.setBounds(82, 79, 43, 22);
		panel.add(snrReadInterval);

		JLabel lblMs = new JLabel("ms");
		lblMs.setBounds(137, 81, 24, 15);
		panel.add(lblMs);

		JLabel label_5 = new JLabel(BaseMessages.getString("D1.label8"));
		label_5.setBounds(159, 81, 85, 15);
		panel.add(label_5);

		final JSpinner snrWriteTimes = new JSpinner();
		snrWriteTimes.setModel(new SpinnerNumberModel(new Integer(3), null,
				null, new Integer(1)));
		snrWriteTimes.setBounds(243, 79, 43, 22);
		panel.add(snrWriteTimes);

		btnStart = new JButton(BaseMessages.getString("D1.button1"));
		btnStart.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				doActionStart(snrWriteTimes);
			}
		});
		btnStart.setBounds(410, 85, 75, 23);
		panel.add(btnStart);

		btnStop = new JButton(BaseMessages.getString("D1.button2"));

		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationDialog.isOperatting = false;
				stop = true;
			}
		});
		btnStop.setBounds(493, 85, 75, 23);
		panel.add(btnStop);

	}

	private void doTest() {
		String epcStr = "";
		String udStr = "";
		String str = "";

		// TODO: 读卡时禁用某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().lockNavigatorPanel();
		btnStop.setEnabled(true);
		while (!stop) {
			ReadTag msg = new ReadTag(rmb, true);
			msg.setAntenna((byte) currentDemo.getConfig().getAntennaIndex());
			msg.setQ((byte) 2);
			msg.setLoop(false);

			boolean isReadSuc = currentDemo.getReader().send(msg, 1000);
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
				if (msg.getReceivedMessage().getList_RXD_TagData() != null
						&& msg.getReceivedMessage().getList_RXD_TagData().length > 0) {
					RXD_TagData m = msg.getReceivedMessage()
							.getList_RXD_TagData()[0];
					if (m.getStatusCode() == 0) {
						wAntenna = m.getReceivedMessage().getAntenna();
						sb = MemoryBank.EPCMemory;
						tagType = "6C";
						epcBuff = m.getReceivedMessage().getEPC();
						if (epcBuff.length >= 2 && "800".equals(readerType)
								&& currentDemo.isRssiEnable_800()) {
							byte[] t = new byte[epcBuff.length - 2];
							System.arraycopy(epcBuff, 0, t, 0, t.length);
							rssi = Util
									.convertByteToHexWordString(epcBuff[epcBuff.length - 2]);
							epcBuff = t;
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
						if (epcBuff.length >= 2 && "800".equals(readerType)
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
						if (epcBuff.length >= 2 && "800".equals(readerType)
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
						tid = Util.convertByteArrayToHexWordString(tidBuff);

						if ("500".equals(readerType)) {
							rssi = tid.substring(tid.length() - 4);
							tid = tid.substring(0, tid.length() - 4);
						}
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

				if ("".equals(epcStr) && "".equals(udStr)) {
					str = "";
					if (dataQueue != null && dataQueue.size() > 0) {
						str = dataQueue.poll();
						lblReadDataCount.setText(BaseMessages.getString("",
								"Message.MSG_172", new String[] { dataQueue
										.size()
										+ "" }));
						if ("6B".equals(tagType) && chkUserdata.isSelected()) {
							udStr = new String(str);
							if (Common.UserData_ASCII) {
								if (str.length() > userdataLen * 2)
									udStr = str.substring(0, userdataLen * 2);
								wUserData = udStr.getBytes();
							} else {
								if (str.length() > userdataLen * 4) {
									udStr = str.substring(0, userdataLen * 4);
								}
								if (udStr.length() % 4 != 0) {
									int catchLen = ((udStr.length() / 4) + 1) * 4;
									udStr = Utils.padRight(str, catchLen, '0');
								}
								wUserData = Util
										.convertHexStringToByteArray(str);
							}
						} else if ("6C".equals(tagType)) {
							if (chkEpc.isSelected()) {
								epcStr = str;
								if (Common.EPC_ASCII) {
									if (epcStr.length() > epcLen * 2)
										epcStr = epcStr
												.substring(0, epcLen * 2);
									wEpcData = epcStr.getBytes();
								} else {
									if (epcStr.length() > epcLen * 4)
										epcStr = epcStr
												.substring(0, epcLen * 4);
									if (epcStr.length() % 4 != 0) {
										int catchLen = ((epcStr.length() / 4) + 1) * 4;
										epcStr = Utils.padRight(epcStr,
												catchLen, '0');
									}
									wEpcData = Util
											.convertHexStringToByteArray(epcStr);
								}
							}
							if (chkUserdata.isSelected()) {
								udStr = str;
								if (Common.UserData_ASCII) {
									if (str.length() > userdataLen * 2)
										udStr = str.substring(0,
												userdataLen * 2);
									wUserData = udStr.getBytes();
								} else {
									if (str.length() > userdataLen * 4)
										udStr = str.substring(0,
												userdataLen * 4);
									if (udStr.length() % 4 != 0) {
										int catchLen = ((udStr.length() / 4) + 1) * 4;
										udStr = Utils.padRight(udStr, catchLen,
												'0');
									}
									wUserData = Util
											.convertHexStringToByteArray(udStr);
								}
							}
						}
					}
				}

				// 显示/写卡
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
					boolean wEpc = false;
					boolean wUd = false;
					boolean isSuc = false;
					if (isAdd) {
						for (int i = 0; i < wCount; i++) {
							IMessage wmsg = null;
							if ("6B".equals(tagType)
									&& chkUserdata.isSelected()) {
								if (wUserData == null) {
									return;
								}
								if (isUnfix) {
									wmsg = new WriteUserData2_6B(wAntenna,
											idBytes, (byte) userdataPtr,
											wUserData);
								} else {
									wmsg = new WriteUserData_6B(wAntenna,
											idBytes, (byte) userdataPtr,
											wUserData);

								}
							} else if ("6C".equals(tagType)) {
								byte[] tagID = null;
								if (sb == MemoryBank.EPCMemory
										&& epcBuff != null) {
									tagID = epcBuff;
								} else if (sb == MemoryBank.TIDMemory
										&& tidBuff != null) {
									tagID = tidBuff;
								}
								if (tagID != null) {
									if (chkEpc.isSelected() && !wEpc) {
										if (wEpcData == null) {
											return;
										}
										wmsg = new WriteEpc(wAntenna, pwd,
												wEpcData, tagID, sb);
										if (currentDemo.getReader().send(wmsg,
												1000)) {
											epc = Util
													.convertByteArrayToHexWordString(wEpcData);
											wEpc = true;
											if (sb == MemoryBank.EPCMemory
													&& chkUserdata.isSelected()) {
												tagID = new byte[wEpcData.length];
												System.arraycopy(wEpcData, 0,
														tagID, 0,
														wEpcData.length);
											}
										}
									}
									if (chkUserdata.isSelected()) {
										if (wUserData == null) {
											return;
										}
										wmsg = new WriteUserData_6C(wAntenna,
												pwd, (byte) userdataPtr,
												wUserData, tagID, sb);
									}
								}
							}
							if (chkUserdata.isSelected() && !wUd) {
								if (currentDemo.getReader().send(wmsg, 1000)) {
									userdata = Util
											.convertByteArrayToHexWordString(wUserData);
									wUd = true;
								}
							}
							if (chkEpc.isSelected()) {
								isSuc = wEpc;
								if (chkUserdata.isSelected() && isSuc) {
									isSuc = wUd;
								}
							} else {
								isSuc = chkUserdata.isSelected() && wUd;
							}

							if (isSuc) {
								epcStr = "";
								udStr = "";
								wEpcData = null;
								wUserData = null;
								break;
							}
						}
						if (isAdd) {
							displayDataGridView("", tagType, epc, tid,
									userdata, wAntenna, rssi, isSuc);

						}
					}
				}
			} else {
				continue;
			}
		}

		// TODO: 停止读卡时解禁某些功能
		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().unlockNavigatorPanel();
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}

	private void displayDataGridView(String string, String tagType, String epc,
			String tid, String userData, byte antenna2, String rssi,
			Boolean isSuc) {
		boolean isExist = false;
		DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
				.getModel();
		if (!isExist) {
			String[] rowData = new String[defaultTableModel.getColumnCount()];
			rowData[0] = tagType;
			rowData[1] = epc;
			rowData[2] = tid;
			rowData[3] = userData;
			// 这个地方修改读取时间
			String readTime = Common.getSystemTime();
			rowData[4] = readTime;
			rowData[5] = isSuc ? "1" : "-1";
			defaultTableModel.addRow(rowData);
			Common.myBeep();
		}
		makeFace(centerTable);
	}

	private void doActionReadFile() {
		if (fileChooser.showOpenDialog(D1.this) == JFileChooser.APPROVE_OPTION) {
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
		TightForm tightForm = TightForm.getTightFormDialog(this);
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
		tightForm.setLocationRelativeTo(null);
		tightForm.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private void doActionStart(final JSpinner snrWriteTimes) {
		if (dataQueue.size() == 0) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_110"));
			return;
		}
		((DefaultTableModel) centerTable.getModel()).setRowCount(0);
		stop = false;
		ConfigurationDialog.isOperatting = true;
		isUnfix = chkChangeLength.isSelected();
		wCount = Integer.parseInt(snrWriteTimes.getValue().toString());
		sleepTime = Integer.parseInt(snrReadInterval.getValue().toString());
		pwd = Utils.getPwd(txtPassWord.getText());

		Thread thread = new Thread() {
			@Override
			public void run() {
				doTest();
			}
		};

		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("ooooo");
			}
		});

		thread.start();

		btnStart.setEnabled(false);
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
	}

	@Override
	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "centerTable.dynamicreadwrite");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);

	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,
			invengo.javaapi.core.IMessageNotification msg) {
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
							setBackground(Color.white);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
