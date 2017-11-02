package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.invengo.xcrf.core.demo.Demo;

public class MainSimpleDemoPanel extends JPanel{
	
	private Box vBox;
	
	public MainSimpleDemoPanel(Demo demo){
		setLayout(new BorderLayout());
		
		
		this.setLayout(new BorderLayout());
		
		JLabel bl = new JLabel("ÕâÊÇ¶ÁÐ´Æ÷--   " + demo.getDemoName());
		bl.setPreferredSize(new Dimension(100,40));
		add(bl,BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(824,300));
		setMaximumSize(new Dimension(2000,400));
		setBorder(new LineBorder(Color.CYAN));
	}
	
	

}
