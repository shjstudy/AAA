package com.invengo.xcrf.ui.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

import com.invengo.xcrf.ui.WidgetFactory.MyTableModel;

public class BooleanRenderer extends JCheckBox implements TableCellRenderer,
		UIResource {
	private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public BooleanRenderer() {
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorderPainted(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		MyTableModel tm = (MyTableModel)table.getModel();
		if(!tm.getEditColumnIndexs().contains(row+","+column))
		{
			setEnabled(false);
			setBackground(Color.GRAY);
			setSelected(false);
			return this;
		}
		setEnabled(true);
		setSelected((value != null && ((Boolean) value).booleanValue()));
		
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(noFocusBorder);
		}

		return this;
	}
}