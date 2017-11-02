package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author zxl672
 */
public class EightCenterNorthJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2731413622765242772L;
	private EightCenterNorthOneJPanel centerNorthOneJPanel;

	public EightCenterNorthOneJPanel getCenterNorthOneJPanel() {
		return centerNorthOneJPanel;
	}

	public EightCenterNorthJPanel(JFrame frame) {
		this.setLayout(new BorderLayout());
		centerNorthOneJPanel = new EightCenterNorthOneJPanel(frame);
		this.add(centerNorthOneJPanel, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(2000, 26));
	}

}