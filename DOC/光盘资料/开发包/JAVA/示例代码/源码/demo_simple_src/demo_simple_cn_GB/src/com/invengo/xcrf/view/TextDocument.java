package com.invengo.xcrf.view;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class is used to limit the length and type of string that input into the
 * text box
 * 
 * @author dp732
 * 
 */
public class TextDocument extends PlainDocument {

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

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null) {// character input can't be null
			return;
		}
		switch (type) {
		case 0:
			flag = Pattern.compile("[0-9a-fA-F]").matcher(str).find();
			break;
		case 1:
			flag = Pattern.compile("[0-9]").matcher(str).find();
			break;
		case 2:
			flag = Pattern.compile("[0-9a-zA-Z]").matcher(str).find();
			break;
		case 3:
			flag = Pattern.compile("[0-9a-fA-F]").matcher(str).find();
			break;
		case 4:
			flag = true;
			break;
		case 5:
			flag = Pattern.compile("[0-9a-fA-F]").matcher(str).find();
			break;
		}
		if (flag) {// if the input character is compliance with the rule
			if (type != 5 && type != 4) {
				str = str.toUpperCase();// change the character to capital
			}
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
