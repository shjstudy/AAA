package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.Log;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.EasAlarm_6C;
import invengo.javaapi.protocol.IRP1.Keepalive;
import invengo.javaapi.protocol.IRP1.PcSendTime_500;
import invengo.javaapi.protocol.IRP1.RXD_IOTriggerSignal_800;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.dialog.TagAccessDialog;

public class ReadDataPanel extends JPanel implements
		IMessageNotificationReceivedHandle {

	JTable centerTable = null;

	private boolean isRead = true;

	private Timer timer;

	private long startReadTime;

	private int totalCount;

	private int lastTotal = 0; // 上一秒的读取总数

	private int tagCount;

	private int immediateSpeed; // 即时速度

	private int rate;// 平均速度

	private String readTagTime;

	// 每列的宽度
	private int[] columnWidth;
	JMenuItem[] items;

	// public final JLabel total;
	// public final JLabel rate;
	// public final JLabel timerl;

	public TagInfo tagInfo;

	private MainFrame frame;

	// 即时速度
	public void setImmediateSpeed(int immediateSpeed) {
		this.immediateSpeed = immediateSpeed;
	}

	public int getImmediateSpeed() {
		return this.immediateSpeed;
	}

	/**
	 * 是否是读取状态
	 * 
	 * @return
	 */
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public ReadDataPanel(final JFrame frame) {
		this.setBackground(Color.white);
		this.frame = (MainFrame) frame;
		this.setLayout(new BorderLayout());

		WidgetFactory widget = WidgetFactory.getInstance();

		centerTable = widget.buildJTable("centerTable", "centerTable.title800");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
		centerTable
				.setPreferredScrollableViewportSize(new Dimension(1200, 800));
		centerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		centerTable.setAutoCreateRowSorter(true);

		centerTable.setBackground(new Color(199, 237, 204));
		final JScrollPane s = new JScrollPane(centerTable);
		// s.setBackground(Color.white);
		s.getViewport().setBackground(new Color(199, 237, 204));
		add(s, BorderLayout.CENTER);

		// 保存每列的宽度
		// int columncount = centerTable.getColumnCount();
		// for(int i = 0 ; i < columncount ; i++ ){
		// columnWidth[i] =
		// centerTable.getTableHeader().getColumnModel().getColumn(i).getWidth();
		// }
		centerTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				if (centerTable.getRowCount() != 0) {
					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getBtnQuery().setEnabled(true);
					if (centerTable.getSelectedRowCount() != 0) {
						eightCenterNorthJPanel.getCenterNorthOneJPanel()
								.getTagAccessJButton().setEnabled(true);
					}
				} else {
					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getBtnQuery().setEnabled(false);
					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getTagAccessJButton().setEnabled(false);
				}
			}

		});

		centerTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						// TODO Auto-generated method stub
						if (centerTable.getSelectedRowCount() > 0) {
							eightCenterNorthJPanel.getCenterNorthOneJPanel()
									.getTagAccessJButton().setEnabled(true);
						} else {
							eightCenterNorthJPanel.getCenterNorthOneJPanel()
									.getTagAccessJButton().setEnabled(false);
						}
					}

				});

		centerTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getClickCount() == 2) {

					if (centerTable.getSelectedRowCount() == 0) {
						return;
					}
					int selectedIndex = centerTable.getSelectedRow();

					JTable dt = WidgetFactory.getInstance().buildJTable(
							"tagInfo", "tag.access.header");

					DefaultTableModel dtModel = (DefaultTableModel) dt
							.getModel();

					String[] rowData = new String[dtModel.getColumnCount()];
					rowData[0] = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedIndex, 0);
					rowData[1] = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedIndex, 1);
					rowData[2] = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedIndex, 2);
					rowData[3] = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedIndex, 3);
					rowData[4] = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedIndex, 4);

					// 是否断开
					String readerName = rowData[0];
					Demo tempDemo = DemoRegistry.getDemoByName(readerName);
					if (!tempDemo.getReader().isConnected()) {
						MessageDialog.showInfoMessage(ReadDataPanel.this,
								BaseMessages.getString("", "Message.MSG_169",
										readerName));
						return;
					}
					int t = 0;
					String ant = "";
					for (int j = 6; j < 10; j++) {
						if (MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, j) == null
								|| ((String) MainDemoPanel.getReadDataPanel().centerTable
										.getValueAt(selectedIndex, j))
										.equals(""))
							continue;
						if (t < Integer.parseInt((String) MainDemoPanel
								.getReadDataPanel().centerTable.getValueAt(
								selectedIndex, j))) {
							t = Integer.parseInt((String) MainDemoPanel
									.getReadDataPanel().centerTable.getValueAt(
									selectedIndex, j));
							String cn = MainDemoPanel.getReadDataPanel().centerTable
									.getColumnName(j);
							ant = cn.substring(cn.length() - 1);
						}
					}

					rowData[5] = ant;
					dtModel.addRow(rowData);
					Common.myBeep();

					// 选中表格所有行
					dt.selectAll();
					TagAccessDialog tagDialog = new TagAccessDialog(dt);
					tagDialog.setLocationRelativeTo(frame);
					tagDialog.setVisible(true);

				}

			}

		});

		eightCenterNorthJPanel = new EightCenterNorthJPanel(frame);
		add(eightCenterNorthJPanel, BorderLayout.NORTH);

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				/* xusheng 2012 4 23 */
				// 更新主界面的表盘数据显示
				TagInfoUpdate();
				/* xusheng 2012 4 23 */

			}
		});
		columnWidth = new int[20];
		int columncount = centerTable.getColumnCount();
		for (int i = 0; i < columncount; i++) {
			columnWidth[i] = centerTable.getTableHeader().getColumnModel()
					.getColumn(i).getWidth();
		}

		s.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					showComlumnMenu().show(s, arg0.getX(), arg0.getY());
				}
			}
		});
	}

	/**
	 * 显示列
	 */
	public JPopupMenu showComlumnMenu() {
		JPopupMenu showClomlumnClickMenu = new JPopupMenu();
		final int columncount = centerTable.getColumnCount();
		// 保存每列的宽度

		items = new JMenuItem[20];
		for (int i = 0; i < columncount; i++) {
			final int x = i;
			final JMenuItem item = new JCheckBoxMenuItem(centerTable
					.getColumnName(i));
			showClomlumnClickMenu.add(item);
			if (centerTable.getTableHeader().getColumnModel().getColumn(i)
					.getWidth() != 0) {
				item.setSelected(true);
				if (i == 0)
					item.setEnabled(false);
			}
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (item.isSelected()) {
						centerTable.getTableHeader().getColumnModel()
								.getColumn(x).setMaxWidth(columnWidth[x]);
						centerTable.getTableHeader().getColumnModel()
								.getColumn(x).setMinWidth(columnWidth[x]);
					} else {
						centerTable.getTableHeader().getColumnModel()
								.getColumn(x).setMinWidth(0);
						centerTable.getTableHeader().getColumnModel()
								.getColumn(x).setMaxWidth(0);
					}

				}
			});
		}

		return showClomlumnClickMenu;
	}

	/*
	 * 更新主界面的表盘数据显示
	 */
	public void TagInfoUpdate() {
	}

	/*
	 * 清空数据时调用
	 */
	public void cleardata() {

		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);

		tagCount = 0;
		startReadTime = System.currentTimeMillis();
		if (this.tagInfo != null) {
			tagInfo.avgSpeed = 0;
			tagInfo.scanTotal = 0;
			tagInfo.immediateSpeed = 0;

			tagInfo.intAvgSpeed.setText("0");
			tagInfo.intCurSpeed.setText("0");
			tagInfo.intTagSum.setText("0");
			tagInfo.vistaCPUInfo.cpu = 0;
			tagInfo.vistaCPUInfo.mem = 0;
			tagInfo.intScanTime.setText("00.00.00");
			tagInfo.intScanum.setText("0");

			totalCount = 0;
			tagCount = 0;
			lastTotal = 0;
		}
	}

	public void startRead() {

		/* xusheng 2012.4.23 */
		this.lastTotal = 0;
		/* xusheng 2012.4.23 */
		if (timer.isRunning())
			return;

		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);
		// total.setText("0");
		rate = 0;
		// timerl.setText("00.00.00");

		startReadTime = System.currentTimeMillis();
		totalCount = 0;
		tagCount = 0;
		timer.start();
	}

	public void stopRead() {
		timer.stop();
	}

	// 设定读取读卡器的信息
	public void addReader(Demo demo) {
		if (!demo.getReader().onMessageNotificationReceived.contains(this))
			demo.getReader().onMessageNotificationReceived.add(this);
	}

	// 取消读卡器的设定
	public void removeReader(Demo demo) {
		demo.getReader().onMessageNotificationReceived.remove(this);
	}

	// 设定读取读卡器的信息
	public void addReader(Reader r) {
		if (!r.onMessageNotificationReceived.contains(this))
			r.onMessageNotificationReceived.add(this);
	}

	// 取消读卡器的设定
	public void removeReader(Reader r) {
		r.onMessageNotificationReceived.remove(this);
	}

	static int i;

	public EightCenterNorthJPanel eightCenterNorthJPanel;

	private int count = 0;

	// 读取数据后，会触发到的事件
	@Override
	public void messageNotificationReceivedHandle(BaseReader baseReader,
			IMessageNotification msg) {
		Reader reader = (Reader) baseReader;
		// String readerFullName = reader.getReaderName() + "--" +
		// reader.getReaderGroup();
		String readerFullName = reader.getReaderName();

		readTagTime = Common.getSystemTime();
		String reserve = "";
		String msgType = msg.getClass().getSimpleName();

		if (msgType.equals("RXD_IOTriggerSignal_800")) {
			RXD_IOTriggerSignal_800 m = (RXD_IOTriggerSignal_800) msg;
			Demo demo = DemoRegistry.getAllDemo().get(reader.getReaderName());
			boolean flagReading = false;
			for (Demo tdemo : DemoRegistry.getAllDemo().values()) {
				if (tdemo.getNode().isSelected()
						&& tdemo.getReader().isConnected()) {
					if (tdemo.isReading()) {
						flagReading = true;
					}
					break;
				}
			}
			if (demo != null) {
				if (m.getReceivedMessage().isStart()) {
					if ((frame).ModeButtonPanel.currentMode.equals("pic_Demo"))
						setRead(true);
					else {
						setRead(false);
						return;
					}
					demo.setReading(true);
				} else {
					demo.setReading(false);
				}
				if (demo.isReading()) {
					if (!flagReading) {

						// 清空
						cleardata();
						timer.start();
						frame.UIUpdateWhenDemoConnected();
						((MainFrame) frame).ModeButtonPanel.lblScanMode
								.setEnabled(false);
						((MainFrame) frame).ModeButtonPanel
								.setButtonDisable(
										((MainFrame) frame).ModeButtonPanel.lblScanMode,
										false);
						((MainFrame) frame).ModeButtonPanel.lblScanMode
								.removeMouseListener(((MainFrame) frame).ModeButtonPanel.Scan_MouseListener);
						((MainFrame) frame).ModeButtonPanel.lblTestMode
								.setEnabled(false);
						((MainFrame) frame).ModeButtonPanel
								.setButtonDisable(
										((MainFrame) frame).ModeButtonPanel.lblTestMode,
										false);
						((MainFrame) frame).ModeButtonPanel.lblTestMode
								.removeMouseListener(((MainFrame) frame).ModeButtonPanel.Test_MouseListener);
					}
				} else {
					timer.stop();
					((MainFrame) frame).ModeButtonPanel.lblScanMode
							.setEnabled(true);
					((MainFrame) frame).ModeButtonPanel.setButtonDisable(
							((MainFrame) frame).ModeButtonPanel.lblScanMode,
							true);
					((MainFrame) frame).ModeButtonPanel.lblScanMode
							.addMouseListener(((MainFrame) frame).ModeButtonPanel.Scan_MouseListener);
					((MainFrame) frame).ModeButtonPanel.lblTestMode
							.setEnabled(true);
					((MainFrame) frame).ModeButtonPanel.setButtonDisable(
							((MainFrame) frame).ModeButtonPanel.lblTestMode,
							true);
					((MainFrame) frame).ModeButtonPanel.lblTestMode
							.addMouseListener(((MainFrame) frame).ModeButtonPanel.Test_MouseListener);
					frame.UIUpdateWhenDemoConnected();
				}

			}
		}
		if (!isRead)
			return;
		if (msgType.equals("RXD_TagData")) {
			RXD_TagData m = (RXD_TagData) msg;
			RXD_TagData.ReceivedInfo receivedInfo = m.getReceivedMessage();

			byte[] bRssi = receivedInfo.getRSSI();
			String rssi = "";
			if (bRssi != null) {
				if (reader.getModelNumber().indexOf("502E") != -1
						|| reader.getModelNumber().indexOf("811") != -1) {
					rssi = Util.convertByteToHexWordString(bRssi[0])
							+ Util.convertByteToHexWordString(bRssi[1]);
				} else {
					rssi = Util.convertByteToHexWordString(bRssi[0]);
				}
			}
			String userData = "";
			if (Common.UserData_ASCII) {
				userData = new String(receivedInfo.getUserData());
			} else {
				userData = Util.convertByteArrayToHexWordString(receivedInfo
						.getUserData());
			}
			String rxdTime = Util.readTimeToString(receivedInfo.getRXDTime());
			String nowString = Common.getSystemTime();

			if (!"".equals(rxdTime)) {
				nowString = rxdTime;
			}
			String epc = "";
			String pc = "";
			if (Common.UserData_ASCII) {
				epc = new String(receivedInfo.getEPC());
				pc = new String(receivedInfo.getEPC_PC());
			} else {
				epc = Util.convertByteArrayToHexWordString(receivedInfo
						.getEPC());
				pc = Util.convertByteArrayToHexWordString(receivedInfo
						.getEPC_PC());
			}
			if (!"".equals(pc)) {
				epc = epc + "(" + pc.trim() + ")";
			}
			addTag(readerFullName, receivedInfo.getTagType(), epc, Util
					.convertByteArrayToHexWordString(receivedInfo.getTID()),
					userData, receivedInfo.getAntenna(), rssi, nowString, Util
							.convertByteArrayToHexWordString(receivedInfo
									.getReserved()));
			Common.myBeep();
			Log.debug(Util.convertByteArrayToHexWordString(receivedInfo
					.getTID()));
		} else if ("EasAlarm_6C".equals(msgType)) {
			EasAlarm_6C m = (EasAlarm_6C) msg;
			if (m.getStatusCode() == 0) {
				int answerType = (m.getReceivedMessage().getAnswerType()) & 0XFF;
				if (answerType == 0xA0) {
					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getLblTagAlarm().setVisible(true);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
					String prifix = BaseMessages
							.getString("DemoMode.lbl_TagAlarm");
					StringBuilder sb = new StringBuilder(prifix);
					for (int i = 0; i < count / 3; i++) {
						sb.append(".");
					}
					count++;
					if (count == 90) {
						count = 0;
					}
					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getLblTagAlarm().setText(sb.toString());

					eightCenterNorthJPanel.getCenterNorthOneJPanel()
							.getLblTagAlarm().setVisible(false);
				} else {
					Log.debug(m.getErrInfo());
				}
			}
		} else if (msgType.equals("PcSendTime_500")) {
			PcSendTime_500 m = (PcSendTime_500) msg;
			if (m.getStatusCode() == 0) {
				int d = (int) (System.currentTimeMillis() / 1000);
				byte[] time = new byte[4];
				time[0] = (byte) (d >> 24);
				time[1] = (byte) ((d >> 16) & 0xFF);
				time[2] = (byte) ((d >> 8) & 0xFF);
				time[3] = (byte) (d & 0xFF);

				PcSendTime_500 order = new PcSendTime_500(m
						.getReceivedMessage().getReaderID(), time);
				reader.setCommConnect(true);
				reader.send(order);
			} else {
				Log.debug(m.getErrInfo());
			}
		} else if (msgType.equals("EasAlarm_6C")) {
			EasAlarm_6C m = (EasAlarm_6C) msg;
			if (m.getReceivedMessage().getAnswerType() == 0xa0) {
			}

		} else if (msgType.equals("PcSendTime_500")) {
		} else if (msgType.equals("RXD_IOSignal_800")) {

		} else if (msgType.equals("Keepalive")) {

			long time = System.currentTimeMillis();
			int s = (int) (time / 1000);
			int ms = (int) (time % 1000);
			byte[] data = new byte[8];

			ByteBuffer byteBuff = ByteBuffer.allocate(8);
			IntBuffer buff = byteBuff.asIntBuffer();
			buff.put(s);
			buff.put(ms);
			buff.flip();
			byteBuff.get(data);

			reader.send(new Keepalive(((Keepalive) msg).getReceivedMessage()
					.getSequence(), data));
		}

	}

	public void addTag(String readerName, String tagType, String epc,
			String tid, String userData, int antenna, String rssi,
			String readTime, String resever) {
		synchronized (centerTable) {
			totalCount++;
			boolean isExist = false;
			DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
					.getModel();
			int rowcount = defaultTableModel.getRowCount();

			for (int i = 0; i < rowcount; i++) {

				if (!userData.equals("")) {
					if (getStringValueAt(i, 0).equals(readerName)
							&& getStringValueAt(i, 2).equals(epc)
							&& getStringValueAt(i, 3).equals(tid)
							&& getStringValueAt(i, 4).equals(userData))
						isExist = true;
				} else {
					if (getStringValueAt(i, 0).equals(readerName)
							&& !epc.equals("")
							&& getStringValueAt(i, 2).equals(epc))
						isExist = true;
					if (getStringValueAt(i, 0).equals(readerName)
							&& getStringValueAt(i, 1).equals(tagType)
							&& !tid.equals("")
							&& getStringValueAt(i, 3).equals(tid))
						isExist = true;
				}

				if (isExist) {
					centerTable.setValueAt(String.valueOf(Integer
							.parseInt(getStringValueAt(i, 5)) + 1), i, 5);
					centerTable.setValueAt(String.valueOf(Integer
							.parseInt(getStringValueAt(i, 5 + antenna)) + 1),
							i, 5 + antenna);
					centerTable.setValueAt(rssi, i, 10);
					centerTable.setValueAt(readTime, i, 11);
					break;
				}

			}
			if (!isExist) {
				String[] rowData = new String[defaultTableModel
						.getColumnCount()];
				rowData[0] = readerName;
				rowData[1] = tagType;
				rowData[2] = epc;
				rowData[3] = tid;
				rowData[4] = userData;
				rowData[5] = "1";
				rowData[6] = "0";
				rowData[7] = "0";
				rowData[8] = "0";
				rowData[9] = "0";
				rowData[10] = rssi;
				rowData[11] = readTime;
				rowData[5 + antenna] = "1";
				rowData[12] = resever;
				defaultTableModel.addRow(rowData);
				tagCount++;

			}

		}

	}

	public String getStringValueAt(int row, int column) {
		return getStringValueAt(centerTable, row, column);
	}

	public String getStringValueAt(JTable table, int row, int column) {
		return (String) table.getValueAt(row, column);
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public long getStartReadTime() {
		return startReadTime;
	}

	public void setStartReadTime(long startReadTime) {
		this.startReadTime = startReadTime;
	}

	public int[] getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(int[] columnWidth) {
		this.columnWidth = columnWidth;
	}

}
