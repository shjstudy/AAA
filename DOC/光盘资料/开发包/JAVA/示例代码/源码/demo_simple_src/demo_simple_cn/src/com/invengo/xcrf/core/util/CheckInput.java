package com.invengo.xcrf.core.util;

import java.awt.Toolkit;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class CheckInput extends PlainDocument {
	private String limit = null; // 输入字符限制的正则表达式

	private int maxLength = -1; // 输入字符最大长度的限制，-1为不限制

	private double maxValue = 0; // 如果输入的是数字，则最大值限制
	private double minValue = 0; // 如果输入的是数字，则最小值限制

	private boolean isMaxValue = false; // 是否采用了最大值限制
	private boolean isMinValue = false; // 是否采用了最小值限制

	private Toolkit toolkit = null; // 用来在错误的时候发出系统声音
	private JTextComponent mytarget;
	private String limit2=null;
	
	public CheckInput(String limit,JTextComponent target,int maxLength)
	{
		super();
		this.init();
		this.limit=limit;
		this.maxLength=maxLength;
		target.setDocument(this);
		mytarget=target;
	}
	public CheckInput(String limit2,String limit,JTextComponent target,int maxLength)
	{
		super();
		this.init();
		this.limit=limit;
		this.limit2=limit2;
		this.maxLength=maxLength;
		target.setDocument(this);
		mytarget=target;
	}

	public CheckInput() {
		super();
		this.init();
	}

	private void init() {
		toolkit = Toolkit.getDefaultToolkit();
	}

	public void setCharLimit(String limit) {
		this.limit = limit;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setMaxValue(double maxValue) {
		this.isMaxValue = true;
		this.maxValue = maxValue;
	}
	
	public void setMinValue(double minValue) {
		this.isMinValue = true;
		this.minValue = minValue;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException, NumberFormatException {
		// offs起始的偏移量 str - 要插入的字符串
		// 若字符串为空，直接返回。
		if (str == null) {
			return;
		}
		char[] ch = str.toCharArray();
		if(mytarget!=null&&mytarget.getText()!=null&&limit2!=null)
		{
			boolean ab2 = Pattern.compile(limit2).matcher(mytarget.getText()+str).matches();
			if (!ab2) {
				super.insertString(offs, "", a);
				toolkit.beep(); // 发出声音
				return;
			}
		}

		
		for (int i = 0; i < ch.length; i++) {
			String temp = String.valueOf(ch[i]);
			// 如果要输入的字符不在允许范围内
			boolean ab = Pattern.compile(limit).matcher(temp).find();
			if (!ab) {
				toolkit.beep(); // 发出声音
				return;
			}
			// 如果有字符长度限制，并且现在的字符长度已经大于或等于限制
			if (maxLength > -1 && this.getLength() >= maxLength) {
				toolkit.beep(); // 发出声音
				return;
			}
		}
		// 如果内容设置了最大数字
		if (isMaxValue) {
			String s = this.getText(0, this.getLength()); // 文档中已有的字符
			s = s.substring(0, offs) + str + s.substring(offs, s.length());
			if (Double.parseDouble(s) > maxValue) {
				toolkit.beep(); // 发出声音
				return;
			}
		}
		// 如果内容设置了最小数字
		if (isMinValue) {
			String s = this.getText(0, this.getLength()); // 文档中已有的字符
			s = s.substring(0, offs) + str + s.substring(offs, s.length());
			if (Double.parseDouble(s) < minValue) {
				toolkit.beep(); // 发出声音
				return;
			}
		}
		super.insertString(offs, new String(ch), a);
	}
}