package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.Util;
import invengo.javaapi.protocol.IRP1.FreqQuery_500;
import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.ui.Utils;
/**
 * 
 * @author yxx981
 *	频段/功率
 */
public class E2_FreqAndPowerPanel extends AbstractConfigPanel{
	private JComboBox cboArea;
	private JList ltArea;
	private JCheckBox ckFreq;
	private JComboBox cboFreqLevel;
	private JSpinner spArgs;
	private JSlider slArgs;
	private JComboBox cboFreqArea;
	private JTextArea txtNotes;
	private String readerType;
	private byte antno = 0x01;
	private JPanel groupBox4;
	private JPanel groupBox5;
	private JPanel groupBox1;
	/**
	 * Create the panel.
	 */
	public E2_FreqAndPowerPanel() {
		setLayout(null);

		groupBox4 = new JPanel();
		groupBox4.setBorder(new TitledBorder(null, "\u529F\u7387\u6821\u51C6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		groupBox4.setBounds(10, 10, 620, 234);
		add(groupBox4);
		groupBox4.setLayout(null);

		groupBox1 = new JPanel();
		groupBox1.setBounds(208, 30, 195, 194);
		groupBox1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u529F\u7387\u6821\u51C6\uFF1A", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 70, 213)));
		groupBox4.add(groupBox1);
		groupBox1.setLayout(null);

		ckFreq = new JCheckBox("");
		ckFreq.setBounds(79, 0, 28, 23);
		groupBox1.add(ckFreq);

		JLabel label_2 = new JLabel("\u529F\u7387\u7B49\u7EA7\uFF1A");
		label_2.setBounds(10, 28, 68, 15);
		groupBox1.add(label_2);

		cboFreqLevel = new JComboBox();
		cboFreqLevel.setBounds(79, 25, 68, 21);
		cboFreqLevel.setSelectedIndex(-1);
		groupBox1.add(cboFreqLevel);

		JLabel lblDpm = new JLabel("dBm");
		lblDpm.setBounds(157, 25, 28, 15);
		groupBox1.add(lblDpm);

		JLabel label_3 = new JLabel("\u6821\u51C6\u53C2\u6570\uFF1A");
		label_3.setBounds(10, 56, 68, 15);
		groupBox1.add(label_3);

		spArgs = new JSpinner();
		spArgs.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spArgs.setBounds(79, 53, 68, 22);
		groupBox1.add(spArgs);

		slArgs = new JSlider();
		slArgs.setValue(0);
		slArgs.setMaximum(255);
		slArgs.setBounds(10, 88, 175, 25);

		slArgs.addChangeListener(new ChangeListener(){			
			public void stateChanged(ChangeEvent e) {
				spArgs.setValue(slArgs.getValue());
			}			
		});

		groupBox1.add(slArgs);

		JButton btnNext = new JButton("\u4E0B\u4E00\u4E2A");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboFreqLevel.getSelectedIndex()<=cboFreqLevel.getItemCount()-2){
					cboFreqLevel.setSelectedIndex(cboFreqLevel.getSelectedIndex()+1);
				}
			}
		});
		btnNext.setBounds(14, 136, 79, 23);
		groupBox1.add(btnNext);

		JButton btnSave = new JButton("\u4FDD\u5B58");
		btnSave.setBounds(103, 136, 79, 23);
		groupBox1.add(btnSave);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(422, 30, 188, 194);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6821\u51C6\u8BB0\u5F55\uFF1A", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		groupBox4.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);

		txtNotes = new JTextArea();
		txtNotes.setLineWrap(true);
		scrollPane.setViewportView(txtNotes);

		JLabel label_1 = new JLabel("\u533A\u57DF:");
		label_1.setBounds(23, 33, 54, 15);
		groupBox4.add(label_1);

		cboArea = new JComboBox();
		cboArea.setModel(new DefaultComboBoxModel(new String[] {"CN", "US", "EU"}));
		cboArea.setBounds(87, 30, 97, 21);
		groupBox4.add(cboArea);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(23, 58, 161, 166);
		groupBox4.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		ltArea = new JList();
		ltArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		ltArea.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		ltArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_4.add(ltArea, BorderLayout.CENTER);

		groupBox5 = new JPanel();
		groupBox5.setBorder(new TitledBorder(null, "\u9891\u6BB5\u7BA1\u7406", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		groupBox5.setBounds(10, 264, 288, 89);
		add(groupBox5);
		groupBox5.setLayout(null);

		JLabel label = new JLabel("\u9891\u6BB5\uFF1A");
		label.setBounds(10, 31, 54, 15);
		groupBox5.add(label);

		cboFreqArea = new JComboBox();
		cboFreqArea.setModel(new DefaultComboBoxModel(new String[] {"FCC(902.750MHz-927.250MHz)", "CN1(920.625MHz-924.375MHz)", "CN2(840.625MHz-844.375MHz)"}));
		cboFreqArea.setBounds(81, 28, 175, 21);
		cboFreqArea.setSelectedIndex(-1);
		groupBox5.add(cboFreqArea);

		JButton btnSet = new JButton("\u8BBE\u7F6E");
		btnSet.setBounds(167, 56, 89, 23);
		groupBox5.add(btnSet);
	}

	@Override
	public void fillConfigData() {
		super.fillConfigData();
		onLoad();
	}
	/**
	 * 面板初始化
	 */
	private void onLoad(){		
		if(this.protocol.equals(RFIDProtocol.IRP1))
		{			
			UserConfig_IRP1 ipr1Config=(UserConfig_IRP1)this.userConfig;
			if("800".equals(ipr1Config.getReaderType()))
			{
				antno=(byte)ipr1Config.getAntennaIndex();
				if (antno > 4)
					antno = 0x01;
				Utils.setPanelEnabled(groupBox4);
				Utils.setPanelDisabled(groupBox5);
				int modelNum = -1;
				//频段查询
				{
					SysQuery_800 squery=new SysQuery_800((byte)0x15);//频段查询
					if(readerInfo.send(squery)){
						modelNum=squery.getReceivedMessage().getQueryData()[0];
						this.cboArea.setEnabled(true);
					}
				}
				//读写器产品型号
				if(modelNum==-1){
					SysQuery_800 squery=new SysQuery_800((byte)0x21);//频段查询
					if(readerInfo.send(squery))
					{
						String wd=Util.convertByteArrayToHexWordString(squery.getReceivedMessage().getQueryData()).toLowerCase();
						char c=wd.charAt(wd.trim().length()-1);
						switch(c){
						case 'c':
							modelNum = 0;
							break;
						case 'a':
							modelNum = 1;
							break;
						case 'e':
							modelNum = 2;
							break;
						case 'k':
							modelNum = 3;
							break;
						case 'j':
							modelNum = 4;
							break;
						default:
							modelNum = 1;
						break;
						}
					}else
					{
						modelNum=1;
					}

					switch (modelNum)
					{
					case 0:						
					case 1:						
					case 2:
						this.cboArea.setSelectedIndex(modelNum);
						break;
					case 3:
					case 4:
						this.cboArea.setSelectedIndex(3);
						break;
					default:
						this.cboArea.setSelectedIndex(1);
					break;
					}
				}
			}
			else
			{
				Utils.setPanelDisabled(groupBox4);
				FreqQuery_500 order = new FreqQuery_500(new byte[] { 0x00 });//频段查询
				if (readerInfo.send(order))//查询系统参数
				{
					cboFreqArea.setSelectedIndex(order.getReceivedMessage().getQueryData()[0]-1);
					Utils.setPanelEnabled(groupBox5);
				}
			}
		}
		Utils.setPanelEnable(false, groupBox1);
		this.ckFreq.setEnabled(true);
	}
	
}
