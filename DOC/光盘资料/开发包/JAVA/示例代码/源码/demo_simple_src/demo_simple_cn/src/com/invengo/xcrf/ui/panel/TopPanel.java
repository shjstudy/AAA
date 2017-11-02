/**
 * 状态栏
 */
package com.invengo.xcrf.ui.panel;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.invengo.xcrf.ui.MainFrame;


/**
 * 底部状态显示
 * @author zxl672
 */
public class TopPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final ImageIcon backgroundImg = new ImageIcon(MainFrame.class
			.getResource("image/OpBtn1.png"));
	
	public TopPanel() {

		// 显示状态栏
		JLabel statusLabel = new JLabel(" ");
		add(statusLabel);

		// 设置最合适大小、外框
		this.setPreferredSize(new Dimension(10, 76));
		
		

				
	
	}
//	public void paintComponent(Graphics g) {
//		int width = this.getWidth();
//		int height = this.getHeight();
//			g
//			.drawImage(backgroundImg.getImage(), 0, 0, width,
//					height, this);
//				super.paintComponent(g);
//	}

}