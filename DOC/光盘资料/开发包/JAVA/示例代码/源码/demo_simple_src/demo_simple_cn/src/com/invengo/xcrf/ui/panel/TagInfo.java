package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;

public class TagInfo extends JPanel implements LanguageChangeListener{

	private static final long serialVersionUID = 6150938241454637009L;
	private JLabel lblTagSum = new JLabel(BaseMessages.getString("MainForm.label1"));
	JLabel intTagSum = new JLabel();

	// private JLabel lblScanSum = new JLabel("");
	JLabel intScanum = new JLabel();

	private JLabel lblCurSpeed = new JLabel(BaseMessages.getString("MainForm.label2"));
	JLabel intCurSpeed = new JLabel();

	private JLabel lblAvgSpeed = new JLabel(BaseMessages.getString("MainForm.label3"));
	JLabel intAvgSpeed = new JLabel();

	private JLabel lblScanTime = new JLabel(BaseMessages.getString("MainForm.label4"));
	JLabel intScanTime = new JLabel();

	public int avgSpeed = 0;
	public int immediateSpeed = 0;
	public int scanTotal = 0;
	public int scanTime = 0;

	public VistaCPUInfo vistaCPUInfo;

	public TagInfo() {

		// 创建表盘对象
		vistaCPUInfo = new VistaCPUInfo();
		vistaCPUInfo.setPreferredSize(new Dimension(198, 159));
		vistaCPUInfo.setOpaque(false);

		// 创建标签信息
		JPanel tagInfo = new JPanel();
		tagInfo.setOpaque(false);
		tagInfo.setLayout(null);
		tagInfo.setPreferredSize(new Dimension(340, 160));

		this.intTagSum.setText("0");
		this.intCurSpeed.setText("0");
		this.intAvgSpeed.setText("0");
		this.intScanTime.setText("00.00.00");

		lblTagSum.setBounds(10, 40, 120, 20);
		lblTagSum.setHorizontalAlignment(SwingConstants.RIGHT);
		tagInfo.add(lblTagSum);
		intTagSum.setBounds(140, 40, 40, 20);
		tagInfo.add(intTagSum);
		lblCurSpeed.setBounds(10, 65, 120, 20);
		lblCurSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		tagInfo.add(lblCurSpeed);
		intCurSpeed.setBounds(140, 65, 40, 20);
		tagInfo.add(intCurSpeed);
		lblAvgSpeed.setBounds(10, 90, 120, 20);
		lblAvgSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
		tagInfo.add(lblAvgSpeed);
		intAvgSpeed.setBounds(140, 90, 40, 20);
		tagInfo.add(intAvgSpeed);
		lblScanTime.setBounds(10, 115, 120, 20);
		lblScanTime.setHorizontalAlignment(SwingConstants.RIGHT);
		tagInfo.add(lblScanTime);
		intScanTime.setBounds(140, 115, 100, 20);
		tagInfo.add(intScanTime);

		SettingPanel settingPanel = new SettingPanel();
		settingPanel.setOpaque(false);

		this.setLayout(new BorderLayout(0, 10));

		this.add(vistaCPUInfo, BorderLayout.WEST);
		this.add(tagInfo, BorderLayout.CENTER);
		this.add(settingPanel, BorderLayout.EAST);
		
		BaseMessages.addListener(this);
	}

	@Override
	public void updateResource() {
		// TODO Auto-generated method stub
		lblTagSum.setText(BaseMessages.getString("MainForm.label1"));
		lblCurSpeed.setText(BaseMessages.getString("MainForm.label2"));
		lblAvgSpeed.setText(BaseMessages.getString("MainForm.label3"));
		lblScanTime.setText(BaseMessages.getString("MainForm.label4"));
		this.updateUI();
	}

}
