package com.invengo.xcrf.ui.dialog;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.core.util.DemoUtil;
import com.invengo.xcrf.ui.WidgetFactory;

/**
 * 对话框形式的基类
 * 
 * @author zxl672
 * 
 */
public abstract class InvengoJDialog extends JDialog implements LanguageChangeListener{
	// 关闭按钮
	protected JButton closeButton = (JButton) WidgetFactory
	.getInstance().buildOrdWidget("closeButton",
			"javax.swing.JButton", "closeButton");
	protected String title;

	/**
	 * 构造函数
	 * 
	 * @param with
	 * @param height
	 */
	public InvengoJDialog(int with, int height,String title) {
		// 设置图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(DemoUtil.getResource("image/logo.gif"));
		setIconImage(image);
		// 设置大小
		setSize(with, height);
		setMinimumSize(new Dimension(with, height));
		setMaximumSize(new Dimension(with, height));
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
		this.title = title;
		setTitle(BaseMessages.getString(title));
		BaseMessages.addListener(this);

		// 关闭按钮处理事件
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	@Override
	public void updateResource() {
		this.setTitle(BaseMessages.getString(title));
	}

}
