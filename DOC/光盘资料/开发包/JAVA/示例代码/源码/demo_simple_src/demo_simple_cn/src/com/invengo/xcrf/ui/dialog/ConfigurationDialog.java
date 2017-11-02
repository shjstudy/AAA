package com.invengo.xcrf.ui.dialog;

import invengo.javaapi.protocol.IRP1.SysQuery_800;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.config.NavigatorPanel;
import com.invengo.xcrf.ui.panel.TestPanel;
import com.invengo.xcrf.ui.panel.readerConfig.ConfigPanel;

public class ConfigurationDialog extends JDialog {

	public static boolean isOperatting = false; //是否有操作正在进行
	
	
	
	private static final long serialVersionUID = -6048036622076116882L;
	private static ConfigurationDialog configDialog;
	private JFrame frame;
	NavigatorPanel panel_1;

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblIrp;
	private JLabel label_5;

	private JPanel panel_2;

	private JPanel defaultPanel = new TestPanel("初始化页面");

	private JPanel showingPanel;
	private JLabel selectedLabel;
	private Demo showingdemo;
	private Box box;
	private JLabel lblBaseVersion;

	public static ConfigurationDialog getConfigDialog() {
		// if (configDialog == null)
		configDialog = new ConfigurationDialog(null);
		configDialog.initPanel();
		return configDialog;
	}

	public static void initialConfigDialog(JFrame frame) {
		if (configDialog != null)
			return;
		configDialog = new ConfigurationDialog(frame);
	}

	public static void rebuildConfigDialog(JFrame frame) {
		configDialog = new ConfigurationDialog(frame);
	}

	public static ConfigurationDialog getInstance() {
		return configDialog;
	}

