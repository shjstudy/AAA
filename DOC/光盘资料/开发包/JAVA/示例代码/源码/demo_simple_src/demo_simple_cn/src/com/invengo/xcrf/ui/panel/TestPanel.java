package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TestPanel extends JPanel{
	
	JLabel label;
	
	public TestPanel()
	{
		this(null);
	}
	
	public TestPanel(String show)
	{
		this.setLayout(new BorderLayout());
		
		label = new JLabel(show);
		
//		label.setBackground(new Color(3355443));
//		label.setForeground(Color.white);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
//		label.setPreferredSize(new Dimension(200,30));
		
		
		add(label,BorderLayout.CENTER);
		
		this.setPreferredSize(new Dimension(1000,800));
	}
	
	public void setLabel(String msg)
	{
		label.setText("这是测试界面 -- "+msg);
	}
	

}
