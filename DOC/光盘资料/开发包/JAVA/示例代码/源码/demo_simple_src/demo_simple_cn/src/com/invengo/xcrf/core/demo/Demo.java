package com.invengo.xcrf.core.demo;

import invengo.javaapi.core.ErrInfoList;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.EasAlarm_6C;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.panel.LogPanel;
import com.invengo.xcrf.ui.panel.MainDemoPanel;
import com.invengo.xcrf.ui.tree.CheckNode;
import com.invengo.xcrf.ui.tree.RootTree;

public class Demo {
	private static Timer reconnTimer = new Timer();
	private Timer checkConnTimer = new Timer();

	private String demoName;
	private String groupName;
	private Reader reader;
	private UserConfig config;
	private boolean isReading;
	private CheckNode node;
	private RFIDProtocol protocl;
	private boolean rssiEnable_800;
	private boolean utcEnable_800;
	private boolean eas;
	private boolean isTryReconnNet;
	private int tryReconnNetTimeSpan = 1;

	private TimerTask reconn;

	private TimerTask timerCheckConn;

	public boolean getIsReading() {
		return isReading;
	}

	public void scheduleReconnTask() {
		if (!config.getConnType().equals("TCPIP_Client")) {
			return;
		}
		if (!isTryReconnNet)
			return;

		reconn = new TimerTask() {

			long startTime = System.currentTimeMillis();

			@Override
			public void run() {
				while (!reader.isConnected()
						&& System.currentTimeMillis() - startTime <= tryReconnNetTimeSpan * 60000L) {
					try {
						if (!Common.pingTest(config.getConnStr())) {
							reader.disConnect();
							LogPanel.info(BaseMessages.getString("",
									"Message.MSG_113", reader.getReaderName()));
						} else if (reader.connect()) {
							LogPanel.info(BaseMessages.getString("",
									"Message.MSG_111", reader.getReaderName()));
							RootTree.getTree().updateUI();
							break;
						} else {
							LogPanel.info(BaseMessages.getString("",
									"Message.MSG_112", reader.getReaderName()));
						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}

					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		reconnTimer.schedule(reconn, 2000);

	}

	public void CheckConnTask() {
		if (!config.getConnType().equals("TCPIP_Client")) {
			return;
		}
		if (isTryReconnNet)
			return;

		timerCheckConn = new TimerTask() {

			@SuppressWarnings("unused")
			long startTime = System.currentTimeMillis();

			@Override
			public void run() {
				while (reader.isConnected()) {

					if (!Common.pingTest(config.getConnStr())) {

						reader.setCommConnect(false);

						Util.logAndTriggerApiErr(reader.getReaderName(),
								"FF22", ErrInfoList.errMap.get("FF22")
										.getErrMsg(), LogType.Warn);
						checkConnTimer.cancel();
					}

					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
					}
				}
			}
		};

		checkConnTimer.schedule(timerCheckConn, 2000);

	}

	public boolean cancelReconnTask() {
		if (reconn == null) {
			return true;
		}
		return reconn.cancel();
	}

	public boolean isTryReconnNet() {
		return isTryReconnNet;
	}

	public void setTryReconnNet(boolean isTryReconnNet) {
		this.isTryReconnNet = isTryReconnNet;
	}

	public int getTryReconnNetTimeSpan() {
		return tryReconnNetTimeSpan;
	}

	public void setTryReconnNetTimeSpan(int tryReconnNetTimeSpan) {
		this.tryReconnNetTimeSpan = tryReconnNetTimeSpan;
	}

	public boolean isUtcEnable_800() {
		return utcEnable_800;
	}

	public void setUtcEnable_800(boolean utcEnable_800) {
		this.utcEnable_800 = utcEnable_800;
	}

	public boolean isRssiEnable_800() {
		return rssiEnable_800;
	}

	public void setRssiEnable_800(boolean rssiEnable_800) {
		this.rssiEnable_800 = rssiEnable_800;
	}

	public void setEas(boolean eas) {
		this.eas = eas;
	}

	public boolean isEas() {
		return eas;
	}

	public CheckNode getNode() {
		return node;
	}

	public void setNode(CheckNode node) {
		this.node = node;
	}

	public UserConfig getConfig() {
		return config;
	}

	public UserConfig_IRP1 getConfig_IRP1() {
		if (config instanceof UserConfig_IRP1)
			return (UserConfig_IRP1) config;
		else
			return null;
	}

	public void setConfig(UserConfig config) {
		this.config = config;
	}

	public boolean isReading() {
		return isReading;
	}

	public void setReading(boolean isReading) {
		this.isReading = isReading;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getDemoName() {
		return demoName;
	}

	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RFIDProtocol getProtocl() {
		return protocl;
	}

	public Demo() {
	}

	public Demo(UserConfig config) {
		this.demoName = config.getReaderName();
		this.groupName = config.getReaderGroup();

		String connType = "RS232";
		if (!connType.equals(config.getConnType()))
			connType = "NetSocket";

		this.reader = new Reader(demoName, connType, config.getConnStr());
		this.reader.setReaderGroup(groupName);
		this.reader.setModelNumber(config.getReaderType());
		this.config = config;
		this.protocl = config.getProtocol();
	}

	public Demo(UserConfig config, Reader reader) {
		this.demoName = reader.getReaderName();
		this.groupName = reader.getReaderGroup();
		this.reader = reader;
		this.config = config;
		this.protocl = config.getProtocol();
	}

	public boolean connect() throws UnsupportedEncodingException {
		if (reader != null && !reader.isConnected() && config.isEnable()) {

			cancelReconnTask();

			boolean connected = reader.connect();
			if (connected) {

				DemoRegistry.registryCurrentDemo(this);

				if (this.getProtocl() == RFIDProtocol.IRP1
						&& this.reader.getModelNumber().equals("unknown")) {
					String mn;
					SysQuery_800 msg = new SysQuery_800((byte) 0x20);
					if (this.reader.send(msg, 300)) {
						mn = new String(msg.getReceivedMessage().getQueryData());
						if (this.getConfig() instanceof UserConfig_IRP1) {
							this.reader.setModelNumber(mn);
						}
						if (mn.indexOf("XC") == -1) {
							if (this.getConfig().getReaderType() == "800") {
								SysQuery_800 msg1 = new SysQuery_800(
										(byte) 0x21);
								if (this.reader.send(msg1, 300)) {
									String smn = new String(msg1
											.getReceivedMessage()
											.getQueryData(), "ASCII");
									if (this.getConfig() instanceof UserConfig_IRP1) {
										String ssmn = smn.substring(0, smn
												.length() - 1);
										String V = ssmn;
										StringBuilder ver = new StringBuilder(
												"");
										for (int i = 0; i < V.length() - 1; i++) {
											char s[] = V.substring(i, i + 1)
													.toCharArray();
											if (s[0] >= '0' && s[0] <= '9') {
												ver.append(s);
											}
										}
									}
									if (this.reader.getModelNumber().indexOf(
											"XC") == -1) {
										switch (Integer.valueOf(this.reader
												.getModelNumber())) {
										case 806:
											break;
										case 860:
											break;
										default:
											break;
										}
									}
								}
							}
						}
					}
				}
				if (this.getProtocl() == RFIDProtocol.IRP1) {
					SysQuery_800 order = new SysQuery_800((byte) 0x14);
					if (this.getReader().send(order, 300)) {
						if (order.getReceivedMessage().getQueryData() != null
								&& order.getReceivedMessage().getQueryData().length > 0) {
							if (order.getReceivedMessage().getQueryData()[0] == 0x01)
								this.setRssiEnable_800(true);
							else
								this.setRssiEnable_800(false);
						}
					} else
						this.setRssiEnable_800(false);
				}

				SysQuery_800 order = new SysQuery_800((byte) 0x18);
				if (this.getReader().send(order, 200)) {
					if (order.getReceivedMessage().getQueryData() != null
							&& order.getReceivedMessage().getQueryData().length > 0) {
						if (order.getReceivedMessage().getQueryData()[0] == 0x01)
							this.setUtcEnable_800(true);
						else
							this.setUtcEnable_800(false);
					}
				} else
					this.setUtcEnable_800(false);

				String msg = BaseMessages.getString("", "Message.MSG_68",
						getDemoName());
				LogPanel.info(msg);
				CheckConnTask();

			} else {
				String msg = BaseMessages.getString("", "Message.MSG_67",
						getDemoName());
				LogPanel.error(msg);

			}
			return connected;
		}
		return false;
	}

	public void disConnect() {
		if (reader != null && reader.isConnected()) {

			cancelReconnTask();

			reader.disConnect();
			DemoRegistry.removeRegistryCurrentDemo(this);
			MainDemoPanel.getReadDataPanel().removeReader(this);
			String msg = BaseMessages.getString("", "Message.MSG_62", reader
					.getReaderName());
			LogPanel.info(msg);
			this.checkConnTimer.cancel();

		}
	}

	public void forceDisConnect() {
		if (reader != null && reader.isConnected() && isReading) {

			cancelReconnTask();

			stopRead();
			reader.disConnect();
			DemoRegistry.removeRegistryCurrentDemo(this);
			MainDemoPanel.getReadDataPanel().removeReader(this);
			String msg = BaseMessages.getString("", "Message.MSG_62", reader
					.getReaderName());
			LogPanel.info(msg);
		}
	}

	public void readTag() {
		if (reader != null && reader.isConnected() && !isReading
				&& config != null && config.getStartRoSpec() != null) {
			if (config.getActiveAntenna() != null) {
				reader.send(config.getActiveAntenna());
			}

			if (eas) {

				EasAlarm_6C msg = new EasAlarm_6C((byte) (config
						.getAntennaIndex()), (byte) 0x01);
				if (reader.send(msg)) {
					isReading = true;
				}
			} else {
				boolean send = reader.send(config.getStartRoSpec());
				if (send) {
					isReading = true;
				}
			}

			if (ConfigurationDialog.getInstance() != null)
				ConfigurationDialog.getInstance().lockNavigatorPanel();

		}
	}

	public void stopRead() {
		if (reader != null && reader.isConnected() && isReading
				&& config != null && config.getStopRoSpec() != null) {
			if (reader.send(config.getStopRoSpec())) {
				isReading = false;
			}
		}

		boolean hasDemoIsReading = false;
		for (Demo demo : DemoRegistry.getCurrentDemos().values()) {
			if (demo.isReading) {
				hasDemoIsReading = true;
				break;
			}
		}

		if (!hasDemoIsReading) {
			MainDemoPanel.getReadDataPanel().stopRead();
		}

		if (ConfigurationDialog.getInstance() != null)
			ConfigurationDialog.getInstance().unlockNavigatorPanel();

	}

}
