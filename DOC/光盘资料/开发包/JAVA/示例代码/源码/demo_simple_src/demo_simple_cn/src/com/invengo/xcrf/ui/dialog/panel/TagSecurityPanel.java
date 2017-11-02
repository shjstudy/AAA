package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.AccessPwdConfig_6C;
import invengo.javaapi.protocol.IRP1.EasConfig_6C;
import invengo.javaapi.protocol.IRP1.GetTagAccessPwd_6C;
import invengo.javaapi.protocol.IRP1.GetTagKillPwd_6C;
import invengo.javaapi.protocol.IRP1.KillPwdConfig_6C;
import invengo.javaapi.protocol.IRP1.LockMemoryBank_6C;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class TagSecurityPanel extends AbstractTagAccessPanel {
	private JTextArea txtLog;
	private JRadioButton rdoSet;
	private JRadioButton rdoCancel;
	private JPasswordField pwdDestory1;
	private JPasswordField pwdDestory2;
	private JPasswordField pwdAccess1;
	private JPasswordField pwdAccess2;

	private ButtonGroup buttonGroup;

	private static final String CANCEL = "cancel";

	private static final String SET = "set";
	private JCheckBox chkDestroy;
	private JCheckBox chkAccess;
	private JCheckBox chkLock;
	private JCheckBox chkEAS;

	private JCheckBox[] checkBoxs = null;
	private JComboBox cboDataType;
	private JComboBox cboSign;

	/**
	 * Create the panel.
	 */
	public TagSecurityPanel() {
		super();
	}

	public TagSecurityPanel(JTable dt) {
		super(dt);
	}

	public TagSecurityPanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super(dt, successLst, failLst);
	}

	private void log(Object info) {
		txtLog.append(info.toString() + "\n");
	}

	@Override
	protected void init() {
		setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 112, 978, 92);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 210, 92);
		add(panel_2);
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("TagAccessForm.gb_KillPwd"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_2.setLayout(null);

		JLabel label = new JLabel(getTextString("TagAccessForm.label9"));
		label.setEnabled(false);
		label.setBounds(10, 29, 72, 15);
		panel_2.add(label);

		pwdDestory1 = new JPasswordField();
		pwdDestory1.setDocument(new TextDocument(8, 0));
		pwdDestory1.setEchoChar('*');
		pwdDestory1.setEditable(false);
		pwdDestory1.setBounds(77, 25, 120, 24);
		panel_2.add(pwdDestory1);

		JLabel label_1 = new JLabel(getTextString("TagAccessForm.label15"));
		label_1.setEnabled(false);
		label_1.setBounds(10, 57, 72, 15);
		panel_2.add(label_1);

		pwdDestory2 = new JPasswordField();
		pwdDestory2.setDocument(new TextDocument(8, 0));
		pwdDestory2.setEchoChar('*');
		pwdDestory2.setEditable(false);
		pwdDestory2.setBounds(77, 52, 120, 24);
		panel_2.add(pwdDestory2);

		chkDestroy = new JCheckBox("");
		chkDestroy.setBounds(114, -4, 26, 23);
		panel_2.add(chkDestroy);

		// JLabel label_6 = new
		// JLabel(BaseMessages.getString("TagAccessForm.label119"));
		// label_6.setForeground(Color.GREEN);
		// label_6.setBounds(29, 72, 181, 15);
		// panel_2.add(label_6);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(225, 10, 219, 92);
		add(panel_3);
		panel_3.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), BaseMessages
				.getString("TagAccessForm.gb_AccessPwd"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_3.setLayout(null);

		JLabel label_2 = new JLabel(getTextString("TagAccessForm.label18"));
		label_2.setEnabled(false);
		label_2.setBounds(10, 29, 86, 15);
		panel_3.add(label_2);

		pwdAccess1 = new JPasswordField();
		pwdAccess1.setDocument(new TextDocument(8, 0));
		pwdAccess1.setEchoChar('*');
		pwdAccess1.setEditable(false);
		pwdAccess1.setBounds(94, 26, 110, 24);
		panel_3.add(pwdAccess1);

		JLabel label_3 = new JLabel(getTextString("TagAccessForm.label17"));
		label_3.setEnabled(false);
		label_3.setBounds(10, 58, 86, 15);
		panel_3.add(label_3);

		pwdAccess2 = new JPasswordField();
		pwdAccess2.setDocument(new TextDocument(8, 0));
		pwdAccess2.setEchoChar('*');
		pwdAccess2.setEditable(false);
		pwdAccess2.setBounds(94, 53, 110, 24);
		panel_3.add(pwdAccess2);

		chkAccess = new JCheckBox("");
		chkAccess.setBounds(115, 0, 26, 23);
		panel_3.add(chkAccess);

		// JLabel label_7 = new
		// JLabel(BaseMessages.getString("TagAccessForm.label119"));
		// label_7.setForeground(Color.GREEN);
		// label_7.setBounds(36, 72, 183, 15);
		// panel_3.add(label_7);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(454, 10, 98, 92);
		add(panel_4);
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "EAS\uFF1A",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70,
						213)));
		panel_4.setLayout(null);

		rdoSet = new JRadioButton(getTextString("TagAccessForm.btnModifyPwd"));
		rdoSet.setEnabled(false);
		rdoSet.setBounds(6, 31, 86, 23);
		rdoSet.setActionCommand(SET);
		panel_4.add(rdoSet);

		rdoCancel = new JRadioButton(
				getTextString("TagAccessForm.rb_EasCancel"));
		rdoCancel.setEnabled(false);
		rdoCancel.setBounds(6, 56, 86, 23);
		rdoCancel.setActionCommand(CANCEL);
		panel_4.add(rdoCancel);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdoSet);
		buttonGroup.add(rdoCancel);

		chkEAS = new JCheckBox("");
		chkEAS.setBounds(39, 0, 22, 23);
		panel_4.add(chkEAS);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u9501\u6807\u7B7E\uFF1A",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70,
						213)));
		panel.setBounds(562, 10, 294, 92);
		add(panel);
		panel.setLayout(null);

		JLabel label_4 = new JLabel(getTextString("TagAccessForm.label5"));
		label_4.setEnabled(false);
		label_4.setBounds(10, 20, 110, 20);
		panel.add(label_4);

		cboDataType = new JComboBox();
		cboDataType.setEnabled(false);
		cboDataType.setModel(new DefaultComboBoxModel(new String[] {
				"\u6240\u6709\u4EE3\u7801\u6570\u636E\u533A",
				"TID\u4EE3\u7801\u533A", "EPC\u4EE3\u7801\u533A",
				"\u7528\u6237\u6570\u636E\u533A",
				"\u8BBF\u95EE\u5BC6\u7801\u533A",
				"\u9500\u6BC1\u5BC6\u7801\u533A" }));
		cboDataType.setBounds(130, 20, 151, 20);
		panel.add(cboDataType);

		JLabel label_5 = new JLabel(getTextString("TagAccessForm.label13"));
		label_5.setEnabled(false);
		label_5.setBounds(10, 50, 110, 20);
		panel.add(label_5);

		cboSign = new JComboBox();
		cboSign.setEnabled(false);
		cboSign.setModel(new DefaultComboBoxModel(new String[] {
				"\u9501\u5B9A", "\u89E3\u9501" }));
		cboSign.setBounds(128, 50, 153, 20);
		panel.add(cboSign);

		chkLock = new JCheckBox("");
		chkLock.setBounds(108, -2, 26, 23);
		panel.add(chkLock);

		JButton btnQuery = new JButton(getTextString("TagAccessForm.btn_Query"));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if(buttonGroup.getSelection()!=null){
				// log(buttonGroup.getSelection().getActionCommand());
				// }
				btnQuery_click();
			}
		});
		btnQuery.setBounds(890, 30, 98, 23);
		add(btnQuery);

		JButton btnSet = new JButton(getTextString("TagAccessForm.rb_EasSet"));
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSet_click();
			}
		});
		btnSet.setBounds(890, 64, 98, 23);
		add(btnSet);

		checkBoxs = new JCheckBox[] { chkDestroy, chkAccess, chkLock, chkEAS };

		for (int i = 0; i < checkBoxs.length; i++) {
			checkBoxs[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JCheckBox checkBox = (JCheckBox) e.getSource();
					JPanel jPanel = (JPanel) checkBox.getParent();
					Utils.setPanelEnable(checkBox.isSelected(), jPanel);
					checkBox.setEnabled(true);
				}
			});
		}
	}

	private void btnQuery_click() {
		initResultLst();
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6B".equals(dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.EPCMemory;
			String tag = "";
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 2).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.TIDMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 4).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.UserMemory;
			}
			IMessage msg = null;

			if (chkAccess.isSelected()) {
				msg = new GetTagAccessPwd_6C(antenna, getPwd(), tagID, mb);
				String demoName = (String) dt.getValueAt(selectedRowIndexs[i],
						0);
				Demo demo = DemoRegistry.getTagAccessDemo(demoName);
				if (demo == null)
					continue;
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
					GetTagAccessPwd_6C messsaGetTagAccessPwd_6C = (GetTagAccessPwd_6C) msg;
					txtLog
							.append(tag
									+ "\r\n·ÃÎÊÃÜÂë£º"
									+ Util
											.convertByteArrayToHexWordString(((invengo.javaapi.protocol.IRP1.GetTagAccessPwd_6C.ReceivedInfo) messsaGetTagAccessPwd_6C
													.getReceivedMessage())
													.getAccessPwd()));
				} else {
					failLst.add(selectedRowIndexs[i]);
					txtLog.append(tag + "\r\n·ÃÎÊÃÜÂë²éÑ¯Ê§°Ü  ");
				}
				txtLog.append("\r\n");
			}

			if (chkDestroy.isSelected()) {
				msg = new GetTagKillPwd_6C(antenna, getPwd(), tagID, mb);
				String demoName = (String) dt.getValueAt(selectedRowIndexs[i],
						0);
				Demo demo = DemoRegistry.getTagAccessDemo(demoName);
				if (demo == null)
					continue;
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
					GetTagKillPwd_6C messageGeTagKillPwd_6C = (GetTagKillPwd_6C) msg;
					log(tag
							+ "\r\nÏú»ÙÃÜÂë£º"
							+ Util
									.convertByteArrayToHexWordString(((invengo.javaapi.protocol.IRP1.GetTagKillPwd_6C.ReceivedInfo) messageGeTagKillPwd_6C
											.getReceivedMessage()).getKillPwd()));
				} else {
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
					log(tag + "\r\nÏú»ÙÃÜÂë²éÑ¯Ê§°Ü");
				}

			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(this, BaseMessages
				.getString("Message.MSG_56"));
	}

	private void btnSet_click() {
		initResultLst();
		// ÅÐ¶ÏÃÜÂëÊÇ·ñÒ»ÖÂ
		if (chkAccess.isSelected()) {
			boolean flag = pwdAccess1.getText().equals(pwdAccess2.getText());
			if (!flag) {
				MessageDialog.showErrorMessage(this, BaseMessages
						.getString("Message.MSG_58"));
				return;
			}
		}
		if (chkDestroy.isSelected()) {
			boolean flag = pwdDestory1.getText().equals(pwdDestory2.getText());
			if (!flag) {
				MessageDialog.showErrorMessage(this, BaseMessages
						.getString("Message.MSG_58"));
				return;
			}
		}

		byte[] pwd = getPwd();
		byte[] pwdAccessNew = getPwd(String.copyValueOf(pwdAccess1
				.getPassword()));
		byte[] pwdDestroyNew = getPwd(String.copyValueOf(pwdDestory1
				.getPassword()));
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {
			boolean isSuc = false;
			if ("6B".equals(dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.ReservedMemory;
			String tag = "";
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 2).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.TIDMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 4).toString();
				tagID = Util.convertHexStringToByteArray(tag);
				mb = MemoryBank.UserMemory;
			}
			IMessage msg = null;
			// IRP1 Ð´EPCÊý¾ÝÖ¸Áî
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (chkDestroy.isSelected()) {
				msg = new KillPwdConfig_6C(antenna, pwd, pwdDestroyNew, tagID,
						mb);
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
				} else {
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
				}
			}
			if (chkAccess.isSelected()) {
				msg = new AccessPwdConfig_6C(antenna, pwd, pwdAccessNew, tagID,
						mb);
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
				} else {
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
				}
			}
			// ÉèÖÃeas
			if (chkEAS.isSelected()) {
				byte flag = 0x00;// È¡Ïû
				if (rdoSet.isSelected())
					flag = 0x01;// ÉèÖÃ
				msg = new EasConfig_6C(antenna, pwd, flag, tagID, mb);
				if (demo.getReader().send(msg)) {
					isSuc = true;
					log(tag + "\r\n  EAS£ºÅäÖÃ³É¹¦¡£");
					successLst.add(selectedRowIndexs[i]);
				} else {
					isSuc = false;
					log(tag + "\r\n  EAS£ºÅäÖÃÊ§°Ü¡£");
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
				}
			}

			// Ëø±êÇ©
			if (chkLock.isSelected()) {
				msg = new LockMemoryBank_6C(antenna, pwd, (byte) cboSign
						.getSelectedIndex(), (byte) cboDataType
						.getSelectedIndex(), tagID, mb);
				if (demo.getReader().send(msg)) {
					isSuc = true;
					log(tag + "\r\n  Ëø±êÇ©£º²Ù×÷³É¹¦¡£");
					successLst.add(selectedRowIndexs[i]);
				} else {
					isSuc = false;
					log(tag + "\r\n  Ëø±êÇ©£º²Ù×÷Ê§°Ü¡£");
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
				}
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(BaseMessages.getString("Message.MSG_56"));
	}
}
