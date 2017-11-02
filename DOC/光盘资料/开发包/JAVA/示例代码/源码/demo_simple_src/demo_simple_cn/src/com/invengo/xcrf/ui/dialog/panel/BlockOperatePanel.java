package com.invengo.xcrf.ui.dialog.panel;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.MemoryBank;
import invengo.javaapi.core.Util;
import invengo.javaapi.core.Util.LogType;
import invengo.javaapi.protocol.IRP1.BlockErase_6C;
import invengo.javaapi.protocol.IRP1.BlockWrite_6C;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.component.TextDocument;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class BlockOperatePanel extends AbstractTagAccessPanel {
	private static final long serialVersionUID = -1864638421283363310L;
	private JTextField txtData;
	private JSpinner spnStart;
	private JSpinner spnSize;
	private JComboBox spnBlock;
	private JComboBox spnBlock2;
	private JSpinner spnStart2;
	private JSpinner spnLength;
	private JSpinner spinner_1;

	public BlockOperatePanel() {
		super();

	}

	public BlockOperatePanel(JTable dt) {
		super(dt);
	}

	public BlockOperatePanel(JTable dt, List<Integer> successLst,
			List<Integer> failLst) {
		super(dt, successLst, failLst);
	}

	@Override
	protected void init() {
		setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(567, 10, 376, 210);
		add(panel_3);
		panel_3.setBorder(new TitledBorder(null, "\u5757\u64E6:",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setLayout(null);

		spnBlock2 = new JComboBox();
		spnBlock2.setBounds(122, 27, 86, 22);
		spnBlock2.setModel(new DefaultComboBoxModel(new String[] { "Reserved",
				"EPC", "TID", "Userdata" }));
		panel_3.add(spnBlock2);

		JLabel label_2 = new JLabel(getTextString("TagAccessForm.label14"));
		label_2.setBounds(20, 59, 86, 15);
		panel_3.add(label_2);

		JLabel label_3 = new JLabel(getTextString("TagAccessForm.label20"));
		label_3.setBounds(20, 34, 54, 15);
		panel_3.add(label_3);

		spnStart2 = new JSpinner();
		spnStart2.setBounds(122, 59, 86, 22);
		spnStart2.setModel(new SpinnerNumberModel(0, 0, 2, 1));
		panel_3.add(spnStart2);

		JLabel label_4 = new JLabel(getTextString("TagAccessForm.label11"));
		label_4.setBounds(20, 94, 54, 15);
		panel_3.add(label_4);

		spnLength = new JSpinner();
		spnLength.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnLength.setBounds(122, 91, 86, 22);
		panel_3.add(spnLength);

		JButton button_1 = new JButton(
				getTextString("TagAccessForm.btn_BlockErase"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blockErase();
			}
		});
		button_1.setBounds(277, 172, 69, 23);
		panel_3.add(button_1);

		JLabel label_6 = new JLabel(getTextString("TagAccessForm.label66"));
		label_6.setBounds(20, 128, 54, 15);
		panel_3.add(label_6);

		spinner_1 = new JSpinner();
		spinner_1.setBounds(122, 123, 86, 22);
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		panel_3.add(spinner_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(69, 10, 439, 210);
		add(panel_2);
		panel_2.setBorder(new TitledBorder(null,
				getTextString("TagAccessForm.groupBox4"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_2.setLayout(null);

		JLabel label = new JLabel(getTextString("TagAccessForm.label20"));
		label.setBounds(34, 34, 54, 15);
		panel_2.add(label);

		spnBlock = new JComboBox();
		spnBlock.setModel(new DefaultComboBoxModel(new String[] { "Reserved",
				"EPC", "TID", "Userdata" }));
		spnBlock.setBounds(136, 27, 86, 22);

		panel_2.add(spnBlock);

		JLabel label_1 = new JLabel(getTextString("TagAccessForm.label14"));
		label_1.setBounds(34, 67, 86, 15);
		panel_2.add(label_1);

		spnStart = new JSpinner();
		spnStart.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnStart.setBounds(136, 64, 54, 22);
		panel_2.add(spnStart);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				getTextString("TagAccessForm.groupBox6"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_4.setBounds(34, 109, 310, 52);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		txtData = new JTextField();
		txtData.setBounds(10, 21, 290, 21);
		txtData.setDocument(new TextDocument(0, 0));
		panel_4.add(txtData);
		txtData.setColumns(10);

		JButton button = new JButton(
				getTextString("TagAccessForm.btn_BlockErase"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blockWrite();
			}
		});
		button.setBounds(264, 172, 69, 23);
		panel_2.add(button);

		spnSize = new JSpinner();
		spnSize.setModel(new SpinnerNumberModel(0, 0, 64, 1));
		spnSize.setBounds(304, 64, 54, 22);
		panel_2.add(spnSize);

		JLabel label_5 = new JLabel(getTextString("TagAccessForm.label11"));
		label_5.setBounds(212, 67, 82, 15);
		panel_2.add(label_5);
	}

	public IMessage createMessage() {
		return null;
	}

	private void blockWrite() {
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
			byte ptr = Byte.parseByte(spnStart.getValue().toString());
			int index = spnBlock.getSelectedIndex();
			byte blockSize = Byte.parseByte(spnSize.getValue().toString());
			MemoryBank bank = null;
			switch (index) {
			case 00:
				bank = MemoryBank.ReservedMemory;
				break;
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
			// IRP1 写用户数据6C指令
			msg = new BlockWrite_6C(antenna, getPwd(), bank, ptr, blockSize,
					getWriteData(this.txtData.getText()), tagID, mb);
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

	private void blockErase() {
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
			byte ptr = Byte.parseByte(spnStart2.getValue() + "");
			byte blockCount = Byte.parseByte(spinner_1.getValue().toString());
			int blockPtr = spnBlock2.getSelectedIndex();
			MemoryBank bank = null;
			switch (blockPtr) {
			case 00:
				bank = MemoryBank.ReservedMemory;
				break;
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
			byte len = Byte.parseByte(spnLength.getValue() + "");
			// IRP1 写用户数据6C指令
			msg = new BlockErase_6C(antenna, getPwd(), bank, ptr, len,
					blockCount, tagID, mb);
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
}
