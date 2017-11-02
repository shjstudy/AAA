package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.FreqQuery_500;
import invengo.javaapi.protocol.IRP1.FrequencyTable;
import invengo.javaapi.protocol.IRP1.PowerParamConfig_500;
import invengo.javaapi.protocol.IRP1.PowerTable;
import invengo.javaapi.protocol.IRP1.SysConfig_500;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.SysQuery_500;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class A3_AntennaConfigPanel extends JPanel implements ConfigPanel {
	private static final long serialVersionUID = -3497556948623766396L;
	private JComboBox ant1;
	private JComboBox ant2;
	private JComboBox ant3;
	private JComboBox ant4;
	private JLabel labVal;
	private JSlider slider;
	private JPanel leftPanel;
	private JPanel middlePanel;
	private JList hzList;
	private JComboBox hzComboBox;
	private JRadioButton rdbtnmhz;
	private JRadioButton rdbtnmhz_1;

	private double[] list = null;

	/**
	 * Create the panel.
	 */
	public A3_AntennaConfigPanel() {
		setPreferredSize(new Dimension(640, 481));
		setLayout(null);

		leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("A3_AntennaConfiguration.groupBox11"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.setBounds(19, 48, 151, 385);
		add(leftPanel);
		leftPanel.setLayout(null);

		JLabel label = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label31"));
		label.setBounds(10, 46, 20, 15);
		leftPanel.add(label);

		ant1 = new JComboBox();
		ant1.setBounds(29, 43, 79, 21);
		leftPanel.add(ant1);

		JLabel lbldmb = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label32"));
		lbldmb.setBounds(108, 46, 43, 15);
		leftPanel.add(lbldmb);

		JLabel label_1 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label38"));
		label_1.setBounds(10, 74, 20, 15);
		leftPanel.add(label_1);

		ant2 = new JComboBox();
		ant2.setBounds(29, 71, 79, 21);
		leftPanel.add(ant2);

		JLabel label_2 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label37"));
		label_2.setBounds(108, 74, 43, 15);
		leftPanel.add(label_2);

		JLabel label_3 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label36"));
		label_3.setBounds(10, 102, 20, 15);
		leftPanel.add(label_3);

		ant3 = new JComboBox();
		ant3.setBounds(29, 99, 79, 21);
		leftPanel.add(ant3);

		JLabel label_4 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label35"));
		label_4.setBounds(108, 102, 43, 15);
		leftPanel.add(label_4);

		JLabel label_5 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label34"));
		label_5.setBounds(10, 133, 20, 15);
		leftPanel.add(label_5);

		ant4 = new JComboBox();
		ant4.setBounds(29, 130, 79, 21);
		leftPanel.add(ant4);

		JLabel label_6 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label33"));
		label_6.setBounds(108, 133, 43, 15);
		leftPanel.add(label_6);

		JButton button = new JButton(BaseMessages
				.getString("A3_AntennaConfiguration.btn_AntPower500"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_AntPower800_Click();
			}
		});
		button.setBounds(29, 341, 112, 23);
		leftPanel.add(button);

		middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(null, BaseMessages
				.getString("A3_AntennaConfiguration.gB_Antenna"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		middlePanel.setBounds(176, 48, 190, 385);
		add(middlePanel);
		middlePanel.setLayout(null);

		JButton button_1 = new JButton(BaseMessages
				.getString("A3_AntennaConfiguration.btn_AntPower800"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_AntPower500_Click();
			}
		});
		button_1.setBounds(75, 341, 105, 23);
		middlePanel.add(button_1);

		labVal = new JLabel("");
		labVal.setBounds(121, 256, 59, 23);
		labVal.setHorizontalAlignment(SwingConstants.RIGHT);
		middlePanel.add(labVal);

		slider = new JSlider();
		slider.setMajorTickSpacing(200);
		slider.setPaintTicks(true);
		slider.setMaximum(12287);
		slider.setMinimum(8192);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(20, 21, 59, 343);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				labVal.setText(String.valueOf(slider.getValue()));
			}
		});

		middlePanel.add(slider);

		JLabel label_7 = new JLabel("32");
		label_7.setBounds(89, 21, 26, 15);
		middlePanel.add(label_7);

		JLabel label_8 = new JLabel("30");
		label_8.setBounds(89, 60, 26, 15);
		middlePanel.add(label_8);

		JLabel label_9 = new JLabel("27");
		label_9.setBounds(89, 140, 26, 15);
		middlePanel.add(label_9);

		JLabel label_10 = new JLabel(BaseMessages
				.getString("A3_AntennaConfiguration.label43"));
		label_10.setBounds(86, 233, 94, 23);
		middlePanel.add(label_10);

		JPanel rigthPanel = new JPanel();
		rigthPanel.setBorder(new TitledBorder(null, BaseMessages
				.getString("A3_AntennaConfiguration.groupBox12"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rigthPanel.setBounds(376, 48, 254, 385);
		add(rigthPanel);
		rigthPanel.setLayout(null);

		JButton frequencyCfgBtn = new JButton(BaseMessages
				.getString("A3_AntennaConfiguration.btn_Freq"));
		frequencyCfgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Freq_Click();
			}
		});
		frequencyCfgBtn.setBounds(10, 340, 109, 23);
		rigthPanel.add(frequencyCfgBtn);

		ButtonGroup g = new ButtonGroup();
		rdbtnmhz = new JRadioButton(BaseMessages
				.getString("A3_AntennaConfiguration.radioButton5"));
		rdbtnmhz.setBounds(6, 23, 136, 23);
		rigthPanel.add(rdbtnmhz);
		rdbtnmhz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnmhz_CheckedChanged();
			}
		});

		rdbtnmhz_1 = new JRadioButton(BaseMessages
				.getString("A3_AntennaConfiguration.radioButton6"));
		rdbtnmhz_1.setBounds(6, 48, 136, 23);
		rigthPanel.add(rdbtnmhz_1);
		rdbtnmhz_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnmhz_1_CheckedChanged();
			}
		});

		g.add(rdbtnmhz);
		g.add(rdbtnmhz_1);

		hzComboBox = new JComboBox();
		hzComboBox.setBounds(148, 23, 96, 22);
		rigthPanel.add(hzComboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(148, 55, 96, 308);
		rigthPanel.add(scrollPane);

		hzList = new JList();
		scrollPane.setViewportView(hzList);

	}

	@Override
	public void fillConfigData() {

		// 查询 天线端口功率
		{
			slider.setValue(10888);
			PowerTable pt = new PowerTable();
			Demo demo = DemoRegistry.getCurrentDemo();
			if (demo != null && demo.getProtocl() == RFIDProtocol.IRP1) {

				if (demo.getConfig().getModelNo() != null
						&& demo.getConfig().getModelNo().indexOf("807") != -1) {
					list = new double[26];
					for (int i = 0; i < list.length; i++) {
						list[i] = 11 + i;
					}
				} else {
					list = new PowerTable().getList();
				}

				UserConfig_IRP1 config = demo.getConfig_IRP1();
				String readerType = config.getReaderType();
				if (readerType.equals("800")) {
					Utils.setPanelEnabled(leftPanel);
					Utils.setPanelDisabled(middlePanel);

					SysQuery_800 order = new SysQuery_800((byte) 0x0C,
							(byte) 0x00);
					if (demo.getReader().send(order, 300)) {
						ant1.removeAllItems();
						ant2.removeAllItems();
						ant3.removeAllItems();
						ant4.removeAllItems();

						for (int i = 0; i < order.getReceivedMessage()
								.getQueryData().length
								&& i < pt.getList().length; i++) {
							if (order.getReceivedMessage().getQueryData()[i] != 0x00) {
								ant1.addItem(list[i]);
								ant2.addItem(list[i]);
								ant3.addItem(list[i]);
								ant4.addItem(list[i]);
							}
						}
					} else {
						ant1.removeAllItems();
						ant2.removeAllItems();
						ant3.removeAllItems();
						ant4.removeAllItems();
						for (int i = 0; i < list.length; i++) {
							ant1.addItem(list[i]);
							ant2.addItem(list[i]);
							ant3.addItem(list[i]);
							ant4.addItem(list[i]);
						}
					}

					SysQuery_800 order1 = new SysQuery_800((byte) 0x03);
					if (demo.getReader().send(order1)) {
						ant1.setSelectedItem(list[order1.getReceivedMessage()
								.getQueryData()[0]]);
						ant2.setSelectedItem(list[order1.getReceivedMessage()
								.getQueryData()[1]]);
						ant3.setSelectedItem(list[order1.getReceivedMessage()
								.getQueryData()[2]]);
						ant4.setSelectedItem(list[order1.getReceivedMessage()
								.getQueryData()[3]]);
					}
				} else if (readerType.equals("500")) {

					Utils.setPanelDisabled(leftPanel);
					Utils.setPanelEnabled(middlePanel);

					byte[] infoParam = { (byte) 0x2f, (byte) 0x00 };
					PowerParamConfig_500 order = new PowerParamConfig_500(
							(byte) 0x01, infoParam);// 查询
					if (demo.getReader().send(order)) {
						int value = ((order.getReceivedMessage()
								.getQueryData()[0] & 0xFF))
								* 256
								+ (order.getReceivedMessage()
										.getQueryData()[1] & 0xFF);
						labVal.setText(value + "");
						slider.setValue(value);
					}
				}

				// 频率
				FrequencyTable ft = null;

				if (readerType.equals("800")) {
					int modelNum = -1;
					// 频段查询
					{
						SysQuery_800 order = new SysQuery_800((byte) 0x15);// 频段查询
						if (demo.getReader().send(order, 300))// 查询系统参数
							modelNum = order.getReceivedMessage()
									.getQueryData()[0];
					}
					// 读写器产品型号
					if (modelNum == -1) {
						SysQuery_800 order = new SysQuery_800((byte) 0x21);
						if (demo.getReader().send(order, 300))// 查询系统参数
						{
							// string model =
							// System.Text.Encoding.ASCII.GetString(order.ReceivedMessage.QueryData).Trim();
							String model = new String(order
									.getReceivedMessage().getQueryData())
									.trim().toLowerCase();
							switch (model.charAt(model.length() - 1)) {
							case 'c':
								modelNum = 0;
								break;
							case 'a':
								modelNum = 1;
								break;
							case 'e':
								modelNum = 2;
								break;
							case 'k':
								modelNum = 3;
								break;
							case 'j':
								modelNum = 4;
								break;
							case 'h':
								modelNum = 5;
								break;
							default:
								modelNum = 1;
								break;
							}
						} else
							modelNum = 1;
					}
					// 查询频段表
					switch (modelNum) {
					case 0:
						ft = new FrequencyTable(FrequencyTable.Name.CN_920_925);
						break;
					case 1:
						ft = new FrequencyTable(FrequencyTable.Name.US_902_928);
						break;
					case 2:
						ft = new FrequencyTable(FrequencyTable.Name.EU_865_868);
						break;
					case 5:
						ft = new FrequencyTable(FrequencyTable.Name.CN_840_845);
						break;

					default:
						ft = new FrequencyTable(FrequencyTable.Name.US_902_928);
						break;
					}

				} else if (readerType.equals("500")) {
					ft = new FrequencyTable(FrequencyTable.Name.US_902_928);
					byte[] param = new byte[] { 0x00 };
					FreqQuery_500 order = new FreqQuery_500(param);
					if (demo.getReader().send(order, 300)) {
						switch (order.getReceivedMessage().getQueryData()[0]) {
						case 0x01:
							ft = new FrequencyTable(
									FrequencyTable.Name.US_902_928);
							break;
						case 0x02:
							ft = new FrequencyTable(
									FrequencyTable.Name.CN_920_925);
							break;
						case 0x03:
							ft = new FrequencyTable(
									FrequencyTable.Name.CN_840_845);
							break;
						default:
							ft = new FrequencyTable(
									FrequencyTable.Name.US_902_928);
							break;
						}
					}
				}

				hzComboBox.removeAll();
				hzList.removeAll();
				// ((DefaultListModel)hzList.getModel()).removeAllElements();

				// for (int i = 0; i < ft.getList().length; i++)
				for (String frequency : ft.getList()) {
					hzComboBox.addItem(frequency);
				}
				hzComboBox.updateUI();
				hzList.setListData(ft.getList());

				if (readerType.equals("800")) {
					// 查询频点
					SysQuery_800 order = new SysQuery_800((byte) 0x04);
					if (demo.getReader().send(order, 300)) {
						initHzCommboxAndListValue(order.getReceivedMessage()
								.getQueryData());
					}
				} else if (readerType.equals("500")) {
					SysQuery_500 order = new SysQuery_500((byte) 0x01,
							(byte) 0x30);
					if (demo.getReader().send(order, 300)) {
						initHzCommboxAndListValue(order.getReceivedMessage()
								.getQueryData());
					}
				}

			}
		}
	}

	private void initHzCommboxAndListValue(byte[] hzLst) {
		if (hzLst == null || hzLst.length == 0)
			return;

		int p = 0;
		int selected[] = new int[hzLst.length];
		for (byte bfreq : hzLst) {
			if (bfreq == (byte) 255)// FF是空的频点，跳过
				break;
			if (p < 1)
				hzComboBox.setSelectedIndex(bfreq);
			selected[p] = bfreq;
			p++;
		}
		hzList.setSelectedIndices(selected);
		hzList.ensureIndexIsVisible(hzLst.length == 0 ? 0 : hzLst[0]);

		if (p == 1) {
			rdbtnmhz.setSelected(true);
			rdbtnmhz_CheckedChanged();
		} else if (p > 1) {
			rdbtnmhz_1.setSelected(true);
			rdbtnmhz_1_CheckedChanged();
		}

	}

	private void rdbtnmhz_CheckedChanged() {
		hzComboBox.setEnabled(true);
		hzList.setEnabled(false);
	}

	private void rdbtnmhz_1_CheckedChanged() {
		hzComboBox.setEnabled(false);
		hzList.setEnabled(true);
	}

	private void btn_AntPower800_Click() {
		if ((ant1.getSelectedItem() + "" + ant1.getSelectedItem()
				+ ant1.getSelectedItem() + ant1.getSelectedItem()).replaceAll(
				"null", "").replace(" ", "").length() < 1) {
			MessageDialog.showInfoMessage("Message.MSG_152");
			return;
		}

		Demo demo = DemoRegistry.getCurrentDemo();
		if (demo != null && demo.getProtocl() == RFIDProtocol.IRP1) {

			// 配置天线端口功率
			UserConfig_IRP1 config = demo.getConfig_IRP1();
			if (config != null && "800".equals(config.getReaderType())) {

				String strSuc = "";
				String strFai = "";

				if (ant1.getSelectedItem() != null
						&& !ant1.getSelectedItem().toString().trim().equals("")) {
					byte[] aData = new byte[2];
					aData[0] = 0x00;
					for (int i = 0; i < list.length; i++) {
						if (list[i] == Double.parseDouble(ant1
								.getSelectedItem().toString()))
							aData[1] = (byte) i;
					}

					SysConfig_800 order = new SysConfig_800((byte) 0x03, aData);
					if (demo.getReader().send(order))
						strSuc = "1#,";
					else
						strFai = "1#,";
				}

				if (ant2.getSelectedItem() != null
						&& !ant2.getSelectedItem().toString().trim().equals("")) {
					byte[] aData = new byte[2];
					aData[0] = 0x01;
					for (int i = 0; i < list.length; i++) {
						if (list[i] == Double.parseDouble(ant2
								.getSelectedItem().toString()))
							aData[1] = (byte) i;
					}

					SysConfig_800 order = new SysConfig_800((byte) 0x03, aData);
					if (demo.getReader().send(order))
						strSuc += "2#,";
					else
						strFai += "2#,";
				}

				if (ant3.getSelectedItem() != null
						&& !ant3.getSelectedItem().toString().trim().equals("")) {
					byte[] aData = new byte[2];
					aData[0] = 0x02;
					for (int i = 0; i < list.length; i++) {
						if (list[i] == Double.parseDouble(ant3
								.getSelectedItem().toString()))
							aData[1] = (byte) i;
					}

					SysConfig_800 order = new SysConfig_800((byte) 0x03, aData);
					if (demo.getReader().send(order))
						strSuc += "3#,";
					else
						strFai += "3#,";
				}

				if (ant4.getSelectedItem() != null
						&& !ant4.getSelectedItem().toString().trim().equals("")) {
					byte[] aData = new byte[2];
					aData[0] = 0x03;
					for (int i = 0; i < list.length; i++) {
						if (list[i] == Double.parseDouble(ant4
								.getSelectedItem().toString()))
							aData[1] = (byte) i;
					}

					SysConfig_800 order = new SysConfig_800((byte) 0x03, aData);
					if (demo.getReader().send(order))
						strSuc += "4#,";
					else
						strFai += "4#,";
				}

				if (strSuc != "")
					strSuc = strSuc.substring(0, strSuc.length() - 1)
							+ BaseMessages.getString("Message.MSG_31");
				if (strFai != "")
					strFai = strFai.substring(0, strFai.length() - 1)
							+ BaseMessages.getString("Message.MSG_5");
				MessageDialog.showInfoMessage(strSuc + "\r\n" + strFai);
			}
		}
	}

	private void btn_AntPower500_Click() {
		Demo demo = DemoRegistry.getCurrentDemo();
		if (demo != null && demo.getProtocl() == RFIDProtocol.IRP1) {
			UserConfig_IRP1 config = demo.getConfig_IRP1();
			if (config != null && "500".equals(config.getReaderType())) {
				byte[] param = new byte[2];
				param[0] = (byte) (slider.getValue() / 256);
				param[1] = (byte) (slider.getValue() % 256);
				PowerParamConfig_500 order = new PowerParamConfig_500(
						(byte) 0x00, param);// 设置
				if (demo.getReader().send(order))
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_27"));
				else
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_28"));
			}
		}
	}

	private void btn_Freq_Click() {
		Demo demo = DemoRegistry.getCurrentDemo();
		if (demo != null && demo.getProtocl() == RFIDProtocol.IRP1) {
			UserConfig_IRP1 config = demo.getConfig_IRP1();
			String readerType = config.getReaderType();
			if (readerType.equals("500")) {
				byte[] pData = null;
				if (rdbtnmhz.isSelected()) {
					pData = new byte[2];
					pData[0] = (byte) hzComboBox.getSelectedIndex();
					pData[1] = (byte) 0xff;
				} else {
					pData = new byte[hzList.getSelectedValues().length + 1];
					if (pData.length > 30)
						pData = new byte[30];
					int p = 0;
					for (int i : hzList.getSelectedIndices()) {
						pData[p] = (byte) i;
						p++;
						if (p >= 30)
							break;
					}
					if (p < 30)
						pData[p] = (byte) 0xff;
				}
				SysConfig_500 order = new SysConfig_500((byte) 0x01,
						(byte) pData.length, pData);
				if (demo.getReader().send(order))
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_27"));
				else
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_28"));
			} else if (readerType.equals("800")) {
				byte[] pData = null;
				if (rdbtnmhz.isSelected()) {
					pData = new byte[1];
					pData[0] = (byte) hzComboBox.getSelectedIndex();
				} else {
					pData = new byte[hzList.getSelectedIndices().length];
					int p = 0;
					for (int i : hzList.getSelectedIndices()) {
						pData[p] = (byte) i;
						p++;
					}
				}
				SysConfig_800 order = new SysConfig_800((byte) 0x04, pData);
				if (demo.getReader().send(order))
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_27"));
				else
					MessageDialog.showInfoMessage(null, BaseMessages
							.getString("Message.MSG_28"));
			}
		}

	}

}
