package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.protocol.IRP1.WiegandMode_500;
import invengo.javaapi.protocol.IRP1.WorkModeSet_500;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.Utils;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class B5_Wiegand extends JPanel implements ConfigPanel {

	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JPanel panel_1;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public B5_Wiegand() {
		setLayout(null);
		
		setName("B5_Wiegand");

		String[] modeAry = BaseMessages.getString("B5_Wiegand.comboBox1").split("\\|");
		panel = new JPanel();
		panel.setName("groupBox1");
		panel.setBorder(new TitledBorder(null, BaseMessages.getString("B5_Wiegand.groupBox1"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(30, 110, 629, 73);
		panel.setVisible(false);
		add(panel);
		panel.setLayout(null);
		
				JLabel lblMode = new JLabel(BaseMessages.getString("B5_Wiegand.label1"));
				lblMode.setBounds(10, 34, 76, 15);
				panel.add(lblMode);
				comboBox = new JComboBox(modeAry);
				comboBox.setBounds(88, 31, 335, 21);
				panel.add(comboBox);
				comboBox.setSelectedIndex(-1);
				
						JButton btnQuery = new JButton(BaseMessages.getString("B5_Wiegand.btn_Query"));
						btnQuery.setBounds(431, 30, 83, 23);
						panel.add(btnQuery);
						
								JButton btnSet = new JButton(BaseMessages.getString("B5_Wiegand.btn_Set"));
								btnSet.setBounds(528, 30, 91, 23);
								panel.add(btnSet);
								
								panel_1 = new JPanel();
								panel_1.setBorder(new TitledBorder(null, BaseMessages.getString("B5_Wiegand.groupBox2"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
								panel_1.setLayout(null);
								panel_1.setBounds(30, 27, 629, 73);
								add(panel_1);
								
								JLabel label = new JLabel(BaseMessages.getString("B5_Wiegand.label2"));
								label.setBounds(10, 34, 76, 15);
								panel_1.add(label);
								
								comboBox_1 = new JComboBox(BaseMessages.getString("B5_Wiegand.comboBox2").split("\\|"));
								comboBox_1.setSelectedIndex(-1);
								comboBox_1.setBounds(88, 31, 337, 21);
								panel_1.add(comboBox_1);
								
								JButton button = new JButton(BaseMessages.getString("B5_Wiegand.button1"));
								button.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										queryWorkMode();
									}
								});
								button.setBounds(435, 30, 83, 23);
								panel_1.add(button);
								
								JButton button_1 = new JButton(BaseMessages.getString("B5_Wiegand.button2"));
								button_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										button1_Click();
									}
								});
								button_1.setBounds(528, 30, 91, 23);
								panel_1.add(button_1);
								
										btnSet.addActionListener(new ActionListener() {
								
											@Override
											public void actionPerformed(ActionEvent e) {
												set();
											}
								
										});

		btnQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				query();
			}

		});

	}

	private void query() {
		Demo demo = DemoRegistry.getCurrentDemo();
		WiegandMode_500 order = new WiegandMode_500((byte) 0x01, (byte) 0x00);
		if (demo.getReader().send(order)) {
			comboBox.setSelectedIndex(order.getReceivedMessage()
					.getQueryData()[0]);
		} else
		{
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_3"));
			Utils.setPanelDisabled(panel);
		}
			
	}

	private void set() {
		if (comboBox.getSelectedIndex() == -1)
		{
			MessageDialog.showInfoMessage(BaseMessages.getString("Message.MSG_167"));
			return;
		}
			

		byte b = (byte) comboBox.getSelectedIndex();
		Demo demo = DemoRegistry.getCurrentDemo();
		WiegandMode_500 order = new WiegandMode_500((byte) 0x00, b);
		if (demo.getReader().send(order)) {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_31"));
		} else
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_5"));
	}
	
    private void button1_Click()
    {
        if (comboBox_1.getSelectedIndex() == -1)
        {
        	MessageDialog.showInfoMessage(BaseMessages.getString("Message.MSG_166"));
            return;
        }
        byte b = (byte) (comboBox_1.getSelectedIndex() + 1);

        WorkModeSet_500 order = new WorkModeSet_500((byte)0x00, b);
        Demo demo = DemoRegistry.getCurrentDemo();
        if (demo.getReader().send(order))
        {
        	MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_31"));
        }
        else
        	MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_5"));
    }
	
    private void queryWorkMode()
    {
        WorkModeSet_500 order = new WorkModeSet_500((byte)0x01, (byte)0x01);
        Demo demo = DemoRegistry.getCurrentDemo();
        if (demo.getReader().send(order))
        {
        	comboBox_1.setSelectedIndex(order.getReceivedMessage().getQueryData()[0] - 1);
        }
        else
        {
        	MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_3"));
			Utils.setPanelDisabled(panel_1);
        }
    }

	@Override
	public void fillConfigData() {
		queryWorkMode();
		Common.readerCapabilitiesCheck(this);
	}
}
