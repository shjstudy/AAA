package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.ReadUserData2_6B;
import invengo.javaapi.protocol.IRP1.ReadUserData_6B;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class Tag6BReaderPanel extends AbstractTagAccessPanel {
	private JSpinner startAdd;
	private JCheckBox chkRead;
	private JSpinner readLength;
	private JTextArea txtLog;

	/**
	 * Create the panel.
	 */
	public Tag6BReaderPanel() {
		super();
	}

	public Tag6BReaderPanel(JTable dt) {
		super(dt);
	}

	public Tag6BReaderPanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super(dt, successLst, failLst);
	}

	@Override
	protected void init() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 999, 199);
		add(scrollPane);

		txtLog = new JTextArea();
		txtLog.setEditable(false);
		scrollPane.setViewportView(txtLog);

		JPanel panel = new JPanel();
		panel.setBounds(0, 198, 999, 33);
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

		JLabel label = new JLabel(getTextString("TagAccessForm.label8"));
		panel.add(label);

		startAdd = new JSpinner();
		startAdd.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		panel.add(startAdd);

		JLabel label_2 = new JLabel("          ");
		panel.add(label_2);

		JLabel label_1 = new JLabel(getTextString("TagAccessForm.label7"));
		panel.add(label_1);

		readLength = new JSpinner();
		readLength.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		panel.add(readLength);

		JLabel label_3 = new JLabel("          ");
		panel.add(label_3);

		chkRead = new JCheckBox(getTextString("TagAccessForm.checkBox1"));
		panel.add(chkRead);

		JLabel label_4 = new JLabel("                                     ");
		panel.add(label_4);

		JButton btnRead = new JButton(
				getTextString("TagAccessForm.btnReadUd_6C"));
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRead_click();
			}
		});
		panel.add(btnRead);
	}

	private void btnRead_click() {
		initResultLst();
		byte ptr = Byte.parseByte(startAdd.getValue().toString());
		byte len = Byte.parseByte(readLength.getValue().toString());
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this,
					BaseMessages.getString("Message.MSG_201"));
			return;
		}
		for (int i = 0; i < selectedRowIndexs.length; i++) {

			if ("6C".equals(dt.getValueAt(selectedRowIndexs[i], 1)))
				continue;
			byte[] tagID = null;
			byte antenna = Byte.parseByte(dt
					.getValueAt(selectedRowIndexs[i], 5).toString());
			String tag = "";
			if (dt.getValueAt(selectedRowIndexs[i], 3) != null
					&& !dt.getValueAt(selectedRowIndexs[i], 3).toString()
							.equals("")) {
				tag = dt.getValueAt(selectedRowIndexs[i], 3).toString();
				tagID = Util.convertHexStringToByteArray(tag);
			}
			IMessage msg = null;
			if (chkRead.isSelected()) {
				msg = new ReadUserData2_6B(antenna, tagID, ptr, len);
			} else {
				msg = new ReadUserData_6B(antenna, tagID, ptr, len);
			}
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				byte[] bytes = null;
				String ud = "";
				if (msg instanceof ReadUserData2_6B) {
					bytes = ((ReadUserData2_6B) msg).getReceivedMessage()
							.getUserData();
				} else {
					bytes = ((ReadUserData_6B) msg).getReceivedMessage()
							.getUserData();
				}
				ud = Util.convertByteArrayToHexString(bytes);
				txtLog.append("Tag:" + tag + "\r\n");
				txtLog.append("UserData:" + ud + "\r\n");
				successLst.add(selectedRowIndexs[i]);
			} else {
				txtLog.append("Tag:" + tag + "\r\n");
				txtLog.append("UserData:\r\n");
				failLst.add(selectedRowIndexs[i]);
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format("%1$02X",msg.getStatusCode()), "", LogType.Error);
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(getTextString("Message.MSG_56"));
	}
}
