package com.invengo.xcrf.core;

import invengo.javaapi.core.APIPath;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JComponent;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.core.demo.Demo;
import com.invengo.xcrf.core.demo.DemoRegistry;
import com.invengo.xcrf.util.NativeUtil;

public class Common {
	final static String fn = APIPath.folderName + "Sysit.xml";

	public static int killPwd_Ptr = 0;
	public static int killPwd_Len = 2;
	public static int accessPwd_Ptr = 2;
	public static int accessPwd_Len = 2;
	public static int EPC_CRC_Ptr = 0;
	public static int EPC_CRC_Len = 1;
	public static int EPC_PC_Ptr = 1;
	public static int EPC_PC_Len = 1;
	public static int EPC_Ptr = 2;
	public static int EPC_MaxLen = 15;
	public static int TID_Ptr = 0;
	public static int TID_MaxLen = 7;
	public static int userdata_Ptr = 0;
	public static int Userdata_MaxLen_6C = 32;

	public static int ID_MaxLen = 4;
	public static int userdata_MaxLen_6B = 108;

	public static boolean EPC_ASCII = false;
	public static boolean UserData_ASCII = false;

	public static boolean Console_Beep = false;

	static {
		try {
			Document doc = new SAXBuilder().build(fn);
			Element element = (Element) XPath.newInstance("//TagType")
					.selectSingleNode(doc);
			if (element != null) {
				killPwd_Ptr = Integer
						.parseInt(((Element) XPath.newInstance(
								"//Tag_6C//M0//KillPWD//Ptr").selectSingleNode(
								element)).getText());
				killPwd_Len = Integer
						.parseInt(((Element) XPath.newInstance(
								"//Tag_6C//M0//KillPWD//Len").selectSingleNode(
								element)).getText());
				accessPwd_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M0//AccessPWD//Ptr").selectSingleNode(
						element)).getText());
				accessPwd_Len = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M0//AccessPWD//Len").selectSingleNode(
						element)).getText());
				EPC_CRC_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//CRC//Ptr").selectSingleNode(element))
						.getText());
				EPC_CRC_Len = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//CRC//Len").selectSingleNode(element))
						.getText());
				EPC_PC_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//PC//Ptr").selectSingleNode(element))
						.getText());
				EPC_PC_Len = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//PC//Len").selectSingleNode(element))
						.getText());
				EPC_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//EPC//Ptr").selectSingleNode(element))
						.getText());
				EPC_MaxLen = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M1//EPC//Len").selectSingleNode(element))
						.getText());
				TID_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M2//Ptr").selectSingleNode(element))
						.getText());
				TID_MaxLen = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M2//Len").selectSingleNode(element))
						.getText());
				userdata_Ptr = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6C//M3//Ptr").selectSingleNode(element))
						.getText());
				Userdata_MaxLen_6C = Integer.parseInt(((Element) XPath
						.newInstance("//Tag_6C//M3//Len").selectSingleNode(
								element)).getText());
				ID_MaxLen = Integer.parseInt(((Element) XPath.newInstance(
						"//Tag_6B//ID").selectSingleNode(element))
						.getAttributeValue("Len"));
				userdata_MaxLen_6B = Integer.parseInt(((Element) XPath
						.newInstance("//Tag_6B//Userdata").selectSingleNode(
								element)).getAttributeValue("Len"));

			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Element GetModelInfoNode(String modelNumbel)
			throws JDOMException, IOException {
		Document doc = new SAXBuilder().build(APIPath.folderName
				+ "XCRFModelInfo.xml");
		if (doc != null) {
			return (Element) XPath.newInstance("//" + modelNumbel)
					.selectSingleNode(doc);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static String[] GetModels() {
		StringBuffer modelStr = new StringBuffer();

		try {
			Document doc = new SAXBuilder().build(APIPath.folderName
					+ "XCRFModelInfo.xml");
			if (doc != null) {
				Element root = (Element) XPath.newInstance("//ModelInfo")
						.selectSingleNode(doc);
				for (Element e : (List<Element>) root.getChildren()) {
					if (modelStr.length() > 0) {
						modelStr.append(",");
					}
					modelStr.append(e.getName());
				}
			}
			return modelStr.toString().split(",");
		} catch (Exception e1) {
			return new String[] {};
		}
	}

	public static int booleanArrayToInteger(boolean[] booleanArray) {
		StringBuffer sb = new StringBuffer(booleanArray.length);
		for (Boolean b : booleanArray) {

			sb.append(b ? "1" : "0");
		}

		return Integer.parseInt(sb.toString(), 2);
	}

	// index为从右往左数的索引
	public static boolean isZeroInByte(int i, int index) {
		String s = Integer.toBinaryString(i);
		String t = s.substring(s.length() - index - 1, s.length() - index);
		return "1".equals(t);
	}

	public static void readerCapabilitiesCheck(JComponent frm) {
		Demo demo = DemoRegistry.getCurrentDemo();
		if (demo != null) {
			try {
				readerCapabilitiesCheck(frm, demo.getProtocl().toString(), demo
						.getConfig().getModelNo(), demo.getConfig()
						.getReaderType());
			} catch (Exception e) {

			}
		}
	}

	public static void readerCapabilitiesCheck(JComponent frm, String protocol,
			String modelNumber, String readerType) throws JDOMException,
			IOException {
		if (protocol.equals("IRP1")) {
			Document doc = new SAXBuilder().build(APIPath.folderName
					+ "ReaderCapabilities.xml");
			try {
				Element xcrf = (Element) XPath.newInstance(
						"Capabilities//IRP1//XCRF//" + frm.getName())
						.selectSingleNode(doc);
				if (xcrf != null)
					setCtrlState(xcrf, frm);

			} catch (Exception e) {
			}

			// 系列节点
			try {
				Element snode = (Element) XPath.newInstance(
						"Capabilities//IRP1//XCRF" + readerType + "//"
								+ frm.getName()).selectSingleNode(doc);
				if (snode != null)
					setCtrlState(snode, frm);
			} catch (Exception e) {
			}

			// 型号节点
			try {
				if (modelNumber != null) {
					String xpath = "Capabilities//IRP1//XCRF" + modelNumber
							+ "//" + frm.getName();
					if (modelNumber.indexOf("XC") != -1)
						xpath = "Capabilities//IRP1//" + modelNumber + "//"
								+ frm.getName();

					Element model = (Element) XPath.newInstance(xpath)
							.selectSingleNode(doc);
					if (model != null) {
						setCtrlState(model, frm);
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	static void setCtrlState(Element e, JComponent frm) {

		for (Element child : (List<Element>) e.getChildren()) {
			for (Component c : frm.getComponents()) {
				if (c.getName() != null && c.getName().equals(child.getName())) {
					if (child.getAttribute("Enabled") != null) {
						c.setEnabled(child.getAttributeValue("Enabled")
								.toLowerCase().equals("true"));
					}
					if (child.getAttribute("Visible") != null) {
						c.setVisible(child.getAttributeValue("Visible")
								.toLowerCase().equals("true"));
					}
				}
			}
		}
	}

	public static void myBeep() {
		if (Console_Beep) {
			NativeUtil.beep();
		}
	}

	// ping测试
	public static boolean pingTest(String ip) {
		if (ip.indexOf(":") != -1)
			ip = ip.split(":")[0];

		if (System.getProperty("os.name").toLowerCase().indexOf("win") != -1) // W
		{
			try {
				Process p = Runtime.getRuntime().exec(
						"ping " + ip + " -n 1 -w 400");
				InputStreamReader ir = new InputStreamReader(p.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);

				String str = input.readLine();
				while (str != null && str != "") {
					if (str.indexOf("Request timed out.") != -1) {
						return false;
					}
					str = input.readLine();
				}
				return true;

			} catch (IOException e) {
				return false;
			}

		} else // L
		{
			try {
				return InetAddress.getByName(ip).isReachable(300);
			} catch (Exception e) {
				return false;
			}
		}

	}

	public static byte[] convertAsciiStringToByteArray(String str) {
		if (str == null)
			return null;
		return str.getBytes();
	}

	public static boolean isGetModelNOByCommand(String modelNO) {
		if (modelNO == null) {
			return false;
		}
		if (modelNO.contains("XC")) {
			return false;
		}
		return true;
	}

	public static boolean isGetModelNOBySelected(String modelNO) {
		if (modelNO == null) {
			return false;
		}
		if (modelNO.contains("XC")) {
			return true;
		}
		return false;
	}

	private static SimpleDateFormat s = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	private static SimpleDateFormat systemFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	public static String readUtcTimeFromEPC(byte[] epc) {
		byte[] utc = new byte[8];
		System.arraycopy(epc, epc.length - 8, utc, 0, 8);

		byte[] time = new byte[4];
		System.arraycopy(utc, 0, time, 0, 4);
		long ms = bytesToLong(time) * 1000;
		System.arraycopy(utc, 4, time, 0, 4);
		ms += bytesToLong(time) / 1000;

		Date utcd = new Date(ms);
		s.setTimeZone(TimeZone.getTimeZone("0"));
		return s.format(utcd) + "(UTC)";
	}

	private static long bytesToLong(byte[] buf) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		firstByte = (0x000000FF & (buf[0]));
		secondByte = (0x000000FF & (buf[1]));
		thirdByte = (0x000000FF & (buf[2]));
		fourthByte = (0x000000FF & (buf[3]));
		return ((firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}

	public static String getSystemTime() {
		return systemFormat.format(new Date());
	}

}
