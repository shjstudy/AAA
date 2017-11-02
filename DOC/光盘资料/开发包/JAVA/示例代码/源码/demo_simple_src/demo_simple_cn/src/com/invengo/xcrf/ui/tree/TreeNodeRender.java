package com.invengo.xcrf.ui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.ui.MainFrame;

public class TreeNodeRender extends JPanel implements TreeCellRenderer {
	ImageIcon foldImg = new ImageIcon(
			MainFrame.class.getResource("image/group_3.png"));
	ImageIcon foldServerImg = new ImageIcon(
			MainFrame.class.getResource("image/group_2.png"));
	private ImageIcon foldServerDisableImg = new ImageIcon(
			MainFrame.class.getResource("image/group_4.png"));
	ImageIcon foldDisableImg = new ImageIcon(
			MainFrame.class.getResource("image/group_1.png"));
	ImageIcon rootImg = new ImageIcon(
			MainFrame.class.getResource("image/root_1.PNG"));

	ImageIcon disEnabledImg = new ImageIcon(
			MainFrame.class.getResource("image/reader_0.png"));
	ImageIcon enabledImg = new ImageIcon(
			MainFrame.class.getResource("image/reader_1.png"));
	ImageIcon connectedImg = new ImageIcon(
			MainFrame.class.getResource("image/reader_2.png"));

	protected JCheckBox check;

	protected TreeLabel label;

	public TreeNodeRender() {
		setLayout(null);
		add(check = new JCheckBox());
		add(label = new TreeLabel());
		check.setBackground(new Color(199,237,204));
		label.setOpaque(true);
		// label.setForeground(UIManager.getColor("Tree.textForeground"));
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		setEnabled(tree.isEnabled());
		CheckNode node = (CheckNode) value;

		check.setSelected(node.isSelected());
		label.setFont(tree.getFont());
		label.setText(stringValue);
		label.setSelected(node.isFocus());
		label.setFocus(node.isFocus());

		if (label.getText().startsWith("Port:")) {
			if (!node.isEnable()) {
				label.setIcon(foldServerDisableImg);
			} else if (node.isConnected()) {
				label.setIcon(foldServerImg);
			} else {
				label.setIcon(foldDisableImg);
			}

		} else if (node.getParent() == node.getRoot()) {
			label.setIcon(rootImg);
		} else if (!leaf) {
			label.setIcon(foldImg);
		} else {
			label.setIcon(connectedImg);
		}

		Demo demo = DemoRegistry.getDemoByNode(node);
		if (demo != null) {
			boolean isEnable = demo.getConfig().isEnable();
			boolean connected = demo.getReader().isConnected();

			if (isEnable) {
				label.setForeground(Color.black);
				label.setIcon(enabledImg);
			} else {
				//label.setForeground(Color.gray);
				label.setIcon(disEnabledImg);
			}

			if (connected) {
				//label.setForeground(new Color(0x6633));
				label.setIcon(connectedImg);
			}
		} else {

			if (label.getText().startsWith("Port:")) {
				if (!node.isEnable()) {
					label.setForeground(Color.gray);
				} else if (node.isConnected()) {
					//label.setForeground(Color.green);
				} else {
					//label.setForeground(Color.red);

				}
			} else {
				label.setForeground(Color.black);
			}
		}

		// } else if (expanded) {
		// label.setIcon(UIManager.getIcon("Tree.openIcon"));
		// } else {
		// label.setIcon(UIManager.getIcon("Tree.closedIcon"));
		// }
		//

		return this;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
						: d_check.height));
	}

	@Override
	public void doLayout() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		int y_check = 0;
		int y_label = 0;
		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}
		check.setLocation(0, y_check);
		check.setBounds(0, y_check, d_check.width, d_check.height);
		label.setLocation(d_check.width, y_label);
		label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
	}

	@Override
	public void setBackground(Color color) {
		if (color instanceof ColorUIResource)
			color = null;
		super.setBackground(color);
	}

	public class TreeLabel extends JLabel {
		boolean isSelected;

		boolean hasFocus;

		public TreeLabel() {
		}

		@Override
		public void setBackground(Color color) {
			if (color instanceof ColorUIResource)
				color = null;
			super.setBackground(color);
		}

		@Override
		public void paint(Graphics g) {
			String str;
			if ((str = getText()) != null) {
				if (0 < str.length()) {
					if (isSelected) {
						g.setColor(UIManager
								.getColor("Tree.selectionBackground"));
					} else {
						g.setColor(UIManager.getColor("Tree.textBackground"));
					}
					Dimension d = getPreferredSize();
					int imageOffset = 0;
					Icon currentI = getIcon();
					if (currentI != null) {
						imageOffset = currentI.getIconWidth()
								+ Math.max(0, getIconTextGap() - 1);
					}
					g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
							d.height);
					if (hasFocus) {
						g.setColor(UIManager
								.getColor("Tree.selectionBorderColor"));
						g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
								d.height - 1);
					}
				}
			}
			super.paint(g);
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension retDimension = super.getPreferredSize();
			if (retDimension != null) {
				retDimension = new Dimension(retDimension.width + 3,
						retDimension.height);
			}
			return retDimension;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;

		}

		public void setFocus(boolean hasFocus) {
			this.hasFocus = hasFocus;
		}
	}
}
