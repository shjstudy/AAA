package com.invengo.xcrf.ui.dialog;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.AccessPwdConfig_6C;
import invengo.javaapi.protocol.IRP1.KillPwdConfig_6C;
import invengo.javaapi.protocol.IRP1.KillTag_6C;
import invengo.javaapi.protocol.IRP1.LockMemoryBank_6C;
import invengo.javaapi.protocol.IRP1.ReadUserData_6C;
import invengo.javaapi.protocol.IRP1.WriteEpc;
import invengo.javaapi.protocol.IRP1.WriteUserData_6C;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.component.DefaultJSpinner;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.panel.AbstractTagAccessPanel;
import com.invengo.xcrf.ui.dialog.panel.BlockOperatePanel;
import com.invengo.xcrf.ui.dialog.panel.Tag6BReaderPanel;
import com.invengo.xcrf.ui.dialog.panel.Tag6BWritePanel;
import com.invengo.xcrf.ui.dialog.panel.Tag6CLockPanel;
import com.invengo.xcrf.ui.dialog.panel.TagSecurityPanel;

public class TagAccessDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIndex;
	private JPasswordField passwordField;
	private JCheckBox checkBox;
	private JTextPane textPane;
	private JTable dt;
	private List<Integer> successLst = new ArrayList<Integer>();
	private List<Integer> failLst = new ArrayList<Integer>();
	private DefaultJSpinner ptr_readud_6c;
	private DefaultJSpinner len_readud_6c;
	private JTextPane panel_readud_6c;
	private JTextPane userDataPanel;
	private DefaultJSpinner spinner_2;// 写用户的起始地址
	private JPasswordField pwdKillString;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JPasswordField passwordField_4;
	private JPasswordField passwordField_5;
	private JCheckBox cb_Check6C;
	private JTabbedPane tabbedPane;
	private JLabel label_16;

	// 标签
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_6;
	private AbstractTagAccessPanel blockAccessPanel;
	private JPanel panel_7;
	private AbstractTagAccessPanel tagSecurityPanel;
	private JPanel tag6BWritePanel;
	private AbstractTagAccessPanel panel_8;
	private AbstractTagAccessPanel panel_10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.invengo.xcrf.ui.theme.InvengoLookAndFeel");
			TagAccessDialog dialog = new TagAccessDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					super.windowClosed(e);
					System.exit(0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TagAccessDialog(JTable data) {
		this.dt = data;
		setBounds(100, 100, 1024, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		passwordField = new JPasswordField();
		passwordField.setDocument(new TextDocument(8, 0));
		passwordField.setEchoChar('*');
		passwordField.setText("00000000");
		passwordField.setBounds(802, 256, 189, 21);
		contentPanel.add(passwordField);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null,
				getTextString("TagAccessForm.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLUE));
		panel.setBounds(12, 12, 996, 234);
		contentPanel.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(6, 20, 980, 208);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(new JScrollPane(dt), BorderLayout.CENTER);

		checkBox = new JCheckBox(getTextString("TagAccessForm.cb_Index"));
		checkBox.setBounds(18, 257, 63, 20);
		contentPanel.add(checkBox);
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (checkBox.isSelected()) {
					txtIndex.setEnabled(true);
				} else {
					txtIndex.setEnabled(false);
				}
			}

		});

		txtIndex = new JTextField();
		txtIndex.setEnabled(false);
		txtIndex.setBounds(95, 256, 178, 21);
		contentPanel.add(txtIndex);
		txtIndex.setColumns(10);

		label_16 = new JLabel(getTextString("A2_TagFilter.label30"));
		label_16.setBounds(276, 256, 71, 21);
		contentPanel.add(label_16);

		final JLabel label_1 = new JLabel(getTextString("TagAccessForm.label2"));
		label_1.setBounds(681, 256, 111, 21);
		contentPanel.add(label_1);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(12, 288, 996, 270);
		contentPanel.add(tabbedPane);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int index = tabbedPane.getSelectedIndex();
				if (index == 0 || index == 2 || index == 3 || index == 7) {
					if (dt.getSelectedRowCount() >= 2) {
						txtIndex.setVisible(true);
						checkBox.setVisible(true);
						label_16.setVisible(true);
					}
				} else {
					label_16.setVisible(false);
					txtIndex.setVisible(false);
					checkBox.setVisible(false);
				}
				if (index == 0 || index == 2 || index == 3 || index == 5) {
					label_1.setVisible(true);
					passwordField.setVisible(true);
				} else {
					label_1.setVisible(false);
					passwordField.setVisible(false);
				}

			}

		});
		// 标签控件
		tabControll();

		// dt.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent arg0) {
		// // TODO Auto-generated method stub
		// if(dt.getSelectedRowCount() < 2){
		// checkBox.setVisible(false);
		// txtIndex.setVisible(false);
		// label_16.setVisible(false);
		// }else{
		// checkBox.setVisible(true);
		// txtIndex.setVisible(true);
		// label_16.setVisible(true);
		// }
		// tabControll();
		// }
		// });

		dt.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						// TODO Auto-generated method stub
						if (dt.getSelectedRowCount() < 2) {
							int index = tabbedPane.getSelectedIndex();
							if (index == 0 || index == 2 || index == 3
									|| index == 7) {
								checkBox.setVisible(false);
								txtIndex.setVisible(false);
								label_16.setVisible(false);
							}
						} else {
							checkBox.setVisible(true);
							txtIndex.setVisible(true);
							label_16.setVisible(true);
						}
						// tabControll();
					}

				});
		// dt.addKeyListener(new KeyAdapter(){
		//
		// @Override
		// public void keyPressed(KeyEvent key) {
		// // TODO Auto-generated method stub
		// super.keyPressed(key);
		//				
		// int k = key.getKeyCode();
		// switch(k){
		// case 37 :
		// case 38 :
		// case 39 :
		// case 40 :
		// tabControll();
		// break;
		// default :
		// //do nothing
		// return;
		// }
		// }
		//			
		// });
		if (dt.getSelectedRowCount() < 2) {
			checkBox.setVisible(false);
			txtIndex.setVisible(false);
			label_16.setVisible(false);
		} else {
			checkBox.setVisible(true);
			txtIndex.setVisible(true);
			label_16.setVisible(true);
		}

		setResizable(false);
		setModal(true);
	}

	/**
	 * 根据密码销毁标签
	 * 
	 * @param pwdField
	 */
	private void btnKill_click(JPasswordField pwdField) {
		initResultLst();
		int option = JOptionPane.showConfirmDialog(this,
				getTextString("Message.MSG_57"),
				getTextString("Message.MSG_21"), JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			byte[] pwd = getPwd(String.copyValueOf(pwdField.getPassword()));
			int[] selectedRowIndexs = dt.getSelectedRows();
			if (selectedRowIndexs.length == 0) {
				MessageDialog.showErrorMessage(this, BaseMessages
						.getString("Message.MSG_201"));
				return;
			}
			for (int i = 0; i < selectedRowIndexs.length; i++) {

				if ("6B"
						.equals((String) dt.getValueAt(selectedRowIndexs[i], 1)))
					continue;

				byte antenna = Byte.parseByte((String) dt.getValueAt(
						selectedRowIndexs[i], 5));
				byte[] tagID = null;
				if (dt.getValueAt(selectedRowIndexs[i], 2) != null
						&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
								.equals("")) {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 2).toString());

				} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
						&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
								.equals("")) {
					String tId = dt.getValueAt(selectedRowIndexs[i], 3)
							.toString();
					tagID = Util.convertHexStringToByteArray(tId);
				} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
						&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
								.equals("")) {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 4).toString());
				}
				IMessage msg = new KillTag_6C(antenna, pwd, tagID);
				// IRP1 写EPC数据指令
				String demoName = (String) dt.getValueAt(selectedRowIndexs[i],
						0);
				Demo demo = DemoRegistry.getTagAccessDemo(demoName);
				if (demo == null)
					continue;
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
				} else {
					failLst.add(selectedRowIndexs[i]);
					Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
							"%1$02X", msg.getStatusCode()), "", LogType.Error);
				}
			}
			makeFace(dt);
			MessageDialog
					.showInfoMessage(this, getTextString("Message.MSG_56"));
		}
	}

	/**
	 * 
	 * @param ch1销毁密码
	 * @param chk2访问密码
	 * @param fileds
	 */
	private void btnModifyPwd(JCheckBox ch1, JCheckBox ch2,
			JPasswordField[] fileds) {
		initResultLst();
		if (ch1.isSelected()) {
			boolean flag = fileds[0].getText().equals(fileds[1].getText());
			if (!flag) {
				MessageDialog.showErrorMessage(this, BaseMessages
						.getString("Message.MSG_58"));
				return;
			}
		}
		if (ch2.isSelected()) {
			boolean flag = fileds[2].getText().equals(fileds[3].getText());
			if (!flag) {
				MessageDialog.showErrorMessage(this, BaseMessages
						.getString("Message.MSG_58"));
				return;
			}
		}
		byte[] pwd = getPwd(String.copyValueOf(passwordField.getPassword()));
		byte[] pwdKillNew = getPwd(String.copyValueOf(fileds[0].getPassword()));
		byte[] pwdAccessNew = getPwd(String
				.copyValueOf(fileds[2].getPassword()));
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6B".equals((String) dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.EPCMemory;
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				tagID = Util.convertHexStringToByteArray(dt.getValueAt(
						selectedRowIndexs[i], 2).toString());
				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				String tId = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tId);
				mb = MemoryBank.TIDMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {
				tagID = Util.convertHexStringToByteArray(dt.getValueAt(
						selectedRowIndexs[i], 4).toString());
				mb = MemoryBank.UserMemory;
			}
			IMessage msg = null;
			// IRP1 写EPC数据指令
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (ch1.isSelected()) {
				msg = new KillPwdConfig_6C(antenna, pwd, pwdKillNew, tagID, mb);
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
				} else {
					failLst.add(selectedRowIndexs[i]);
				}
			}
			if (ch2.isSelected()) {
				msg = new AccessPwdConfig_6C(antenna, pwd, pwdAccessNew, tagID,
						mb);
				if (demo.getReader().send(msg)) {
					successLst.add(selectedRowIndexs[i]);
				} else {
					failLst.add(selectedRowIndexs[i]);
				}
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(this, getTextString("Message.MSG_56"));
	}

	/**
	 * 
	 * @param e
	 * @param j1数据类型下拉框
	 * @param j2锁定表示
	 */
	private void btnLockOK_click(ActionEvent e, JComboBox j1, JComboBox j2) {
		initResultLst();
		byte[] pwd = getPwd(String.copyValueOf(passwordField.getPassword()));
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6B".equals((String) dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.ReservedMemory;
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				tagID = Util.convertHexStringToByteArray(dt.getValueAt(
						selectedRowIndexs[i], 2).toString());
				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				String tId = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tId);
				mb = MemoryBank.TIDMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {
				tagID = Util.convertHexStringToByteArray(dt.getValueAt(
						selectedRowIndexs[i], 4).toString());
				mb = MemoryBank.UserMemory;
			}
			IMessage msg = null;
			// IRP1 写EPC数据指令
			msg = new LockMemoryBank_6C(antenna, pwd, (byte) j2
					.getSelectedIndex(), (byte) j1.getSelectedIndex(), tagID,
					mb);
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				successLst.add(selectedRowIndexs[i]);
			} else {
				failLst.add(selectedRowIndexs[i]);
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
						"%1$02X", msg.getStatusCode()), "", LogType.Error);
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(this, getTextString("Message.MSG_56"));
	}

	private void btnWriteUserData(ActionEvent e) {
		initResultLst();
		byte[] pwd = getPwd(String.copyValueOf(passwordField.getPassword()));
		byte[] wd = getWriteData(userDataPanel.getText());

		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6B".equals((String) dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.ReservedMemory;
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {

				if (Common.EPC_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 2).toString());
				} else {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 2).toString());
				}

				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				String tId = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tId);
				mb = MemoryBank.TIDMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {

				if (Common.UserData_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 4).toString());
				} else {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 4).toString());
				}

				mb = MemoryBank.UserMemory;
			}
			IMessage msg = null;
			byte ptr = (byte) spinner_2.getIntValue();
			// IRP1 写EPC数据指令
			msg = new WriteUserData_6C(antenna, pwd, ptr, wd, tagID, mb);

			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				if (cb_Check6C.isSelected()) {
					ReadUserData_6C rdMsg = new ReadUserData_6C(antenna, ptr,
							(byte) (wd.length / 2), tagID, mb);
					if (demo.getReader().send(rdMsg)) {
						byte[] rd = rdMsg.getReceivedMessage().getUserData();
						Boolean isSuc = true;
						for (int j = 0; j < wd.length; j++) {
							if (rd[j] != wd[j]) {
								isSuc = false;
								break;
							}
						}
						if (isSuc) {
							dt.setValueAt(Util.convertByteArrayToHexString(wd),
									selectedRowIndexs[i], 4);
							successLst.add(selectedRowIndexs[i]);
							/**
							 * 2012.7.11,xusheng,更新修改项
							 */

						} else {
							failLst.add(selectedRowIndexs[i]);
						}
					}
				} else {
					dt.setValueAt(Util.convertByteArrayToHexString(wd),
							selectedRowIndexs[i], 4);
					successLst.add(selectedRowIndexs[i]);
				}

				if (checkBox.isSelected()) {
					wd = bytesAdd(wd, Util.convertHexStringToByteArray(txtIndex
							.getText().trim()));
				}
			} else {
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
						"%1$02X", msg.getStatusCode()), "", LogType.Error);
				failLst.add(selectedRowIndexs[i]);
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(this, getTextString("Message.MSG_56"));
	}

	private void btnWriteEpc_Click() {
		initResultLst();
		byte[] pwd = getPwd(String.copyValueOf(passwordField.getPassword()));
		byte[] wd = getWriteData(textPane.getText());

		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}

		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6B".equals((String) dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;

			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			MemoryBank mb = MemoryBank.EPCMemory;
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				if (Common.EPC_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 2).toString());
				} else {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 2).toString());
				}

				mb = MemoryBank.EPCMemory;
			} else if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {

				tagID = Util.convertHexStringToByteArray(dt.getValueAt(
						selectedRowIndexs[i], 3).toString());
				mb = MemoryBank.TIDMemory;

			} else if (dt.getValueAt(selectedRowIndexs[i], 4) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 4).toString()
							.equals("")) {

				if (Common.UserData_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 4).toString());
				} else {
					tagID = Util.convertHexStringToByteArray(dt.getValueAt(
							selectedRowIndexs[i], 4).toString());
				}
				mb = MemoryBank.UserMemory;

			}
			IMessage msg = null;

			// IRP1 写EPC数据指令
			msg = new WriteEpc(antenna, pwd, wd, tagID, mb);

			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				dt.setValueAt(Util.convertByteArrayToHexString(wd),
						selectedRowIndexs[i], 2);
				successLst.add(selectedRowIndexs[i]);

				if (checkBox.isSelected()) {
					wd = bytesAdd(wd, Util.convertHexStringToByteArray(txtIndex
							.getText().trim()));
				}
			} else {
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
						"%1$02X", msg.getStatusCode()), "", LogType.Error);
				failLst.add(selectedRowIndexs[i]);
			}
		}

		makeFace(dt);

		MessageDialog.showInfoMessage(this, getTextString("Message.MSG_56"));

	}

	private void btnReadUd_6C_Click() {
		initResultLst();
		byte ptr = (byte) ptr_readud_6c.getIntValue();
		byte len = (byte) len_readud_6c.getIntValue();

		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
			return;
		}

		String resultStr = "";

		for (int i = 0; i < selectedRowIndexs.length; i++) {
			byte antenna = Byte.parseByte((String) dt.getValueAt(
					selectedRowIndexs[i], 5));
			byte[] tagID = null;
			String tag = "";
			MemoryBank mb = MemoryBank.EPCMemory;
			if (dt.getValueAt(selectedRowIndexs[i], 2) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 2).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 2).toString();

				if (Common.EPC_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(tag);
				} else {
					tagID = Util.convertHexStringToByteArray(tag);
				}
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

				if (Common.UserData_ASCII) {
					tagID = Common.convertAsciiStringToByteArray(tag);
				} else {
					tagID = Util.convertHexStringToByteArray(tag);
				}
				mb = MemoryBank.UserMemory;

			}
			IMessage msg = null;

			msg = new ReadUserData_6C(antenna, ptr, len, tagID, mb);

			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				successLst.add(selectedRowIndexs[i]);
				String ud = Util
						.convertByteArrayToHexWordString(((ReadUserData_6C) msg)
								.getReceivedMessage().getUserData());
				resultStr += "Tag:" + tag + "\r\n" + "UserData:" + ud
						+ "\r\n\r\n";

			} else {
				failLst.add(selectedRowIndexs[i]);
				resultStr += "Tag:" + tag + "\r\n" + "UserData:"
						+ msg.getErrInfo() + "\r\n\r\n";
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format(
						"%1$02X", msg.getStatusCode()), "", LogType.Error);
			}
		}

		panel_readud_6c.setText(resultStr);
		makeFace(dt);

		MessageDialog.showInfoMessage(this, getTextString("Message.MSG_56"));
	}

	public void initResultLst() {
		successLst.clear();
		failLst.clear();
	}

	public void clearTableSelected() {

	}

	public void makeFace(JTable table) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {

					if (successLst.contains(row)) {
						setBackground(new Color(0, 115, 0));
					} else if (failLst.contains(row)) {
						setBackground(new Color(115, 0, 0));
					} else {
						setBackground(Color.white);
					}
					return super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
			table.getSelectionModel().clearSelection();
			table.updateUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public byte[] getPwd(String pwdText) {
		if (pwdText.equals(""))
			return new byte[4];
		byte[] pwd = Util.convertHexStringToByteArray(pwdText);
		if (pwd.length < 4) {
			byte[] p = new byte[4];
			System.arraycopy(pwd, 0, p, 4 - pwd.length, pwd.length);
			return p;
		}
		return pwd;
	}

	byte[] getWriteData(String str) {
		byte[] data = Util.convertHexStringToByteArray(str);
		if (data.length % 2 == 1) {
			byte[] d = new byte[data.length + 1];
			System.arraycopy(data, 0, d, 0, data.length);
			return d;
		}
		return data;
	}

	byte[] bytesAdd(byte[] bytes, byte[] index) {
		byte[] b = new byte[bytes.length];
		System.arraycopy(bytes, 0, b, 0, b.length);
		int pre = 0;
		for (int i = 0; i < index.length; i++) {
			int bs = (int) bytes[bytes.length - i - 1]
					+ (int) index[index.length - i - 1] + pre;
			pre = bs / 256;
			b[b.length - i - 1] = (byte) (bs % 256);
		}
		int p = index.length;
		if (pre > 0 && b.length > p) {
			while (b[b.length - p - 1] == 0xff) {
				b[b.length - p - 1] = 0;
				p++;
				if (b.length <= p)
					break;
			}
			if (b.length > p)
				b[b.length - p - 1] += 1;
		}
		return b;
	}

	private String getTextString(String name) {
		return BaseMessages.getString(name);
	}

	// 根据标签数据来控制需要显示的tab
	private void tabControll() {

		tabbedPane.removeAll();

		boolean flag6C = false;
		boolean flag6B = false;
		if (dt == null || dt.getSelectedRowCount() == 0)
			return;

		for (int i : dt.getSelectedRows()) {
			if (((String) (dt.getValueAt(i, 1))).equals("6C") && !flag6C) {
				flag6C = true;
				panel_2 = new JPanel();
				tabbedPane.addTab(getTextString("TagAccessForm.tP_WriteEpc"),
						null, panel_2, null);
				panel_2.setLayout(null);

				textPane = new JTextPane();
				textPane.setBorder(new LineBorder(Color.LIGHT_GRAY));
				textPane.setBounds(10, 10, 971, 168);
				int len = Common.EPC_MaxLen;
				textPane.setDocument(new TextDocument(len * 4, 0));
				textPane.enableInputMethods(false);
				textPane.setLayout(new BorderLayout());
				JPanel jplText = new JPanel();
				jplText
						.setPreferredSize(new Dimension(textPane.getWidth(), 30));
				jplText.setLayout(new BorderLayout());
				textPane.add(jplText, BorderLayout.SOUTH);
				final JLabel dText = new JLabel(
						"数据长度:0字节 (当前字符总数:0, 字符类型:Hex )");
				// dText.setHorizontalAlignment(SwingConstants.LEFT);
				jplText.add(dText, BorderLayout.CENTER);
				textPane.getDocument().addDocumentListener(
						new DocumentListener() {

							@Override
							public void changedUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (textPane.getText().length() % 4 != 0)
									count = textPane.getText().length() / 4 + 1;
								else
									count = textPane.getText().length() / 4;
								dText.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ textPane.getText().length()
										+ ", 字符类型:Hex )");
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (textPane.getText().length() % 4 != 0)
									count = textPane.getText().length() / 4 + 1;
								else
									count = textPane.getText().length() / 4;
								dText.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ textPane.getText().length()
										+ ", 字符类型:Hex )");
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (textPane.getText().length() % 4 != 0)
									count = textPane.getText().length() / 4 + 1;
								else
									count = textPane.getText().length() / 4;
								dText.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ textPane.getText().length()
										+ ", 字符类型:Hex )");
							}
						});
				panel_2.add(textPane);

				JButton button = new JButton(
						getTextString("TagAccessForm.btnWriteUd_6C"));
				button.setBounds(863, 188, 93, 23);
				panel_2.add(button);

				panel_3 = new JPanel();
				tabbedPane.addTab(getTextString("TagAccessForm.tP_ReadUd_6C"),
						null, panel_3, null);
				panel_3.setLayout(null);

				panel_readud_6c = new JTextPane();
				panel_readud_6c.setBorder(new LineBorder(Color.LIGHT_GRAY));
				panel_readud_6c.setBounds(10, 10, 971, 168);
				panel_readud_6c.setEditable(false);
				panel_3.add(panel_readud_6c);

				JLabel lblNew = new JLabel(getTextString("C4_TagRW.label6"));
				lblNew.setBounds(10, 188, 77, 22);
				panel_3.add(lblNew);

				ptr_readud_6c = new DefaultJSpinner(new SpinnerNumberModel(0,
						0, 192, 1));
				ptr_readud_6c.setBounds(88, 188, 85, 22);
				panel_3.add(ptr_readud_6c);

				JLabel lblNew_1 = new JLabel(
						getTextString("TagAccessForm.label6"));
				lblNew_1.setBounds(209, 188, 77, 22);
				panel_3.add(lblNew_1);

				len_readud_6c = new DefaultJSpinner(new SpinnerNumberModel(0,
						0, 192, 1));
				len_readud_6c.setModel(new SpinnerNumberModel(2, 0, 192, 1));
				len_readud_6c.setBounds(286, 188, 85, 22);
				len_readud_6c.setValue(4);
				panel_3.add(len_readud_6c);

				JButton button_1 = new JButton(
						getTextString("TagAccessForm.btnReadUd_6C"));
				button_1.setBounds(662, 188, 93, 23);
				panel_3.add(button_1);

				panel_4 = new JPanel();
				tabbedPane.addTab(getTextString("TagAccessForm.tp_WriteUd_6C"),
						null, panel_4, null);
				panel_4.setLayout(null);

				userDataPanel = new JTextPane();
				userDataPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				userDataPanel.setBounds(10, 10, 971, 168);
				len = Common.Userdata_MaxLen_6C;
				userDataPanel.setDocument(new TextDocument(len * 4, 0));
				userDataPanel.enableInputMethods(false);
				userDataPanel.setLayout(new BorderLayout());
				JPanel jplText2 = new JPanel();
				jplText2.setPreferredSize(new Dimension(userDataPanel
						.getWidth(), 30));
				jplText2.setLayout(new BorderLayout());
				userDataPanel.add(jplText2, BorderLayout.SOUTH);
				final JLabel dText2 = new JLabel(
						"数据长度:0字节 (当前字符总数:0, 字符类型:Hex )");
				// dText.setHorizontalAlignment(SwingConstants.LEFT);
				jplText2.add(dText2, BorderLayout.CENTER);
				userDataPanel.getDocument().addDocumentListener(
						new DocumentListener() {

							@Override
							public void changedUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (userDataPanel.getText().length() % 4 != 0)
									count = userDataPanel.getText().length() / 4 + 1;
								else
									count = userDataPanel.getText().length() / 4;
								dText2.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ userDataPanel.getText().length()
										+ ", 字符类型:Hex )");
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (userDataPanel.getText().length() % 4 != 0)
									count = userDataPanel.getText().length() / 4 + 1;
								else
									count = userDataPanel.getText().length() / 4;
								dText2.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ userDataPanel.getText().length()
										+ ", 字符类型:Hex )");
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								// TODO Auto-generated method stub
								int count = 0;
								if (userDataPanel.getText().length() % 4 != 0)
									count = userDataPanel.getText().length() / 4 + 1;
								else
									count = userDataPanel.getText().length() / 4;
								dText2.setText("数据长度:" + count + "字节 (当前字符总数:"
										+ userDataPanel.getText().length()
										+ ", 字符类型:Hex )");
							}
						});
				panel_4.add(userDataPanel);

				JLabel label_2 = new JLabel(
						getTextString("TagConfigForm.label8"));
				label_2.setBounds(10, 188, 77, 22);
				panel_4.add(label_2);

				spinner_2 = new DefaultJSpinner(new SpinnerNumberModel(0, 0,
						192, 1));
				spinner_2.setBounds(88, 188, 85, 22);
				panel_4.add(spinner_2);

				cb_Check6C = new JCheckBox(BaseMessages
						.getString("TagAccessForm.cb_Check6C"));
				cb_Check6C.setBounds(500, 175, 85, 50);
				panel_4.add(cb_Check6C);

				JButton button_2 = new JButton(
						getTextString("TagAccessForm.btnWriteEpc"));
				button_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnWriteUserData(e);
					}
				});
				button_2.setBounds(662, 188, 93, 23);
				panel_4.add(button_2);

				panel_6 = new JPanel();

				panel_6.setLayout(null);

				JLabel lblDel = new JLabel(
						getTextString("TagAccessForm.label3"));
				lblDel.setBounds(193, 45, 167, 22);
				panel_6.add(lblDel);

				JLabel label_5 = new JLabel(
						getTextString("TagAccessForm.label16"));
				label_5.setBounds(193, 103, 146, 15);
				panel_6.add(label_5);

				pwdKillString = new JPasswordField();
				pwdKillString.setDocument(new TextDocument(8, 0));
				pwdKillString.setBounds(345, 99, 121, 24);
				panel_6.add(pwdKillString);

				JButton btnKillTag = new JButton(
						getTextString("A2_TagFilter.button5"));
				btnKillTag.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnKill_click(pwdKillString);
					}
				});
				btnKillTag.setBounds(603, 156, 93, 23);
				panel_6.add(btnKillTag);

				blockAccessPanel = new BlockOperatePanel(dt, successLst,
						failLst);
				blockAccessPanel.setPwdField(passwordField);
				// tabbedPane.addTab("锁标签 6C", null, panel_5, null);
				tabbedPane.addTab(getTextString("TagAccessForm.tP_Block"),
						null, blockAccessPanel, null);

				JPanel panel_5 = new JPanel();
				// tabbedPane.addTab("锁标签 6C", null, panel_5, null);
				tabbedPane.addTab(getTextString("TagAccessForm.tP_KillTag_6C"),
						null, panel_6, null);
				panel_5.setLayout(null);

				JLabel label_3 = new JLabel(
						getTextString("TagAccessForm.label5"));
				label_3.setBounds(69, 70, 87, 20);
				panel_5.add(label_3);

				final JComboBox comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(new String[] {
						"\u6240\u6709\u4EE3\u7801\u6570\u636E\u533A",
						"TID\u4EE3\u7801\u533A", "EPC\u4EE3\u7801\u533A",
						"\u7528\u6237\u6570\u636E\u533A",
						"\u8BBF\u95EE\u5BC6\u7801\u533A",
						"\u9500\u6BC1\u5BC6\u7801\u533A" }));
				comboBox.setBounds(162, 70, 172, 20);
				panel_5.add(comboBox);

				final JComboBox comboBox_1 = new JComboBox();
				comboBox_1.setModel(new DefaultComboBoxModel(new String[] {
						"\u9501\u5B9A", "\u89E3\u9501" }));
				comboBox_1.setBounds(516, 70, 133, 20);
				panel_5.add(comboBox_1);

				JLabel label_4 = new JLabel(
						getTextString("TagAccessForm.label13"));
				label_4.setBounds(419, 70, 87, 20);
				panel_5.add(label_4);

				JButton btnLockOK = new JButton(
						getTextString("TagAccessForm.btn_BlockErase"));
				btnLockOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnLockOK_click(e, comboBox, comboBox_1);
					}
				});
				btnLockOK.setBounds(646, 181, 93, 23);
				panel_5.add(btnLockOK);

				panel_7 = new JPanel();
				// tabbedPane.addTab("标签密码 6C", null, panel_7, null);
				panel_7.setLayout(null);

				tagSecurityPanel = new TagSecurityPanel(dt, successLst, failLst);
				tagSecurityPanel.setPwdField(passwordField);
				tabbedPane.addTab(getTextString("TagAccessForm.tP_TagPwd_6C"),
						null, tagSecurityPanel, null);

				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnWriteEpc_Click();
					}
				});

				button_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnReadUd_6C_Click();
					}
				});

			}
			if (((String) (dt.getValueAt(i, 1))).equals("6B") && !flag6B) {
				flag6B = true;
				panel_8 = new Tag6BReaderPanel(dt, successLst, failLst);
				panel_8.setPwdField(passwordField);
				tabbedPane.addTab(getTextString("TagAccessForm.tP_ReadUd_6B"),
						null, panel_8, null);

				Tag6BWritePanel tag6BWritePanel = new Tag6BWritePanel(dt,
						successLst, failLst);
				tag6BWritePanel.setCbIndex(checkBox);
				tag6BWritePanel.setPwdField(passwordField);
				tag6BWritePanel.setTxtIndex(txtIndex);
				tabbedPane.addTab(getTextString("TagAccessForm.tp_WriteUd_6B"),
						null, tag6BWritePanel, null);

				panel_10 = new Tag6CLockPanel(dt, successLst, failLst);
				panel_10.setPwdField(passwordField);
				tabbedPane.addTab(getTextString("TagAccessForm.tP_LockTag_6B"),
						null, panel_10, null);

				checkBox.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						txtIndex.setEnabled(checkBox.isSelected());
					}

				});
			}

		}

	}

}
