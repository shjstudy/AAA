package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.ReaderInterval_500;
import invengo.javaapi.protocol.IRP1.TagUpInterval_500;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class B7_NetMode extends JPanel implements ConfigPanel {

	private Demo currentDemo;
	private JSpinner snrConnectTime;
	private JSpinner snrDataSendTime;
	private JSpinner snrRequestSendTime;
	private JSpinner snrInterval;

	/**
	 * Create the panel.
	 */
	public B7_NetMode() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel
				.setBorder(new TitledBorder(
						null,
						"\u76F8\u540C\u6807\u7B7E\u4E0A\u4F20\u65F6\u95F4\u8BBE\u7F6E/\u67E5\u8BE2",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 20, 330, 92);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(
				"\u95F4\u9694\u65F6\u95F4\uFF08\u79D2\uFF09\uFF1A");
		label.setBounds(22, 38, 104, 15);
		panel.add(label);

		snrInterval = new JSpinner();
		snrInterval.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
		snrInterval.setBounds(121, 35, 66, 22);
		panel.add(snrInterval);

		JButton btnConfig = new JButton("\u8BBE\u7F6E");
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] interval = new byte[2];
				int value = Integer.parseInt(snrInterval.getValue().toString());
				interval[0] = (byte) (value / 256);
				interval[1] = (byte) (value % 256);
				TagUpInterval_500 order = new TagUpInterval_500((byte) 0x00,
						interval);

				if (currentDemo.getReader().send(order)) {
					MessageDialog.showInfoMessage("设置成功");
				} else {
					MessageDialog.showInfoMessage("设置失败");
				}

			}
		});
		btnConfig.setBounds(220, 34, 76, 23);
		panel.add(btnConfig);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null,
				"\u5176\u4ED6\u65F6\u95F4\u8BBE\u7F6E/\u67E5\u8BE2",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 134, 330, 233);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel label_1 = new JLabel(
				"\u4E0E\u670D\u52A1\u5668\u7684\u8FDE\u63A5\u65F6\u95F4\uFF08\u79D2\uFF09\uFF1A");
		label_1.setBounds(21, 49, 168, 15);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel(
				"\u6807\u7B7E\u6570\u636E\u53D1\u9001\u65F6\u95F4\uFF08\u79D2\uFF09\uFF1A");
		label_2.setBounds(21, 84, 168, 15);
		panel_1.add(label_2);

		JLabel label_3 = new JLabel(
				"\u6388\u65F6\u8BF7\u6C42\u53D1\u9001\u65F6\u95F4\uFF08\u79D2\uFF09\uFF1A");
		label_3.setBounds(21, 120, 168, 15);
		panel_1.add(label_3);

		snrConnectTime = new JSpinner();
		snrConnectTime.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
		snrConnectTime.setBounds(199, 42, 59, 22);
		panel_1.add(snrConnectTime);

		snrDataSendTime = new JSpinner();
		snrDataSendTime.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
		snrDataSendTime.setBounds(199, 77, 59, 22);
		panel_1.add(snrDataSendTime);

		snrRequestSendTime = new JSpinner();
		snrRequestSendTime.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
		snrRequestSendTime.setBounds(199, 113, 59, 22);
		panel_1.add(snrRequestSendTime);

		JButton btnOtherConfig = new JButton("\u8BBE\u7F6E");
		btnOtherConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] interval = new byte[3];
				interval[0] = Byte.parseByte(snrConnectTime.getValue()
						.toString());
				interval[1] = Byte.parseByte(snrDataSendTime.getValue()
						.toString());
				interval[2] = Byte.parseByte(snrRequestSendTime.getValue()
						.toString());
				ReaderInterval_500 order = new ReaderInterval_500((byte) 0x00,
						interval);
				if (currentDemo.getReader().send(order)) {
					MessageDialog.showInfoMessage("设置成功");
				} else {
					MessageDialog.showInfoMessage("设置失败");
				}
			}
		});
		btnOtherConfig.setBounds(220, 167, 76, 23);
		panel_1.add(btnOtherConfig);

	}

	@Override
	public void fillConfigData() {
		currentDemo = DemoRegistry.getCurrentDemo();

		if (currentDemo.getProtocl() == RFIDProtocol.IRP1) {
			TagUpInterval_500 order = new TagUpInterval_500((byte) 0x01,
					new byte[] { 0x00, 0x00 });
			Reader reader = currentDemo.getReader();
			if (reader != null && reader.send(order)) {
				this.snrInterval.setValue(order.getReceivedMessage()
						.getIntervalSecond());
			}
			ReaderInterval_500 msg = new ReaderInterval_500((byte) 0x01,
					new byte[] { 0x01, 0x01, 0x01 });
			if (reader != null && reader.send(msg)) {
				byte[] data = msg.getReceivedMessage().getInfoParam();
				if (data != null && data.length >= 3) {
					this.snrConnectTime.setValue(msg.getReceivedMessage()
							.getInfoParam()[0]);
					this.snrDataSendTime.setValue(msg.getReceivedMessage()
							.getInfoParam()[1]);
					this.snrRequestSendTime.setValue(msg.getReceivedMessage()
							.getInfoParam()[2]);
				}
			}
		}
	}
}
