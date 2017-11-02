package com.invengo.xcrf.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.invengo.xcrf.ui.panel.ConnType;
import com.invengo.xcrf.ui.panel.Pick;

public class UnitResultUtils {
	private static String fileName = "./test.log";
	private static DateFormat formatValue = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static void saveUnitResult(Pick pick) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fileName);
			Element root = doc.getRootElement();
			Element pickElement = new Element("Pick");
			pickElement.setAttribute("PickName", pick.getPickName());
			pickElement.setAttribute("MarkTime",
					formatValue.format(pick.getMarkTime()));
			pickElement.setAttribute("Protocol", pick.getProtocol());
			pickElement.setAttribute("TimeMill", pick.getMarkTime().getTime()
					+ "");
			Element connTypeElement = null;
			for (ConnType connType : pick.getConnTypes()) {
				connTypeElement = new Element("ConnType");
				connTypeElement.setAttribute("Value", connType.getValue());

				Element child = new Element("OpenPower");
				Text text = new Text(connType.getOpenPower() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("ClosePower");
				text = new Text(connType.getClosePower() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Antenna1");
				text = new Text(connType.getAntenna1() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Antenna2");
				text = new Text(connType.getAntenna2() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Antenna3");
				text = new Text(connType.getAntenna3() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Antenna4");
				text = new Text(connType.getAntenna4() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Beep");
				text = new Text(connType.getBeep() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("LED");
				text = new Text(connType.getLed() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("GPIO");
				text = new Text(connType.getGpio() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Rate");
				text = new Text(connType.getRate() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				child = new Element("Power");
				text = new Text(connType.getPower() + "");
				child.addContent(text);
				connTypeElement.addContent(child);

				pickElement.addContent(connTypeElement);
			}

			root.addContent(pickElement);
			xmlOut.output(doc, new FileOutputStream(new File(fileName)));

		} catch (JDOMException e) {
		} catch (IOException e) {
		}
	}

	public static void deleteUnitResult(String pickName) {
		try {
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			Document doc = new SAXBuilder().build(fileName);
			Element pick = (Element) XPath.newInstance(
					"//Pick[@PickName='" + pickName + "']").selectSingleNode(
					doc);
			doc.getRootElement().removeContent(pick);
			xmlOut.output(doc, new FileOutputStream(new File(fileName)));
		} catch (JDOMException e) {
		} catch (IOException e) {
		}
	}

	// ´æÔÚÎªtrue
	public static boolean checkPickName(String pickName) {
		try {
			Document doc = new SAXBuilder().build(fileName);
			Element pick = (Element) XPath.newInstance(
					"//Pick[@PickName='" + pickName + "']").selectSingleNode(
					doc);
			if (pick != null) {
				return true;
			}
		} catch (JDOMException e) {
		} catch (IOException e) {
		}
		return false;
	}

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 3);
		Date endTime = calendar.getTime();
		List<Pick> result = findUnitResults(startTime, endTime);
		System.out.println(result.size());

	}

	@SuppressWarnings("unchecked")
	public static List<Pick> findUnitResults(Date startTime, Date endTime) {
		List<Pick> picks = new ArrayList<Pick>();
		List<Element> pickElements;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long start = calendar.getTimeInMillis();

		calendar.setTime(endTime);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		long end = calendar.getTimeInMillis();

		try {
			Document doc = new SAXBuilder().build(fileName);
			pickElements = XPath
					.newInstance(
							"//Pick[@TimeMill>" + start + " and @TimeMill<"
									+ end + "]").selectNodes(doc);
		} catch (JDOMException e) {
			return picks;
		} catch (IOException e) {
			return picks;
		}

		Pick pick = null;
		int index = 1;
		for (Element pickElement : pickElements) {
			pick = new Pick();
			pick.setIndex(index++);
			pick.setPickName(pickElement.getAttributeValue("PickName"));
			try {
				pick.setMarkTime(formatValue.parse(pickElement
						.getAttributeValue("MarkTime")));
			} catch (ParseException e) {
				continue;
			}
			pick.setProtocol(pickElement.getAttributeValue("Protocol"));

			List<Element> connTypeElements = pickElement
					.getChildren("ConnType");
			List<ConnType> connTypes = new ArrayList<ConnType>();
			ConnType connType = null;
			for (Element connTypeElement : connTypeElements) {
				connType = new ConnType();
				connType.setValue(connTypeElement.getAttributeValue("Value"));

				connType.setOpenPower(Integer.parseInt(connTypeElement
						.getChild("OpenPower").getValue()));
				connType.setClosePower(Integer.parseInt(connTypeElement
						.getChild("ClosePower").getValue()));

				connType.setAntenna1(Integer.parseInt(connTypeElement.getChild(
						"Antenna1").getValue()));

				connType.setAntenna2(Integer.parseInt(connTypeElement.getChild(
						"Antenna2").getValue()));

				connType.setAntenna3(Integer.parseInt(connTypeElement.getChild(
						"Antenna3").getValue()));

				connType.setAntenna4(Integer.parseInt(connTypeElement.getChild(
						"Antenna4").getValue()));

				connType.setBeep(Integer.parseInt(connTypeElement.getChild(
						"Beep").getValue()));

				connType.setLed(Integer.parseInt(connTypeElement
						.getChild("LED").getValue()));
				connType.setGpio(Integer.parseInt(connTypeElement.getChild(
						"GPIO").getValue()));
				connType.setRate(Integer.parseInt(connTypeElement.getChild(
						"Rate").getValue()));
				connType.setPower(Integer.parseInt(connTypeElement.getChild(
						"Power").getValue()));
				connTypes.add(connType);
			}
			pick.setConnTypes(connTypes);

			picks.add(pick);
		}
		return picks;
	}

}
