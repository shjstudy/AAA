package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.EpcFilter_6C;
import invengo.javaapi.protocol.IRP1.IDFilter_6B;
import invengo.javaapi.protocol.IRP1.SelectTag_6C;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class TagFilterPanel extends JPanel implements ConfigPanel {
	private static final long serialVersionUID = 1641420332213456807L;
	private final JTextField textField;
	private final JTextField txtTID;
	private final JTextField textField_2;
	private final JTextField textField_3;
	private final JTextField textField_4;
	private JPanel panel = null;
	private final JPanel panel_1;
	private final JPanel panel_2;
	private static final String EIGHT_00 = "800";

	private static final String FIVE_00 = "500";

	private final JComboBox comboBox;
	private final JSpinner spinner;
	private final JSpinner spinner_1;

	/**
	 * Create the panel.
	 */
	public TagFilterPanel() {
		setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("A2_TagFilter.groupBox8"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(65, 42, 565, 84);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages
				.getString("A2_TagFilter.label23"));
		label.setBounds(10, 21, 54, 15);
		panel.add(label);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {
				"EPC\u6570\u636E\u533A", "TID\u6570\u636E\u533A",
				"\u7528\u6237\u6570\u636E\u533A" }));
		comboBox.setBounds(84, 18, 140, 21);
		panel.add(comboBox);

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				switch (index) {
				case 0:// EPC
					spinner.setModel(new SpinnerNumberModel(0, 0,
							Common.EPC_MaxLen * 2 * 8 - 1, 1));
					spinner_1.setModel(new SpinnerNumberModel(0, 0,
							Common.EPC_MaxLen * 2 * 8, 1));

					textField.setColumns(Common.EPC_MaxLen * 4);
					break;
				case 1:
					spinner.setModel(new SpinnerNumberModel(0, 0,
							Common.TID_MaxLen * 2 * 8 - 1, 1));
					spinner_1.setModel(new SpinnerNumberModel(0, 0,
							Common.TID_MaxLen * 2 * 8, 1));
					textField.setColumns(Common.TID_MaxLen * 4);
					break;
				case 2:
					spinner.setModel(new SpinnerNumberModel(0, 0,
							Common.Userdata_MaxLen_6C * 2 * 8 - 1, 1));
					spinner_1.setModel(new SpinnerNumberModel(0, 0,
							Common.Userdata_MaxLen_6C * 2 * 8, 1));
					textField.setColumns(Common.Userdata_MaxLen_6C * 4);
					break;
				default:
					break;
				}

			}
		});

		JLabel label_1 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label18"));
		label_1.setBounds(10, 52, 75, 15);
		panel.add(label_1);

		textField = new JTextField();
		textField.setBounds(84, 49, 295, 21);
		textField.setDocument(new TextDocument(0, 0));
		textField.enableInputMethods(false);
		panel.add(textField);
		textField.setColumns(10);

		JLabel label_2 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label21"));
		label_2.setBounds(389, 52, 54, 15);
		panel.add(label_2);

		JLabel label_3 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label20"));
		label_3.setBounds(244, 21, 76, 15);
		panel.add(label_3);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 256, 1));
		spinner.setBounds(330, 19, 43, 20);
		panel.add(spinner);

		JLabel label_4 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label19"));
		label_4.setBounds(389, 21, 102, 15);
		panel.add(label_4);

		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 256, 1));
		spinner_1.setBounds(501, 18, 54, 21);
		panel.add(spinner_1);

		JButton button = new JButton(BaseMessages
				.getString("A2_TagFilter.button5"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button_click();
			}
		});
		button.setBounds(453, 48, 102, 23);
		panel.add(button);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("A2_TagFilter.groupBox9"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_1.setBounds(65, 136, 565, 84);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel label_5 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label24"));
		label_5.setBounds(10, 25, 54, 15);
		panel_1.add(label_5);

		txtTID = new JTextField();
		txtTID.setColumns(10);
		txtTID.setDocument(new TextDocument(0, 0));
		txtTID.enableInputMethods(false);
		txtTID.setBounds(85, 22, 295, 21);
		panel_1.add(txtTID);

		JLabel label_6 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label25"));
		label_6.setBounds(390, 25, 54, 15);
		panel_1.add(label_6);

		JLabel label_7 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label22"));
		label_7.setBounds(10, 53, 54, 15);
		panel_1.add(label_7);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setDocument(new TextDocument(0, 0));
		textField_2.enableInputMethods(false);
		textField_2.setBounds(85, 50, 295, 21);
		panel_1.add(textField_2);

		JLabel label_8 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label26"));
		label_8.setBounds(390, 53, 54, 15);
		panel_1.add(label_8);

		JButton button_1 = new JButton(BaseMessages
				.getString("A2_TagFilter.button6"));
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1_click();
			}
		});
		button_1.setBounds(454, 21, 101, 23);
		panel_1.add(button_1);

		JButton button_2 = new JButton(BaseMessages
				.getString("A2_TagFilter.button8"));
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button2_click();
			}
		});
		button_2.setBounds(454, 49, 101, 23);
		panel_1.add(button_2);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, BaseMessages
				.getString("A2_TagFilter.groupBox10"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_2.setBounds(65, 230, 565, 97);
		add(panel_2);
		panel_2.setLayout(null);

		JLabel label_9 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label28"));
		label_9.setBounds(10, 29, 54, 15);
		panel_2.add(label_9);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setDocument(new TextDocument(0, 0));
		textField_3.enableInputMethods(false);
		textField_3.setBounds(85, 26, 295, 21);
		panel_2.add(textField_3);

		JLabel label_10 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label29"));
		label_10.setBounds(390, 29, 54, 15);
		panel_2.add(label_10);

		JLabel label_11 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label27"));
		label_11.setBounds(10, 60, 54, 15);
		panel_2.add(label_11);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setDocument(new TextDocument(0, 0));
		textField_4.enableInputMethods(false);
		textField_4.setBounds(85, 57, 295, 21);
		panel_2.add(textField_4);

		JLabel label_12 = new JLabel(BaseMessages
				.getString("A2_TagFilter.label30"));
		label_12.setBounds(390, 60, 54, 15);
		panel_2.add(label_12);

		JButton button_3 = new JButton(BaseMessages
				.getString("A2_TagFilter.button7"));
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button3_click();
			}
		});
		button_3.setBounds(454, 56, 101, 23);
		panel_2.add(button_3);

		// JLabel label_13 = new JLabel("");
		// label_13
		// .setIcon(new ImageIcon(
		// TagFilterPanel.class
		// .getResource("/com/invengo/xcrf/ui/image/Invengo/XC-RF806.jpg")));
		// label_13.setHorizontalAlignment(SwingConstants.CENTER);
		// label_13.setBounds(65, 337, 565, 122);
		// add(label_13);
	}

	@Override
	public void fillConfigData() {
		panel_onload();
	}

	private void panel_onload() {
		Utils.setPanelEnable(true, panel_2);
		Utils.setPanelEnable(true, panel);
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		UserConfig_IRP1 userConfig = (UserConfig_IRP1) currentDemo.getConfig();
		RFIDProtocol protocool = userConfig.getProtocol();
		if (RFIDProtocol.IRP1.equals(protocool)) {
			if (EIGHT_00.equals(userConfig.getReaderType())) {
				Utils.setPanelEnable(false, panel_2);
			} else if (FIVE_00.equals(userConfig.getReaderType())) {
				Utils.setPanelEnable(false, panel);
			}
		}
	}

	/**
	 * 标签标准过滤选项
	 */
	private void button_click() {
		byte matchArea = (byte) (comboBox.getSelectedIndex() + 1);// 匹配区域
		MemoryBank bank = MemoryBank.EPCMemory;
		switch (matchArea) {
		case 01:
			bank = MemoryBank.EPCMemory;
			break;
		case 02:
			bank = MemoryBank.TIDMemory;
			break;
		case 03:
			bank = MemoryBank.UserMemory;
			break;
		default:
			break;
		}
		String data = textField.getText().trim();
		if ("".equals(data)) {
			MessageDialog.showInfoMessage(BaseMessages.getString("MSG_144"));
			return;
		}
		byte[] tagID = Util.convertHexStringToByteArray(data);
		IMessage msg = new SelectTag_6C(bank, Byte.parseByte(spinner.getValue()
				.toString()), (byte) (tagID.length * 8), tagID);
		sendMsg(msg);
	}

	/**
	 * IP_6B过滤
	 */
	private void button3_click() {

		String tagTid = textField_3.getText().trim();
		String mask = textField_4.getText().trim();
		if ("".equals(tagTid)) {
			MessageDialog.showInfoMessage(BaseMessages.getString("",
					"Message.MSG_141", new String[] { "6B" }));
			return;
		}
		if ("".equals(mask)) {
			MessageDialog.showInfoMessage(BaseMessages.getString("",
					"Message.MSG_142", new String[] { "6B" }));
			return;
		}

		if (tagTid.length() != 16) {
			tagTid = Utils.padRight(tagTid, 16, '0');
		}
		if (mask.length() != 16) {
			mask = Utils.padRight(mask, 16, 'f');
		}
		textField_3.setText(tagTid);
		textField_4.setText(mask);
		byte[] tID = Util.convertHexStringToByteArray(tagTid);
		byte[] tagMask = Util.convertHexStringToByteArray(mask);
		IMessage msg = new IDFilter_6B(tID, tagMask);
		sendMsg(msg);
	}

	/**
	 * // EPC_6C过滤取消
	 */
	private void button2_click() {
		IMessage msg = new EpcFilter_6C((byte) 0x01, null, null);
		sendMsg(msg);
	}

	/**
	 * EPC_6C过滤设置
	 */
	private void button1_click() {

		String tagTid = txtTID.getText().trim();
		if ("".equals(tagTid)) {
			MessageDialog.showInfoMessage(BaseMessages.getString("",
					"Message.MSG_141", new String[] { "6C" }));
			return;
		}
		String mask = textField_2.getText().trim();
		if ("".equals(mask)) {
			MessageDialog.showInfoMessage(BaseMessages.getString("",
					"Message.MSG_142", new String[] { "6C" }));
			return;
		}

		if (tagTid.length() < 24) {
			tagTid = Utils.padRight(tagTid, 24, '0');
		} else if (tagTid.length() > 24 && tagTid.length() < 60) {
			tagTid = Utils.padRight(tagTid, 60, '0');
		}
		this.txtTID.setText(tagTid);
		if (mask.length() < 24) {
			mask = Utils.padRight(mask, 24, '0');
		} else if (mask.length() > 24 && mask.length() < 60) {
			mask = Utils.padRight(mask, 60, '0');
		}
		textField_2.setText(mask);

		byte[] tID = Util.convertHexStringToByteArray(tagTid);
		byte[] tagMask = Util.convertHexStringToByteArray(mask);
		IMessage msg = new EpcFilter_6C((byte) 0x00, tID, tagMask);
		sendMsg(msg);
	}

	private void sendMsg(IMessage msg) {
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		if (currentDemo.getReader().send(msg)) {
			MessageDialog.showInfoMessage(this, BaseMessages
					.getString("Message.MSG_4"));
		} else {
			MessageDialog.showInfoMessage(this, BaseMessages
					.getString("Message.MSG_5"));
		}
	}

}
