package com.invengo.xcrf.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.ui.MainFrame;

public class AboutDialog extends JDialog implements LanguageChangeListener{

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setBounds(100, 100, 391, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		// 设置图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(MainFrame.class.getResource(
		"image/logo.gif"));
		setIconImage(image);
		
		// 标题
		setTitle(BaseMessages.getString("menu.about"));
		
		
		JLabel label = new JLabel(new ImageIcon(MainFrame.class
				.getResource("image/logo.gif")));
		label.setBounds(26, 36, 54, 48);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel(BaseMessages.getString("menu.about.softname"));
		label_1.setBounds(115, 44, 254, 15);
		contentPanel.add(label_1);
		
		JLabel label_2 = new JLabel(BaseMessages.getString("menu.about.copyright"));//WidgetFactory.getInstance().buildJLabel("", "javax.swing.JLabel", "menu.about.ver");
		label_2.setBounds(115, 112, 142, 15);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel(BaseMessages.getString("menu.about.company"));//WidgetFactory.getInstance().buildJLabel("", "javax.swing.JLabel", "menu.about.copyright");
		label_3.setBounds(68, 138, 283, 15);
		contentPanel.add(label_3);
		
		JLabel label_4 = new JLabel(BaseMessages.getString("menu.about.ver"));//WidgetFactory.getInstance().buildJLabel("", "javax.swing.JLabel", "menu.about.company");
		label_4.setBounds(115, 69, 102, 15);
		contentPanel.add(label_4);
		
		
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		
		BaseMessages.addListener(this);
	}

	@Override
	public void updateResource() {
		setTitle(BaseMessages.getString("menu.about"));
	}
}
