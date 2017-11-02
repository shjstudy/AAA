package com.invengo.xcrf.core.demo;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.PowerOff;
import invengo.javaapi.protocol.IRP1.ReadTag;
import invengo.javaapi.protocol.IRP1.SysConfig_500;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import javax.swing.JDialog;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.ui.dialog.CreateDialog;
import com.invengo.xcrf.ui.dialog.CreateReaderDialog;

public final class UserConfig_IRP1 extends UserConfig {
	// public Int32 AntennaIndex;
	// public Int32 TagNum;
	// public Int32 ReadTime;
	// public Int32 StopTime;

	protected final RFIDProtocol realProtocol = RFIDProtocol.IRP1;
	protected String readerType;
	protected ReadMemoryBank rmb;
	protected int antennaIndex;
	protected int tagNum;
	protected boolean isLoop;
	protected int readTime;
	protected int stopTime;
	protected String stopType;
	protected byte tidLen;
	protected byte userDataPtr;
	protected byte userDataLen;

	protected String serverPort;

	protected byte UserdataPtr_6B;
	protected byte UserDataLen_6B;
	protected byte ReservedLen;
	protected byte[] AccessPwd;
	protected byte ReadTimes_6C;
	protected byte ReadTimes_6B;

	public byte getUserdataPtr_6B() {
		return UserdataPtr_6B;
	}

	public void setUserdataPtr_6B(byte userdataPtr_6B) {
		UserdataPtr_6B = userdataPtr_6B;
	}

	public byte getUserDataLen_6B() {
		return UserDataLen_6B;
	}

	public void setUserDataLen_6B(byte userDataLen_6B) {
		UserDataLen_6B = userDataLen_6B;
	}

	public byte getReservedLen() {
		return ReservedLen;
	}

	public void setReservedLen(byte reservedLen) {
		ReservedLen = reservedLen;
	}

	public byte[] getAccessPwd() {
		return AccessPwd;
	}

	public void setAccessPwd(byte[] accessPwd) {
		AccessPwd = accessPwd;
	}

	public byte getReadTimes_6C() {
		return ReadTimes_6C;
	}

	public void setReadTimes_6C(byte readTimes_6C) {
		ReadTimes_6C = readTimes_6C;
	}

	public byte getReadTimes_6B() {
		return ReadTimes_6B;
	}

