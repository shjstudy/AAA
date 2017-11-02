package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.invengo.xcrf.core.Common;
import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.WidgetFactory;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.dialog.TagAccessDialog;

/**
 * @author
 */
public class EightCenterNorthOneJPanel extends JPanel  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2960698036020365773L;

	MainFrame frame;

	private AbstractButton stopJButton = null;

	private AbstractButton readButton;

	private AbstractButton generalWriteButton;

	private AbstractButton tagAccessJButton;

	private AbstractButton clearJButton;

	EightCenterNorthOneJPanel panel;
	
	private AbstractButton showComlumn;

	private JPopupMenu showClomlumnClickMenu;
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(EightCenterNorthOneJPanel.class);

	private AbstractButton btnQuery;

	private volatile JLabel lblTagAlarm = null;
	
	private JPanel panel2;

	public JLabel getLblTagAlarm() {
		return lblTagAlarm;
	}

	public EightCenterNorthOneJPanel(final JFrame frame) {
		this.panel = this;
		this.frame = (MainFrame)frame;
		this.setLayout(new BorderLayout());
		JPanel theBar = buildToolBar();
		add(theBar);
		this.setPreferredSize(new Dimension(2000, 55));
	}

	public JPanel buildToolBar() {
		
		setBackground(Color.white);
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new BorderLayout());

		WidgetFactory widget = WidgetFactory.getInstance();

		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBackground(Color.white);
		readButton = widget.buildOrdWidget("DemoMode.btnReadTag");// new
		// JButton("扫描");
		// readButton.setPreferredSize(new Dimension(87,23));
		readButton.setBounds(340, 1, 87, 23);
		panel1.add(readButton);
		readButton.setEnabled(false);

		stopJButton = widget.buildOrdWidget("DemoMode.btnStop");// new
		// JButton("停止");
		stopJButton.setBounds(435, 1, 87, 23);
		panel1.add(stopJButton);
		stopJButton.setEnabled(false);
		
		tagAccessJButton = widget.buildOrdWidget("DemoMode.btnTagAccess");// new
		// JButton("标签操作");
		tagAccessJButton.setBounds(530, 1, 120, 23);
		panel1.add(tagAccessJButton);
		tagAccessJButton.setEnabled(false);

		btnQuery = widget.buildOrdWidget("DemoMode.button1");
		// JButton("数据统计");
		btnQuery.setBounds(310, 1, 87, 23);
		//panel1.add(btnQuery);
		panel1.setPreferredSize(new Dimension(450, 25));
		toolPanel.add(panel1, BorderLayout.CENTER);
		btnQuery.setEnabled(false);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		lblTagAlarm = new JLabel(BaseMessages
				.getString("DemoMode.lbl_TagAlarm"));
		lblTagAlarm.setForeground(Color.RED);
		lblTagAlarm.setVisible(false);
		lblTagAlarm.setPreferredSize(new Dimension(100, 20));
		centerPanel.setBounds(0,0,100,23);
		centerPanel.add(lblTagAlarm);

		panel1.add(centerPanel);

	    panel2 = new JPanel();
	    panel2.setBackground(Color.white);
		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(220, 25));
		clearJButton = widget.buildOrdWidget("DemoMode.btnClear");// new
		// JButton("清空数据");

		//显示列
		showComlumn = widget.buildOrdWidget("DemoMode.btnList");
		showComlumn.setBounds(115, 1, 100, 23);
		//panel2.add(showComlumn);
		clearJButton.setBounds(656, 1, 100, 23);
		panel1.add(clearJButton);

		//toolPanel.add(panel2, BorderLayout.EAST);
		
		
		addActionListeners();
		
		
		
		
		return toolPanel;
	}

	private void addActionListeners() {
		clearJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel model = (DefaultTableModel) MainDemoPanel
						.getReadDataPanel().centerTable.getModel();
				model.setRowCount(0);
				MainDemoPanel.getReadDataPanel().cleardata();
				EightCenterNorthOneJPanel.this.btnQuery.setEnabled(false);
				EightCenterNorthOneJPanel.this.tagAccessJButton.setEnabled(false);
			}
		});

		readButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Map<String, Demo> demos = DemoRegistry.getCurrentDemos();
				if (demos.size() > 0) {
					MainDemoPanel.getReadDataPanel().setRead(true);
					MainDemoPanel.getReadDataPanel().startRead();
					/*xusheng*/
					readButton.setEnabled(false);
					stopJButton.setEnabled(true);
					/*xusheng*/
					for (Demo demo : demos.values()) {
						if(demo.getNode().isSelected()){
							MainDemoPanel.getReadDataPanel().addReader(demo);// 注册调用数据
							demo.readTag();
						}
					}
					
				}

			}
		});

		stopJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, Demo> demos = DemoRegistry.getCurrentDemos();
				if (demos.size() > 0) {
					MainDemoPanel.getReadDataPanel().stopRead();
					
					for (Demo demo : demos.values()) {
//						MainDemoPanel.getReadDataPanel().removeReader(demo);
						MainDemoPanel.getReadDataPanel().setRead(false);
						demo.stopRead();
					}
					
					
					
				}
				/*xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新*/
				(frame).UIUpdateWhenDemoConnected();
				/*xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新*/
			}
		});

		tagAccessJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRowIndexs = MainDemoPanel.getReadDataPanel().centerTable
						.getSelectedRows();
		
				
