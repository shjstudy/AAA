package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.SysConfig_500;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.SysQuery_500;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.util.IPField;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.tree.RootTree;

public class B6_NetCommPanel extends AbstractConfigPanel {
	private static final long serialVersionUID = -8149589522734381023L;
	private IPField txtIP;
	private IPField txtCode;
	private IPField txtGateway;
	private String[] ips = new String[3];// {“ip”，“subnet”，“gateway”}
	private String readerType;

	private boolean isShowSelectFaildDialog = false;

	/**
	 * Create the panel.
	 */
	public B6_NetCommPanel() {

		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages
				.getString("B6_NetComm.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 273, 124);
		add(panel);
		panel.setLayout(null);

		JLabel lblIp = new JLabel(BaseMessages.getString("B6_NetComm.label1"));
		lblIp.setBounds(10, 21, 54, 15);
		panel.add(lblIp);

		txtIP = new IPField();
		txtIP.setBounds(110, 21, 159, 21);
		panel.add(txtIP);
		// txtIP.setColumns(10);

		JLabel label = new JLabel(BaseMessages.getString("B6_NetComm.label2"));
		label.setBounds(10, 46, 90, 15);
		panel.add(label);

		txtCode = new IPField();
		// txtCode.setColumns(10);
		txtCode.setBounds(110, 44, 159, 21);
		panel.add(txtCode);

		JLabel label_1 = new JLabel(BaseMessages.getString("B6_NetComm.label3"));
		label_1.setBounds(10, 67, 54, 15);
		panel.add(label_1);

		txtGateway = new IPField();
		// txtGateway.setColumns(10);
		txtGateway.setBounds(110, 67, 159, 21);
		panel.add(txtGateway);

		JButton btnQuery = new JButton(BaseMessages
				.getString("B6_NetComm.button3"));
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isShowSelectFaildDialog = true;
				fillConfigData();
				isShowSelectFaildDialog = false;
			}
		});
		btnQuery.setBounds(59, 92, 88, 23);
		panel.add(btnQuery);

		JButton btnSet = new JButton(BaseMessages
				.getString("B6_NetComm.button4"));
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setIP();
			}
		});
		btnSet.setBounds(151, 92, 99, 23);
		panel.add(btnSet);

	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();
		UserConfig_IRP1 userConfig = (UserConfig_IRP1) this.userConfig;
		this.readerType = userConfig.getReaderType();
		if (RFIDProtocol.IRP1.equals(this.protocol)) {
			if (Utils.FIVE_00.equals(readerType)) {
				IMessage msg = new SysQuery_500((byte) 0x00, (byte) 0x0c);
				if (Utils.sendMsg(msg)) {
					SysQuery_500 message = (SysQuery_500) msg;
					getIP(message.getReceivedMessage().getQueryData());
				} else {
					if (isShowSelectFaildDialog) {
						MessageDialog.showInfoMessage(this, BaseMessages
								.getString("Message.MSG_3"));
						isShowSelectFaildDialog = false;
					}
				}

			} else if (Utils.EIGHT_00.equals(readerType)) {
				IMessage msg = new SysQuery_800((byte) 0x06);
				if (Utils.sendMsg(msg)) {
					SysQuery_800 message = (SysQuery_800) msg;
					getIP(message.getReceivedMessage().getQueryData());
				} else {
					if (isShowSelectFaildDialog) {
						MessageDialog.showInfoMessage(this, BaseMessages
								.getString("Message.MSG_3"));
						isShowSelectFaildDialog = false;
					}
				}
			}

		}

	}

	private void getIP(byte[] datas) {
		int data[] = new int[datas.length];
		for (int i = 0; i < datas.length; i++) {
			data[i] = datas[i] & 0xff;
		}
		StringBuffer ip = new StringBuffer(128);
		StringBuffer subnet = new StringBuffer(128);
		StringBuffer gateway = new StringBuffer(128);
		StringBuffer bf[] = new StringBuffer[] { ip, subnet, gateway };
		int count = 0;
		for (int i : data) {
			int h = count % 4;
			int k = count / 4;
			if (h != 3)
				bf[k].append(i).append(".");
			else {
				bf[k].append(i);
			}
			count++;
		}
		// 显示
		this.txtIP.setText(ip.toString());
		this.txtCode.setText(subnet.toString());
		this.txtGateway.setText(gateway.toString());
		// 设置成员变量
		this.ips[0] = ip.toString();
		this.ips[1] = subnet.toString();
		this.ips[2] = gateway.toString();
	}

	private void setIP() {
		byte ipData[] = new byte[12];
		fillIpData(ipData);
		if (ips[0].equals(txtIP.getText()) && ips[1].equals(txtCode.getText())
				&& ips[2].equals(txtGateway.getText())) {
			MessageDialog.showInfoMessage(this, BaseMessages
					.getString("Message.MSG_33"));
		} else {
			IMessage msg = null;
			if (Utils.FIVE_00.equals(readerType)) {
				msg = new SysConfig_500((byte) 0x00, (byte) 0x0c, ipData);
			} else if (Utils.EIGHT_00.equals(readerType)) {
				msg = new SysConfig_800((byte) 0x06, ipData);
			}
			if (Utils.sendMsg(msg)) {
				this.ips[0] = txtIP.getIpAddress();
				this.ips[1] = txtCode.getIpAddress();
				this.ips[2] = txtGateway.getIpAddress();
				Demo demo = DemoRegistry.getCurrentDemo();
				if (demo.getReader().getPortType().equals("TCPIP_Client")) {
					demo.getReader().setCommConnect(false);
					demo.getReader().disConnect();
					RootTree.getTree().updateUI();
					MainFrame.getMainFrame().UIUpdateWhenDemoConnected();
					MessageDialog.showInfoMessage(this, BaseMessages
							.getString("Message.MSG_233"));
				} else {
					MessageDialog.showInfoMessage(this, BaseMessages
							.getString("Message.MSG_31"));
				}
			} else {
				MessageDialog.showInfoMessage(this, BaseMessages
						.getString("Message.MSG_5"));
			}
		}
	}

	private void fillIpData(byte[] ipData) {
		int p = 0;
		for (String s : txtIP.getText().split("\\.")) {
			ipData[p] = (byte) Integer.parseInt(s);
			p++;
		}
		for (String s : txtCode.getText().split("\\.")) {
			ipData[p] = (byte) Integer.parseInt(s);
			p++;
		}
		for (String s : txtGateway.getText().split("\\.")) {
			ipData[p] = (byte) Integer.parseInt(s);
			p++;
		}
	}

	private void serverConfigBtnClick() {
	}

	private void serverQueryBtnClick() {
	}

}
