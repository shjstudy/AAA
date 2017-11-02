package com.invengo.xcrf.ui.panel;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.TcpIpListener;
import invengo.javaapi.handle.IClientConnHandle;
import invengo.javaapi.protocol.IRP1.PowerOff;
import invengo.javaapi.protocol.IRP1.ReadTag;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.SysConfig_500;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.ReadTag.ReadMemoryBank;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfigUtil;
import com.invengo.xcrf.core.demo.UserConfig_IRP1;
import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.MainFrame;
import com.invengo.xcrf.ui.dialog.ConfigurationDialog;
import com.invengo.xcrf.ui.dialog.CreateDialog;
import com.invengo.xcrf.ui.dialog.CreateReaderDialog;
import com.invengo.xcrf.ui.dialog.MessageDialog;
import com.invengo.xcrf.ui.tree.CheckNode;
import com.invengo.xcrf.ui.tree.RootTree;
import com.invengo.xcrf.ui.tree.TreeNodeRender;

public class TreePanel extends JScrollPane {

	private static final long serialVersionUID = -8609722384462020980L;

	private CheckNode focusNode;

	private JPopupMenu tableRightClickMenu;

	private JMenuItem connButton;
	private JMenuItem disConnButton;
	private JMenuItem enableButton;
	private JMenuItem disableButton;
	private JMenuItem configButton;
	private JMenuItem configConnButton;
	private JMenuItem clearReaderButton;

	private JPopupMenu serverPopupMenu;

	private JMenuItem serverConnButton;
	private JMenuItem serverDisConnButton;
	private JMenuItem serverEnableButton;
	private JMenuItem serverDisableButton;
	private JMenuItem serverConfigButton;
	private JMenuItem serverClearbutton;

	private JFrame frame;

	// private JMenuItem serverConfigConnButton;

	/**
	 * @param frame
	 */
	public TreePanel(JFrame frame) {

		this.frame = frame;

		setLayout(new ScrollPaneLayout());
		// 加一颗树。。。
		JTree rootTree = RootTree.getTree();
		rootTree.setCellRenderer(new TreeNodeRender());
		// 监听
		rootTree.addMouseListener(new NodeSelectionListener(rootTree));

		rootTree.setBackground(new Color(199, 237, 204));

		// 添加树到面板
		setViewportView(rootTree);

		// 右键对话框
		buildPopWindow();

		// server的右键菜单
		buildServerPopMenu();

	}

