package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class MainPanel extends JPanel {

	
	public MainDemoPanel panel;
	/**
	 * Create the panel.
	 */
	public MainPanel(JFrame frame) {
		setLayout(new BorderLayout(0, 0));
		
	    panel = new MainDemoPanel(frame,SwingConstants.TOP);
		add(panel, BorderLayout.CENTER);
		panel.setBorder(new LineBorder(Color.CYAN));
		
		JPanel whitePanel = new JPanel();
		whitePanel.setOpaque(false);
		whitePanel.setPreferredSize(new Dimension(28,20));
		//add(whitePanel,BorderLayout.EAST);
		
//		JPanel logPanel = new LogPanel();
//		logPanel.setPreferredSize(new Dimension(816,100));
//		add(logPanel, BorderLayout.SOUTH);
		
//		JPanel panel_2 = new JPanel();
//		add(panel_1, BorderLayout.NORTH);

	}

}
