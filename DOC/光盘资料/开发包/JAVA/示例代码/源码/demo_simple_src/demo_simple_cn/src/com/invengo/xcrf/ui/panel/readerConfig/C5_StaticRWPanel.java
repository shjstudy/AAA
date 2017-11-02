package com.invengo.xcrf.ui.panel.readerConfig;

import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.panel.AbstractReaderDataTable;
import com.invengo.xcrf.ui.panel.MainDemoPanel;

/**
 * 
 * @author yxx981
 * 
 */
public class C5_StaticRWPanel extends AbstractReaderDataTable {

	private static final long serialVersionUID = 8857307708454997528L;

	private int times; // 读卡次数

	private int timeout; // 时间

	private boolean isReading = false;

	private JButton btnStop;

	private JButton btnReadTag;

	private long startTime;

	private JSpinner spnReadTime;
	private JSpinner spnTagCount;

	private JCheckBox chkReadTime;

	private JCheckBox chkTagCount;
	private JLabel lblSucessRate;
	private JLabel lblReadCount;
	private JLabel lblRate;
	private JLabel lblReadTime;

	/**
	 * 重写父类的createTable，使用属性文件中的centerTable.dynamicreadwrite作为表格的column
	 */
	@Override
	protected void createTable() {
		centerTable = WidgetFactory.getInstance().buildJTable(
				"centerTestTable", "centerTable.staticread");
		centerTable.setRowSelectionAllowed(true);
		centerTable.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Create the panel.
	 */
	public C5_StaticRWPanel() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane(centerTable);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel, BorderLayout.NORTH);

		chkReadTime = new JCheckBox(BaseMessages
				.getString("C5_StaticRW.radioButton1"));
		chkReadTime.setSelected(true);
		chkReadTime.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(chkReadTime);

		spnReadTime = new JSpinner();
		spnReadTime.setModel(new SpinnerNumberModel(10, 1, 65536, 1));
		spnReadTime.setPreferredSize(new Dimension(60, 20));
		panel.add(spnReadTime);

		chkTagCount = new JCheckBox(BaseMessages
				.getString("C5_StaticRW.radioButton2"));
		panel.add(chkTagCount);

		spnTagCount = new JSpinner();
		spnTagCount.setModel(new SpinnerNumberModel(1, 1, 10000, 1));
		spnTagCount.setPreferredSize(new Dimension(60, 20));
		panel.add(spnTagCount);

		JLabel label = new JLabel(BaseMessages.getString("C5_StaticRW.label2"));
		label.setForeground(Color.RED);
		panel.add(label);

		btnReadTag = new JButton(BaseMessages.getString("C5_StaticRW.btn_Read"));
		btnReadTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readTag();
			}
		});
		panel.add(btnReadTag);

		btnStop = new JButton(BaseMessages.getString("C5_StaticRW.btn_Stop"));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopRead();
			}
		});
		panel.add(btnStop);
		JPanel footer = new JPanel();

		add(footer, BorderLayout.SOUTH);
		footer.setLayout(new GridLayout(0, 4, 0, 0));
		lblReadTime = new JLabel(BaseMessages.getString("C5_StaticRW.readTime"));
		lblReadTime.setPreferredSize(new Dimension(200, 20));
		footer.add(lblReadTime);

		lblRate = new JLabel(BaseMessages.getString("C5_StaticRW.raString"));
		footer.add(lblRate);

		lblReadCount = new JLabel(BaseMessages
				.getString("C5_StaticRW.totalCount"));
		footer.add(lblReadCount);

		lblSucessRate = new JLabel(BaseMessages
				.getString("C5_StaticRW.rateString"));
		lblSucessRate.setAlignmentX(30.0f);
		footer.add(lblSucessRate);
	}

	@Override
	public void messageNotificationReceivedHandle(BaseReader reader,
			IMessageNotification msg) {
		super.messageNotificationReceivedHandle(reader, msg);

		display();
		if (chkTagCount.isSelected() && times <= count) {
			isReading = false;
			stopRead();
			count = 0;
		}
	}

	private void readTag() {
		/* xusheng 2012 4.23 */
		ConfigurationDialog.isOperatting = true;
		/* xusheng 2012 4.23 */
		isReading = true;
		new Thread(new ConctrlThread(), "listenerThread").start();
		startTime = System.currentTimeMillis();
		times = Integer.parseInt(spnTagCount.getValue().toString());
		timeout = Integer.parseInt(spnReadTime.getValue().toString());
		super.addReader();
		MainDemoPanel.getReadDataPanel().setRead(false);
		currentDemo.readTag();
		btnReadTag.setEnabled(false);
		btnStop.setEnabled(true);
		DefaultTableModel model = (DefaultTableModel) centerTable.getModel();
		model.setRowCount(0);
		// 监控线程启动

	}

	private void stopRead() {
		isReading = false;
		this.currentDemo.stopRead();
		btnStop.setEnabled(false);
		btnReadTag.setEnabled(true);
		this.count = 0;
		totalTime = 0;
		super.removeReader();

		/* xusheng 2012 4.23 */
		ConfigurationDialog.isOperatting = false;
		/* xusheng 2012 4.23 */
	}

	class ConctrlThread implements Runnable {

		public void run() {
			while (!volidateStop()) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopRead();
			isReading = false;
		}

		private boolean volidateStop() {
			totalTime = System.currentTimeMillis() - startTime;
			lblReadTime.setText(BaseMessages.getString("C5_StaticRW.readTime")
					+ totalTime / 1000.0);
			if (chkReadTime.isSelected()) {
				if (totalTime / 1000 >= timeout) {
					return true;
				}
			}
			return !isReading;
		}

	}

	private synchronized void display() {
		double rate = 100.00;
		if (chkTagCount.isSelected()) {
			rate = (double) count / times * 100;
		}
		double ra = totalCount / (totalTime / 1000.00);
		DecimalFormat df = new DecimalFormat("#.##");
		String raString = df.format(ra);
		String rateString = df.format(rate);

		lblSucessRate.setText(BaseMessages.getString("C5_StaticRW.rateString")
				+ rateString);
		lblReadCount.setText(BaseMessages.getString("C5_StaticRW.totalCount")
				+ totalCount);
		lblRate.setText(BaseMessages.getString("C5_StaticRW.raString")
				+ raString);

		// totalCount = 0;
	}

	@Override
	protected synchronized boolean isAddRow() {
		if (chkTagCount.isSelected()) {
			return times >= count;
		} else {
			return true;
		}
	}

}
