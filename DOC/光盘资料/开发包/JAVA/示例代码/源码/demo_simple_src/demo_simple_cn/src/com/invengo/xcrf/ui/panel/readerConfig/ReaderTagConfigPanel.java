package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.FastReadTIDConfig_6C;
import invengo.javaapi.protocol.IRP1.FastReadTIDConfig_EpcLength;
import invengo.javaapi.protocol.IRP1.FixedTidLengthConfig_6C;
import invengo.javaapi.protocol.IRP1.ReadTagConfig_6B;
import invengo.javaapi.protocol.IRP1.ReadTagConfig_6C;
import invengo.javaapi.protocol.IRP1.ReadUnfixedTidConfig_6C;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.Const;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class ReaderTagConfigPanel extends JPanel implements ConfigPanel {

	private static final long serialVersionUID = -5447773812549779075L;

	private Demo showDemo;

	private JComboBox antenna500comboBox = new JComboBox(
			Const.ANTENNA_ITEMS_500);
	private JComboBox antenna800comboBox = new JComboBox(
			Const.ANTENNA_ITEMS_800);

	private JComboBox typeComboBox;
	private JComboBox antennacomboBox;
	private JSpinner tagNumSpinner;
	private JSpinner.NumberEditor tagNumEditor;

	private JSpinner readTimeSpinner;
	private JSpinner.NumberEditor readTimeEditor;

	private JSpinner stopTimeSpinner;
	private JSpinner.NumberEditor stopTimeEditor;

	private JComboBox stopTypeCombobox;
	private JSpinner tidLen_6c_2Spinner;
	private JSpinner.NumberEditor tidLen_6c_2Editor;

	private JSpinner userDataPtr_6c_2Spinner;
	private JSpinner.NumberEditor userDataPtr_6c_2Editor;

	private JSpinner userDataLen_6c_2Spinner;
	private JSpinner.NumberEditor userDataLen_6c_2Editor;

	private JSpinner userData_6bSpinner;
	private JSpinner.NumberEditor userData_6bEditor;

	private JCheckBox chckbxEpc;
	private JCheckBox chckbxTid;
	private JCheckBox chckbxUserdata;
	private JSpinner userDataLen_6cSpinner;
	private JSpinner.NumberEditor userDataLen_6cEditor;

	private JSpinner tidLen_6cSpinner;
	private JSpinner.NumberEditor tidLen_6cEditor;
	JLabel lblTagNum;
	private JRadioButton resizeOnBtn;
	private JRadioButton resizeOffBtn;
	private JSpinner tidLen_resizeableSpinner;
	private JSpinner.NumberEditor tidLen_resizeableEditor;

	private JRadioButton fastOnBtn;
	private JRadioButton fastOffBtn;
	private JSpinner epcLen_fastReadSpinner;
	private JSpinner.NumberEditor epcLen_fastReadEditor;

	private JRadioButton rdbtnLoop;
	private JRadioButton rdbtnSingle;
	private JPanel panel_3;
	private JPanel panel_2;

	private boolean isEnable = false;// 读变长TID配置
	private int tidLen = 0;// TID长度值
	private boolean isFastReadEnable = false;// 快读TID配置
	private int epcLen = 0;// EPC长度值

	private byte[] infoParam_6c;
	private byte infoParam_6b;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_1;
	JSpinner spnUserDataPreLength;

	private JCheckBox chkEAS;
	private JCheckBox chkRSSI;
	private JCheckBox chkUTC;
	private JSpinner spnTIDLength;
	private JSpinner spnUserDataLength;
	private JSpinner spnUserDataStartLength;

	JSpinner spnReadTimes_6C;
	JSpinner spnReseverLen;

	JPasswordField psw;

	JSpinner spnReadTimes_6B;
	JSpinner spnUserDataLength_6B;
	JSpinner spnUserData_6B_StartAdress;
	JLabel label_8;
	JLabel label_5;
	private JCheckBox cbxEPC;
	private JCheckBox cbxTID;
	private JCheckBox cbxUserData;
	private JPanel panel_9;

	private JCheckBox checkBox;

	private JCheckBox checkBox_1;

	private JCheckBox checkBox_2;

	private JCheckBox checkBox_3;

	private JPanel panel_6c_read;

	private JPanel panel_6B_read;

	private JPanel panel_6B_read_short;

	private JSpinner spnUserDataLength_6B_short;

	/**
	 * Create the panel.
	 */
	public ReaderTagConfigPanel() {
		// setBackground(Color.WHITE);
		setPreferredSize(new Dimension(640, 481));
		setLayout(null);
		setName("A1_ReadTagConfig");
		JLabel label = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label4"));
		label.setBounds(33, 20, 98, 12);
		add(label);

		typeComboBox = new JComboBox(Const.MSG_TYPES);
		typeComboBox.setBounds(141, 16, 286, 20);
		add(typeComboBox);

		chkEAS = new JCheckBox();
		chkEAS.setName("cb_Eas");
		chkRSSI = new JCheckBox();
		chkRSSI.setName("cb_Rssi");
		chkUTC = new JCheckBox();
		chkUTC.setName("cb_Utc");

		chkEAS.setText("EAS");
		chkEAS.setBounds(433, 16, 60, 20);
		chkEAS.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chkEASActionPerformed(evt);
			}
		});
		add(chkEAS);

		chkRSSI.setText("RSSI");
		chkRSSI.setBounds(491, 15, 60, 20);
		chkRSSI.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chkRSSIActionPerformed(evt);
			}
		});
		add(chkRSSI);

		chkUTC.setText("UTC");
		chkUTC.setBounds(550, 16, 60, 20);
		chkUTC.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chkUTCActionPerformed(evt);
			}
		});
		add(chkUTC);

		JPanel panel = new JPanel();
		panel.setName("A1_ReadTagConfig");
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox7"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(33, 67, 574, 107);
		add(panel);
		panel.setLayout(null);

		JLabel lblAntenna = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label5"));
		lblAntenna.setBounds(10, 35, 75, 15);
		panel.add(lblAntenna);

		JLabel lblReadertype = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label6"));
		lblReadertype.setBounds(10, 62, 75, 15);
		panel.add(lblReadertype);

		lblTagNum = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label7"));
		lblTagNum.setBounds(10, 83, 214, 15);
		panel.add(lblTagNum);

		antennacomboBox = antenna800comboBox;
		antennacomboBox.setName("cbAntenna");
		antenna800comboBox.setBounds(100, 33, 96, 20);
		panel.add(antenna800comboBox);

		panel_9 = new JPanel();
		panel_9.setVisible(false);
		panel_9.setBounds(96, 21, 209, 33);
		panel_9.setName("p_Antenna");
		panel.add(panel_9);
		panel_9.setLayout(null);

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

		antenna500comboBox.setBounds(100, 33, 96, 20);
		// panel.add(antenna500comboBox);

		ButtonGroup loopGroup = new ButtonGroup();
		rdbtnLoop = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.rbLoop"));
		rdbtnLoop.setBounds(98, 60, 102, 16);
		loopGroup.add(rdbtnLoop);
		panel.add(rdbtnLoop);

		rdbtnSingle = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.rbSingle"));
		rdbtnSingle.setBounds(205, 61, 84, 16);
		loopGroup.add(rdbtnSingle);
		panel.add(rdbtnSingle);

		tagNumSpinner = new JSpinner();

		tagNumSpinner.setModel(new SpinnerNumberModel(new Integer(8), null,
				null, new Integer(1)));
		tagNumEditor = new JSpinner.NumberEditor(tagNumSpinner, "#####");
		initNumEditor(tagNumEditor, "8");
		tagNumSpinner.setEditor(tagNumEditor);

		tagNumSpinner.setBounds(207, 80, 59, 20);
		panel.add(tagNumSpinner);

		JLabel lblStop = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label10"));
		lblStop.setBounds(332, 33, 127, 15);
		panel.add(lblStop);

		JLabel lblStopTime = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label11"));
		lblStopTime.setBounds(332, 60, 127, 15);
		panel.add(lblStopTime);

		JLabel lblStopType = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label9"));
		lblStopType.setBounds(394, 85, 65, 15);
		panel.add(lblStopType);

		readTimeSpinner = new JSpinner();
		readTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), null,
				null, new Integer(1)));
		readTimeEditor = new JSpinner.NumberEditor(readTimeSpinner, "#####");
		initNumEditor(readTimeEditor, "0");
		readTimeSpinner.setEditor(readTimeEditor);
		readTimeSpinner.setBounds(475, 29, 75, 20);
		panel.add(readTimeSpinner);

		stopTimeSpinner = new JSpinner();
		stopTimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), null,
				null, new Integer(1)));
		stopTimeEditor = new JSpinner.NumberEditor(stopTimeSpinner, "#####");
		initNumEditor(stopTimeEditor, "0");
		stopTimeSpinner.setEditor(stopTimeEditor);
		stopTimeSpinner.setBounds(475, 56, 75, 20);
		panel.add(stopTimeSpinner);

		stopTypeCombobox = new JComboBox(Const.STOP_TYPES);
		stopTypeCombobox.setBounds(475, 82, 75, 20);
		panel.add(stopTypeCombobox);

		JButton button = new JButton(BaseMessages
				.getString("A1_ReadTagConfig.btn_Query"));
		button.setBounds(390, 421, 75, 23);
		add(button);

		JButton button_1 = new JButton(BaseMessages
				.getString("A1_ReadTagConfig.btn_Config"));
		button_1.setBounds(475, 421, 127, 23);
		add(button_1);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fillConfigData();
			}

		});

		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modifyConfig();
			}

		});
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		if (currentDemo != null) {
			String protocol = currentDemo.getProtocl().toString();
			String modelNo = currentDemo.getConfig().getModelNo();
			if (modelNo != null && modelNo.indexOf("-") != -1)
				modelNo = modelNo.substring(modelNo.indexOf("-") + 1);
			String readerType = currentDemo.getConfig().getReaderType();
			try {
				Common.readerCapabilitiesCheck(this, protocol, modelNo,
						readerType);
			} catch (JDOMException e1) {
			} catch (IOException e1) {
			}

			try {
				Common.readerCapabilitiesCheck(panel, protocol, modelNo,
						readerType);
			} catch (JDOMException e1) {
			} catch (IOException e1) {
			}
		}
	}

	@Override
	public void fillConfigData() {

		Demo currentDemo = DemoRegistry.getCurrentDemo();
		if (showDemo == null && showDemo != currentDemo) {
			chkEAS.setSelected(currentDemo.isEas());

			changeEASStatus(currentDemo.isEas());
			UserConfig userConfig = currentDemo.getConfig();
			if (userConfig instanceof UserConfig_IRP1) {
				UserConfig_IRP1 config = (UserConfig_IRP1) userConfig;

				// 设置天线

				if (Common.isGetModelNOBySelected(currentDemo.getConfig()
						.getModelNo())) {
					try {
						Element modeE = Common.GetModelInfoNode(currentDemo
								.getConfig().getModelNo());

						String protocols = modeE.getChildText("Protocol");
						if (protocols.indexOf("IRP1.0") != -1) {
							Element IRP1E = modeE.getChild("IRP1.0");
							String[] rt = IRP1E.getChildText("Scan").split(",");
							if (rt != null && rt.length > 0) {
								if (typeComboBox.getItemCount() != 0)
									typeComboBox.removeAllItems();
								for (int i = 0; i < rt.length; i++)
									typeComboBox.addItem(rt[i]);
							}
							this.typeComboBox.setSelectedItem(config.getRmb()
									.toString());

							String c = IRP1E.getChildText("AntennaCtrl");
							antennacomboBox
									.setVisible(!(c.equals("p_Antenna")));
							panel_9.setVisible(c.equals("p_Antenna"));
							int na = Integer.parseInt(IRP1E
									.getChildText("AntennaNum"));
							antennacomboBox.removeAllItems();
							checkBox.setSelected(false);
							checkBox_1.setSelected(false);
							checkBox_2.setSelected(false);
							checkBox_3.setSelected(false);
							checkBox.setVisible(false);
							checkBox_1.setVisible(false);
							checkBox_2.setVisible(false);
							checkBox_3.setVisible(false);

							if (na == 1) {
								antennacomboBox.addItem("1#");
								checkBox.setVisible(true);
							}
							if (na == 2) {
								antennacomboBox.addItem("1#");
								antennacomboBox.addItem("2#");
								antennacomboBox.addItem("1-2#");
								checkBox.setVisible(true);
								checkBox_1.setVisible(true);
							}
							if (na == 3) {
								checkBox.setVisible(true);
								checkBox_1.setVisible(true);
								checkBox_2.setVisible(true);
							}
							if (na == 4) {
								antennacomboBox.addItem("1#");
								antennacomboBox.addItem("2#");
								antennacomboBox.addItem("3#");
								antennacomboBox.addItem("4#");
								antennacomboBox.addItem("1-2#");
								antennacomboBox.addItem("1-3#");
								antennacomboBox.addItem("1-4#");
								checkBox.setVisible(true);
								checkBox_1.setVisible(true);
								checkBox_2.setVisible(true);
								checkBox_3.setVisible(true);
							}
							if (antennacomboBox.getItemCount() > 0)
								antennacomboBox.setSelectedIndex(0);

							if (config.getAntennaIndex() > 0x80) {
								int i = currentDemo.getConfig_IRP1()
										.getAntennaIndex();
								checkBox.setSelected(Common.isZeroInByte(i, 0));
								checkBox_1.setSelected(Common
										.isZeroInByte(i, 1));
								checkBox_2.setSelected(Common
										.isZeroInByte(i, 2));
								checkBox_3.setSelected(Common
										.isZeroInByte(i, 3));
							} else {
								this.antennacomboBox.setSelectedIndex(config
										.getAntennaIndex());
							}
						}

					} catch (Exception e) {
						// do nothing
						e.printStackTrace();
					}

				} else {

					this.typeComboBox.setSelectedItem(config.getRmb()
							.toString());

					antennacomboBox.removeAllItems();
					if (config.getReaderType().equals("500")) {
						antennacomboBox.addItem("1#");
						antennacomboBox.addItem("2#");
						antennacomboBox.addItem("1-2#");
						antennacomboBox.setVisible(true);
					} else {
						antennacomboBox.addItem("1#");
						antennacomboBox.addItem("2#");
						antennacomboBox.addItem("3#");
						antennacomboBox.addItem("4#");
						antennacomboBox.addItem("1-2#");
						antennacomboBox.addItem("1-3#");
						antennacomboBox.addItem("1-4#");
						antennacomboBox.setVisible(true);
					}

					this.antennacomboBox.setSelectedIndex(config
							.getAntennaIndex());

				}

				if (config.getIsLoop()) {
					this.rdbtnLoop.setSelected(true);
					this.rdbtnSingle.setSelected(false);
				} else {
					this.rdbtnLoop.setSelected(false);
					this.rdbtnSingle.setSelected(true);
				}
				this.tagNumEditor.getTextField().setText(
						String.valueOf(config.getTagNum()));
				this.readTimeEditor.getTextField().setText(
						String.valueOf(config.getReadTime()));
				this.stopTimeEditor.getTextField().setText(
						String.valueOf(config.getStopTime()));
				this.stopTypeCombobox.setSelectedItem(config.getStopType());
			}
		}
		tagReadTypeComboxControll();
		if (typeComboBox.getActionListeners().length == 0) {
			typeComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ReaderTagConfigPanel.this.tagReadTypeComboxControll();
				}

			});
		}
		chkRSSI.setSelected(DemoRegistry.getCurrentDemo().isRssiEnable_800());
		chkUTC.setSelected(DemoRegistry.getCurrentDemo().isUtcEnable_800());

	}

	public void modifyConfig() {
		String errMsg = "";
		Demo currentDemo = DemoRegistry.getCurrentDemo();

		// general setting
		UserConfig_IRP1 config = (UserConfig_IRP1) currentDemo.getConfig();
		config.setRmb(ReadMemoryBank.valueOf(typeComboBox.getSelectedItem()
				.toString()));
		if (chkEAS.isSelected()) {
			currentDemo.setEas(true);
		} else {
			currentDemo.setEas(false);
		}

		if (panel_9.isVisible()) {
			boolean[] ary = new boolean[] { true, false, false, false,
					this.checkBox_3.isSelected(), this.checkBox_2.isSelected(),
					this.checkBox_1.isSelected(), this.checkBox.isSelected() };
			config.setAntennaIndex(Common.booleanArrayToInteger(ary));
		} else {
			config.setAntennaIndex(antennacomboBox.getSelectedIndex());
		}

		config.setIsLoop(rdbtnLoop.isSelected());
		config.setTagNum(Integer
				.parseInt(tagNumEditor.getTextField().getText()));
		config.setReadTime(Integer.parseInt(readTimeEditor.getTextField()
				.getText()));
		config.setStopTime(Integer.parseInt(stopTimeEditor.getTextField()
				.getText()));
		config.setStopType(stopTypeCombobox.getSelectedItem().toString());
		if (panel_3 != null && panel_3.isVisible()) {
			config.setTidLen((byte) Byte.parseByte(this.tidLen_6cSpinner
					.getValue().toString()));
			config
					.setUserDataPtr((byte) Integer
							.parseInt(this.spnUserDataStartLength.getValue()
									.toString()));
			config
					.setUserDataLen((byte) Integer
							.parseInt(this.userDataLen_6cSpinner.getValue()
									.toString()));
		} else if (panel_6c_read != null && panel_6c_read.isVisible()) {
			config.setTidLen((byte) Integer.parseInt(this.spnTIDLength
					.getValue().toString()));
			config.setUserDataPtr((byte) Integer
					.parseInt(this.spnUserDataPreLength.getValue().toString()));
			config.setUserDataLen((byte) Integer
					.parseInt(this.spnUserDataLength.getValue().toString()));

			config.setReadTimes_6C((byte) Integer.parseInt(this.spnReadTimes_6C
					.getValue().toString()));
			config.setReservedLen((byte) Integer.parseInt(this.spnReseverLen
					.getValue().toString()));
			config
					.setAccessPwd(Util.convertHexStringToByteArray(psw
							.getText()));

		} else {
			config.setTidLen((byte) 0);
			config.setUserDataPtr((byte) 0);
			config.setUserDataLen((byte) 0);
		}

		if (panel_6B_read_short != null && panel_6B_read_short.isVisible()) {
			config.setUserDataLen_6B((byte) Integer
					.parseInt(this.spnUserDataLength_6B_short.getValue()
							.toString()));
		} else if (panel_6B_read != null && panel_6B_read.isVisible()) {
			config.setReadTimes_6B((byte) Integer.parseInt(this.spnReadTimes_6B
					.getValue().toString()));
			config.setUserDataLen_6B((byte) Integer
					.parseInt(this.spnUserDataLength_6B.getValue().toString()));
			config.setUserdataPtr_6B((byte) Integer
					.parseInt(this.spnUserData_6B_StartAdress.getValue()
							.toString()));
		} else {
			config.setReadTimes_6B((byte) 0);
			config.setUserDataLen_6B((byte) 0);
			config.setUserdataPtr_6B((byte) 0);
		}

		config.setStartAndStopSospec();

		// config.removeConfig();
		// config.saveConfig();
		config.updateConfig();

		Reader reader = currentDemo.getReader();

		if (config.getModelNo().equals("XCRF-502E-F6G")) {
			// 6C读标签配置
			{
				if (panel_6c_read != null && panel_6c_read.isVisible()) {
					byte[] infoParam = new byte[5];
					infoParam[0] = (byte) ((this.cbxEPC.isSelected()) ? 1 : 0);
					infoParam[1] = (byte) ((this.cbxTID.isSelected()) ? 1 : 0);
					infoParam[2] = (byte) Integer.parseInt(this.spnTIDLength
							.getValue().toString());
					infoParam[3] = (byte) ((this.cbxUserData.isSelected()) ? 1
							: 0);
					infoParam[4] = (byte) Integer
							.parseInt(this.spnUserDataLength.getValue()
									.toString());

					boolean isSame = true;
					if (infoParam_6c != null) {
						for (int i = 0; i < 5; i++) {
							if (infoParam[i] != infoParam_6c[i]) {
								isSame = false;
								break;
							}
						}
					} else {
						isSame = false;
					}

					if (!isSame) {
						ReadTagConfig_6C order = new ReadTagConfig_6C(
								(byte) 0x00, infoParam);
						if (reader.send(order))
							infoParam_6c = infoParam;
						else
							errMsg = BaseMessages.getString("Message.MSG_222")
									+ "\r\n";
					}
				}
			}
			// 6c short
			{
				if (panel_3 != null && panel_3.isVisible()) {

					byte[] infoParam = new byte[5];
					infoParam[0] = (byte) ((this.chckbxEpc.isSelected()) ? 1
							: 0);
					infoParam[1] = (byte) ((this.chckbxTid.isSelected()) ? 1
							: 0);
					infoParam[2] = (byte) Integer
							.parseInt(this.tidLen_6cSpinner.getValue()
									.toString());
					infoParam[3] = (byte) ((this.chckbxUserdata.isSelected()) ? 1
							: 0);
					infoParam[4] = (byte) Integer
							.parseInt(this.userDataLen_6cSpinner.getValue()
									.toString());

					boolean isSame = true;
					if (infoParam_6c == null) {
						isSame = false;
					} else {
						for (int i = 0; i < 5; i++) {
							if (infoParam[i] != infoParam_6c[i]) {
								isSame = false;
								break;
							}
						}
					}

					if (!isSame) {
						ReadTagConfig_6C order = new ReadTagConfig_6C(
								(byte) 0x00, infoParam);
						if (reader.send(order))
							infoParam_6c = infoParam;
						else
							errMsg = BaseMessages.getString("Message.MSG_222")
									+ "\r\n";
					}

				}

			}

			{// 6B读标签配置
				if (panel_2 != null && panel_2.isVisible()) {
					byte infoParam = (byte) (Byte
							.parseByte(this.userData_6bEditor.getTextField()
									.getText()) * 2);
					if (infoParam_6b != infoParam) {
						ReadTagConfig_6B order = new ReadTagConfig_6B(
								(byte) 0x00, infoParam);
						if (reader.send(order))
							infoParam_6b = infoParam;
						else
							errMsg += BaseMessages.getString("Message.MSG_223")
									+ "\r\n";
					}
				}
			}
			{// 6B short
				if (panel_6B_read_short != null
						&& panel_6B_read_short.isVisible()) {
					int temp = (Integer) spnUserDataLength_6B_short.getValue();
					byte infoParam = (byte) (temp * 2);
					if (infoParam_6b != infoParam) {
						ReadTagConfig_6B order = new ReadTagConfig_6B(
								(byte) 0x00, infoParam);
						if (reader.send(order))
							infoParam_6b = infoParam;
						else
							errMsg += BaseMessages.getString("Message.MSG_223")
									+ "\r\n";
					}
				}
			}
			{// 读变长TID配置
				// 设置读变长TID开关
				if (panel_4 != null && panel_4.isVisible()
						&& isEnable != resizeOnBtn.isSelected()) {
					byte infoParam = 0x00;
					if (resizeOnBtn.isSelected())
						infoParam = 0x01;
					ReadUnfixedTidConfig_6C order = new ReadUnfixedTidConfig_6C(
							(byte) 0x00, infoParam);
					if (reader.send(order)) {
						isEnable = resizeOnBtn.isSelected();
					} else {
						errMsg += BaseMessages.getString("Message.MSG_224")
								+ "\r\n";
					}
				}

				// 设置固定TID长度
				if (panel_6 != null && panel_6.isVisible()) {
					int setTidLen = Integer.parseInt(tidLen_resizeableEditor
							.getTextField().getText());
					if (tidLen != setTidLen) {
						FixedTidLengthConfig_6C order = new FixedTidLengthConfig_6C(
								(byte) 0x00, (byte) setTidLen);
						if (reader.send(order)) {
							tidLen = setTidLen;
						} else {
							errMsg += BaseMessages.getString("Message.MSG_225")
									+ "\r\n";
						}
					}
				}
			}

			{// 快读TID配置
				// 设置快读TID开关
				if (panel_5 != null && panel_5.isVisible()
						&& isFastReadEnable != fastOnBtn.isSelected()) {
					byte infoParam = 0x00;
					if (fastOnBtn.isSelected())
						infoParam = 0x01;
					FastReadTIDConfig_6C order = new FastReadTIDConfig_6C(
							(byte) 0x00, infoParam);
					if (reader.send(order)) {
						isFastReadEnable = fastOnBtn.isSelected();
					} else {
						errMsg += BaseMessages.getString("Message.MSG_226")
								+ "\r\n";
					}

				}

				// 设置快读TID时EPC相对长度
				if (panel_7 != null && panel_7.isVisible()) {
					int setEpcLen = Integer.parseInt(this.epcLen_fastReadEditor
							.getTextField().getText());
					if (epcLen != setEpcLen) {
						byte[] infoParam = new byte[2];
						infoParam[0] = (byte) (setEpcLen >> 8); //
						infoParam[1] = (byte) (setEpcLen % 256); //
						FastReadTIDConfig_EpcLength order = new FastReadTIDConfig_EpcLength(
								(byte) 0x00, infoParam);
						if (reader.send(order)) {
							epcLen = setEpcLen;
						} else {
							errMsg += BaseMessages.getString("Message.MSG_227")
									+ "\r\n";
						}
					}
				}
			}
		}

		if (errMsg.length() > 0)
			MessageDialog.showErrorMessage(this, errMsg);
		else
			MessageDialog.showInfoMessage(this, BaseMessages.getString("",
					"Message.MSG_26", new String[] { errMsg }));

	}

	// 初始化预计读标签数选择栏
	public void initNumEditor(final JSpinner.NumberEditor editor,
			final String defaultVal) {
		editor.getTextField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(
						String.valueOf(e.getKeyChar())).find();

				try {
					int num = Integer.parseInt(editor.getTextField().getText());
					if (num < 0)
						editor.getTextField().setText(defaultVal);
				} catch (Exception ex) {
					editor.getTextField().setText(defaultVal);
				}

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

	private void chkEASActionPerformed(ActionEvent evt) {
		boolean isChecked = chkEAS.isSelected();

		changeEASStatus(isChecked);
	}

	private void changeEASStatus(boolean isChecked) {
		typeComboBox.setEnabled(!isChecked);
		chkRSSI.setEnabled(!isChecked);
		chkUTC.setEnabled(!isChecked);

		rdbtnLoop.setEnabled(!isChecked);
		// rdbtnSingle.setEnabled(!isChecked);

		tagNumSpinner.setEnabled(!isChecked);

		readTimeSpinner.setEnabled(!isChecked);
		stopTimeSpinner.setEnabled(!isChecked);
		stopTypeCombobox.setEnabled(!isChecked);
	}

	private void chkRSSIActionPerformed(java.awt.event.ActionEvent event) {

		byte[] rssiConfig = new byte[1];
		if (chkRSSI.isSelected()) {
			rssiConfig[0] = 0x01;
		}
		SysConfig_800 order = new SysConfig_800((byte) 0x14, rssiConfig);
		Reader reader = DemoRegistry.getCurrentDemo().getReader();
		if (reader.send(order)) {
			DemoRegistry.getCurrentDemo().setRssiEnable_800(
					chkRSSI.isSelected());
		} else {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_49"));

			chkRSSI.setSelected(!chkRSSI.isSelected());
		}

	}

	private void chkUTCActionPerformed(java.awt.event.ActionEvent evt) {

		byte[] utcConfig = new byte[1];
		if (chkUTC.isSelected()) {
			utcConfig[0] = 0x01;
		}
		SysConfig_800 order = new SysConfig_800((byte) 0x18, utcConfig);
		Reader reader = DemoRegistry.getCurrentDemo().getReader();
		if (reader.send(order)) {
			DemoRegistry.getCurrentDemo().setUtcEnable_800(chkUTC.isSelected());
		} else {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_49"));

			chkUTC.setSelected(!chkUTC.isSelected());
		}

	}

	public void setPanelEnable(JPanel panel, boolean enable) {
		if (panel != null) {
			panel.setVisible(enable);
			if (panel.getComponents().length > 0)
				for (Component component : panel.getComponents()) {
					component.setVisible(enable);
				}
		}

	}

	/**
	 * 6B+6c
	 */
	private void TagReadConfig_6c() {
		if (panel_6c_read != null) {
			remove(panel_6c_read);
		}
		panel_6c_read = new JPanel();
		panel_6c_read.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox8"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_6c_read.setBounds(33, 178, 500, 153);
		add(panel_6c_read);
		panel_6c_read.setLayout(null);

		JLabel label_1 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label18"));
		label_1.setBounds(28, 25, 150, 15);
		panel_6c_read.add(label_1);

		spnReadTimes_6C = new JSpinner();
		spnReadTimes_6C.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spnReadTimes_6C.setBounds(171, 23, 49, 20);
		panel_6c_read.add(spnReadTimes_6C);

		JLabel label_2 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label19"));
		label_2.setBounds(230, 25, 90, 15);
		panel_6c_read.add(label_2);

		cbxEPC = new JCheckBox("EPC");
		cbxEPC.setBounds(6, 46, 103, 23);
		panel_6c_read.add(cbxEPC);

		cbxTID = new JCheckBox("TID");
		cbxTID.setBounds(6, 71, 50, 23);
		panel_6c_read.add(cbxTID);

		JLabel lbllength = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label2"));
		lbllength.setBounds(130, 75, 50, 15);
		panel_6c_read.add(lbllength);

		spnTIDLength = new JSpinner();
		spnTIDLength.setBounds(133 + 40, 73, 54, 20);
		spnTIDLength
				.setModel(new SpinnerNumberModel(0, 0, Common.TID_MaxLen, 1));
		panel_6c_read.add(spnTIDLength);

		cbxUserData = new JCheckBox("UserData");
		cbxUserData.setBounds(6, 96, 80, 23);
		panel_6c_read.add(cbxUserData);

		JLabel label_3 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label2"));
		label_3.setBounds(130, 100, 50, 15);
		panel_6c_read.add(label_3);

		spnUserDataLength = new JSpinner();
		spnUserDataLength.setBounds(133 + 40, 97, 54, 20);
		spnUserDataLength.setModel(new SpinnerNumberModel(0, 0,
				Common.Userdata_MaxLen_6C, 1));
		panel_6c_read.add(spnUserDataLength);

		JLabel label_4 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label12"));
		label_4.setBounds(197 + 40, 100, 100, 15);
		panel_6c_read.add(label_4);

		spnUserDataPreLength = new JSpinner();
		spnUserDataPreLength.setBounds(251 + 100, 99, 80, 20);
		spnUserDataPreLength.setModel(new SpinnerNumberModel(0, 0,
				Common.Userdata_MaxLen_6C - 1, 1));
		panel_6c_read.add(spnUserDataPreLength);

		JCheckBox chxReserve = new JCheckBox(BaseMessages
				.getString("A1_ReadTagConfig.checkBox5"));
		chxReserve.setBounds(6, 121, 120, 23);
		panel_6c_read.add(chxReserve);

		JLabel label_5 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label2"));
		label_5.setBounds(130, 125, 50, 15);
		panel_6c_read.add(label_5);

		spnReseverLen = new JSpinner();
		spnReseverLen.setBounds(133 + 40, 121, 54, 20);
		spnReseverLen.setModel(new SpinnerNumberModel(0, 0, Common.killPwd_Len
				+ Common.accessPwd_Len, 1));
		panel_6c_read.add(spnReseverLen);

		JLabel label_6 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label8"));
		label_6.setBounds(197 + 40, 125, 110, 15);
		panel_6c_read.add(label_6);

		psw = new JPasswordField();
		psw.setBounds(251 + 100, 122, 120, 21);
		panel_6c_read.add(psw);
		psw.setText("00000000");
		psw.setColumns(10);

		Demo demo = DemoRegistry.getCurrentDemo();
		UserConfig userConfig = demo.getConfig();
		if (userConfig instanceof UserConfig_IRP1) {
			UserConfig_IRP1 config = (UserConfig_IRP1) userConfig;

			// if (config.getRmb() == ReadMemoryBank.EPC_TID_UserData_6C_2) {
			// ReadTag rt = (ReadTag) config.getStartRoSpec();
			this.spnTIDLength.setValue(config.getTidLen());
			this.spnUserDataLength.setValue(config.getUserDataLen());

			this.spnReadTimes_6C.setValue(config.getReadTimes_6C());
			this.spnReseverLen.setValue(config.getReservedLen());
			if (Util.convertByteArrayToHexString(config.getAccessPwd()) != null
					&& !Util.convertByteArrayToHexString(config.getAccessPwd())
							.equals(""))
				this.psw.setText(Util.convertByteArrayToHexString(config
						.getAccessPwd()));
			else
				this.psw.setText("00000000");
			this.spnUserDataPreLength.setValue(config.getUserDataPtr());

			cbxTID.setSelected(true);

			cbxUserData.setSelected(true);

			cbxEPC.setSelected(true);

			cbxEPC.setEnabled(false);

			chxReserve.setSelected(true);

		}
		// }
	}

	private void TagReadConfig_6c_e(boolean e) {
		panel_6c_read.setEnabled(e);
		for (Component c : panel_6c_read.getComponents()) {
			c.setEnabled(e);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(e);
				}
			}
		}
	}

	private void TagReadConfig_6c_v(boolean v) {
		if (panel_6c_read == null)
			return;
		panel_6c_read.setVisible(v);
		for (Component c : panel_6c_read.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	/**
	 * 6B
	 */
	public void TagReadConfig_6B() {

		if (panel_6B_read != null) {
			remove(panel_6B_read);
		}

		panel_6B_read = new JPanel();
		panel_6B_read.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox5"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_6B_read);
		panel_6B_read.setBounds(33, 340, 500, 80);
		panel_6B_read.setLayout(null);

		JLabel label_7 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label18"));
		label_7.setBounds(28, 25, 150, 15);
		panel_6B_read.add(label_7);

		spnReadTimes_6B = new JSpinner();
		spnReadTimes_6B.setBounds(180, 23, 49, 20);
		panel_6B_read.add(spnReadTimes_6B);

		JLabel label_8 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label19"));
		label_8.setBounds(230, 25, 90, 15);
		panel_6B_read.add(label_8);

		JLabel label_9 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label15"));
		label_9.setBounds(36, 51, 80, 15);
		panel_6B_read.add(label_9);

		JLabel label_10 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label14"));
		label_10.setBounds(120, 51, 50, 15);
		panel_6B_read.add(label_10);

		spnUserDataLength_6B = new JSpinner();
		spnUserDataLength_6B.setBounds(160, 48, 54, 20);
		spnUserDataLength_6B.setModel(new SpinnerNumberModel(0, 0,
				Common.userdata_MaxLen_6B, 1));
		panel_6B_read.add(spnUserDataLength_6B);

		JLabel label_11 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label12"));
		label_11.setBounds(224, 51, 80, 15);
		panel_6B_read.add(label_11);

		spnUserData_6B_StartAdress = new JSpinner();
		spnUserData_6B_StartAdress.setBounds(310, 49, 60, 20);

		spnUserData_6B_StartAdress.setModel(new SpinnerNumberModel(0, 0,
				Common.userdata_MaxLen_6B - 1, 1));
		panel_6B_read.add(spnUserData_6B_StartAdress);

		Demo demo = DemoRegistry.getCurrentDemo();
		UserConfig userConfig = demo.getConfig();
		if (userConfig instanceof UserConfig_IRP1) {
			UserConfig_IRP1 config = (UserConfig_IRP1) userConfig;
			spnReadTimes_6B.setValue(config.getReadTimes_6B());
			spnUserDataLength_6B.setValue(config.getUserDataLen_6B());
			spnUserData_6B_StartAdress.setValue(config.getUserdataPtr_6B());

		}

	}

	private void TagReadConfig_6B_e(boolean e) {
		panel_6B_read.setEnabled(e);
		for (Component c : panel_6B_read.getComponents()) {
			c.setEnabled(e);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(e);
				}
			}
		}
	}

	private void TagReadConfig_6B_v(boolean v) {
		if (panel_6B_read == null)
			return;
		panel_6B_read.setVisible(v);
		for (Component c : panel_6B_read.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	/**
	 * TID
	 */
	private void TagReadConfig_TID() {

		if (panel_4 != null) {
			remove(panel_4);
		}

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("A1_ReadTagConfig.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_4.setBounds(33, 177, 250, 97);
		add(panel_4);
		panel_4.setLayout(null);

		panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_6.setBounds(6, 42, 168, 46);
		panel_4.add(panel_6);
		panel_6.setLayout(null);

		tidLen_resizeableSpinner = new JSpinner();
		tidLen_resizeableSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		tidLen_resizeableEditor = new JSpinner.NumberEditor(
				tidLen_resizeableSpinner, "#####");
		initNumEditor(tidLen_resizeableEditor, "0");
		tidLen_resizeableSpinner.setEditor(tidLen_resizeableEditor);
		tidLen_resizeableSpinner.setBounds(10, 16, 75, 20);
		panel_6.add(tidLen_resizeableSpinner);

		label_8 = new JLabel(BaseMessages.getString("A1_ReadTagConfig.label13"));
		label_8.setBounds(95, 19, 69, 15);
		panel_6.add(label_8);

		resizeOnBtn = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.radioButton3"));
		resizeOnBtn.setBounds(17, 21, 68, 23);
		panel_4.add(resizeOnBtn);

		resizeOffBtn = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.radioButton4"));
		resizeOffBtn.setBounds(93, 21, 68, 23);
		panel_4.add(resizeOffBtn);

		ButtonGroup resize = new ButtonGroup();
		resize.add(resizeOnBtn);
		resize.add(resizeOffBtn);
		resizeOnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_6.setEnabled(true);
				tidLen_resizeableSpinner.setEnabled(true);
				label_8.setEnabled(true);
			}
		});
		resizeOffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_6.setEnabled(false);
				tidLen_resizeableSpinner.setEnabled(false);
				label_8.setEnabled(false);
			}
		});
	}

	private void TagReadConfig_TID_e(boolean e) {
		panel_4.setEnabled(e);
		for (Component c : panel_4.getComponents()) {
			c.setEnabled(e);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(e);
				}
			}
		}
	}

	private void TagReadConfig_TID_v(boolean v) {
		if (panel_4 == null)
			return;
		panel_4.setVisible(v);
		for (Component c : panel_4.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	/**
	 * TID
	 */
	private void quickTIDConfig(int x, int y) {

		if (panel_5 != null) {
			remove(panel_5);
		}

		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox3"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		if (x == 0 && y == 0)
			panel_5.setBounds(33, 298, 250, 97);
		else
			panel_5.setBounds(x, y, 180, 97);
		add(panel_5);
		panel_5.setLayout(null);

		panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox4"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_7.setBounds(6, 42, 168, 46);
		panel_5.add(panel_7);
		panel_7.setLayout(null);

		epcLen_fastReadSpinner = new JSpinner();
		epcLen_fastReadSpinner.setModel(new SpinnerNumberModel(new Integer(0),
				null, null, new Integer(1)));
		epcLen_fastReadEditor = new JSpinner.NumberEditor(
				epcLen_fastReadSpinner, "#####");
		initNumEditor(epcLen_fastReadEditor, "0");
		epcLen_fastReadSpinner.setEditor(epcLen_fastReadEditor);
		epcLen_fastReadSpinner.setBounds(10, 16, 120, 20);
		panel_7.add(epcLen_fastReadSpinner);

		ButtonGroup bg = new ButtonGroup();
		fastOnBtn = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.radioButton1"));
		fastOnBtn.setBounds(18, 23, 68, 23);
		panel_5.add(fastOnBtn);

		fastOffBtn = new JRadioButton(BaseMessages
				.getString("A1_ReadTagConfig.radioButton2"));
		fastOffBtn.setBounds(88, 23, 68, 23);

		fastOffBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (fastOffBtn.isSelected()) {
					panel_7.setEnabled(false);
					epcLen_fastReadSpinner.setEnabled(false);
				}
			}

		});
		fastOnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (fastOnBtn.isSelected()) {
					panel_7.setEnabled(true);
					epcLen_fastReadSpinner.setEnabled(true);
				}
			}
		});
		panel_5.add(fastOffBtn);
		bg.add(fastOffBtn);
		bg.add(fastOnBtn);
	}

	private void quickTIDConfig(boolean v) {
		panel_5.setEnabled(v);
		for (Component c : panel_5.getComponents()) {
			c.setEnabled(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(v);
				}
			}
		}
	}

	private void quickTIDConfig_v(boolean v) {
		if (panel_5 == null)
			return;
		panel_5.setVisible(v);
		for (Component c : panel_5.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	/**
	 * 简化6B配置
	 */
	private void tagReadConfig_6B_short(int x, int y) {

		if (panel_6B_read_short != null) {
			remove(panel_6B_read_short);
		}

		panel_6B_read_short = new JPanel();
		panel_6B_read_short.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox5"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_6B_read_short);
		if (x == 0 && y == 0)
			panel_6B_read_short.setBounds(33, 340, 350, 80);
		else
			panel_6B_read_short.setBounds(x, y, 350, 80);
		panel_6B_read_short.setLayout(null);

		JLabel label_9 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label15"));
		label_9.setBounds(76, 36, 80, 15);
		panel_6B_read_short.add(label_9);

		JLabel label_10 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label14"));
		label_10.setBounds(160, 36, 50, 15);
		panel_6B_read_short.add(label_10);

		spnUserDataLength_6B_short = new JSpinner();
		spnUserDataLength_6B_short.setBounds(230, 33, 54, 20);
		spnUserDataLength_6B_short.setModel(new SpinnerNumberModel(0, 0,
				Common.userdata_MaxLen_6B, 1));
		panel_6B_read_short.add(spnUserDataLength_6B_short);

		Demo demo = DemoRegistry.getCurrentDemo();
		UserConfig userConfig = demo.getConfig();
		if (userConfig instanceof UserConfig_IRP1) {
			UserConfig_IRP1 config = (UserConfig_IRP1) userConfig;
			// ReadTag rt = (ReadTag) config.getStartRoSpec();
			spnUserDataLength_6B_short.setValue(config.getUserDataLen_6B());

		}
	}

	private void tagReadConfig_6B_short_e(boolean e) {
		panel_6B_read_short.setEnabled(e);
		for (Component c : panel_6B_read_short.getComponents()) {
			c.setEnabled(e);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(e);
				}
			}
		}
	}

	private void tagReadConfig_6B_short_v(boolean v) {
		if (panel_6B_read_short == null)
			return;
		panel_6B_read_short.setVisible(v);
		for (Component c : panel_6B_read_short.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	/**
	 * 6c读标签配置
	 */
	private void tagReadConfig_6C_short() {

		if (panel_3 != null) {
			remove(panel_3);
		}

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, BaseMessages
				.getString("A1_ReadTagConfig.groupBox8"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_3.setBounds(33, 178, 400, 97);
		add(panel_3);
		panel_3.setLayout(null);

		chckbxEpc = new JCheckBox(BaseMessages
				.getString("A1_ReadTagConfig.checkBox1"));
		chckbxEpc.setBounds(16, 26, 66, 15);
		panel_3.add(chckbxEpc);

		JLabel label_4 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label14"));
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(98, 72, 48, 15);
		panel_3.add(label_4);

		userDataLen_6cSpinner = new JSpinner();
		userDataLen_6cSpinner.setModel(new SpinnerNumberModel(0, 0,
				Common.Userdata_MaxLen_6C, 1));
		userDataLen_6cEditor = new JSpinner.NumberEditor(userDataLen_6cSpinner,
				"#####");
		initNumEditor(userDataLen_6cEditor, "0");
		userDataLen_6cSpinner.setEditor(userDataLen_6cEditor);
		userDataLen_6cSpinner.setBounds(150, 69, 42, 20);
		panel_3.add(userDataLen_6cSpinner);

		chckbxTid = new JCheckBox(BaseMessages
				.getString("A1_ReadTagConfig.checkBox2"));
		chckbxTid.setBounds(16, 49, 66, 15);
		panel_3.add(chckbxTid);

		chckbxUserdata = new JCheckBox(BaseMessages
				.getString("A1_ReadTagConfig.checkBox3"));
		chckbxUserdata.setBounds(16, 72, 120, 15);
		panel_3.add(chckbxUserdata);

		JLabel label_3 = new JLabel(BaseMessages
				.getString("A1_ReadTagConfig.label1"));
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(98, 47, 48, 15);
		panel_3.add(label_3);

		tidLen_6cSpinner = new JSpinner();
		tidLen_6cSpinner.setModel(new SpinnerNumberModel(0, 0, 7, 1));
		tidLen_6cEditor = new JSpinner.NumberEditor(tidLen_6cSpinner, "#####");
		initNumEditor(tidLen_6cEditor, "0");
		tidLen_6cSpinner.setEditor(tidLen_6cEditor);
		tidLen_6cSpinner.setBounds(150, 46, 42, 20);

		label_5 = new JLabel(BaseMessages.getString("A1_ReadTagConfig.label12"));
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(200, 71, 120, 15);
		panel_3.add(label_5);

		spnUserDataStartLength = new JSpinner();
		spnUserDataStartLength.setModel(new SpinnerNumberModel(0, 0, 7, 1));
		spnUserDataStartLength.setModel(new SpinnerNumberModel(0, 0,
				Common.Userdata_MaxLen_6C - 1, 1));
		spnUserDataStartLength.setBounds(330, 69, 42, 20);

		panel_3.add(spnUserDataStartLength);
		panel_3.add(tidLen_6cSpinner);

		Demo demo = DemoRegistry.getCurrentDemo();
		UserConfig userConfig = demo.getConfig();
		if (userConfig instanceof UserConfig_IRP1) {
			UserConfig_IRP1 config = (UserConfig_IRP1) userConfig;

			// if (config.getRmb() == ReadMemoryBank.EPC_TID_UserData_6C_2) {
			// ReadTag rt = (ReadTag) config.getStartRoSpec();
			this.tidLen_6cSpinner.setValue(config.getTidLen());
			this.spnUserDataStartLength.setValue(config.getUserDataPtr());

			this.userDataLen_6cSpinner.setValue(config.getUserDataLen());

			chckbxTid.setSelected(true);

			chckbxUserdata.setSelected(true);

			chckbxEpc.setSelected(true);

			chckbxEpc.setEnabled(false);

		}
		// }
	}

	private void tagReadConfig_6C_short(boolean e) {
		panel_3.setEnabled(e);
		for (Component c : panel_3.getComponents()) {
			c.setEnabled(e);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setEnabled(e);
				}
			}
		}
	}

	private void tagReadConfig_6C_short_v(boolean v) {
		if (panel_3 == null)
			return;
		panel_3.setVisible(v);
		for (Component c : panel_3.getComponents()) {
			c.setVisible(v);
			if (c instanceof JPanel) {
				for (Component cc : ((JPanel) c).getComponents()) {
					cc.setVisible(v);
				}
			}
		}
	}

	private void clear() {
		tagReadConfig_6C_short_v(false);
		tagReadConfig_6B_short_v(false);
		quickTIDConfig_v(false);
		TagReadConfig_TID_v(false);
		TagReadConfig_6c_v(false);
		TagReadConfig_6B_v(false);
	}

	private void tagReadTypeComboxControll() {

		if (typeComboBox.getSelectedItem() == null)
			return;

		clear();

		ReadMemoryBank RMB = ReadMemoryBank.valueOf(typeComboBox
				.getSelectedItem().toString());
		if (RMB == ReadMemoryBank.EPC_6C || RMB == ReadMemoryBank.EPC_6C_ID_6B
				|| RMB == ReadMemoryBank.EPC_PC_6C
				|| RMB == ReadMemoryBank.EPC_TID_UserData_6C
				|| RMB == ReadMemoryBank.ID_6B || RMB == ReadMemoryBank.TID_6C) {
			if (RMB == ReadMemoryBank.ID_6B) {
				lblTagNum.setVisible(false);
				tagNumSpinner.setVisible(false);
			} else {
				lblTagNum.setVisible(true);
				tagNumSpinner.setVisible(true);
			}

		}
		Demo demo = DemoRegistry.getCurrentDemo();
		if ("XCRF-502E-F6G".equals(demo.getConfig().getModelNo())
				&& RMB == ReadMemoryBank.EPC_TID_UserData_6C) {
			quickTIDConfig(0, 0);
			tagReadConfig_6C_short();

			// 配置
			byte[] infoParam = new byte[5];
			ReadTagConfig_6C msg = new ReadTagConfig_6C((byte) 0x01, infoParam);
			// 6c
			if (demo.getReader().send(msg, 100)) {
				infoParam_6c = new byte[5];
				infoParam_6c[0] = msg.getReceivedMessage().getInfoParam()[0]; // epc开关
				infoParam_6c[1] = msg.getReceivedMessage().getInfoParam()[1]; // tid开关
				infoParam_6c[2] = msg.getReceivedMessage().getInfoParam()[2]; // tid长度
				infoParam_6c[3] = msg.getReceivedMessage().getInfoParam()[3]; // usd开关
				infoParam_6c[4] = msg.getReceivedMessage().getInfoParam()[4]; // usd长度

				this.chckbxEpc
						.setSelected((infoParam_6c[0] == (byte) 0x01) ? true
								: false);
				this.chckbxTid
						.setSelected((infoParam_6c[1] == (byte) 0x01) ? true
								: false);
				this.chckbxUserdata
						.setSelected((infoParam_6c[3] == (byte) 0x01) ? true
								: false);

				this.chckbxEpc.setEnabled(true);
				this.chckbxTid.setEnabled(true);
				this.chckbxUserdata.setEnabled(true);

				int max = (Integer) ((SpinnerNumberModel) (this.userDataLen_6cSpinner
						.getModel())).getMaximum();
				this.userDataLen_6cSpinner
						.setValue((infoParam_6c[4]) > max ? max
								: infoParam_6c[4]);
				max = (Integer) ((SpinnerNumberModel) (this.tidLen_6cSpinner
						.getModel())).getMaximum();
				this.tidLen_6cSpinner.setValue((infoParam_6c[2]) > max ? max
						: infoParam_6c[2]);

				this.spnUserDataStartLength.setVisible(false);
				this.label_5.setVisible(false);

			} else {
				tagReadConfig_6C_short(false);
			}
			// TID
			FastReadTIDConfig_6C tid_msg = new FastReadTIDConfig_6C(
					(byte) 0x01, (byte) 0x00);
			if (demo.getReader().send(tid_msg, 100)) {
				if (tid_msg.getReceivedMessage().getQueryData()[0] == (byte) 0x00) {
					this.fastOffBtn.setSelected(true);
				} else {
					this.fastOnBtn.setSelected(true);
					this.isFastReadEnable = true;
				}
			} else {
				quickTIDConfig(false);
			}

			FastReadTIDConfig_EpcLength fastmsg = new FastReadTIDConfig_EpcLength(
					(byte) 0x01, new byte[] { 0x00, 0x00 });
			if (demo.getReader().send(fastmsg)) {
				epcLen_fastReadSpinner.setValue(((int) fastmsg
						.getReceivedMessage().getQueryData()[0] << 8)
						+ (int) fastmsg.getReceivedMessage().getQueryData()[1]);
				epcLen = (Integer) (epcLen_fastReadSpinner.getValue());
			}
			this.updateUI();
		}

		if (RMB == ReadMemoryBank.EPC_TID_UserData_6C_2) {

			tagReadConfig_6C_short();

		}

		if (RMB == ReadMemoryBank.EPC_TID_UserData_6C_ID_UserData_6B) {
			tagReadConfig_6C_short();
			tagReadConfig_6B_short(33, 300);
			quickTIDConfig(450, 178);

			if ("500".equals(demo.getConfig().getReaderType())) {
				byte[] infoParam = new byte[5];
				ReadTagConfig_6C msg = new ReadTagConfig_6C((byte) 0x01,
						infoParam);
				if (demo.getReader().send(msg, 100)) {
					infoParam_6c = new byte[5];
					infoParam_6c[0] = msg.getReceivedMessage().getInfoParam()[0];
					infoParam_6c[1] = msg.getReceivedMessage().getInfoParam()[1];
					infoParam_6c[2] = msg.getReceivedMessage().getInfoParam()[2];
					infoParam_6c[3] = msg.getReceivedMessage().getInfoParam()[3];
					infoParam_6c[4] = msg.getReceivedMessage().getInfoParam()[4];

					this.chckbxEpc
							.setSelected((infoParam_6c[0] == (byte) 0x01) ? true
									: false);
					this.chckbxTid
							.setSelected((infoParam_6c[1] == (byte) 0x01) ? true
									: false);
					this.chckbxUserdata
							.setSelected((infoParam_6c[3] == (byte) 0x01) ? true
									: false);

					this.chckbxEpc.setEnabled(true);
					this.chckbxTid.setEnabled(true);
					this.chckbxUserdata.setEnabled(true);

					int max = (Integer) ((SpinnerNumberModel) (this.userDataLen_6cSpinner
							.getModel())).getMaximum();
					this.userDataLen_6cSpinner
							.setValue((infoParam_6c[4]) > max ? max
									: infoParam_6c[4]);
					max = (Integer) ((SpinnerNumberModel) (this.tidLen_6cSpinner
							.getModel())).getMaximum();
					this.tidLen_6cSpinner
							.setValue((infoParam_6c[2]) > max ? max
									: infoParam_6c[2]);
					this.spnUserDataStartLength.setVisible(false);
					this.label_5.setVisible(false);
				} else {
					tagReadConfig_6C_short(false);
				}
			}
			// 6B
			if ("500".equals(demo.getConfig().getReaderType())) {
				ReadTagConfig_6B msg = new ReadTagConfig_6B((byte) 0x01,
						(byte) 0x00);
				if (demo.getReader().send(msg, 100)) {
					int d = ((int) msg.getReceivedMessage().getInfoParam()) / 2;
					int max = (Integer) ((SpinnerNumberModel) (this.spnUserDataLength_6B_short
							.getModel())).getMaximum();
					spnUserDataLength_6B_short.setValue(d > max ? max : d);
					this.infoParam_6b = (byte) (msg.getReceivedMessage()
							.getInfoParam() / 2);
				} else {
					tagReadConfig_6B_short_e(false);
				}
			}

			if (demo.getConfig().getModelNo().equals("XCRF-502E-F6G")) {

			}

			// 快读TID配置
			if ("500".equals(demo.getConfig().getReaderType())) {
				FastReadTIDConfig_6C msg = new FastReadTIDConfig_6C(
						(byte) 0x01, (byte) 0x00);
				if (demo.getReader().send(msg, 100)) {
					if (msg.getReceivedMessage().getQueryData()[0] == (byte) 0x00) {
						this.fastOffBtn.setSelected(true);
					} else {
						this.fastOnBtn.setSelected(true);
						this.isFastReadEnable = true;
					}
				} else {
					quickTIDConfig(false);
				}
			}
			if ("500".equals(demo.getConfig().getReaderType())) {
				FastReadTIDConfig_EpcLength msg = new FastReadTIDConfig_EpcLength(
						(byte) 0x01, new byte[] { 0x00, 0x00 });
				if (demo.getReader().send(msg, 100)) {
					this.epcLen_fastReadSpinner
							.setValue((((int) msg.getReceivedMessage()
									.getQueryData()[0] << 8) + (int) msg
									.getReceivedMessage().getQueryData()[1]));
					this.epcLen = (Integer) (this.epcLen_fastReadSpinner
							.getValue());
				}
			} else {
				quickTIDConfig(false);
			}

		}
		if (RMB == ReadMemoryBank.EPC_TID_UserData_Received_6C_ID_UserData_6B) {
			TagReadConfig_6B();
			TagReadConfig_6c();

			cbxEPC.setSelected(true);
			cbxEPC.setEnabled(false);
		}

		if (demo.getConfig().getModelNo().equals("XCRF-502E-F6G")
				&& RMB == ReadMemoryBank.TID_6C
				|| RMB == ReadMemoryBank.TID_6C_ID_6B) {
			quickTIDConfig(0, 0);
			TagReadConfig_TID();

			ReadUnfixedTidConfig_6C msg = new ReadUnfixedTidConfig_6C(
					(byte) 0x01, (byte) 0x00);
			if (demo.getReader().send(msg, 100)) {
				if (msg.getReceivedMessage().getQueryData()[0] == (byte) 0x00) {
					this.resizeOffBtn.setSelected(true);
				} else {
					this.resizeOnBtn.setSelected(true);
					this.isEnable = true;
				}
			} else {
				TagReadConfig_TID_e(false);
			}

			// 查询固定TID长度
			FixedTidLengthConfig_6C msg1 = new FixedTidLengthConfig_6C(
					(byte) 0x01, (byte) 0x00);
			if (demo.getReader().send(msg1, 100)) {
				int d = msg1.getReceivedMessage().getQueryData()[0];
				int max = (Integer) ((SpinnerNumberModel) (this.tidLen_resizeableSpinner
						.getModel())).getMaximum();
				this.tidLen_resizeableSpinner.setValue(d > max ? max : d);
				this.tidLen = (Integer) this.tidLen_resizeableSpinner
						.getValue();
			} else {
				TagReadConfig_TID_e(false);
			}

			// 快读TID配置
			FastReadTIDConfig_6C msg2 = new FastReadTIDConfig_6C((byte) 0x01,
					(byte) 0x00);
			if (demo.getReader().send(msg2, 100)) {
				if (msg2.getReceivedMessage().getQueryData()[0] == (byte) 0x00) {
					this.fastOffBtn.setSelected(true);
				} else {
					this.fastOnBtn.setSelected(true);
					this.isFastReadEnable = true;
				}
			} else {
				quickTIDConfig(false);
			}

			FastReadTIDConfig_EpcLength msg3 = new FastReadTIDConfig_EpcLength(
					(byte) 0x01, new byte[] { 0x00, 0x00 });
			if (demo.getReader().send(msg3, 100)) {
				this.epcLen_fastReadSpinner
						.setValue((((int) msg3.getReceivedMessage()
								.getQueryData()[0] << 8) + (int) msg3
								.getReceivedMessage().getQueryData()[1]));
				this.epcLen = (Integer) this.epcLen_fastReadSpinner.getValue();
			} else {
				quickTIDConfig(false);
			}
		}

		{
			if (panel_5 != null && panel_5.isVisible() && panel_5.isEnabled()) {
				if (fastOffBtn.isSelected()) {
					panel_7.setEnabled(false);
					epcLen_fastReadSpinner.setEnabled(false);
				} else {
					panel_7.setEnabled(true);
					epcLen_fastReadSpinner.setEnabled(true);
				}
			}
			if (panel_4 != null && panel_4.isVisible() && panel_4.isEnabled()) {
				if (resizeOnBtn.isSelected()) {
					panel_6.setEnabled(true);
					tidLen_resizeableSpinner.setEnabled(true);
					label_8.setEnabled(true);
				} else {
					panel_6.setEnabled(false);
					tidLen_resizeableSpinner.setEnabled(false);
					label_8.setEnabled(false);
				}
			}
		}
		this.updateUI();
	}

}