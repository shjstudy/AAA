package com.invengo.xcrf.ui.tree;

import invengo.javaapi.core.RFIDProtocol;
import invengo.javaapi.core.TcpIpListener;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 8510391715397780445L;

	protected boolean isSelected;

	protected boolean isFocus;

	protected boolean isConnected;

	private String nodeName;

	private boolean enable;
	private RFIDProtocol protocol;
	private String readerType;

	public String getReaderType() {
		return readerType;
	}

	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}

	private TcpIpListener tcpIpListener = null;

	public TcpIpListener getTcpIpListener() {
		return tcpIpListener;
	}

	public void setTcpIpListener(TcpIpListener tcpIpListener) {
		this.tcpIpListener = tcpIpListener;
	}

	public RFIDProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(RFIDProtocol protocol) {
		this.protocol = protocol;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String name) {
		nodeName = name;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}

	public CheckNode() {
		this(null);
	}

	public CheckNode(String str) {
		this(str, true, false);
	}

	public CheckNode(String str, boolean allowsChildren, boolean isSelected) {
		super(str, allowsChildren);
		this.isSelected = isSelected;
		this.nodeName = str;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		// 如果不是叶子节点，同步状态到子节点
		if (this.getChildCount() != 0) {
			NodeUtil.changeChildNodesSelectStatus(this);
		}

		setParentNodeSelected(isSelected);

	}

	// 修改父节点状态
	public void setParentNodeSelected(boolean isSelected) {
		this.isSelected = isSelected;

		// case 1: 叶子节点取消选中时，若有父节点，则将父节点也取消选中
		if (this.isSelected() == false) {
			if (this.getParent() != null
					&& ((CheckNode) this.getParent()).isSelected() == true)
				((CheckNode) this.getParent()).setParentNodeSelected(false);
		}
		// case 2: 叶子节点选中时，若有父节点，则判断其余叶子节点是否全部选中，若全部选中，则更新父节点为选中
		else if (this.isSelected() == true) {
			if (this.getParent() != null
					&& ((CheckNode) this.getParent()).isSelected() == false) {
				CheckNode parentNode = (CheckNode) this.getParent();
				if (NodeUtil.checkNodeSelectStatus(parentNode, true)) {
					parentNode.setParentNodeSelected(true);
				}
			}
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	public String getName() {
		return nodeName;
	}

	public boolean equals(CheckNode node) {
		if (node == null)
			return this == node;
		return node.getName().equals(this.getName());
	}

	public boolean isFocus() {
		return isFocus;
	}

	public void setFocus(boolean isFocus) {
		this.isFocus = isFocus;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

}
