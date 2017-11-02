package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.WidgetFactory;

public class ScanPanel extends JPanel implements
		IMessageNotificationReceivedHandle{

	private JPanel infoPanel2;

	private Timer timer;

	public TagInfo tagInfo;

	private Map<String, UCCtrl_TagInfo> panelMaps = Collections
			.synchronizedMap(new LinkedHashMap<String, UCCtrl_TagInfo>());

	private static final String IMAGEPATH = "/com/invengo/xcrf/ui/image/TID/";

	private static final Map<String, String> imageMap = new HashMap<String, String>();

	private boolean isScan = false;

	public int tagCount = 0;

	// JLabel lblInfo = null;

	private long startReadTime;

	// 表盘数据更新
	// 标签总数 tagCount

	// 读取总数
	public int totalCount = 0;
	// 即时速度
	public int immediateSpeed;
	// 平均速度
	public int rate;
	// 扫描时间
	public String scanTime;
	// 上一秒读的总数
	public int lastTotal;

	private MainFrame frame;
	static {
		Document doc;
		try {
			doc = new SAXBuilder().build(ScanPanel.class
					.getResourceAsStream(IMAGEPATH + "MDID.xml"));
			List<Element> elements = XPath.newInstance("//Company")
					.selectNodes(doc);
			for (Element e : elements) {
				String[] ss = e.getText().split(",");
				for (String s : ss) {
					imageMap.put(s, e.getAttributeValue("Name"));
				}
			}
		} catch (JDOMException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {

		}

	}

	public AbstractButton btnScan;

	public AbstractButton btnStop;

	/**
	 * Create the panel.
	 */
	public ScanPanel(JFrame main) {
		frame = (MainFrame) main;
		setLayout(new BorderLayout(0, 0));

		WidgetFactory widget = WidgetFactory.getInstance();

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(2000, 25));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		// JLabel label = new JLabel("\u6807\u7B7E\u4FE1\u606F");
		// panel.add(label, BorderLayout.WEST);

		// btnScan = widget.buildOrdWidget("ScanModeCtrl.btn_Scan");
		JPanel scanAndStop = new JPanel();
		scanAndStop.setBackground(Color.white);
		scanAndStop.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));

		btnScan = widget.buildOrdWidget("DemoMode.btnReadTag");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scan();
			}
		});
		btnScan.setPreferredSize(new Dimension(87, 23));
		scanAndStop.add(btnScan);
		btnScan.setEnabled(false);

		// btnStop = widget.buildOrdWidget("ScanModeCtrl.btn_StopScan");
		btnStop = widget.buildOrdWidget("DemoMode.btnStop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isScan = false;
				stop();
			}
		});
		btnStop.setPreferredSize(new Dimension(87, 23));
		scanAndStop.add(btnStop);

		scanAndStop.setOpaque(false);
		panel.add(scanAndStop, BorderLayout.WEST);

		JPanel whitePanel = new JPanel();

		whitePanel.setOpaque(false);
		panel.add(whitePanel, BorderLayout.CENTER);

		/* xusheng 2012.5.9 清空 */

		JPanel clear = new JPanel();
		clear.setOpaque(false);
		clear.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		AbstractButton btnClear = widget.buildOrdWidget("ScanModeCtrl.btn_Clean");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnClear.setPreferredSize(new Dimension(87, 23));
		clear.add(btnClear);

		panel.add(clear, BorderLayout.EAST);
		/* xusheng 2012.5.9 清空 */

		// lblInfo = widget.buildJLabel("ScanModeCtrl.label39");// new
		// // JLabel("\u6807\u7B7E\u603B\u6570\uFF1A");
		// add(lblInfo, BorderLayout.SOUTH);
		infoPanel2 = new JPanel();
		infoPanel2.setBackground(Color.white);
		// infoPanel2.setAutoscrolls(true);
		// infoPanel2
		// .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// final JScrollPane s = new JScrollPane(infoPanel2);
		// add(s, BorderLayout.CENTER);
		// s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		BoxLayout box = new BoxLayout(infoPanel2, BoxLayout.Y_AXIS);
		infoPanel2.setLayout(box);
		final JScrollPane s = new JScrollPane(infoPanel2);
		s.getViewport().setBackground(Color.white);
		add(s, BorderLayout.CENTER);
		this.setBackground(Color.white);
		this.btnStop.setEnabled(false);

		// 用于更新表盘的timer
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// total.setText(String.valueOf(tagCount));

				// rate.setText(String.valueOf(Math
				// .round(totalCount * 1000 / time)));

				/* xusheng 2012 4 23 */
				// 更新主界面的表盘数据显示
				TagInfoUpdate(false);
				/* xusheng 2012 4 23 */

			}
		});

		Map<String, Demo> demos = DemoRegistry.getCurrentDemos();
		for (Demo demo : demos.values()) {
			Reader reader = demo.getReader();
			if (!reader.onMessageNotificationReceived.contains(this)) {
				reader.onMessageNotificationReceived.add(this);
			}
		}
		
	}

	public void addReader(Demo demo){
		if (!demo.getReader().onMessageNotificationReceived.contains(this))
			demo.getReader().onMessageNotificationReceived.add(this);
	}
	
	public void removeReader(Demo demo) {
		demo.getReader().onMessageNotificationReceived.remove(this);
	}
	
	private void TagInfoUpdate(boolean isReset) {

		if (isReset) {
			if (this.tagInfo != null) {
				tagInfo.avgSpeed = 0;
				tagInfo.scanTotal = 0;
				tagInfo.immediateSpeed = 0;

				tagInfo.intAvgSpeed.setText("0");
				tagInfo.intCurSpeed.setText("0");
				tagInfo.intTagSum.setText("0");
				tagInfo.intScanum.setText("0");
				tagInfo.intScanTime.setText("00.00.00");
				tagInfo.vistaCPUInfo.cpu = 0;
				tagInfo.vistaCPUInfo.mem = 0;
			}
			return;
		}
		// 即时速度计算
		this.immediateSpeed = totalCount - lastTotal;
		lastTotal = totalCount;

		// 扫描时间
		long time = System.currentTimeMillis() - startReadTime;
		SimpleDateFormat sdf = new SimpleDateFormat(".mm.ss");
		Timestamp t = new Timestamp(time);
		int hours = (int) (time / 3600000);
		if (hours < 10)
			this.tagInfo.intScanTime.setText("0" + hours + sdf.format(t));
		// timerl = "0" + hours + sdf.format(t);
		else
			this.tagInfo.intScanTime.setText(hours + sdf.format(t));
		// timerl = hours + sdf.format(t);

		// 平均速度计算
		rate = (int) (totalCount * 1000 / time);

		if (this.tagInfo != null) {
			tagInfo.avgSpeed = rate;
			tagInfo.scanTotal = tagCount;
			tagInfo.immediateSpeed = immediateSpeed;

			tagInfo.intAvgSpeed.setText(((Integer) rate).toString());
			tagInfo.intCurSpeed.setText(((Integer) immediateSpeed).toString());
			tagInfo.intTagSum.setText(((Integer) tagCount).toString());
			tagInfo.vistaCPUInfo.cpu = rate;
			tagInfo.vistaCPUInfo.mem = immediateSpeed;
		}
	}

	private int i = 0;

	/**
	 * 扫描
	 */
	private void scan() {
		reset();// 重置
		panelMaps.clear();
		isScan = true;
		
		timer.start();
		this.btnScan.setEnabled(false);
		this.btnStop.setEnabled(true);

		Map<String, Demo> demos = DemoRegistry.getCurrentDemos();
		for (Demo demo : demos.values()) {
			demo.readTag();
		}
		
		/* xusheng 2012.5.10 */
		(frame).ModeButtonPanel.setButtonDisable((frame).ModeButtonPanel.lblReadDataMode, false);
		(frame).ModeButtonPanel.lblReadDataMode
				.removeMouseListener((frame).ModeButtonPanel.ReadDate_MouseListener);
		(frame).ModeButtonPanel.setButtonDisable((frame).ModeButtonPanel.lblTestMode, false);
		(frame).ModeButtonPanel.lblTestMode
				.removeMouseListener((frame).ModeButtonPanel.Test_MouseListener);
		/* xusheng 2012.5.10 */
	}

	private void stop() {
		Map<String, Demo> demos = DemoRegistry.getCurrentDemos();
		for (Demo demo : demos.values()) {
//			demo.getReader().onMessageNotificationReceived.remove(this);
			demo.stopRead();
		}

		timer.stop();

		panelMaps.clear();
		this.btnStop.setEnabled(false);
		frame.UIUpdateWhenDemoConnected();

		/* xusheng 2012.5.10 */
		(frame).ModeButtonPanel.setButtonDisable((frame).ModeButtonPanel.lblReadDataMode, true);
		(frame).ModeButtonPanel.lblReadDataMode
				.addMouseListener((frame).ModeButtonPanel.ReadDate_MouseListener);
		(frame).ModeButtonPanel.setButtonDisable((frame).ModeButtonPanel.lblTestMode, true);
		(frame).ModeButtonPanel.lblTestMode
				.addMouseListener((frame).ModeButtonPanel.Test_MouseListener);
		/* xusheng 2012.5.10 */
	}

	private void updatePanels() {
		i = 0;
		tagCount = 0;
		infoPanel2.removeAll();

		for (UCCtrl_TagInfo info : panelMaps.values()) {
			// info.setBounds(5, 5 + 100 * i, 374, 100);

			info.setPreferredSize(new Dimension(374, 100));
			infoPanel2.add(info);

			i++;
		}
		/** 填充PANEL* */
		{
			JPanel jp1 = new JPanel();
			jp1.setPreferredSize(new Dimension(374, 100));
			JPanel jp2 = new JPanel();
			jp2.setPreferredSize(new Dimension(374, 100));
			JPanel jp3 = new JPanel();
			jp3.setPreferredSize(new Dimension(374, 100));
			JPanel jp4 = new JPanel();
			jp4.setPreferredSize(new Dimension(374, 100));
			infoPanel2.add(jp3);
			infoPanel2.add(jp4);
		}
		infoPanel2.repaint(10);
		tagCount = panelMaps.size();

	}

	private void reset() {
		i = 0;
		panelMaps.clear();
		tagCount = 0;
		infoPanel2.removeAll();
		infoPanel2.updateUI();

		TagInfoUpdate(true);

		this.lastTotal = 0;
		rate = 0;

		startReadTime = System.currentTimeMillis();
		totalCount = 0;
		tagCount = 0;
	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,
			IMessageNotification msg) {}
}
