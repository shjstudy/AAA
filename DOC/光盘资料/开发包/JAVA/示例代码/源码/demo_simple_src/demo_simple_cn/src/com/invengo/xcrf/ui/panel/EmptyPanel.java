package com.invengo.xcrf.ui.panel;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.invengo.xcrf.core.i18n.BaseMessages;

public class EmptyPanel extends JPanel {
	private static final long serialVersionUID = -7970324539968033690L;
	private JLabel jLabel1;
	private JLabel jLabel2;

	public EmptyPanel() {
		initComponents();
	}

	private void initComponents() {
		setPreferredSize(new Dimension(400, 240));
		setMaximumSize(new Dimension(800, 240));
		setMinimumSize(new Dimension(800, 240));
		jLabel1 = new JLabel();
		jLabel1.setText("=======================================================");
		add(jLabel1);
		jLabel1.setBounds(10, 10, 420, 15);
		jLabel2 = new JLabel();
		jLabel2.setText(BaseMessages.getString("Message.MSG_80"));
		jLabel2.setBounds(10, 30, 450, 15);
		add(jLabel2);

		setMinimumSize(new Dimension(500, 400));
		setLayout(null);

	}

}