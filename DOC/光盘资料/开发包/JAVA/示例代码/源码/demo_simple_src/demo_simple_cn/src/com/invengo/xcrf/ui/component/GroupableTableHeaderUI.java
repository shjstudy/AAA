package com.invengo.xcrf.ui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * GroupableTableHeader
 * 
 */

public class GroupableTableHeaderUI extends BasicTableHeaderUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		Rectangle clipBounds = g.getClipBounds();
		if (header.getColumnModel() == null)
			return;
		((GroupableTableHeader) header).setColumnMargin();
		int column = 0;
		Dimension size = header.getSize();
		Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
		Hashtable h = new Hashtable();
		// int columnMargin = header.getColumnModel().getColumnMargin();

		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			cellRect.height = size.height;
			cellRect.y = 0;
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			Enumeration cGroups = ((GroupableTableHeader) header)
					.getColumnGroups(aColumn);
			if (cGroups != null) {
				int groupHeight = 0;
				while (cGroups.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
					Rectangle groupRect = (Rectangle) h.get(cGroup);
					if (groupRect == null) {
						groupRect = new Rectangle(cellRect);
						Dimension d = cGroup.getSize(header.getTable());
						groupRect.width = d.width;
						groupRect.height = d.height;
						h.put(cGroup, groupRect);
					}
					paintCell(g, groupRect, cGroup);
					groupHeight += groupRect.height;
					cellRect.height = size.height - groupHeight;
					cellRect.y = groupHeight;
				}
			}
			cellRect.width = aColumn.getWidth();// + columnMargin;
			if (cellRect.intersects(clipBounds)) {
				paintCell(g, cellRect, column);
			}
			cellRect.x += cellRect.width;
			column++;
		}
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		// TableCellRenderer renderer = header.getDefaultRenderer();

		final JCheckBox selectBox = new JCheckBox();
		TableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				String valueStr = (String) value;
				JLabel label = new JLabel(valueStr);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				selectBox.setHorizontalAlignment(SwingConstants.CENTER);
				selectBox.setBorderPainted(true);
				selectBox.setSelected(Boolean.parseBoolean((String) value));
				JComponent component = (valueStr.equals("true") || valueStr
						.equals("false")) ? selectBox : label;

				component.setBorder(UIManager
						.getBorder("TableHeader.cellBorder"));

				JTableHeader header = table.getTableHeader();
				if (header != null) {
					component.setForeground(header.getForeground());
					component.setBackground(header.getBackground());
					component.setFont(header.getFont());
				}
				return component;
			}
		};

		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), aColumn.getHeaderValue(), false, false, -1,
				columnIndex);
		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x - 3,
				cellRect.y, cellRect.width + 3, cellRect.height, true);

	}

	private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
		TableCellRenderer renderer = cGroup.getHeaderRenderer();
		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);
		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width - 3, cellRect.height, true);
	}

	private int getHeaderHeight() {
		int height = 0;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);
			TableCellRenderer renderer = header.getDefaultRenderer();
			Component comp = renderer.getTableCellRendererComponent(header
					.getTable(), aColumn.getHeaderValue(), false, false, -1,
					column);
			int cHeight = comp.getPreferredSize().height;
			Enumeration enumeration = ((GroupableTableHeader) header)
					.getColumnGroups(aColumn);
			if (enumeration != null) {
				while (enumeration.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) enumeration
							.nextElement();
					cHeight += cGroup.getSize(header.getTable()).height;
				}
			}
			height = Math.max(height, cHeight);
		}
		return height;
	}

	private Dimension createHeaderSize(long width) {
		TableColumnModel columnModel = header.getColumnModel();
		width += columnModel.getColumnMargin() * columnModel.getColumnCount();
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}
}
