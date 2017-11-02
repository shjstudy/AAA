package com.invengo.xcrf.ui.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.invengo.xcrf.core.i18n.BaseMessages;

public class MessageDialog {

	public static void showErrorMessage(Component rootComponent, String msg) {
		JOptionPane.showMessageDialog(rootComponent, msg);
	}

	public static void showInfoMessage(Component rootComponent, String msg) {
		JOptionPane.showOptionDialog(rootComponent, msg,
				BaseMessages.getString("OptionPane.messageDialogTitle"),
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, new String[] { BaseMessages.getString("button.ok") },
				null);
	}

	public static void showInfoMessage(String msg) {
		JOptionPane.showOptionDialog(null, msg,
				BaseMessages.getString("OptionPane.messageDialogTitle"),
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, new String[] { BaseMessages.getString("button.ok") },
				null);
	}

	public static boolean showConfirmDialog(String msg, String msg1) {
		return JOptionPane.showConfirmDialog(null, msg, msg1,
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

}
