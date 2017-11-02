package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.ResetToFactoryDefault_500;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdom.JDOMException;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class A0_BaseConfig extends JPanel implements ConfigPanel {
	private Demo currentDemo;

	/**
	 * Create the panel.
	 */
	public A0_BaseConfig() {
		setLayout(null);
		setName("A0_BaseConfig");
		setPreferredSize(new Dimension(640, 480));

		JLabel label = new JLabel("\u6062\u590D\u51FA\u5382\u8BBE\u7F6E");
		label.setName("label1");
		label.setBounds(459, 381, 109, 15);
		label.setVisible(false);
		add(label);

		Canvas canvas = new Canvas();
		canvas.setName("pic_ResetToFactoryDefault");
		canvas.setBackground(Color.BLUE);
		canvas.setForeground(Color.BLUE);
		canvas.setBounds(405, 370, 35, 35);
		canvas.setVisible(false);
		add(canvas);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentDemo != null) {
					if (currentDemo.getProtocl() == RFIDProtocol.IRP1) {
						if (Utils.FIVE_00.equals(currentDemo.getConfig()
								.getReaderType())) {
							ResetToFactoryDefault_500 order = new ResetToFactoryDefault_500();
							Reader reader = currentDemo.getReader();
							if (reader != null && reader.send(order, 3000)) {
								MessageDialog.showInfoMessage(BaseMessages
										.getString("Message.MSG_24"));
							} else {
								MessageDialog.showInfoMessage(BaseMessages
										.getString("Message.MSG_25"));
							}
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}
		});
		currentDemo = DemoRegistry.getCurrentDemo();
		if (currentDemo != null) {
			try {
				Common.readerCapabilitiesCheck(this, currentDemo.getProtocl()
						.toString(), currentDemo.getConfig().getModelNo(),
						currentDemo.getConfig().getReaderType());
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void fillConfigData() {
		currentDemo = DemoRegistry.getCurrentDemo();

		try {
			Common.readerCapabilitiesCheck(this, currentDemo.getProtocl()
					.toString(), currentDemo.getConfig().getModelNo(),
					currentDemo.getConfig().getReaderType());
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
