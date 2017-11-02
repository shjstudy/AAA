package com.invengo.xcrf.core.demo;

import invengo.javaapi.protocol.IRP1.Reader;

import java.util.HashMap;
import java.util.Map;

import com.invengo.xcrf.ui.tree.CheckNode;
import com.invengo.xcrf.ui.tree.NodeUtil;

public class DemoRegistry {

	private static final Map<String, Demo> allDemo = new HashMap<String, Demo>();

	private static Demo currentDemo;

	private static final Map<String, Demo> currentDemos = new HashMap<String, Demo>();

	public static void registryCurrentDemo(Demo demo) {
		String keyName = demo.getDemoName();
		currentDemos.put(keyName, demo);
	}

	public static void removeRegistryCurrentDemo(Demo demo) {
		String keyName = demo.getDemoName();
		if (currentDemos.containsKey(keyName)) {
			currentDemos.remove(keyName);
		}

		CheckNode node = demo.getNode();
		CheckNode parent = (CheckNode) node.getParent();
		if (parent.getNodeName().startsWith("Port:")) {
			if (allDemo.containsKey(keyName)) {
				allDemo.remove(keyName);
			}
		}
	}

	public static Map<String, Demo> getCurrentDemos() {
		return currentDemos;
	}

	public static Demo getDemoByReader(Reader reader) {
		String keyName = reader.getReaderName();
		return currentDemos.get(keyName);

	}

	public static Demo getDemoByName(String keyName) {
		Demo demo = allDemo.get(keyName);
		return demo;
	}

	public static Demo getDemoByNode(CheckNode node) {
		String key = NodeUtil.getNodeFullName(node);
		Demo demo = allDemo.get(key);
		return demo;
	}

	public static Map<String, Demo> getAllDemo() {
		return allDemo;
	}

	public static void registryDemo(Demo demo) {
		String keyName = demo.getDemoName();
		allDemo.put(keyName, demo);
	}

	public static Demo getTagAccessDemo(String name) {
		// if (name.lastIndexOf("--") == -1)
		// return null;
		// String realName = name.substring(0, name.lastIndexOf("--"));
		// String groupName = name.substring(name.lastIndexOf("--") + 2);
		// String keyName = "group:" + groupName + "name:" + realName;
		return currentDemos.get(name);
	}

	public static void removeRegistryDemo(Demo demo) {
		String keyName = demo.getDemoName();
		if (allDemo.containsKey(keyName))
			allDemo.remove(keyName);
	}

	public static void setCurrentDemo(Demo demo) {
		currentDemo = demo;
	}

	public static Demo getCurrentDemo() {
		return currentDemo;
	}

	public static boolean hasReadingDemo() {
		Map<String, Demo> demos = getCurrentDemos();
		for (Demo demo : demos.values()) {
			if (demo.getReader() != null && demo.getReader().isConnected()) {
				return true;
			}
		}
		return false;
	}

	public static void forceCloseReader() {
		Map<String, Demo> demos = getCurrentDemos();
		for (Demo demo : demos.values()) {
			if (demo.getReader() != null && demo.getReader().isConnected()
					&& demo.isReading()) {
				demo.forceDisConnect();
			}
		}
	}

}
