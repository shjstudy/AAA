package com.invengo.xcrf.ui.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import com.invengo.xcrf.core.util.DemoUtil;


public class WaitBar extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WaitBar() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(DemoUtil.getResource("image/logo.gif"));
		setIconImage(image);
		// 设置大小
		setSize(200, 100);
		// 模态对话框
		setModal(true);
		// 设置居中
		setLocationRelativeTo(null);
		// 增加关闭处理事件
		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void WindowClosing(WindowEvent e) {
				dispose();
			}
		});
        Container pane = getContentPane();
        pane.setLayout(null);
        Insets insets = pane.getInsets();

        JProgressBar bar = new JProgressBar(0,100);//创建进度条
        bar.setIndeterminate(true);//值为 true 意味着不确定，而值为 false 则意味着普通或者确定。 

        Dimension dim = bar.getPreferredSize();
        int x = insets.left + 20;
        int y = insets.top + 20;
        dim.width += 100;
        bar.setBounds(x,y,dim.width,dim.height);
        pane.add(bar);		
		
	}
}
