package com.invengo.xcrf.ui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.dialog.MessageDialog;

public class FindTagPanel extends JDialog {

	private static final long serialVersionUID = -8340755890325995310L;
	private String queryContend = "";
	private int index = 0;
	private JTable table;

	public static boolean show;

	public FindTagPanel(JTable table) {
		this.table = table;
		initComponents();
		show = true;
	}

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		txtQueryContext = new javax.swing.JTextField();
		btnQuery = new javax.swing.JButton();

		setMaximumSize(new java.awt.Dimension(365, 145));
		setMinimumSize(new java.awt.Dimension(365, 145));
		setPreferredSize(new java.awt.Dimension(365, 145));
		getContentPane().setLayout(null);
		setTitle(BaseMessages.getString("FindTagForm.FindTagForm"));
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				show = false;
			}
		});

		jLabel1.setText("查找内容：");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 20, 150, 20);

		getContentPane().add(txtQueryContext);
		txtQueryContext.setBounds(20, 40, 330, 21);

		btnQuery.setText("查找下一个");
		getContentPane().add(btnQuery);
		btnQuery.setBounds(241, 80, 110, 23);

		cbkUper = new JCheckBox(BaseMessages.getString("FindTagForm.cbkUper"));
		cbkUper.setBounds(132, 80, 103, 23);
		getContentPane().add(cbkUper);
		btnQuery.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = txtQueryContext.getText();
				if ("".equals(text)) {
					MessageDialog.showInfoMessage(FindTagPanel.this,
							"请输入要查找的内容！");
					return;
				}
				if (cbkUper.isSelected()) {
					text = text.toLowerCase();
				}
				if (!text.equals(queryContend)) {
					queryContend = text;
					index = 0;
				}
				TableModel tableModel = table.getModel();
				int rowCount = tableModel.getRowCount();
				if (index == rowCount) {
					index = 0;
				}
				boolean find = false;
				out: for (int rowIndex = index; rowIndex < rowCount; rowIndex++) {
					for (int columnIndex = 2; columnIndex < 5; columnIndex++) {
						String value = tableModel.getValueAt(rowIndex,
								columnIndex).toString();
						if (value != null && value.contains(text)) {
							table.setRowSelectionInterval(rowIndex, rowIndex);
							index = rowIndex + 1;
							find = true;
							break out;
						}
					}
				}
				if (!find) {
					MessageDialog.showInfoMessage(FindTagPanel.this,
							BaseMessages.getString("", "Message.MSG_12",
									new String[] { text }));
				}
			}
		});

	}

	private javax.swing.JButton btnQuery;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField txtQueryContext;
	private JCheckBox cbkUper;
}
