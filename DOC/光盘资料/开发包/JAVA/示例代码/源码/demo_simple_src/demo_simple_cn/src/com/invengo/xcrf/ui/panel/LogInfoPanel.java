package com.invengo.xcrf.ui.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.core.util.UnitResultUtils;

public class LogInfoPanel extends JPanel {
	private static final long serialVersionUID = -7970324539968033690L;
	private final Pick pick;
	private DateFormat formatOutput = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private JTable resultTable;
	private JLabel jLabel1;
	private JLabel jLabel2;

	private final JPopupMenu clickMenu;

	public LogInfoPanel(Pick pick) {
		this.pick = pick;
		clickMenu = new JPopupMenu();
		JMenuItem deleteButton = new JMenuItem("É¾³ý¸ÃÏî¼ÇÂ¼");
		clickMenu.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LogInfoPanel.this.setVisible(false);
				UnitResultUtils.deleteUnitResult(getPick().getPickName());
			}
		});
		initComponents();
	}

	public Pick getPick() {
		return pick;
	}

	private TableModel buildTableModel() {
		TableModel result = new DefaultTableModel(12, pick.getConnTypes()
				.size() + 1);
		result.setValueAt(BaseMessages.getString("Message.MSG_83"), 0, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_84"), 1, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_85"), 2, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_86"), 3, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_87"), 4, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_88"), 5, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_89"), 6, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_90"), 7, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_91"), 8, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_92"), 9, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_93"), 10, 0);
		result.setValueAt(BaseMessages.getString("Message.MSG_94"), 11, 0);
		for (int i = 0; i < pick.getConnTypes().size(); i++) {
			ConnType connType = pick.getConnTypes().get(i);
			result.setValueAt(connType.getValue(), 0, i + 1);
			result.setValueAt(connType.getOpenPowerStr(), 1, i + 1);
			result.setValueAt(connType.getClosePowerStr(), 2, i + 1);
			result.setValueAt(connType.getAntenna1Str(), 3, i + 1);
			result.setValueAt(connType.getAntenna2Str(), 4, i + 1);
			result.setValueAt(connType.getAntenna3Str(), 5, i + 1);
			result.setValueAt(connType.getAntenna4Str(), 6, i + 1);
			result.setValueAt(connType.getBeepStr(), 7, i + 1);
			result.setValueAt(connType.getLedStr(), 8, i + 1);
			result.setValueAt(connType.getGpioStr(), 9, i + 1);
			result.setValueAt(connType.getRateStr(), 10, i + 1);
			result.setValueAt(connType.getPowerStr(), 11, i + 1);
		}
		return result;
	}

	private void initComponents() {
		setPreferredSize(new Dimension(400, 240));
		setMaximumSize(new Dimension(800, 240));
		setMinimumSize(new Dimension(800, 240));
		// setBorder(new LineBorder(Color.DARK_GRAY, 1));
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		jLabel1 = new JLabel();
		jLabel1.setText("=======================================================");
		add(jLabel1);
		jLabel1.setBounds(10, 10, 420, 15);
		jLabel2 = new JLabel();
		jLabel2.setText(BaseMessages.getString(
				"",
				"Message.MSG_82",
				new String[] { pick.getIndex() + "",
						formatOutput.format(pick.getMarkTime()),
						pick.getPickName(), pick.getProtocol() }));
		jLabel2.setBounds(10, 30, 450, 15);
		add(jLabel2);
		resultTable = new JTable();
		// resultTable.setBackground(new java.awt.Color(255, 228, 181));
		resultTable.setEnabled(false);
		resultTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				formMouseClicked(evt);
			}
		});
		setMinimumSize(new Dimension(500, 400));
		setLayout(null);
		//setBackground(new Color(255, 228, 181));

		resultTable.setModel(buildTableModel());
		resultTable.setShowHorizontalLines(false);
		resultTable.setShowVerticalLines(false);

		add(resultTable);
		resultTable.setBounds(50, 50, (pick.getConnTypes().size() + 1) * 80,
				200);
	}

	private void formMouseClicked(MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON3) {
			clickMenu.show(evt.getComponent(), evt.getX(), evt.getY());
		}
	}

}