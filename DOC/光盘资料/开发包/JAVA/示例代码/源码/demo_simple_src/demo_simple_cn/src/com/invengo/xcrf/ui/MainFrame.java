package com.invengo.xcrf.ui;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.ErrInfoList;
import invengo.javaapi.handle.IApiExceptionHandle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.i18n.LanguageChangeListener;
import com.invengo.xcrf.core.i18n.LanguageChoice;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.panel.LeftTreeModePanel;
import com.invengo.xcrf.ui.panel.LogPanel;
import com.invengo.xcrf.ui.panel.MainDemoPanel;
import com.invengo.xcrf.ui.panel.MainPanel;
import com.invengo.xcrf.ui.panel.ModeButtonPanel;
import com.invengo.xcrf.ui.panel.TagInfo;

public class MainFrame extends JFrame implements LanguageChangeListener {

	private JPanel contentPane;

	private boolean isShiftStart;

	private static MainFrame mainFrame = new MainFrame();

	public JPanel logPanel;

	public TagInfo tagInfo;

	public static MainPanel mainPanel;

	public ModeButtonPanel ModeButtonPanel;

	public static MainFrame getMainFrame() {
		if (mainFrame == null)
			mainFrame = new MainFrame();
		return mainFrame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// MainFrame a = new MainFrame();

	}

	public class timerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			tagInfo.scanTotal = MainDemoPanel.scanPanel.tagCount;
		}
	}

	/**
	 * Create the frame.
	 */
	private MainFrame() {
		mainFrame = this;

		// 设置图标
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(this.getClass().getResource(
				"image/logo.gif"));
		setIconImage(image);

		// 标题
		setTitle(BaseMessages.getString("MainForm.MainForm"));

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JPanel top = new JPanel();

		this.setBackground(new Color(170, 199, 247));
		contentPane = new JPanel();
		contentPane.setOpaque(false);

		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		top.setPreferredSize(new Dimension(1024, 33));
		contentPane.add(top, BorderLayout.NORTH);
		top.setBackground(new Color(170, 199, 247));
		top.setOpaque(false);

		JPanel leftPanel = new LeftTreeModePanel(this);
		leftPanel.setOpaque(false);
		// leftPanel.setBorder(new LineBorder(Color.CYAN));
		contentPane.add(leftPanel, BorderLayout.WEST);

		mainPanel = new MainPanel(this);
		mainPanel.setOpaque(false);
		// mainPanel.setBorder(new LineBorder(Color.CYAN));
		contentPane.add(mainPanel, BorderLayout.CENTER);

		logPanel = new LogPanel();
		logPanel.setSize(new Dimension(500, 100));
		logPanel.setOpaque(false);

		BaseReader.onApiException.add((IApiExceptionHandle) logPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(logPanel, BorderLayout.CENTER);

		bottomPanel.setPreferredSize(new Dimension(816, 160));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		setSize(new Dimension(1032, 802));
		setMinimumSize(new Dimension(800, 600));
		setVisible(true);

		BaseMessages.addListener(this);

		if (LanguageChoice.getInstance().getDefaultLocale().toString()
				.toLowerCase().equals("zh_cn"))
			ErrInfoList.reset(2052);

		Runnable run = new Runnable() {

			@Override
			public void run() {
				// 使frame获取焦点，以触发监听事件
				requestFocusInWindow();

				KeyAdapter l = new KeyAdapter() {

					@Override
					public void keyPressed(KeyEvent e) {
						if (!isShiftStart)
							if (e.getKeyCode() == 16) {
								isShiftStart = true;
							}
					}

				};
				addKeyListener(l);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				ConfigurationDialog.initialConfigDialog(mainFrame);
				// System.out.println(isShiftStart);
				removeKeyListener(l);
			}

		};
		new Thread(run).start();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (DemoRegistry.hasReadingDemo()) {
					if (MessageDialog.showConfirmDialog(BaseMessages
							.getString("Message.MSG_124"), BaseMessages
							.getString("Message.MSG_21"))) {
						DemoRegistry.forceCloseReader();
						System.exit(0);
					}
				} else {
					System.exit(0);
				}

			}
		});
	}

	public boolean isShiftStart() {
		return isShiftStart;
	}

	@Override
	public void updateResource() {
		setTitle(BaseMessages.getString("MainForm.MainForm"));

		ConfigurationDialog.rebuildConfigDialog(this);
	}

	/**
	 * xusheng 2012.5.8 有读写器连接或断开(节点被选择/取消)时的UI控制
	 */
	public void UIUpdateWhenDemoConnected() {
		boolean flag = false;
		boolean flagReading = false;
		for (Demo demo : DemoRegistry.getAllDemo().values()) {
			if (demo.getNode().isSelected() && demo.getReader().isConnected()) {

				// /*演示模式*/
				// if(((JButton)(mainPanel.panel.rdp.eightCenterNorthJPanel.getCenterNorthOneJPanel().getStopJButton())).isEnabled()){
				// mainPanel.panel.rdp.eightCenterNorthJPanel
				// .getCenterNorthOneJPanel().getReadButton().setEnabled(false);
				// }else{
				// mainPanel.panel.rdp.eightCenterNorthJPanel
				// .getCenterNorthOneJPanel().getReadButton().setEnabled(true);
				// }
				// /*扫描模式*/
				// if(((JButton)(mainPanel.panel.scanPanel.btnStop)).isEnabled()){
				// mainPanel.panel.scanPanel.btnScan.setEnabled(false);
				// }else{
				// mainPanel.panel.scanPanel.btnScan.setEnabled(true);
				// }
				flag = true;

				if (demo.isReading()) {
					flagReading = true;
				}
				break;
			}
		}
		if (flag == false) {// 全部断开了的
			// 演示模式
			((JButton) (mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getStopJButton()))
					.setEnabled(false);
			mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getReadButton()
					.setEnabled(false);
			// 扫描模式
			((JButton) (MainDemoPanel.scanPanel.btnStop)).setEnabled(false);
			MainDemoPanel.scanPanel.btnScan.setEnabled(false);

		}
		if (flag == true && flagReading == false) { // 有连接，但是没有正在读
			// 演示模式
			((JButton) (mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getStopJButton()))
					.setEnabled(false);
			mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getReadButton().setEnabled(true);
			// 扫描模式
			((JButton) (MainDemoPanel.scanPanel.btnStop)).setEnabled(false);
			MainDemoPanel.scanPanel.btnScan.setEnabled(true);

		}
		if (flag == true && flagReading == true) { // 有连接，也有正在读
			// 演示模式
			((JButton) (mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getStopJButton()))
					.setEnabled(true);
			mainPanel.panel.rdp.eightCenterNorthJPanel
					.getCenterNorthOneJPanel().getReadButton()
					.setEnabled(false);
			// 扫描模式
			((JButton) (MainDemoPanel.scanPanel.btnStop)).setEnabled(true);
			MainDemoPanel.scanPanel.btnScan.setEnabled(false);
		}
	}
}
