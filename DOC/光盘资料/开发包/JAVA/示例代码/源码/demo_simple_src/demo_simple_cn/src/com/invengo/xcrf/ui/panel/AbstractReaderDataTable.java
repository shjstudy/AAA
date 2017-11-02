package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.Log;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.PcSendTime_500;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.Reader;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.panel.readerConfig.ConfigPanel;

public abstract class AbstractReaderDataTable extends JPanel implements
		IMessageNotificationReceivedHandle, ConfigPanel {

	protected JTable centerTable;

	protected int count;// 读到标签的个数

	protected int totalCount;

	protected Demo currentDemo;

	protected byte[] lock = new byte[0];

	private String epcStr;

	protected long totalTime;// 总计耗时

	protected AbstractReaderDataTable() {
		super();
		// 添加数据表格
		createTable();
	}

	// 设定读取读卡器的信息
	protected void addReader() {
		if (!currentDemo.getReader().onMessageNotificationReceived
				.contains(this))
			currentDemo.getReader().onMessageNotificationReceived.add(this);
	}

	// 取消读卡器的设定
	protected void removeReader() {
		currentDemo.getReader().onMessageNotificationReceived.remove(this);
		this.count = 0;
		totalCount = 0;
		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		if (model.getRowCount() > 0) {
			centerTable.getSelectionModel().setSelectionInterval(0, 0);
		}
		totalTime = 0;
	}

	public void fillConfigData() {
		currentDemo = DemoRegistry.getCurrentDemo();
		addReader();
		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);
	}

	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "centerTable.title800");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader baseReader,
			IMessageNotification msg) {
		Reader reader = (Reader) baseReader;
		/* xusheng 2012.4.25 */
		String readTagTime = Common.getSystemTime();
		/* xusheng 2012.4.25 */
		String readerFullName = reader.getReaderName();

		String msgType = msg.getClass().getSimpleName();
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
					userData, receivedInfo.getAntenna(), rssi, nowString);
			Log.debug(Util.convertByteArrayToHexWordString(receivedInfo
					.getTID()));
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
				reader.send(order);
			} else {
				Log.debug(m.getErrInfo());
			}
		}
	}

	protected String getEpcString() {
		return epcStr;
	}

	public void addTag(String readerName, String tagType, String epc,
			String tid, String userData, int antenna) {
		synchronized (centerTable) {
			/* xusheng 2012.4.23 */
			totalCount++;
			/* xusheng 2012.4.23 */
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
				}

			}
			if (!isExist) {
				count++;
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
				rowData[5 + antenna] = "1";
				if (isAddRow())
					defaultTableModel.addRow(rowData);
			}
		}
	}

	public String getStringValueAt(int row, int column) {
		return getStringValueAt(centerTable, row, column);
	}

	public String getStringValueAt(JTable table, int row, int column) {
		return (String) table.getValueAt(row, column);
	}

	public void addTagToTable(int columnIndex, String tag, int ant,
			String readerName) {
		synchronized (centerTable) {
			boolean ishave = false;
			DefaultTableModel defaultTableModel = (DefaultTableModel) centerTable
					.getModel();
			int rowcount = defaultTableModel.getRowCount();

			for (int i = 0; i < rowcount; i++) {
				String tagdata = (String) defaultTableModel.getValueAt(i,
						columnIndex);
				String ownerReader = (String) defaultTableModel
						.getValueAt(i, 0);
				if (tag.equals(tagdata) && ownerReader.equals(readerName)) {
					String total = (String) defaultTableModel.getValueAt(i, 5);
					int inttotal = Integer.parseInt(total);
					centerTable.setValueAt(String.valueOf(inttotal + 1), i, 5);
					String antone = (String) defaultTableModel.getValueAt(i,
							5 + ant);
					int intantone = 0;
					if (antone != null) {
						intantone = Integer.parseInt(antone);
					}
					centerTable.setValueAt(String.valueOf(intantone + 1), i,
							5 + ant);
					ishave = true;
					break;
				}
			}
			if (!ishave) {
				count++;
				String[] rowData = new String[defaultTableModel
						.getColumnCount()];
				rowData[0] = readerName;
				rowData[columnIndex] = tag;
				rowData[5] = "1";
				rowData[5 + ant] = "1";
				if (isAddRow())
					defaultTableModel.addRow(rowData);
			}
		}
	}

	public void addTag(String readerName, String tagType, String epc,
			String tid, String userData, int antenna, String rssi,
			String readTagTime) {
		synchronized (centerTable) {
			/* xusheng 2012.4.23 */
			totalCount++;
			/* xusheng 2012.4.23 */
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
					centerTable.setValueAt(readTagTime, i, 11);
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
				rowData[11] = readTagTime;
				rowData[5 + antenna] = "1";
				if (isAddRow())
					defaultTableModel.addRow(rowData);
				count++;
			}
		}
	}

	protected boolean isAddRow() {
		return true;
	}
}
