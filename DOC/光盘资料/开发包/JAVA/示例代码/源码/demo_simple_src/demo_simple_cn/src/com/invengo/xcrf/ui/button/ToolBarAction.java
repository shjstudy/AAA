package com.invengo.xcrf.ui.button;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import com.invengo.xcrf.core.i18n.BaseMessages;
import com.invengo.xcrf.ui.WidgetFactory;

public class ToolBarAction extends AbstractAction{

	/**
	 * 
	 */
	private static Class<?> PKG = WidgetFactory.class;
	
	private static final long serialVersionUID = 1L;
	String thename;
	boolean isCon = false;
	
	Component thisComponent;

	public ToolBarAction(String name, Icon icon,Component component) {
		super(name, icon);
		thename = name;
		thisComponent = component;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (thename == "out") {
				Toolkit.getDefaultToolkit().beep();
				Object[] options = { BaseMessages.getString("yes"),
						BaseMessages.getString("no") };
				int response = JOptionPane.showOptionDialog(thisComponent,
						BaseMessages.getString("exitConfirm"), BaseMessages
								.getString("systemInfo"),
						JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
				if (response == 0) {
					if (isCon) {
						try {
//							ConnManager.getIntance().closeconn();
						} catch (Exception e2) {
							System.exit(0);
						}
					}
					System.exit(0);
				}
			}
		} catch (Exception ex) {

		}
	}

	
	public void isCloseConn() {
		isCon = false;
	}

	
	public void isConn() {
		isCon = true;
	}
}
