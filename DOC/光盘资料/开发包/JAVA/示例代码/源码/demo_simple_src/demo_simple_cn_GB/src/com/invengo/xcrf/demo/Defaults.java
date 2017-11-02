package com.invengo.xcrf.demo;

import invengo.javaapi.protocol.IRP1.Reader;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class contains all global parameters and receives messages from the
 * reader
 * 
 * @author dp732
 * 
 */
public class Defaults {
	private int fontSize = 15;
	private String fontName = "Serif";
	private int fontType = 0;
	public int fontContainerHeight = 24;
	public Font font;
	public Font tableFont;
	public Font borderFont;
	public Color backgroundColor = new Color(6710886);
	public Color textColor = new Color(16777215);
	public Color menuColor = new Color(3355443);
	public Color menuTextColor = new Color(16777215);
	public Color titleColor = new Color(16763904);
	public Color listTitleColor = Color.lightGray;
	public int appletX = 0;// x-coordinate of this applet
	public int appletY = 0;// y-coordinate of this applet
	public int appletW = 820;// width of this applet
	public int appletH = 500;// height of this applet
	public int workspaceX;// x-coordinate of workspace
	public int workspaceY;// y-coordinate of workspace
	public int workspaceH;// height of workspace
	public int workspaceW;// width of workspace
	public Container workPanel;
	public static Messagedialog messageDialog;
	public ResourceBundle bundle;
	public Locale defaultLocale = Locale.getDefault();// default language
	public Reader reader;
	public String ip = "192.168.1.1";
	public boolean isConnected = true;
	public int conType = 0;// 连接方式 0 TCP 1 串口

	private static Defaults defaults = new Defaults();

	/**
	 * @param menu
	 * @param reader
	 * @return
	 * @preserve
	 */
	public static Defaults getInstance(Reader reader) {
		defaults.setReader(reader);
		messageDialog = new Messagedialog(defaults);
		messageDialog.setLocationRelativeTo(defaults.workPanel);
		return defaults;
	}

	private Defaults() {

	}

	public void setFont() {
		this.font = new Font(this.fontName, this.fontType, this.fontSize);
		this.fontContainerHeight = (this.fontSize + this.fontSize * 25 / 100);
		this.tableFont = new Font("Courier New", 1, this.fontSize * 90 / 100);
		this.borderFont = new Font(this.fontName, this.fontType,
				this.fontSize * 4 / 5);
	}

	/**
	 * Used to set work panel
	 * 
	 * @param workPanel
	 *            The workpanel to set
	 */
	public void setPanel(Container workPanel) {
		this.workPanel = workPanel;
	}

	/**
	 * Used to set locale-specific(language)
	 * 
	 * @param locale
	 *            The locale to set
	 */
	public void setLocale(Locale locale) {
		this.defaultLocale = locale;
		this.bundle = ResourceBundle.getBundle("resswing", defaultLocale);
	}

	private void setReader(Reader reader) {
		this.reader = reader;
	}

}