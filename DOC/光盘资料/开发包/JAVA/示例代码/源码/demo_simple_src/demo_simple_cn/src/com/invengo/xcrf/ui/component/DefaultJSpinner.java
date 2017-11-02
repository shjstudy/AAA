package com.invengo.xcrf.ui.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DefaultJSpinner extends JSpinner {

	private static final long serialVersionUID = -7675453967747753541L;
	private JSpinner.NumberEditor editor;

	public DefaultJSpinner(SpinnerNumberModel arg0) {
		super(arg0);
		editor = new JSpinner.NumberEditor(this, "#####");
		initNumEditor(editor, "0");
		this.setEditor(editor);
	}

	public int getIntValue() {
		int intVal = 0;
		try {
			intVal = Integer.parseInt(editor.getTextField().getText());
		} catch (Exception e) {

		}
		return intVal;
	}

	// 初始化预计读标签数选择栏
	public void initNumEditor(final JSpinner.NumberEditor editor,
			final String defaultVal) {
		editor.getTextField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				boolean flag = Pattern.compile("[0-9]").matcher(
						String.valueOf(e.getKeyChar())).find();

				try {
					int num = Integer.parseInt(editor.getTextField().getText());
					if (num < 0)
						editor.getTextField().setText(defaultVal);
				} catch (Exception ex) {
					editor.getTextField().setText(defaultVal);
				}

				if (!flag) {
					e.consume();
					return;
				}
			}

		});

		editor.getTextField().addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				int portInt = 0;
				try {
					portInt = Integer.parseInt(editor.getTextField().getText());
				} catch (Exception e) {
					editor.getTextField().setText(defaultVal);
				}
				if (portInt > 32768) {
					editor.getTextField().setText("32768");
				}
			}

		});
	}

}
