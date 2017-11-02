package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class B3_KeepAlive extends JPanel implements ConfigPanel {

	private static final long serialVersionUID = -2566019551819850572L;
	JSpinner spinner;

	/**
	 * Create the panel.
	 */
	public B3_KeepAlive() {

		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BaseMessages.getString("B3_KeepAlive.groupBox1"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(25, 25, 378, 146);
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel(BaseMessages.getString("B3_KeepAlive.label2"));
		label.setBounds(28, 61, 89, 15);
		panel.add(label);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0,0,null,1));
		spinner.setBounds(127, 58, 87, 20);
		panel.add(spinner);

		JLabel label_1 = new JLabel("ms");
		label_1.setBounds(224, 61, 30, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel(BaseMessages.getString("B3_KeepAlive.label1"));
		label_2.setBounds(28, 90, 226, 15);
		panel.add(label_2);

		JButton button = new JButton(BaseMessages.getString("B3_KeepAlive.button1"));
		button.setBounds(264, 58, 104, 20);
		panel.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (RFIDProtocol.IRP1 == DemoRegistry.getCurrentDemo()
						.getProtocl()) {
					byte[] t = new byte[2];
					t[0] = (byte) ((Integer) (spinner.getValue()) / 256);
					t[1] = (byte) ((Integer) (spinner.getValue()) % 256);
					SysConfig_800 msg = new SysConfig_800((byte) 0x1E, t);
					if (DemoRegistry.getCurrentDemo().getReader().send(msg)) {
						MessageDialog.showInfoMessage(BaseMessages
								.getString("Message.MSG_31"));
					}
				}
			}
		});
	}

	@Override
	public void fillConfigData() {
		if (RFIDProtocol.IRP1 == DemoRegistry.getCurrentDemo().getProtocl()) {
			SysQuery_800 msg = new SysQuery_800((byte) 0x1E);
			if (DemoRegistry.getCurrentDemo().getReader().send(msg)) {
				spinner
						.setValue((((msg.getReceivedMessage().getQueryData()[0]) & 0XFF) << 8)
								+ ((msg.getReceivedMessage().getQueryData()[1]) & 0XFF));
			}
		}
	}
}
