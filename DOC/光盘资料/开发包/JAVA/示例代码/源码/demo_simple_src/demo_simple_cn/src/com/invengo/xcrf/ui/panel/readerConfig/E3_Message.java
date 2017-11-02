package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IBufferReceivedHandle;
import invengo.javaapi.protocol.IRP1.CRCClass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.util.DealMessageUtil;
import com.invengo.xcrf.core.util.DealMessageUtil.Item;
import com.invengo.xcrf.core.util.DealMessageUtil.Message;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class E3_Message extends JPanel implements ConfigPanel,
		IBufferReceivedHandle {
	private static final long serialVersionUID = -839824216018701887L;
	private JPanel listPanel; // 指令列表
	private JPanel buildPanel; // 指令构造
	private JPanel paramPanel; // 指令参数
	private JPanel monitorPanel; // 数据监控
	private JPanel sendPanel; // 发送数据
	private JPanel receivePanel; // 接收数据

	private JList commandList;
	private final DefaultListModel listModel = new DefaultListModel();
	private JLabel lblCommand4List;
	private JTextField txtCommand4List;
	private JButton btnChangeCommand;

	private JCheckBox chkPip;
	private JCheckBox chkCommandLen;
	private JCheckBox chkCommand;
	private JTextField txtCommand4Build;
	private JCheckBox chkcrc;
	private JButton btnSend;
	private JTextArea txtCommandParam;

	private JTextArea txtSendData;
	private JTextArea txtReceiveData;

	private JButton btnClean;

	private List<Message> messages = null;// 列表的内容
	private Demo currentDemo;

	public E3_Message() {
		messages = DealMessageUtil.getMessages();
		fillListData();
		buildComponent();
	}

	@Override
	public void fillConfigData() {
		currentDemo = DemoRegistry.getCurrentDemo();
		addReader();
	}

	private void addReader() {
		List<IBufferReceivedHandle> onBufferReceived = new ArrayList<IBufferReceivedHandle>();
		onBufferReceived.add(this);
		BaseReader.onBufferReceived = onBufferReceived;
	}

	// 构建页面的组件
	private void buildComponent() {
		setLayout(null);

		listPanel = new JPanel();
		listPanel.setBorder(new TitledBorder(null, "\u6307\u4EE4\u5217\u8868:",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listPanel.setBounds(10, 10, 199, 450);
		add(listPanel);
		listPanel.setLayout(null);

		lblCommand4List = new JLabel("\u6307\u4EE4\u5B57:");
		lblCommand4List.setBounds(10, 421, 54, 15);
		listPanel.add(lblCommand4List);

		commandList = new JList(listModel);
		commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commandList.setSelectedIndex(0);
		commandList.setBounds(1, 1, 177, 386);

		commandList.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				onListMouseClicked();
			}
		});

		JScrollPane scrollPane = new JScrollPane(commandList);

		scrollPane.setBounds(10, 24, 179, 388);
		listPanel.add(scrollPane);

		txtCommand4List = new JTextField();
		txtCommand4List.setBounds(58, 419, 48, 21);
		txtCommand4List.setColumns(10);
		listPanel.add(txtCommand4List);

		btnChangeCommand = new JButton("\u66F4\u6539");
		btnChangeCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeAction();
			}
		});
		btnChangeCommand.setBounds(121, 420, 65, 23);
		listPanel.add(btnChangeCommand);

		buildPanel = new JPanel();
		buildPanel.setBorder(new TitledBorder(null,
				"\u6307\u4EE4\u6784\u9020:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		buildPanel.setBounds(212, 10, 458, 150);
		add(buildPanel);
		buildPanel.setLayout(null);

		chkPip = new JCheckBox("\u9488\u5934");
		chkPip.setBounds(16, 20, 61, 23);
		buildPanel.add(chkPip);

		chkCommandLen = new JCheckBox("\u6307\u4EE4\u957F\u5EA6");
		chkCommandLen.setSelected(true);
		chkCommandLen.setBounds(82, 20, 92, 23);
		buildPanel.add(chkCommandLen);

		chkCommand = new JCheckBox("\u6307\u4EE4\u5B57");
		chkCommand.setSelected(true);
		chkCommand.setBounds(173, 20, 65, 23);
		buildPanel.add(chkCommand);

		txtCommand4Build = new JTextField();
		txtCommand4Build.setBounds(244, 21, 49, 21);
		buildPanel.add(txtCommand4Build);
		txtCommand4Build.setColumns(10);

		chkcrc = new JCheckBox("CRC");
		chkcrc.setSelected(true);
		chkcrc.setBounds(303, 20, 49, 23);
		buildPanel.add(chkcrc);

		btnSend = new JButton("\u53D1\u9001");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendAction();
			}
		});
		btnSend.setBounds(358, 20, 65, 23);
		buildPanel.add(btnSend);

		paramPanel = new JPanel();
		paramPanel.setBorder(new TitledBorder(null,
				"\u6307\u4EE4\u53C2\u6570:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		paramPanel.setBounds(6, 45, 442, 95);
		buildPanel.add(paramPanel);
		paramPanel.setLayout(null);

		txtCommandParam = new JTextArea();
		txtCommandParam.setBounds(10, 23, 422, 62);
		paramPanel.add(txtCommandParam);

		monitorPanel = new JPanel();
		monitorPanel.setBorder(new TitledBorder(null,
				"\u6570\u636E\u76D1\u63A7:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		monitorPanel.setBounds(212, 159, 458, 301);
		add(monitorPanel);
		monitorPanel.setLayout(null);

		sendPanel = new JPanel();
		sendPanel.setBorder(new TitledBorder(null, "\u53D1\u9001\u6570\u636E:",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sendPanel.setBounds(10, 24, 438, 108);
		monitorPanel.add(sendPanel);
		sendPanel.setLayout(null);

		txtSendData = new JTextArea();
		txtSendData.setBounds(7, 26, 421, 72);
		scrollPane = new JScrollPane(txtSendData);
		scrollPane.setBounds(7, 26, 421, 72);
		sendPanel.add(scrollPane);

		receivePanel = new JPanel();
		receivePanel.setBorder(new TitledBorder(null,
				"\u63A5\u6536\u6570\u636E:", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		receivePanel.setBounds(10, 142, 438, 108);
		monitorPanel.add(receivePanel);
		receivePanel.setLayout(null);

		txtReceiveData = new JTextArea();
		txtReceiveData.setBounds(10, 22, 418, 76);
		txtReceiveData.setLineWrap(true);
		scrollPane = new JScrollPane(txtReceiveData);
		scrollPane.setBounds(10, 22, 418, 76);
		receivePanel.add(scrollPane);

		btnClean = new JButton("\u6E05\u7A7A");
		btnClean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cleanAction();
			}
		});
		btnClean.setBounds(378, 260, 65, 23);
		monitorPanel.add(btnClean);
	}

	private void fillListData() {
		for (Message msg : messages) {
			String type = msg.getType();
			if ("00".equals(type)) {
				String parentName = msg.getName();
				for (Item item : msg.getItems()) {
					listModel.addElement(parentName + "(" + item.getName()
							+ ")");
				}
			} else {
				listModel.addElement(msg.getName());
			}
		}
	}

	// 清空按钮的事件
	private void cleanAction() {
		txtSendData.setText("");
		txtReceiveData.setText("");
	}

	private void sendAction() {
		String commandParam = txtCommandParam.getText().trim();

		if (!((!commandParam.equals("") && Util.isHexString(commandParam)) || commandParam
				.equals(""))) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_40"));
			return;
		}
		String command4Build = txtCommand4Build.getText().trim();
		if (command4Build == null || command4Build.equals("")
				|| !Util.isHexString(command4Build)) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_40"));
			return;
		}

		if (command4Build.length() % 2 == 1) {
			txtCommand4Build.setText("0" + command4Build);
		}

		if (commandParam.length() % 2 == 1) {
			commandParam = "0" + commandParam;
			txtCommandParam.setText(commandParam);

		}

		// string msgStr = textBox4.Text.Trim();
		// // 指令字
		if (chkCommand.isSelected()) {
			commandParam = txtCommand4Build.getText() + commandParam;
		}
		// // 指令长度
		if (chkCommandLen.isSelected()) {
			int len = commandParam.length() / 2;
			commandParam = Util.convertIntToHexString(len) + commandParam;
		}

		byte[] data = null;
		// CRC处理
		if (chkcrc.isSelected()) {
			int buffLen = commandParam.length() / 2 + 2;
			byte[] buff = new byte[buffLen];
			byte[] b = Util.convertHexStringToByteArray(commandParam);
			System.arraycopy(b, 0, buff, 0, b.length);
			byte[] crc = CRCClass.getCRC16(b);
			System.arraycopy(crc, 0, buff, b.length, 2);
			data = buff;
		} else {
			data = Util.convertHexStringToByteArray(commandParam);
		}

		if (chkPip.isSelected()) {
			data = formatData(data);
		}

		txtSendData.setText(txtSendData.getText()
				+ Util.convertByteArrayToHexString(data) + "\r\n");
		currentDemo.getReader().send(data);
	}

	private byte[] formatData(byte[] data) {
		int count = data.length;
		for (byte b : data) {
			if (b == 0x55 || b == 0x56)
				count++;
		}
		byte[] m = new byte[count + 1];
		m[0] = 0x55;
		if (count > data.length) {
			int p = 1;
			for (byte b : data) {
				if (b == 0x55) {
					m[p] = 0x56;
					p++;
					m[p] = 0x56;
				} else if (b == 0x56) {
					m[p] = 0x56;
					p++;
					m[p] = 0x57;
				} else {
					m[p] = b;
				}
				p++;
			}
		} else {
			System.arraycopy(data, 0, m, 1, count);
		}
		return m;
	}

	/**
	 * 修改按钮的点击事件处理器
	 */
	private void changeAction() {
		String command = txtCommand4List.getText().trim();
		if (!Util.isHexString(command)) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_40"));
			return;
		}
		if (command.length() % 2 == 1) {
			command = "0" + command;
		}

		command = command.toUpperCase();

		String nodeName = commandList.getSelectedValue().toString();
		boolean isExist = false;
		int index = nodeName.indexOf("(");
		if (index != -1) {

			String parentName = nodeName.substring(0, index);
			String childName = nodeName.substring(index + 1,
					nodeName.length() - 1);
			outter: for (Message msg : messages) {
				if (parentName.equals(msg.getName())) {
					for (Item item : msg.getItems()) {
						if (command.equals(item.getType())) {
							isExist = true;
							break outter;
						}
					}
					break;
				}
			}
			if (isExist) {
				MessageDialog.showInfoMessage(BaseMessages
						.getString("Message.MSG_43"));
				return;
			} else {
				if (MessageDialog.showConfirmDialog(BaseMessages.getString("",
						"Message.MSG_44", new String[] { nodeName, command }),
						BaseMessages.getString("Message.MSG_21"))) {
					DealMessageUtil.changeCommandValue(parentName, childName,
							command);
					outter: for (Message msg : messages) {
						if (parentName.equals(msg.getName())) {
							for (Item item : msg.getItems()) {
								if (childName.equals(item.getName())) {
									item.setType(command);
									break outter;
								}
							}
							break;
						}
					}
					txtCommand4Build.setText(command);
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_45"));
				}
			}
		} else {
			for (Message msg : messages) {
				if (command.equals(msg.getType())) {
					isExist = true;
					break;
				}
			}

			if (isExist) {
				MessageDialog.showInfoMessage(BaseMessages
						.getString("Message.MSG_43"));
				return;
			} else {
				if (MessageDialog.showConfirmDialog(BaseMessages.getString("",
						"Message.MSG_44", new String[] { nodeName, command }),
						BaseMessages.getString("Message.MSG_21"))) {
					DealMessageUtil.changeCommandValue(nodeName, command);
					for (Message msg : messages) {
						if (nodeName.equals(msg.getName())) {
							msg.setType(command);
						}
					}
					txtCommand4Build.setText(command);
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_45"));
				}
			}

		}

	}

	/**
	 * 左侧列表的点击事件
	 */
	private void onListMouseClicked() {
		String value = (String) commandList.getSelectedValue();
		String type = "";
		int index = value.indexOf("(");
		if (index != -1) {
			String parentName = value.substring(0, index);
			String childName = value.substring(index + 1, value.length() - 1);
			outter: for (Message msg : messages) {
				if (parentName.equals(msg.getName())) {
					for (Item item : msg.getItems()) {
						if (childName.equals(item.getName())) {
							type = item.getType();
							break outter;
						}
					}
				}
			}
		} else {
			for (Message msg : messages) {
				if (value.equals(msg.getName())) {
					type = msg.getType();
					break;
				}
			}

		}
		txtCommand4List.setText(type);
		txtCommand4Build.setText(type);

	}

	@Override
	public void bufferReceived(BaseReader reader, byte[] originalData) {
		txtReceiveData.setText(txtReceiveData.getText()
				+ Util.convertByteArrayToHexString(originalData));

	}

}
