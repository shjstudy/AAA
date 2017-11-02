package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.Gpi_800;
import invengo.javaapi.protocol.IRP1.Gpo_800;
import invengo.javaapi.protocol.IRP1.IODevices_500;
import invengo.javaapi.protocol.IRP1.IODevices_502C;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class IOConfigPanel extends JPanel implements ConfigPanel {
	private static final long serialVersionUID = 4794950305887611543L;
	private JTextField txtPort1;
	private JTextField txtPort2;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private JComboBox comboBox_4;

	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JPanel panel;
	private JPanel panel_1;
	private JComboBox cboIO;
	private JComboBox cboCtrl;
	private JTextField txtPort3;
	private JTextField txtPort4;

	/**
	 * Create the panel.
	 */
	public IOConfigPanel() {
		setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("A4_GPIO.groupBox2"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLUE));
		panel.setBounds(22, 10, 217, 409);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("A4_GPIO.label7"));
		label.setBounds(10, 37, 68, 15);
		panel.add(label);

		txtPort1 = new JTextField();
		//txtPort1.setDocument(new TextDocument(0,1));
		txtPort1.setBounds(102, 34, 66, 21);
		panel.add(txtPort1);
		txtPort1.setColumns(10);

		JLabel label_1 = new JLabel(BaseMessages.getString("A4_GPIO.label8"));
		label_1.setBounds(10, 71, 68, 15);
		panel.add(label_1);

		txtPort2 = new JTextField();
		txtPort2.setColumns(10);
		//txtPort2.setDocument(new TextDocument(0,1));
		txtPort2.setBounds(102, 68, 66, 21);
		panel.add(txtPort2);

		checkBox_1 = new JCheckBox(BaseMessages.getString("A4_GPIO.cbIO1"));
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_1.setEnabled(checkBox_1.isSelected());
			}
		});
		checkBox_1.setBounds(10, 204, 102, 23);
		panel.add(checkBox_1);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox1")));
		comboBox_1.setBounds(118, 205, 54, 22);
		comboBox_1.setSelectedIndex(-1);
		comboBox_1.setEnabled(false);
		panel.add(comboBox_1);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox2")));
		comboBox_2.setBounds(118, 233, 54, 22);
		comboBox_2.setEnabled(false);
		comboBox_2.setSelectedIndex(-1);
		panel.add(comboBox_2);

		checkBox_2 = new JCheckBox(BaseMessages.getString("A4_GPIO.cbIO2"));
		checkBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_2.setEnabled(checkBox_2.isSelected());
			}
		});
		checkBox_2.setBounds(10, 232, 102, 23);
		panel.add(checkBox_2);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox3")));
		comboBox_3.setBounds(118, 262, 54, 22);
		comboBox_3.setEnabled(false);
		comboBox_3.setSelectedIndex(-1);
		panel.add(comboBox_3);

		checkBox_3 = new JCheckBox(BaseMessages.getString("A4_GPIO.cbIO3"));
		checkBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_3.setEnabled(checkBox_3.isSelected());
			}
		});
		checkBox_3.setBounds(10, 261, 102, 23);
		panel.add(checkBox_3);

		comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox2")));
		comboBox_4.setBounds(118, 291, 54, 22);
		comboBox_4.setEnabled(false);
		comboBox_4.setSelectedIndex(-1);
		panel.add(comboBox_4);

		checkBox_4 = new JCheckBox(BaseMessages.getString("A4_GPIO.cbIO4"));
		checkBox_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_4.setEnabled(checkBox_4.isSelected());
			}
		});
		checkBox_4.setBounds(10, 290, 102, 23);
		panel.add(checkBox_4);

		JButton button = new JButton(BaseMessages
				.getString("A4_GPIO.btn_Query"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Query_Click();
			}
		});
		button.setBounds(10, 167, 161, 23);
		panel.add(button);

		JButton button_1 = new JButton(BaseMessages
				.getString("A4_GPIO.btn_Config"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Config_Click();
			}
		});
		button_1.setBounds(10, 323, 160, 23);
		panel.add(button_1);
		
		JLabel label_2 = new JLabel(BaseMessages.getString("A4_GPIO.label1"));
		label_2.setBounds(10, 107, 68, 15);
		panel.add(label_2);
		
		txtPort3 = new JTextField();
		txtPort3.setColumns(10);
		txtPort3.setBounds(102, 104, 66, 21);
		panel.add(txtPort3);
		
		JLabel label_3 = new JLabel(BaseMessages.getString("A4_GPIO.label2"));
		label_3.setBounds(10, 139, 68, 15);
		panel.add(label_3);
		
		txtPort4 = new JTextField();
		txtPort4.setColumns(10);
		txtPort4.setBounds(102, 136, 66, 21);
		panel.add(txtPort4);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("A4_GPIO.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_1.setLayout(null);
		panel_1.setBounds(249, 10, 231, 179);
		add(panel_1);

		JLabel lblIo = new JLabel(BaseMessages.getString("A4_GPIO.label14"));
		lblIo.setBounds(23, 21, 156, 22);
		panel_1.add(lblIo);

		cboIO = new JComboBox();
		cboIO.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox8")));
		cboIO.setBounds(46, 45, 175, 21);
		panel_1.add(cboIO);

		JLabel label_6 = new JLabel(BaseMessages.getString("A4_GPIO.label15"));
		label_6.setBounds(23, 71, 156, 22);
		panel_1.add(label_6);

		cboCtrl = new JComboBox();
		cboCtrl.setModel(new DefaultComboBoxModel(BaseMessages
				.getStrings("A4_GPIO.comboBox6")));
		cboCtrl.setBounds(46, 103, 175, 21);
		panel_1.add(cboCtrl);

		JButton btnOK = new JButton(BaseMessages.getString("A4_GPIO.btnOK"));
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_OK_Click();
			}

		});
		btnOK.setBounds(153, 146, 68, 23);
		panel_1.add(btnOK);

		Component[] comps = panel.getComponents();
		for (Component cboComp : comps) {
			if (cboComp instanceof JComboBox) {
				JComboBox cbo = (JComboBox) cboComp;
				cbo.setSelectedIndex(-1);
			}
		}
		Utils.setPanelEnable(false, panel_1);