	/**
	 * Create the dialog.
	 */
	private ConfigurationDialog(JFrame frame) {

		/*xusheng 2012.4.23*/
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		/*xusheng 2012.4.23*/
		this.frame = frame;
		setBounds(100, 100, 800, 626);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(800, 600));
		this.setResizable(false);

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, BaseMessages
					.getString("ReaderConfigForm.groupBox13"),
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(800, 100));
			panel.setLayout(null);
			{
				JLabel readName = new JLabel(BaseMessages
						.getString("ReaderConfigForm.label8"));
				readName.setBounds(31, 28, 101, 15);
				panel.add(readName);
			}

			textField = new JTextField();
			textField.setBounds(142, 25, 125, 21);
			panel.add(textField);
			textField.setColumns(10);
			textField.setEnabled(false);

			JLabel label = new JLabel(BaseMessages
					.getString("ReaderConfigForm.label46"));
			label.setBounds(274, 28, 73, 15);
			panel.add(label);

			textField_1 = new JTextField();
			textField_1.setBounds(357, 25, 112, 21);
			panel.add(textField_1);
			textField_1.setColumns(10);
			textField_1.setEnabled(false);

			JLabel label_1 = new JLabel(BaseMessages
					.getString("ReaderConfigForm.label44"));
			label_1.setBounds(502, 28, 112, 15);
			panel.add(label_1);

			textField_2 = new JTextField();
			textField_2.setBounds(632, 25, 99, 21);
			panel.add(textField_2);
			textField_2.setColumns(10);
			textField_2.setEnabled(false);

			JLabel label_2 = new JLabel(BaseMessages
					.getString("ReaderConfigForm.label49"));
			label_2.setBounds(31, 67, 153, 15);
			panel.add(label_2);

			lblIrp = new JLabel("IRP1");
			lblIrp.setBounds(211, 67, 54, 15);
			panel.add(lblIrp);

			JLabel label_4 = new JLabel(BaseMessages
					.getString("ReaderConfigForm.label45"));
			label_4.setBounds(521, 67, 154, 15);
			panel.add(label_4);

			label_5 = new JLabel("....");
			label_5.setBounds(685, 67, 112, 15);
			panel.add(label_5);

			JLabel label_3 = new JLabel(BaseMessages
					.getString("ReaderConfigForm.label1"));
			label_3.setBounds(259, 67, 153, 15);
			panel.add(label_3);

			lblBaseVersion = new JLabel();
			lblBaseVersion.setBounds(422, 67, 89, 15);
			panel.add(lblBaseVersion);

		}


		setModal(true);
		// initPanel();

		
		/*xusheng 2012.4.23 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//if (DemoRegistry.getCurrentDemo().getIsReading()) {
				isoping();
			}
		});
		/*xusheng 2012.4.23*/
	}

	/* xusheng 2012.4.23
	 * 关闭对话框检查是否有操作正在进行中
	 */
	public void isoping(){
		if(ConfigurationDialog.isOperatting || DemoRegistry.getCurrentDemo().getIsReading()){
			MessageDialog.showInfoMessage(BaseMessages.getString("Message.MSG_246"));
			return;
		}
		else{
			//ConfigurationDialog.this.setVisible(false);
			this.setVisible(false);
		}
	}
	/*xusheng 2012.4.23*/
	public void initPanel() {
		Demo currentdemo = DemoRegistry.getCurrentDemo();
		if (currentdemo != null) {
			UserConfig_IRP1 userconfig = (UserConfig_IRP1) currentdemo
					.getConfig();
			textField.setText(userconfig.getReaderName());
			textField_1.setText(userconfig.getReaderGroup());
			lblIrp.setText(userconfig.getProtocol().toString());
			label_5.setText(userconfig.getConnType());
			SysQuery_800 msg = new SysQuery_800((byte) 0x21);
			String modelNO = currentdemo.getConfig().getModelNo();
			if (currentdemo.getReader().send(msg, 1000)) {
				
				//如果当前型号为1.空 或者2.是通过命令获取的，则更新为读取到的型号
				if(modelNO==null || Common.isGetModelNOByCommand(modelNO))
				{
					modelNO = new String(msg.getReceivedMessage().getQueryData());
					currentdemo.getConfig().setModelNo(modelNO);
				}
				textField_2.setText(modelNO);
			}else{
				if(modelNO==null || Common.isGetModelNOByCommand(modelNO))
				{
					modelNO = "unknown";
				}
				textField_2.setText(modelNO);
			}
			msg = new SysQuery_800((byte) 0x23);
			if (currentdemo.getReader().send(msg, 1000)) {
				// 删除末尾的字节0
				byte[] buffer = msg.getReceivedMessage().getQueryData();
				int i = buffer.length - 1;
				for (; i >= 0; i--) {
					if (buffer[i] != 0) {
						break;
					}
				}

				lblBaseVersion.setText("U" + new String(buffer, 0, i + 1));
			}
		}
		{

			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));

			// TODO : 修改
			panel_1 = new NavigatorPanel(false);
			panel_1.setPreferredSize(new Dimension(144, 580));
			panel.add(panel_1, BorderLayout.WEST);

			defaultPanel = panel_1.getDefaultPanel();

			panel_2 = new JPanel();
			panel_2.setLayout(new BorderLayout());
			box = Box.createVerticalBox();
			box.add(defaultPanel);

			for (Entry<JLabel, JPanel> entry : panel_1.getButtonMapings()
					.entrySet()) {
				final JLabel jb = entry.getKey();
				final JPanel jp = entry.getValue();

				jb.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (showingPanel != jp) {
							if (showingPanel != null) {
								showingPanel.setVisible(false);
							}
							if (jp instanceof ConfigPanel) {
								((ConfigPanel) jp).fillConfigData();
							}
							jp.setVisible(true);
							showingPanel = jp;
							panel_2.updateUI();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

				});
				jb.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (selectedLabel != jb) {
							if (selectedLabel != null) {
								selectedLabel.getParent().setBackground(
										panel_2.getBackground());
							}
							selectedLabel = jb;
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						jb.getParent().setBackground(Color.LIGHT_GRAY);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						if (selectedLabel != jb)
							jb.getParent().setBackground(
									panel_2.getBackground());
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

				});

				box.add(jp);

			}
			panel_2.add(box, BorderLayout.CENTER);
			panel.add(panel_2, BorderLayout.CENTER);

		}
		
		
		
		

		if (showingdemo == null) {
			showingdemo = currentdemo;

			showingPanel = defaultPanel;
			showingPanel.setVisible(true);
			panel_2.updateUI();
		}// 改选了读写器
		else if (showingdemo != null && showingdemo != currentdemo) {
			showingdemo = currentdemo;

			showingPanel.setVisible(false);
			showingPanel = defaultPanel;
			showingPanel.setVisible(true);
			if (selectedLabel != null)
				selectedLabel.getParent().setBackground(Color.white);
			selectedLabel = null;
			panel_2.updateUI();
		}

		configDialog.setLocationRelativeTo(frame);
	}

	public NavigatorPanel getNavigatorPanel() {
		return panel_1;
	}

	public void updateResource() {
		configDialog = new ConfigurationDialog(frame);
	}
	
	
	private Map<JLabel,MouseListener[]> listenersMap = new HashMap<JLabel,MouseListener[]>();
	
	/**
	 * 锁定导航面板
	 */
	public void lockNavigatorPanel()
	{
		if(panel_1 != null)
		for(JLabel label : panel_1.getButtonMapings().keySet())
		{
			if(label != selectedLabel)
			{
				label.setEnabled(false);
				
				if(label.getMouseListeners()!=null){
					listenersMap.put(label, label.getMouseListeners());
					for(MouseListener ml : label.getMouseListeners())
					{
						label.removeMouseListener(ml);
					}
				}
			}
			
		}
	}
	
	/**
	 * 解锁导航面板
	 */
	public void unlockNavigatorPanel()
	{
		if(panel_1 != null)
		for(JLabel label : panel_1.getButtonMapings().keySet())
		{
			if(label != selectedLabel)
			{
				label.setEnabled(true);
				
				MouseListener[] listeners = listenersMap.remove(label);
				if(listeners != null)
					for(MouseListener ml : listeners)
					{
						label.addMouseListener(ml);
					}
			}
		}
	}
	
}