	public void setReadTimes_6B(byte readTimes_6B) {
		ReadTimes_6B = readTimes_6B;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	/**
	 * @return the serverPort
	 */
	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	@Override
	public void config(JDialog dialog) {

		if (dialog instanceof CreateDialog) {
			CreateDialog cd = (CreateDialog) dialog;
			readerName = cd.getReadNameField().getText();
			readerGroup = cd.getGroupNameComBox().getEditor().getItem()
					.toString();
			enable = cd.getEnableCheckBox().isSelected();
			if (cd.getModelTypeComboBox().getSelectedIndex() != -1) {
				modelNo = cd.getModelTypeComboBox().getSelectedItem()
						.toString();
			}

			if (cd.getRdbtnTcpip().isSelected()) {
				connType = "TCPIP_Client";
				String ip = cd.getIpField().getIpAddress();
				String port = cd.getClientPortEditor().getTextField().getText();
				connStr = ip + ":" + port;
			} else if (cd.getRdbtnRs().isSelected()) {
				connType = "RS232";
				String comPort = cd.getCommBox().getSelectedItem().toString();
				String baudRate = cd.getCommbtBox().getSelectedItem()
						.toString();
				connStr = comPort + "," + baudRate;
			} else if (cd.getRdbtnUsb().isSelected()) {
				connStr = "\\\\?\\usb#vid_8086&amp;pid_feed#serialnum01#{48c602d4-c77e-45b9-8133-20c9683bd1a6}";
				connType = "USB";
			} else if (cd.getRdbtnTcpipServer().isSelected()) {
				connStr = "";
				connType = "TCPIP_Server";
				serverPort = cd.getServerPortEditor().getTextField().getText();
			}
			protocol = realProtocol;
			readerType = cd.getReaderTypeComboBox().getSelectedItem()
					.toString();
			rmb = ReadMemoryBank.valueOf(cd.getMsgTypeComboBox()
					.getSelectedItem().toString());
			if (cd.getAntennaComboBox().isVisible()) {
				antennaIndex = cd.getAntennaComboBox().getSelectedIndex();
			} else {
				antennaIndex = Common.booleanArrayToInteger(cd
						.getAntentaCheckBoxAry());
			}

			isLoop = cd.getLoopButton().isSelected();
			tagNum = Integer.parseInt(cd.getNumberTagEditor().getTextField()
					.getText());
			readTime = Integer.parseInt(cd.getNumReadTimeEditor()
					.getTextField().getText());
			stopTime = Integer.parseInt(cd.getNumStopTimeEditor()
					.getTextField().getText());
			stopType = cd.getStopTypeComboBox().getSelectedItem().toString();

			setStartAndStopSospec();
		} else {
			CreateReaderDialog cd = (CreateReaderDialog) dialog;

			readerName = cd.getTfdReaderId().getText();
			readerGroup = cd.getGroupNameComBox().getEditor().getItem()
					.toString();
			enable = true;
			if (cd.getModelTypeComboBox().getSelectedIndex() != -1) {
				modelNo = cd.getModelTypeComboBox().getSelectedItem()
						.toString();
			}

			if (cd.getRadioButton().isSelected()) {
				if (cd.getRbnTcpIp().isSelected()) {
					connType = "TCPIP_Client";
					String ip = cd.getTfdTcpIp().getIpAddress();
					String port = cd.getSpnPort().getValue().toString();
					connStr = ip + ":" + port;
				} else if (cd.getRbnRS232().isSelected()) {
					connType = "RS232";
					String comPort = cd.getCmbPort2().getSelectedItem()
							.toString();
					String baudRate = cd.getCmbBote().getSelectedItem()
							.toString();
					connStr = comPort + "," + baudRate;
				} else if (cd.getRbnUSB().isSelected()) {
					connStr = "\\\\?\\usb#vid_8086&amp;pid_feed#serialnum01#{48c602d4-c77e-45b9-8133-20c9683bd1a6}";
					connType = "USB";
				}
			} else if (cd.getRadioButton_1().isSelected()) {// ·þÎñÆ÷
				connStr = "";
				connType = "TCPIP_Server";
				serverPort = cd.getSpinner_5_port().getValue().toString();
			}
			protocol = realProtocol;
			readerType = cd.getStopType();
			rmb = ReadMemoryBank.valueOf(cd.getMsgTypeComboBox()
					.getSelectedItem().toString());
			if (cd.getAntennaComboBox().isVisible()) {
				antennaIndex = cd.getAntennaComboBox().getSelectedIndex();
			} else {
				antennaIndex = Common.booleanArrayToInteger(cd
						.getAntentaCheckBoxAry());
			}

			isLoop = cd.getLoopButton().isSelected();
			tagNum = Integer.parseInt(cd.getNumberTagEditor().getTextField()
					.getText());
			readTime = Integer.parseInt(cd.getNumReadTimeEditor()
					.getTextField().getText());
			stopTime = Integer.parseInt(cd.getNumStopTimeEditor()
					.getTextField().getText());
			stopType = cd.getStopType();

			setStartAndStopSospec();
		}

	}

	public void setStartAndStopSospec() {
		ReadTag rt = null;
		if (stopTime > 0) {
			rt = new ReadTag(rmb, readTime, stopTime);
			if (stopType.equals("500"))
				stopRoSpec = new PowerOff();
			if (stopType.equals("800"))
				stopRoSpec = new PowerOff();
			stopRoSpec.setIsReturn(false);
		} else {
			rt = new ReadTag(rmb);
			if (stopType.equals("500"))
				stopRoSpec = new PowerOff();
			if (stopType.equals("800"))
				stopRoSpec = new PowerOff();
			stopRoSpec.setIsReturn(false);
		}

		if (rmb == ReadMemoryBank.EPC_TID_UserData_6C_2) {
			rt.setTidLen(tidLen);
			rt.setUserDataPtr_6C(userDataPtr);
			rt.setUserDataLen_6C(userDataLen);
		}
		/**
		 * xusheng 2012.7.17
		 */
		{
			rt.setTidLen(tidLen);
			rt.setUserDataLen_6B(UserDataLen_6B);
			rt.setUserDataPtr_6B(UserdataPtr_6B);
			rt.setReservedLen(ReservedLen);
			if (AccessPwd != null)
				rt.setAccessPwd(AccessPwd);
			rt.setReadTimes_6C(ReadTimes_6C);
			rt.setReadTimes_6B(ReadTimes_6B);
		}
		if (antennaIndex > 0x80) {
			rt.setAntenna((byte) antennaIndex);
			activeAntenna = null;
		} else {
			if (stopType.equals("500")) {
				rt.setAntenna((byte) ((antennaIndex + 1) % 3));
				activeAntenna = new SysConfig_500((byte) 0x02, (byte) 0x01,
						new byte[] { ((byte) (antennaIndex + 1)) });
			}
			if (stopType.equals("800")) {
				rt.setAntenna((byte) (antennaIndex + 1));
				if (rt.getAntenna() > 4)
					rt.setAntenna((byte) 0x00);

				byte[] d = new byte[1];
				if (rt.getAntenna() > 0)
					d[0] = 0x01;
				else {
					d[0] = (byte) (antennaIndex - 2);
				}
				activeAntenna = new SysConfig_800((byte) 0x02, d);
			}
		}

		rt.setLoop(isLoop);
		rt.setQ((byte) (Math.log((double) tagNum + 1) / Math.log(2)));
		startRoSpec = rt;

	}

	@Override
	public boolean saveConfig() {
		return UserConfigUtil.saveUserConfigIRP1ToXmlFile(this);
	}

	@Override
	public boolean updateConfig() {

		return UserConfigUtil.updateUserConfigIRP1ToXmlFile(this);
	}

	@Override
	public void removeConfig() {
		UserConfigUtil.removeUserConfigIRP1ToXmlFile(this);
	}

	@Override
	public String getReaderType() {
		return readerType;
	}

	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}