//		JLabel label_2 = new JLabel("");
//		label_2
//				.setIcon(new ImageIcon(
//						IOConfigPanel.class
//								.getResource("/com/invengo/xcrf/ui/image/Invengo/XC2903-PDA-2.png")));
//		label_2.setBounds(249, 199, 381, 271);
//		add(label_2);
//
//		JLabel label_3 = new JLabel("");
//		label_3
//				.setIcon(new ImageIcon(
//						IOConfigPanel.class
//								.getResource("/com/invengo/xcrf/ui/image/Invengo/XC2903-PDA-3.png")));
//		label_3.setBounds(476, 13, 154, 176);
//		add(label_3);
	}

	@Override
	public void fillConfigData() {
		// Ò³Ãæ³õÊ¼»¯
		Demo current = DemoRegistry.getCurrentDemo();
		if (current.getProtocl() == RFIDProtocol.IRP1) {
			if (current.getConfig().getReaderType().equals("800")) {
				Utils.setPanelEnabled(panel);
				Utils.setPanelDisabled(panel_1);
			} else {
				Utils.setPanelEnabled(panel_1);
				Utils.setPanelDisabled(panel);
			}
		}
	}

	private void btn_OK_Click() {
		if (cboIO.getSelectedItem() == null) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_146"));
			return;
		}
		if (cboCtrl.getSelectedItem() == null) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_146"));
			return;
		}

		String msg = "";
		String setOk = BaseMessages.getString("Message.MSG_31");
		String setFail = BaseMessages.getString("Message.MSG_5");
		Demo current = DemoRegistry.getCurrentDemo();
		switch (cboIO.getSelectedIndex()) {
		case 0:
			IODevices_500 order = new IODevices_500((byte) 0x00, (byte) cboCtrl
					.getSelectedIndex());

			if (current.getReader().send(order)) {
				msg = setOk;
			} else {
				IODevices_502C order01 = new IODevices_502C((byte) 0x00,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order01)) {
					msg = setOk;
				} else {
					msg = setFail;
				}
			}
			break;
		case 1:

			IODevices_500 order1 = new IODevices_500((byte) 0x01,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order1))
				msg = setOk;
			else {
				IODevices_502C order11 = new IODevices_502C((byte) 0x01,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order11))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 2:
			IODevices_500 order2 = new IODevices_500((byte) 0x10,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order2))
				msg = setOk;
			else {
				IODevices_502C order21 = new IODevices_502C((byte) 0x10,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order21))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 3:
			IODevices_500 order3 = new IODevices_500((byte) 0x11,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order3))
				msg = setOk;
			else {
				IODevices_502C order31 = new IODevices_502C((byte) 0x11,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order31))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 4:
			IODevices_500 order4 = new IODevices_500((byte) 0x12,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order4))
				msg = setOk;
			else {
				IODevices_502C order41 = new IODevices_502C((byte) 0x12,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order41))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 5:
			IODevices_500 order5 = new IODevices_500((byte) 0x13,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order5))
				msg = setOk;
			else {
				IODevices_502C order51 = new IODevices_502C((byte) 0x13,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order51))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 6:
			IODevices_500 order6 = new IODevices_500((byte) 0x14,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order6))
				msg = setOk;
			else {
				IODevices_502C order61 = new IODevices_502C((byte) 0x14,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order61))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 7:
			IODevices_500 order7 = new IODevices_500((byte) 0x15,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order7))
				msg = setOk;
			else {
				IODevices_502C order71 = new IODevices_502C((byte) 0x15,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order71))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 8:
			IODevices_500 order8 = new IODevices_500((byte) 0x16,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order8))
				msg = setOk;
			else {
				IODevices_502C order81 = new IODevices_502C((byte) 0x16,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order81))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 9:
			IODevices_500 order9 = new IODevices_500((byte) 0x17,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order9))
				msg = setOk;
			else {
				IODevices_502C order91 = new IODevices_502C((byte) 0x17,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order91))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		case 10:
			IODevices_500 order10 = new IODevices_500((byte) 0x18,
					(byte) cboCtrl.getSelectedIndex());
			if (current.getReader().send(order10))
				msg = setOk;
			else {
				IODevices_502C order101 = new IODevices_502C((byte) 0x18,
						(byte) cboCtrl.getSelectedIndex());
				if (current.getReader().send(order101))
					msg = setOk;
				else
					msg = setFail;
			}
			break;
		}

		MessageDialog.showInfoMessage(msg);

	}

	private void btn_Query_Click() {
		IMessage msg = new Gpi_800();
		if (sendMsg(msg)) {
			Gpi_800 gpi = (Gpi_800) msg;
			byte b = gpi.getReceivedMessage().getQueryData()[0];
			txtPort1.setText(comboBox_1.getItemAt(b & 0x01 ).toString());
			txtPort2.setText(comboBox_1.getItemAt((b & 0x02) >> 1).toString());
			txtPort3.setText(comboBox_1.getItemAt((b & 0x04) >> 2).toString());
			txtPort4.setText(comboBox_1.getItemAt((b & 0x08) >> 3).toString());
			
		}
	}

	private void btn_Config_Click() {
		StringBuffer ms = new StringBuffer("");
		StringBuffer mf = new StringBuffer("");
		if (checkBox_1.isSelected()) {
			byte d = (byte) comboBox_1.getSelectedIndex();
			IMessage msg = new Gpo_800((byte) 0x01, d);
			if (sendMsg(msg)) {
				ms.append("1#,");
			} else {
				mf.append("1#,");
			}
		}
		if (checkBox_2.isSelected()) {
			byte d = (byte) comboBox_2.getSelectedIndex();
			IMessage msg = new Gpo_800((byte) 0x02, d);
			if (sendMsg(msg)) {
				ms.append("2#,");
			} else {
				mf.append("2#,");
			}
		}
		if (checkBox_3.isSelected()) {
			byte d = (byte) comboBox_3.getSelectedIndex();
			IMessage msg = new Gpo_800((byte) 0x03, d);
			if (sendMsg(msg)) {
				ms.append("3#,");
			} else {
				mf.append("3#,");
			}
		}
		if (checkBox_4.isSelected()) {
			byte d = (byte) comboBox_4.getSelectedIndex();
			IMessage msg = new Gpo_800((byte) 0x04, d);
			if (sendMsg(msg)) {
				ms.append("4#,");
			} else {
				mf.append("4#,");
			}
		}
		String message = "";
		if (ms.length() > 0) {
			message = ms.substring(0, ms.length() - 1) + BaseMessages.getString("Message.MSG_31");
		}
		if (mf.length() > 0) {
			message += "\r\n" + mf.substring(0, mf.length() - 1) + BaseMessages.getString("Message.MSG_31");
		}
		if (message.length() > 0)
			MessageDialog.showInfoMessage(this, message);
		else {
			MessageDialog.showInfoMessage(this, BaseMessages
					.getString("IOConfigPanel.notSelect"));
		}
	}

	private boolean sendMsg(IMessage msg) {
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		return currentDemo.getReader().send(msg);
	}
}
