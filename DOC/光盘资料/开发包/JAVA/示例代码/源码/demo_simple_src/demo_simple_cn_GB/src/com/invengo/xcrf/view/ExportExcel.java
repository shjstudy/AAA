package com.invengo.xcrf.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel {

	JTable table;
	FileOutputStream fos;
	JFileChooser jfc = new JFileChooser();

	boolean needExport = false;

	public ExportExcel(JTable table) {
		this.table = table;
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return (file.getName().indexOf("xls") != -1);
			}

			public String getDescription() {
				return "Excel";
			}
		});

		int result = jfc.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			needExport = true;
		} else {
			needExport = false;
		}
		File file = jfc.getSelectedFile();
		try {
			this.fos = new FileOutputStream(file + ".xls");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public int export() {
		int result = 0;
		if (needExport) {
			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet hs = wb.createSheet();
				TableModel tm = table.getModel();
				int row = tm.getRowCount();
				int cloumn = tm.getColumnCount();
				HSSFCellStyle style = wb.createCellStyle();
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				HSSFFont font = wb.createFont();
				font.setFontHeightInPoints((short) 11);
				style.setFont(font);
				HSSFCellStyle style1 = wb.createCellStyle();
				style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style1.setFillForegroundColor(HSSFColor.ORANGE.index);
				style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				HSSFFont font1 = wb.createFont();
				font1.setFontHeightInPoints((short) 15);
				font1.setBoldweight((short) 700);
				style1.setFont(font);
				for (int i = 0; i < row + 1; i++) {
					HSSFRow hr = hs.createRow(i);
					for (int j = 0; j < cloumn; j++) {
						if (i == 0) {
							String value = tm.getColumnName(j);
							hs.setColumnWidth(j, 15 * 256);
							HSSFRichTextString srts = new HSSFRichTextString(
									value);
							HSSFCell hc = hr.createCell(j);
							hc.setCellStyle(style1);
							hc.setCellValue(srts);
						} else {
							if (tm.getValueAt(i - 1, j) != null) {
								String value = tm.getValueAt(i - 1, j)
										.toString();
								HSSFRichTextString srts = new HSSFRichTextString(
										value);
								HSSFCell hc = hr.createCell(j);
								hc.setCellStyle(style);
								if (value.equals("") || value == null) {
									hc.setCellValue(new HSSFRichTextString(""));
								} else {
									hc.setCellValue(srts);
								}
							}
						}
					}
				}
				wb.write(fos);
				fos.close();
				result = 0;
			} catch (IOException ex) {
				result = -1;
				ex.printStackTrace();
			}
		}else {
			result = 1;
		}
		return result;
	}
}
