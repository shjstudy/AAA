package com.invengo.xcrf.ui.panel;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;

public class MainDemoPanel extends JPanel implements LanguageChangeListener {

	private static ReadDataPanel readDataPanel;
	public ReadDataPanel rdp;

	public static ScanPanel scanPanel;
	public CardLayout cardLayout;

	public MainDemoPanel(JFrame main, int top) {
		// super(top);
		rdp = new ReadDataPanel(main);
		readDataPanel = rdp;
		// addTab(BaseMessages.getString("MainForm.pic_Demo"),
		// readDataPanel = rdp);
		/* xusheng 2012.4.20 */
		// addTab(BaseMessages.getString("MainForm.pic_Scan"), new ScanPanel());
		scanPanel = new ScanPanel(main);
		// addTab(BaseMessages.getString("MainForm.pic_Scan"), scanPanel);
		/* xusheng 2012.4.20 */
		// addTab(BaseMessages.getString("MainForm.pic_Test"), new
		// TestModePanel(main));
		// addTab("场景模式", new TestPanel("场景模式"));
		// addTab("等等模式", new TestPanel("等等模式"));
		// setBorder(BorderFactory.createLineBorder(Color.black,2));
		cardLayout = new CardLayout();
		this.setLayout(cardLayout);
		add(rdp, "pic_Demo");
		add(scanPanel, "pic_Scan");

		BaseMessages.addListener(this);
	}

	public static ReadDataPanel getReadDataPanel() {
		return readDataPanel;
	}

	public static ScanPanel getScanPanel() {
		return scanPanel;
	}

	@Override
	public void updateResource() {
		// this.setTitleAt(0, BaseMessages.getString("MainForm.pic_Demo"));
		// this.setTitleAt(1, BaseMessages.getString("MainForm.pic_Scan"));
		// this.setTitleAt(2, BaseMessages.getString("MainForm.pic_Test"));
	}

	public void scanPanelShow() {

		cardLayout.show(this, "pic_Scan");
	}

	public void readDataPanelShow() {

		cardLayout.show(this, "pic_Demo");
	}

	public void testModePanelShow() {

		cardLayout.show(this, "pic_Test");
	}
}
