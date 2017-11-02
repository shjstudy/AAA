package com.invengo.xcrf.ui;

import invengo.javaapi.core.IMessage;
import invengo.javaapi.core.Util;

import java.awt.Component;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.core.demo.UserConfig;

public class Utils {
	public static final String EIGHT_00 = "800";
	public static final String FIVE_00 = "500";

	public static void setPanelEnabled(JPanel panel) {
		setPanelEnable(true, panel);
	}

	public static void setPanelDisabled(JPanel panel) {
		setPanelEnable(false, panel);
	}

	/**
	 * 
	 * @param falg
	 * @param panel
	 */
	public static void setPanelEnable(boolean flag, JPanel panel) {
		panel.setEnabled(flag);
		Component[] comps = panel.getComponents();
		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof JPasswordField) {
				JPasswordField pwdField = (JPasswordField) comps[i];
				pwdField.setEditable(flag);
			}
			if (comps[i] instanceof JPanel) {
				JPanel p = (JPanel) comps[i];
				setPanelEnable(flag, p);
			}
			comps[i].setEnabled(flag);
		}
	}

	public static boolean sendMsg(IMessage msg) {
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		return currentDemo.getReader().send(msg);
	}
	
	public static boolean sendMsg(IMessage msg , int timeout) {
		Demo currentDemo = DemoRegistry.getCurrentDemo();
		return currentDemo.getReader().send(msg,timeout);
	}
	

	public static UserConfig getUserConfig() {
		return DemoRegistry.getCurrentDemo().getConfig();
	}

	/**
	 * 创建一个随机字节数组
	 * 
	 * @param len
	 *            数组长度
	 * @return
	 */
	public static byte[] createRandomData(int len) {
		// 创建一个随机字节数组
		byte[] writeData = new byte[len * 2];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < writeData.length; i++) {
			writeData[i] = (byte) random.nextInt(128);
		}
		return writeData;
	}

	public static byte[] getPwd(String pwdText) {
		if (pwdText.equals(""))
			return new byte[4];
		byte[] pwd = Util.convertHexStringToByteArray(pwdText);
		if (pwd.length < 4) {
			byte[] p = new byte[4];
			System.arraycopy(pwd, 0, p, 4 - pwd.length, pwd.length);
			return p;
		}
		return pwd;
	}

	/**
	 * 
	 * @param msg
	 *            判断字符串是否为空
	 * @return
	 */
	public static boolean isEmpty(String msg) {
		return msg == null || msg.trim().equals("");
	}

	public static String padZero(String original) {
		return padZero(original, true);
	}

	public static String padZero(String original, boolean isRightPad) {
		if (original.length() % 4 == 0) {
			return original;
		}
		int expectLen = (original.length() + 3) / 4 * 4;
		char replace = '0';

		if (isRightPad)
			return padRight(original, expectLen, replace);

		return padLeft(original, expectLen, replace);
	}

	public static String padRight(String original, int expectLeng, char replace) {
		int length = original.length();
		if (expectLeng <= length) {
			return original;
		} else {
			StringBuilder sb = new StringBuilder(original);
			for (int i = 0; i < expectLeng - length; i++) {
				sb.append(replace);
			}
			return sb.toString();
		}
	}

	public static String padLeft(String original, int expectLeng, char replace) {
		int length = original.length();
		if (expectLeng <= length) {
			return original;
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < expectLeng - length; i++) {
				sb.append(replace);
			}
			sb.append(original);
			return sb.toString();
		}
	}

}
