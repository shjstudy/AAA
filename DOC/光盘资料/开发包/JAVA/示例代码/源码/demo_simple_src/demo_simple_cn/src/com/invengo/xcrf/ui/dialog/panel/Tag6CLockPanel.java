package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.LockStateQuery_6B;
import invengo.javaapi.protocol.IRP1.LockUserData_6B;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class Tag6CLockPanel extends AbstractTagAccessPanel {

	private static List<JCheckBox> checkList = new ArrayList<JCheckBox>(256);

	public Tag6CLockPanel(JTable dt) {
		super(dt);
	}

	private static final String[] STRINGNAMES = new String[] { "地址:(0-31)",
			"地址:(32-63)", "地址:(64-95)", "地址:(96-127)", "地址:(128-159)",
			"地址:(160-191)", "地址:(192-215)" };
	private static final int MAX = 216;

	/**
	 * Create the panel.
	 */
	public Tag6CLockPanel() {
		super();
	}

	public Tag6CLockPanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super(dt, successLst, failLst);
	}

	@Override
	protected void init() {
		checkList.clear();
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 995, 198);
		add(panel);
		addCheckBox(panel);

		final JCheckBox checkBox = new JCheckBox(
				getTextString("TagAccessForm.cb_LockAll_6B"));
		checkBox.setBounds(0, 204, 60, 23);
		add(checkBox);

		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean flag = checkBox.isSelected();
				setChecked(flag);
			}
		});

		JButton btnQuery = new JButton(
				getTextString("TagAccessForm.btnLock6BQuery"));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLock6BQuery_Click();
			}
		});
		btnQuery.setBounds(827, 208, 74, 23);
		add(btnQuery);

		JButton btnLock = new JButton(getTextString("TagAccessForm.btnLock6B"));
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLock6B_Click();
			}
		});
		btnLock.setBounds(915, 208, 74, 23);
		add(btnLock);
	}

	private final static int lableWidth = 5;

	private void addCheckBox(JPanel panel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		panel.setLayout(gridBagLayout);
		int x = 0;
		int y = 0;
		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.BOTH;
		g.weightx = 1.0;
		for (int i = 0; i < MAX; i++) {
			if (i == 0 || i % 32 == 1) {
				JLabel jLabel = new JLabel(STRINGNAMES[y]);
				g.gridx = x;
				g.gridy = y;
				g.gridwidth = lableWidth;
				panel.add(jLabel, g);
			}
			if (i % 32 == 0 && i != 0) {
				y++;
				x = 0;
			}
			JCheckBox checkBox = new JCheckBox("");
			g.gridx = x + lableWidth + 1;
			g.gridy = y;
			g.gridwidth = 1;
			checkBox.setActionCommand(x + "_" + y);
			checkBox.setToolTipText("地址:" + i);
			checkList.add(checkBox);
			panel.add(checkBox, g);
			x++;
		}
	}

	private void setChecked(boolean flag) {
		for (int i = 0; i < checkList.size(); i++) {
			checkList.get(i).setSelected(flag);
		}
	}

	private void btnLock6BQuery_Click() {

		for (int x = 0; x < checkList.size(); x++) {
			checkList.get(x).setOpaque(false);
			checkList.get(x).setBackground(Color.white);
		}
		initResultLst();
		byte[] qd = new byte[216];
		int count = 0;
		for (int i = 0, len = checkList.size(); i < len; i++) {
			JCheckBox ck = checkList.get(i);
			if (ck.isSelected()) {
				qd[i] = 0x01;
				count++;
			}
		}
		int timeout = count * 50;
		if (timeout < 1000) {
			timeout = 1000;
		} else if (timeout > 2000) {
			timeout = 2000;
		}
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
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
			IMessage msg = new LockStateQuery_6B(antenna, tagID, qd);
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo.getReader().send(msg, timeout)) {
				LockStateQuery_6B lockStateQuery_6B = (LockStateQuery_6B) msg;
				byte[] s = lockStateQuery_6B.getReceivedMessage()
						.getLockResult();

				if (s != null || s.length > 0) {
					for (int j = 0; j < s.length; j++) {
						if (qd[j] == 0x01 && s[j] == 0x01) {
							JCheckBox checkBox = checkList.get(j);
							checkBox.setOpaque(true);
							checkBox.setBackground(Color.red);
						}
					}
				}
				successLst.add(selectedRowIndexs[i]);
			} else {
				failLst.add(selectedRowIndexs[i]);
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(getTextString("Message.MSG_56"));
	}

	private void btnLock6B_Click() {
		initResultLst();
		byte[] qd = new byte[216];
		int count = 0;
		for (int i = 0, len = checkList.size(); i < len; i++) {
			JCheckBox ck = checkList.get(i);
			if (ck.isSelected()) {
				qd[i] = 0x01;
				count++;
			}
		}
		int timeout = count * 50;
		if (timeout < 1000) {
			timeout = 1000;
		}
		int[] selectedRowIndexs = dt.getSelectedRows();
		if (selectedRowIndexs.length == 0) {
			MessageDialog.showErrorMessage(this, BaseMessages
					.getString("Message.MSG_201"));
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
			IMessage msg = new LockUserData_6B(antenna, tagID, qd);
			String demoName = (String) dt.getValueAt(selectedRowIndexs[i], 0);
			Demo demo = DemoRegistry.getTagAccessDemo(demoName);
			if (demo.getReader().send(msg, timeout)) {
				successLst.add(selectedRowIndexs[i]);
			} else {
				failLst.add(selectedRowIndexs[i]);
			}
		}
		makeFace(dt);
		MessageDialog.showInfoMessage(getTextString("Message.MSG_56"));
	}
}