/*xusheng 2012.5.9 如果选中的读写器已断开，MSG提示  start*/
					Map<String,String> disconnectedReaderList = new HashMap<String,String>();
					
					for(int selectedIndex = 0 ; selectedIndex < selectedRowIndexs.length ; selectedIndex++ ){
					//for (int selectedIndex : selectedRowIndexs){
						String readerName = (String) MainDemoPanel.getReadDataPanel().centerTable
							.getValueAt(selectedRowIndexs[selectedIndex], 0);
						Demo tempDemo = DemoRegistry.getDemoByName(readerName);
						if(!tempDemo.getReader().isConnected()){
							disconnectedReaderList.put(readerName,readerName);
							//消取选择行
							selectedRowIndexs[selectedIndex] = -1;
						}
					}
					//重新选择行
					int count=0;
					MainDemoPanel.getReadDataPanel().centerTable.clearSelection();
					for(int i = 0 ; i < selectedRowIndexs.length ; i++ ){
						if(selectedRowIndexs[i] != -1 ){
							count++;
							MainDemoPanel.getReadDataPanel().centerTable.addRowSelectionInterval(selectedRowIndexs[i], selectedRowIndexs[i]);
						}
					}
					
					
					
					//如果选中的行中有读写器已断开的
					if(disconnectedReaderList.size()>0){
						StringBuilder param = new StringBuilder();
						for(String s : disconnectedReaderList.values()){
							param.append(s);
							param.append(";");
						}
						if(count == 0)
							MessageDialog.showInfoMessage(panel, BaseMessages.getString("Message.MSG_169"));
						else
							MessageDialog.showInfoMessage(panel, BaseMessages.getString("Message.MSG_170")+"\n"+BaseMessages
									.getString("","Message.MSG_171",param.toString()));
					}
			
/*xusheng 2012.5.9 如果选中的读写器已断开，MSG提示  end*/	
					
					//重新选择已选中行
					selectedRowIndexs = MainDemoPanel.getReadDataPanel().centerTable
						.getSelectedRows();
					
					if (selectedRowIndexs.length != 0) {
						JTable dt = WidgetFactory.getInstance().buildJTable(
								"tagInfo", "tag.access.header");

						DefaultTableModel dtModel = (DefaultTableModel) dt
								.getModel();
						
						
					for (int selectedIndex : selectedRowIndexs) {

						String[] rowData = new String[dtModel.getColumnCount()];
						rowData[0] = (String) MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, 0);
						rowData[1] = (String) MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, 1);
						rowData[2] = (String) MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, 2);
						rowData[3] = (String) MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, 3);
						rowData[4] = (String) MainDemoPanel.getReadDataPanel().centerTable
								.getValueAt(selectedIndex, 4);

						int t = 0;
						String ant = "";
						for (int j = 6; j < 10; j++) {
							if (MainDemoPanel.getReadDataPanel().centerTable
									.getValueAt(selectedIndex, j) == null
									|| ((String) MainDemoPanel
											.getReadDataPanel().centerTable
											.getValueAt(selectedIndex, j))
											.equals(""))
								continue;
							if (t < Integer.parseInt((String) MainDemoPanel
									.getReadDataPanel().centerTable.getValueAt(
									selectedIndex, j))) {
								t = Integer.parseInt((String) MainDemoPanel
										.getReadDataPanel().centerTable
										.getValueAt(selectedIndex, j));
								String cn = MainDemoPanel.getReadDataPanel().centerTable
										.getColumnName(j);
								ant = cn.substring(cn.length() - 1);
							}
						}

						rowData[5] = ant;
						dtModel.addRow(rowData);
						Common.myBeep();
					}

					// 选中表格所有行
					//dt.setRowSelectionInterval(0, 0);
					dt.selectAll();
					TagAccessDialog tagDialog = new TagAccessDialog(dt);
					tagDialog.setLocationRelativeTo(frame);
					tagDialog.setVisible(true);
				} else {
//					MessageDialog.showInfoMessage(panel, BaseMessages
//							.getString("EightCenterNorthOne.selectOneTag"));
				}

			}
		});

		btnQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!FindTagPanel.show) {
					FindTagPanel findTag = new FindTagPanel(MainDemoPanel
							.getReadDataPanel().centerTable);
					findTag.setAlwaysOnTop(true);
					findTag.setLocationRelativeTo(null);
					findTag.setVisible(true);
				}
			}
		});
		
		showComlumn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainFrame.mainPanel.panel.rdp.showComlumnMenu().show(panel2,100,24);
			}
			
		});
	}

	public AbstractButton getStopJButton() {
		return stopJButton;
	}

	public void setStopJButton(AbstractButton stopJButton) {
		this.stopJButton = stopJButton;
	}

	public AbstractButton getReadButton() {
		return readButton;
	}

	public void setReadButton(AbstractButton readButton) {
		this.readButton = readButton;
	}

	public AbstractButton getTagAccessJButton() {
		return tagAccessJButton;
	}

	public void setTagAccessJButton(AbstractButton tagAccessJButton) {
		this.tagAccessJButton = tagAccessJButton;
	}

	public AbstractButton getBtnQuery() {
		return btnQuery;
	}

	public void setBtnQuery(AbstractButton btnQuery) {
		this.btnQuery = btnQuery;
	}



}