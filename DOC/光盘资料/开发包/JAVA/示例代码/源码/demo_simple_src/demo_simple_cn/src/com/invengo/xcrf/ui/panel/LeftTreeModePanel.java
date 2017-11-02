package com.invengo.xcrf.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfigUtil;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.dialog.CreateDialog;
import com.invengo.xcrf.ui.dialog.CreateReaderDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.tree.CheckNode;
import com.invengo.xcrf.ui.tree.RootTree;

public class LeftTreeModePanel extends JPanel {

	static Color backgroud_title = new Color(0x99ccff);

	private final TreePanel treePanel;
	private CreateDialog createDialog;
	private MainFrame frame;

	JLabel label;
	JLabel label_1;
	JLabel label_2;
	JLabel label_3;
	JLabel label_4;


	public LeftTreeModePanel(JFrame frame) {

		this.frame = (MainFrame) frame;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(264, 453));

		Box vBox = Box.createVerticalBox();

		JPanel p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		p2.setPreferredSize(new Dimension(292, 27));
		p2.setLayout(null);

		JButton btnConnect = new JButton("连接");
		btnConnect.setBounds(1, 1, 60, 21);
		btnConnect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				connReaders();
			}}
		);
		p2.add(btnConnect);
		
		JButton btnDisconnect = new JButton("断开");
		btnDisconnect.setBounds(64, 1, 60, 21);
		btnDisconnect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				disconnReaders();
			}}
		);
		p2.add(btnDisconnect);
		
		JButton btnAdd = new JButton("添加");
		btnAdd.setBounds(138, 1, 60, 21);
		btnAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showCreateDialog();
			}}
		);
		p2.add(btnAdd);
		
		JButton btnDelete = new JButton("删除");
		btnDelete.setBounds(200, 1, 60, 21);
		btnDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeSelectedReaders();
			}}
		);
		p2.add(btnDelete);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(292, 23));
		panel.setMaximumSize(new Dimension(292, 23));
		panel.setMinimumSize(new Dimension(292, 23));
		// panel.add(p1, BorderLayout.NORTH);
		panel.add(p2, BorderLayout.CENTER);
		panel.setOpaque(false);
		panel.setBackground(new Color(199,237,204));
		vBox.add(panel);

		treePanel = new TreePanel(frame);
		treePanel.getViewport().setBackground(new Color(199,237,204));
		vBox.add(treePanel);
		vBox.setBorder(new LineBorder(Color.CYAN));
		
		add(vBox, BorderLayout.CENTER);
	}


	private void showCreateDialog() {
		CreateReaderDialog crd = new CreateReaderDialog(frame);
		crd.setVisible(true);
	}

	private void updateSelectedReader() {
		Demo demo = DemoRegistry.getCurrentDemo();
		if (demo != null && demo.getNode() != null
				&& demo.getNode().isSelected() && !demo.isReading() ) {
			if(demo.getReader().isConnected())
				return;
			
			createDialog = CreateDialog.getInstance();
			createDialog.fillData(demo);
			createDialog.setUpdate(true);
			createDialog.setVisible(true);
		} else if (this.treePanel.getFocusNode().getParent() == RootTree.getServerRootNode()
				&& this.treePanel.getFocusNode().isSelected()) {
			CreateReaderDialog crd = new CreateReaderDialog(frame);
			crd.page1(false);
			crd.page5(true);
			crd.setTitle(BaseMessages.getString("SysitConfigFormEdit.SysitConfigFormEdit"));
			crd.getPriStep().setVisible(false);
			crd.okButton.setVisible(false);
			crd.cancelButton.setText(BaseMessages.getString("SysitConfigFormEdit.button1"));
			crd.cancelButton.setVisible(true);
			crd.cancelButton.setEnabled(true);
			crd.getRadioButton_1().setSelected(true);
			crd.setUpdate(true);
			crd.setSeverPort(this.treePanel.getFocusNode().getNodeName());
			crd.setVisible(true);
		} else {
			MessageDialog.showInfoMessage(BaseMessages
					.getString("Message.MSG_10"));
		}
	}

	private void removeSelectedReaders() {
		if (!MessageDialog.showConfirmDialog(BaseMessages
				.getString("Message.MSG_9"), BaseMessages
				.getString("Message.MSG_21"))) {
			return;
		}
		List<String> deleteLst = new ArrayList<String>();
		// for (Entry<String, Demo> entry :
		// DemoRegistry.getAllDemo().entrySet()) {
		Iterator iterator = DemoRegistry.getAllDemo().keySet().iterator();
		while (iterator.hasNext()) {
			String KeyDemo = (String) iterator.next();
			Demo demo = DemoRegistry.getAllDemo().get(KeyDemo);
			if (demo.getConfig().isEnable() == false
					&& demo.getNode().isSelected()) {
				deleteLst.add(KeyDemo);
				// DemoRegistry.getAllDemo().remove(KeyDemo);
				// 删除reader在xml文件中的设定信息
				demo.getConfig().removeConfig();
				// DemoRegistry.removeRegistryDemo(demo);
				// 删除树节点
				RootTree.getTree().removeNode(demo);

				if ( DemoRegistry.getCurrentDemo()!= null && DemoRegistry.getCurrentDemo().getDemoName().equals(
						demo.getDemoName())) {
					DemoRegistry.setCurrentDemo(null);
				}
			}
		}
		if (deleteLst.size() != 0) {

			for (String key : deleteLst) {
				DemoRegistry.getAllDemo().remove(key);
			}
			RootTree.getTree().updateUI();
		}

		CheckNode server = RootTree.getServerRootNode();
		for (int i = server.getChildCount() - 1; i >= 0; i--) {
			CheckNode child = (CheckNode) server.getChildAt(i);

			if (child.isSelected() && !child.isEnable()) {
				server.remove(child);
				UserConfigUtil.removeServer(child.getNodeName().substring(
						child.getNodeName().indexOf(":") + 1));
			}
		}
		RootTree.getTree().updateUI();
	}

	private void connReaders() {
		Map<String, Demo> demos = DemoRegistry.getAllDemo();
		for (Demo demo : demos.values()) {
			// xusheng 2012.5.9 如果节点没有选中,不连接
			if (demo.getNode().isSelected()) {
				try {
					MainDemoPanel.getReadDataPanel().addReader(demo);
					demo.connect();
				} catch (Exception ex) {
					System.out.println(ex);
					continue;
				}
			}

		}
		RootTree.getTree().updateUI();
		/* xusheng 2012.5.9 主界面更新 */
		frame.UIUpdateWhenDemoConnected();
		/* xusheng 2012.5.9 主界面更新 */
	}

	private void disconnReaders() {
		Map<String, Demo> demos = DemoRegistry.getAllDemo();
		String readers = "";
		for (Demo demo : demos.values()) {
			if (demo.getReader() != null && demo.getReader().isConnected()
					&& demo.isReading() && demo.getNode().isSelected()) {
				readers += demo.getReader().getReaderName() + "\r\n";
			}
		}
		if (!"".equals(readers)) {
			readers = readers.substring(0, readers.lastIndexOf("\r\n"));
			if (!MessageDialog.showConfirmDialog(BaseMessages.getString("",
					"TreePanel.disconnect", new String[] { readers }),
					BaseMessages.getString("Message.MSG_21"))) {
				return;
			}
		}
		for (Demo demo : demos.values()) {
			if (demo.getNode().isSelected()) {
				if (demo.isReading()) {
					demo.forceDisConnect();
				} else {
					demo.disConnect();
				}
			}
		}
		RootTree.getTree().updateUI();
		/* xusheng 2012.5.9 主界面更新 */
		frame.UIUpdateWhenDemoConnected();
		/* xusheng 2012.5.9 主界面更新 */
	}

}
