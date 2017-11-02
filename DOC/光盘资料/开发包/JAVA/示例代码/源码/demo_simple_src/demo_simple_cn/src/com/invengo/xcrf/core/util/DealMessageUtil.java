package com.invengo.xcrf.core.util;

import invengo.javaapi.core.APIPath;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class DealMessageUtil {
	@SuppressWarnings("unchecked")
	public static List<Message> getMessages() {
		String path = APIPath.folderName + "IRP1Message.xml";
		List<Message> result = new ArrayList<Message>();
		try {
			Document doc = new SAXBuilder().build(new File(path));
			List<Element> messages = XPath.newInstance("//Message")
					.selectNodes(doc);
			for (Element element : messages) {
				Message msg = new Message();
				msg.setName(element.getAttributeValue("Name"));
				String type = element.getAttributeValue("Type");
				msg.setType(type);
				if ("00".equals(type)) {
					List<Element> items = element.getChildren();
					List<Item> itemList = new ArrayList<Item>();
					for (Element itemEle : items) {
						Item item = new Item();
						item.setName(itemEle.getAttributeValue("Name"));
						item.setType(itemEle.getAttributeValue("Type"));
						itemList.add(item);
					}
					msg.setItems(itemList);
				}
				result.add(msg);
			}
		} catch (Exception e) {
			// TODO 要处理
			e.printStackTrace();
		}
		return result;
	}

	public static void changeCommandValue(String name, String type) {
		String path = APIPath.folderName + "IRP1Message.xml";
		try {
			Document doc = new SAXBuilder().build(new File(path));
			Element element = (Element) XPath.newInstance(
					"//Message[@Name='" + name + "']").selectSingleNode(doc);
			element.setAttribute("Type", type);
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(doc, new FileOutputStream(new File(path)));
		} catch (Exception e) {
			// TODO 要处理
			e.printStackTrace();
		}
	}

	public static void changeCommandValue(String parentName, String itemName,
			String type) {
		String path = APIPath.folderName + "IRP1Message.xml";
		try {
			Document doc = new SAXBuilder().build(new File(path));
			Element itemElement = (Element) XPath.newInstance(
					"//Message[@Name='" + parentName + "']/Item[@Name='"
							+ itemName + "']").selectSingleNode(doc);
			itemElement.setAttribute("Type", type);
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(doc, new FileOutputStream(new File(path)));
		} catch (Exception e) {
			// TODO 要处理
			e.printStackTrace();
		}
	}

	public static class Message {
		private String name;
		private String type;// 十六进制的内容

		private List<Item> items = new ArrayList<Item>(0);

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

	}

	public static class Item {
		private String name;
		private String type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}
}
