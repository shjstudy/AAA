package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.BizExceptions;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class A5_ReaderConnPanel extends JPanel {

	private JList list;

	/**
	 * Create the panel.
	 */
	public A5_ReaderConnPanel() {
		setLayout(null);

		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("A5_ReaderConn.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 191, 148);
		add(panel);
		panel.setLayout(null);

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "9600", "19200", "115200" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(22, 29, 143, 63);
		panel.add(list);

		JButton btnQuery = new JButton(
				BaseMessages.getString("A5_ReaderConn.button1"));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getBaudRate();
				} catch (BizExceptions e1) {
					MessageDialog.showInfoMessage(panel.getParent(),
							e1.getMessage());
				} catch (Exception ex) {
					MessageDialog.showInfoMessage(panel.getParent(),
							ex.getMessage());
				}
			}
		});
		btnQuery.setBounds(22, 114, 70, 23);
		panel.add(btnQuery);

		JButton btnConfig = new JButton(
				BaseMessages.getString("A5_ReaderConn.button2"));
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					configBaudRate();
				} catch (BizExceptions e1) {
					MessageDialog.showInfoMessage(panel.getParent(),
							e1.getMessage());
				} catch (Exception ex) {
					MessageDialog.showInfoMessage(panel.getParent(),
							ex.getMessage());
				}
			}
		});
		btnConfig.setBounds(102, 114, 70, 23);
		panel.add(btnConfig);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(A5_ReaderConnPanel.class
				.getResource("/com/invengo/xcrf/ui/image/Invengo/A-3.png")));
		label.setBounds(10, 170, 425, 300);
		add(label);

	}

	private void getBaudRate() throws BizExceptions {
		UserConfig_IRP1 userConfig = (UserConfig_IRP1) Utils.getUserConfig();
		RFIDProtocol protocool = userConfig.getProtocol();
		String readerType = userConfig.getReaderType();
		if (RFIDProtocol.IRP1.equals(protocool)) {
			if (Utils.FIVE_00.equals(readerType)) {
				throw new BizExceptions(
						BaseMessages.getString("Message.MSG_200"));
			} else if (Utils.EIGHT_00.equals(readerType)) {
				IMessage msg = new SysQuery_800((byte) 0x0C);// 查询波特率
				if (Utils.sendMsg(msg)) {
					SysQuery_800 message = (SysQuery_800) msg;
					this.list.setSelectedIndex(message.getReceivedMessage()
							.getQueryData()[0] - 1);
				} else {
					throw new BizExceptions(
							BaseMessages.getString("Message.MSG_3"));
				}
			}
		}
	}

	private void configBaudRate() throws BizExceptions {
		UserConfig_IRP1 userConfig = (UserConfig_IRP1) Utils.getUserConfig();
		RFIDProtocol protocool = userConfig.getProtocol();
		String readerType = userConfig.getReaderType();
		if (RFIDProtocol.IRP1.equals(protocool)) {
			if (Utils.FIVE_00.equals(readerType)) {
				throw new BizExceptions(
						BaseMessages.getString("Message.MSG_200"));
			} else if (Utils.EIGHT_00.equals(readerType)) {
				IMessage msg = new SysConfig_800((byte) 0x0C,
						new byte[] { (byte) (list.getSelectedIndex() + 1) });// 查询波特率
				if (Utils.sendMsg(msg)) {
					MessageDialog.showInfoMessage(this,
							BaseMessages.getString("Message.MSG_31"));
				} else {
					throw new BizExceptions(
							BaseMessages.getString("Message.MSG_3"));
				}
			}
		}
	}
}