	private void buildServerPopMenu() {
		serverPopupMenu = new JPopupMenu();
		serverConnButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_95"));
		serverDisConnButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_96"));
		serverEnableButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_97"));
		serverDisableButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_98"));
		serverConfigButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_99"));
		serverClearbutton = new JMenuItem(BaseMessages
				.getString("MainForm.picRemove"));
		// serverConfigConnButton = new JMenuItem(
		// BaseMessages.getString("Message.MSG_100"));

		serverPopupMenu.add(serverConnButton);
		serverPopupMenu.add(serverDisConnButton);
		serverPopupMenu.add(serverEnableButton);
		serverPopupMenu.add(serverDisableButton);
		serverPopupMenu.add(serverConfigButton);
		serverPopupMenu.add(serverClearbutton);
		// serverPopupMenu.add(serverConfigConnButton);

		serverConnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (focusNode != null) {
					final String name = focusNode.getNodeName();
					if (name.indexOf("Port:") != -1) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								TcpIpListener rs = new TcpIpListener(Integer
										.valueOf(name.substring(5, name
												.length())), focusNode
										.getProtocol());
								focusNode.setTcpIpListener(rs);
								IClientConnHandle connHandle = new ClientConnHandle();
								rs.onClientConnHandle.add(connHandle);
								rs.run();
							}
						});

						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								try {
									focusNode.setConnected(true);
									RootTree.getTree().updateUI();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
					}
				}
			}
		});
		serverDisConnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (focusNode != null) {
					String name = focusNode.getNodeName();
					if (name.indexOf("Port:") != -1) {
						TcpIpListener rs = focusNode.getTcpIpListener();
						if (rs != null) {
							if (focusNode.getChildCount() > 0) {
								;
								if (!MessageDialog.showConfirmDialog(
										BaseMessages.getString("",
												"Message.MSG_8",
												new String[] { focusNode
														.getNodeName() }),
										BaseMessages
												.getString("Message.MSG_21"))) {
									return;
								}

							}
							/* xusheng 2012.4.25 */
							// 节点下的连接全部断开
							for (Demo demo : DemoRegistry.getCurrentDemos()
									.values()) {
								if (demo.getNode().getParent() == focusNode
										&& demo.getNode().isSelected())
									if (demo.isReading()) {
										demo.forceDisConnect();
									} else {
										demo.disConnect();
									}
								DemoRegistry.getCurrentDemos().remove(demo);
							}
							focusNode.removeAllChildren();
							/* xusheng 2012.4.25 */
							rs.onClientConnHandle.clear();
							rs.stop();

							focusNode.setTcpIpListener(null);
							focusNode.setConnected(false);

							// RootTree.getTree().updateUI();

						}
					}
				}
			}
		});
		serverEnableButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (focusNode != null) {
					String name = focusNode.getNodeName();
					if (name.indexOf("Port:") != -1) {
						focusNode.setEnable(true);
						UserConfigUtil.updateServer(name.substring(5, name
								.length()), true);
						RootTree.getTree().updateUI();
					}
				}
			}
		});
		serverDisableButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (focusNode != null) {
					String name = focusNode.getNodeName();
					if (name.indexOf("Port:") != -1) {
						focusNode.setEnable(false);
						UserConfigUtil.updateServer(name.substring(5, name
								.length()), false);
						RootTree.getTree().updateUI();
					}
				}

			}
		});

		serverConfigButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CreateReaderDialog crd = new CreateReaderDialog(frame);
				crd.page1(false);
				crd.page5(true);
				crd.setTitle(BaseMessages
						.getString("SysitConfigFormEdit.SysitConfigFormEdit"));
				crd.getPriStep().setVisible(false);
				crd.okButton.setVisible(false);
				crd.cancelButton.setText(BaseMessages.getString("button.ok"));
				crd.cancelButton.setVisible(true);
				crd.cancelButton.setEnabled(true);
				crd.getRadioButton_1().setSelected(true);
				crd.setUpdate(true);
				crd.setSeverPort(focusNode.getNodeName());
				crd.setVisible(true);
			}
		});

		serverClearbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (focusNode.getParent() == RootTree.getServerRootNode()) {

					focusNode.removeAllChildren();
					UserConfigUtil
							.removeServer(focusNode.getNodeName().substring(
									focusNode.getNodeName().indexOf(":") + 1));

					RootTree.getServerRootNode().remove(focusNode);
				}
				RootTree.getTree().updateUI();
			}
		});
	}

	private class ClientConnHandle implements IClientConnHandle {
		@Override
		public void clientConnHandle(Socket socket, String ver) {
			Reader reader = new Reader(socket);
			try {
				int count = RootTree.getServerRootNode().getChildCount();
				for (int i = 0; i < count; i++) {
					CheckNode node = (CheckNode) RootTree.getServerRootNode()
							.getChildAt(i);
					if (("Port:" + reader.getReaderGroup()).equals(node
							.getNodeName())) {
						UserConfig_IRP1 config = new UserConfig_IRP1();
						config.setProtocol(RFIDProtocol.IRP1);
						config.setReaderName(reader.getReaderName());
						config.setReaderGroup(reader.getReaderGroup());
						config.setServerPort(reader.getReaderGroup());
						config.setConnType("TCPIP_Server");

						String readerType = node.getReaderType();
						config.setReaderType(readerType);
						config.setEnable(true);
						config.setRmb(ReadMemoryBank.EPC_6C);
						config.setIsLoop(true);
						config.setTagNum(8);
						if ("800".equals(readerType)) {
							config.setStartRoSpec(new ReadTag(
									ReadMemoryBank.EPC_6C));
							config.setActiveAntenna(new SysConfig_800(
									(byte) 0x02, new byte[] { 0x01 }));

							config.setStopRoSpec(new PowerOff());
							config.setStopType("800");
						} else if ("500".equals(readerType)) {
							config.setStartRoSpec(new ReadTag(
									ReadMemoryBank.EPC_6C));
							config.setActiveAntenna(new SysConfig_500(
									(byte) 0x02, (byte) 0x01,
									new byte[] { 0x01 }));

							config.setStopRoSpec(new PowerOff());
							config.setStopType("500");
						}
						for (int j = 0; j < node.getChildCount(); j++) {
							CheckNode child = (CheckNode) node.getChildAt(j);
							if ((child.getNodeName().substring(0, child
									.getNodeName().indexOf(":")))
									.equals(reader.getReaderName()
											.substring(
													0,
													reader.getReaderName()
															.indexOf(":")))) {
								RootTree.getTree()
										.removeServerNode(node, child);
								Demo oldDemo = new Demo();
								oldDemo.setDemoName(child.getNodeName());
								oldDemo.setGroupName(reader.getReaderGroup());
								DemoRegistry.removeRegistryCurrentDemo(oldDemo);
								break;
							}
						}
						Demo demo = new Demo(config, reader);
						DemoRegistry.registryCurrentDemo(demo);
						DemoRegistry.setCurrentDemo(demo);
						// if (reader != null && reader.isConnected()
						// && config != null
						// && config.getStopRoSpec() != null) {
						// if (reader.send(config.getStopRoSpec())) {
						// System.out
						// .println("===========sssssssssssssssss");
						// }
						// }
						MainDemoPanel.getReadDataPanel().addReader(demo);// 注册调用数据
						RootTree.getTree().addServerNode(demo, node);
						RootTree.getTree().updateUI();
						break;
					}
				}
			} catch (Exception e) {

			}
			/* xusheng */
			((MainFrame) frame).UIUpdateWhenDemoConnected();
			/* xusheng */
		}
	}

	// 初始化按钮选项
	private void reBuildPopMenuBtn() {
		if (focusNode == null)
			return;

		Demo currentDemo = DemoRegistry.getCurrentDemo();

		if (focusNode.getParent() == RootTree.getReaderRootNode()) {
			boolean enableGroupOperationButton = false;
			// Enumeration<CheckNode> childs = focusNode.children();
			// while(childs.hasMoreElements())
			// {
			// if(childs.nextElement().isSelected())
			// {
			// enableGroupOperationButton = true;
			// break;
			// }
			// }

			for (Demo demo : DemoRegistry.getCurrentDemos().values()) {
				if (demo.getNode().getParent() == focusNode
						&& demo.getNode().isSelected()
						&& demo.getConfig().isEnable()) {
					enableGroupOperationButton = true;
					break;
				}
			}

			connButton.setEnabled(enableGroupOperationButton);
			disConnButton.setEnabled(enableGroupOperationButton);

			enableButton.setEnabled(false);
			disableButton.setEnabled(false);
			configButton.setEnabled(false);
			configConnButton.setEnabled(false);
			clearReaderButton.setEnabled(false);

		} else if (currentDemo != null) {

			if (!currentDemo.getConfig().isEnable()) {
				connButton.setEnabled(false);
				disConnButton.setEnabled(false);
				enableButton.setEnabled(true);
				disableButton.setEnabled(false);
				configButton.setEnabled(false);
				clearReaderButton.setEnabled(true);

			} else if (!currentDemo.getReader().isConnected()) {
				connButton.setEnabled(true);
				disConnButton.setEnabled(false);
				enableButton.setEnabled(false);
				disableButton.setEnabled(true);
				configButton.setEnabled(false);
				clearReaderButton.setEnabled(false);

			} else {
				connButton.setEnabled(false);
				disConnButton.setEnabled(true);
				enableButton.setEnabled(false);
				disableButton.setEnabled(false);
				configButton.setEnabled(true);
				clearReaderButton.setEnabled(false);
			}

			if (!currentDemo.getReader().isConnected()) {
				configConnButton.setEnabled(currentDemo.getNode().isSelected());
			} else {
				configConnButton.setEnabled(false);
			}
			/* xusheng 2012.4.25 */
			if (!focusNode.isSelected()) {
				connButton.setEnabled(false);
				disConnButton.setEnabled(false);
				enableButton.setEnabled(false);
				disableButton.setEnabled(false);
				configButton.setEnabled(false);
				clearReaderButton.setEnabled(false);
			}
			/* xusheng 2012.4.25 */
			if (focusNode.getParent().getParent() == RootTree
					.getServerRootNode()) {
				connButton.setEnabled(false);
				disableButton.setVisible(false);
				clearReaderButton.setVisible(false);
			} else {

				disableButton.setVisible(true);
				clearReaderButton.setVisible(true);
			}

		}
	}

	private void buildPopWindow() {
		tableRightClickMenu = new JPopupMenu();
		connButton = new JMenuItem(BaseMessages.getString("Message.MSG_95"));
		disConnButton = new JMenuItem(BaseMessages.getString("Message.MSG_96"));
		enableButton = new JMenuItem(BaseMessages.getString("Message.MSG_97"));
		disableButton = new JMenuItem(BaseMessages.getString("Message.MSG_98"));
		configButton = new JMenuItem(BaseMessages.getString("Message.MSG_99"));
		configConnButton = new JMenuItem(BaseMessages
				.getString("Message.MSG_100"));
		clearReaderButton = new JMenuItem(BaseMessages
				.getString("MainForm.picRemove"));

		tableRightClickMenu.add(connButton);
		tableRightClickMenu.add(disConnButton);
		tableRightClickMenu.add(enableButton);
		tableRightClickMenu.add(disableButton);
		tableRightClickMenu.add(configButton);
		tableRightClickMenu.add(configConnButton);
		tableRightClickMenu.add(clearReaderButton);

		reBuildPopMenuBtn();

		connButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 当选中为组时，进行组操作
				if (focusNode.getParent() == RootTree.getReaderRootNode()) {
					for (Demo demo : DemoRegistry.getCurrentDemos().values()) {
						try {
							if (demo.getNode().isSelected()) {
								boolean connect = demo.connect();
								if (connect) {
									((MainFrame) frame)
											.UIUpdateWhenDemoConnected();
								}
							}
						} catch (Exception ex) {

						}
					}
					RootTree.getTree().updateUI();

				} else {
					try {
						Demo demo = DemoRegistry.getCurrentDemo();
						// MainDemoPanel.getReadDataPanel().addReader(demo);
						demo.connect();
						RootTree.getTree().updateUI();
					} catch (Exception ex) {

					}
				}
				/* xusheng */
				((MainFrame) frame).UIUpdateWhenDemoConnected();
				/* xusheng */

			}

		});

		disConnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// 当选中为组时，进行组操作
				if (focusNode.getParent() == RootTree.getReaderRootNode()) {
					// 获取需要处理的demo
					List<Demo> demos = new ArrayList<Demo>();
					for (Demo demo : DemoRegistry.getCurrentDemos().values()) {
						if (demo.getNode().getParent() == focusNode
								&& demo.getNode().isSelected())
							demos.add(demo);
					}

					String readers = "";
					for (Demo demo : demos) {
						if (demo.getReader() != null
								&& demo.getReader().isConnected()
								&& demo.isReading()) {
							readers += demo.getReader().getReaderName()
									+ "\r\n";
						}
					}
					if (!"".equals(readers)) {
						readers = readers.substring(0, readers
								.lastIndexOf("\r\n"));
						if (!MessageDialog.showConfirmDialog(BaseMessages
								.getString("", "TreePanel.disconnect",
										new String[] { readers }), BaseMessages
								.getString("Message.MSG_21"))) {
							return;
						}
					}
					for (Demo demo : demos) {
						if (demo.isReading()) {
							demo.forceDisConnect();
						} else {
							demo.disConnect();
						}
					}
					RootTree.getTree().updateUI();

				} else {
					try {
						Demo demo = DemoRegistry.getCurrentDemo();

						MainDemoPanel.getReadDataPanel().addReader(demo);
						if (demo.getReader() != null
								&& demo.getReader().isConnected()
								&& demo.isReading()) {

							if (MessageDialog.showConfirmDialog(BaseMessages
									.getString("", "TreePanel.disconnect",
											new String[] { demo.getReader()
													.getReaderName() }),
									BaseMessages.getString("Message.MSG_21"))) {
								demo.forceDisConnect();
							}
						} else {
							demo.disConnect();
						}
						if (demo.getNode().getParent().getParent() == RootTree
								.getServerRootNode()) {
							RootTree.getTree().removeServerNode(
									(CheckNode) demo.getNode().getParent(),
									demo.getNode());
						}
						RootTree.getTree().updateUI();
					} catch (Exception ex) {

					}
				}
				/* xusheng */
				((MainFrame) frame).UIUpdateWhenDemoConnected();
				/* xusheng */
			}

		});

		enableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Demo currentDemo = DemoRegistry.getCurrentDemo();
				currentDemo.getConfig().setEnable(true);
				// currentDemo.getConfig().removeConfig();
				// currentDemo.getConfig().saveConfig();
				currentDemo.getConfig().updateConfig();

				RootTree.getTree().updateUI();
			}

		});

		disableButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Demo currentDemo = DemoRegistry.getCurrentDemo();
				currentDemo.getConfig().setEnable(false);
				// currentDemo.getConfig().removeConfig();
				// currentDemo.getConfig().saveConfig();
				currentDemo.getConfig().updateConfig();

				RootTree.getTree().updateUI();
			}

		});

		configButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Demo demo = DemoRegistry.getCurrentDemo();
				if (demo != null && !demo.isReading()) {
					ConfigurationDialog.getConfigDialog().setVisible(true);
					RootTree.getTree().updateUI();
				} else {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_6"));
				}
			}

		});

		configConnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Demo demo = DemoRegistry.getCurrentDemo();
				if (demo != null && demo.getNode() != null
						&& demo.getNode().isSelected()) {
					CreateDialog createDialog = CreateDialog.getInstance();
					createDialog.fillData(demo);
					createDialog.setUpdate(true);
					createDialog.setVisible(true);
				}
			}

		});

		clearReaderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DemoRegistry.getCurrentDemo().getConfig().removeConfig();
				RootTree.getTree().removeNode(DemoRegistry.getCurrentDemo());
				DemoRegistry.getAllDemo().remove(
						DemoRegistry.getCurrentDemo().getDemoName());
				DemoRegistry.setCurrentDemo(null);
				RootTree.getTree().updateUI();
			}
		});
	}

	private final Timer timer = new Timer();
	private TimerTask task = null;

	class NodeSelectionListener implements MouseListener {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int row = tree.getRowForLocation(e.getX(), e.getY());
			TreePath path = tree.getPathForRow(row);
			if (path != null) {
				CheckNode node = (CheckNode) path.getLastPathComponent();

				switch (e.getButton()) {
				// 左键事件

				case MouseEvent.BUTTON1:
					// 改变节点状态
					if (node.isLeaf()
							&& !((CheckNode) node.getParent()).isRoot()) {
						DemoRegistry.setCurrentDemo(DemoRegistry
								.getDemoByNode(node));

						if (e.getClickCount() == 2) {
							cancelSingleClickTask();
							changeFocusNode(node);
							// tree.updateUI();
							showInfoMessages();
						}

						// if(e.getClickCount() == 2){
						// cancelSingleClickTask();
						// if(node.isSelected() &&
						// DemoRegistry.getDemoByNode(node).getReader().isConnected()){
						// if(node.getParent().getParent() ==
						// RootTree.getReaderRootNode()){
						// if(!DemoRegistry.getDemoByNode(node).isReading()){
						// ConfigurationDialog.getConfigDialog().setVisible(true);
						// RootTree.getTree().updateUI();
						// }
						// }else if(node.getParent().getParent() ==
						// RootTree.getServerRootNode()){
						// if(!DemoRegistry.getDemoByNode(node).isReading()){
						// ConfigurationDialog.getConfigDialog().setVisible(true);
						// RootTree.getTree().updateUI();
						// }
						// }
						// }
						// break;
						// }
						//						
						if (e.getClickCount() == 1) {
							addSingleClickTask(node, 230);
						}

						boolean isSelected = !(node.isSelected());
						Demo demo = DemoRegistry.getDemoByNode(node);
						if (demo != null) {
							if (isSelected) {
								MainDemoPanel.getReadDataPanel()
										.addReader(demo);
								MainDemoPanel.getScanPanel().addReader(demo);
							} else {
								if (!demo.isReading()) {
									MainDemoPanel.getReadDataPanel()
											.removeReader(demo);
									MainDemoPanel.getScanPanel().removeReader(
											demo);
								}
							}

						}

					} else {
						boolean isSelected = !(node.isSelected());
						node.setSelected(isSelected);
						changeFocusNode(node);
						tree.updateUI();
					}

					/* xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新 */
					((MainFrame) TreePanel.this.frame)
							.UIUpdateWhenDemoConnected();
					/* xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新 */
					break;
				// 右键事件
				case MouseEvent.BUTTON3:
					// 如果是server节点，就注册server右键菜单
					if (node.getParent() == RootTree.getServerRootNode()) {

						changeFocusNode(node);
						rebuildServerPopupMenu(node);
						serverPopupMenu.show(e.getComponent(), e.getX(), e
								.getY());
					} else if (node.getParent().getParent() == RootTree
							.getServerRootNode()) {
						changeFocusNode(node);
						// reBuildPopMenuBtn();
						buildPopWindow();
						tableRightClickMenu.show(e.getComponent(), e.getX(), e
								.getY());
					} else {
						// /*xusheng 2012.4.25*/
						// if (node == RootTree.getServerRootNode()) {
						// changeFocusNode(node);
						// rebuildServerPopupMenu(node);
						// serverPopupMenu.show(e.getComponent(), e.getX(), e
						// .getY());
						// }
						// /*xusheng 2012.4.25*/
						if (node != RootTree.getReaderRootNode()
								&& node != RootTree.getServerRootNode()
								/* xusheng 2012.4.25 */
								&& ((node.getParent()) != RootTree
										.getReaderRootNode())) {
							/* xusheng 2012.4.25 */
							changeFocusNode(node);
							DemoRegistry.setCurrentDemo(DemoRegistry
									.getDemoByNode(node));
							// reBuildPopMenuBtn();
							buildPopWindow();
							tableRightClickMenu.show(e.getComponent(),
									e.getX(), e.getY());
						}
					}
					break;
				default:
					break;
				}
				tree.updateUI();
			}
		}

		@SuppressWarnings("synthetic-access")
		private void rebuildServerPopupMenu(CheckNode node) {
			serverConnButton.setText(BaseMessages.getString("Message.MSG_95"));
			serverDisConnButton.setText(BaseMessages
					.getString("Message.MSG_96"));
			serverEnableButton
					.setText(BaseMessages.getString("Message.MSG_97"));
			serverDisableButton.setText(BaseMessages
					.getString("Message.MSG_98"));
			serverConfigButton
					.setText(BaseMessages.getString("Message.MSG_99"));
			// serverConfigConnButton.setText(BaseMessages
			// .getString("Message.MSG_100"));

			if (!node.isSelected()) {
				serverConnButton.setEnabled(false);
				serverDisConnButton.setEnabled(false);
				serverEnableButton.setEnabled(false);
				serverDisableButton.setEnabled(false);
				serverConfigButton.setEnabled(false);
				serverClearbutton.setEnabled(false);
			} else if (!node.isEnable()) {
				serverConnButton.setEnabled(false);
				serverDisConnButton.setEnabled(false);
				serverEnableButton.setEnabled(true);
				serverDisableButton.setEnabled(false);
				serverConfigButton.setEnabled(false);
				serverClearbutton.setEnabled(true);
			} else if (node.isConnected()) {
				serverConnButton.setEnabled(false);
				serverDisConnButton.setEnabled(true);
				serverEnableButton.setEnabled(false);
				serverDisableButton.setEnabled(false);
				serverConfigButton.setEnabled(false);
				serverClearbutton.setEnabled(false);

			} else if (node.isEnable()) {
				serverConnButton.setEnabled(true);
				serverDisConnButton.setEnabled(false);
				serverEnableButton.setEnabled(false);
				serverDisableButton.setEnabled(true);
				serverConfigButton.setEnabled(true);
				serverClearbutton.setEnabled(false);
			}
		}

		// 设定任务，在指定时间后，响应单击事件
		private void addSingleClickTask(final CheckNode node, long delay) {
			final Runnable run = new Runnable() {
				@Override
				public void run() {
					boolean isSelected = !(node.isSelected());
					node.setSelected(isSelected);
					changeFocusNode(node);
					tree.updateUI();
					/* xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新 */
					((MainFrame) TreePanel.this.frame)
							.UIUpdateWhenDemoConnected();
					/* xusheng 2012.5.9 如果选中的读写器已连接,进行主界面UI更新 */
				}
			};

			task = new TimerTask() {
				@Override
				public void run() {
					SwingUtilities.invokeLater(run);
				}
			};
			timer.schedule(task, delay);
		}

		// 取消之前设定的
		private void cancelSingleClickTask() {
			if (task != null) {
				task.cancel();
			}
		}

		// show info message
		private void showInfoMessages() {
			Demo currentDemo = DemoRegistry.getCurrentDemo();
			if (currentDemo == null) {
				return;
			}

			if (currentDemo.getReader().isConnected()) {
				// try{
				if (currentDemo.isReading()) {
					MessageDialog.showInfoMessage(BaseMessages
							.getString("Message.MSG_6"));
					return;
				}
				ConfigurationDialog.getConfigDialog().setVisible(true);
				// }catch(Exception e){

				// }
			}
			// else if (!currentDemo.getConfig().isEnable()) {
			// MessageDialog.showInfoMessage(null, "请先启用并连接读写器！");
			// }
			// else if (!currentDemo.getReader().isConnected()) {
			// MessageDialog.showInfoMessage(null, "请先连接读写器！");
			// }
			// else {
			// // TODO
			// }
		}

		private void changeFocusNode(CheckNode node) {
			if (focusNode != node) {
				if (focusNode != null) {
					focusNode.setFocus(false);
				}
				focusNode = node;
				node.setFocus(true);
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public CheckNode getFocusNode() {
		return focusNode;
	}

	public void setFocusNode(CheckNode focusNode) {
		this.focusNode = focusNode;
	}

}
