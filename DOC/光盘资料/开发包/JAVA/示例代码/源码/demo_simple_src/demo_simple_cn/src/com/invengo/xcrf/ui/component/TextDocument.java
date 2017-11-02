package com.invengo.xcrf.ui.component;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * This class is used to limit the length and type of string that input into the
 * text box
 * 
 * @author dp732
 * 
 */
public class TextDocument extends DefaultStyledDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit;// constraint length
	private int type;// constraint type
	private boolean flag;// input rule

	public TextDocument(int limit, int type) {
		super();
		this.limit = limit;
		this.type = type;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null) {// character input can't be null
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String temp;
			if (i == str.length() - 1) {
				temp = str.substring(i);
			} else {
				temp = str.substring(i, i + 1);
			}

			switch (type) {
			case 0:
				flag = Pattern.compile("[0-9a-fA-F]").matcher(temp).find();
				break;
			case 1:
				flag = Pattern.compile("[0-9]").matcher(temp).find();
				break;
			case 2:
				flag = Pattern.compile("[0-9a-zA-Z]").matcher(temp).find();
				break;
			case 3:
				flag = Pattern.compile("[0-9a-fA-F]").matcher(temp).find();
				break;
			default:
				break;
			}

			if (flag) {
				sb.append(temp);
			}
		}

		str = sb.toString();
		if (str != "") {
			// if the input character is compliance with the rule
			str = str.toUpperCase();// change the character to capital
			if (limit != 0) {// if limit isn't equal '0', length of the input
				// string must less than or equal limit
				if ((getLength() + str.length()) <= limit) {
					super.insertString(offset, str, attr);
				}
			} else {// otherwise,no limit
				super.insertString(offset, str, attr);
			}
			if (type == 3) {
				if (getLength() == 3 || getLength() == 7
						|| getLength() % 4 == 3) {
					super.insertString(offset, "  ", attr);
				}
			}
		}
	}
}
