package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.ReadUserData2_6B;
import invengo.javaapi.protocol.IRP1.ReadUserData_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData2_6B;
import invengo.javaapi.protocol.IRP1.WriteUserData_6B;

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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class Tag6BWritePanel extends AbstractTagAccessPanel {
	private JCheckBox chkWrite;
	private JSpinner spnStar;
	private JTextArea textWriteData;
	private JCheckBox cbIndex;
	private JCheckBox cbCheck6B;
	private JTextField txtIndex;

	public void setCbIndex(JCheckBox cbIndex) {
		this.cbIndex = cbIndex;
	}

	public Tag6BWritePanel() {
		super();

	}

	public Tag6BWritePanel(JTable dt) {
		super(dt);
	}

	public Tag6BWritePanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super(dt, successLst, failLst);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 990, 198);
		add(scrollPane);

		textWriteData = new JTextArea();
		scrollPane.setViewportView(textWriteData);
		textWriteData.setDocument(new TextDocument(Common.userdata_MaxLen_6B,0));
		textWriteData.enableInputMethods(false);
		JPanel panel = new JPanel();
		panel.setBounds(0, 198, 990, 33);
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

		JLabel label = new JLabel(getTextString("TagAccessForm.label4"));
		panel.add(label);

		spnStar = new JSpinner();
		spnStar.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		panel.add(spnStar);

		JLabel label_1 = new JLabel("              ");
		panel.add(label_1);

		chkWrite = new JCheckBox(getTextString("TagAccessForm.checkBox2"));
		panel.add(chkWrite);

		JLabel label_2 = new JLabel(
				"                                                  ");
		panel.add(label_2);

		cbCheck6B = new JCheckBox(getTextString("TagAccessForm.cb_Check6B"));
		panel.add(cbCheck6B);

		JButton button = new JButton(
				getTextString("TagAccessForm.btnWriteUd_6B"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnWriteData_click();
			}
		});
		panel.add(button);
	}

	private void btnWriteData_click() {
		initResultLst();
		// –¥»Î–£—È
		if ("".equals(this.textWriteData.getText().trim())) {
			MessageDialog.showInfoMessage(getTextString("Message.MSG_55"));
			return;
		}
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this,
					BaseMessages.getString("Message.MSG_201"));
			return;
		}
		byte ptr = Byte.parseByte(this.spnStar.getValue().toString());
		byte[] wd = getWriteData(this.textWriteData.getText());
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
			if (chkWrite.isSelected()) {
				msg = new WriteUserData2_6B(antenna, tagID, ptr, wd);
			} else {
				msg = new WriteUserData_6B(antenna, tagID, ptr, wd);
			}
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo == null)
				continue;
			if (demo.getReader().send(msg)) {
				if (cbCheck6B.isSelected()) {
					if (chkWrite.isSelected()) {
						ReadUserData2_6B readUserData = new ReadUserData2_6B(
								antenna, tagID, ptr, (byte) wd.length);
						if (demo.getReader().send(msg)) {
							byte[] rd = readUserData.getReceivedMessage()
									.getUserData();
							boolean isSuccess = true;
							for (int j = 0; j < wd.length; j++) {
								if (rd[i] != wd[i]) {
									isSuccess = false;
									break;
								}
							}
							if (isSuccess) {
								successLst.add(selectedRowIndexs[i]);
							} else {
								failLst.add(selectedRowIndexs[i]);
							}
						}
					} else {
						ReadUserData_6B readUserData = new ReadUserData_6B(
								antenna, tagID, ptr, (byte) wd.length);
						if (demo.getReader().send(msg)) {
							byte[] rd = readUserData.getReceivedMessage()
									.getUserData();
							boolean isSuccess = true;
							for (int j = 0; j < wd.length; j++) {
								if (rd[i] != wd[i]) {
									isSuccess = false;
									break;
								}
							}
							if (isSuccess) {
								successLst.add(selectedRowIndexs[i]);
							} else {
								failLst.add(selectedRowIndexs[i]);
							}
						}
					}
				} else {
					successLst.add(selectedRowIndexs[i]);
					if (cbIndex.isSelected()) {
						wd = bytesAdd(wd, getWriteData(txtIndex.getText()));
					}
				}
			} else {
				failLst.add(selectedRowIndexs[i]);
				Util.logAndTriggerApiErr(demo.getDemoName(), String.format("%1$02X",msg.getStatusCode()), "", LogType.Error);
			}

		}
		makeFace(dt);
		MessageDialog.showInfoMessage(getTextString("Message.MSG_56"));
	}

	public void setTxtIndex(JTextField txtIndex) {
		this.txtIndex = txtIndex;
	}

}