	public ReadMemoryBank getRmb() {
		return rmb;
	}

	public void setRmb(ReadMemoryBank rmb) {
		this.rmb = rmb;
	}

	@Override
	public int getAntennaIndex() {
		return antennaIndex;
	}

	public void setAntennaIndex(int antennaIndex) {
		this.antennaIndex = antennaIndex;
	}

	public int getTagNum() {
		return tagNum;
	}

	public void setTagNum(int tagNum) {
		this.tagNum = tagNum;
	}

	public boolean getIsLoop() {
		return isLoop;
	}

	public void setIsLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public int getReadTime() {
		return readTime;
	}

	public void setReadTime(int readTime) {
		this.readTime = readTime;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopType() {
		return stopType;
	}

	public void setStopType(String stopType) {
		this.stopType = stopType;
	}

	public byte getTidLen() {
		return tidLen;
	}

	public void setTidLen(byte tidLen) {
		this.tidLen = tidLen;
	}

	public byte getUserDataPtr() {
		return userDataPtr;
	}

	public void setUserDataPtr(byte userDataPtr) {
		this.userDataPtr = userDataPtr;
	}

	public byte getUserDataLen() {
		return userDataLen;
	}

	public void setUserDataLen(byte userDataLen) {
		this.userDataLen = userDataLen;
	}

	public RFIDProtocol getRealProtocol() {
		return realProtocol;
	}

}
