package com.invengo.xcrf.core.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;


/**
 * 转换xml语系档为properties文件
 * @author zxq943
 *
 */
public class LanguageGetter {

	// TODO 转换语言的工具
	private static String fileName = "Demo(2052).xml" ;
	private static String[] otherProperties = {"MessageBox","Theme","Message"};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Properties properties = new Properties();
			List<String> keyLst = new ArrayList<String>();
			
			Document doc = new SAXBuilder().build(fileName);
			List<Element> forms = XPath.newInstance("//Form").selectNodes(doc);
			
			for (Element form : forms) 
			{
				String formName = form.getAttributeValue("Name");
				System.out.println(formName);
				
				for(Element child : (List<Element>)form.getChildren())
				{
					String childName = saveConvert(formName + "." + child.getAttributeValue("Name"),true,true);
					String childContent = saveConvert(child.getText(),false,true);
					System.out.println(childName + "="+childContent);
					
//					properties.setProperty(childName, childContent);
					keyLst.add(childName+"="+childContent);
				}
				
			}
			
			
			for(String type : otherProperties)
			{
				Element element = (Element) XPath.newInstance("//"+type).selectSingleNode(doc);
				System.out.println(type);
				
				for(Element child : (List<Element>)element.getChildren())
				{
					String childName = saveConvert(type + "." + child.getAttributeValue("Name"),true,true);
					String childContent = saveConvert(child.getText(),false,true);
					System.out.println(childName + "="+childContent);
					
//					properties.setProperty(childName, childContent);
					keyLst.add(childName+"="+childContent);
				}
			}
			
			
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("auto.properties"), "8859_1"));
			for(String s : keyLst)
			{
				bw.write(s);
				bw.newLine();
			}
			
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("auto1.properties"), "8859_1"));
			int i = 0 ;
			for(String s : keyLst)
			{
				bw1.write(s.split("=")[0]+"="+i++);
				bw1.newLine();
			}
			bw1.flush();
			bw.flush();
			
			
//			properties.store(new FileOutputStream("auto.properties"), "This is language properties that is created automaticlly !");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static String saveConvert(String theString, boolean escapeSpace,
			boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * @param	nibble	the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
}
